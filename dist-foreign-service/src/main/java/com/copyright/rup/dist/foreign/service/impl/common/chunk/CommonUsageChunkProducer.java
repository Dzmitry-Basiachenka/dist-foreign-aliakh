package com.copyright.rup.dist.foreign.service.impl.common.chunk;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Common implementation of {@link IProducer} to send usages to queue.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/12/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class CommonUsageChunkProducer implements IProducer<List<Usage>> {

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
    public void send(List<Usage> usages) throws RupRuntimeException {
        try {
            producerTemplate.sendBody(endPoint, usages);
        } catch (CamelExecutionException e) {
            throw new RupRuntimeException(
                String.format("Exception appeared while sending usages to queue. Endpoint=%s. Usages=%s", endPoint,
                    usages), e);
        }
    }
}
