package com.scarlatti.springbatchdemobase;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
            .incrementer(new FailingHelloWorldJobJobParametersIncrementer())
            .start(helloWorldStep)
            .build();

        return helloWorldJob;
    }

    private static class FailingHelloWorldJobJobParametersIncrementer extends RunIdIncrementer {
        @Override
        public JobParameters getNext(JobParameters parameters) {
            super.getNext(parameters);
            JobParameters jobParameters = (parameters == null) ? new JobParameters() : parameters;

            try {
                jobParameters = new JobParametersBuilder(jobParameters)
                    .addString("hostname", InetAddress.getLocalHost().getHostName(), false)
                    .toJobParameters();
            } catch (UnknownHostException e) {
                jobParameters = new JobParametersBuilder(jobParameters)
                    .addString("hostname", null, false)
                    .toJobParameters();
            }
            RunIdIncrementer runIdIncrementer = new RunIdIncrementer();

            return runIdIncrementer.getNext(jobParameters);
        }
    }
}
