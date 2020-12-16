package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.ui.common.CommonBaseItemsFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Supplier;

/**
 * Implementation of SAL licensee filter widget.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 12/17/2020
 *
 * @author Aliaksandr Liakh
 */
class SalLicenseeFilterWidget extends CommonBaseItemsFilterWidget<SalLicensee> {

    /**
     * Constructor.
     *
     * @param supplier {@link SalLicensee}s supplier
     */
    SalLicenseeFilterWidget(Supplier<List<SalLicensee>> supplier) {
        super(ForeignUi.getMessage("label.licensees"), ForeignUi.getMessage("label.licensees"),
            ForeignUi.getMessage("prompt.licensee"), "licensees-filter-window", SalLicensee.class,
            licensee -> Lists.newArrayList(licensee.getName(), licensee.getAccountNumber().toString()),
            supplier);
    }

    @Override
    public String getBeanItemCaption(SalLicensee licensee) {
        return String.format("%s - %s", licensee.getAccountNumber(), licensee.getName());
    }
}
