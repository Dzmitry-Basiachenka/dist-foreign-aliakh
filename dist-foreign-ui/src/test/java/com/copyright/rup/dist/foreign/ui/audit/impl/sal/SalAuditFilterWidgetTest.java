package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonStatusFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.LazyRightsholderFilterWindow.IRightsholderFilterSaveListener;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link SalAuditFilterWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalAuditFilterWidgetTest {

    private CommonAuditFilterWidget widget;

    @Before
    public void setUp() {
        ISalAuditFilterController controller = createMock(ISalAuditFilterController.class);
        expect(controller.getProductFamily()).andReturn("SAL").times(2);
        expect(controller.getUsagePeriods()).andReturn(buildUsagePeriods()).once();
        replay(controller);
        widget = new SalAuditFilterWidget(controller);
        widget.setController(controller);
        widget.init();
        verify(controller);
    }

    @Test
    public void testLayout() {
        assertTrue(widget.isSpacing());
        assertEquals(new MarginInfo(true), widget.getMargin());
        assertEquals("audit-filter-widget", widget.getStyleName());
        assertEquals(9, widget.getComponentCount());
        Component component = widget.getComponent(0);
        assertTrue(component instanceof Label);
        verifyLabel((Label) component);
        component = widget.getComponent(1);
        assertTrue(component instanceof LazyRightsholderFilterWidget);
        assertEquals("Rightsholders", Whitebox.getInternalState(component, Button.class).getCaption());
        assertNotNull(Whitebox.getInternalState(component, IRightsholderFilterSaveListener.class));
        component = widget.getComponent(2);
        assertTrue(component instanceof UsageBatchFilterWidget);
        verifyFilterWidget((UsageBatchFilterWidget) component, "Batches");
        component = widget.getComponent(3);
        assertTrue(component instanceof CommonStatusFilterWidget);
        verifyFilterWidget((CommonStatusFilterWidget) component, "Status");
        verifyComboBox(widget.getComponent(4), "Detail Type", Arrays.asList(SalDetailTypeEnum.IB,
            SalDetailTypeEnum.UD));
        verifyComboBox(widget.getComponent(5), "Usage Period", buildUsagePeriods());
        verifyTextField(widget.getComponent(6), "Event ID");
        verifyTextField(widget.getComponent(7), "Dist. Name");
        component = widget.getComponent(8);
        assertTrue(component instanceof HorizontalLayout);
        verifyButtonsLayout((HorizontalLayout) component);
        assertEquals(Alignment.MIDDLE_RIGHT, widget.getComponentAlignment(component));
    }

    @Test
    public void testApplyFilter() {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setProductFamily("SAL");
        assertEquals(auditFilter, widget.getAppliedFilter());
        auditFilter.setRhAccountNumbers(Collections.singleton(1000018380L));
        auditFilter.setBatchesIds(Collections.singleton("8f8fbc50-4ad9-4103-af71-7d96b558a81a"));
        auditFilter.setStatuses(Collections.singleton(UsageStatusEnum.ELIGIBLE));
        auditFilter.setCccEventId("53256");
        auditFilter.setDistributionName("SAL December 2020");
        auditFilter.setSearchValue("Limited");
        auditFilter.setUsagePeriod(2020);
        auditFilter.setSalDetailType(SalDetailTypeEnum.IB);
        Whitebox.setInternalState(widget, "filter", auditFilter);
        widget.applyFilter();
        assertEquals(auditFilter, widget.getAppliedFilter());
    }

    private void verifyLabel(Label label) {
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
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
        assertEquals(Unit.PERCENTAGE, textField.getWidthUnits());
    }

    private void verifyComboBox(Component component, String caption, Collection<?> expectedItems) {
        assertEquals(ComboBox.class, component.getClass());
        ComboBox<?> comboBox = (ComboBox<?>) component;
        assertEquals(caption, comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
        assertTrue(comboBox.isEmptySelectionAllowed());
        assertTrue(comboBox.isTextInputAllowed());
        ListDataProvider<?> listDataProvider = (ListDataProvider<?>) comboBox.getDataProvider();
        assertEquals(expectedItems, listDataProvider.getItems());
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        verifySize(layout);
        assertEquals("filter-buttons", layout.getStyleName());
        assertEquals("filter-buttons", layout.getId());
        assertEquals(2, layout.getComponentCount());
        Component component = layout.getComponent(0);
        assertTrue(component instanceof Button);
        verifyButton((Button) component, "Apply", false);
        component = layout.getComponent(1);
        assertTrue(component instanceof Button);
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

    private List<Integer> buildUsagePeriods() {
        return Collections.singletonList(2020);
    }
}