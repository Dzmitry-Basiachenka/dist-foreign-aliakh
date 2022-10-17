package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmBaselineFilterController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmBaselineFilterControllerTest {

    private final UdmBaselineFilterController controller = new UdmBaselineFilterController();

    private IUdmBaselineService udmBaselineService;
    private IUdmTypeOfUseService udmTypeOfUseService;
    private ILicenseeClassService licenseeClassService;

    @Before
    public void setUp() {
        udmBaselineService = createMock(IUdmBaselineService.class);
        udmTypeOfUseService = createMock(IUdmTypeOfUseService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        Whitebox.setInternalState(controller, udmBaselineService);
        Whitebox.setInternalState(controller, udmTypeOfUseService);
        Whitebox.setInternalState(controller, licenseeClassService);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testGetDetailLicenseeClasses() {
        List<DetailLicenseeClass> licenseeClasses = Collections.singletonList(new DetailLicenseeClass());
        expect(licenseeClassService.getDetailLicenseeClasses("ACL")).andReturn(licenseeClasses).once();
        replay(licenseeClassService);
        assertSame(licenseeClasses, controller.getDetailLicenseeClasses());
        verify(licenseeClassService);
    }

    @Test
    public void testGetAggregateLicenseeClasses() {
        List<AggregateLicenseeClass> licenseeClasses = Collections.singletonList(new AggregateLicenseeClass());
        expect(licenseeClassService.getAggregateLicenseeClasses("ACL")).andReturn(licenseeClasses).once();
        replay(licenseeClassService);
        assertSame(licenseeClasses, controller.getAggregateLicenseeClasses());
        verify(licenseeClassService);
    }

    @Test
    public void testGetReportedTypeOfUses() {
        List<String> reportedTypeOfUses = Collections.singletonList("PRINT_COPIES");
        expect(udmTypeOfUseService.getAllUdmTous()).andReturn(reportedTypeOfUses).once();
        replay(udmTypeOfUseService);
        assertSame(reportedTypeOfUses, controller.getReportedTypeOfUses());
        verify(udmTypeOfUseService);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Arrays.asList(202012, 201906);
        expect(udmBaselineService.getPeriods()).andReturn(periods).once();
        replay(udmBaselineService);
        assertSame(periods, controller.getPeriods());
        verify(udmBaselineService);
    }
}
