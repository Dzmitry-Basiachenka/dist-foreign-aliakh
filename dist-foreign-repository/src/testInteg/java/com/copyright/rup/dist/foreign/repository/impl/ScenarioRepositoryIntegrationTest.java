package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link ScenarioRepository}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 * @author Mikalai Bezmen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-sql-test-context.xml"})
@TransactionConfiguration
@Transactional
public class ScenarioRepositoryIntegrationTest {

    private static final String SCENARIO_NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String USAGE_BATCH_ID = "a5b64c3a-55d2-462e-b169-362dca6a4dd6";
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final BigDecimal NET_TOTAL = new BigDecimal("204").setScale(10);
    private static final BigDecimal SERVICE_FEE_TOTAL = new BigDecimal("96").setScale(10);
    private static final BigDecimal GROSS_TOTAL = new BigDecimal("300").setScale(10);
    private static final BigDecimal REPORTED_TOTAL = new BigDecimal("200.00");

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageBatchRepository batchRepository;

    @Test
    public void testGetCountByName() {
        assertEquals(0, scenarioRepository.getCountByName(SCENARIO_NAME));
        scenarioRepository.insert(buildScenario(SCENARIO_ID, SCENARIO_NAME));
        assertEquals(1, scenarioRepository.getCountByName(SCENARIO_NAME));
    }

    @Test
    public void testFindAll() {
        assertEquals(2, scenarioRepository.findAll().size());
        String scenarioId = RupPersistUtils.generateUuid();
        scenarioRepository.insert(buildScenario(scenarioId, SCENARIO_NAME));
        List<Scenario> scenarios = scenarioRepository.findAll();
        assertEquals(3, scenarios.size());
        verifyScenario(scenarios.get(0), scenarioId, SCENARIO_NAME, DESCRIPTION, GROSS_TOTAL, NET_TOTAL,
            SERVICE_FEE_TOTAL, REPORTED_TOTAL);
        verifyScenario(scenarios.get(1), "e27551ed-3f69-4e08-9e4f-8ac03f67595f", "Scenario name 2",
            "The description of scenario 2", new BigDecimal("20000.0000000000"), new BigDecimal("13600.0000000000"),
            new BigDecimal("6400.0000000000"), new BigDecimal("25000.00"));
        verifyScenario(scenarios.get(2), "b1f0b236-3ae9-4a60-9fab-61db84199d6f", "Scenario name",
            "The description of scenario", new BigDecimal("24000.0000000000"), new BigDecimal("16320.0000000000"),
            new BigDecimal("7680.0000000000"), new BigDecimal("18000.00"));
    }

    @Test
    public void testFindNamesByUsageBatchId() {
        List<String> scenariosNames = scenarioRepository.findNamesByUsageBatchId(USAGE_BATCH_ID);
        assertNotNull(scenariosNames);
        assertEquals(1, scenariosNames.size());
        Scenario scenario = buildScenario(SCENARIO_ID, SCENARIO_NAME);
        scenarioRepository.insert(scenario);
        usageRepository.insert(buildUsage(85695423L));
        scenariosNames = scenarioRepository.findNamesByUsageBatchId(USAGE_BATCH_ID);
        assertNotNull(scenariosNames);
        assertEquals(2, scenariosNames.size());
    }

    @Test
    public void testRemove() {
        scenarioRepository.insert(buildScenario(SCENARIO_ID, SCENARIO_NAME));
        assertEquals(1, scenarioRepository.getCountByName(SCENARIO_NAME));
        scenarioRepository.remove(SCENARIO_ID);
        assertEquals(0, scenarioRepository.getCountByName(SCENARIO_NAME));
    }

    @Test
    public void testFindSourceRros() {
        scenarioRepository.insert(buildScenario(SCENARIO_ID, SCENARIO_NAME));
        usageRepository.insert(buildUsage(65874985L));
        UsageBatch batch = buildBatch(2000017000L);
        batchRepository.insert(batch);
        Usage usage = buildUsage(25685478L);
        usage.setBatchId(batch.getId());
        usageRepository.insert(usage);
        // inserting different batch with the same RRO to verify that it will be returned only once
        batch = buildBatch(2000017000L);
        batchRepository.insert(batch);
        usage = buildUsage(75423658L);
        usage.setBatchId(batch.getId());
        usageRepository.insert(usage);
        List<Rightsholder> sourceRros = scenarioRepository.findSourceRros(SCENARIO_ID);
        assertEquals(2, sourceRros.size());
        assertTrue(sourceRros.stream().map(Rightsholder::getAccountNumber).collect(Collectors.toSet()).containsAll(
            Sets.newHashSet(2000017010L, 2000017000L)));
    }

