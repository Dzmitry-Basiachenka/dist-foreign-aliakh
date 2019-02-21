package com.copyright.rup.dist.foreign.ui.rest;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.service.api.scheduler.ISchedulerService;
import com.copyright.rup.dist.common.service.api.scheduler.ScheduledJobStatus;
import com.copyright.rup.dist.foreign.ui.rest.gen.api.JobsApiDelegate;
import com.copyright.rup.dist.foreign.ui.rest.gen.model.JobStatus;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * REST service for scheduled jobs workflow.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/06/2019
 *
 * @author Aliaksanr Liakh
 */
@Component
public class JobsRest implements JobsApiDelegate {

    @Autowired
    private ISchedulerService schedulerService;

    @Override
    public ResponseEntity<JobStatus> getJobStatus(String jobName) {
        try {
            ScheduledJobStatus status = schedulerService.getJobStatus(jobName);
            if (ScheduledJobStatus.RUNNING == status) {
                return new ResponseEntity<>(buildJobStatus("RUNNING"), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(buildJobStatus("WAITING"), HttpStatus.OK);
            }
        } catch (SchedulerException e) {
            throw new RupRuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<JobStatus> triggerJob(String jobName) {
        try {
            ScheduledJobStatus status = schedulerService.triggerJob(jobName);
            if (ScheduledJobStatus.RUNNING == status) {
                return new ResponseEntity<>(buildJobStatus("RUNNING"), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(buildJobStatus("TRIGGERED"), HttpStatus.OK);
            }
        } catch (SchedulerException e) {
            throw new RupRuntimeException(e);
        }
    }

    private JobStatus buildJobStatus(String status) {
        JobStatus jobStatus = new JobStatus();
        jobStatus.setStatus(status);
        return jobStatus;
    }
}
