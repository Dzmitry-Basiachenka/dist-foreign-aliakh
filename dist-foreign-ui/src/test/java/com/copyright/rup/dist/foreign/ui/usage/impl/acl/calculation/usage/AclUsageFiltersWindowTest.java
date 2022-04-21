package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.BaseUdmItemsFilterWidget;

import com.google.common.collect.Sets;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link AclUsageFiltersWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/20/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFiltersWindowTest {

    private static final String USAGE_FILTER = "usageFilter";
    private static final Integer LC_ID = 26;
    private static final String LC_DESCRIPTION = "Law Firms";
    private static final String PRINT_TYPE_OF_USE = "PRINT";

    private AclUsageFiltersWindow window;

    @Before
    public void setUp() {
        window = new AclUsageFiltersWindow(createMock(IAclUsageFilterController.class), new AclUsageFilter());
    }

    @Test
    public void testConstructor() {
        verifyWindow(window, "ACL usages additional filters", 600, 490, Unit.PIXELS);
        VerticalLayout verticalLayout = verifyRootLayout(window.getContent());
        verifyPanel(verticalLayout.getComponent(0));
    }

    @Test
    public void testConstructorWithPopulatedFilter() {
        AclUsageFilter usageFilter = buildExpectedFilter();
        usageFilter.setPeriods(Sets.newHashSet(202206, 202212));
        usageFilter.setTypeOfUses(Sets.newHashSet("DIGITAL", PRINT_TYPE_OF_USE));
        window = new AclUsageFiltersWindow(createMock(IAclUsageFilterController.class), usageFilter);
        verifyFiltersData();
    }

    @Test
    public void testClearButtonClickListener() {
        AclUsageFilter usageFilter = buildExpectedFilter();
        Whitebox.setInternalState(window, USAGE_FILTER, usageFilter);
        assertFalse(usageFilter.isEmpty());
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertTrue(usageFilter.isEmpty());
    }

    private VerticalLayout verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyButtonsLayout(verticalLayout.getComponent(1), "Save", "Clear", "Close");
        return verticalLayout;
    }

    private void verifyPanel(Component component) {
        assertTrue(component instanceof Panel);
        Component panelContent = ((Panel) component).getContent();
        assertTrue(panelContent instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(3, verticalLayout.getComponentCount());
        verifyItemsFilterLayout(verticalLayout.getComponent(0), "Periods", "Detail Licensee Classes");
        verifyItemsFilterLayout(verticalLayout.getComponent(1), "Aggregate Licensee Classes", "Pub Types");
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "Types of Use");
    }

    private void verifyItemsFilterLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyItemsFilterWidget(layout.getComponent(0), firstCaption);
        verifyItemsFilterWidget(layout.getComponent(1), secondCaption);
    }

    private AclUsageFilter buildExpectedFilter() {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(LC_ID);
        detailLicenseeClass.setDescription(LC_DESCRIPTION);
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(LC_ID);
        aggregateLicenseeClass.setDiscipline(LC_DESCRIPTION);
        PublicationType publicationType = new PublicationType();
        publicationType.setName("BK");
        publicationType.setDescription("Book");
        AclUsageFilter filter = Whitebox.getInternalState(window, USAGE_FILTER);
        filter.setPeriods(Collections.singleton(202206));
        filter.setDetailLicenseeClasses(Collections.singleton(detailLicenseeClass));
        filter.setAggregateLicenseeClasses(Collections.singleton(aggregateLicenseeClass));
        filter.setPubTypes(Collections.singleton(publicationType));
        filter.setTypeOfUses(Collections.singleton(PRINT_TYPE_OF_USE));
        return filter;
    }

    private void verifyFiltersData() {
        assertFilterWidgetLabelValue("periodFilterWidget", "(2)");
        assertFilterWidgetLabelValue("detailLicenseeClassFilterWidget", "(1)");
        assertFilterWidgetLabelValue("aggregateLicenseeClassFilterWidget", "(1)");
        assertFilterWidgetLabelValue("pubTypeFilterWidget", "(1)");
        assertFilterWidgetLabelValue("typeOfUseFilterWidget", "(2)");
    }

    private void assertFilterWidgetLabelValue(String filterName, String value) {
        BaseUdmItemsFilterWidget filterWidget = Whitebox.getInternalState(window, filterName);
        assertEquals(value, ((Label) filterWidget.getComponent(0)).getValue());
    }
}
