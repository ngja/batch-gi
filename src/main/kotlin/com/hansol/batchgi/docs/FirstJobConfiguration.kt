package com.hansol.batchgi.docs

import com.hansol.batchgi.logger
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class FirstJobConfiguration : DefaultBatchConfiguration() {

    @Bean
    fun firstRunner(jobLauncher: JobLauncher, firstJob: Job) =  CommandLineRunner {
        val jobExecution = jobLauncher.run(firstJob, JobParameters())
        logger.info { "Job Status: ${jobExecution.status}" }
        logger.info { "Job Completed" }
    }

    @Bean
    fun firstJob(jobRepository: JobRepository, firstStep: Step): Job {
        return JobBuilder("firstJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .listener(FirstJobExecutionListener())
            .start(firstStep)
            .build()
    }

    @Bean
    fun firstStep(jobRepository: JobRepository, transactionManager: PlatformTransactionManager): Step {
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