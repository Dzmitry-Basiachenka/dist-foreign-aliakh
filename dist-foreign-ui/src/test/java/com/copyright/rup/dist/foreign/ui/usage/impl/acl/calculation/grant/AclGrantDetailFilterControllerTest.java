package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclGrantDetailFilterController}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 01/28/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailFilterControllerTest {

    private final AclGrantDetailFilterController controller = new AclGrantDetailFilterController();

    private IAclGrantSetService aclGrantSetService;

    @Before
    public void setUp() {
        aclGrantSetService = createMock(IAclGrantSetService.class);
        Whitebox.setInternalState(controller, aclGrantSetService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(AclGrantDetailFilterWidget.class));
    }

    @Test
    public void testGetAllAclGrantSets() {
        List<AclGrantSet> grantSets = Collections.singletonList(new AclGrantSet());
        expect(aclGrantSetService.getAll()).andReturn(grantSets).once();
        replay(aclGrantSetService);
        assertSame(grantSets, controller.getAllAclGrantSets());
        verify(aclGrantSetService);
    }

    @Test
    public void testGetGrantPeriods() {
        List<Integer> grantPeriods = Arrays.asList(202212, 202206, 202112);
        expect(aclGrantSetService.getGrantPeriods()).andReturn(grantPeriods).once();
        replay(aclGrantSetService);
        assertSame(grantPeriods, controller.getGrantPeriods());
        verify(aclGrantSetService);
    }
}
