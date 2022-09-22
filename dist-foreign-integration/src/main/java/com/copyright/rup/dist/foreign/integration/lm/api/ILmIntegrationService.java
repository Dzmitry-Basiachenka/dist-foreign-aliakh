package com.copyright.rup.dist.foreign.integration.lm.api;

import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;

import java.util.List;

/**
 * Interface which represents service logic for LM system.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/09/18
 *
 * @author Ihar Suvorau
 */
public interface ILmIntegrationService {

    /**
     * Sends list of {@link ExternalUsage}s to LM.
     *
     * @param externalUsages list of {@link ExternalUsage}s
     * @throws com.copyright.rup.common.exception.RupRuntimeException in case when message is failed to send
     */
    void sendToLm(List<ExternalUsage> externalUsages);
}
