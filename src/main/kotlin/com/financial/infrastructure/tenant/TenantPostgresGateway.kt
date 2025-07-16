package com.financial.infrastructure.tenant

import com.financial.domain.tenant.Tenant
import com.financial.domain.tenant.TenantGateway
import com.financial.domain.tenant.TenantID
import com.financial.infrastructure.tenant.persistence.TenantJpaEntity
import com.financial.infrastructure.tenant.persistence.TenantJpaRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import java.sql.SQLException
import javax.sql.DataSource

@Component
class TenantPostgresGateway(
    private val tenantJpaRepository: TenantJpaRepository,
    private val dataSource: DataSource
) : TenantGateway {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(TenantPostgresGateway::class.java)
    }

    override fun create(tenant: Tenant): Tenant {
        this.tenantJpaRepository.save(TenantJpaEntity.from(tenant))

        createSchema(tenant)

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
            log.error("Set schema error [message:  ${e.message}]")
            throw e
        }
    }

    fun createSchema(tenant: Tenant) {
        try {
            val tenantName = tenant.name

            dataSource.connection.use { connection ->
                connection.autoCommit = true

                connection.createStatement().use { statement ->
                    val result =
                        statement.executeQuery(
                            """
                            SELECT schema_name FROM information_schema.schemata WHERE schema_name = '$tenantName'
                        """.trimIndent()
                        )

                    if (!result.next()) {
                        statement.execute("""CREATE SCHEMA "$tenantName"""")
                        statement.execute("""SET search_path TO "$tenantName"""")

                        val sql = Files.readString(Path.of("src/main/resources/schema-template.sql"))

                        for (stmt in sql.split(";")) {

                            if (stmt.trim().isNotEmpty()) {
                                statement.execute(stmt)
                            }
                        }

                        log.info("Schema created [tenant-id: $tenantName]")
                    } else {
                        log.warn("Exists schema [tenant-id: $tenantName]")
                    }
                }
            }
        } catch (e: SQLException) {
            log.error("Schema error [message:  ${e.message}]")
            throw e
        }
    }

}