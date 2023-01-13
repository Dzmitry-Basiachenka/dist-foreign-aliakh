package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

/**
 * Test for {@link CommonAclAppliedFilterPanel}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Anton Azarenka
 */
public class CommonAclAppliedFilterPanelTest {

    private final CommonAclAppliedFilterPanel filterPanel = new CommonAclAppliedFilterPanel() {};
    private Label expectedLabel;
    private UdmUsageFilter udmUsageFilter;

    @Before
    public void setUp() {
        expectedLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
        udmUsageFilter = new UdmUsageFilter();
    }

    @Test
    public void testCreateLabelWithSingleValue() {
        expectedLabel.setValue("<li><b><i>Status: </i></b>ELIGIBLE</li>");
        assertNull(
            filterPanel.createLabelWithSingleValue(UdmUsageFilter::getUsageStatus, udmUsageFilter, "label.status"));
        udmUsageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        Label actualLabel =
            filterPanel.createLabelWithSingleValue(UdmUsageFilter::getUsageStatus, udmUsageFilter, "label.status");
        verifyLabel(actualLabel);
    }

    @Test
    public void testCreateLabelWithMultipleValues() {
        expectedLabel.setValue("<li><b><i>Assignees: </i></b>user@copyright.com, john@copyright.com</li>");
        assertNull(filterPanel.createLabelWithMultipleValues(udmUsageFilter.getAssignees(), "label.assignees",
            String::valueOf));
        udmUsageFilter.setAssignees(new HashSet<>(List.of("user@copyright.com", "john@copyright.com")));
        Label actualLabel = filterPanel.createLabelWithMultipleValues(udmUsageFilter.getAssignees(), "label.assignees",
            String::valueOf);
        verifyLabel(actualLabel);
    }

    @Test
    public void testCreateLabelWithOperator() {
        expectedLabel.setValue("<li><b><i>Annual Multiplier From: </i></b>50</li><li><b><i>Annual Multiplier To: " +
            "</i></b>100</li><li><b><i>Operator: </i></b>BETWEEN</li>");
        FilterExpression<Number> filterExpression = new FilterExpression<>();
        assertNull(filterPanel.createLabelWithOperator(filterExpression, "label.annual_multiplier_from",
            "label.annual_multiplier_to"));
        filterExpression.setFieldFirstValue(50);
        filterExpression.setFieldSecondValue(100);
        filterExpression.setOperator(FilterOperatorEnum.BETWEEN);
        udmUsageFilter.setAnnualMultiplierExpression(filterExpression);
        Label actualLabel = filterPanel.createLabelWithOperator(filterExpression, "label.annual_multiplier_from",
            "label.annual_multiplier_to");
        verifyLabel(actualLabel);
    }

    @Test
    public void testCreateLabelWithOperatorIsNull() {
        expectedLabel.setValue("<li><b><i>Price: </i></b>IS_NULL</li>");
        FilterExpression<Number> filterExpression = new FilterExpression<>();
        filterExpression.setOperator(FilterOperatorEnum.IS_NULL);
        Label actualLabel = filterPanel.createLabelWithOperator(filterExpression, "label.price", StringUtils.EMPTY);
        verifyLabel(actualLabel);
    }

    private void verifyLabel(Label actualLabel) {
        assertNotNull(actualLabel);
        assertEquals(expectedLabel.getValue(), actualLabel.getValue());
    }
}
