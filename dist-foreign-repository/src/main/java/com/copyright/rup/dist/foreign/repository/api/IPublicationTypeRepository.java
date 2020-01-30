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
     * Find publication type id by name ignoring case.
     *
     * @param pubTypeName name of publication type in any case
     * @return publication type id
     */
    String findIdByName(String pubTypeName);
}
