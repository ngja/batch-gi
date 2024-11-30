package com.hansol.batchgi.sample.jpa

import com.hansol.batchgi.sample.domain.CustomerCredit
import com.hansol.batchgi.sample.domain.trade.internal.CustomerCreditIncreaseProcessor
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class JpaJobConfiguration(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
) {

    @Bean
    fun jpaItemReader(entityManagerFactory: EntityManagerFactory): JpaPagingItemReader<CustomerCredit> {
        return JpaPagingItemReaderBuilder<CustomerCredit>().name("itemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select c from CustomerCredit c")
            .build()
    }

    @Bean
    fun jpaItemWriter(entityManagerFactory: EntityManagerFactory): JpaItemWriter<CustomerCredit> {
        return JpaItemWriterBuilder<CustomerCredit>().entityManagerFactory(entityManagerFactory).build()
    }

    @Bean
    fun jpaJob(
        itemReader: JpaPagingItemReader<CustomerCredit>,
        itemWriter: JpaItemWriter<CustomerCredit>
    ): Job {
        return JobBuilder("ioSampleJob", jobRepository)
            .start(
                StepBuilder("step1", jobRepository)
                    .chunk<CustomerCredit, CustomerCredit>(2, transactionManager)
                    .reader(itemReader)
                    .processor(CustomerCreditIncreaseProcessor())
                    .writer(itemWriter)
                    .build()
            )
            .build()
    }
}