package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link RightsholderService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 */
public class RightsholderServiceTest {

    @Test
    public void testGetRros() {
        IRightsholderRepository rightsholderRepository = createMock(IRightsholderRepository.class);
        List<Rightsholder> rros = Collections.singletonList(new Rightsholder());
        RightsholderService rightsholderService = new RightsholderService();
        Whitebox.setInternalState(rightsholderService, rightsholderRepository);
        expect(rightsholderRepository.findRros()).andReturn(rros).once();
        replay(rightsholderRepository);
        assertEquals(rros, rightsholderService.getRros());
        verify(rightsholderRepository);
    }
}
