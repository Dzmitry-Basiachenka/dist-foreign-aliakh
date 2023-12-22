package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.ui.common.CommonAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

/**
 * Widget for applied udm proxy value filters.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/10/2023
 *
 * @author Stepan Karakhanov
 */
public class UdmProxyValueAppliedFilterWidget extends CommonAppliedFilterPanel {

    private static final long serialVersionUID = -2381655989602580487L;

    /**
     * Constructor.
     */
    public UdmProxyValueAppliedFilterWidget() {
        super();
        VaadinUtils.addComponentStyle(this, "udm-proxy-values-filter-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link UdmProxyValueFilter}
     */
    public void refreshFilterPanel(UdmProxyValueFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(sortIntegerValuesByDesc(filter.getPeriods()), "label.periods",
                String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(filter.getPubTypeNames(), "label.pub_type_codes", String::valueOf),
                layout);
        }
        setContent(layout);
    }
}
