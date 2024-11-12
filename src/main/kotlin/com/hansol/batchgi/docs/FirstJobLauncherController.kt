package com.hansol.batchgi.docs

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FirstJobLauncherController(
    private val jobLauncher: JobLauncher,
    private val firstJob: Job,
) {

    @PostMapping("/launch")
    fun launch() {
        // Running Jobs from within a Web Container
        jobLauncher.run(firstJob, JobParameters())
    }
}