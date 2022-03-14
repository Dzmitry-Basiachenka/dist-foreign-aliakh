package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link AclGrantDetailAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/11/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailAppliedFilterWidgetTest {

    private static final String GRANT_SET_NAME = "Grant set name";
    private static final String LICENSE_TYPE = "MACL";
    private static final String GRANT_STATUS = "DENY";
    private static final String TYPE_OF_USE = "PRINT";

    private AclGrantDetailAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        widget = new AclGrantDetailAppliedFilterWidget();
        AclGrantDetailFilter filter = buildAclGrantDetailFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
    }

    private void verifyLayout() {
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(10, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Grant Sets", GRANT_SET_NAME);
        verifyLabel(((VerticalLayout) component).getComponent(1), "License Types", LICENSE_TYPE);
        verifyLabel(((VerticalLayout) component).getComponent(2), "Grant Statuses", GRANT_STATUS);
        verifyLabel(((VerticalLayout) component).getComponent(3), "Types of Use", TYPE_OF_USE);
        verifyLabel(((VerticalLayout) component).getComponent(4), "Grant Set Period", "202212");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(5),
            "<li><b><i>Wr Wrk Inst From: </i></b>306985899</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(6),
            "<li><b><i>RH Account # From: </i></b>1000002859</li><li><b><i>Operator: </i></b>EQUALS</li>");
        verifyLabelWithOperator(((VerticalLayout) component).getComponent(7),
            "<li><b><i>RH Name: </i></b>John Wiley</li><li><b><i>Operator: </i></b>CONTAINS</li>");
        verifyLabel(((VerticalLayout) component).getComponent(8), "Eligible", "Y");
        verifyLabel(((VerticalLayout) component).getComponent(9), "Editable", "N");
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private void verifyLabelWithOperator(Component component, String caption) {
        UiTestHelper.verifyLabel(component, caption, ContentMode.HTML, -1.0f);
    }

    private AclGrantDetailFilter buildAclGrantDetailFilter() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Collections.singleton(GRANT_SET_NAME));
        filter.setLicenseTypes(Collections.singleton(LICENSE_TYPE));
        filter.setGrantStatuses(Collections.singleton(GRANT_STATUS));
        filter.setTypeOfUses(Collections.singleton(TYPE_OF_USE));
        filter.setGrantSetPeriod(202212);
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 306985899L, null));
        filter.setRhAccountNumberExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 1000002859L, null));
        filter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, "John Wiley", null));
        filter.setEligibleExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        filter.setEditableExpression(new FilterExpression<>(FilterOperatorEnum.N));
        return filter;
    }
}
