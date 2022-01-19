package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * Verifies {@link UdmValueAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/17/2021
 *
 * @author Anton Azarenka
 */
public class UdmValueAppliedFiltersWidgetTest {

    private static final String COMMENT = "Comment";
    private UdmValueAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        widget = new UdmValueAppliedFilterWidget();
        UdmValueFilter filter = buildUdmValueFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
    }

    private void verifyLayout() {
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(24, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Periods", "201506");
        verifyLabel(((VerticalLayout) component).getComponent(1), "Status", "NEW");
        verifyLabel(((VerticalLayout) component).getComponent(2), "Pub Type", "BK - Book");
        verifyLabel(((VerticalLayout) component).getComponent(3), "Assignees", "user@copyright.com");
        verifyLabel(((VerticalLayout) component).getComponent(4), "Last Value Periods", "IS_NULL");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(5),
            "<li><b><i>Wr Wrk Inst From: </i></b>306985899</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(6),
            "<li><b><i>System Title: </i></b>Tenside, surfactants, detergents</li><li>" +
                "<b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(7),
            "<li><b><i>System Standard Number: </i></b>1873-7773</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(8),
            "<li><b><i>RH Account # From: </i></b>1000002859</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(9),
            "<li><b><i>RH Name: </i></b>John Wiley</li><li><b><i>Operator: </i></b>CONTAINS</li>");
        verifyLabel(((VerticalLayout) component).getComponent(10), "Currency", "US - United States");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(11),
            "<li><b><i>Price: </i></b>5.0000000000</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(12),
            "<li><b><i>Price in USD: </i></b>2.5000000000</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabel(((VerticalLayout) component).getComponent(13), "Price Flag", "Y");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(14),
            "<li><b><i>Price Comment: </i></b>price comment</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabel(((VerticalLayout) component).getComponent(15), "Last Price Flag", "Y");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(16),
            "<li><b><i>Last Price Comment: </i></b>last price comment</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(17),
            "<li><b><i>Content: </i></b>50</li><li><b><i>Operator: </i></b>GREATER_THAN_OR_EQUALS_TO</li>");
        verifyLabel(((VerticalLayout) component).getComponent(18), "Content Flag", "Y");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(19),
            "<li><b><i>Content Comment: </i></b>content comment</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabel(((VerticalLayout) component).getComponent(20), "Last Content Flag", "N");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(21),
            "<li><b><i>Last Content Comment: </i></b>last content comment</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabel(((VerticalLayout) component).getComponent(22), "Last Pub Type", "BK - Book");
        verifyLabel(((VerticalLayout) component).getComponent(23), COMMENT, COMMENT);
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private void verifyLabelWithOperator(Component component, String caption) {
        UiTestHelper.verifyLabel(component, caption, ContentMode.HTML, -1.0f);
    }

    private UdmValueFilter buildUdmValueFilter() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(Collections.singleton(201506));
        filter.setStatus(UdmValueStatusEnum.NEW);
        filter.setAssignees(Collections.singleton("user@copyright.com"));
        filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NULL.name()));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 306985899L, null));
        filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, "Tenside, surfactants, detergents", null));
        filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, "1873-7773", null));
        filter.setRhAccountNumberExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 1000002859L, null));
        filter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, "John Wiley", null));
        filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, new BigDecimal("5.0000000000"), null));
        filter.setPriceInUsdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, new BigDecimal("2.5000000000"), null));
        filter.setPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        filter.setPriceCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "price comment", null));
        filter.setLastPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, "last price comment", null));
        filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 50, null));
        filter.setContentFlagExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        filter.setCurrency(new Currency("US", "United States"));
        filter.setContentCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "content comment", null));
        filter.setLastContentFlagExpression(new FilterExpression<>(FilterOperatorEnum.N));
        filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, "last content comment", null));
        filter.setPubType(buildPubType());
        filter.setLastPubType(buildPubType());
        filter.setComment(COMMENT);
        return filter;
    }

    private PublicationType buildPubType() {
        PublicationType publicationType = new PublicationType();
        publicationType.setId("ce650157-3dbf-4385-938c-f3f1e10f4577");
        publicationType.setName("BK");
        publicationType.setDescription("Book");
        return publicationType;
    }
}
