package com.saishaddai.feature.soundboard

import android.media.MediaDataSource
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import shake2play.shared.generated.resources.Res
import java.io.IOException
import kotlin.coroutines.resume

class AndroidSoundPlayer(
    private val lifecycleOwner: LifecycleOwner,
) : SoundPlayer, DefaultLifecycleObserver {
    private val mainHandler = Handler(Looper.getMainLooper())
    private var activePlayer: MediaPlayer? = null
    private var activeDataSource: ByteArrayMediaDataSource? = null
    private var activeContinuation: CancellableContinuation<PlaybackResult>? = null
    private var activePlayback: CompletableDeferred<PlaybackResult>? = null
    private var activePlayJob: Job? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override suspend fun play(soundId: String): PlaybackResult {
        val resourcePath = soundResourcePath(soundId)
            ?: return PlaybackResult.Failed(PlaybackFailure.SOUND_NOT_FOUND)

        return withContext(Dispatchers.Main.immediate) {
            if (lifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                return@withContext PlaybackResult.Failed(PlaybackFailure.LIFECYCLE_UNAVAILABLE)
            }

            activePlayback?.let { return@withContext it.await() }

            val playback = CompletableDeferred<PlaybackResult>()
            activePlayback = playback
            activePlayJob = currentCoroutineContext()[Job]
            try {
                val audioBytes = Res.readBytes(resourcePath)
                val result = suspendCancellableCoroutine { continuation ->
                    startPlayback(audioBytes, continuation)
                }
                playback.complete(result)
                result
            } finally {
                if (activePlayback === playback) {
                    activePlayback = null
                    activePlayJob = null
                }
                if (!playback.isCompleted) {
                    playback.cancel()
                }
            }
        }
    }

    override fun stop() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            stopOnMainThread()
        } else {
            mainHandler.post(::stopOnMainThread)
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        stopOnMainThread()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        stopOnMainThread()
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    private fun startPlayback(
        audioBytes: ByteArray,
        continuation: CancellableContinuation<PlaybackResult>,
    ) {
        val dataSource = ByteArrayMediaDataSource(audioBytes)
        val player = MediaPlayer()
        activeDataSource = dataSource
        activePlayer = player
        activeContinuation = continuation

        continuation.invokeOnCancellation {
            mainHandler.post { releasePlayback(continuation) }
        }

        player.setOnPreparedListener { preparedPlayer ->
            if (activePlayer !== preparedPlayer) return@setOnPreparedListener
            try {
                preparedPlayer.start()
            } catch (_: IllegalStateException) {
                finishPlayback(PlaybackResult.Failed(PlaybackFailure.DECODER_ERROR))
            }
        }
        player.setOnCompletionListener {
            finishPlayback(PlaybackResult.Completed)
        }
        player.setOnErrorListener { _, _, _ ->
            finishPlayback(PlaybackResult.Failed(PlaybackFailure.DECODER_ERROR))
            true
        }

        try {
            player.setDataSource(dataSource)
            player.prepareAsync()
        } catch (_: IOException) {
            finishPlayback(PlaybackResult.Failed(PlaybackFailure.DECODER_ERROR))
        } catch (_: IllegalArgumentException) {
            finishPlayback(PlaybackResult.Failed(PlaybackFailure.DECODER_ERROR))
        } catch (_: IllegalStateException) {
            finishPlayback(PlaybackResult.Failed(PlaybackFailure.DECODER_ERROR))
        }
    }

    private fun finishPlayback(result: PlaybackResult) {
        val continuation = activeContinuation ?: return
        releasePlayback(continuation)
        continuation.resume(result)
    }

    private fun releasePlayback(continuation: CancellableContinuation<PlaybackResult>) {
        if (activeContinuation !== continuation) return
        activeContinuation = null
        activePlayer?.release()
        activePlayer = null
        activeDataSource?.close()
        activeDataSource = null
    }

    private fun stopOnMainThread() {
        val continuation = activeContinuation
        if (continuation != null) {
            releasePlayback(continuation)
            continuation.cancel()
        }
        activePlayJob?.cancel()
    }
}

private class ByteArrayMediaDataSource(
    private val bytes: ByteArray,
) : MediaDataSource() {
    override fun readAt(position: Long, buffer: ByteArray, offset: Int, size: Int): Int {
        if (position < 0 || position >= bytes.size) return -1
        val bytesToRead = minOf(size, bytes.size - position.toInt())
        bytes.copyInto(
            destination = buffer,
            destinationOffset = offset,
            startIndex = position.toInt(),
            endIndex = position.toInt() + bytesToRead,
        )
        return bytesToRead
    }

    override fun getSize(): Long = bytes.size.toLong()

    override fun close() = Unit
}
