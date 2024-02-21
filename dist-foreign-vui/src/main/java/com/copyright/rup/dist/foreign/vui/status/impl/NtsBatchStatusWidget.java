package com.copyright.rup.dist.foreign.vui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.vui.status.api.INtsBatchStatusWidget;

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
        addColumn(UsageBatchStatus::getBatchName, "table.column.batch_name", "batchName");
        addColumn(UsageBatchStatus::getTotalCount, "table.column.total_count", "totalCount", "140px");
        addColumn(UsageBatchStatus::getWorkFoundCount, "table.column.work_found", "workFoundCount", "140px");
        addColumn(UsageBatchStatus::getRhFoundCount, "table.column.rh_found", "rhFoundCount", "110px");
        addColumn(UsageBatchStatus::getNonStmRhCount, "table.column.non_stm_rh", "nonStmRhCount", "130px");
        addColumn(UsageBatchStatus::getUsTaxCountryCount, "table.column.us_tax_country", "usTaxCountryCount", "160px");
        addColumn(UsageBatchStatus::getUnclassifiedCount, "table.column.unclassified", "unclassifiedCount", "140px");
        addColumn(UsageBatchStatus::getEligibleCount, "table.column.eligible", "eligibleCount", "100px");
        addColumn(UsageBatchStatus::getExcludedCount, "table.column.excluded", "excludedCount", "120px");
        addColumn(UsageBatchStatus::getStatus, "table.column.status", "status");
    }
}
