package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Verifies {@link UdmValueFilterController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueFilterControllerTest {

    private final UdmValueFilterController controller = new UdmValueFilterController();
    private IPublicationTypeService publicationTypeService;
    private IUdmValueService udmValueService;

    @Before
    public void setUp() {
        publicationTypeService = createMock(IPublicationTypeService.class);
        udmValueService = createMock(IUdmValueService.class);
        Whitebox.setInternalState(controller, publicationTypeService);
        Whitebox.setInternalState(controller, udmValueService);
    }

    @Test
    public void testInstantiateWidget() {
        IUdmValueFilterWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(UdmValueFilterWidget.class, widget.getClass());
    }

    @Test
    public void testGetAssignees() {
        // TODO add implementation
    }

    @Test
    public void testGetLastValuePeriods() {
        // TODO add implementation
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Arrays.asList(202006, 202112);
        expect(udmValueService.getPeriods()).andReturn(periods).once();
        replay(udmValueService);
        assertEquals(periods, controller.getPeriods());
        verify(udmValueService);
    }

    @Test
    public void testGetPublicationTypes() {
        List<PublicationType> pubTypes = Collections.singletonList(buildPublicationType("Book", "1.00"));
        expect(publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY)).andReturn(pubTypes).once();
        replay(publicationTypeService);
        assertEquals(pubTypes, controller.getPublicationTypes());
        verify(publicationTypeService);
    }

    @Test
    public void testGetCurrencyCodesToCurrencyNamesMap() {
        Map<String, String> map = ImmutableMap.of("USD", "US Dollar");
        expect(udmValueService.getCurrencyCodesToCurrencyNamesMap()).andReturn(map).once();
        replay(udmValueService);
        assertEquals(map, controller.getCurrencyCodesToCurrencyNamesMap());
        verify(udmValueService);
    }

    private PublicationType buildPublicationType(String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
