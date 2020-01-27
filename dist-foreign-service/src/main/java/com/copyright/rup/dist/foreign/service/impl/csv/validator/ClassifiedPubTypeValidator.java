package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;

import java.util.Objects;

/**
 * The validator for Classified AACL usages to check if publication type
 * exists in df_publication_type table.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
public class ClassifiedPubTypeValidator implements DistCsvProcessor.IValidator<AaclClassifiedUsage> {

    private final IPublicationTypeService pubTypeService;

    /**
     * Constructor.
     *
     * @param pubTypeService instance of {@link IPublicationTypeService}
     */
    public ClassifiedPubTypeValidator(IPublicationTypeService pubTypeService) {
        this.pubTypeService = pubTypeService;
    }

    @Override
    public boolean isValid(AaclClassifiedUsage aaclClassifiedUsage) {
        return pubTypeService.isPublicationTypeExist(Objects.requireNonNull(aaclClassifiedUsage).getPublicationType());
    }

    @Override
    public String getErrorMessage() {
        return "Loaded Publication Type is missing in the system";
    }
}
