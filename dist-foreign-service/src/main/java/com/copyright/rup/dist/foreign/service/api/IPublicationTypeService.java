package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.PublicationType;

import java.util.List;

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
     * Checks whether publication type id exists with provided name ignoring case for specified product family.
     *
     * @param pubTypeName   name of publication type
     * @param productFamily product family
     * @return {@code true} if publication type name is present, {@code false} otherwise
     */
    boolean publicationTypeExist(String pubTypeName, String productFamily);

    /**
     * Gets list of all {@link PublicationType}s for specific product family.
     *
     * @param productFamily product family
     * @return list of {@link PublicationType}
     */
    List<PublicationType> getPublicationTypes(String productFamily);
}
