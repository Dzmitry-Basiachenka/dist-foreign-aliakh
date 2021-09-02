package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

    private IUdmTypeOfUseService udmTypeOfUseService;
    private ILicenseeClassService licenseeClassService;

    @Before
    public void setUp() {
        udmTypeOfUseService = createMock(IUdmTypeOfUseService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
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
        assertEquals(licenseeClasses, controller.getDetailLicenseeClasses());
        verify(licenseeClassService);
    }

    @Test
    public void testGetAggregateLicenseeClasses() {
        List<AggregateLicenseeClass> licenseeClasses = Collections.singletonList(new AggregateLicenseeClass());
        expect(licenseeClassService.getAggregateLicenseeClasses("ACL")).andReturn(licenseeClasses).once();
        replay(licenseeClassService);
        assertEquals(licenseeClasses, controller.getAggregateLicenseeClasses());
        verify(licenseeClassService);
    }

    @Test
    public void testGetTypeOfUses() {
        List<String> typeOfUses = Collections.singletonList("PRINT_COPIES");
        expect(udmTypeOfUseService.getAllUdmTous()).andReturn(typeOfUses).once();
        replay(udmTypeOfUseService);
        assertEquals(typeOfUses, controller.getTypeOfUses());
        verify(udmTypeOfUseService);
    }
}
