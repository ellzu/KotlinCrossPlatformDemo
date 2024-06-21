package com.elib.request

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
class RequestContext {

    companion object {
        private  val callContexts = ThreadLocal<RequestContext?>()

        val currentOrNull: RequestContext? = callContexts.get()
        val current: RequestContext
            get() {
            return callContexts.get() ?: error("current context null")
        }

        val ktorPlugin = createApplicationPlugin("ECallContext.ktorPlugin") {
            onCall {
                println("onCall Thread: ${Thread.currentThread().id}")
                callContexts.set(RequestContext(it))
            }
            onCallRespond { call, body ->
                val eCall = current

//                if (eCall.responseModel != null) {
//                    val body = eCall.responseModel!!
//                    eCall.responseModel = null
//                    //这里会重新触发一次ktor的respond响应链，所以这种做法不是太好
//                    //但是 ktor暂时没有更好的办法修改响应body,
//                    //所以暂时在RequestContext设计一个executeRespond函数，让onRequest事件负责唤起响应过程
//                    call.respond(body)
//                    return@onCallRespond
//                }

                eCall.responseHeaders.forEach {
                    call.response.headers.append(it.key, it.value, false)
                }

                eCall.responseCode?.let {
                    call.response.status(HttpStatusCode(it, "${it}"))
                }
                callContexts.remove()
            }
        }
    }

    private val ktorAppCall: ApplicationCall
    private constructor(ktorAppCall: ApplicationCall) {
        this.ktorAppCall = ktorAppCall
        ktorAppCall.response
    }

    protected val responseHeaders = LinkedHashMap<String, String>()
    fun setResponseHeader(name: String, value: String?) {
        if (value != null) {
            responseHeaders[name] = value
        } else {
            responseHeaders.remove(name)
        }
    }
    fun responseHeader(name: String): String? = responseHeaders[name]
    var responseCode: Int? = null
    var responseModel: Any? = null

    suspend fun executeRespond() {
        val model = responseModel ?: return
        ktorAppCall?.let {
            it.respond(model)
        }
    }

    fun requestHeader(name: String): String? = ktorAppCall.request.header(name)
    fun requestParameter(name: String): String? = ktorAppCall.parameters[name]

    fun sessionValue(name: String): Any? {
        return ktorAppCall.sessions.get(name)
    }
    fun sessionSetValue(name: String, value: Any?) {
        if (value != null) {
            ktorAppCall.sessions.set(name, value)
        } else {
            ktorAppCall.sessions.clear(name)
        }
    }

}