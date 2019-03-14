package com.scarlatti.springbatchdemobase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * @author Alessandro Scarlatti
 * @since Wednesday, 1/9/2019
 */
@Component
public class HelloWorldTasklet implements Tasklet {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldTasklet.class);
    private NamedParameterJdbcTemplate jdbcTemplate;

    public HelloWorldTasklet(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Hello World");

        long jobId = chunkContext
            .getStepContext()
            .getStepExecution()
            .getJobExecution()
            .getJobId();

        int strangeWidgetsFound = Math.abs(new Random(new Date().getTime()).nextInt());

        // save how many strange widgets we found
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("batchId", jobId)
            .addValue("strangeWidgetsFound", strangeWidgetsFound);

        jdbcTemplate.update("INSERT INTO STRANGE_WIDGETS_FOUND (BATCH_ID, WIDGETS_FOUND) VALUES(:batchId, :strangeWidgetsFound)", params);

        return RepeatStatus.FINISHED;
    }
}
