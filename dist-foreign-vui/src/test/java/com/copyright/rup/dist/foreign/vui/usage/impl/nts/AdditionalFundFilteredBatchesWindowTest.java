package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyFileDownloader;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IAdditionalFundBatchesFilterWindow;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Verifies {@link AdditionalFundFilteredBatchesWindow}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/29/2019
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UI.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class AdditionalFundFilteredBatchesWindowTest {

    @Test
    public void testConstructor() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(source).once();
        INtsUsageController controller = createMock(INtsUsageController.class);
        var usageBatches = buildUsageBatches();
        expect(controller.getAdditionalFundBatchesStreamSource(usageBatches, new BigDecimal("3580246.79")))
            .andReturn(streamSource).once();
        replay(UI.class, ui, controller, streamSource);
        var window = new AdditionalFundFilteredBatchesWindow(controller, usageBatches,
            createMock(IAdditionalFundBatchesFilterWindow.class));
        verify(UI.class, ui, controller, streamSource);
        verifyWindow(window, "Filtered batches", "700px", "400px", Unit.PIXELS, true);
        verifyRootLayout(getDialogContent(window));
        verifyButtonsLayout(getFooterLayout(window));
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var rootLayout = (VerticalLayout) component;
        assertEquals(1, rootLayout.getComponentCount());
        var grid = (Grid) rootLayout.getComponentAt(0);
        verifyGrid(grid, List.of(
            Pair.of("Usage Batch Name", null),
            Pair.of("Gross NTS Withdrawn Amount", "200px")
        ));
        Object[][] expectedCells = {
            {"Usage Batch 1", "1,234,567.89"},
            {"Usage Batch 2", "2,345,678.90"}
        };
        verifyGridItems(grid, buildUsageBatches(), expectedCells);
        verifyFooter(grid);
    }

    private void verifyFooter(Grid<?> grid) {
        var footerRow = grid.getFooterRows().get(0);
        var totalCell = footerRow.getCell(grid.getColumnByKey("name"));
        assertEquals("Total", totalCell.getText());
        var amountCell = footerRow.getCell(grid.getColumnByKey("grossAmount"));
        assertEquals("3,580,246.79", amountCell.getText());
    }

    private void verifyButtonsLayout(HorizontalLayout buttonsLayout) {
        assertEquals(3, buttonsLayout.getComponentCount());
        verifyFileDownloader(buttonsLayout.getComponentAt(0), "Export", true, true);
        verifyButton(buttonsLayout.getComponentAt(1), "Continue", true, true);
        verifyButton(buttonsLayout.getComponentAt(2), "Cancel", true, true);
    }

    private List<UsageBatch> buildUsageBatches() {
        return List.of(buildUsageBatch("Usage Batch 1", new BigDecimal("1234567.89")),
            buildUsageBatch("Usage Batch 2", new BigDecimal("2345678.90")));
    }

    private UsageBatch buildUsageBatch(String name, BigDecimal grossAmount) {
        var usageBatch = new UsageBatch();
        usageBatch.setName(name);
        usageBatch.setGrossAmount(grossAmount);
        return usageBatch;
    }
}
