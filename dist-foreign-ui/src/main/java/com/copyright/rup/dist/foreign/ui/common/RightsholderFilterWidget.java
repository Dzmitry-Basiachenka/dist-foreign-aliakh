package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for rightsholders.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/15/17
 *
 * @author Mikalai Bezmen
 */
public class RightsholderFilterWidget extends CommonBaseItemsFilterWidget<Rightsholder> {

    private final String rightsholderNotFoundString;

    /**
     * Constructor.
     *
     * @param caption                    widget caption
     * @param searchPrompt               search field prompt
     * @param rightsholderNotFoundString a string to be shown as righstholder name if the name is blank
     * @param supplier                   {@link Rightsholder}s supplier
     */
    public RightsholderFilterWidget(String caption, String searchPrompt, String rightsholderNotFoundString,
                                    Supplier<List<Rightsholder>> supplier) {
        super(caption, ForeignUi.getMessage("window.filter_format", caption), searchPrompt,
            "rightsholders-filter-window", Rightsholder.class,
            rightsholder -> Lists.newArrayList(rightsholder.getName(), rightsholder.getAccountNumber().toString()),
            supplier);
        this.rightsholderNotFoundString = rightsholderNotFoundString;
    }

    @Override
    public String getBeanItemCaption(Rightsholder rightsholder) {
        return String.format("%s - %s", rightsholder.getAccountNumber(),
            StringUtils.defaultIfBlank(rightsholder.getName(), rightsholderNotFoundString));
    }
}
