package com.copyright.rup.dist.foreign.repository.api;

import java.io.Serializable;
import java.util.List;

/**
 * Represents interface of repository for UDM Price (Access) Types.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 10/13/2021
 *
 * @author Ihar Suvorau
 */
public interface IUdmPriceTypeRepository extends Serializable {

    /**
     * @return list of all price types.
     */
    List<String> findAllPriceTypes();

    /**
     * @return list of all price access types.
     */
    List<String> findAllPriceAccessTypes();
}
