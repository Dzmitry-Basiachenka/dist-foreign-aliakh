package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.service.impl.csv.AclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

    @Before
    public void setUp() {
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        Whitebox.setInternalState(controller, csvProcessorFactory);
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
}
