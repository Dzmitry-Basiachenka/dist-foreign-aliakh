package com.copyright.rup.dist.foreign.ui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.ui.status.api.IFasBatchStatusWidget;

/**
 * Implementation of {@link IFasBatchStatusWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
public class FasBatchStatusWidget extends CommonBatchStatusWidget implements IFasBatchStatusWidget {

    @Override
    protected void addColumns() {
        addColumn(UsageBatchStatus::getBatchName, "table.column.batch_name", "batchName", true);
        addColumn(UsageBatchStatus::getTotalCount, "table.column.total_count", "totalCount", true, 130);
        addColumn(UsageBatchStatus::getNewCount, "table.column.new", "newCount", true, 130);
        addColumn(UsageBatchStatus::getWorkNotFoundCount, "table.column.work_not_found", "workNotFoundCount", true,
            130);
        addColumn(UsageBatchStatus::getWorkFoundCount, "table.column.work_found", "workFoundCount", true, 130);
        addColumn(UsageBatchStatus::getWorkResearchCount, "table.column.sent_for_research", "workResearchCount",
            true, 135);
        addColumn(UsageBatchStatus::getRhNotFoundCount, "table.column.rh_not_found", "rhNotFoundCount", true, 130);
        addColumn(UsageBatchStatus::getRhFoundCount, "table.column.rh_found", "rhFoundCount", true, 130);
        addColumn(UsageBatchStatus::getSentForRaCount, "table.column.sent_for_ra", "sentForRaCount", true, 130);
        addColumn(UsageBatchStatus::getNtsWithdrawnCount, "table.column.nts_withdrawn", "ntsWithdrawnCount", true, 130);
        addColumn(UsageBatchStatus::getEligibleCount, "table.column.eligible", "eligibleCount", true, 130);
        addColumn(UsageBatchStatus::getStatus, "table.column.status", "status", true);
    }
}
