package com.saishaddai

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform