package com.hansol.batchgi.docs

import com.hansol.batchgi.logger
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class FirstJobConfiguration(
    val jobRepository: JobRepository,
    val transactionManager: PlatformTransactionManager,
) {


    @Bean
    fun firstJob(firstStep: Step): Job {
        return JobBuilder("firstJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .listener(FirstJobExecutionListener())
            .start(firstStep)
            .build()
    }

    @Bean
    fun firstStep(): Step {
        return StepBuilder("firstStep", jobRepository)
            .tasklet(
                {_, _ ->
                    logger.info { "Hello, World!" }
                    RepeatStatus.FINISHED
                }, transactionManager
            )
            .build()
    }
}