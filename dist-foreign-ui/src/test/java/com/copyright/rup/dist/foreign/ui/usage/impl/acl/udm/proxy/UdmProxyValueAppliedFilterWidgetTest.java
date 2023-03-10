package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.util.Set;

/**
 * Test for {@link UdmProxyValueAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/10/2023
 *
 * @author Stepan Karakhanov
 */
public class UdmProxyValueAppliedFilterWidgetTest {

    private UdmProxyValueAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        widget = new UdmProxyValueAppliedFilterWidget();
        UdmProxyValueFilter filter = buildUdmProxyValueFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
    }

    private void verifyLayout() {
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyLabel(((VerticalLayout) component).getComponent(0), "Periods", "202212, 202106, 201506");
        verifyLabel(((VerticalLayout) component).getComponent(1), "Pub Type Codes", "BK");
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private UdmProxyValueFilter buildUdmProxyValueFilter() {
        UdmProxyValueFilter filter = new UdmProxyValueFilter();
        filter.setPeriods(Set.of(202106, 201506, 202212));
        filter.setPubTypeNames(Set.of("BK"));
        return filter;
    }
}
