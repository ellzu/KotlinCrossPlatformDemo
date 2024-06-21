package com.ezxx1.users

import com.elib.request.RequestContext
import com.elib.request.RequestController
import com.elib.request.RequestMapping
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.userRouting() {
    routing {
        UserRoute.unauthorizedRouting(this)
        UserRoute.authenticatedRouting(this)
    }
}


class UserRoute {
    companion object {
    }
}

fun  UserRoute.Companion.unauthorizedRouting(routing: Routing) = routing.route("/") {
    get("user.regist") {
        println("coming in regist")
        call.sessions.set("token", "1")
        call.respond(UserInfo())
    }
    get("user.login") {
        call.sessions.set("token", "1")
        val u = UserInfo()
        RequestContext.current.setResponseHeader("etest1", "etest_value")
        call.respond(u)
    }
}

fun UserRoute.Companion.authenticatedRouting(routing: Routing) = routing.route("/user") {
    install(createRouteScopedPlugin("UserAuthentication"){
        onCall {
            UserAuthentication.verify()
        }
    })
    get("info") {
        val userID = call.parameters["userID"]
        call.respondText {
            "request detail with $userID"
        }
    }
    get("loginout") {
        call.sessions.clear("token")
        call.respondText { "success" }
    }
}


@RequestController("/user2")
class UserController {

    @RequestMapping
    fun login() {
        val call = RequestContext.current
        val u = UserInfo()
        u.phone = "login"
        call.responseModel = u
    }

    @RequestMapping
    fun login2(): UserInfo? {
        val call = RequestContext.current
        val u = UserInfo()
        u.phone = "login2"
        return u
    }

    @RequestMapping
    fun info(): UserInfo? {
        val u = UserInfo()
        u.phone = "empty"
        return u
    }

}


//fun UserRoute.Companion.login() {
//    val call = RequestContext.current
//}
//fun  tmysql() {
////    Class.forName("com.mysql.cj.jdbc.Driver")
//    val app = EApplication.shared
//    val url = app.config("db.url")
//    println("db.url: ${url}")
//    val conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ezxx1", "root", "gjsgjs")
//    val statement = conn.prepareStatement("select * from user")
//    val result = statement.executeQuery()
//    while (result.next()) {
//        val id = result.getInt(1)
//        val phone = result.getString(2)
//        val password = result.getString(3)
//        print("user id: $id, phone:$phone, password:$password")
//    }
//
//}
//
//fun  tmysql2() {
//    println("UserInfo::id.name:${UserInfo::id.name}")
//    userTableInfo.integer(UserInfo::id.name).autoIncrement()
//    userTableInfo.varchar(UserInfo::phone.name, 16)
//    userTableInfo.varchar(UserInfo::password.name, 64)
//
//    val driverClassName = "com.mysql.cj.jdbc.Driver"
//    val jdbcURL = "jdbc:mysql://127.0.0.1:3306/ezxx1"
//    val database = Database.connect(jdbcURL, driverClassName, "root", "gjsgjs")
//    transaction(database) {
//        val users =  userTableInfo.slice(userTableInfo.columns[0],userTableInfo.columns[1]).select() {
//
//                userTableInfo.columns.filter {
//                    it.name.equals(UserInfo::id.name)
//                }.first() as Column<Int> eq 1
//
//            }
//            .map {
//                val u = UserInfo()
//                u.id = it[userTableInfo.columns.first() as Column<Int>]
////                u.phone = it[UserTable.phone]
////                u.password = it[UserTable.password]
//                return@map u
//            }
//        println("users.count:${users.count()} ${UserInfo::id.name}")
//    }
//
//}
