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
     * Checks whether publication type id exist with provided name ignoring case in database.
     *
     * @param pubTypeName name of publication type
     * @return {@code true} if publication type name is present, {@code false} otherwise
     */
    boolean publicationTypeExist(String pubTypeName);
}