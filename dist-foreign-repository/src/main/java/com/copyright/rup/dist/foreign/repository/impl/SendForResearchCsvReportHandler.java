package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.UsageDto;

import org.apache.ibatis.session.ResultContext;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
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
public class SendForResearchCsvReportHandler extends UsageCsvReportHandler<UsageDto> {

    private final Set<String> usagesIds;

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     * @throws IOException if header cannot be written
     */
    SendForResearchCsvReportHandler(OutputStream outputStream) throws IOException {
        super(outputStream);
        usagesIds = new HashSet<>();
    }

    @Override
    public void handleResult(ResultContext<? extends UsageDto> context) throws RupRuntimeException {
        super.handleResult(context);
        usagesIds.add(context.getResultObject().getId());
    }

    public Set<String> getUsagesIds() {
        return usagesIds;
    }
}
