package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

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

    @Autowired
    private IUsageRepository usageRepository;

    @Test
    @Ignore
    public void testInsertUsage() {
        assertEquals(1, usageRepository.insertUsage(buildUsage()));
        //TODO {dbaraukova} add verifiration of fields
    }

    private Usage buildUsage() {
        Usage usage  = new Usage();
        usage.setId(UUID.randomUUID().toString());
        usage.setDetailId(1);
        usage.setBatchId(UUID.randomUUID().toString());
        usage.setWrWrkInst(123456789L);
        usage.setWorkTitle("Title");
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(87452169L);
        usage.setRightsholder(rightsholder);
        usage.setStatus(UsageStatusEnum.ELIGIBLE);
        usage.setArticle("Article");
        usage.setStandardNumber("StandardNumber");
        usage.setPublisher("Publisher");
        usage.setMarket("Market");
        usage.setMarketPeriodFrom(2015);
        usage.setMarketPeriodTo(2017);
        usage.setAuthor("Author");
        usage.setNumberOfCopies(155);
        usage.setOriginalAmount(new BigDecimal("11.25"));
        usage.setGrossAmount(new BigDecimal("54.44"));
        usage.setCreateUser("user");
        usage.setUpdateUser("user");
        return usage;
    }
}
