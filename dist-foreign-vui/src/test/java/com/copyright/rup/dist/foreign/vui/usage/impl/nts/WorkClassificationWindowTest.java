package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyFileDownloader;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySearchWidget;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IWorkClassificationController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.IConfirmCancelListener;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Verifies {@link WorkClassificationWindow}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/12/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, UI.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class WorkClassificationWindowTest {

    private static final Set<String> BATCHES_IDS = Set.of("7db67e36-c465-4d0c-93b1-50418f9c15a1");

    private WorkClassificationWindow window;
    private IWorkClassificationController controller;

    @Before
    public void setUp() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(source).times(1);
        controller = createMock(IWorkClassificationController.class);
        expect(controller.getWorkClassificationThreshold()).andReturn(5).once();
        expect(controller.getClassificationCount(BATCHES_IDS, null)).andReturn(10).once();
        expect(controller.getExportWorkClassificationStreamSource(anyObject(Set.class), anyObject(Supplier.class)))
            .andReturn(streamSource).once();
        replay(UI.class, ui, controller, streamSource);
        window = new WorkClassificationWindow(BATCHES_IDS, controller);
        verify(controller, streamSource);
        reset(UI.class, ui, controller, streamSource);
    }

    @Test
    public void testComponentStructure() {
        verifyWindow(window, "Works Classification", "1000px", "530px", Unit.PIXELS, true);
        var rootLayout = (VerticalLayout) getDialogContent(window);
        assertEquals(3, rootLayout.getComponentCount());
        var toolbarLayout = (HorizontalLayout) rootLayout.getComponentAt(1);
        verifyFileDownloader(toolbarLayout.getComponentAt(0), "Export", true, true);
        verifySearchWidget(toolbarLayout.getComponentAt(1),
            "Enter Wr Wrk Inst or System Title or Standard # or Rightsholder Name/Account #");
        verifyGrid((Grid) rootLayout.getComponentAt(2), List.of(
            Pair.of("Wr Wrk Inst", "140px"),
            Pair.of("System Title", "300px"),
            Pair.of("Classification", "150px"),
            Pair.of("Standard Number", "180px"),
            Pair.of("Standard Number Type", "225px"),
            Pair.of("RH Account #", "150px"),
            Pair.of("RH Name", "300px"),
            Pair.of("Classification Date", "190px"),
            Pair.of("Classified By", "300px")
        ));
        verifyButtonsLayout(getFooterLayout(window), true,
            "Mark as STM", "Mark as Non-STM", "Mark as Belletristic", "Delete Classification", "Clear", "Close");
    }

    @Test
    public void testGridValues() {
        var classifications = loadExpectedWorkClassifications("work_classification_b133a87f.json");
        expect(controller.getClassificationCount(BATCHES_IDS, "")).andReturn(1).once();
        expect(controller.getClassifications(BATCHES_IDS, "", 0, Integer.MAX_VALUE, List.of()))
            .andReturn(classifications).once();
        replay(controller);
        var grid = Whitebox.<Grid<WorkClassification>>getInternalState(window, "grid");
        Object[][] expectedCells = {{
            "180382914", "2001 IEEE Workshop on High Performance Switching", "NON-STM", "1008902112377654XX",
            "VALISBN13", "1000009997", "IEEE - Inst of Electrical and Electronics Engrs", "02/01/2019",
            "user@copyright.com"
        }};
        verifyGridItems(grid, classifications, expectedCells);
        verify(controller);
    }

    @Test
    public void testMarkWithoutSelectedItems() {
        mockStatic(Windows.class);
        Dialog confirmWindow = createMock(Dialog.class);
        var button = (Button) getFooterLayout(window).getComponentAt(0);
        Windows.showNotificationWindow(eq("Please select at least one work"));
        expectLastCall().once();
        replay(Windows.class, controller, confirmWindow);
        button.click();
        verify(Windows.class, controller, confirmWindow);
    }

    @Test
    public void testMarkWithUpdatedUsages() {
        mockStatic(Windows.class);
        Dialog confirmWindow = createMock(Dialog.class);
        var grid = Whitebox.<Grid<WorkClassification>>getInternalState(window, "grid");
        var classifications = buildClassifications();
        grid.setItems(classifications);
        grid.getSelectionModel().select(new WorkClassification());
        var button = (Button) getFooterLayout(window).getComponentAt(0);
        Capture<IConfirmCancelListener> confirmListener = newCapture();
        expect(controller.getCountToUpdate(classifications)).andReturn(2).once();
        expect(Windows.showConfirmDialog(eq("2 usages will be updated. Are you sure you want to confirm action?"),
            capture(confirmListener))).andReturn(confirmWindow).once();
        controller.updateClassifications(classifications, "STM");
        expectLastCall().once();
        replay(Windows.class, controller, confirmWindow);
        button.click();
        confirmListener.getValue().confirm();
        verify(Windows.class, controller, confirmWindow);
    }

    @Test
    public void testMarkWithoutUpdatedUsages() {
        mockStatic(Windows.class);
        Dialog confirmWindow = createMock(Dialog.class);
        var grid = Whitebox.<Grid<WorkClassification>>getInternalState(window, "grid");
        var classifications = buildClassifications();
        grid.setItems(classifications);
        grid.getSelectionModel().select(new WorkClassification());
        var button = (Button) getFooterLayout(window).getComponentAt(0);
        Capture<IConfirmCancelListener> confirmListener = newCapture();
        expect(controller.getCountToUpdate(classifications)).andReturn(0).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to perform action?"), capture(confirmListener)))
            .andReturn(confirmWindow).once();
        controller.updateClassifications(classifications, "STM");
        expectLastCall().once();
        replay(Windows.class, controller, confirmWindow);
        button.click();
        confirmListener.getValue().confirm();
        verify(Windows.class, controller, confirmWindow);
    }

    private Set<WorkClassification> buildClassifications() {
        return Set.of(new WorkClassification());
    }

    private List<WorkClassification> loadExpectedWorkClassifications(String fileName) {
        try {
            var content = TestUtils.fileToString(this.getClass(), fileName);
            var mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<WorkClassification>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
