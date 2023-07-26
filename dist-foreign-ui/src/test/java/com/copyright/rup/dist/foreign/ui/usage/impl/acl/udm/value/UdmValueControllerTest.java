package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.ExchangeRate;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.integration.rfex.api.IRfexIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmPriceTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.ui.audit.impl.UdmValueAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.ui.audit.impl.UdmValueHistoryWindow;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;

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
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link UdmValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class, Windows.class, StreamSource.class})
public class UdmValueControllerTest {

    private static final String ACL_PRODUCT_FAMILY = "ACL";
    private static final OffsetDateTime DATE = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));

    private final UdmValueController controller = new UdmValueController();
    private final UdmValueFilter udmValueFilter = new UdmValueFilter();
    private IUdmValueFilterController udmValueFilterController;
    private IUdmValueFilterWidget udmValueFilterWidget;
    private IUdmValueService valueService;
    private IUdmValueAuditService udmValueAuditService;
    private IUdmProxyValueService udmProxyValueService;
    private IUdmValueWidget udmValueWidget;
    private IUdmBaselineService baselineService;
    private IPublicationTypeService publicationTypeService;
    private IUdmPriceTypeService udmPriceTypeService;
    private IRfexIntegrationService rfexIntegrationService;
    private IUdmReportService udmReportService;
    private IStreamSourceHandler streamSourceHandler;

    @Before
    public void setUp() {
        udmValueFilterController = createMock(IUdmValueFilterController.class);
        udmValueFilterWidget = createMock(IUdmValueFilterWidget.class);
        valueService = createMock(IUdmValueService.class);
        udmValueAuditService = createMock(IUdmValueAuditService.class);
        udmProxyValueService = createMock(IUdmProxyValueService.class);
        udmValueWidget = createMock(IUdmValueWidget.class);
        baselineService = createMock(IUdmBaselineService.class);
        publicationTypeService = createMock(IPublicationTypeService.class);
        udmPriceTypeService = createMock(IUdmPriceTypeService.class);
        rfexIntegrationService = createMock(IRfexIntegrationService.class);
        udmReportService = createMock(IUdmReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, udmValueFilterController);
        Whitebox.setInternalState(controller, udmValueFilterWidget);
        Whitebox.setInternalState(controller, valueService);
        Whitebox.setInternalState(controller, udmValueAuditService);
        Whitebox.setInternalState(controller, udmProxyValueService);
        Whitebox.setInternalState(controller, udmValueWidget);
        Whitebox.setInternalState(controller, baselineService);
        Whitebox.setInternalState(controller, publicationTypeService);
        Whitebox.setInternalState(controller, udmPriceTypeService);
        Whitebox.setInternalState(controller, rfexIntegrationService);
        Whitebox.setInternalState(controller, udmReportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
    }

    @Test
    public void testGetBeansCount() {
        expect(udmValueFilterController.getWidget()).andReturn(udmValueFilterWidget).once();
        expect(udmValueFilterWidget.getAppliedFilter()).andReturn(udmValueFilter).once();
        expect(valueService.getValueCount(udmValueFilter)).andReturn(10).once();
        replay(udmValueFilterController, udmValueFilterWidget, valueService, udmValueWidget);
        assertEquals(10, controller.getBeansCount());
        verify(udmValueFilterController, udmValueFilterWidget, valueService, udmValueWidget);
    }

    @Test
    public void testLoadBeans() {
        List<UdmValueDto> udmValues = List.of(new UdmValueDto());
        Capture<Pageable> pageableCapture = newCapture();
        expect(udmValueFilterController.getWidget()).andReturn(udmValueFilterWidget).once();
        expect(udmValueFilterWidget.getAppliedFilter()).andReturn(udmValueFilter).once();
        expect(valueService.getValueDtos(eq(udmValueFilter), capture(pageableCapture), isNull()))
            .andReturn(udmValues).once();
        replay(udmValueFilterController, udmValueFilterWidget, valueService, udmValueWidget);
        assertSame(udmValues, controller.loadBeans(0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(udmValueFilterController, udmValueFilterWidget, valueService, udmValueWidget);
    }

    @Test
    public void testInstantiateWidget() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasManagerPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(false).once();
        replay(ForeignSecurityUtils.class);
        IUdmValueWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(UdmValueWidget.class, widget.getClass());
    }

    @Test
    public void testGetBaselinePeriods() {
        List<Integer> periods = List.of(200212, 201912);
        expect(baselineService.getPeriods()).andReturn(periods).once();
        replay(baselineService);
        assertEquals(periods, controller.getBaselinePeriods());
        verify(baselineService);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = List.of(200212, 201912);
        expect(valueService.getPeriods()).andReturn(periods).once();
        replay(valueService);
        assertEquals(periods, controller.getPeriods());
        verify(valueService);
    }

    @Test
    public void testAssignValues() {
        Set<UdmValueDto> udmValues = Set.of(new UdmValueDto());
        valueService.assignValues(udmValues);
        expectLastCall().once();
        replay(valueService);
        controller.assignValues(udmValues);
        verify(valueService);
    }

    @Test
    public void testUnassignValues() {
        Set<UdmValueDto> udmValues = Set.of(new UdmValueDto());
        valueService.unassignValues(udmValues);
        expectLastCall().once();
        replay(valueService);
        controller.unassignValues(udmValues);
        verify(valueService);
    }

    @Test
    public void testUpdateValue() {
        UdmValueDto udmValueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValueChangesMap = new UdmValueAuditFieldToValuesMap(udmValueDto);
        valueService.updateValue(udmValueDto, fieldToValueChangesMap.getActionReasons());
        expectLastCall().once();
        replay(valueService);
        controller.updateValue(udmValueDto, fieldToValueChangesMap.getActionReasons());
        verify(valueService);
    }

    @Test
    public void testPopulatesValueBatch() {
        expect(valueService.populateValueBatch(202019)).andReturn(1).once();
        replay(valueService);
        assertEquals(1, controller.populatesValueBatch(202019));
        verify(valueService);
    }

    @Test
    public void testIsAllowedForPublishingTrue() {
        expect(valueService.isAllowedForPublishing(202106)).andReturn(true).once();
        replay(valueService);
        assertTrue(controller.isAllowedForPublishing(202106));
        verify(valueService);
    }

    @Test
    public void testIsAllowedForPublishingFalse() {
        expect(valueService.isAllowedForPublishing(202106)).andReturn(false).once();
        replay(valueService);
        assertFalse(controller.isAllowedForPublishing(202106));
        verify(valueService);
    }

    @Test
    public void testGetAllCurrencies() {
        List<Currency> currencies = List.of(new Currency("USD", "US Dollar"));
        expect(valueService.getAllCurrencies()).andReturn(currencies).once();
        replay(valueService);
        assertEquals(currencies, controller.getAllCurrencies());
        verify(valueService);
    }

    @Test
    public void testGetAllPublicationTypes() {
        List<PublicationType> pubTypes = List.of(buildPublicationType("Book", "1.00"));
        expect(publicationTypeService.getPublicationTypes(ACL_PRODUCT_FAMILY)).andReturn(pubTypes).once();
        replay(publicationTypeService);
        assertEquals(pubTypes, controller.getAllPublicationTypes());
        verify(publicationTypeService);
    }

    @Test
    public void testGetUdmRecordThreshold() {
        expect(valueService.getUdmRecordThreshold()).andReturn(10000).once();
        replay(valueService);
        assertEquals(10000, controller.getUdmRecordThreshold());
        verify(valueService);
    }

    @Test
    public void testGetAllPriceTypes() {
        List<String> priceTypes = List.of("Individual", "Institution");
        expect(udmPriceTypeService.getAllPriceTypes()).andReturn(priceTypes).once();
        replay(udmPriceTypeService);
        assertEquals(priceTypes, controller.getAllPriceTypes());
        verify(udmPriceTypeService);
    }

    @Test
    public void testGetAllPriceAccessTypes() {
        List<String> priceAccessTypes = List.of("Print", "Digital");
        expect(udmPriceTypeService.getAllPriceAccessTypes()).andReturn(priceAccessTypes).once();
        replay(udmPriceTypeService);
        assertEquals(priceAccessTypes, controller.getAllPriceAccessTypes());
        verify(udmPriceTypeService);
    }

    @Test
    public void testPublishToBaseline() {
        expect(valueService.publishToBaseline(202106)).andReturn(2).once();
        replay(valueService);
        assertEquals(2, controller.publishToBaseline(202106));
        verify(valueService);
    }

    @Test
    public void testGetExchangeRate() {
        String currencyCode = "EUR";
        LocalDate date = LocalDate.now();
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setInverseExchangeRateValue(new BigDecimal("1.1628"));
        exchangeRate.setExchangeRateUpdateDate(date);
        expect(rfexIntegrationService.getExchangeRate(currencyCode, date)).andReturn(exchangeRate).once();
        replay(rfexIntegrationService);
        assertEquals(exchangeRate, controller.getExchangeRate(currencyCode, date));
        verify(rfexIntegrationService);
    }

    @Test
    public void testCalculateProxyValues() {
        expect(udmProxyValueService.calculateProxyValues(202106)).andReturn(2).once();
        replay(udmProxyValueService);
        assertEquals(2, controller.calculateProxyValues(202106));
        verify(udmProxyValueService);
    }

    @Test
    public void testShowUdmValueHistory() {
        mockStatic(Windows.class);
        Window.CloseListener closeListener = createMock(Window.CloseListener.class);
        Capture<UdmValueHistoryWindow> windowCapture = newCapture();
        String udmValueId = "432320b8-5029-47dd-8137-99007cb69bf1";
        List<UdmValueAuditItem> auditItems = List.of();
        expect(udmValueAuditService.getUdmValueAudit(udmValueId)).andReturn(auditItems).once();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(Windows.class, closeListener, udmValueAuditService);
        controller.showUdmValueHistory(udmValueId, closeListener);
        UdmValueHistoryWindow window = windowCapture.getValue();
        assertNotNull(window);
        assertEquals("History for UDM value 432320b8-5029-47dd-8137-99007cb69bf1", window.getCaption());
        verify(Windows.class, closeListener, udmValueAuditService);
    }

    @Test
    public void testGetExportValuesStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "export_udm_value_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(udmValueFilterController.getWidget()).andReturn(udmValueFilterWidget).once();
        expect(udmValueFilterWidget.getAppliedFilter()).andReturn(udmValueFilter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        udmReportService.writeUdmValuesCsvReport(udmValueFilter, pos);
        replay(OffsetDateTime.class, udmValueFilterWidget, udmValueFilterController, streamSourceHandler, valueService,
            udmReportService);
        IStreamSource streamSource = controller.getExportValuesStreamSource();
        assertEquals("export_udm_value_01_02_2021_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        assertNotNull(posConsumer);
        verify(OffsetDateTime.class, udmValueFilterWidget, udmValueFilterController, streamSourceHandler, valueService,
            udmReportService);
    }

    private PublicationType buildPublicationType(String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
