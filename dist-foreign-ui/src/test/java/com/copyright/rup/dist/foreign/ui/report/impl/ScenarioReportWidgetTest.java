package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenarioReportController;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Extension;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * Verifies {@link ScenarioReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/22/2020
 *
 * @author Ihar Suvorau
 */
public class ScenarioReportWidgetTest {

    @Test
    public void testInit() {
        ICommonScenarioReportController controller = new WorkSharesByAggLcClassReportController();
        IScenarioService scenarioService = createMock(IScenarioService.class);
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName("Test scenario name");
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, productFamilyProvider);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn("AACL").once();
        expect(scenarioService.getScenarios("AACL")).andReturn(Collections.singletonList(scenario)).once();
        expect(streamSource.getSource()).andReturn(
            new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).once();
        IStreamSourceHandler streamSourceHandler = createMock(IStreamSourceHandler.class);
        expect(streamSourceHandler.getCsvStreamSource(anyObject(), anyObject())).andReturn(streamSource).once();
        Whitebox.setInternalState(controller, streamSourceHandler);
        replay(scenarioService, streamSourceHandler, streamSource, productFamilyProvider);
        ScenarioReportWidget widget = (ScenarioReportWidget) controller.initWidget();
        verify(scenarioService, streamSourceHandler, streamSource, productFamilyProvider);
        verifySize(widget);
        verifyContent(widget.getContent(), scenario);
    }

    private void verifySize(ScenarioReportWidget widget) {
        assertEquals(425, widget.getWidth(), 0);
        assertEquals(-1.0, widget.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, widget.getWidthUnits());
        assertEquals(Sizeable.Unit.PIXELS, widget.getHeightUnits());
    }

    private void verifyContent(Component component, Scenario scenario) {
        assertEquals(VerticalLayout.class, component.getClass());
        VerticalLayout content = (VerticalLayout) component;
        assertEquals(2, content.getComponentCount());
        verifyScenarioCombobox(content.getComponent(0), scenario);
        verifyButtonsLayout(content.getComponent(1));
    }

    private void verifyScenarioCombobox(Component component, Scenario scenario) {
        assertEquals(ComboBox.class, component.getClass());
        ComboBox<Scenario> scenarioComboBox = (ComboBox<Scenario>) component;
        assertEquals("Scenario name", scenarioComboBox.getCaption());
        assertFalse(scenarioComboBox.isEmptySelectionAllowed());
        assertFalse(scenarioComboBox.isTextInputAllowed());
        ListDataProvider<Scenario> listDataProvider = (ListDataProvider<Scenario>) scenarioComboBox.getDataProvider();
        Collection<?> actualScenarios = listDataProvider.getItems();
        assertEquals(1, actualScenarios.size());
        assertEquals(scenario, actualScenarios.iterator().next());
        assertEquals(scenario.getName(), scenarioComboBox.getItemCaptionGenerator().apply(scenario));
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        Button exportButton = verifyButton(layout.getComponent(0), "Export", 0);
        Collection<Extension> extensions = exportButton.getExtensions();
        assertTrue(CollectionUtils.isNotEmpty(extensions));
        assertEquals(1, extensions.size());
        assertTrue(extensions.iterator().next() instanceof OnDemandFileDownloader);
        verifyButton(layout.getComponent(1), "Close", 1);
    }

    private Button verifyButton(Component component, String caption, int listenerCount) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(caption, button.getId());
        assertEquals(listenerCount, button.getListeners(ClickEvent.class).size());
        return button;
    }
}
