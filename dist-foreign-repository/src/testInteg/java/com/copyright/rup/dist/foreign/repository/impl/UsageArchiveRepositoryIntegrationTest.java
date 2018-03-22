package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.repository.api.Sort.Direction;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Integration test for {@link UsageArchiveRepository}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/08/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=usage-archive-repository-test-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class UsageArchiveRepositoryIntegrationTest {

    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final String RH_ACCOUNT_NAME_1 = "IEEE - Inst of Electrical and Electronics Engrs";
    private static final String RH_ACCOUNT_NAME_2 = "John Wiley & Sons - Books";
    private static final String RH_ACCOUNT_NAME_3 = "Kluwer Academic Publishers - Dordrecht";
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("54.4400000000");
    private static final Long WR_WRK_INST = 123456783L;
    private static final String WORK_TITLE = "Work Title";
    private static final String ARTICLE = "Article";
    private static final String STANDARD_NUMBER = "StandardNumber";
    private static final String PUBLISHER = "Publisher";
    private static final String MARKET = "Market";
    private static final Integer MARKED_PERIOD_FROM = 2015;
    private static final Integer MARKED_PERIOD_TO = 2017;
    private static final String AUTHOR = "Author";
    private static final BigDecimal REPORTED_VALUE = new BigDecimal("11.25");
    private static final LocalDate PUBLICATION_DATE = LocalDate.of(2016, 11, 3);
    private static final OffsetDateTime PAID_DATE =
        LocalDate.of(2016, 11, 3).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime();
    private static final Long DETAIL_ID = 12345L;
    private static final Integer NUMBER_OF_COPIES = 155;
    private static final String SCENARIO_ID = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";

    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;

    @Test
    public void testInsert() {
        usageArchiveRepository.insert(
            buildUsage(RupPersistUtils.generateUuid(), "56282dbc-2468-48d4-b926-93d3458a656a"));
        List<UsageDto> usageDtos = usageArchiveRepository.findByScenarioIdAndRhAccountNumber(
            "b1f0b236-3ae9-4a60-9fab-61db84199d6f", RH_ACCOUNT_NUMBER, null, null, null);
        assertEquals(1, usageDtos.size());
        UsageDto usageDto = usageDtos.get(0);
        assertNotNull(usageDto);
        assertEquals(DETAIL_ID, usageDto.getDetailId());
        assertEquals("CADRA_11Dec16", usageDto.getBatchName());
        assertEquals(WR_WRK_INST, usageDto.getWrWrkInst());
        assertEquals(2017, usageDto.getFiscalYear(), 0);
        assertEquals(RH_ACCOUNT_NUMBER, usageDto.getRhAccountNumber(), 0);
        assertEquals(2000017004L, usageDto.getPayeeAccountNumber(), 0);
        assertEquals(WORK_TITLE, usageDto.getWorkTitle());
        assertEquals(UsageStatusEnum.LOCKED, usageDto.getStatus());
        assertEquals(ARTICLE, usageDto.getArticle());
        assertEquals(STANDARD_NUMBER, usageDto.getStandardNumber());
        assertEquals(PUBLISHER, usageDto.getPublisher());
        assertEquals(PUBLICATION_DATE, usageDto.getPublicationDate());
        assertEquals(MARKET, usageDto.getMarket());
        assertEquals(MARKED_PERIOD_FROM, usageDto.getMarketPeriodFrom());
        assertEquals(MARKED_PERIOD_TO, usageDto.getMarketPeriodTo());
        assertEquals(AUTHOR, usageDto.getAuthor());
        assertEquals(NUMBER_OF_COPIES, usageDto.getNumberOfCopies());
        assertEquals(REPORTED_VALUE, usageDto.getReportedValue());
        assertEquals(GROSS_AMOUNT, usageDto.getGrossAmount());
        assertEquals("FAS", usageDto.getProductFamily());
    }


    @Test
    public void testFindRightsholderTotalsHoldersByScenarioIdEmptySearchValue() {
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null,
                null);
        assertEquals(3, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 67874.80, 21720.00, 46154.80),
            rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 2125.24, 680.0768, 1445.1632),
            rightsholderTotalsHolders.get(2));
    }

    @Test
    public void testFindRightsholderTotalsHoldersByScenarioIdNotEmptySearchValue() {
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "JoHn", null, null);
        assertEquals(1, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 67874.80, 21720.00, 46154.80),
            rightsholderTotalsHolders.get(0));
        rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "IEEE", null, null);
        assertEquals(1, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(0));
        rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "ec", null, null);
        assertEquals(2, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 2125.24, 680.0768, 1445.1632),
            rightsholderTotalsHolders.get(1));
    }

    @Test
    public void testFindRightsholderTotalsHoldersByScenarioIdSortByAccountNumber() {
        Sort accountNumberSort = new Sort("rightsholder.accountNumber", Direction.ASC);
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null,
                accountNumberSort);
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 67874.80, 21720.00, 46154.80),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 2125.24, 680.0768, 1445.1632),
            rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(2));
    }

    @Test
    public void testFindRightsholderTotalsHoldersByScenarioIdSortByName() {
        Sort accountNumberSort = new Sort("rightsholder.name", Direction.DESC);
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null,
                accountNumberSort);
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 2125.24, 680.0768, 1445.1632),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 67874.80, 21720.00, 46154.80),
            rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(2));
    }

    @Test
    public void testFindCountByScenarioIdAndRhAccountNumberNullSearchValue() {
        assertEquals(1, usageArchiveRepository.findCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009997L, null));
        assertEquals(1, usageArchiveRepository.findCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000002859L, null));
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberNullSearchValue() {
        assertEquals(1,
            usageArchiveRepository.findByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009997L, null, null, null)
                .size());
        assertEquals(1,
            usageArchiveRepository.findByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000002859L, null, null, null)
                .size());
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByRorName() {
        verifySearch("JAC, Japan Academic Association for Copyright Clearance, Inc.", 1);
        verifySearch("Copyright Clearance, Inc.", 1);
        verifySearch("CoPyRight", 1);
        verifySearch("JAC, Japan Acade mic", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByRorAccountNumber() {
        verifySearch("2000017010", 1);
        verifySearch("0001701", 1);
        verifySearch("200001 7010", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchDetailId() {
        verifySearch("6997788886", 1);
        verifySearch("78888", 1);
        verifySearch("69977 88886", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByWrWrkInst() {
        verifySearch("243904752", 1);
        verifySearch("24390", 1);
        verifySearch("24461 4835", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByStandardNumber() {
        verifySearch("1008902112377654XX", 1);
        verifySearch("1008902112377654xx", 1);
        verifySearch("100890 2002377655XX", 0);
    }

    @Test
    public void testFindRightsholderTotalsHolderCountByScenarioId() {
        assertEquals(3,
            usageArchiveRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY));
        assertEquals(1, usageArchiveRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, "IEEE"));
    }

    @Test
    public void testWriteScenarioUsagesCsvReport() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor()
            .execute(() -> usageArchiveRepository.writeScenarioUsagesCsvReport(SCENARIO_ID, outputStream));
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        assertEquals("Detail ID,Usage Batch Name,Product Family,Fiscal Year,RRO Account #,RRO Name,Payment Date," +
            "Title,Article,Standard Number,Wr Wrk Inst,RH Account #,RH Name,Payee Account #,Payee Name,Publisher," +
            "Pub Date,Number of Copies,Reported value,Gross Amt in USD,Service Fee Amount,Net Amt in USD," +
            "Service Fee %,Market,Market Period From,Market Period To,Author", bufferedReader.readLine());
        assertEquals("6997788888,CADRA_11Dec16,FAS,FY2017,7000813806,,01/11/2017," +
            "\"2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, " +
            "USA\",Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle," +
            "1008902112377654XX,180382914,1000009997,IEEE - Inst of Electrical and Electronics Engrs,1000009997," +
            "IEEE - Inst of Electrical and Electronics Engrs,IEEE,09/10/2013,2502232,2500.00,35000.0000000000," +
            "11200.0000000000,23800.0000000000,32.0,Doc Del,2013,2017," +
            "\"Íñigo López de Mendoza, marqués de Santillana\"", bufferedReader.readLine());
        assertEquals("6997788884,JAACC_11Dec16,FAS,FY2016,2000017010," +
            "\"JAC, Japan Academic Association for Copyright Clearance, Inc.\",09/10/2015," +
            "10 Years Plant Molecular Biology,DIN EN 779:2012,1008902002377655XX,345870577,1000005413," +
            "Kluwer Academic Publishers - Dordrecht,1000005413,Kluwer Academic Publishers - Dordrecht," +
            "McGraw-Hill,12/31/2009,2630,1280.00,2125.2400000000,680.0768000000,1445.1632000000,32.0,Edu,2015," +
            "2019,Mirjam H. Hüberli", bufferedReader.readLine());
        assertEquals("6997788886,JAACC_11Dec16,FAS,FY2016,2000017010," +
            "\"JAC, Japan Academic Association for Copyright Clearance, Inc.\",09/10/2015,100 ROAD MOVIES," +
            "DIN EN 779:2012,1008902112377654XX,243904752,1000002859,John Wiley & Sons - Books,1000002859," +
            "John Wiley & Sons - Books,IEEE,09/10/2013,250232,9900.00,67874.8000000000,21720.0000000000," +
            "46154.8000000000,32.0,Doc Del,2013,2017,Philippe de Mézières", bufferedReader.readLine());
        assertNull(bufferedReader.readLine());
    }

    @Test
    public void testFindDetailIdToIdMap() {
        Map<Long, String> resultMap = ImmutableMap.of(5423214888L, "7241b7e0-6ab8-4483-896d-fd485c574293", 5423214999L,
            "ce9a7770-1ae6-11e8-b566-0800200c9a66");
        assertEquals(resultMap,
            usageArchiveRepository.findDetailIdToIdMap(Lists.newArrayList(5423214888L, 5423214999L)));
    }

    @Test
    public void testUpdatePaidInfo() {
        List<UsageDto> usages =
            usageArchiveRepository.findByScenarioIdAndRhAccountNumber("98caae9b-2f20-4c6d-b2af-3190a1115c48",
                1000002859L, null, null, null);
        assertTrue(CollectionUtils.isNotEmpty(usages));
        assertEquals(1, usages.size());
        UsageDto usage = usages.get(0);
        verifyPaidUsage(usage, UsageStatusEnum.LOCKED, 1000002859L, null, null, null, null, null, null);
        PaidUsage paidUsage = new PaidUsage();
        Rightsholder payee = new Rightsholder();
        payee.setAccountNumber(1000005413L);
        paidUsage.setPayee(payee);
        paidUsage.setId("7241b7e0-6ab8-4483-896d-fd485c574293");
        paidUsage.setCheckNumber("578945");
        paidUsage.setCheckDate(PAID_DATE);
        paidUsage.setCccEventId("53256");
        paidUsage.setDistributionName("FDA March 17");
        paidUsage.setDistributionDate(PAID_DATE);
        paidUsage.setPeriodEndDate(PUBLICATION_DATE);
        paidUsage.setStatus(UsageStatusEnum.PAID);
        usageArchiveRepository.updatePaidInfo(paidUsage);
        usages =
            usageArchiveRepository.findByScenarioIdAndRhAccountNumber("98caae9b-2f20-4c6d-b2af-3190a1115c48",
                1000002859L, null, null, null);
        assertTrue(CollectionUtils.isNotEmpty(usages));
        assertEquals(1, usages.size());
        usage = usages.get(0);
        verifyPaidUsage(usage, UsageStatusEnum.PAID, 1000005413L, "578945", PAID_DATE, "53256", "FDA March 17",
            PAID_DATE, PUBLICATION_DATE);
    }

    private void verifyPaidUsage(UsageDto actualUsageDto, UsageStatusEnum status, Long payeeAccountNumber,
                                 String checkNumber, OffsetDateTime checkDate, String cccEventId,
                                 String distributionName, OffsetDateTime distributionDate,
                                 LocalDate periodEndDate) {
        assertEquals(5423214888L, actualUsageDto.getDetailId(), 0);
        assertEquals(status, actualUsageDto.getStatus());
        assertEquals(payeeAccountNumber, actualUsageDto.getPayeeAccountNumber(), 0);
        assertEquals(checkNumber, actualUsageDto.getCheckNumber());
        assertEquals(checkDate, actualUsageDto.getCheckDate());
        assertEquals(cccEventId, actualUsageDto.getCccEventId());
        assertEquals(distributionName, actualUsageDto.getDistributionName());
        assertEquals(distributionDate, actualUsageDto.getDistributionDate());
        assertEquals(periodEndDate, actualUsageDto.getPeriodEndDate());
    }

    private void verifySearch(String searchValue, int expectedSize) {
        assertEquals(expectedSize, usageArchiveRepository.findByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000002859L,
            searchValue, null, null).size());
        assertEquals(expectedSize,
            usageArchiveRepository.findCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000002859L, searchValue));
    }

    private RightsholderTotalsHolder buildRightsholderTotalsHolder(String rhName, Long rhAccountNumber,
                                                                   Double grossTotal, Double serviceFeeTotal,
                                                                   Double netTotal) {
        RightsholderTotalsHolder rightsholderTotalsHolder = new RightsholderTotalsHolder();
        rightsholderTotalsHolder.getRightsholder().setAccountNumber(rhAccountNumber);
        rightsholderTotalsHolder.getRightsholder().setName(rhName);
        rightsholderTotalsHolder.getPayee().setAccountNumber(rhAccountNumber);
        rightsholderTotalsHolder.getPayee().setName(rhName);
        rightsholderTotalsHolder.setGrossTotal(BigDecimal.valueOf(grossTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder.setServiceFeeTotal(
            BigDecimal.valueOf(serviceFeeTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder.setNetTotal(BigDecimal.valueOf(netTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder.setServiceFee(new BigDecimal("0.32000"));
        return rightsholderTotalsHolder;
    }

    private Usage buildUsage(String usageId, String usageBatchId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setBatchId(usageBatchId);
        usage.setScenarioId("b1f0b236-3ae9-4a60-9fab-61db84199d6f");
        usage.setDetailId(DETAIL_ID);
        usage.setWrWrkInst(WR_WRK_INST);
        usage.setWorkTitle(WORK_TITLE);
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.getRightsholder().setName("CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil");
        usage.getPayee().setAccountNumber(2000017004L);
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setProductFamily("FAS");
        usage.setArticle(ARTICLE);
        usage.setStandardNumber(STANDARD_NUMBER);
        usage.setPublisher(PUBLISHER);
        usage.setPublicationDate(PUBLICATION_DATE);
        usage.setMarket(MARKET);
        usage.setMarketPeriodFrom(MARKED_PERIOD_FROM);
        usage.setMarketPeriodTo(MARKED_PERIOD_TO);
        usage.setAuthor(AUTHOR);
        usage.setNumberOfCopies(NUMBER_OF_COPIES);
        usage.setReportedValue(REPORTED_VALUE);
        usage.setGrossAmount(GROSS_AMOUNT);
        usage.setNetAmount(new BigDecimal("25.1500000000"));
        return usage;
    }
}
