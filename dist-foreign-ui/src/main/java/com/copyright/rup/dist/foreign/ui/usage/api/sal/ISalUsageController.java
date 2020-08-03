package com.copyright.rup.dist.foreign.ui.usage.api.sal;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.impl.csv.SalUsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;

import java.util.List;

/**
 * Interface for SAL usages controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Ihar Suvorau
 */
public interface ISalUsageController extends ICommonUsageController {

    /**
     * Gets licensee name by provided account number.
     *
     * @param licenseeAccountNumber licensee account number
     * @return licensee name
     */
    String getLicenseeName(Long licenseeAccountNumber);

    /**
     * Inserts item bank and its usages.
     *
     * @param itemBank {@link UsageBatch} instance
     * @param usages   list of {@link Usage}s
     * @return count of inserted usages
     */
    int loadItemBank(UsageBatch itemBank, List<Usage> usages);

    /**
     * @return instance of {@link SalUsageCsvProcessor}
     */
    SalUsageCsvProcessor getSalUsageCsvProcessor();

    /**
     * Return instance of {@link IStreamSource} for errors result.
     *
     * @param fileName         name of processed file
     * @param processingResult information about errors
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult);
}
