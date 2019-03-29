package com.scarlatti.springbatchdemobase;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.core.step.skip.CompositeSkipPolicy;
import org.springframework.batch.core.step.skip.ExceptionClassifierSkipPolicy;
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
    public Job helloWorldJob() {
        Step helloWorldStep = stepBuilderFactory
            .get("eggCookingStep")
            .<Egg, Egg>chunk(1)
            .reader(new EggReader())
            .processor(new EggProcessor())
            .writer(new EggWriter())
            .faultTolerant()
            .skipPolicy(new AlwaysSkipItemSkipPolicy())
            .listener(new EggCookingStepListener())
            .build();

        Job eggCookingJob = jobBuilderFactory
            .get("helloWorldJob")
            .incrementer(new FailingHelloWorldJobJobParametersIncrementer())
            .start(helloWorldStep)
            .build();

        return eggCookingJob;
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
