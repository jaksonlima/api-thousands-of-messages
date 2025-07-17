package com.financial.infrastructure.tenant

import com.financial.domain.account.AccountID
import com.financial.domain.tenant.Tenant
import com.financial.domain.tenant.TenantGateway
import com.financial.infrastructure.tenant.persistence.TenantJpaEntity
import com.financial.infrastructure.tenant.persistence.TenantJpaRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.sql.SQLException
import java.util.*
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

    override fun findByAccountId(accountId: AccountID): Optional<Tenant> {
        return this.tenantJpaRepository.findByAccountId(accountId.toString())
            .map { it.toDomain() }
    }

    override fun createSchema(tenant: Tenant): Boolean {
        try {
            val tenantName = tenant.name
            val tenantId = tenant.id().toString()

            dataSource.connection.use { connection ->
                connection.autoCommit = true

                connection.createStatement().use { statement ->
                    val ps =
                        connection.prepareStatement(
                            """
                            SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?""".trimIndent()
                        )

                    ps.setString(1, tenantName)

                    val result = ps.executeQuery()

                    if (!result.next()) {
                        statement.execute("""CREATE SCHEMA "$tenantName"""")
                        statement.execute("""SET search_path TO "$tenantName"""")

                        val sql = this::class.java.getResource("/db/schema-template.sql")?.readText()
                            ?: throw IllegalStateException("schema-template.sql not found")

                        for (stmt in sql.split(";")) {

                            if (stmt.trim().isNotBlank()) {
                                statement.execute(stmt)
                            }
                        }
                        log.info("Schema created [tenant-id: $tenantId]")

                        return true;
                    } else {
                        log.warn("Schema exists [tenant-id: $tenantId]")
                    }
                }
            }

            return false
        } catch (e: SQLException) {
            log.error("Schema error [message:  ${e.message}]")
            throw e
        }
    }


}