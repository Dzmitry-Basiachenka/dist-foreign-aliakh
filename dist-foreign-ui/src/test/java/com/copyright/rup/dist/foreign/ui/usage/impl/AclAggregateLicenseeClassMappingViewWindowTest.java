package com.copyright.rup.dist.foreign.ui.usage.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsVisibility;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.google.common.collect.ImmutableMap;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Map;

/**
 * Verifies {@link AclAggregateLicenseeClassMappingViewWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/11/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclAggregateLicenseeClassMappingViewWindowTest {

    private static final String MATERIALS = "Materials";

    private AclAggregateLicenseeClassMappingViewWindow window;
    private final List<DetailLicenseeClass> defaultParams = List.of(
        buildDetailLicenseeClass(2, "Textiles, Apparel, etc.", 51, MATERIALS),
        buildDetailLicenseeClass(6, "Chemicals", 52, "Medical"));
    private final List<DetailLicenseeClass> appliedParams = List.of(
        buildDetailLicenseeClass(2, "Textiles, Apparel, etc.", 51, MATERIALS),
        buildDetailLicenseeClass(6, "Chemicals", 53, "Metals"));

    @Before
    public void setUp() {
        window = new AclAggregateLicenseeClassMappingViewWindow();
        window.setDefault(defaultParams);
    }

    @Test
    public void testConstructor() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        verifyWindow(window, "Licensee Class Mapping", 850, 550, Unit.PIXELS);
        assertEquals(2, content.getComponentCount());
        Component component = content.getComponent(0);
        assertThat(component, instanceOf(Grid.class));
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        verifyButtonsLayout(content.getComponent(1), "Close");
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        verifyButtonsVisibility(ImmutableMap.of(buttonsLayout.getComponent(0), true));
    }

    @Test
    public void testSetDefaultParameters() {
        window.setDefault(defaultParams);
        Map<Integer, AggregateLicenseeClass> idsToDefaultAggLicClasses = ImmutableMap.of(
            2, buildAggregateLicenseeClass(51, MATERIALS),
            6, buildAggregateLicenseeClass(52, "Medical"));
        assertEquals(idsToDefaultAggLicClasses, Whitebox.getInternalState(window, "idsToDefaultAggLicClasses"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSetAppliedParameters() {
        window.setAppliedParameters(appliedParams);
        Object[][] expectedCells = {
            {2, "Textiles, Apparel, etc.", 51, MATERIALS, 51, MATERIALS},
            {6, "Chemicals", 52, "Medical", 53, "Metals"}
        };
        verifyGridItems((Grid) ((VerticalLayout) window.getContent()).getComponent(0), appliedParams, expectedCells);
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid) {
        UiTestHelper.verifyGrid(grid, List.of(
            Triple.of("Det LC ID", -1.0, 1),
            Triple.of("Det LC Name", -1.0, 2),
            Triple.of("Default Agg LC ID", -1.0, 1),
            Triple.of("Default Agg LC Name", -1.0, 2),
            Triple.of("Scenario Agg LC ID", -1.0, 1),
            Triple.of("Scenario Agg LC Name", -1.0, 2)));
        List<Column> columns = grid.getColumns();
        columns.forEach(column -> assertTrue(column.isSortable()));
        assertFalse(grid.getDataProvider().isInMemory());
    }

    private DetailLicenseeClass buildDetailLicenseeClass(Integer detLicClassId, String detLicClassDescription,
                                                         Integer aggLicClassId, String aggLicClassDescription) {
        DetailLicenseeClass detLicClass = new DetailLicenseeClass();
        detLicClass.setId(detLicClassId);
        detLicClass.setDescription(detLicClassDescription);
        detLicClass.setAggregateLicenseeClass(buildAggregateLicenseeClass(aggLicClassId, aggLicClassDescription));
        return detLicClass;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer aggLicClassId, String aggLicClassDescription) {
        AggregateLicenseeClass aggLicClass = new AggregateLicenseeClass();
        aggLicClass.setId(aggLicClassId);
        aggLicClass.setDescription(aggLicClassDescription);
        return aggLicClass;
    }
}
