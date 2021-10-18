package com.copyright.rup.dist.foreign.service.api.acl;

import java.util.List;

/**
 * Represents interface of service for UDM Price (Access) Types.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 10/18/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmPriceTypeService {

    /**
     * @return list of all price types.
     */
    List<String> getAllPriceTypes();

    /**
     * @return list of all price access types.
     */
    List<String> getAllPriceAccessTypes();
}
