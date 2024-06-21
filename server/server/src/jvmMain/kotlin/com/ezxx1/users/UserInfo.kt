package com.ezxx1.users

import kotlinx.serialization.*
import org.jetbrains.exposed.sql.*

@Serializable
class UserInfo() {
    var id: ULong? = null
    var phone: String? = null
    var password: String? = null
}

object UserTable: Table("user") {
    val id = ulong("id").autoIncrement().entityId()
    val phone = varchar("phone", 16)
    val password = varchar("password", 64)
    override val primaryKey = PrimaryKey(id)
}