package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineValueService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link UdmBaselineValueFilterController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueFilterControllerTest {

    private final UdmBaselineValueFilterController controller = new UdmBaselineValueFilterController();

    private IPublicationTypeService publicationTypeService;
    private IUdmBaselineValueService udmBaselineValueService;

    @Before
    public void setUp() {
        publicationTypeService = createMock(IPublicationTypeService.class);
        udmBaselineValueService = createMock(IUdmBaselineValueService.class);
        Whitebox.setInternalState(controller, publicationTypeService);
        Whitebox.setInternalState(controller, udmBaselineValueService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(UdmBaselineValueFilterWidget.class));
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = List.of(202006);
        expect(udmBaselineValueService.getPeriods()).andReturn(periods).once();
        replay(udmBaselineValueService);
        assertEquals(periods, controller.getPeriods());
        verify(udmBaselineValueService);
    }

    @Test
    public void testGetPublicationTypes() {
        List<PublicationType> pubTypes = List.of(buildPublicationType("Book", "1.00"));
        expect(publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY)).andReturn(pubTypes).once();
        replay(publicationTypeService);
        assertEquals(pubTypes, controller.getPublicationTypes());
        verify(publicationTypeService);
    }

    private PublicationType buildPublicationType(String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
