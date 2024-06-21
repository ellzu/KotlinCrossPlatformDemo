
plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
//    id("org.jetbrains.reflekt")
    application
}

group = "com.ezxx1"
version = "1.0-SNAPSHOT"

//repositories {
//    mavenCentral()
//    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
//}

kotlin {
    jvm {
        jvmToolchain(20)
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }
    js {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }

    sourceSets {

        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                val kotlinVersion = "1.9.10"
                implementation("org.slf4j:slf4j-api:2.0.9")
                implementation("org.slf4j:slf4j-simple:2.0.9")

                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

                val ktorVersion = "2.3.4"
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-server-html-builder-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-resources:$ktorVersion")
                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-server-request-validation:$ktorVersion")
                implementation("io.ktor:ktor-server-auth:$ktorVersion")
                implementation("io.ktor:ktor-server-cors:$ktorVersion")
                implementation("io.ktor:ktor-server-sessions:$ktorVersion")
                implementation("io.ktor:ktor-server-auto-head-response:$ktorVersion")
                implementation("io.ktor:ktor-server-locations:$ktorVersion")

                // https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
                implementation("com.mysql:mysql-connector-j:8.1.0")

                val exposedVersion = "0.41.1"
                implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
                implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
                implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

                implementation("com.mchange:c3p0:0.9.5.5")
                implementation("com.alibaba:druid:1.2.19")

                implementation("org.reflections:reflections:0.10.2")

            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.624")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.3-pre.346")
//                implementation("org.jetbrains.kotlin-wrappers:kotlin-csstype-js:3.1.2-pre.624")
//                implementation("org.jetbrains.kotlin-wrappers:kotlin-csstype:3.1.2-pre.625")
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("com.ezxx1.server.ServerKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}