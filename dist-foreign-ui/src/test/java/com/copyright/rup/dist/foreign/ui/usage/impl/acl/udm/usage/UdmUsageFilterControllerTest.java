package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link UdmUsageFilterController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/04/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmUsageFilterControllerTest {

    private final UdmUsageFilterController controller = new UdmUsageFilterController();

    private IUdmBatchService udmBatchService;
    private IUdmUsageService udmUsageService;
    private IUdmTypeOfUseService udmTypeOfUseService;
    private ILicenseeClassService licenseeClassService;

    @Before
    public void setUp() {
        udmBatchService = createMock(IUdmBatchService.class);
        udmUsageService = createMock(IUdmUsageService.class);
        udmTypeOfUseService = createMock(IUdmTypeOfUseService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        Whitebox.setInternalState(controller, udmBatchService);
        Whitebox.setInternalState(controller, udmUsageService);
        Whitebox.setInternalState(controller, udmTypeOfUseService);
        Whitebox.setInternalState(controller, licenseeClassService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(UdmUsageFilterWidget.class));
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = List.of(202006);
        expect(udmUsageService.getPeriods()).andReturn(periods).once();
        replay(udmUsageService);
        assertSame(periods, controller.getPeriods());
        verify(udmUsageService);
    }

    @Test
    public void testGetUdmBatchesForFilter() {
        List<UdmBatch> udmBatches = List.of(new UdmBatch());
        expect(udmBatchService.getUdmBatches()).andReturn(udmBatches).once();
        replay(udmBatchService);
        assertSame(udmBatches, controller.getUdmBatches());
        verify(udmBatchService);
    }

    @Test
    public void testGetAssignees() {
        List<String> assignees = List.of("user@copyright.com");
        expect(udmUsageService.getAssignees()).andReturn(assignees).once();
        replay(udmUsageService);
        assertSame(assignees, controller.getAssignees());
        verify(udmUsageService);
    }

    @Test
    public void testGetPublicationFormats() {
        List<String> pubFormats = List.of("Digital");
        expect(udmUsageService.getPublicationFormats()).andReturn(pubFormats).once();
        replay(udmUsageService);
        assertSame(pubFormats, controller.getPublicationFormats());
        verify(udmUsageService);
    }

    @Test
    public void testGetPublicationTypes() {
        List<String> pubTypes = List.of("Book");
        expect(udmUsageService.getPublicationTypes()).andReturn(pubTypes).once();
        replay(udmUsageService);
        assertSame(pubTypes, controller.getPublicationTypes());
        verify(udmUsageService);
    }

    @Test
    public void testGetDetailLicenseeClasses() {
        List<DetailLicenseeClass> licenseeClasses = List.of(new DetailLicenseeClass());
        expect(licenseeClassService.getDetailLicenseeClasses("ACL")).andReturn(licenseeClasses).once();
        replay(licenseeClassService);
        assertSame(licenseeClasses, controller.getDetailLicenseeClasses());
        verify(licenseeClassService);
    }

    @Test
    public void testGetReportedTypeOfUses() {
        List<String> reportedTypeOfUses = List.of("PRINT_COPIES");
        expect(udmTypeOfUseService.getAllUdmTous()).andReturn(reportedTypeOfUses).once();
        replay(udmTypeOfUseService);
        assertSame(reportedTypeOfUses, controller.getReportedTypeOfUses());
        verify(udmTypeOfUseService);
    }

    @Test
    public void testGetAllActionReasons() {
        List<UdmActionReason> actionReasons = List.of(
            new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content"),
            new UdmActionReason("97fd8093-7f36-4a09-99f1-1bfe36a5c3f4", "Arbitrary RFA search result order"));
        expect(udmUsageService.getAllActionReasons()).andReturn(actionReasons).once();
        replay(udmUsageService);
        assertSame(actionReasons, controller.getAllActionReasons());
        verify(udmUsageService);
    }
}
