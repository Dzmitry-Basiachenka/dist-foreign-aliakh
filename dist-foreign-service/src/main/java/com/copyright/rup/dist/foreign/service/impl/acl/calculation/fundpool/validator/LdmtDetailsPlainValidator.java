package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool.validator;

import com.copyright.rup.dist.common.integration.camel.IValidator;
import com.copyright.rup.dist.common.integration.camel.ValidationException;
import com.copyright.rup.dist.foreign.domain.LdmtDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IValidator} to perform plain validation for {@link LdmtDetail}s.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/12/2022
 *
 * @author Aliaksandr Liakh
 */
@Component("df.service.ldmtDetailsPlainValidator")
public class LdmtDetailsPlainValidator implements IValidator<List<LdmtDetail>> {

    @Override
    public void validate(List<LdmtDetail> ldmtDetails) {
        Map<Integer, List<String>> indexToErrors = new LinkedHashMap<>();
        for (int i = 0; i < ldmtDetails.size(); i++) {
            LdmtDetail ldmtDetail = ldmtDetails.get(i);
            List<String> errors = new ArrayList<>();
            if (Objects.isNull(ldmtDetail.getDetailLicenseeClassId())) {
                errors.add("Licensee Class Id should not be null or blank");
            }
            if (StringUtils.isBlank(ldmtDetail.getLicenseType())) {
                errors.add("License Type should not be null or blank");
            }
            if (StringUtils.isBlank(ldmtDetail.getTypeOfUse())) {
                errors.add("Type Of Use should not be null or blank");
            }
            if (Objects.isNull(ldmtDetail.getGrossAmount())) {
                errors.add("Gross Amount should not be null");
            }
            if (Objects.isNull(ldmtDetail.getNetAmount())) {
                errors.add("Net Amount should not be null");
            }
            if (!errors.isEmpty()) {
                indexToErrors.put(i, errors);
            }
        }
        if (!indexToErrors.isEmpty()) {
            throw new ValidationException(indexToErrors
                .entrySet()
                .stream()
                .map(entry -> String.format("LDMT detail #%d is not valid: %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n")));
        }
    }
}
