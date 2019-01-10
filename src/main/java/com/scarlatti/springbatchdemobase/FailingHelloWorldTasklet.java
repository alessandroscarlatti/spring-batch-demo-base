package com.scarlatti.springbatchdemobase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * @author Alessandro Scarlatti
 * @since Wednesday, 1/9/2019
 */
@Component
public class FailingHelloWorldTasklet implements Tasklet {

    private static final Logger log = LoggerFactory.getLogger(FailingHelloWorldTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        if ((long) chunkContext.getStepContext().getJobParameters().get("run.id") == 3L) {
//            log.info("Succeeding because this is the third execution!");
//            contribution.setExitStatus(ExitStatus.COMPLETED);
//            return RepeatStatus.FINISHED;
//        } else {
//            log.info("Failing because we have not reached the third execution yet!");
//            contribution.setExitStatus(ExitStatus.FAILED);
//            return RepeatStatus.FINISHED;
//        }
//        contribution.setExitStatus(new ExitStatus("OH NO THIS IS BAD", "This is really, really, really, bad and very, very long"));
//        contribution.setExitStatus(new ExitStatus("THIS IS GOOD", "This is really, really, really, good and very, very long"));
        throw new RuntimeException("Oh no!");
//        chunkContext.getStepContext().getStepExecution().getFailureExceptions().add(new RuntimeException("Oh no!"));
//        contribution.setExitStatus(new ExitStatus("SUCCESS"));
//        return RepeatStatus.FINISHED;
    }
}
