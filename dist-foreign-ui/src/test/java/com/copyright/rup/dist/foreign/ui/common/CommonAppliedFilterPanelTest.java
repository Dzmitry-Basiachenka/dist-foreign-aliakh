package com.copyright.rup.dist.foreign.ui.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Test for {@link CommonAppliedFilterPanel}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Anton Azarenka
 */
public class CommonAppliedFilterPanelTest {

    private final CommonAppliedFilterPanel filterPanel = new CommonAppliedFilterPanel() {};
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

    @Test
    public void testConvertFilterOperatorToString() {
        assertEquals("EQUALS", filterPanel.convertFilterOperatorToString(FilterOperatorEnum.EQUALS));
        assertEquals("IS_NOT_NULL", filterPanel.convertFilterOperatorToString(FilterOperatorEnum.IS_NOT_NULL));
        assertEquals("BETWEEN", filterPanel.convertFilterOperatorToString(FilterOperatorEnum.BETWEEN));
        assertNull(filterPanel.convertFilterOperatorToString(null));
    }

    @Test
    public void testSortStringValuesByNaturalOrder() {
        assertEquals(new LinkedHashSet<>(List.of("ACL", "JACDCL", "MACL", "VGW")),
            filterPanel.sortStringValuesByNaturalOrder(Set.of("VGW", "MACL", "ACL", "JACDCL")));
    }

    @Test
    public void testSortIntegerValuesByDesc() {
        assertEquals(new LinkedHashSet<>(List.of(10, 9, 8, 7, 4, 2, 1)),
            filterPanel.sortIntegerValuesByDesc(Set.of(9, 4, 1, 2, 10, 7, 8)));
    }

    @Test
    public void testSortDetailLicenseeClasses() {
        DetailLicenseeClass detailLicenseeClass1 = new DetailLicenseeClass(2, "Textiles, Apparel, etc.");
        DetailLicenseeClass detailLicenseeClass2 = new DetailLicenseeClass(3, "Lumber, Paper, etc.");
        Set<DetailLicenseeClass> actualDetailLicenseeClasses = Set.of(detailLicenseeClass2, detailLicenseeClass1);
        Set<DetailLicenseeClass> expectedDetailLicenseeClasses =
            new LinkedHashSet<>(List.of(detailLicenseeClass1, detailLicenseeClass2));
        assertEquals(expectedDetailLicenseeClasses, filterPanel.sortDetailLicenseeClasses(actualDetailLicenseeClasses));
    }

    @Test
    public void testSortAggregateLicenseeClasses() {
        AggregateLicenseeClass aggLicClass1 = new AggregateLicenseeClass();
        aggLicClass1.setId(56);
        aggLicClass1.setDescription("Financial");
        AggregateLicenseeClass aggLicClass2 = new AggregateLicenseeClass();
        aggLicClass2.setId(113);
        aggLicClass2.setDescription("Life Sciences");
        Set<AggregateLicenseeClass> actualAggLicClasses = Set.of(aggLicClass2, aggLicClass1);
        Set<AggregateLicenseeClass> expectedAggLicClasses = new LinkedHashSet<>(List.of(aggLicClass1, aggLicClass2));
        assertEquals(expectedAggLicClasses, filterPanel.sortAggregateLicenseeClasses(actualAggLicClasses));
    }

    @Test
    public void testSortPublicationTypes() {
        PublicationType pubType1 = new PublicationType();
        pubType1.setName("BK");
        pubType1.setDescription("Book");
        PublicationType pubType2 = new PublicationType();
        pubType2.setName("NP");
        pubType2.setDescription("Newspaper");
        Set<PublicationType> actualPubTypes = Set.of(pubType2, pubType1);
        Set<PublicationType> expectedPubTypes = new LinkedHashSet<>(List.of(pubType1, pubType2));
        assertEquals(expectedPubTypes, filterPanel.sortPublicationTypes(actualPubTypes));
    }

    @Test
    public void testSortActionReasons() {
        UdmActionReason reason1 = new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content");
        UdmActionReason reason2 = new UdmActionReason("04b99a98-56d3-4f59-bfcb-2c72d18ebbbc", "Created new work");
        UdmActionReason reason3 =
            new UdmActionReason("be6ece83-4739-479d-b468-5dcea822e1f8", "Incorrect/inappropriate Det Lic Class");
        Set<UdmActionReason> actualReasons = Set.of(reason3, reason1, reason2);
        Set<UdmActionReason> expectedReasons = new LinkedHashSet<>(List.of(reason1, reason2, reason3));
        assertEquals(expectedReasons, filterPanel.sortActionReasons(actualReasons));
    }

    private void verifyLabel(Label actualLabel) {
        assertNotNull(actualLabel);
        assertEquals(expectedLabel.getValue(), actualLabel.getValue());
    }
}
