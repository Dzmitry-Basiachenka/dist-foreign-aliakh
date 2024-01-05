package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import com.copyright.rup.dist.foreign.domain.UsageBatch.AclciFields;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Implementation of {@link StdSerializer} for {@link AclciFields}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/08/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclciBatchFieldsSerializer extends StdSerializer<AclciFields> {

    private static final long serialVersionUID = 6068493155990937791L;

    /**
     * Default constructor.
     */
    AclciBatchFieldsSerializer() {
        super(AclciFields.class);
    }

    @Override
    public void serialize(AclciFields aclciFields, JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeStartObject();
        jg.writeNumberField("licensee_account_number", aclciFields.getLicenseeAccountNumber());
        jg.writeStringField("licensee_name", aclciFields.getLicenseeName());
        jg.writeEndObject();
    }
}
