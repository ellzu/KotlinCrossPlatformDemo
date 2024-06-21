package com.ezxx1.server

import com.elib.app.EZApplication
import com.elib.request.RequestContext
import com.elib.request.RequestController
import com.elib.request.RequestMapping
import com.ezxx1.users.userRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.json.Json
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ConfigurationBuilder
import kotlin.reflect.full.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
//fun main(args: Array<String>) {
//    embeddedServer(Netty, 8080, "0.0.0.0", module = Application::init).start(true)
//}

fun io.ktor.server.application.Application.init() {
    EZApplication.initWithKtorAPP(this)
    install(RequestContext.ktorPlugin)
    install(ContentNegotiation){
        json(Json {
            prettyPrint = true
            encodeDefaults = true
            explicitNulls = false
        })
    }
    install(Sessions) {
        cookie<String>("token")
    }
    install(Resources){
    }

//    userRouting()
}

