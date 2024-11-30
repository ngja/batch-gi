package com.hansol.batchgi.controller

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class JobLauncherController(
    private val jobLauncher: JobLauncher,
    private val firstJob: Job,
    private val secondJob: Job,
) {

    @PostMapping("/launch/first")
    fun launch() {
        // Running Jobs from within a Web Container
        jobLauncher.run(firstJob, JobParameters(mapOf("seconds" to JobParameter(LocalDateTime.now().second, Int::class.java))))
    }

    @PostMapping("/launch/second")
    fun launchSecond() {
        jobLauncher.run(secondJob, JobParameters(mapOf("seconds" to JobParameter(LocalDateTime.now().second, Int::class.java))))
    }
}