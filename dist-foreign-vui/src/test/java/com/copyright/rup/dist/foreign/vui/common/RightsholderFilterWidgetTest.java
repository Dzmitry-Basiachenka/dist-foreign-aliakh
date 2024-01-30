package com.copyright.rup.dist.foreign.vui.common;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.FilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.function.ValueProvider;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Verifies {@link RightsholderFilterWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/15/2017
 *
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class RightsholderFilterWidgetTest {

    private static final Long RH_ACCOUNT_NUMBER = 1000000001L;
    private static final String RH_NAME = "Rothchild Consultants";

    private RightsholderFilterWidget rightsholderFilterWidget;

    @Before
    public void setUp() {
        rightsholderFilterWidget =
            new RightsholderFilterWidget("Rightsholders", "Enter Rightsholder Name/Account #",
                "<span class=\"filter-field-empty-name\">Rightsholder is absent in PRM</span>",
                () -> List.of(buildRightsholder()));
    }

    @Test
    public void testLoadBeans() {
        List<Rightsholder> rightsholders = rightsholderFilterWidget.loadBeans();
        assertEquals(1, rightsholders.size());
        assertEquals(buildRightsholder(), rightsholders.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(Rightsholder.class, rightsholderFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        var rightsholder = buildRightsholder();
        assertEquals("1000000001 - Rothchild Consultants", rightsholderFilterWidget.getBeanItemCaption(rightsholder));
    }

    @Test
    public void testGetBeanItemCaptionNullName() {
        var rightsholder = buildRightsholder();
        rightsholder.setName(null);
        assertEquals("1000000001 - <span class=\"filter-field-empty-name\">Rightsholder is absent in PRM</span>",
            rightsholderFilterWidget.getBeanItemCaption(rightsholder));
    }

    @Test
    public void testOnComponentEvent() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        Set<Long> accountNumbers = Set.of(RH_ACCOUNT_NUMBER);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(accountNumbers).once();
        replay(filterSaveEvent);
        rightsholderFilterWidget.onComponentEvent(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Capture<ValueProvider<Rightsholder, List<String>>> providerCapture = newCapture();
        Windows.showFilterWindow(eq("Rightsholders filter"), same(rightsholderFilterWidget),
            capture(providerCapture));
        expectLastCall().andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Set.of());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn(Optional.empty()).once();
        filterWindow.setId("rightsholders-filter-window");
        expectLastCall().once();
        filterWindow.addClassName("rightsholders-filter-window");
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Rightsholder Name/Account #");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        rightsholderFilterWidget.showFilterWindow();
        ValueProvider<Rightsholder, List<String>> valueProvider = providerCapture.getValue();
        assertEquals(List.of(RH_NAME, RH_ACCOUNT_NUMBER.toString()), valueProvider.apply(buildRightsholder()));
        verify(filterWindow, Windows.class);
    }

    private Rightsholder buildRightsholder() {
        var rightsholder = new Rightsholder();
        rightsholder.setName(RH_NAME);
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        return rightsholder;
    }
}
