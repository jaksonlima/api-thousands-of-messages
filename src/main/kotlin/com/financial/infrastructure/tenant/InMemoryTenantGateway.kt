package com.financial.infrastructure.tenant

import com.financial.domain.tenant.Tenant
import com.financial.domain.tenant.TenantGateway
import com.financial.domain.tenant.TenantID
import java.nio.file.Files
import java.nio.file.Path
import java.sql.SQLException
import java.util.concurrent.ConcurrentHashMap
import javax.sql.DataSource


class InMemoryTenantGateway(
    private val dataSource: DataSource
) : TenantGateway {

    companion object {
        private val db: MutableMap<TenantID, Tenant> = ConcurrentHashMap()
    }

    override fun create(tenant: Tenant): Tenant {
        db.putIfAbsent(tenant.id(), tenant)
        return tenant
    }

    fun setSearchPath(tenantId: TenantID) {
        try {
            val tenantId = tenantId.value().toString()

            dataSource.connection.use { connection ->
                connection.createStatement().use { statement ->
                    statement.execute("SET search_path TO $tenantId");
                }
            }
        } catch (e: SQLException) {
            println("Schema error [message:  ${e.message}]")
            throw e
        }
    }

    fun createSchema(tenantId: TenantID) {
        try {
            val tenantId = tenantId.value().toString()

            dataSource.connection.use { connection ->
                connection.createStatement().use { statement ->
                    val result =
                        statement.executeQuery("""
                            SELECT schema_name FROM information_schema.schemata WHERE schema_name = $tenantId
                        """.trimIndent())

                    if (!result.next()) {
                        statement.execute("CREATE SCHEMA $tenantId");
                        statement.execute("SET search_path TO $tenantId");

                        val sql = Files.readString(Path.of("src/main/resources/schema-template.sql"))

                        for (stmt in sql.split(";")) {

                            if (stmt.trim().isNotEmpty()) {
                                statement.execute(stmt)
                            }
                        }

                        println("Schema created [tenant-id:  $tenantId]")
                    }
                }
            }
        } catch (e: SQLException) {
            println("Schema error [message:  ${e.message}]")
            throw e
        }
    }
}