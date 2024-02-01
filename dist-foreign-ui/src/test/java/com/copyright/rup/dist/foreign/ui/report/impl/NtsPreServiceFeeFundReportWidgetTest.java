package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Extension;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * Verifies {@link NtsPreServiceFeeFundReportWidget}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/01/2024
 *
 * @author Dzmitry Basiachenka
 */
public class NtsPreServiceFeeFundReportWidgetTest {

    @Test
    public void testInit() {
        var controller = new NtsPreServiceFeeFundReportController();
        IFundPoolService fundPoolService = createMock(IFundPoolService.class);
        var fundPool = new FundPool();
        fundPool.setId("78952e37-e902-4ce6-8b6f-618e58a22bca");
        fundPool.setProductFamily("NTS");
        fundPool.setName("Fund name");
        Whitebox.setInternalState(controller, fundPoolService);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(fundPoolService.getFundPools("NTS")).andReturn(List.of(fundPool)).once();
        expect(streamSource.getSource()).andReturn(
            new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).once();
        IStreamSourceHandler streamSourceHandler = createMock(IStreamSourceHandler.class);
        expect(streamSourceHandler.getCsvStreamSource(anyObject(), anyObject())).andReturn(streamSource).once();
        Whitebox.setInternalState(controller, streamSourceHandler);
        replay(fundPoolService, streamSourceHandler, streamSource);
        var widget = (NtsPreServiceFeeFundReportWidget) controller.initWidget();
        verify(fundPoolService, streamSourceHandler, streamSource);
        verifyWindow(widget, StringUtils.EMPTY, 450, -1, Unit.PIXELS);
        verifyContent(widget.getContent(), fundPool);
    }

    private void verifyContent(Component component, FundPool fundPool) {
        assertEquals(VerticalLayout.class, component.getClass());
        var content = (VerticalLayout) component;
        assertEquals(2, content.getComponentCount());
        verifyFundPoolComboBox(content.getComponent(0), fundPool);
        verifyButtonsLayout(content.getComponent(1));
    }

    private void verifyFundPoolComboBox(Component component, FundPool fundPool) {
        assertEquals(ComboBox.class, component.getClass());
        ComboBox<FundPool> fundPoolComboBox = (ComboBox<FundPool>) component;
        assertEquals("Fund Name", fundPoolComboBox.getCaption());
        assertEquals(100, fundPoolComboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, fundPoolComboBox.getWidthUnits());
        assertFalse(fundPoolComboBox.isEmptySelectionAllowed());
        assertFalse(fundPoolComboBox.isTextInputAllowed());
        ListDataProvider<FundPool> listDataProvider = (ListDataProvider<FundPool>) fundPoolComboBox.getDataProvider();
        Collection<?> actualFundPools = listDataProvider.getItems();
        assertEquals(1, actualFundPools.size());
        assertEquals(fundPool, actualFundPools.iterator().next());
        assertEquals(fundPool.getName(), fundPoolComboBox.getItemCaptionGenerator().apply(fundPool));
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        var exportButton = verifyButton(layout.getComponent(0), "Export", true);
        Collection<Extension> extensions = exportButton.getExtensions();
        assertTrue(CollectionUtils.isNotEmpty(extensions));
        assertEquals(1, extensions.size());
        assertThat(extensions.iterator().next(), instanceOf(OnDemandFileDownloader.class));
        verifyButton(layout.getComponent(1), "Close", true);
    }
}
