package com.copyright.rup.dist.foreign.integration.oracle.api;

import com.copyright.rup.dist.foreign.domain.RhTaxInformation;
import com.copyright.rup.dist.foreign.integration.oracle.api.domain.OracleRhTaxInformationRequest;

import java.util.Collection;
import java.util.Map;

/**
 * Interface for service that gets RH tax information from Oracle.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/17/20
 *
 * @author Stanislau Rudak
 */
public interface IOracleRhTaxInformationService {

    /**
     * Gets rightsholder tax information.
     *
     * @param oracleRhTaxInformationRequests collection of {@link OracleRhTaxInformationRequest}s
     * @return map of tax information to requested TBO account number
     */
    Map<Long, RhTaxInformation> getRhTaxInformation(
        Collection<OracleRhTaxInformationRequest> oracleRhTaxInformationRequests);
}
