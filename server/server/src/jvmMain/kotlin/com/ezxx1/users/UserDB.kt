package com.ezxx1.users

import org.jetbrains.exposed.sql.Table

class UserDB {
    companion object {
        val table = Table()
    }
    class Table: org.jetbrains.exposed.sql.Table {
        constructor(): super(name = "user") {
            val x = UserInfo::class
            for (member in x.members) {
                println("member.name: ${member.name} type: ${member.returnType}")
            }
        }
    }
}

val userDBTable = Table("user").apply {

}
