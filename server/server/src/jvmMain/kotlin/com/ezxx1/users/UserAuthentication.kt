package com.ezxx1.users

import com.elib.request.RequestContext


class UserToken(val id: ULong, val token: String, val userID: ULong) {

}

class UserAuthentication {
    companion object {
    }

}

suspend fun UserAuthentication.Companion.verify() {
    val call = RequestContext.current

//    println("token: $token")
}
