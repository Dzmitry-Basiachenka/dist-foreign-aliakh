package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.ImmutableMap;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable.Unit;
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
        verifyWindow(window, "Batches filter", 450, 400, Unit.PIXELS);
        assertEquals("batches-filter-window", window.getStyleName());
        verifyRootLayout(window.getContent());
        verify(controller);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(3, verticalLayout.getComponentCount());
        verifySearchWidget(verticalLayout.getComponent(0));
        verifyCheckBoxGroupLayout(verticalLayout.getComponent(1));
        verifyButtonsLayout(verticalLayout.getComponent(2));
    }

    private void verifySearchWidget(Component component) {
        assertThat(component, instanceOf(SearchWidget.class));
        SearchWidget searchWidget = (SearchWidget) component;
        verifySize(searchWidget);
        assertEquals("Enter Usage Batch Name",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyCheckBoxGroupLayout(Component component) {
        assertThat(component, instanceOf(Panel.class));
        Panel panel = (Panel) component;
        assertEquals(1, panel.getComponentCount());
        CheckBoxGroup checkBoxGroup = (CheckBoxGroup) panel.getContent();
        DataProvider dataProvider = checkBoxGroup.getDataProvider();
        Stream<?> stream = dataProvider.fetch(new Query<>());
        assertEquals(Collections.singletonList(buildUsageBatch()), stream.collect(Collectors.toList()));
    }

    private void verifyButtonsLayout(Component component) {
        UiTestHelper.verifyButtonsLayout(component, "Continue", "Select All", "Clear", "Close");
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        UiTestHelper.verifyButtonsVisibility(ImmutableMap.of(
                buttonsLayout.getComponent(0), true,
                buttonsLayout.getComponent(1), false,
                buttonsLayout.getComponent(2), true,
                buttonsLayout.getComponent(3), true
            )
        );
    }

    private void verifySize(Component component) {
        assertEquals((float) 100, component.getWidth(), 0);
        assertEquals((float) -1, component.getHeight(), 0);
        assertEquals(Unit.PIXELS, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName(USAGE_BATCH_NAME);
        return usageBatch;
    }
}
