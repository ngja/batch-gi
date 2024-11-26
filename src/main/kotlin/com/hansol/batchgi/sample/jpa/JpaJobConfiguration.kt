package com.hansol.batchgi.sample.jpa

import com.hansol.batchgi.sample.domain.CustomerCredit
import com.hansol.batchgi.sample.domain.trade.internal.CustomerCreditIncreaseProcessor
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class JpaJobConfiguration(
    private val jpaTransactionManager: JpaTransactionManager,
) : DefaultBatchConfiguration() {

    override fun getTransactionManager(): PlatformTransactionManager {
        return jpaTransactionManager
    }

    @Bean
    fun itemReader(entityManagerFactory: EntityManagerFactory): JpaPagingItemReader<CustomerCredit> {
        return JpaPagingItemReaderBuilder<CustomerCredit>().name("itemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select c from CustomerCredit c")
            .build()
    }

    @Bean
    fun itemWriter(entityManagerFactory: EntityManagerFactory): JpaItemWriter<CustomerCredit> {
        return JpaItemWriterBuilder<CustomerCredit>().entityManagerFactory(entityManagerFactory).build()
    }

    @Bean
    fun job(
        jobRepository: JobRepository,
        jpaTransactionManager: JpaTransactionManager,
        itemReader: JpaPagingItemReader<CustomerCredit>,
        itemWriter: JpaItemWriter<CustomerCredit>
    ): Job {
        return JobBuilder("ioSampleJob", jobRepository)
            .start(
                StepBuilder("step1", jobRepository)
                    .chunk<CustomerCredit, CustomerCredit>(2, jpaTransactionManager)
                    .reader(itemReader)
                    .processor(CustomerCreditIncreaseProcessor())
                    .writer(itemWriter)
                    .build()
            )
            .build()
    }

    @Bean
    fun jpaTransactionManager(entityManagerFactory: EntityManagerFactory): JpaTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }

    @Bean
    fun entityManagerFactory(
        persistenceUnitManager: PersistenceUnitManager,
        dataSource: DataSource
    ): EntityManagerFactory? {
        val factoryBean = LocalContainerEntityManagerFactoryBean()
        factoryBean.dataSource = dataSource
        factoryBean.setPersistenceUnitManager(persistenceUnitManager)
        factoryBean.jpaVendorAdapter = HibernateJpaVendorAdapter()
        factoryBean.afterPropertiesSet()
        return factoryBean.`object`
    }

    @Bean
    fun persistenceUnitManager(dataSource: DataSource): PersistenceUnitManager {
        val persistenceUnitManager = DefaultPersistenceUnitManager()
        persistenceUnitManager.defaultDataSource = dataSource
        persistenceUnitManager.afterPropertiesSet()
        return persistenceUnitManager
    }
}