package com.copyright.rup.dist.foreign.integration.lm.api;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.LiabilityDetail;

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
     * Sends list of {@link LiabilityDetail}s to LM.
     *
     * @param liabilityDetails list of {@link LiabilityDetail}s
     * @throws RupRuntimeException in case when message is failed to send
     */
    void sendToLm(List<LiabilityDetail> liabilityDetails) throws RupRuntimeException;
}
