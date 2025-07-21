package com.financial.infrastructure.configuration.multitenant

import org.hibernate.cfg.Environment
import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration
class HibernateConfig(
    private val jpaProperties: JpaProperties
) {
    private val jpaPathPackagesToScan = "com.financial.infrastructure.*"

    @Bean
    fun jpaVendorAdapter(): JpaVendorAdapter {
        return HibernateJpaVendorAdapter()
    }

    @Bean
    fun entityManagerFactory(
        dataSource: DataSource,
        multiTenantConnectionProviderImpl: MultiTenantConnectionProvider,
        currentTenantIdentifierResolverImpl: CurrentTenantIdentifierResolver
    ): LocalContainerEntityManagerFactoryBean {
        val properties = HashMap(jpaProperties.properties)
        properties[Environment.MULTI_TENANT_CONNECTION_PROVIDER] = multiTenantConnectionProviderImpl
        properties[Environment.MULTI_TENANT_IDENTIFIER_RESOLVER] = currentTenantIdentifierResolverImpl

        return LocalContainerEntityManagerFactoryBean().apply {
            setDataSource(dataSource)
            setPackagesToScan(jpaPathPackagesToScan)
            jpaVendorAdapter = jpaVendorAdapter()
            setJpaPropertyMap(properties)
        }
    }
}