    @Test
    public void testFindRightsholdersByScenarioAndSourceRro() {
        scenarioRepository.insert(buildScenario(SCENARIO_ID, SCENARIO_NAME));
        // build one usage with different pair of rh and payee
        Usage usage1 = buildUsage(54236984L);
        usage1.setRightsholder(buildRightsholder(2000017004L,
            "Access Copyright, The Canadian Copyright Agency"));
        usage1.setPayee(buildRightsholder(2000017010L,
            "JAC, Japan Academic Association for Copyright Clearance, Inc."));
        // build 2 usages with the same rh and payee
        Usage usage2 = buildUsage(30120014L);
        Usage usage3 = buildUsage(24125874L);
        usageRepository.insert(usage1);
        usageRepository.insert(usage2);
        usageRepository.insert(usage3);
        usageRepository.addToScenario(Lists.newArrayList(usage1, usage2, usage3));
        List<RightsholderPayeePair> result =
            scenarioRepository.findRightsholdersByScenarioAndSourceRro(SCENARIO_ID, 2000017010L);
        assertEquals(2, result.size());
        result.sort(Comparator.comparing(RightsholderPayeePair::getRightsholder)
            .thenComparing(RightsholderPayeePair::getPayee));
        // one pair should be returned for usage 3
        RightsholderPayeePair pair1 = result.get(0);
        assertEquals(usage1.getRightsholder(), pair1.getRightsholder());
        assertEquals(usage1.getPayee(), pair1.getPayee());
        RightsholderPayeePair pair2 = result.get(1);
        // one pair should be returned for usages 1 and 2
        assertEquals(usage2.getRightsholder(), pair2.getRightsholder());
        assertEquals(usage2.getPayee(), pair2.getPayee());
        assertEquals(usage3.getRightsholder(), pair2.getRightsholder());
        assertEquals(usage3.getPayee(), pair2.getPayee());
    }

    private UsageBatch buildBatch(Long rroAccountNumber) {
        UsageBatch batch = new UsageBatch();
        batch.setId(RupPersistUtils.generateUuid());
        batch.setGrossAmount(new BigDecimal("100.00"));
        batch.setPaymentDate(LocalDate.of(2017, 1, 1));
        batch.setFiscalYear(2017);
        batch.setName("Batch name");
        Rightsholder rro = new Rightsholder();
        rro.setAccountNumber(rroAccountNumber);
        batch.setRro(rro);
        return batch;
    }

    private Usage buildUsage(Long detailId) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setBatchId(USAGE_BATCH_ID);
        usage.setScenarioId(SCENARIO_ID);
        usage.setDetailId(detailId);
        usage.setWrWrkInst(123456783L);
        usage.setWorkTitle("WorkTitle");
        usage.setRightsholder(buildRightsholder(7000813806L,
            "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil"));
        usage.setPayee(usage.getRightsholder());
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setArticle("Article");
        usage.setStandardNumber("StandardNumber");
        usage.setPublisher("Publisher");
        usage.setPublicationDate(LocalDate.of(2016, 11, 3));
        usage.setMarket("Market");
        usage.setMarketPeriodFrom(2015);
        usage.setMarketPeriodTo(2017);
        usage.setAuthor("Author");
        usage.setNumberOfCopies(1);
        usage.setReportedValue(new BigDecimal("11.25"));
        usage.setGrossAmount(new BigDecimal("54.4400000000"));
        return usage;
    }

    private Scenario buildScenario(String id, String name) {
        Scenario scenario = new Scenario();
        scenario.setId(id);
        scenario.setName(name);
        scenario.setNetTotal(NET_TOTAL);
        scenario.setServiceFeeTotal(SERVICE_FEE_TOTAL);
        scenario.setGrossTotal(GROSS_TOTAL);
        scenario.setReportedTotal(REPORTED_TOTAL);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setDescription(DESCRIPTION);
        return scenario;
    }

    private void verifyScenario(Scenario scenario, String id, String name, String description, BigDecimal grossAmount,
                                BigDecimal netAmount, BigDecimal serviceFeeAmount, BigDecimal reportedAmount) {
        assertEquals(id, scenario.getId());
        assertEquals(name, scenario.getName());
        assertEquals(description, scenario.getDescription());
        assertEquals("SYSTEM", scenario.getCreateUser());
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
        assertEquals(grossAmount, scenario.getGrossTotal());
        assertEquals(netAmount, scenario.getNetTotal());
        assertEquals(serviceFeeAmount, scenario.getServiceFeeTotal());
        assertEquals(reportedAmount, scenario.getReportedTotal());
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
