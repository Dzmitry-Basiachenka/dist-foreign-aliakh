package com.copyright.rup.dist.foreign.ui.common.util;

import com.copyright.rup.vaadin.ui.VaadinUtils;

import com.vaadin.ui.Table;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Column generator for Integer values.
 * Uses {@link java.util.Objects#toString(Object, String)} method to generate value.
 * Returns {@link org.apache.commons.lang3.StringUtils#EMPTY} if value is {@code null}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/08/2017
 *
 * @author Mikalai Bezmen
 */
// TODO {mbezmen} move to rup-vaadin and combine with LongColumnGenerator.
public class IntegerColumnGenerator implements Table.ColumnGenerator {

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        return Objects.toString(VaadinUtils.getContainerPropertyValue(source, itemId, columnId, Integer.class),
            StringUtils.EMPTY);
    }
}
