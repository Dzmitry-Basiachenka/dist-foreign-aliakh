package com.copyright.rup.dist.foreign.service.api;

/**
 * Represents interface of service for publication type business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
public interface IPublicationTypeService {

    /**
     * Checks if publication type with publication type name is exist in database.
     *
     * @param pubTypeName name of publication type
     * @return {@code true} if publication type name is present, {@code false} otherwise
     */
    boolean isPublicationTypeExist(String pubTypeName);
}
