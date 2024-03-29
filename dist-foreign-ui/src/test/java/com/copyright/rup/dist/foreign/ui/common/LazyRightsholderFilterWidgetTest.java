package com.copyright.rup.dist.foreign.ui.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link LazyRightsholderFilterWidget}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/02/18
 *
 * @author Ihar Suvorau
 */
public class LazyRightsholderFilterWidgetTest {

    private static final String CAPTION = "caption";
    private static final String ZERO_ITEMS_SELECTED_LABEL = "(0)";
    private static final String ONE_ITEMS_SELECTED_LABEL = "(1)";

    private LazyRightsholderFilterWidget itemsFilterWidget;

    @Before
    public void setUp() {
        itemsFilterWidget = new LazyRightsholderFilterWidget(CAPTION, createMock(ICommonAuditFilterController.class));
    }

    @Test
    public void testConstructor() {
        verifyLabel(itemsFilterWidget.getComponent(0));
        verifyButton(itemsFilterWidget.getComponent(1));
        assertTrue(itemsFilterWidget.isSpacing());
    }

    @Test
    public void testReset() {
        ((Label) itemsFilterWidget.getComponent(0)).setValue(ONE_ITEMS_SELECTED_LABEL);
        assertEquals(ONE_ITEMS_SELECTED_LABEL, ((Label) itemsFilterWidget.getComponent(0)).getValue());
        itemsFilterWidget.reset();
        assertEquals(ZERO_ITEMS_SELECTED_LABEL, ((Label) itemsFilterWidget.getComponent(0)).getValue());
    }

    @Test
    public void testAddClickListener() {
        ClickListener clickListener = createMock(ClickListener.class);
        assertEquals(2, ((Button) itemsFilterWidget.getComponent(1)).getListeners(ClickEvent.class).size());
        itemsFilterWidget.addClickListener(clickListener);
        assertEquals(3, ((Button) itemsFilterWidget.getComponent(1)).getListeners(ClickEvent.class).size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddFilterSaveListener() {
        IFilterSaveListener<Rightsholder> saveListener = createMock(IFilterSaveListener.class);
        assertNull(Whitebox.getInternalState(itemsFilterWidget, "saveListener"));
        itemsFilterWidget.addFilterSaveListener(saveListener);
        assertEquals(saveListener, Whitebox.getInternalState(itemsFilterWidget, "saveListener"));
    }

    private void verifyLabel(Component component) {
        UiTestHelper.verifyLabel(component, ZERO_ITEMS_SELECTED_LABEL, ContentMode.TEXT, -1.0f);
    }

    private void verifyButton(Component component) {
        assertNotNull(component);
        assertEquals(Button.class, component.getClass());
        Button button = (Button) component;
        assertTrue(StringUtils.contains(button.getStyleName(), CAPTION));
        assertTrue(StringUtils.contains(button.getStyleName(), ValoTheme.BUTTON_LINK));
        assertTrue(button.isDisableOnClick());
    }
}
