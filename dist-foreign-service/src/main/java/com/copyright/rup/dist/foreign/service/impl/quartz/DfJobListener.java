package com.copyright.rup.dist.foreign.service.impl.quartz;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.job.JobInfo;
import com.copyright.rup.dist.foreign.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.service.impl.job.JobStatusProducer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * Listener for jobs.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/14/2019
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.service.jobListener")
public class DfJobListener extends JobListenerSupport {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private JobStatusProducer producer;

    @Override
    public String getName() {
        return "JobListener";
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        JobInfo jobInfo = buildJobInfo(context, jobException);
        LOGGER.debug("Send job status info. Started. JobInfo={}", jobInfo);
        producer.send(jobInfo);
        LOGGER.debug("Send job status info. Finished. JobInfo={}", jobInfo);
    }

    private JobInfo buildJobInfo(JobExecutionContext context, JobExecutionException jobException) {
        JobInfo jobInfo;
        if (Objects.isNull(jobException)) {
            jobInfo = Objects.requireNonNull((JobInfo) context.getResult());
        } else {
            jobInfo = new JobInfo();
            jobInfo.setStatus(JobStatusEnum.FAILED);
            jobInfo.setResult(buildErrorMessage(jobException));
        }
        jobInfo.setName(context.getJobDetail().getKey().getName());
        jobInfo.setExecutionDatetime(
            OffsetDateTime.ofInstant(context.getFireTime().toInstant(), ZoneId.systemDefault()));
        return jobInfo;
    }

    private String buildErrorMessage(JobExecutionException jobException) {
        return String.format("ErrorMessage=%s, ErrorStackTrace=%s",
            ExceptionUtils.getMessage(jobException),
            ExceptionUtils.getStackTrace(jobException));
    }
}
