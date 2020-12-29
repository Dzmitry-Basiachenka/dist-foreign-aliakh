package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.vaadin.data.ValueProvider;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link SalLicenseeFilterWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 12/17/2020
 *
 * @author Aliaksandr Liakhn
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class SalLicenseeFilterWidgetTest {

    private SalLicenseeFilterWidget salLicenseeFilterWidget;

    @Before
    public void setUp() {
        salLicenseeFilterWidget = new SalLicenseeFilterWidget(() -> Collections.singletonList(buildLicensee()));
    }

    @Test
    public void testLoadBeans() {
        List<SalLicensee> licensees = salLicenseeFilterWidget.loadBeans();
        assertEquals(1, licensees.size());
        SalLicensee licensee = licensees.get(0);
        assertEquals(1114L, licensee.getAccountNumber().longValue());
        assertEquals("Agway, Inc.", licensee.getName());
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(SalLicensee.class, salLicenseeFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals("1114 - Agway, Inc.", salLicenseeFilterWidget.getBeanItemCaption(buildLicensee()));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent<SalLicensee> filterSaveEvent = createMock(FilterSaveEvent.class);
        Set<SalLicensee> accountNumbers = Collections.singleton(buildLicensee());
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(accountNumbers).once();
        replay(filterSaveEvent);
        salLicenseeFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow<SalLicensee> filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Capture<ValueProvider<SalLicensee, List<String>>> providerCapture = new Capture<>();
        Windows.showFilterWindow(eq("Licensees filter"), same(salLicenseeFilterWidget), capture(providerCapture));
        expectLastCall().andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(new HashSet<>());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("licensees-filter-window");
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Licensee Name/Account #");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        salLicenseeFilterWidget.showFilterWindow();
        assertEquals(Arrays.asList("Agway, Inc.", "1114"), providerCapture.getValue().apply(buildLicensee()));
        verify(filterWindow, Windows.class);
    }

    private SalLicensee buildLicensee() {
        SalLicensee licensee = new SalLicensee();
        licensee.setAccountNumber(1114L);
        licensee.setName("Agway, Inc.");
        return licensee;
    }
}
