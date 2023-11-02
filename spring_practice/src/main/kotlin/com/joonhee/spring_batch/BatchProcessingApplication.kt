package com.joonhee.spring_batch

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class BatchProcessingApplication {
}

fun main(args: Array<String>) {
    runApplication<BatchProcessingApplication>(*args)
//    System.exit(SpringApplication.exit(runApplication<BatchProcessingApplication>(*args)))
}