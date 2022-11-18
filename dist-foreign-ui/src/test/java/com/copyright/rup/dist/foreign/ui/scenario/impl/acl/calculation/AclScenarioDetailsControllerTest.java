package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclCommonScenarioDetailsController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclCommonScenarioDetailsWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Window;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link AclScenarioDetailsController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/17/2022
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AclScenarioDetailsController.class, Windows.class, OffsetDateTime.class, StreamSource.class})
public class AclScenarioDetailsControllerTest {

    private static final String SCENARIO_UID = "df42960e-9fd8-4480-95cd-3b6464022bcf";
    private static final String SEARCH_VALUE = "search";
    private static final int OFFSET = 5;
    private static final int COUNT = 10;
    private static final String SORT_PROPERTY = "detailId";
    private static final OffsetDateTime DATE = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));

    private AclScenarioDetailsController controller;
    private IAclScenarioUsageService aclScenarioUsageService;
    private IStreamSourceHandler streamSourceHandler;
    private IAclCalculationReportService aclCalculationReportService;
    private AclScenario scenario;

    @Before
    public void setUp() {
        scenario = buildAclScenario();
        controller = createPartialMock(AclScenarioDetailsController.class, "initWidget", "getWidget");
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        aclCalculationReportService = createMock(IAclCalculationReportService.class);
        aclScenarioUsageService = createMock(IAclScenarioUsageService.class);
        Whitebox.setInternalState(controller, aclScenarioUsageService);
        Whitebox.setInternalState(controller, scenario);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, aclCalculationReportService);
    }

    @Test
    public void testLoadBeans() {
        List<AclScenarioDetailDto> scenarioDetailDtos = Collections.singletonList(new AclScenarioDetailDto());
        Capture<Pageable> pageableCapture = newCapture();
        Capture<Sort> sortCapture = newCapture();
        expect(controller.getWidget()).andReturn(new AclScenarioDetailsControllerTest.WidgetMock()).once();
        expect(aclScenarioUsageService.getByScenarioId(eq(SCENARIO_UID), eq(SEARCH_VALUE), capture(pageableCapture),
            capture(sortCapture))).andReturn(scenarioDetailDtos).once();
        replay(controller, aclScenarioUsageService);
        List<AclScenarioDetailDto> actualScenarioDetailDtos = controller.loadBeans(OFFSET, COUNT,
            Collections.singletonList(new QuerySortOrder(SORT_PROPERTY, SortDirection.DESCENDING)));
        assertEquals(OFFSET, pageableCapture.getValue().getOffset());
        assertEquals(COUNT, pageableCapture.getValue().getLimit());
        assertEquals(SORT_PROPERTY, sortCapture.getValue().getProperty());
        assertEquals(Direction.DESC.getValue(), sortCapture.getValue().getDirection());
        assertEquals(scenarioDetailDtos, actualScenarioDetailDtos);
        verify(controller, aclScenarioUsageService);
    }

    @Test
    public void testGetSize() {
        expect(controller.getWidget()).andReturn(new AclScenarioDetailsControllerTest.WidgetMock()).once();
        expect(aclScenarioUsageService.getCountByScenarioId(SCENARIO_UID, SEARCH_VALUE)).andReturn(10).once();
        replay(controller, aclScenarioUsageService);
        assertEquals(10, controller.getSize());
        verify(controller, aclScenarioUsageService);
    }

    @Test
    public void testShowWidget() {
        mockStatic(Windows.class);
        WidgetMock widget = new WidgetMock();
        expect(controller.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        replay(controller, Windows.class);
        controller.showWidget(new AclScenario());
        assertEquals("Scenario Details", widget.getCaption());
        verify(controller, Windows.class);
    }

    @Test
    public void testGetExportAclScenarioDetailsStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = scenario.getName() + "_Details_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        aclCalculationReportService.writeAclScenarioDetailsCsvReport(scenario.getId(), pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, streamSourceHandler, aclCalculationReportService);
        IStreamSource streamSource = controller.getExportAclScenarioDetailsStreamSource();
        assertEquals("Scenario_name_Details_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, streamSourceHandler, aclCalculationReportService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(AclScenarioDetailsWidget.class));
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
        aclScenario.setName("Scenario name");
        return aclScenario;
    }

    private static class WidgetMock extends Window implements IAclCommonScenarioDetailsWidget {

        @Override
        public String getSearchValue() {
            return SEARCH_VALUE;
        }

        @Override
        public IAclCommonScenarioDetailsWidget init() {
            return this;
        }

        @Override
        public void setController(IAclCommonScenarioDetailsController controller) {
            // do nothing
        }
    }
}
