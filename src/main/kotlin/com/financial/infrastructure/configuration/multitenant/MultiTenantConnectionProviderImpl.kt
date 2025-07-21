package com.financial.infrastructure.configuration.multitenant

import org.hibernate.HibernateException
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

@Component
class MultiTenantConnectionProviderImpl(
    private val dataSource: DataSource
) : MultiTenantConnectionProvider<String> {

    override fun getAnyConnection(): Connection {
        return dataSource.connection
    }

    override fun releaseAnyConnection(connection: Connection) {
        connection.close()
    }

    override fun getConnection(tenantIdentifier: String): Connection {
        val connection = anyConnection
        try {
            connection.createStatement().execute("""SET search_path TO "$tenantIdentifier"""")
        } catch (e: SQLException) {
            throw HibernateException("Unable to change to schema $tenantIdentifier", e)
        }
        return connection
    }

    override fun releaseConnection(tenantIdentifier: String, connection: Connection) {
        try {
            connection.createStatement().execute("set search_path to $tenantIdentifier")
        } catch (e: SQLException) {
            throw HibernateException("Unable to connect to schema $tenantIdentifier", e)
        } finally {
            connection.close()
        }
    }

    override fun supportsAggressiveRelease(): Boolean = true

    override fun isUnwrappableAs(unwrapType: Class<*>): Boolean = false

    override fun <T : Any?> unwrap(
        unwrapType: Class<T?>
    ): T? = null

}