package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.List;

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
        List<String> assignees = List.of("wjohn@copyright.com");
        expect(udmValueService.getAssignees()).andReturn(assignees).once();
        replay(udmValueService);
        assertEquals(assignees, controller.getAssignees());
        verify(udmValueService);
    }

    @Test
    public void testGetLastValuePeriods() {
        List<String> periods = List.of("202106");
        expect(udmValueService.getLastValuePeriods()).andReturn(periods).once();
        replay(udmValueService);
        assertEquals(periods, controller.getLastValuePeriods());
        verify(udmValueService);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = List.of(202006, 202112);
        expect(udmValueService.getPeriods()).andReturn(periods).once();
        replay(udmValueService);
        assertEquals(periods, controller.getPeriods());
        verify(udmValueService);
    }

    @Test
    public void testGetPublicationTypes() {
        List<PublicationType> pubTypes = List.of(buildPublicationType("Book", "1.00"));
        expect(publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY)).andReturn(pubTypes).once();
        replay(publicationTypeService);
        assertEquals(pubTypes, controller.getPublicationTypes());
        verify(publicationTypeService);
    }

    @Test
    public void testGetAllCurrencies() {
        List<Currency> currencies = List.of(new Currency("USD", "US Dollar"));
        expect(udmValueService.getAllCurrencies()).andReturn(currencies).once();
        replay(udmValueService);
        assertEquals(currencies, controller.getAllCurrencies());
        verify(udmValueService);
    }

    private PublicationType buildPublicationType(String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
