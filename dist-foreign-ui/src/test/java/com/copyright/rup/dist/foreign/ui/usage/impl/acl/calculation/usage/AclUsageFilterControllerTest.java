package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;

import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclUsageFilterController}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFilterControllerTest {

    private static final String ACL_PRODUCT_FAMILY = "ACL";
    private final AclUsageFilterController controller = new AclUsageFilterController();

    private IAclUsageBatchService aclUsageBatchService;
    private IAclUsageService aclUsageService;
    private ILicenseeClassService licenseeClassService;
    private IPublicationTypeService publicationTypeService;
    private IUdmTypeOfUseService udmTypeOfUseService;

    @Before
    public void setUp() {
        aclUsageBatchService = createMock(IAclUsageBatchService.class);
        aclUsageService = createMock(IAclUsageService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        publicationTypeService = createMock(IPublicationTypeService.class);
        udmTypeOfUseService = createMock(IUdmTypeOfUseService.class);
        Whitebox.setInternalState(controller, aclUsageBatchService);
        Whitebox.setInternalState(controller, aclUsageService);
        Whitebox.setInternalState(controller, licenseeClassService);
        Whitebox.setInternalState(controller, publicationTypeService);
        Whitebox.setInternalState(controller, udmTypeOfUseService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(AclUsageFilterWidget.class));
    }

    @Test
    public void testGetAllAclUsageBatches() {
        List<AclUsageBatch> aclUsageBatches = Collections.singletonList(new AclUsageBatch());
        expect(aclUsageBatchService.getAll()).andReturn(aclUsageBatches).once();
        replay(aclUsageBatchService);
        assertSame(aclUsageBatches, controller.getAllAclUsageBatches());
        verify(aclUsageBatchService);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Collections.singletonList(202006);
        expect(aclUsageService.getPeriods()).andReturn(periods).once();
        replay(aclUsageService);
        assertSame(periods, controller.getPeriods());
        verify(aclUsageService);
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

    @Test
    public void testGetPublicationTypes() {
        List<PublicationType> pubTypes = Collections.singletonList(buildPublicationType());
        expect(publicationTypeService.getPublicationTypes(ACL_PRODUCT_FAMILY)).andReturn(pubTypes).once();
        replay(publicationTypeService);
        assertSame(pubTypes, controller.getPublicationTypes());
        verify(publicationTypeService);
    }

    @Test
    public void testGetReportedTypeOfUses() {
        List<String> reportedTypeOfUses = Collections.singletonList("PRINT_COPIES");
        expect(udmTypeOfUseService.getAllUdmTous()).andReturn(reportedTypeOfUses).once();
        replay(udmTypeOfUseService);
        assertSame(reportedTypeOfUses, controller.getReportedTypeOfUses());
        verify(udmTypeOfUseService);
    }

    private PublicationType buildPublicationType() {
        PublicationType pubType = new PublicationType();
        pubType.setName("BK");
        pubType.setDescription("Book");
        return pubType;
    }
}
