package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmAppliedFilterPanel;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Function;

/**
 * Widget for applied UDM value filters.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/17/2021
 *
 * @author Anton Azarenka
 */
public class UdmValueAppliedFilterWidget extends CommonUdmAppliedFilterPanel {

    /**
     * Constructor.
     */
    public UdmValueAppliedFilterWidget() {
        super();
        VaadinUtils.addComponentStyle(this, "udm-values-filter-panel-widget");
    }

    /**
     * Refreshes filter panel.
     *
     * @param filter instance of {@link UdmValueFilter}
     */
    public void refreshFilterPanel(UdmValueFilter filter) {
        VerticalLayout layout = initLayout();
        if (!filter.isEmpty()) {
            addLabel(createLabelWithMultipleValues(filter.getPeriods(), "label.periods", String::valueOf), layout);
            addLabel(createLabelWithSingleValue(UdmValueFilter::getStatus, filter, "label.status"), layout);
            addLabel(createLabelWithSingleValue(getPubTypeFunction(UdmValueFilter::getPubType, filter), filter,
                "label.pub_type"), layout);
            addLabel(createLabelWithMultipleValues(filter.getAssignees(), "label.assignees", String::valueOf), layout);
            addLabel(createLabelWithMultipleValues(filter.getLastValuePeriods(), "label.last_value_periods",
                String::valueOf), layout);
            addLabel(createLabelWithOperator(filter.getWrWrkInstExpression(), "label.wr_wrk_inst_from",
                "label.wr_wrk_inst_to"), layout);
            addLabel(createLabelWithOperator(filter.getSystemTitleExpression(), "label.system_title",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getSystemStandardNumberExpression(), "label.system_standard_number",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getRhAccountNumberExpression(), "label.rh_account_number_from",
                "label.rh_account_number_to"), layout);
            addLabel(createLabelWithOperator(filter.getRhNameExpression(), "label.rh_name", StringUtils.EMPTY), layout);
            addLabel(createLabelWithSingleValue(
                Objects.nonNull(filter.getCurrency())
                    ? valueFilter -> valueFilter.getCurrency().getCodeAndDescription()
                    : UdmValueFilter::getCurrency,
                filter, "label.currency"), layout);
            addLabel(createLabelWithOperator(filter.getPriceExpression(), "label.price", StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getPriceInUsdExpression(), "label.price_in_usd", StringUtils.EMPTY),
                layout);
            addLabel(createLabelWithSingleValue(udmValueFilter ->
                BooleanUtils.toYNString(udmValueFilter.getPriceFlag()), filter, "label.price_flag"), layout);
            addLabel(createLabelWithOperator(filter.getPriceCommentExpression(), "label.price_comment",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithSingleValue(udmValueFilter ->
                BooleanUtils.toYNString(udmValueFilter.getLastPriceFlag()), filter, "label.last_price_flag"), layout);
            addLabel(createLabelWithOperator(filter.getLastPriceCommentExpression(), "label.last_price_comment",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithOperator(filter.getContentExpression(), "label.content", StringUtils.EMPTY),
                layout);
            addLabel(createLabelWithSingleValue(udmValueFilter ->
                BooleanUtils.toYNString(udmValueFilter.getContentFlag()), filter, "label.content_flag"), layout);
            addLabel(createLabelWithOperator(filter.getContentCommentExpression(), "label.content_comment",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithSingleValue(udmValueFilter -> BooleanUtils.toYNString(
                udmValueFilter.getLastContentFlag()), filter, "label.last_content_flag"), layout);
            addLabel(createLabelWithOperator(filter.getLastContentCommentExpression(), "label.last_content_comment",
                StringUtils.EMPTY), layout);
            addLabel(createLabelWithSingleValue(getPubTypeFunction(UdmValueFilter::getLastPubType, filter), filter,
                "label.last_pub_type"), layout);
            addLabel(createLabelWithSingleValue(UdmValueFilter::getComment, filter, "label.comment"), layout);
        }
        setContent(layout);
    }

    private Function<UdmValueFilter, ?> getPubTypeFunction(Function<UdmValueFilter, PublicationType> function,
                                                           UdmValueFilter filter) {
        PublicationType publicationType = function.apply(filter);
        if (Objects.nonNull(publicationType)) {
            return Objects.nonNull(publicationType.getId())
                ? udmValueFilter -> publicationType.getNameAndDescription()
                : udmValueFilter -> "NULL";
        }
        return function;
    }
}
