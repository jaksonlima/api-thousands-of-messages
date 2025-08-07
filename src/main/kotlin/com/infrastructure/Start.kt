package com.infrastructure

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class Start(
    private val dataSource: DataSource
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        dataSource.connection.use { connection ->
            connection.autoCommit = true

            connection.createStatement().use { statement ->
                val sql = this::class.java.getResource("/db/template.sql")?.readText()
                    ?: throw IllegalStateException("template.sql not found")

                for (stmt in sql.split(";")) {

                    if (stmt.trim().isNotBlank()) {
                        statement.execute(stmt)
                    }
                }
            }
        }
    }
}