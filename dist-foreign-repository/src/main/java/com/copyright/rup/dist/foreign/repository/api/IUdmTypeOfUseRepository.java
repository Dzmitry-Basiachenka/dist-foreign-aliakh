package com.copyright.rup.dist.foreign.repository.api;

import java.util.List;
import java.util.Map;

/**
 * Represents interface of repository for UDM type of uses.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/20/2021
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmTypeOfUseRepository {

    /**
     * Finds all UDM type of uses.
     *
     * @return list of UDM type of uses
     */
    List<String> findAllUdmTous();

    /**
     * Finds map UDM type of uses to RMS type of uses.
     *
     * @return map of UDM type of uses to RMS type of uses
     */
    Map<String, String> findUdmTouToRmsTouMap();
}
