package com.copyright.rup.dist.foreign.ui.audit.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFiltersLabel;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.aacl.IAaclAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonStatusFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.vaadin.server.Sizeable;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link AaclAuditFilterWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/17/20
 *
 * @author Anton Azarenka
 */
public class AaclAuditFilterWidgetTest {

    private static final int USAGE_PERIOD = 2020;

    private CommonAuditFilterWidget widget;

    @Before
    public void setUp() {
        IAaclAuditFilterController auditFilterController = createMock(IAaclAuditFilterController.class);
        expect(auditFilterController.getProductFamily()).andReturn("AACL").times(2);
        expect(auditFilterController.getUsagePeriods()).andReturn(Collections.singletonList(USAGE_PERIOD)).once();
        replay(auditFilterController);
        widget = new AaclAuditFilterWidget(auditFilterController);
        widget.setController(auditFilterController);
        widget.init();
        verify(auditFilterController);
    }

    @Test
    public void testLayout() {
        assertTrue(widget.isSpacing());
        assertEquals(new MarginInfo(true), widget.getMargin());
        assertEquals("audit-filter-widget", widget.getStyleName());
        assertEquals(8, widget.getComponentCount());
        Component component = widget.getComponent(0);
        verifyFiltersLabel(component);
        component = widget.getComponent(1);
        assertThat(component, instanceOf(LazyRightsholderFilterWidget.class));
        assertEquals("Rightsholders", Whitebox.getInternalState(component, Button.class).getCaption());
        assertNotNull(Whitebox.getInternalState(component, IFilterSaveListener.class));
        component = widget.getComponent(2);
        assertThat(component, instanceOf(UsageBatchFilterWidget.class));
        verifyFilterWidget((UsageBatchFilterWidget) component, "Batches");
        component = widget.getComponent(3);
        assertThat(component, instanceOf(CommonStatusFilterWidget.class));
        verifyFilterWidget((CommonStatusFilterWidget) component, "Statuses");
        verifyComboBox(widget.getComponent(4), "Usage Period", true, USAGE_PERIOD);
        verifyTextField(widget.getComponent(5), "Event ID");
        verifyTextField(widget.getComponent(6), "Dist. Name");
        component = widget.getComponent(7);
        assertThat(component, instanceOf(HorizontalLayout.class));
        verifyButtonsLayout((HorizontalLayout) component);
        assertEquals(Alignment.MIDDLE_RIGHT, widget.getComponentAlignment(component));
    }

    @Test
    public void testApplyFilter() {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setProductFamily("AACL");
        assertEquals(auditFilter, widget.getAppliedFilter());
        auditFilter.setCccEventId("53256");
        auditFilter.setUsagePeriod(USAGE_PERIOD);
        Whitebox.setInternalState(widget, "filter", auditFilter);
        widget.applyFilter();
        assertEquals(auditFilter, widget.getAppliedFilter());
    }

    private void verifyFilterWidget(BaseItemsFilterWidget filterWidget, String caption) {
        assertEquals(caption, Whitebox.getInternalState(filterWidget, Button.class).getCaption());
        assertNotNull(Whitebox.getInternalState(filterWidget, IFilterSaveListener.class));
    }

    private void verifyTextField(Component component, String caption) {
        assertNotNull(component);
        assertEquals(TextField.class, component.getClass());
        TextField textField = (TextField) component;
        assertEquals(caption, textField.getCaption());
        assertEquals(100, textField.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, textField.getWidthUnits());
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        verifySize(layout);
        assertEquals("filter-buttons", layout.getStyleName());
        assertEquals("filter-buttons", layout.getId());
        assertEquals(2, layout.getComponentCount());
        Component component = layout.getComponent(0);
        assertThat(component, instanceOf(Button.class));
        verifyButton((Button) component, "Apply", false);
        component = layout.getComponent(1);
        assertThat(component, instanceOf(Button.class));
        verifyButton((Button) component, "Clear", true);
    }

    private void verifyButton(Button button, String caption, boolean enabled) {
        assertEquals(caption, button.getCaption());
        assertEquals(1, button.getListeners(ClickEvent.class).size());
        assertEquals(enabled, button.isEnabled());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(-1, component.getHeight(), 0);
        assertEquals(Unit.PIXELS, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
