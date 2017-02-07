package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.CurrencyEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
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

    private static final Long RH_ACCOUNT_NUMBER = 12345678L;
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2016, 11, 8);
    private static final Integer FISCAL_YEAR = 2017;
    private static final String RH_ACCOUNT_NAME = "Rh Account Name";
    private static final String USAGE_BATCH_NAME = "Usage Batch Name";
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("54.44");
    private static final BigDecimal CONVERSION_RATE = new BigDecimal("10.0000000000");
    private static final BigDecimal APPLIED_CONVERSION_RATE = new BigDecimal("1.0000000000");
    private static final Long WR_WRK_INST = 123456789L;
    private static final String WORK_TITLE = "Work Title";
    private static final String ARTICLE = "Article";
    private static final String STANDART_NUMBER = "StandardNumber";
    private static final String PUBLISHER = "Publisher";
    private static final String MARKET = "Market";
    private static final Integer MARKED_PERIOD_FROM = 2015;
    private static final Integer MARKED_PERIOD_TO = 2017;
    private static final String AUTHOR = "Author";
    private static final BigDecimal ORIGINAL_AMOUNT = new BigDecimal("11.25");
    private static final LocalDate PUBLICATION_DATE = LocalDate.of(2016, 11, 9);
    private static final BigDecimal NET_AMOUNT = new BigDecimal("9.13");
    private static final BigDecimal SERVICE_FEE = new BigDecimal("0.15000");
    private static final BigDecimal SERVICE_FEE_AMOUNT = new BigDecimal("2.98");
    private static final Long DETAIL_ID = 12345L;
    private static final Integer NUMBER_OF_COPIES = 155;

    @Autowired
    private IUsageRepository usageRepository;

    @Autowired
    private IUsageBatchRepository usageBatchRepository;

    @Test
    @Ignore
    public void testInsertUsage() {
        assertEquals(1, usageRepository.insertUsage(buildUsage(UUID.randomUUID().toString())));
        //TODO {dbaraukova} add verifiration of fields
    }

    @Test
    public void testFindByFilter() {
        UsageBatch usageBatch = buildUsageBatch();
        UsageFilter usageFilter = buildUsageFilter(usageBatch.getId());
        usageBatchRepository.insert(usageBatch);
        usageRepository.insertUsage(buildUsage(usageBatch.getId()));
        List<UsageDto> usageDtos = usageRepository.findByFilter(usageFilter, new Pageable(0, 1),
            new Sort("detailId", Sort.Direction.ASC));
        assertNotNull(usageDtos);
        assertEquals(1, usageDtos.size());
        verifyUsageDto(usageDtos.get(0));
    }

    @Test
    public void testGetUsagesCount() {
        UsageBatch usageBatch = buildUsageBatch();
        usageBatchRepository.insert(usageBatch);
        usageRepository.insertUsage(buildUsage(usageBatch.getId()));
        assertEquals(1, usageRepository.getUsagesCount(buildUsageFilter(usageBatch.getId())));
    }

    private void verifyUsageDto(UsageDto usageDto) {
        assertNotNull(usageDto);
        assertEquals(DETAIL_ID, usageDto.getDetailId());
        assertEquals(WR_WRK_INST, usageDto.getWrWrkInst());
        assertEquals(WORK_TITLE, usageDto.getWorkTitle());
        assertEquals(RH_ACCOUNT_NUMBER, usageDto.getRightsholder().getAccountNumber());
        assertEquals(UsageStatusEnum.ELIGIBLE, usageDto.getStatus());
        assertEquals(ARTICLE, usageDto.getArticle());
        assertEquals(STANDART_NUMBER, usageDto.getStandardNumber());
        assertEquals(PUBLISHER, usageDto.getPublisher());
        assertEquals(PUBLICATION_DATE, usageDto.getPublicationDate());
        assertEquals(MARKET, usageDto.getMarket());
        assertEquals(MARKED_PERIOD_FROM, usageDto.getMarketPeriodFrom());
        assertEquals(MARKED_PERIOD_TO, usageDto.getMarketPeriodTo());
        assertEquals(AUTHOR, usageDto.getAuthor());
        assertEquals(NUMBER_OF_COPIES, usageDto.getNumberOfCopies());
        assertEquals(ORIGINAL_AMOUNT, usageDto.getOriginalAmount());
        assertEquals(GROSS_AMOUNT, usageDto.getGrossAmount());
        assertEquals(USAGE_BATCH_NAME, usageDto.getBatchName());
        assertEquals(RH_ACCOUNT_NUMBER, usageDto.getRro().getAccountNumber());
        assertEquals(PAYMENT_DATE, usageDto.getPaymentDate());
        assertEquals(FISCAL_YEAR, usageDto.getFiscalYear());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(UUID.randomUUID().toString());
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setRro(buildRightsholder());
        usageBatch.setPaymentDate(PAYMENT_DATE);
        usageBatch.setFiscalYear(FISCAL_YEAR);
        usageBatch.setGrossAmount(GROSS_AMOUNT);
        usageBatch.setCurrency(CurrencyEnum.USD);
        usageBatch.setConversionRate(CONVERSION_RATE);
        usageBatch.setAppliedConversionRate(APPLIED_CONVERSION_RATE);
        return usageBatch;
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(UUID.randomUUID().toString());
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        rightsholder.setName(RH_ACCOUNT_NAME);
        return rightsholder;
    }

    private UsageFilter buildUsageFilter(String usageBatchId) {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setRhAccountNumbers(Collections.singleton(RH_ACCOUNT_NUMBER));
        usageFilter.setUsageBatchesIds(Collections.singleton(usageBatchId));
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        usageFilter.setPaymentDate(PAYMENT_DATE);
        usageFilter.setFiscalYear(FISCAL_YEAR);
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
}
