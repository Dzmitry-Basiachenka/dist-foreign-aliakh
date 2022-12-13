package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator for ACLCI license type.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/14/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclciLicenseTypeValidator implements DistCsvProcessor.IValidator<String> {

    private static final Set<String> VALUES = Arrays.stream(AclciLicenseTypeEnum.values())
        .map(Enum::name)
        .collect(Collectors.toSet());

    @Override
    public boolean isValid(String value) {
        return StringUtils.isNotEmpty(value) && VALUES.contains(value.toUpperCase(Locale.ROOT));
    }

    @Override
    public String getErrorMessage() {
        return "Field value should be any of: " + Arrays.toString(AclciLicenseTypeEnum.values());
    }
}
