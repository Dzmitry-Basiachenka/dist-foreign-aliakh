package com.copyright.rup.dist.foreign.service.api.acl;

import java.util.List;
import java.util.Map;

/**
 * Represents interface of service for UDM type of use logic.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/2021
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmTypeOfUseService {

    /**
     * Gets all UDM type of uses.
     *
     * @return list of UDM type of uses
     */
    List<String> getAllUdmTous();

    /**
     * Gets map UDM type of uses to RMS type of uses.
     *
     * @return map of UDM type of uses to RMS type of uses
     */
    Map<String, String> getUdmTouToRmsTouMap();
}
