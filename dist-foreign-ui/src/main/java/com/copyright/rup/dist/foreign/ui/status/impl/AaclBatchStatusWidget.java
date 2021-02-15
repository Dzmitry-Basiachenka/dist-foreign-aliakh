package com.copyright.rup.dist.foreign.ui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.status.api.IAaclBatchStatusWidget;

/**
 * Implementation of {@link IAaclBatchStatusWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
public class AaclBatchStatusWidget extends CommonBatchStatusWidget implements IAaclBatchStatusWidget {

    @Override
    protected void addColumns() {
        addColumn(UsageBatchStatus::getBatchName, "table.column.batch_name", "batchName", true);
        addColumn(UsageBatchStatus::getTotalCount, "table.column.total_count", "totalCount", true, 130);
        addColumn(UsageBatchStatus::getNewCount, "table.column.new", "newCount", true, 130);
        addColumn(UsageBatchStatus::getWorkNotFoundCount, "table.column.work_not_found", "workNotFoundCount", true, 130);
        addColumn(UsageBatchStatus::getWorkFoundCount, "table.column.work_found", "workFoundCount", true, 130);
        addColumn(UsageBatchStatus::getRhFoundCount, "table.column.rh_found", "rhFoundCount", true, 130);
        addColumn(UsageBatchStatus::getEligibleCount, "table.column.eligible", "eligibleCount", true, 130);
        addColumn(UsageBatchStatus::getExcludedCount, "table.column.excluded", "excludedCount", true, 130);
        addColumn(UsageBatchStatus::getStatus, "table.column.status", "status", true);
    }
}
