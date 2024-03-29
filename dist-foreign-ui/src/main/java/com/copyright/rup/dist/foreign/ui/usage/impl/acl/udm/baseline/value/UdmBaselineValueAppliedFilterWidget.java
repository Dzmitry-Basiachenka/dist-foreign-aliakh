package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.ui.common.CommonAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

/**
 * Widget for applied udm baseline values filters.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/21/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueAppliedFilterWidget extends CommonAppliedFilterPanel {

    private static final long serialVersionUID = -8826959282028367916L;

    /**
     * Constructor.
     */
    public UdmBaselineValueAppliedFilterWidget() {
        super();
        VaadinUtils.addComponentStyle(this, "udm-baseline-values-filter-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link UdmBaselineValueFilter}
     */
    public void refreshFilterPanel(UdmBaselineValueFilter filter) {
        VerticalLayout layout = initFilterPanel();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(sortIntegerValuesByDesc(filter.getPeriods()), "label.periods",
                String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(sortPublicationTypes(filter.getPubTypes()), "label.pub_types",
                PublicationType::getNameAndDescription), layout);
            addLabel(createLabelWithOperator(filter.getWrWrkInstExpression(), "label.wr_wrk_inst_from",
                "label.wr_wrk_inst_to"), layout);
            addLabel(createLabelWithOperator(filter.getSystemTitleExpression(), "label.system_title",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithSingleValue(udmValueFilter -> convertFilterOperatorToString(
                udmValueFilter.getPriceFlagExpression().getOperator()), filter, "label.price_flag"), layout);
            addLabel(createLabelWithSingleValue(udmValueFilter -> convertFilterOperatorToString(
                udmValueFilter.getContentFlagExpression().getOperator()), filter, "label.content_flag"), layout);
            addLabel(createLabelWithSingleValue(udmValueFilter -> convertFilterOperatorToString(
                udmValueFilter.getContentUnitPriceFlagExpression().getOperator()), filter,
                "label.content_unit_price_flag"), layout);
            addLabel(createLabelWithOperator(filter.getPriceExpression(), "label.price_from", "label.price_to"),
                layout);
            addLabel(createLabelWithOperator(filter.getContentExpression(), "label.content_from", "label.content_to"),
                layout);
            addLabel(createLabelWithOperator(filter.getContentUnitPriceExpression(),
                "label.content_unit_price_from", "label.content_unit_price_to"), layout);
            addLabel(createLabelWithOperator(filter.getCommentExpression(), "label.comment", StringUtils.EMPTY),
                layout);
        }
        setContent(layout);
    }
}
