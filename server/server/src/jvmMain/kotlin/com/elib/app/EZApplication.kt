package com.elib.app

import com.alibaba.druid.pool.DruidAbstractDataSource
import com.alibaba.druid.pool.DruidDataSource
import com.elib.request.RequestControllerAnnotation
import com.elib.request.findControllers
import com.elib.request.onRequest
import io.ktor.server.application.*
import io.ktor.server.routing.*
import javax.sql.DataSource


class EZApplication {
    companion object  {
        private lateinit var _shared: EZApplication
        val shared: EZApplication
            get() {
            if (Companion::_shared.isInitialized) {
                error("must be call EApplication.initWithXXX init application")
            }
            return _shared
        }

        fun initWithKtorAPP(ktorAPP: Application) {
            _shared = EZApplication(ktorAPP)
        }
    }

    var ktorAPP: Application? = null
    val dbSource : DataSource

//    private constructor() {
//        error("todo custom EApplication constructor")
//    }

    private constructor(ktorAPP: Application) {
        this.ktorAPP = ktorAPP

        val driverClass = configString("db.driverClass")
        val jdbcURL = configString("db.url")
        val jdbcUser = configString("db.user")
        val jdbcPassword = configString("db.password")
        val source = DruidDataSource()
        source.driverClassName = driverClass
        source.url = jdbcURL
        source.username= jdbcUser
        source.password = jdbcPassword

        source.maxWait = 5*1000
//        source.validationQuery = "select 1"
        source.isTestWhileIdle = true
        source.isTestOnBorrow = false
        source.isTestOnReturn = false
        source.initialSize = DruidAbstractDataSource.DEFAULT_INITIAL_SIZE
        source.maxActive = DruidAbstractDataSource.DEFAULT_MAX_ACTIVE_SIZE
        source.isUseUnfairLock = true

        dbSource = source

        loadRequestControllerAnnotation()
    }

}

fun EZApplication.configStringOrNil(name: String): String? {
    return ktorAPP?.run {
        val configs = environment.config.toMap()
        return environment.config.propertyOrNull(name)?.getString()
    }
}

fun EZApplication.configString(name: String): String {
    return configStringOrNil(name) ?: error("get config $name fail")
}


fun EZApplication.configStringsOrNil(name: String): List<String>? {
    return ktorAPP?.run {
        val configs = environment.config.toMap()
        return environment.config.propertyOrNull(name)?.getList()
    }
}

fun EZApplication.configStrings(name: String): List<String> {
    return configStringsOrNil(name) ?: error("get config $name fail")
}

//fun EZApplication.registRequestAnnotationController(pkg: String) {
//    RequestControllerAnnotation.findControllers(pkg).forEach {
//
//    }
//}

private fun EZApplication.loadRequestControllerAnnotation() {
    val pkgs = configStrings("RequestControllerAnnotation.packages")
//    var pkgs = pkgsString.split(",")
    pkgs.forEach {
        loadRequestControllerAnnotation(it)
    }
}

private fun EZApplication.loadRequestControllerAnnotation(pkg: String) {
    RequestControllerAnnotation.findControllers(pkg).forEach {
        loadRequestControllerAnnotation(it)
    }
}

private fun EZApplication.loadRequestControllerAnnotation(controller: RequestControllerAnnotation.Controller) {
    ktorAPP?.let { ktorAPP ->
        val clsPath = controller.annotation.path.ifEmpty {
            controller.kClass.simpleName ?: "/"
        }
        val route = ktorAPP.routing {}.route(clsPath){}
        controller.functions.forEach { function ->
            val funPath: String = function.annotation.path.ifEmpty {
                function.function.name
            }
            route.get(funPath) {
                RequestControllerAnnotation.onRequest(controller.kClass, function.function)
            }
        }
    }//end ktorAPP
}
