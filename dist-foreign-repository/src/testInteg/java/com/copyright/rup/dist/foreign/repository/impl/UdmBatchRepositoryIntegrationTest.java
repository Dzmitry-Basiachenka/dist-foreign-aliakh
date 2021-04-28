package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.repository.api.IUdmBatchRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verifies {@link UdmBatchRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 04/28/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@Transactional
public class UdmBatchRepositoryIntegrationTest {

    private static final String UDM_BATCH_UID = "4b5751aa-6258-44c6-b839-a1ec0edfcf4d";
    private static final String UDM_BATCH_NAME = "UDM Batch 2021 June";

    @Autowired
    private IUdmBatchRepository udmBatchRepository;

    @Test
    public void testInsertUsageBatch() {
        udmBatchRepository.insert(buildUdmBatch());
        UdmBatch udmBatch = udmBatchRepository.findById(UDM_BATCH_UID);
        assertNotNull(udmBatch);
        assertEquals(UDM_BATCH_UID, udmBatch.getId());
        assertEquals(UDM_BATCH_NAME, udmBatch.getName());
        assertEquals(Integer.valueOf(202006), udmBatch.getPeriod());
        assertEquals(UdmChannelEnum.CCC, udmBatch.getChannel());
        assertEquals(UdmUsageOriginEnum.SS, udmBatch.getUsageOrigin());
    }

    private UdmBatch buildUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setId(UDM_BATCH_UID);
        udmBatch.setName(UDM_BATCH_NAME);
        udmBatch.setPeriod(202006);
        udmBatch.setChannel(UdmChannelEnum.CCC);
        udmBatch.setUsageOrigin(UdmUsageOriginEnum.SS);
        return udmBatch;
    }
}
