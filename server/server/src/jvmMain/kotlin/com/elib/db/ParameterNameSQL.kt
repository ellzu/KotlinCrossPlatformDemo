package com.elib.db

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLType
import java.sql.Types

class ParameterNameSQL(sql: String) {
    val resolveSQL: String
    private val parameterNames = arrayListOf<String>()

    init {
        val regex = Regex(":[a-zA-Z0-9_]+")
        this.resolveSQL = regex.replace(sql) {
            parameterNames.add(it.value.substring(1))
            return@replace "?"
        }
    }

    fun statementIndex(name: String): Int? {
        val index = parameterNames.indexOf(name)
        if (index < 0) {
            return null
        }
        return index + 1
    }

    fun setStatementObject(statement: PreparedStatement,  name: String,  value: Any,  targetSqlType: Int? = null) {
        statementIndex(name)?.let {
            if (targetSqlType != null) {
                statement.setObject(it, value, targetSqlType)
            } else {
                statement.setObject(it, value)
            }
        }
    }
}