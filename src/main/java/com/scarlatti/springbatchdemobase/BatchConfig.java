package com.scarlatti.springbatchdemobase;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.core.step.skip.CompositeSkipPolicy;
import org.springframework.batch.core.step.skip.ExceptionClassifierSkipPolicy;
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
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

    private Job nestedJob() {
        Step step = stepBuilderFactory.get("nestedStep")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("BatchConfig.nestedJob() ");
                return RepeatStatus.FINISHED;
            })
            .build();

        return jobBuilderFactory.get("nestedJob")
            .start(step)
            .build();
    }

    @Bean
    public Job helloWorldJob() {
        Step step = stepBuilderFactory.get("helloWorldStep")
            .job(nestedJob())
            .build();

        Job eggCookingJob = jobBuilderFactory
            .get("helloWorldJob")
            .start(step)
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
