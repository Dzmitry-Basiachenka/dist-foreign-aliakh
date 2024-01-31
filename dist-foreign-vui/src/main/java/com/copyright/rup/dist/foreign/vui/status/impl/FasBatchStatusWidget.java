package com.copyright.rup.dist.foreign.vui.status.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.vui.status.api.IFasBatchStatusWidget;

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

    private static final long serialVersionUID = 6562944003794416364L;

    @Override
    protected void addColumns() {
        addColumn(UsageBatchStatus::getBatchName, "table.column.batch_name", "batchName");
        addColumn(UsageBatchStatus::getTotalCount, "table.column.total_count", "totalCount", "140px");
        addColumn(UsageBatchStatus::getNewCount, "table.column.new", "newCount", "70px");
        addColumn(UsageBatchStatus::getWorkNotFoundCount, "table.column.work_not_found", "workNotFoundCount",
            "170px");
        addColumn(UsageBatchStatus::getWorkFoundCount, "table.column.work_found", "workFoundCount", "140px");
        addColumn(UsageBatchStatus::getWorkResearchCount, "table.column.sent_for_research", "workResearchCount",
            "190px");
        addColumn(UsageBatchStatus::getRhNotFoundCount, "table.column.rh_not_found", "rhNotFoundCount", "150px");
        addColumn(UsageBatchStatus::getRhFoundCount, "table.column.rh_found", "rhFoundCount", "120px");
        addColumn(UsageBatchStatus::getSentForRaCount, "table.column.sent_for_ra", "sentForRaCount", "130px");
        addColumn(UsageBatchStatus::getNtsWithdrawnCount, "table.column.nts_withdrawn", "ntsWithdrawnCount", "160px");
        addColumn(UsageBatchStatus::getEligibleCount, "table.column.eligible", "eligibleCount", "100px");
        addColumn(UsageBatchStatus::getStatus, "table.column.status", "status");
    }
}
