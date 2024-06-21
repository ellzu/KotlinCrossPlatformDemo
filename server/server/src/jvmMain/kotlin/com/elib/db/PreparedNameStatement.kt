package com.elib.db

import java.sql.Connection
import java.sql.PreparedStatement

class PreparedNameStatement(conn: Connection, sql: String) {
    val resolveStatement: PreparedStatement
    val nameSQL: ParameterNameSQL
    init {
        nameSQL = ParameterNameSQL(sql)
        resolveStatement = conn.prepareStatement(nameSQL.resolveSQL)
    }
    fun setObject(name: String, value: Any, targetSqlType: Int?/*java.sql.Types*/ = null) {
        nameSQL.statementIndex(name)?.let {
            if (targetSqlType != null) {
                resolveStatement.setObject(it, value, targetSqlType)
            } else {
                resolveStatement.setObject(it, value)
            }
        }
    }
}