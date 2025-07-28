package com.financial.infrastructure.configuration.multitenant

import org.hibernate.HibernateException
import org.hibernate.cfg.AvailableSettings
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

@Component
class TenantConnectionProvider(
    private val dataSource: DataSource,
) : MultiTenantConnectionProvider<String>, HibernatePropertiesCustomizer {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(TenantConnectionProvider::class.java)
    }

    override fun getAnyConnection(): Connection {
        return dataSource.connection;
    }

    override fun releaseAnyConnection(connection: Connection) {
        connection.close()
    }

    override fun getConnection(tenantIdentifier: String): Connection {
        val connection = anyConnection
        try {
            connection.createStatement().execute("""set search_path to "$tenantIdentifier"""")
        } catch (e: SQLException) {
            log.error(e.message)
            throw HibernateException("Unable to change to schema $tenantIdentifier")
        }
        return connection
    }

    override fun releaseConnection(tenantIdentifier: String, connection: Connection) {
        try {
            connection.use {
                connection.createStatement().execute("""set search_path to "$tenantIdentifier"""")
            }
        } catch (e: SQLException) {
            log.error(e.message)
            throw HibernateException("Unable to connect to schema $tenantIdentifier")
        }
    }

    override fun supportsAggressiveRelease(): Boolean {
        return true
    }

    override fun isUnwrappableAs(unwrapType: Class<*>): Boolean {
        return false
    }

    override fun <T : Any?> unwrap(
        unwrapType: Class<T?>
    ): T? {
        return null
    }

    override fun customize(hibernateProperties: MutableMap<String, Any>) {
        hibernateProperties[AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER] = this
    }
}