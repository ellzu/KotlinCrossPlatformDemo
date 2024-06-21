

plugins {
    val kotlinVersion = "1.9.10"
    kotlin("multiplatform").version(kotlinVersion).apply(false)
    kotlin("jvm").version(kotlinVersion).apply(false)
    kotlin("js").version(kotlinVersion).apply(false)
    kotlin("plugin.serialization").version(kotlinVersion).apply(false)
//    id("org.jetbrains.reflekt").version("1.7.0").apply(false)
}