package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailWidget;

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
    private IAclGrantDetailWidget aclGrantDetailWidget;

    @Before
    public void setUp() {
        udmBaselineService = createMock(IUdmBaselineService.class);
        aclGrantSetService = createMock(IAclGrantSetService.class);
        aclGrantDetailWidget = createMock(IAclGrantDetailWidget.class);
        Whitebox.setInternalState(controller, udmBaselineService);
        Whitebox.setInternalState(controller, aclGrantSetService);
    }

    @Test
    public void testLoadBeans() {
        // TODO {aliakh} implement for filter story
        assertEquals(Collections.emptyList(), controller.loadBeans(0, 10, null));
    }

    @Test
    public void testGetBeansCount() {
        // TODO {aliakh} implement for filter story
        assertEquals(0, controller.getBeansCount());
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
