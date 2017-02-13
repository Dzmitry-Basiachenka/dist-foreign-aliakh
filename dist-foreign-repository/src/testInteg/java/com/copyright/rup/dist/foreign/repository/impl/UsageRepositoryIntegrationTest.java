package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Integration test for {@link UsageRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/03/17
 *
 * @author Darya Baraukova
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-sql-test-context.xml"})
@TransactionConfiguration
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class UsageRepositoryIntegrationTest {

    private static final String USAGE_BATCH_ID_1 = "56282dbc-2468-48d4-b926-93d3458a656a";
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2017, 1, 11);
    private static final Integer FISCAL_YEAR = 2017;
    private static final String RH_ACCOUNT_NAME = "Rh Account Name";
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("54.44");
    private static final Long WR_WRK_INST = 123456783L;
    private static final String WORK_TITLE = "Work Title";
    private static final String ARTICLE = "Article";
    private static final String STANDART_NUMBER = "StandardNumber";
    private static final String PUBLISHER = "Publisher";
    private static final String MARKET = "Market";
    private static final Integer MARKED_PERIOD_FROM = 2015;
    private static final Integer MARKED_PERIOD_TO = 2017;
    private static final String AUTHOR = "Author";
    private static final BigDecimal ORIGINAL_AMOUNT = new BigDecimal("11.25");
    private static final LocalDate PUBLICATION_DATE = LocalDate.of(2016, 11, 3);
    private static final BigDecimal NET_AMOUNT = new BigDecimal("3.13");
    private static final BigDecimal SERVICE_FEE = new BigDecimal("0.15000");
    private static final BigDecimal SERVICE_FEE_AMOUNT = new BigDecimal("2.38");
    private static final Long DETAIL_ID = 12345L;
    private static final Integer NUMBER_OF_COPIES = 155;
    private static final String DETAIL_ID_KEY = "detailId";
    private static final String WORK_TITLE_KEY = "workTitle";
    private static final String ARTICLE_KEY = "article";
    private static final String STANDART_NUMBER_KEY = "standardNumber";
    private static final String WR_WRK_INST_KEY = "wrWrkInst";
    private static final String RH_ACCOUNT_NUMBER_KEY = "rightsholderAccountNumber";
    private static final String PUBLISHER_KEY = "publisher";
    private static final String PUBLICATION_DATE_KEY = "publicationDate";
    private static final String NUMBER_OF_COPIES_KEY = "numberOfCopies";
    private static final String ORIGINAL_AMOUNT_KEY = "originalAmount";
    private static final String GROSS_AMOUNT_KEY = "grossAmount";
    private static final String MARKET_KEY = "market";
    private static final String MARKED_PERIOD_FROM_KEY = "marketPeriodFrom";
    private static final String MARKED_PERIOD_TO_KEY = "marketPeriodTo";
    private static final String AUTHOR_KEY = "author";
    private static final String BATCH_NAME_KEY = "batchName";
    private static final String FISCAL_YEAR_KEY = "fiscalYear";
    private static final String RRO_ACCOUNT_NUMBER_KEY = "rroAccountNumber";
    private static final String PAYMENT_DATE_KEY = "paymentDate";
    private static final String USAGE_ID_1 = "111111111";
    private static final String USAGE_ID_2 = "222222222";
    private static final String USAGE_ID_3 = "444444444";

    @Autowired
    private IUsageRepository usageRepository;

    @Test
    @Ignore
    public void testInsertUsage() {
        assertEquals(1, usageRepository.insertUsage(buildUsage(UUID.randomUUID().toString())));
        //TODO {dbaraukova} add verifiration of fields
    }

    @Test
    public void testGetUsagesCount() {
        assertEquals(1, usageRepository.getUsagesCount(
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR)));
    }

    @Test
    public void testFindByFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 1, USAGE_ID_1);
    }

    @Test
    public void testFindByUsageBatchFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.singleton(USAGE_BATCH_ID_1), null, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 1, USAGE_ID_1);
    }

    @Test
    public void testFindByRhAccountNumberFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.emptySet(), null, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 1, USAGE_ID_1);
    }

    @Test
    public void testFindByStatusFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.emptySet(), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByPaymentDateFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.emptySet(), null, PAYMENT_DATE, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 2, USAGE_ID_2, USAGE_ID_1);
    }

    @Test
    public void testFindByFiscalYearFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.emptySet(), null, null, FISCAL_YEAR);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 2, USAGE_ID_2, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByDetailId() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByWorkTitle() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(WORK_TITLE_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByArticle() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(ARTICLE_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByStandartNumber() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(STANDART_NUMBER_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByWrWrkInst() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(WR_WRK_INST_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByRhAccountNumber() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(RH_ACCOUNT_NUMBER_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByPublisher() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(PUBLISHER_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByPublicationDate() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(PUBLICATION_DATE_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByNumberOfCopies() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(NUMBER_OF_COPIES_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByOriginalAmount() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(ORIGINAL_AMOUNT_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByGrossAmount() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(GROSS_AMOUNT_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByMarket() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(MARKET_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByMarketPeriodFrom() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(MARKED_PERIOD_FROM_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByMarketPeriodTo() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(MARKED_PERIOD_TO_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByAuthor() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(AUTHOR_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByBatchName() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(BATCH_NAME_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByFiscalYear() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(FISCAL_YEAR_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByRroAccountNumber() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(RRO_ACCOUNT_NUMBER_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByPaymentDate() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(PAYMENT_DATE_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testWriteUsagesCsvReport() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton(USAGE_BATCH_ID_1));
        usageRepository.writeUsagesCsvReport(usageFilter, outputStream);
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
        assertEquals("Detail ID,Usage Batch Name,Fiscal Year,RRO Account #,RRO Name,Payment Date,Title,Article," +
                "Standard Number,Wr Wrk Inst,RH Account #,RH Name,Publisher,Pub Date,Number of Copies," +
                "Amt in Orig Currency,Amt in USD,Market,Market Period From,Market Period To,Author,Detail Status",
            bufferedReader.readLine());
        assertEquals("6997788888,CADRA_11Dec16,FY2017,7000813806,,01/11/2017,\"2001 IEEE Workshop on High" +
            " Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA\",Efficient Generation of H2 by" +
            " Splitting Water with an Isothermal Redox Cycle,1008902112377654XX,180382914,1000009997,,IEEE," +
            "09/10/2013,2502232,2500.00,13461.54,Doc Del,2013,2017,\"Íñigo López de Mendoza, marqués de Santillana\"" +
            ",ELIGIBLE",
            bufferedReader.readLine());
        assertNull(bufferedReader.readLine());
    }

    @Test
    public void testWriteUsagesEmptyCsvReport() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        UsageFilter usageFilter = new UsageFilter();
        usageRepository.writeUsagesCsvReport(usageFilter, outputStream);
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
        assertEquals("Detail ID,Usage Batch Name,Fiscal Year,RRO Account #,RRO Name,Payment Date,Title,Article," +
                "Standard Number,Wr Wrk Inst,RH Account #,RH Name,Publisher,Pub Date,Number of Copies," +
                "Amt in Orig Currency,Amt in USD,Market,Market Period From,Market Period To,Author,Detail Status",
            bufferedReader.readLine());
        assertNull(bufferedReader.readLine());
    }

    private UsageFilter buildUsageFilter(Set<Long> accountNumbers, Set<String> usageBatchIds, UsageStatusEnum status,
                                         LocalDate paymentDate, Integer fiscalYear) {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setRhAccountNumbers(accountNumbers);
        usageFilter.setUsageBatchesIds(usageBatchIds);
        usageFilter.setUsageStatus(status);
        usageFilter.setPaymentDate(paymentDate);
        usageFilter.setFiscalYear(fiscalYear);
        return usageFilter;
    }

    private Usage buildUsage(String usageBatchId) {
        Usage usage = new Usage();
        usage.setId(UUID.randomUUID().toString());
        usage.setBatchId(usageBatchId);
        usage.setDetailId(DETAIL_ID);
        usage.setWrWrkInst(WR_WRK_INST);
        usage.setWorkTitle(WORK_TITLE);
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        rightsholder.setName(RH_ACCOUNT_NAME);
        usage.setRightsholder(rightsholder);
        usage.setStatus(UsageStatusEnum.ELIGIBLE);
        usage.setArticle(ARTICLE);
        usage.setStandardNumber(STANDART_NUMBER);
        usage.setPublisher(PUBLISHER);
        usage.setPublicationDate(PUBLICATION_DATE);
        usage.setMarket(MARKET);
        usage.setMarketPeriodFrom(MARKED_PERIOD_FROM);
        usage.setMarketPeriodTo(MARKED_PERIOD_TO);
        usage.setAuthor(AUTHOR);
        usage.setNumberOfCopies(NUMBER_OF_COPIES);
        usage.setOriginalAmount(ORIGINAL_AMOUNT);
        usage.setNetAmount(NET_AMOUNT);
        usage.setServiceFee(SERVICE_FEE);
        usage.setServiceFeeAmount(SERVICE_FEE_AMOUNT);
        usage.setGrossAmount(GROSS_AMOUNT);
        return usage;
    }

    private void verifyUsageDtos(List<UsageDto> usageDtos, int count, String... usageIds) {
        assertNotNull(usageDtos);
        assertEquals(count, usageDtos.size());
        for (int i = 0; i < count; i++) {
            assertEquals(usageIds[i], usageDtos.get(i).getId());
        }
    }
}
