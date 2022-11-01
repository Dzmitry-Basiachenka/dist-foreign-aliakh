package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.filter.AclScenarioFilter;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.CommonAclAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

/**
 * Widget for applied ACL scenario filters.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Mikita Maistrenka
 */
public class AclScenariosAppliedFilterWidget extends CommonAclAppliedFilterPanel {

    /**
     * Constructor.
     */
    public AclScenariosAppliedFilterWidget() {
        super();
        VaadinUtils.addComponentStyle(this, "acl-scenarios-applied-filter-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link AclScenarioFilter}
     */
    public void refreshFilterPanel(AclScenarioFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(sortIntegerValuesByDesc(filter.getPeriods()), "label.periods",
                String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(sortStringValuesByNaturalOrder(filter.getLicenseTypes()),
                "label.license_types", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(aclScenarioFilter -> BooleanUtils.toYNString(filter.getEditable()),
                filter, "label.editable"), layout);
            addLabel(createLabelWithSingleValue(AclScenarioFilter::getStatus, filter, "label.status"), layout);
        }
        setContent(layout);
    }
}
