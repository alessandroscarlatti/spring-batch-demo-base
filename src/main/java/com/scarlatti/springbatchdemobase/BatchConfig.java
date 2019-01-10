package com.scarlatti.springbatchdemobase;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alessandro Scarlatti
 * @since Wednesday, 1/9/2019
 */
@Configuration
public class BatchConfig {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job job(HelloWorldTasklet helloWorldTasklet) {
        Step helloWorldStep = stepBuilderFactory
            .get("helloWorldStep")
            .tasklet(helloWorldTasklet)
            .build();

        Job helloWorldJob = jobBuilderFactory
            .get("helloWorldJob")
            .start(helloWorldStep)
            .build();

        return helloWorldJob;
    }
}
