package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;

import com.google.common.collect.Sets;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * Verifies {@link UdmUsableDetailsByCountryReportWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/07/2022
 *
 * @author Anton Azarenka
 */
public class UdmSurveyDashboardReportWidgetTest {

    private UdmSurveyDashboardReportWidget widget;

    @Before
    public void setUp() {
        UdmSurveyDashboardReportController controller = createMock(UdmSurveyDashboardReportController.class);
        widget = new UdmSurveyDashboardReportWidget();
        widget.setController(controller);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(
            new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(controller.getPeriods()).andReturn(Arrays.asList(202106, 202212)).once();
        replay(controller, streamSource);
        widget.init();
        verify(controller, streamSource);
    }

    @Test
    public void testInit() {
        verifyWindow(widget, StringUtils.EMPTY, 300, -1, Sizeable.Unit.PIXELS);
        assertEquals("udm-survey-dashboard-report-window", widget.getStyleName());
        assertEquals("udm-survey-dashboard-report-window", widget.getId());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(2, content.getComponentCount());
        Component grid = content.getComponent(0);
        assertThat(grid, instanceOf(Grid.class));
        verifyGrid((Grid) grid, Collections.singletonList(Triple.of("Periods", -1.0, -1)));
        verifyButtonsLayout(content.getComponent(1), "Export", "Close");
    }

    @Test
    public void testGetPeriods() {
        VerticalLayout content = (VerticalLayout) widget.getContent();
        Grid<Integer> grid = (Grid<Integer>) content.getComponent(0);
        grid.select(202106);
        assertEquals(Collections.singleton(202106), widget.getSelectedPeriods());
        grid.select(202212);
        assertEquals(Sets.newHashSet(202106, 202212), widget.getSelectedPeriods());
        grid.deselectAll();
        assertEquals(Collections.emptySet(), widget.getSelectedPeriods());
    }

    @Test
    public void testExportButtonState() {
        VerticalLayout content = (VerticalLayout) widget.getContent();
        Grid<Integer> grid = (Grid<Integer>) content.getComponent(0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(1);
        Button exportButton = (Button) buttonsLayout.getComponent(0);
        assertFalse(exportButton.isEnabled());
        grid.select(202106);
        assertTrue(exportButton.isEnabled());
        grid.deselectAll();
        assertFalse(exportButton.isEnabled());
    }
}
