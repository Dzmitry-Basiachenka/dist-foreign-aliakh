package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.IWorkClassificationController;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Sets;
import com.vaadin.server.Extension;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link WorkClassificationWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/12/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class WorkClassificationWindowTest {

    private final Set<String> batchesIds = Collections.singleton(RupPersistUtils.generateUuid());
    private WorkClassificationWindow window;
    private IWorkClassificationController workClassificationController;

    @Before
    public void setUp() {
        workClassificationController = createMock(IWorkClassificationController.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(workClassificationController.getWorkClassificationThreshold()).andReturn(5).once();
        expect(workClassificationController.getClassificationCount(batchesIds, null))
            .andReturn(10).once();
        expect(workClassificationController.getExportWorkClassificationStreamSource(
            anyObject(Set.class), anyObject(Supplier.class))).andReturn(streamSource).once();
        expect(streamSource.getSource())
            .andReturn(new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).once();
        replay(workClassificationController, streamSource);
        window = new WorkClassificationWindow(batchesIds, workClassificationController);
        verify(workClassificationController, streamSource);
        reset(workClassificationController);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("Works Classification", window.getCaption());
        verifySize(window, 1000, Unit.PIXELS, 530, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(new MarginInfo(true), content.getMargin());
        assertTrue(content.isSpacing());
        verifySize(content, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        assertEquals(4, content.getComponentCount());
        Component toolbarComponent = content.getComponent(1);
        assertEquals(HorizontalLayout.class, toolbarComponent.getClass());
        HorizontalLayout toolbar = (HorizontalLayout) toolbarComponent;
        verifyExportButton(toolbar.getComponent(0));
        Component searchWidgetComponent = toolbar.getComponent(1);
        assertEquals(SearchWidget.class, searchWidgetComponent.getClass());
        Component gridComponent = content.getComponent(2);
        assertEquals(Grid.class, gridComponent.getClass());
        verifyGrid((Grid) gridComponent);
        assertEquals(1, content.getExpandRatio(gridComponent), 0);
        Component buttonsLayoutComponent = content.getComponent(3);
        assertEquals(HorizontalLayout.class, buttonsLayoutComponent.getClass());
        verifyButtonsLayout((HorizontalLayout) buttonsLayoutComponent);
        assertEquals(Alignment.BOTTOM_RIGHT, content.getComponentAlignment(buttonsLayoutComponent));
    }

    @Test
    public void testMarkWithoutSelectedItems() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        VerticalLayout content = (VerticalLayout) window.getContent();
        Button button = (Button) ((HorizontalLayout) content.getComponent(3)).getComponent(0);
        ClickListener listener = (ClickListener) button.getListeners(ClickEvent.class).iterator().next();
        Windows.showNotificationWindow(eq("Please select at least one work"));
        expectLastCall().once();
        replay(workClassificationController, confirmWindowCapture, Windows.class);
        listener.buttonClick(null);
        verify(workClassificationController, confirmWindowCapture, Windows.class);
    }

    @Test
    public void testMarkWithUpdatedUsages() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<WorkClassification> grid = (Grid) content.getComponent(2);
        Set<WorkClassification> classifications = buildClassifications();
        grid.setItems(classifications);
        grid.getSelectionModel().select(new WorkClassification());
        Button button = (Button) ((HorizontalLayout) content.getComponent(3)).getComponent(0);
        Capture<IListener> confirmListener = new Capture<>();
        expect(workClassificationController.getCountToUpdate(classifications)).andReturn(2).once();
        expect(Windows.showConfirmDialog(eq("2 usages will be updated. Are you sure you want to confirm action?"),
            capture(confirmListener))).andReturn(confirmWindowCapture).once();
        workClassificationController.updateClassifications(classifications, "STM");
        expectLastCall().once();
        replay(workClassificationController, confirmWindowCapture, Windows.class);
        ClickListener listener = (ClickListener) button.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(null);
        confirmListener.getValue().onActionConfirmed();
        verify(workClassificationController, confirmWindowCapture, Windows.class);
    }

    @Test
    public void testMarkWithoutUpdatedUsages() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<WorkClassification> grid = (Grid) content.getComponent(2);
        Set<WorkClassification> classifications = buildClassifications();
        grid.setItems(classifications);
        grid.getSelectionModel().select(new WorkClassification());
        Button button = (Button) ((HorizontalLayout) content.getComponent(3)).getComponent(0);
        Capture<IListener> confirmListener = new Capture<>();
        expect(workClassificationController.getCountToUpdate(classifications)).andReturn(0).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to perform action?"), capture(confirmListener)))
            .andReturn(confirmWindowCapture).once();
        workClassificationController.updateClassifications(classifications, "STM");
        expectLastCall().once();
        replay(workClassificationController, confirmWindowCapture, Windows.class);
        ClickListener listener = (ClickListener) button.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(null);
        confirmListener.getValue().onActionConfirmed();
        verify(workClassificationController, confirmWindowCapture, Windows.class);
    }

    private void verifyExportButton(Component exportButtonComponent) {
        assertEquals(Button.class, exportButtonComponent.getClass());
        Button exportButton = (Button) exportButtonComponent;
        assertEquals("Export", exportButton.getCaption());
        Collection<Extension> extensions = exportButton.getExtensions();
        assertTrue(CollectionUtils.isNotEmpty(extensions));
        assertEquals(1, extensions.size());
        assertThat(extensions.iterator().next(), instanceOf(OnDemandFileDownloader.class));
    }

    private void verifySize(Component component, float width, Unit widthUnit, float height, Unit heightUnit) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(widthUnit, component.getWidthUnits());
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        verifySize(grid, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Wr Wrk Inst", "System Title", "Classification", "Standard Number",
            "Standard Number Type", "RH Account #", "RH Name", "Classification Date", "Classified By"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        assertEquals(100, columns.get(0).getWidth(), 0);
        assertEquals(285, columns.get(1).getWidth(), 0);
        assertEquals(110, columns.get(2).getWidth(), 0);
        assertEquals(140, columns.get(3).getWidth(), 0);
        assertEquals(155, columns.get(4).getWidth(), 0);
        assertEquals(100, columns.get(5).getWidth(), 0);
        assertEquals(285, columns.get(6).getWidth(), 0);
        assertEquals(135, columns.get(7).getWidth(), 0);
        assertEquals(245, columns.get(8).getWidth(), 0);
    }

    private void verifyButtonsLayout(HorizontalLayout buttonsLayout) {
        assertTrue(buttonsLayout.isSpacing());
        assertEquals(new MarginInfo(false), buttonsLayout.getMargin());
        assertEquals(6, buttonsLayout.getComponentCount());
        assertEquals("Mark as STM", buttonsLayout.getComponent(0).getCaption());
        assertEquals("Mark as Non-STM", buttonsLayout.getComponent(1).getCaption());
        assertEquals("Mark as Belletristic", buttonsLayout.getComponent(2).getCaption());
        assertEquals("Delete Classification", buttonsLayout.getComponent(3).getCaption());
        assertEquals("Clear", buttonsLayout.getComponent(4).getCaption());
        assertEquals("Close", buttonsLayout.getComponent(5).getCaption());
    }

    private Set<WorkClassification> buildClassifications() {
        return Sets.newHashSet(new WorkClassification(), new WorkClassification());
    }
}
