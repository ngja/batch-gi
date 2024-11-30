package com.hansol.batchgi.docs

import org.junit.jupiter.api.Test
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
@SpringBatchTest
class FirstJobConfigurationTests(
    @Autowired private val jobLauncherTestUtils: JobLauncherTestUtils,
    @Autowired private val firstJob: Job,
) {

    @Test
    fun testJob() {
        jobLauncherTestUtils.job = firstJob
        val jobExecution = jobLauncherTestUtils.launchJob()
        assertEquals(ExitStatus.COMPLETED, jobExecution.exitStatus)
    }
}