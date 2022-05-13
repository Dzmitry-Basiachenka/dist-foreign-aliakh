package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool.validator;

import com.copyright.rup.dist.common.integration.camel.IValidator;
import com.copyright.rup.dist.common.integration.camel.ValidationException;
import com.copyright.rup.dist.foreign.domain.LdmtDetail;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IValidator} to perform validation for {@link LdmtDetail}s.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/12/2022
 *
 * @author Aliaksandr Liakh
 */
// TODO verify duplicated (Detail Licensee Class Id, Type of Use) pairs
@Component("df.service.ldmtDetailsValidator")
public class LdmtDetailsValidator implements IValidator<List<LdmtDetail>> {

    private static final Set<String> LICENSE_TYPES = ImmutableSet.of("ACL", "JACDCL", "MACL", "VGW");
    private static final Set<String> TYPE_OF_USES = ImmutableSet.of("DIGITAL", "PRINT");

    @Autowired
    private ILicenseeClassService licenseeClassService;

    @Override
    public void validate(List<LdmtDetail> ldmtDetails) {
        Map<Integer, List<String>> indexToErrors = new LinkedHashMap<>();
        for (int i = 0; i < ldmtDetails.size(); i++) {
            List<String> errors = validate(ldmtDetails.get(i));
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

    private List<String> validate(LdmtDetail ldmtDetail) {
        List<String> errors = new ArrayList<>();
        if (!licenseeClassService.aclDetailLicenseeClassExists(ldmtDetail.getDetailLicenseeClassId())) {
            errors.add("Detail Licensee Class is not valid: " + ldmtDetail.getDetailLicenseeClassId());
        }
        if (!LICENSE_TYPES.contains(ldmtDetail.getLicenseType())) {
            errors.add("License Type is not valid: " + ldmtDetail.getLicenseType());
        }
        if (!TYPE_OF_USES.contains(ldmtDetail.getTypeOfUse())) {
            errors.add("Type of Use is not valid: " + ldmtDetail.getTypeOfUse());
        }
        if (isAmountInvalid(ldmtDetail.getGrossAmount())) {
            errors.add("The integer part of Gross Amount should be less or equal to 10 digits: " +
                ldmtDetail.getGrossAmount());
        }
        if (isAmountInvalid(ldmtDetail.getNetAmount())) {
            errors.add("The integer part of Net Amount should be less or equal to 10 digits: " +
                ldmtDetail.getNetAmount());
        }
        if (ldmtDetail.getGrossAmount().compareTo(ldmtDetail.getNetAmount()) < 0) {
            errors.add("Net Amount should be less than or equal to Gross Amount: " +
                ldmtDetail.getNetAmount() + ", " + ldmtDetail.getGrossAmount());
        }
        return errors;
    }

    private boolean isAmountInvalid(BigDecimal amount) {
        return getDigitsCount(amount) > 10;
    }

    private int getDigitsCount(BigDecimal value) {
        return value.signum() == 0 ? 1 : value.precision() - value.scale();
    }
}
