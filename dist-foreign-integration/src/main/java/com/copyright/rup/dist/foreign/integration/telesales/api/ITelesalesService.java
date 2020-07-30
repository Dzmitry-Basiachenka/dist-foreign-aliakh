package com.copyright.rup.dist.foreign.integration.telesales.api;

/**
 * Interface for telesales integration.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/27/2020
 *
 * @author Ihar Suvorau
 */
public interface ITelesalesService {

    /**
     * Gets licensee name by provided licensee account number.
     *
     * @param licenseeAccountNumber licensee account number
     * @return licensee name
     */
    String getLicenseeName(Long licenseeAccountNumber);
}
