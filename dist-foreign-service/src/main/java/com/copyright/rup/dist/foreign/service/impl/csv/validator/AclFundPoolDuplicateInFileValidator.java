package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * The validator to check whether {@link com.copyright.rup.dist.foreign.domain.AclFundPoolDetail} with the same
 * Wr Wrk Inst and Type Of Use doesn't exist in the file.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/28/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolDuplicateInFileValidator implements DistCsvProcessor.IValidator<AclFundPoolDetail> {

    private final Set<Pair<Integer, String>> existingRecords = new HashSet<>();

    @Override
    public boolean isValid(AclFundPoolDetail item) {
        return existingRecords.add(Pair.of(item.getDetailLicenseeClass().getId(), item.getTypeOfUse()));
    }

    @Override
    public String getErrorMessage() {
        return "Fund Pool with Detail Licensee Class Id and Type of Use already present in file";
    }
}
