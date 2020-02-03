package com.copyright.rup.dist.foreign.repository.api;

/**
 * Interface for Publication type repository.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
public interface IPublicationTypeRepository {

    /**
     * Checks whether publication type id exist with provided name ignoring case in database.
     *
     * @param pubTypeName name of publication type (case insensitive)
     * @return {@code true} if publication type name is present, {@code false} otherwise
     */
    boolean isPublicationTypeExist(String pubTypeName);
}
