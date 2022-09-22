package com.copyright.rup.dist.foreign.repository.impl.csv.fas;

import com.copyright.rup.dist.foreign.domain.UsageDto;

import org.apache.ibatis.session.ResultContext;

import java.io.OutputStream;
import java.util.Objects;
import java.util.Set;

/**
 * Writes usages into an {@link OutputStream} and collects their identifiers.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 3/21/18
 *
 * @author Uladzislau Shalamitski
 */
public class SendForResearchCsvReportHandler extends FasUsageCsvReportHandler {

    private final Set<String> usagesIds;

    /**
     * Constructor.
     *
     * @param outputStream           instance of {@link OutputStream}
     * @param usagesIdsToBePopulated a mutable {@link Set} where handled usage ids will be stored
     */
    public SendForResearchCsvReportHandler(OutputStream outputStream, Set<String> usagesIdsToBePopulated) {
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
