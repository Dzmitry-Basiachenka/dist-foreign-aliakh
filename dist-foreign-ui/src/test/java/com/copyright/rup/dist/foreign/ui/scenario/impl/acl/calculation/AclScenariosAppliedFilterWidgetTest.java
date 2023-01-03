package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AclScenarioFilter;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.google.common.collect.Sets;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

/**
 * Verifies {@link AclScenariosAppliedFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Mikita Maistrenka
 */
public class AclScenariosAppliedFilterWidgetTest {

    private AclScenariosAppliedFilterWidget widget;

    @Test
    public void testRefreshFilterPanel() {
        widget = new AclScenariosAppliedFilterWidget();
        AclScenarioFilter filter = buildAclScenarioFilter();
        widget.refreshFilterPanel(filter);
        verifyLayout();
    }

    private void verifyLayout() {
        verifyWindow(widget, null, 100, 100, Unit.PERCENTAGE);
        Component component = widget.getContent();
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(4, verticalLayout.getComponentCount());
        verifyLabel(verticalLayout.getComponent(0), "Periods", "202212, 202112, 201506");
        verifyLabel(verticalLayout.getComponent(1), "License Types", "ACL, JACDCL, MACL, VGW");
        verifyLabel(verticalLayout.getComponent(2), "Editable", "Y");
        verifyLabel(verticalLayout.getComponent(3), "Status", "APPROVED");
    }

    private void verifyLabel(Component component, String labelName, String labelValue) {
        UiTestHelper.verifyLabel(component, String.format("<li><b><i>%s: </i></b>%s</li>", labelName, labelValue),
            ContentMode.HTML, -1.0f);
    }

    private AclScenarioFilter buildAclScenarioFilter() {
        AclScenarioFilter filter = new AclScenarioFilter();
        filter.setPeriods(Sets.newHashSet(202112, 201506, 202212));
        filter.setLicenseTypes(Sets.newHashSet("MACL", "JACDCL", "ACL", "VGW"));
        filter.setEditable(true);
        filter.setStatus(ScenarioStatusEnum.APPROVED);
        return filter;
    }
}
