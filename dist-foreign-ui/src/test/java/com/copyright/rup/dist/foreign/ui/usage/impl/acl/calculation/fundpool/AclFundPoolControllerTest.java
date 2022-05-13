package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.impl.csv.AclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * Verifies {@link AclFundPoolController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/18/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolControllerTest {

    private final AclFundPoolController controller = new AclFundPoolController();
    private CsvProcessorFactory csvProcessorFactory;
    private IAclFundPoolService fundPoolService;

    @Before
    public void setUp() {
        fundPoolService = createMock(IAclFundPoolService.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        Whitebox.setInternalState(controller, fundPoolService);
    }

    @Test
    public void testInstantiateWidget() {
        IAclFundPoolWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclFundPoolWidget.class, widget.getClass());
    }

    @Test
    public void testGetCsvProcessor() {
        AclFundPoolCsvProcessor processor = new AclFundPoolCsvProcessor();
        expect(csvProcessorFactory.getAclFundPoolCvsProcessor()).andReturn(processor).once();
        replay(csvProcessorFactory);
        assertSame(processor, controller.getCsvProcessor());
        verify(csvProcessorFactory);
    }

    @Test
    public void testIsAclFundPoolExist() {
        expect(fundPoolService.fundPoolExists("Fund Pool Name")).andReturn(true).once();
        replay(fundPoolService);
        assertTrue(controller.isFundPoolExist("Fund Pool Name"));
        verify(fundPoolService);
    }

    @Test
    public void testLoadManualFundPool() {
        AclFundPool fundPool = buildFundPool(true);
        fundPoolService.insertManualAclFundPool(fundPool, Collections.singletonList(buildFundPoolDetail()));
        expectLastCall().once();
        replay(fundPoolService);
        int count = controller.loadManualFundPool(fundPool, Collections.singletonList(buildFundPoolDetail()));
        assertEquals(1, count);
        verify(fundPoolService);
    }

    @Test
    public void testCreateLdmtFundPool() {
        AclFundPool fundPool = buildFundPool(false);
        expect(fundPoolService.insertLdmtAclFundPool(fundPool)).andReturn(1).once();
        replay(fundPoolService);
        int count = controller.createLdmtFundPool(fundPool);
        assertEquals(1, count);
        verify(fundPoolService);
    }

    private AclFundPoolDetail buildFundPoolDetail() {
        AclFundPoolDetail aclFundPoolDetail = new AclFundPoolDetail();
        aclFundPoolDetail.setFundPoolId("4f01a2fc-c5d4-4738-9715-c7dafc0c1fad");
        aclFundPoolDetail.setLicenseType("ACL");
        aclFundPoolDetail.setGrossAmount(new BigDecimal("0.55"));
        return aclFundPoolDetail;
    }

    private AclFundPool buildFundPool(boolean manualUploadFlag) {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setName("Fund Pool Name");
        aclFundPool.setLicenseType("ACL");
        aclFundPool.setManualUploadFlag(manualUploadFlag);
        return aclFundPool;
    }
}
