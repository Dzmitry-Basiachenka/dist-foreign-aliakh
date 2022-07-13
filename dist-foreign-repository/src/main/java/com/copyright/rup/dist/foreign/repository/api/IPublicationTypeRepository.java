package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.PublicationType;

import java.util.List;

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
     * Checks whether publication type id exists with provided name ignoring case for specified product family.
     *
     * @param pubTypeName   name of publication type (case insensitive)
     * @param productFamily product family
     * @return {@code true} if publication type name is present, {@code false} otherwise
     */
    boolean isExistForProductFamily(String pubTypeName, String productFamily);

    /**
     * Finds list of all {@link PublicationType}s for specific product family.
     *
     * @param productFamily product family
     * @return list of {@link PublicationType}.
     */
    List<PublicationType> findByProductFamily(String productFamily);

    /**
     * Finds list of all {@link AclPublicationType}s.
     *
     * @return list of {@link AclPublicationType}s.
     */
    List<AclPublicationType> findAclHistoricalPublicationTypes();

    /**
     * Insert new ACL publication type.
     *
     * @param publicationType instance of {@link AclPublicationType}
     */
    void insertAclHistoricalPublicationType(AclPublicationType publicationType);
}
