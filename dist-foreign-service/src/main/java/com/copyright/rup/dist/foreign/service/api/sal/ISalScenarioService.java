package com.copyright.rup.dist.foreign.service.api.sal;

/**
 * Represents service interface for SAL specific scenarios business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/24/2020
 *
 * @author Aliaksandr Liakh
 */
public interface ISalScenarioService {

    /**
     * Gets {@link Scenario} name based on SAL fund pool id.
     *
     * @param fundPoolId fund pool id
     * @return {@link Scenario} name or {@code null} if none found
     */
    String getScenarioNameByFundPoolId(String fundPoolId);
}
