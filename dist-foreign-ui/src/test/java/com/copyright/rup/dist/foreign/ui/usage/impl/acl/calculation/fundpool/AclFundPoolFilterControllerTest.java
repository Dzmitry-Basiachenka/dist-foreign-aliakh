package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclFundPoolFilterController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/18/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolFilterControllerTest {

    private static final String ACL_PRODUCT_FAMILY = "ACL";
    private final AclFundPoolFilterController controller = new AclFundPoolFilterController();

    private IAclFundPoolService fundPoolService;
    private ILicenseeClassService licenseeClassService;

    @Before
    public void setUp() {
        fundPoolService = createMock(IAclFundPoolService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        Whitebox.setInternalState(controller, fundPoolService);
        Whitebox.setInternalState(controller, licenseeClassService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(AclFundPoolFilterWidget.class));
    }

    @Test
    public void testGetFundPoolNames() {
        List<AclFundPool> aclFundPools = Collections.singletonList(new AclFundPool());
        expect(fundPoolService.getAll()).andReturn(aclFundPools).once();
        replay(fundPoolService);
        assertSame(aclFundPools, controller.getFundPoolNames());
        verify(fundPoolService);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Collections.singletonList(202212);
        expect(fundPoolService.getPeriods()).andReturn(periods).once();
        replay(fundPoolService);
        assertSame(periods, controller.getPeriods());
        verify(fundPoolService);
    }

    @Test
    public void testGetDetailLicenseeClasses() {
        List<DetailLicenseeClass> licenseeClasses = Collections.singletonList(new DetailLicenseeClass());
        expect(licenseeClassService.getDetailLicenseeClasses(ACL_PRODUCT_FAMILY)).andReturn(licenseeClasses).once();
        replay(licenseeClassService);
        assertSame(licenseeClasses, controller.getDetailLicenseeClasses());
        verify(licenseeClassService);
    }

    @Test
    public void testGetAggregateLicenseeClasses() {
        List<AggregateLicenseeClass> licenseeClasses = Collections.singletonList(new AggregateLicenseeClass());
        expect(licenseeClassService.getAggregateLicenseeClasses(ACL_PRODUCT_FAMILY)).andReturn(licenseeClasses).once();
        replay(licenseeClassService);
        assertSame(licenseeClasses, controller.getAggregateLicenseeClasses());
        verify(licenseeClassService);
    }
}
