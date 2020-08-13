package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderService;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Verifies {@link RightsholderService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 */
public class RightsholderServiceTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final Long ACCOUNT_NUMBER_1 = 7000813806L;
    private static final Long ACCOUNT_NUMBER_2 = 5412316485L;
    private static final String RIGHTSHOLDER_NAME = "Rightsholder Name";
    private static final String SCENARIO_ID = "b3c58636-0357-4db6-bc3f-d9a4b7d998d7";
    private IRightsholderRepository rightsholderRepository;
    private RightsholderService rightsholderService;

    @Before
    public void setUp() {
        rightsholderRepository = createMock(IRightsholderRepository.class);
        IPrmRightsholderService prmRightsholderService = createMock(IPrmRightsholderService.class);
        rightsholderService = new RightsholderService(rightsholderRepository, prmRightsholderService, null);
    }

    @Test
    public void testGetRros() {
        List<Rightsholder> rros = Collections.singletonList(new Rightsholder());
        expect(rightsholderRepository.findRros("FAS")).andReturn(rros).once();
        replay(rightsholderRepository);
        assertEquals(rros, rightsholderService.getRros("FAS"));
        verify(rightsholderRepository);
    }

    @Test
    public void testUpdateRightsholder() {
        Rightsholder rightsholder = buildRightsholder(ACCOUNT_NUMBER_1);
        rightsholderRepository.deleteByAccountNumber(rightsholder.getAccountNumber());
        expectLastCall().once();
        rightsholderRepository.insert(rightsholder);
        expectLastCall().once();
        replay(rightsholderRepository);
        rightsholderService.updateRightsholder(rightsholder);
        verify(rightsholderRepository);
    }

    @Test
    public void testGetFromUsages() {
        Pageable pageable = new Pageable(0, 10);
        expect(rightsholderRepository.findFromUsages(FAS_PRODUCT_FAMILY, "10001", pageable, null))
            .andReturn(Collections.emptyList()).once();
        replay(rightsholderRepository);
        assertEquals(Collections.emptyList(),
            rightsholderService.getFromUsages(FAS_PRODUCT_FAMILY, "10001", pageable, null));
        verify(rightsholderRepository);
    }

    @Test
    public void testGetCountFromUsages() {
        expect(rightsholderRepository.findCountFromUsages(FAS_PRODUCT_FAMILY, "Rightsholder"))
            .andReturn(5).once();
        replay(rightsholderRepository);
        assertEquals(5, rightsholderService.getCountFromUsages(FAS_PRODUCT_FAMILY, "Rightsholder"));
        verify(rightsholderRepository);
    }

    @Test
    public void testUpdateRighstholdersAsync() {
        ExecutorService executorService = createMock(ExecutorService.class);
        Whitebox.setInternalState(rightsholderService, executorService);
        Capture<Runnable> runnableCapture = new Capture<>();
        Set<Long> accountNumbers = Collections.singleton(ACCOUNT_NUMBER_2);
        executorService.execute(capture(runnableCapture));
        expectLastCall().once();
        replay(executorService);
        rightsholderService.updateRighstholdersAsync(accountNumbers);
        assertNotNull(runnableCapture.getValue());
        verify(executorService);
    }

    @Test
    public void testUpdateUsagesPayeesAsync() {
        ExecutorService executorService = createMock(ExecutorService.class);
        Whitebox.setInternalState(rightsholderService, executorService);
        Capture<Runnable> runnableCapture = new Capture<>();
        Usage usage = new Usage();
        usage.getPayee().setAccountNumber(ACCOUNT_NUMBER_2);
        List<Usage> usages = Collections.singletonList(usage);
        executorService.execute(capture(runnableCapture));
        expectLastCall().once();
        replay(executorService);
        rightsholderService.updateUsagesPayeesAsync(usages);
        assertNotNull(runnableCapture.getValue());
        verify(executorService);
    }

    @Test
    public void testGetByScenarioId() {
        List<Rightsholder> rightsholders =
            Arrays.asList(buildRightsholder(7000813806L), buildRightsholder(1000009522L));
        String scenarioId = RupPersistUtils.generateUuid();
        expect(rightsholderRepository.findByScenarioId(scenarioId)).andReturn(rightsholders).once();
        replay(rightsholderRepository);
        assertEquals(rightsholders, rightsholderService.getByScenarioId(scenarioId));
        verify(rightsholderRepository);
    }

    @Test
    public void testGetRightsholdersByScenarioId() {
        expect(rightsholderRepository.findRhPayeePairByScenarioId(SCENARIO_ID)).andReturn(Collections.emptyList())
            .once();
        PowerMock.replay(rightsholderRepository);
        assertEquals(Collections.emptyList(), rightsholderService.getRhPayeePairByScenarioId(SCENARIO_ID));
        PowerMock.verify(rightsholderRepository);
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(RIGHTSHOLDER_NAME);
        return rightsholder;
    }
}
