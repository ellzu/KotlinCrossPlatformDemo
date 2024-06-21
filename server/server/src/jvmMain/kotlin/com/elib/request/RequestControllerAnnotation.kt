package com.elib.request

import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ConfigurationBuilder
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.findAnnotations

annotation class RequestController(val path: String = "")
annotation class RequestMapping(val path: String = "")

class RequestControllerAnnotation {
    companion object{
    }
    data class Function(
        val function: KFunction<*>,
        val annotation: RequestMapping
    )
    data class Controller(
        val kClass: KClass<*>,
        val annotation: RequestController,
        val functions: List<RequestControllerAnnotation.Function>
    )
}

suspend fun RequestControllerAnnotation.Companion.onRequest(kCls: KClass<*>, kFun: KFunction<*>)  {
    val constructors = kCls.constructors.firstOrNull {
        return@firstOrNull it.parameters.isEmpty()
    }
    val obj = constructors?.call() ?: error("create controller fail with: $kCls")
    var callResult: Any? = null
    if (kFun.isSuspend) {
        callResult = kFun.callSuspend(obj)
    } else {
        callResult = kFun.call(obj)
    }

    val eCall = RequestContext.current
    if (eCall.responseModel == null && callResult != null && callResult !is Unit) {
        eCall.responseModel = callResult
    }
    eCall.executeRespond()
}

fun RequestControllerAnnotation.Companion.findControllers(pkg: String): List<RequestControllerAnnotation.Controller> {
    val reflection = Reflections(
        ConfigurationBuilder()
            .forPackage(pkg)
            .setScanners(Scanners.TypesAnnotated)
    )
    val kClsSet = reflection.getTypesAnnotatedWith(RequestController::class.java).map {
        it.kotlin
    }
    val controllers = mutableListOf<RequestControllerAnnotation.Controller>()
    kClsSet.forEach { kCls ->
        val clsAnnotation = kCls.findAnnotations(RequestController::class).first()
        val funtions = mutableListOf<RequestControllerAnnotation.Function>()
        kCls.declaredFunctions.forEach { kFun ->
            val annotation = kFun.findAnnotations(RequestMapping::class).firstOrNull() ?: return@forEach
            funtions.add(RequestControllerAnnotation.Function(kFun, annotation))
        }
        controllers.add(RequestControllerAnnotation.Controller(kCls, clsAnnotation, funtions))
    }
    return controllers
}