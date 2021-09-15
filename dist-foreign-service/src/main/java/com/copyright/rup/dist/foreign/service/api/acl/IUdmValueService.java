package com.copyright.rup.dist.foreign.service.api.acl;

import java.util.Map;

/**
 * Represents interface of service for UDM value logic.
 * <p>
 * Copyright (C) 20121 copyright.com
 * <p>
 * Date: 09/15/2021
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmValueService {

    /**
     * Gets map of currency codes to currency names.
     *
     * @return map of currency codes to currency names
     */
    Map<String, String> getCurrencyCodesToCurrencyNamesMap();
}
