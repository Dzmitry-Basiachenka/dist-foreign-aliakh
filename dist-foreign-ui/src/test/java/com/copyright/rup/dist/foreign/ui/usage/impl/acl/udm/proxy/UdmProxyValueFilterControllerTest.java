package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterWidget;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link UdmProxyValueFilterController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmProxyValueFilterControllerTest {

    private UdmProxyValueFilterController controller;
    private IPublicationTypeService publicationTypeService;

    @Before
    public void setUp() {
        controller = new UdmProxyValueFilterController();
        publicationTypeService = createMock(IPublicationTypeService.class);
        Whitebox.setInternalState(controller, publicationTypeService);
    }

    @Test
    public void testInstantiateWidget() {
        IUdmProxyValueFilterWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(UdmProxyValueFilterWidget.class, widget.getClass());
    }

    @Test
    public void testGetPublicationTypeCodes() {
        List<PublicationType> pubTypes =
            Arrays.asList(buildPublicationType("BK", "Book", "1.00"), buildPublicationType("NL", "Newspaper", "1.90"));
        expect(publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY)).andReturn(pubTypes).once();
        replay(publicationTypeService);
        assertEquals(Arrays.asList("BK", "NL"), controller.getPublicationTypeCodes());
        verify(publicationTypeService);
    }

    private PublicationType buildPublicationType(String name, String description, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setName(name);
        pubType.setDescription(description);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
