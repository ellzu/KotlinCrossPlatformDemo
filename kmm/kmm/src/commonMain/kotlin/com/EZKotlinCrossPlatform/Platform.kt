package com.ezkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform