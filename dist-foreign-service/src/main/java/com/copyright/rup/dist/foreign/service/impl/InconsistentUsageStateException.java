package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.Usage;

/**
 * Represents exceptional case when usage is in inconsistent state.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/28/2019
 *
 * @author Uladzislau Shalamitski
 */
public class InconsistentUsageStateException extends RupRuntimeException {

    /**
     * Constructor.
     *
     * @param usage usage in inconsistent state
     */
    public InconsistentUsageStateException(Usage usage) {
        super(String.format("Usage is in inconsistent state. UsageId=%s, Status=%s, RecordVersion=%s", usage.getId(),
            usage.getStatus(), usage.getVersion()));
    }

    /**
     * Constructor.
     *
     * @param udmUsage UDM usage in inconsistent state
     */
    public InconsistentUsageStateException(UdmUsage udmUsage) {
        super(String.format("UDM Usage is in inconsistent state. UDM UsageId=%s, Status=%s, RecordVersion=%s",
            udmUsage.getId(), udmUsage.getStatus(), udmUsage.getVersion()));
    }
}
