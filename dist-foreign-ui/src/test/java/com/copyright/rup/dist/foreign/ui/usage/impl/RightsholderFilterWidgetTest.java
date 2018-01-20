package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link RightsholderFilterWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/15/17
 *
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class RightsholderFilterWidgetTest {

    private static final Long ACCOUNT_NUMBER = 12345678L;
    private static final String RIGHTSHOLDER_NAME = "Rightsholder";

    private RightsholderFilterWidget rightsholderFilterWidget;
    private Rightsholder rightsholder;

    @Before
    public void setUp() {
        rightsholder = buildRightsholder();
        rightsholderFilterWidget =
            new RightsholderFilterWidget("RROs", () -> Lists.newArrayList(rightsholder));
    }

    @Test
    public void testLoadBeans() {
        List<Rightsholder> rightsholders = rightsholderFilterWidget.loadBeans();
        assertEquals(1, rightsholders.size());
        assertEquals(rightsholder, rightsholders.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(Rightsholder.class, rightsholderFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals("12345678 - Rightsholder", rightsholderFilterWidget.getBeanItemCaption(rightsholder));
    }

    @Test
    public void testGetBeanItemCaptionNullName() {
        rightsholder.setName(null);
        assertEquals("12345678 - <span class=\"filter-field-empty-name\">RRO is absent in PRM</span>",
            rightsholderFilterWidget.getBeanItemCaption(rightsholder));
    }

    @Test
    public void testGetIdForBean() {
        assertEquals(ACCOUNT_NUMBER, rightsholderFilterWidget.getIdForBean(rightsholder));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent<Long> filterSaveEvent = createMock(FilterSaveEvent.class);
        Set accountNumbers = Sets.newHashSet(ACCOUNT_NUMBER);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(accountNumbers).once();
        replay(filterSaveEvent);
        rightsholderFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow<Long, Rightsholder> filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Windows.showFilterWindow("RROs filter", rightsholderFilterWidget, "name", "accountNumber");
        expectLastCall().andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(null);
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("rightsholders-filter-window");
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Rightsholder Name/Account #");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        rightsholderFilterWidget.showFilterWindow();
        verify(filterWindow, Windows.class);
    }

    private Rightsholder buildRightsholder() {
        Rightsholder newRightsholder = new Rightsholder();
        newRightsholder.setAccountNumber(ACCOUNT_NUMBER);
        newRightsholder.setName(RIGHTSHOLDER_NAME);
        return newRightsholder;
    }
}
