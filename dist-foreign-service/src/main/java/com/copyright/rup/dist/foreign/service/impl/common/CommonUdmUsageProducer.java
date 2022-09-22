package com.copyright.rup.dist.foreign.service.impl.common;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.UdmUsage;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Common implementation of {@link IProducer} to send UDM usages to queue.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Ihar Suvorau
 */
public class CommonUdmUsageProducer implements IProducer<List<UdmUsage>> {

    @Autowired
    @Qualifier("df.service.producerTemplate")
    private ProducerTemplate producerTemplate;

    private String endPoint;

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
    public void send(List<UdmUsage> usages) {
        try {
            producerTemplate.sendBody(endPoint, usages);
        } catch (CamelExecutionException e) {
            throw new RupRuntimeException(
                String.format("Exception appeared while sending UDM usages to queue. Endpoint=%s. Usages=%s", endPoint,
                    usages), e);
        }
    }
}
