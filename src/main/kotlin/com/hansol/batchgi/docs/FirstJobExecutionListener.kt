package com.hansol.batchgi.docs

import com.hansol.batchgi.logger
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener

class FirstJobExecutionListener : JobExecutionListener {
    override fun beforeJob(jobExecution: JobExecution) {
        logger.info { "Job ID ${jobExecution.jobId}" }
        logger.info { "Job Parameters ${jobExecution.jobParameters}" }
        logger.info { "Job Instance ${jobExecution.jobInstance}" }
    }

    override fun afterJob(jobExecution: JobExecution) {
        // 성공, 실패 무조건 실행
        // 구분하려면 분기 필요
        if (BatchStatus.COMPLETED == jobExecution.status) {
            logger.info { "COMPLETED" }
        }
        if (BatchStatus.FAILED == jobExecution.status) {
            logger.info { "FAILED" }
        }
    }
}