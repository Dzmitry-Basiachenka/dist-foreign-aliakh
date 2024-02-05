package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.reset;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IReconcileRightsholdersController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Verifies {@link RightsholderDiscrepanciesWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/25/18
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UI.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class RightsholderDiscrepanciesWindowTest {

    private final List<RightsholderDiscrepancy> rightsholderDiscrepancies =
        loadExpectedRightsholderDiscrepancy("rightsholder_discrepancy_123456789.json");

    private RightsholderDiscrepanciesWindow window;
    private IReconcileRightsholdersController controller;


    @Before
    public void setUp() throws Exception {
        controller = createMock(IReconcileRightsholdersController.class);
        IFasScenariosController scenariosController = createMock(IFasScenariosController.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> fileSource =
            new SimpleImmutableEntry<>(() -> "file.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(fileSource).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource);
        replay(controller, streamSource, ui, UI.class);
        window = new RightsholderDiscrepanciesWindow(controller, scenariosController);
        verify(controller, streamSource, ui, UI.class);
        reset(controller, streamSource);
    }

    @Test
    public void testConstructor() {
        assertEquals("Reconcile Rightsholders", window.getHeaderTitle());
        assertEquals("1100px", window.getWidth());
        assertEquals("530px", window.getHeight());
        VerticalLayout content = (VerticalLayout) UiTestHelper.getDialogContent(window);
        assertEquals(1, content.getComponentCount());
        assertThat(content.getComponentAt(0), instanceOf(Grid.class));
        Grid grid = (Grid) content.getComponentAt(0);
        assertEquals("rightsholder-discrepancies-grid", grid.getId().orElseThrow());
        verifyGrid(grid, List.of(
            Pair.of("RH Account #", "135px"),
            Pair.of("RH Name", "300px"),
            Pair.of("New RH Account #", "170px"),
            Pair.of("New RH Name", "300px"),
            Pair.of("Wr Wrk Inst", "110px"),
            Pair.of("Title", "300px")
        ));
        verifyButtonsLayout(UiTestHelper.getFooterComponent(window, 1));
    }

    @Test
    public void testGridValues() {
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(rightsholderDiscrepancies).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        replay(controller);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) UiTestHelper.getDialogContent(window)).getComponentAt(0);
        Object[][] expectedCells = {
            {"1000009522", "Zoological Society of Pakistan [T]", "2000070936", "Frances Lincoln Ltd", "123456789",
                "504 absolutely essential words"}
        };
        verifyGridItems(grid, rightsholderDiscrepancies, expectedCells);
    }

    public void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        Component fileDownloader = layout.getComponentAt(0);
        assertThat(fileDownloader, instanceOf(OnDemandFileDownloader.class));
        assertEquals("Export", ((Button) fileDownloader.getChildren().findFirst().get()).getText());
        Component approveButton = layout.getComponentAt(1);
        assertThat(approveButton, instanceOf(Button.class));
        assertEquals("Approve", ((Button) approveButton).getText());
        Component cancelButton = layout.getComponentAt(2);
        assertThat(cancelButton, instanceOf(Button.class));
        assertEquals("Cancel", ((Button) cancelButton).getText());
        assertEquals("rightsholder-discrepancies-buttons-layout", layout.getId().orElseThrow());
    }

    private List<RightsholderDiscrepancy> loadExpectedRightsholderDiscrepancy(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
