package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * The validator to check whether {@link AclGrantDetailDto} with the same Wr Wrk Inst and Type Of Use doesn't exist
 * in the file.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/07/22
 *
 * @author Ihar Suvorau
 */
public class AclGrantDetailDuplicateInFileValidator implements DistCsvProcessor.IValidator<AclGrantDetailDto> {

    private final Set<Pair<Long, String>> existingRecords = new HashSet<>();

    @Override
    public boolean isValid(AclGrantDetailDto item) {
        boolean valid = false;
        Pair<Long, String> duplicatePair = Pair.of(item.getWrWrkInst(), item.getTypeOfUse());
        if (!existingRecords.contains(duplicatePair)) {
            valid = true;
            existingRecords.add(duplicatePair);
        }
        return valid;
    }

    @Override
    public String getErrorMessage() {
        return "Grant with Wr Wrk Inst and Type of Use already present in file";
    }
}
