package com.copyright.rup.dist.foreign.service.impl.matching;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Implementation of {@link IProducer} to send usages for rights matching.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/15/2018
 *
 * @author Ihar Suvorau
 */
public class RightsMatchingProducer implements IProducer<Usage> {

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
        checkArgument(StringUtils.isNotBlank(endPoint), "End point should not be null or blank");
        this.endPoint = endPoint;
    }

    @Override
    public void send(Usage message) throws RupRuntimeException {
        try {
            producerTemplate.sendBody(endPoint, message);
        } catch (CamelExecutionException e) {
            throw new RupRuntimeException("Exception appeared while sending usages for rights queue", e);
        }
    }
}
