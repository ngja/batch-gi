package com.hansol.batchgi.docs

import com.hansol.batchgi.logger
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SecondJobConfiguration(
    val jobRepository: JobRepository,
    val transactionManager: PlatformTransactionManager,
) {

    @Bean
    fun secondJob(): Job {
        return JobBuilder("secondJob", jobRepository)
            .start(secondStep())
            .build()
    }

    @Bean
    fun secondStep(): Step {
        return StepBuilder("secondStep", jobRepository)
            .chunk<String, String>(10, transactionManager)
            .reader(itemReader())
            .processor(itemProcessor())
            .writer(itemWriter())
            .build()
    }

    @Bean
    fun itemReader(): ItemReader<String> {
        return ListItemReader(listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7"))
    }

    @Bean
    fun itemProcessor(): ItemProcessor<String, String> {
        return ItemProcessor { item -> "pro $item" }
    }

    @Bean
    fun itemWriter(): ItemWriter<String> {
        return ItemWriter { items -> logger.info { "## Write $items" }}
    }
}