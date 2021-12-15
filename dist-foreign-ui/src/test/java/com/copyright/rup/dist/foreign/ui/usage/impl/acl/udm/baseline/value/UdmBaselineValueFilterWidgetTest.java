package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyButtonsLayout;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Verifies {@link UdmBaselineValueFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueFilterWidgetTest {

    private UdmBaselineValueFilterWidget widget;
    private UdmBaselineValueFilterController controller;

    @Before
    public void setUp() {
        controller = createMock(UdmBaselineValueFilterController.class);
        expect(controller.getPublicationTypes()).andReturn(buildPublicationTypes()).once();
        widget = new UdmBaselineValueFilterWidget(controller);
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(2, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1), "Apply", "Clear");
        verify(controller);
    }

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(4, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyPeriodsFilterLayout(verticalLayout.getComponent(1));
        verifyPubTypeComboBox(verticalLayout.getComponent(2));
        verifyMoreFiltersButton(verticalLayout.getComponent(3));
    }

    private void verifyPeriodsFilterLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals("Periods", button.getCaption());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
    }

    @SuppressWarnings("unchecked")
    private void verifyPubTypeComboBox(Component component) {
        assertTrue(component instanceof ComboBox);
        ComboBox<?> comboBox = (ComboBox<?>) component;
        assertEquals("Pub Type", comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
        ListDataProvider<PublicationType> listDataProvider =
            (ListDataProvider<PublicationType>) comboBox.getDataProvider();
        Collection<PublicationType> actualPublicationTypes = listDataProvider.getItems();
        List<PublicationType> expectedPublicationTypes = buildPublicationTypes();
        assertEquals(expectedPublicationTypes.size(), actualPublicationTypes.size());
        assertEquals(expectedPublicationTypes, actualPublicationTypes);
    }

    private void verifyFiltersLabel(Component component) {
        assertTrue(component instanceof Label);
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
    }

    private void verifyMoreFiltersButton(Component component) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals("More Filters", component.getCaption());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
    }

    private static List<PublicationType> buildPublicationTypes() {
        PublicationType bkPubType = new PublicationType();
        bkPubType.setName("BK");
        bkPubType.setDescription("Book");
        PublicationType nlPubType = new PublicationType();
        nlPubType.setName("NL");
        nlPubType.setDescription("Newsletter");
        PublicationType npPubType = new PublicationType();
        npPubType.setName("NP");
        npPubType.setDescription("Newspaper");
        return Arrays.asList(bkPubType, nlPubType, npPubType);
    }
}
