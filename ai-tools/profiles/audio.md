# Audio profile

## Purpose

Validate project audio assets or verify sound playback in an approved local test environment.

## Allowed capabilities

- Read and inspect project audio assets relevant to the task, including format and basic metadata.
- Play a project sound through the app or a local test harness when playback verification is required.
- Run existing audio-related unit or UI tests after checking the configured tasks and environment.
- Create or replace audio assets only when explicitly requested and within the project's asset directory.

## Boundaries

- Do not record from a microphone, access ambient audio, or inspect unrelated audio files without explicit authorization.
- Do not upload audio to external services without explicit authorization.
- Do not change system audio settings or play unexpected audio outside the approved test context.
- Do not overwrite or delete existing audio assets unless the task explicitly calls for it.
