package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyFileDownloader;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyMenuBar;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Verifies {@link AaclUsageWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AaclUsageWidget.class, ForeignSecurityUtils.class, Windows.class, UI.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class AaclUsageWidgetTest {

    private static final String BATCH_ID = "3a070817-03ae-4ebd-b25f-dd3168a7ffb0";
    private static final String WIDTH_140 = "140px";

    private AaclUsageWidget widget;
    private IAaclUsageController controller;

    @Before
    public void setUp() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(2);
        expect(ui.getUIId()).andReturn(1).times(2);
        controller = createMock(IAaclUsageController.class);
        var filterWidget = new AaclUsageFilterWidget(createMock(IAaclUsageFilterController.class));
        filterWidget.getAppliedFilter().setUsageBatchesIds(Set.of(BATCH_ID));
        widget = new AaclUsageWidget(controller);
        widget.setController(controller);
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(source).times(2);
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        expect(controller.getSendForClassificationUsagesStreamSource()).andReturn(streamSource).once();
        replay(UI.class, ui, controller, streamSource);
        widget.init();
        verify(UI.class, ui, controller, streamSource);
        reset(UI.class, ui, controller, streamSource);
    }

    @Test
    public void testWidgetStructure() {
        assertThat(widget.getPrimaryComponent(), instanceOf(AaclUsageFilterWidget.class));
        Component secondaryComponent = widget.getSecondaryComponent();
        assertThat(secondaryComponent, instanceOf(VerticalLayout.class));
        VerticalLayout contentLayout = (VerticalLayout) secondaryComponent;
        var toolbarLayout = (HorizontalLayout) contentLayout.getComponentAt(0);
        verifyButtonsLayout((HorizontalLayout) toolbarLayout.getComponentAt(0));
        verifyGrid((Grid<?>) contentLayout.getComponentAt(1), List.of(
            Pair.of("Detail ID", "130px"),
            Pair.of("Detail Status", "115px"),
            Pair.of("Product Family", "125px"),
            Pair.of("Usage Batch Name", "145px"),
            Pair.of("Period End Date", "115px"),
            Pair.of("RH Account #", "115px"),
            Pair.of("RH Name", "300px"),
            Pair.of("Wr Wrk Inst", "110px"),
            Pair.of("System Title", "300px"),
            Pair.of("Standard Number", WIDTH_140),
            Pair.of("Standard Number Type", "155px"),
            Pair.of("Det LC ID", "80px"),
            Pair.of("Det LC Enrollment", WIDTH_140),
            Pair.of("Det LC Discipline", WIDTH_140),
            Pair.of("Pub Type", WIDTH_140),
            Pair.of("Institution", WIDTH_140),
            Pair.of("Usage Period", "100px"),
            Pair.of("Usage Source", WIDTH_140),
            Pair.of("Number of Copies", WIDTH_140),
            Pair.of("Number of Pages", WIDTH_140),
            Pair.of("Right Limitation", "120px"),
            Pair.of("Comment", "200px")));
    }

    @Test
    public void testGetController() {
        assertSame(controller, widget.getController());
    }

    @Test
    public void testAddToScenarioButtonClickListener() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithEmptyUsages() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithInvalidStatus() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithInvalidRightsholders() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithProcessingBatches() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithAttachedToScenarioBatches() {
        //TODO {aliakh} implement
    }

    @Test
    public void testAddToScenarioButtonClickListenerWithIneligibleBatches() {
        //TODO {aliakh} implement
    }

    @Test
    public void testInitMediator() throws Exception {
        AaclUsageMediator mediator = createMock(AaclUsageMediator.class);
        expectNew(AaclUsageMediator.class).andReturn(mediator).once();
        mediator.setLoadUsageBatchMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setLoadFundPoolMenuItem(anyObject(MenuItem.class));
        expectLastCall().once();
        mediator.setSendForClassificationButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setSendForClassificationDownloader(anyObject(OnDemandFileDownloader.class));
        expectLastCall().once();
        mediator.setLoadClassifiedUsagesButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        replay(AaclUsageMediator.class, mediator, controller);
        assertNotNull(widget.initMediator());
        verify(AaclUsageMediator.class, mediator, controller);
    }

    @Test
    public void testSendForClassification() {
        //TODO {aliakh} implement
    }

    @Test
    public void testViewFundPool() {
        //TODO {aliakh} implement
    }

    @Test
    public void testSendForClassificationInvalidUsagesState() {
        //TODO {aliakh} implement
    }

    @Test
    public void testLoadClassifiedUsagesButtonClickListener() {
        //TODO {aliakh} implement
    }

    private void verifyButtonsLayout(HorizontalLayout buttonsLayout) {
        assertEquals(6, buttonsLayout.getComponentCount());
        verifyMenuBar(buttonsLayout.getComponentAt(0), "Usage Batch", true, List.of("Load", "View"));
        verifyMenuBar(buttonsLayout.getComponentAt(1), "Fund Pool", true, List.of("Load", "View"));
        verifyFileDownloader(buttonsLayout.getComponentAt(2), "Send for Classification", true, true);
        verifyButton(buttonsLayout.getComponentAt(3), "Load Classified Details", true, true);
        verifyButton(buttonsLayout.getComponentAt(4), "Add To Scenario", true, true);
        verifyFileDownloader(buttonsLayout.getComponentAt(5), "Export", true, true);
    }
}