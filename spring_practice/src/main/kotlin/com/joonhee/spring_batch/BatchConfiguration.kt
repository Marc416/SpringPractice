package com.joonhee.spring_batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource


@Configuration
class BatchConfiguration(){


    // csv reader to create com.joonhee.spring_batch.Person
    @Bean
    fun reader(): FlatFileItemReader<Person> {
        return FlatFileItemReaderBuilder<Person>()
            .name("personItemReader")
            .resource(ClassPathResource("sample-data.csv"))
            .delimited()
            .names("firstName", "lastName")
            .targetType(Person::class.java)
            .build()
    }

    // Converter to UpperCase - Business Logic
    @Bean
    fun processor(): PersonItemProcessor {
        return PersonItemProcessor()
    }

    // JdbcBatchItemWriter to insert com.joonhee.spring_batch.Person to DB
    @Bean
    fun writer(dataSource: DataSource): JdbcBatchItemWriter<Person> {
        return JdbcBatchItemWriterBuilder<Person>()
            .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
            .dataSource(dataSource)
            .beanMapped()
            .build()
    }

    /**
     * Actual Job Configuration below
     */

    @Bean
    fun importUserJob(jobRepository: JobRepository?, step1: Step, listener: JobCompletionNotificationListener): Job {
        return JobBuilder("importUserJob", jobRepository!!)
            .listener(listener)
            .start(step1)
            .build()
    }

    // chunk 를 사용하는경우
    // Chunk how much date to process at a time = 3
    // 3개 씩 배치 처리
    @Bean
    fun step2(jobRepository: JobRepository?, transactionManager: DataSourceTransactionManager,
              reader: FlatFileItemReader<Person>?, processor: PersonItemProcessor?, writer: JdbcBatchItemWriter<Person>?): Step {
        return StepBuilder("step1", jobRepository!!)
            .chunk<Person, Person>(3, transactionManager!!)
            .reader(reader!!)
            .processor(processor!!)
            .writer(writer!!)
            .build()
    }

    @Bean
    fun dataSourceTransactionManager(dataSource: DataSource): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }


}
