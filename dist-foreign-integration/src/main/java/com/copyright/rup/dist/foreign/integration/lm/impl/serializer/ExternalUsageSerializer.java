package com.copyright.rup.dist.foreign.integration.lm.impl.serializer;

import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsageWrapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Implementation of {@link StdSerializer} for {@link ExternalUsageWrapper}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/10/18
 *
 * @author Ihar Suvorau
 */
public class ExternalUsageSerializer extends StdSerializer<ExternalUsageWrapper> {

    private static final long serialVersionUID = 1299697721496866760L;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructor.
     */
    public ExternalUsageSerializer() {
        super(ExternalUsageWrapper.class);
    }

    @Override
    public void serialize(ExternalUsageWrapper externalUsageWrapper, JsonGenerator jsonGenerator,
                          SerializerProvider provider) throws IOException {
        jsonGenerator.setCodec(objectMapper);
        jsonGenerator.writeObject(externalUsageWrapper);
    }
}
