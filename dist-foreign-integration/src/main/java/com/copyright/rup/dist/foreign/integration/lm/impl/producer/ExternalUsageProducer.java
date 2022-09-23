package com.copyright.rup.dist.foreign.integration.lm.impl.producer;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsageMessage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsageWrapper;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Implementation of {@link IProducer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
public class ExternalUsageProducer implements IProducer<ExternalUsageMessage> {

    @Autowired
    @Qualifier("df.integration.producerTemplate")
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
    public void send(ExternalUsageMessage message) {
        try {
            producerTemplate.sendBodyAndHeaders(endPoint, new ExternalUsageWrapper(message.getExternalUsages()),
                message.getHeaders());
        } catch (CamelExecutionException e) {
            throw new RupRuntimeException("Exception appeared while sending usages to LM", e);
        }
    }
}
