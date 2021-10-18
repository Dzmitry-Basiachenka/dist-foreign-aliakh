package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.repository.api.IUdmPriceTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link UdmPriceTypeService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/18/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmPriceTypeServiceTest {

    private final UdmPriceTypeService udmPriceTypeService = new UdmPriceTypeService();
    private IUdmPriceTypeRepository udmPriceTypeRepository;

    @Before
    public void setUp() {
        udmPriceTypeRepository = createMock(IUdmPriceTypeRepository.class);
        Whitebox.setInternalState(udmPriceTypeService, udmPriceTypeRepository);
    }

    @Test
    public void testGetAllPriceTypes() {
        List<String> priceTypes = Arrays.asList("Individual", "Institution");
        expect(udmPriceTypeRepository.findAllPriceTypes()).andReturn(priceTypes).once();
        replay(udmPriceTypeRepository);
        assertEquals(priceTypes, udmPriceTypeService.getAllPriceTypes());
        verify(udmPriceTypeRepository);
    }

    @Test
    public void testGetAllPriceAccessTypes() {
        List<String> priceAccessTypes = Arrays.asList("Print", "Digital");
        expect(udmPriceTypeRepository.findAllPriceAccessTypes()).andReturn(priceAccessTypes).once();
        replay(udmPriceTypeRepository);
        assertEquals(priceAccessTypes, udmPriceTypeService.getAllPriceAccessTypes());
        verify(udmPriceTypeRepository);
    }
}
