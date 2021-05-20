package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.repository.api.IUdmTypeOfUseRepository;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Verifies {@link UdmTypeOfUseService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/2021
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class UdmTypeOfUseServiceTest {

    private final UdmTypeOfUseService udmTypeOfUseService = new UdmTypeOfUseService();
    private IUdmTypeOfUseRepository udmTypeOfUseRepository;

    @Before
    public void setUp() {
        udmTypeOfUseRepository = createMock(IUdmTypeOfUseRepository.class);
        Whitebox.setInternalState(udmTypeOfUseService, udmTypeOfUseRepository);
    }

    @Test
    public void testGetAllUdmTous() {
        List<String> udmTous = Arrays.asList("UDM_TOU_1", "UDM_TOU_2", "UDM_TOU_3", "UDM_TOU_5", "UDM_TOU_6");
        expect(udmTypeOfUseRepository.findAllUdmTous()).andReturn(udmTous).once();
        replay(udmTypeOfUseRepository);
        assertEquals(udmTous, udmTypeOfUseService.getAllUdmTous());
        verify(udmTypeOfUseRepository);
    }

    @Test
    public void testGetUdmTouToRmsTouMap() {
        Map<String, String> udmTousToRmsTous = ImmutableMap.of("UDM_TOU_1", "PRINT", "UDM_TOU_2", "DIGITAL");
        expect(udmTypeOfUseRepository.findUdmTouToRmsTouMap()).andReturn(udmTousToRmsTous).once();
        replay(udmTypeOfUseRepository);
        assertEquals(udmTousToRmsTous, udmTypeOfUseService.getUdmTouToRmsTouMap());
        verify(udmTypeOfUseRepository);
    }
}
