package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool.validator;

import com.copyright.rup.dist.common.integration.camel.IValidator;
import com.copyright.rup.dist.common.integration.camel.ValidationException;
import com.copyright.rup.dist.foreign.domain.LdmtDetail;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;

import com.google.common.collect.ImmutableSet;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IValidator} to perform validation for {@link LdmtDetail}s.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/12/2022
 *
 * @author Aliaksandr Liakh
 */
@Component("df.service.ldmtDetailsValidator")
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class LdmtDetailsValidator implements IValidator<List<LdmtDetail>> {

    private static final Set<String> LICENSE_TYPES = ImmutableSet.of("ACL", "JACDCL", "MACL", "VGW");
    private static final Set<String> TYPE_OF_USES = ImmutableSet.of("DIGITAL", "PRINT");

    @Autowired
    private ILicenseeClassService licenseeClassService;

    @Override
    public void validate(List<LdmtDetail> ldmtDetails) {
        Map<Integer, List<String>> indexToErrors = new LinkedHashMap<>();
        IntStream.range(0, ldmtDetails.size()).forEach(i -> {
            List<String> errors = validate(ldmtDetails.get(i));
            if (!errors.isEmpty()) {
                indexToErrors.put(i, errors);
            }
        });
        DuplicatesProcessor processor = new DuplicatesProcessor(ldmtDetails);
        if (!indexToErrors.isEmpty() || processor.hasDuplicates()) {
            throw new ValidationException(Stream.concat(
                indexToErrors
                    .entrySet()
                    .stream()
                    .map(entry -> String.format("LDMT detail #%d is not valid: %s", entry.getKey(), entry.getValue())),
                processor.getErrorStream()
            ).collect(Collectors.joining("; ")));
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
        if (ldmtDetail.getGrossAmount().compareTo(ldmtDetail.getNetAmount()) < 0) {
            errors.add("Net Amount should be less than or equal to Gross Amount: " +
                ldmtDetail.getNetAmount() + ", " + ldmtDetail.getGrossAmount());
        }
        return errors;
    }

    /**
     * Processor to find duplicate Detail Licensee Class Id, Type of Use pairs in the list of {@link LdmtDetail}s.
     */
    private static class DuplicatesProcessor {

        private final List<LdmtDetail> ldmtDetails;
        private final Map<Pair<Integer, String>, Integer> pairToCounts = new LinkedHashMap<>();

        DuplicatesProcessor(List<LdmtDetail> ldmtDetails) {
            this.ldmtDetails = ldmtDetails;
            ldmtDetails.stream().map(
                    ldmtDetail -> new ImmutablePair<>(ldmtDetail.getDetailLicenseeClassId(), ldmtDetail.getTypeOfUse()))
                .forEach(pair -> {
                    pairToCounts.putIfAbsent(pair, 0);
                    pairToCounts.put(pair, pairToCounts.get(pair) + 1);
                });
        }

        private boolean hasDuplicates() {
            return ldmtDetails.size() != pairToCounts.size();
        }

        private String getErrorMessage() {
            return "LDMT details contain duplicate Detail Licensee Class Id, Type of Use pairs: " +
                pairToCounts.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(entry -> String.format("(%s, %s)", entry.getKey().getKey(), entry.getKey().getValue()))
                    .collect(Collectors.joining(", "));
        }

        private Stream<String> getErrorStream() {
            return hasDuplicates() ? Stream.of(getErrorMessage()) : Stream.of();
        }
    }
}
