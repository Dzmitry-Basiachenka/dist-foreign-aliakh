package com.copyright.rup.dist.foreign.ui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.status.api.INtsBatchStatusWidget;

/**
 * Implementation of {@link INtsBatchStatusWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
public class NtsBatchStatusWidget extends CommonBatchStatusWidget implements INtsBatchStatusWidget {

    private static final long serialVersionUID = 5061512660191028638L;

    @Override
    protected void addColumns() {
        addColumn(UsageBatchStatus::getBatchName, "table.column.batch_name", "batchName", true);
        addColumn(UsageBatchStatus::getTotalCount, "table.column.total_count", "totalCount", true, 130);
        addColumn(UsageBatchStatus::getWorkFoundCount, "table.column.work_found", "workFoundCount", true, 130);
        addColumn(UsageBatchStatus::getRhFoundCount, "table.column.rh_found", "rhFoundCount", true, 130);
        addColumn(UsageBatchStatus::getNonStmRhCount, "table.column.non_stm_rh", "nonStmRhCount", true, 130);
        addColumn(UsageBatchStatus::getUsTaxCountryCount, "table.column.us_tax_country", "usTaxCountryCount", true,
            130);
        addColumn(UsageBatchStatus::getUnclassifiedCount, "table.column.unclassified", "unclassifiedCount", true, 130);
        addColumn(UsageBatchStatus::getEligibleCount, "table.column.eligible", "eligibleCount", true, 130);
        addColumn(UsageBatchStatus::getExcludedCount, "table.column.excluded", "excludedCount", true, 130);
        addColumn(UsageBatchStatus::getStatus, "table.column.status", "status", true);
    }
}
