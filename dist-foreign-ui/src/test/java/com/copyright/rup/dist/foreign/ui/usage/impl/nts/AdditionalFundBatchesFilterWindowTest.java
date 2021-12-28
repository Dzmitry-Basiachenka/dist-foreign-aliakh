package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Verifies {@link AdditionalFundBatchesFilterWindow}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/27/2019
 *
 * @author Aliaksandr Liakh
 */
public class AdditionalFundBatchesFilterWindowTest {

    private static final String USAGE_BATCH_ID = "0358deb3-caa3-4c4e-85cd-c353fcc8e6b7";
    private static final String USAGE_BATCH_NAME = "Copibec 24May18";

    @Test
    public void testConstructor() {
        IFilterWindowController<UsageBatch> controller = createMock(IFilterWindowController.class);
        expect(controller.loadBeans()).andReturn(Collections.singletonList(buildUsageBatch())).once();
        replay(controller);
        AdditionalFundBatchesFilterWindow window =
            new AdditionalFundBatchesFilterWindow(controller);
        assertEquals("Batches filter", window.getCaption());
        verifySize(window, Unit.PIXELS, 450, 400);
        assertEquals("batches-filter-window", window.getStyleName());
        verifyRootLayout(window.getContent());
        verify(controller);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(3, verticalLayout.getComponentCount());
        verifySearchWidget(verticalLayout.getComponent(0));
        verifyCheckBoxGroupLayout(verticalLayout.getComponent(1));
        verifyButtonsLayout(verticalLayout.getComponent(2));
    }

    private void verifySearchWidget(Component component) {
        assertTrue(component instanceof SearchWidget);
        SearchWidget searchWidget = (SearchWidget) component;
        verifySize(searchWidget, Unit.PERCENTAGE, 100, -1);
        assertEquals("Enter Usage Batch Name",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyCheckBoxGroupLayout(Component component) {
        assertTrue(component instanceof Panel);
        Panel panel = (Panel) component;
        assertEquals(1, panel.getComponentCount());
        CheckBoxGroup checkBoxGroup = (CheckBoxGroup) panel.getContent();
        DataProvider dataProvider = checkBoxGroup.getDataProvider();
        Stream<?> stream = dataProvider.fetch(new Query<>());
        assertEquals(Collections.singletonList(buildUsageBatch()), stream.collect(Collectors.toList()));
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(4, layout.getComponentCount());
        Button saveButton = verifyButton(layout.getComponent(0), "Continue");
        Button selectAllButton = verifyButton(layout.getComponent(1), "Select All");
        Button clearButton = verifyButton(layout.getComponent(2), "Clear");
        Button closeButton = verifyButton(layout.getComponent(3), "Close");
        assertTrue(saveButton.isVisible());
        assertFalse(selectAllButton.isVisible());
        assertTrue(clearButton.isVisible());
        assertTrue(closeButton.isVisible());
        assertEquals(1, saveButton.getListeners(ClickEvent.class).size());
        assertEquals(1, selectAllButton.getListeners(ClickEvent.class).size());
        assertEquals(1, clearButton.getListeners(ClickEvent.class).size());
        assertEquals(1, closeButton.getListeners(ClickEvent.class).size());
    }

    private Button verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private void verifySize(Component component, Unit widthUnit, float width, float height) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(Unit.PIXELS, component.getHeightUnits());
        assertEquals(widthUnit, component.getWidthUnits());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName(USAGE_BATCH_NAME);
        return usageBatch;
    }
}
