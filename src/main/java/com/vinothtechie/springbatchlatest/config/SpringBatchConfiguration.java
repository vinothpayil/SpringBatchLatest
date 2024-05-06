package com.vinothtechie.springbatchlatest.config;

import com.vinothtechie.springbatchlatest.dto.EmployeeDTO;

import com.vinothtechie.springbatchlatest.entity.Employee;
import com.vinothtechie.springbatchlatest.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SpringBatchConfiguration {

    private JobRepository jobRepository;
    private PlatformTransactionManager platformTransactionManager;
    private DataSource dataSource;

    @Bean
    @StepScope
    public FlatFileItemReader<EmployeeDTO> reader(
            @Value("#{jobParameters['inputFilePath']}")
            FileSystemResource fileSystemResource) {
        return new FlatFileItemReaderBuilder<EmployeeDTO>()
                .name("employeeItemReader")
                .linesToSkip(1)
                .resource(fileSystemResource)
                .delimited()
                .names("employeeId","fullName","jobTitle","department",
                        "businessUnit","gender","ethnicity","age")
                .targetType(EmployeeDTO.class)
                .build();
    }

    @Bean
    public ItemProcessor<EmployeeDTO, Employee> itemProcessor() {
        return new EmployeeProcessor();
    }

    @Bean
    public EmployeeWriter employeeWriter(final EmployeeRepository employeeRepository) {
        return new EmployeeWriter(employeeRepository);

    }

    @Bean
    public Step importEmployeeStep(final JobRepository jobRepository,
                                   final PlatformTransactionManager platformTransactionManager,
                                   final EmployeeRepository employeeRepository
                                   ) {
        return new StepBuilder("importEmployeeStep",jobRepository)
                .<EmployeeDTO, Employee>chunk(100,platformTransactionManager)
                .reader(reader(null))
                .processor(itemProcessor())
                .writer(employeeWriter(employeeRepository))
                .build();

    }

    @Bean
    public Job importEmployeeJob(final JobRepository jobRepository,
                                 final PlatformTransactionManager platformTransactionManager,
                                 final EmployeeRepository employeeRepository) {
        return new JobBuilder("job",jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importEmployeeStep(jobRepository,platformTransactionManager,employeeRepository))
                .build();
    }

}
