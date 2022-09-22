package com.copyright.rup.dist.foreign.repository.impl.csv.aacl;

import com.copyright.rup.dist.foreign.domain.UsageDto;

import org.apache.ibatis.session.ResultContext;

import java.io.OutputStream;
import java.util.Objects;
import java.util.Set;

/**
 * Writes usages for classification into an {@link OutputStream} and collects their identifiers.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/17/20
 *
 * @author Stanislau Rudak
 */
public class SendForClassificationCsvReportHandler extends AaclUsageCsvReportHandler {

    private final Set<String> usagesIds;

    /**
     * Constructor.
     *
     * @param outputStream           instance of {@link OutputStream}
     * @param usagesIdsToBePopulated a mutable {@link Set} where handled usage ids will be stored
     */
    public SendForClassificationCsvReportHandler(OutputStream outputStream, Set<String> usagesIdsToBePopulated) {
        super(outputStream);
        usagesIds = Objects.requireNonNull(usagesIdsToBePopulated);
    }

    @Override
    public void handleResult(ResultContext<? extends UsageDto> context) {
        super.handleResult(context);
        usagesIds.add(context.getResultObject().getId());
    }

    public Set<String> getUsagesIds() {
        return usagesIds;
    }
}
