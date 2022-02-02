package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclGrantDetailController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailControllerTest {

    private static final String GRANT_SET_NAME = "Grant Set Name";

    private final AclGrantDetailController controller = new AclGrantDetailController();

    private IUdmBaselineService udmBaselineService;
    private IAclGrantSetService aclGrantSetService;
    private IAclGrantDetailService aclGrantDetailService;
    private IAclGrantDetailFilterController aclGrantDetailFilterController;
    private IAclGrantDetailWidget aclGrantDetailWidget;

    @Before
    public void setUp() {
        udmBaselineService = createMock(IUdmBaselineService.class);
        aclGrantSetService = createMock(IAclGrantSetService.class);
        aclGrantDetailService = createMock(IAclGrantDetailService.class);
        aclGrantDetailFilterController = createMock(IAclGrantDetailFilterController.class);
        aclGrantDetailWidget = createMock(IAclGrantDetailWidget.class);
        Whitebox.setInternalState(controller, udmBaselineService);
        Whitebox.setInternalState(controller, aclGrantSetService);
        Whitebox.setInternalState(controller, aclGrantDetailFilterController);
        Whitebox.setInternalState(controller, aclGrantDetailService);
    }

    @Test
    public void testGetBeansCount() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        IAclGrantDetailFilterWidget aclGrantDetailFilterWidget = createMock(IAclGrantDetailFilterWidget.class);
        expect(aclGrantDetailFilterController.getWidget()).andReturn(aclGrantDetailFilterWidget).once();
        expect(aclGrantDetailFilterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(aclGrantDetailService.getCount(filter)).andReturn(1).once();
        replay(aclGrantDetailFilterController, aclGrantDetailFilterWidget, aclGrantDetailService);
        assertEquals(1, controller.getBeansCount());
        verify(aclGrantDetailFilterController, aclGrantDetailFilterWidget, aclGrantDetailService);
    }

    @Test
    public void testLoadBeans() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        List<AclGrantDetailDto> grantDetails = Collections.singletonList(new AclGrantDetailDto());
        Capture<Pageable> pageableCapture = newCapture();
        IAclGrantDetailFilterWidget aclGrantDetailFilterWidget = createMock(IAclGrantDetailFilterWidget.class);
        expect(aclGrantDetailFilterController.getWidget()).andReturn(aclGrantDetailFilterWidget).once();
        expect(aclGrantDetailFilterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(aclGrantDetailService.getDtos(eq(filter), capture(pageableCapture), isNull()))
            .andReturn(grantDetails).once();
        replay(aclGrantDetailFilterController, aclGrantDetailFilterWidget, aclGrantDetailService);
        assertSame(grantDetails, controller.loadBeans(0, 1, null));
        Pageable pageable = pageableCapture.getValue();
        assertEquals(1, pageable.getLimit());
        assertEquals(0, pageable.getOffset());
        verify(aclGrantDetailFilterController, aclGrantDetailFilterWidget, aclGrantDetailService);
    }

    @Test
    public void testInstantiateWidget() {
        aclGrantDetailWidget = controller.instantiateWidget();
        assertNotNull(aclGrantDetailWidget);
    }

    @Test
    public void testGetBaselinePeriods() {
        List<Integer> expectedPeriods = Collections.singletonList(202106);
        expect(udmBaselineService.getPeriods()).andReturn(expectedPeriods).once();
        replay(udmBaselineService);
        List<Integer> periods = controller.getBaselinePeriods();
        assertEquals(expectedPeriods, periods);
        verify(udmBaselineService);
    }

    @Test
    public void testGrantSetExist() {
        expect(aclGrantSetService.isGrantSetExist(GRANT_SET_NAME)).andReturn(true).once();
        replay(aclGrantSetService);
        assertTrue(controller.isGrantSetExist(GRANT_SET_NAME));
        verify(aclGrantSetService);
    }

    @Test
    public void testInsertAclGrantSet() {
        expect(aclGrantSetService.insert(buildAclGrantSet())).andReturn(1).once();
        replay(aclGrantSetService);
        assertEquals(1, controller.insertAclGrantSet(buildAclGrantSet()));
        verify(aclGrantSetService);
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet aclGrantSet = new AclGrantSet();
        aclGrantSet.setName(GRANT_SET_NAME);
        aclGrantSet.setGrantPeriod(202012);
        aclGrantSet.setPeriods(Collections.singleton(202112));
        aclGrantSet.setLicenseType("ACL");
        aclGrantSet.setEditable(true);
        return aclGrantSet;
    }
}
