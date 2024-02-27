package com.copyright.rup.dist.foreign.vui.report.impl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link NtsPreServiceFeeFundReportWidget}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/01/2024
 *
 * @author Dzmitry Basiachenka
 */
public class NtsPreServiceFeeFundReportWidgetTest implements IVaadinComponentFinder {

    private NtsPreServiceFeeFundReportController controller;
    private IFundPoolService fundPoolService;

    @Before
    public void setUp() {
        controller = new NtsPreServiceFeeFundReportController();
        fundPoolService = createMock(IFundPoolService.class);
        IStreamSourceHandler streamSourceHandler = createMock(IStreamSourceHandler.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, fundPoolService);
        expect(streamSourceHandler.getCsvStreamSource(anyObject(), anyObject())).andReturn(streamSource).once();
    }

    @Test
    public void testInit() {
        var fundPool = new FundPool();
        fundPool.setId("78952e37-e902-4ce6-8b6f-618e58a22bca");
        fundPool.setProductFamily("NTS");
        fundPool.setName("Fund name");
        expect(fundPoolService.getFundPools("NTS")).andReturn(List.of(fundPool)).once();
        replay(fundPoolService);
        var widget = (NtsPreServiceFeeFundReportWidget) controller.initWidget();
        verifyAll();
        verifyWindow(widget, StringUtils.EMPTY, "450px", null, Unit.PIXELS, false);
        verifyContent(UiTestHelper.getDialogContent(widget), fundPool);
        verifyButtonsLayout(UiTestHelper.getFooterComponent(widget, 1));
    }

    private void verifyContent(Component component, FundPool fundPool) {
        assertEquals(VerticalLayout.class, component.getClass());
        var content = (VerticalLayout) component;
        assertEquals(1, content.getComponentCount());
        verifyFundPoolComboBox(content.getComponentAt(0), fundPool);
    }

    private void verifyFundPoolComboBox(Component component, FundPool fundPool) {
        assertEquals(ComboBox.class, component.getClass());
        ComboBox<FundPool> fundPoolComboBox = (ComboBox<FundPool>) component;
        assertEquals("Fund Name", fundPoolComboBox.getLabel());
        assertEquals("100%", fundPoolComboBox.getWidth());
        assertEquals(Unit.PERCENTAGE, fundPoolComboBox.getWidthUnit().orElseThrow());
        ListDataProvider<FundPool> listDataProvider = (ListDataProvider<FundPool>) fundPoolComboBox.getDataProvider();
        Collection<?> actualFundPools = listDataProvider.getItems();
        assertEquals(1, actualFundPools.size());
        assertEquals(fundPool, actualFundPools.iterator().next());
        assertEquals(fundPool.getName(), fundPoolComboBox.getItemLabelGenerator().apply(fundPool));
    }

    public void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        var fileDownloader = layout.getComponentAt(0);
        assertThat(fileDownloader, instanceOf(OnDemandFileDownloader.class));
        assertEquals("Export", ((Button) fileDownloader.getChildren().findFirst().get()).getText());
        var closeButton = layout.getComponentAt(1);
        assertThat(closeButton, instanceOf(Button.class));
        assertEquals("Close", ((Button) closeButton).getText());
    }
}
