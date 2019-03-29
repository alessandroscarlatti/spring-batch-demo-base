package com.scarlatti.springbatchdemobase;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 3/28/2019
 */
public class FailingStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        throw new RuntimeException("Asdf");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        throw new RuntimeException("asdf");
    }
}
