package com.scarlatti.springbatchdemobase;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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
    public Job helloWorldJob(HelloWorldTasklet helloWorldTasklet) {
        Step helloWorldStep = stepBuilderFactory
            .get("helloWorldStep")
            .tasklet(helloWorldTasklet)
            .build();

        Job helloWorldJob = jobBuilderFactory
            .get("helloWorldJob")
            .incrementer(new RunIdIncrementer())
            .start(helloWorldStep)
            .build();

        return helloWorldJob;
    }

    @Bean
    public Job failingHelloWorldJob(FailingHelloWorldTasklet failingHelloWorldTasklet, HelloWorldTasklet helloWorldTasklet) {
        Step failingHelloWorldStep = stepBuilderFactory
            .get("failingHelloWorldStep")
            .tasklet(failingHelloWorldTasklet)
            .build();

        Step helloWorldStep = stepBuilderFactory
            .get("helloWorldStep")
            .tasklet(helloWorldTasklet)
            .build();

        Job failingHelloWorldJob = jobBuilderFactory
            .get("failingHelloWorldJob")
            .incrementer(new FailingHelloWorldJobJobParametersIncrementer())
            .start(failingHelloWorldStep)
            .next(helloWorldStep)
            .build();

        return failingHelloWorldJob;
    }

    private static class FailingHelloWorldJobJobParametersIncrementer implements JobParametersIncrementer {
        @Override
        public JobParameters getNext(JobParameters parameters) {
            JobParameters jobParameters = (parameters == null) ? new JobParameters() : parameters;

            jobParameters = new JobParametersBuilder(jobParameters)
                .addString("jobName", "FAILING_JOB", true)
                .toJobParameters();
            RunIdIncrementer runIdIncrementer = new RunIdIncrementer();

            return runIdIncrementer.getNext(jobParameters);
        }
    }
}
