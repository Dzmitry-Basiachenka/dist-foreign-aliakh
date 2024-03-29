package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportController;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Verifies {@link AclCommonReportWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/05/2022
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AclCommonReportWidget.class, RupContextUtils.class, OffsetDateTime.class})
public class AclCommonReportWidgetTest {

    private final AclCommonReportWidget widget = new AclCommonReportWidget();
    private final IAclCommonReportController controller = createMock(IAclCommonReportController.class);
    private final IStreamSource streamSource = createMock(IStreamSource.class);

    @Before
    public void setUp() {
        widget.setController(controller);
        expect(controller.getPeriods()).andReturn(List.of(202012)).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testInit() {
        replay(controller, streamSource);
        widget.init();
        verifyWindow(widget, StringUtils.EMPTY, 350, 145, Sizeable.Unit.PIXELS);
        assertEquals("acl-report-window", widget.getStyleName());
        assertEquals("acl-report-window", widget.getId());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(3, content.getComponentCount());
        verifyComboBox(content.getComponent(0), "Period", true, 202012);
        ComboBox<Integer> periodComboBox = (ComboBox<Integer>) content.getComponent(0);
        periodComboBox.setSelectedItem(202012);
        verifyItemsFilterWidget(content.getComponent(1), "Scenarios");
        verifyButtonsLayout(content.getComponent(2), "Export", "Close");
        verify(controller, streamSource);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetReportInfo() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        mockStatic(RupContextUtils.class);
        AclScenarioFilterWidget filterWidget = createMock(AclScenarioFilterWidget.class);
        Set<AclScenario> scenarios = Set.of(new AclScenario());
        expect(filterWidget.getSelectedItems()).andReturn(scenarios).once();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(RupContextUtils.getUserName()).andReturn("jjohn@copyright.com").once();
        replay(controller, filterWidget, streamSource, RupContextUtils.class, OffsetDateTime.class);
        widget.init();
        VerticalLayout content = (VerticalLayout) widget.getContent();
        ComboBox<Integer> periodComboBox = (ComboBox<Integer>) content.getComponent(0);
        periodComboBox.setSelectedItem(202012);
        Whitebox.setInternalState(widget, filterWidget);
        AclCalculationReportsInfoDto reportInfo = widget.getReportInfo();
        assertEquals(202012, reportInfo.getPeriod(), 0);
        assertEquals("jjohn@copyright.com", reportInfo.getUser());
        assertEquals(now, reportInfo.getReportDateTime());
        verify(controller, filterWidget, streamSource, RupContextUtils.class, OffsetDateTime.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSelectedPeriod() {
        replay(controller, streamSource);
        widget.init();
        widget.setController(controller);
        VerticalLayout content = (VerticalLayout) widget.getContent();
        ComboBox<Integer> periodComboBox = (ComboBox<Integer>) content.getComponent(0);
        AclScenarioFilterWidget scenarioFilterWidget = (AclScenarioFilterWidget) content.getComponent(1);
        Button exportButton = (Button) ((HorizontalLayout) content.getComponent(2)).getComponent(0);
        assertFalse(scenarioFilterWidget.isEnabled());
        assertFalse(exportButton.isEnabled());
        periodComboBox.setSelectedItem(202012);
        assertTrue(scenarioFilterWidget.isEnabled());
        assertTrue(exportButton.isEnabled());
        verify(controller, streamSource);
    }
}
