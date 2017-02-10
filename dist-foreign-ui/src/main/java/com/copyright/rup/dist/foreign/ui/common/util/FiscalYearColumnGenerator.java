package com.copyright.rup.dist.foreign.ui.common.util;

import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.vaadin.ui.VaadinUtils;

import com.vaadin.ui.Table;

/**
 * Column generator for Fiscal Year.
 * Returns {@link org.apache.commons.lang3.StringUtils#EMPTY} if value is {@code null}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/08/2017
 *
 * @author Mikalai Bezmen
 */
public class FiscalYearColumnGenerator implements Table.ColumnGenerator {

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        return UsageBatchUtils.getFiscalYear(
            VaadinUtils.getContainerPropertyValue(source, itemId, columnId, Integer.class));
    }
}
