package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Test for {@link UdmBaselineValueAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/21/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueAppliedFilterWidgetTest {

    private static final BigDecimal PRICE = new BigDecimal("50.0000000000");
    private static final BigDecimal CONTENT = new BigDecimal("2.0000000000");
    private static final BigDecimal CONTENT_UNIT_PRICE = new BigDecimal("25.0000000000");
    private UdmBaselineValueAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        widget = new UdmBaselineValueAppliedFilterWidget();
        UdmBaselineValueFilter filter = buildUdmFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
    }

    private void verifyLayout() {
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(11, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Periods", "202212, 202106, 201506");
        verifyLabel(((VerticalLayout) component).getComponent(1), "Pub Types",
            "BK - Book, BK2 - Book series, SJ - Scholarly Journal");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(2),
            "<li><b><i>Wr Wrk Inst From: </i></b>306985899</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(3),
            "<li><b><i>System Title: </i></b>Tenside, surfactants, detergents</li><li>" +
                "<b><i>Operator: </i></b>EQUALS</li>");
        verifyLabel(((VerticalLayout) component).getComponent(4), "Price Flag", "N");
        verifyLabel(((VerticalLayout) component).getComponent(5), "Content Flag", "Y");
        verifyLabel(((VerticalLayout) component).getComponent(6), "CUP Flag", "N");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(7),
            "<li><b><i>Price From: </i></b>50.0000000000</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(8),
            "<li><b><i>Content From: </i></b>2.0000000000</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(9),
            "<li><b><i>Content Unit Price From: </i></b>25.0000000000</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(10),
            "<li><b><i>Comment: </i></b>Comment</li><li><b><i>Operator: </i></b>EQUALS</li>");
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private void verifyLabelWithOperator(Component component, String caption) {
        UiTestHelper.verifyLabel(component, caption, ContentMode.HTML, -1.0f);
    }

    private UdmBaselineValueFilter buildUdmFilter() {
        UdmBaselineValueFilter filter = new UdmBaselineValueFilter();
        filter.setPeriods(Set.of(202106, 201506, 202212));
        filter.setPubTypes(Set.of(buildPubType("f1f523ca-1b46-4d3a-842d-99252785187c", "BK2", "Book series"),
            buildPubType("34574f62-7922-48b9-b798-73bf5c3163da", "SJ", "Scholarly Journal"),
            buildPubType("ce650157-3dbf-4385-938c-f3f1e10f4577", "BK", "Book")));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 306985899L, null));
        filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, "Tenside, surfactants, detergents", null));
        filter.setPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.N));
        filter.setContentFlagExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        filter.setContentUnitPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.N));
        filter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null));
        filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_UNIT_PRICE, null));
        filter.setCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, "Comment", null));
        return filter;
    }

    private PublicationType buildPubType(String id, String name, String description) {
        PublicationType publicationType = new PublicationType();
        publicationType.setId(id);
        publicationType.setName(name);
        publicationType.setDescription(description);
        return publicationType;
    }
}
