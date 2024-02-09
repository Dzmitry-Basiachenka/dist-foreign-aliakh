package com.copyright.rup.dist.foreign.vui.common;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyItemsFilterWidget;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.LazyFilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.textfield.TextField;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link LazyRightsholderFilterWidget}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/02/18
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class LazyRightsholderFilterWidgetTest {

    private static final String CAPTION = "caption";
    private static final String ZERO_ITEMS_SELECTED_LABEL = "(0)";
    private static final String ONE_ITEMS_SELECTED_LABEL = "(1)";
    private static final Long RH_ACCOUNT_NUMBER = 1000033963L;

    private LazyRightsholderFilterWidget lazyRightsholderFilterWidget;
    private ICommonAuditFilterController controller;

    @Before
    public void setUp() {
        controller = createMock(ICommonAuditFilterController.class);
        lazyRightsholderFilterWidget = new LazyRightsholderFilterWidget(CAPTION, controller);
    }

    @Test
    public void testConstructor() {
        verifyItemsFilterWidget(lazyRightsholderFilterWidget, "caption");
    }

    @Test
    public void testLoadBeans() {
        List<Rightsholder> expectedRightsholders = List.of(buildRightsholder());
        expect(controller.loadBeans(StringUtils.EMPTY, 0, 1, List.of())).andReturn(expectedRightsholders).once();
        replay(controller);
        Collection<Rightsholder> actualRightsholders =
            lazyRightsholderFilterWidget.loadBeans(StringUtils.EMPTY, 0, 1, List.of());
        assertEquals(1, actualRightsholders.size());
        assertEquals(expectedRightsholders, actualRightsholders);
        verify(controller);
    }

    @Test
    public void testReset() {
        ((Html) lazyRightsholderFilterWidget.getComponentAt(0)).setHtmlContent("<span>(1)</span>");
        assertEquals(ONE_ITEMS_SELECTED_LABEL, ((Html) lazyRightsholderFilterWidget.getComponentAt(0)).getInnerHtml());
        lazyRightsholderFilterWidget.reset();
        assertEquals(ZERO_ITEMS_SELECTED_LABEL, ((Html) lazyRightsholderFilterWidget.getComponentAt(0)).getInnerHtml());
    }

    @Test
    public void testAddFilterSaveListener() {
        IFilterSaveListener<Rightsholder> saveListener = createMock(IFilterSaveListener.class);
        assertNull(Whitebox.getInternalState(lazyRightsholderFilterWidget, "saveListener"));
        lazyRightsholderFilterWidget.addFilterSaveListener(saveListener);
        assertEquals(saveListener, Whitebox.getInternalState(lazyRightsholderFilterWidget, "saveListener"));
    }

    @Test
    public void testOnComponentEvent() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        Set<Long> accountNumbers = Set.of(RH_ACCOUNT_NUMBER);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(accountNumbers).once();
        replay(filterSaveEvent);
        lazyRightsholderFilterWidget.onComponentEvent(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        Capture<LazyFilterWindow> lazyFilterWindowCapture = newCapture();
        Windows.showModalWindow(capture(lazyFilterWindowCapture));
        expectLastCall().once();
        replay(Windows.class);
        Set<Rightsholder> selectedItemsIds = Set.of(buildRightsholder());
        Whitebox.setInternalState(lazyRightsholderFilterWidget, selectedItemsIds);
        lazyRightsholderFilterWidget.showFilterWindow();
        var lazyFilterWindow = lazyFilterWindowCapture.getValue();
        assertEquals(selectedItemsIds, lazyFilterWindow.getSelectedItemsIds());
        assertEquals("rightsholders-filter-window", lazyFilterWindow.getId().orElseThrow());
        assertEquals("rightsholders-filter-window", lazyFilterWindow.getClassName());
        var searchWidget = Whitebox.getInternalState(lazyFilterWindow, SearchWidget.class);
        var textField = Whitebox.getInternalState(searchWidget, TextField.class);
        assertEquals("Enter Rightsholder Name/Account #", textField.getPlaceholder());
        verify(Windows.class);
    }

    private Rightsholder buildRightsholder() {
        var rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        rightsholder.setName("Alfred R. Lindesmith");
        return rightsholder;
    }
}
