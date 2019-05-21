package com.copyright.rup.dist.foreign.service.impl.job;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.integration.camel.IProducer;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Objects;

/**
 * Producer for sending information about executed jobs.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/14/2019
 *
 * @author Uladzislau Shalamitski
 */
public class JobStatusProducer implements IProducer<JobInfo> {

    @Autowired
    @Qualifier("df.service.producerTemplate")
    private ProducerTemplate producerTemplate;
    private String endPoint;

    /**
     * Sets producer template.
     *
     * @param producerTemplate an instance of {@link ProducerTemplate}
     */
    public void setProducerTemplate(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    /**
     * Sets end point.
     *
     * @param endPoint new value of end point
     */
    public void setEndPoint(String endPoint) {
        this.endPoint = Objects.requireNonNull(endPoint);
    }

    @Override
    public void send(JobInfo jobInfo) {
        producerTemplate.sendBodyAndHeader(endPoint, jobInfo, "status", jobInfo.getStatus().name());
    }
}
