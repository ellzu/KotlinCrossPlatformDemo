package com.ezkmm


class IOSPlatform: Platform {
    override val name: String = "123"
}

actual fun getPlatform(): Platform = IOSPlatform()
