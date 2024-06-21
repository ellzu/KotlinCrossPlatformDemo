package com.elib.db

import com.elib.app.EZApplication
import org.jetbrains.exposed.sql.Database

fun Database.Companion.connect(): Database {
    val app = EZApplication.shared
//    val driverClass = app.config("db.driverClass")
//    val jdbcURL = app.config("db.url")
//    val  jdbcUser = app.config("db.user")
//    val jdbcPassword = app.config("db.password")
//    return Database.connect(jdbcURL, driverClass, jdbcUser, jdbcPassword)
    return Database.connect(app.dbSource)
}