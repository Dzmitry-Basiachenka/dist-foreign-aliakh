package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Test for {@link ViewBaselineWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/18/21
 *
 * @author Anton Azarenka
 */
public class ViewBaselineWindowTest {

    private ViewBaselineWindow window;
    private UdmBaselineDto udmBaselineDto;

    @Test
    public void testConstructor() {
        buildUdmBaselineDto();
        window = new ViewBaselineWindow(udmBaselineDto);
        assertEquals("View UDM Baseline", window.getCaption());
        assertEquals(500, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(700, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testUdmUsageDataBinding() {
        buildUdmBaselineDto();
        window = new ViewBaselineWindow(udmBaselineDto);
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        assertTextFieldValue(verticalLayout.getComponent(0), "144678d6-81d9-4cdd-98f7-a1a0f4563fd1");
        assertTextFieldValue(verticalLayout.getComponent(1), "202006");
        assertTextFieldValue(verticalLayout.getComponent(2), "SS");
        assertTextFieldValue(verticalLayout.getComponent(3), "b1ab74db-4e47-44f5-bb47-bed2fb1730f6");
        assertTextFieldValue(verticalLayout.getComponent(4), "123456789");
        assertTextFieldValue(verticalLayout.getComponent(5), "Brain surgery");
        assertTextFieldValue(verticalLayout.getComponent(6), "26 - Law Firms");
        assertTextFieldValue(verticalLayout.getComponent(7), "26 - Law Firms");
        assertTextFieldValue(verticalLayout.getComponent(8), "United States");
        assertTextFieldValue(verticalLayout.getComponent(9), "CCC");
        assertTextFieldValue(verticalLayout.getComponent(10), "Book");
        assertTextFieldValue(verticalLayout.getComponent(11), "10.00000");
        assertTextFieldValue(verticalLayout.getComponent(12), "user@copyright.com");
        assertTextFieldValue(verticalLayout.getComponent(13), "06/01/2020");
        assertTextFieldValue(verticalLayout.getComponent(14), "wuser@copyright.com");
        assertTextFieldValue(verticalLayout.getComponent(15), "06/01/2020");
    }

    private void assertTextFieldValue(Component component, String expectedValue) {
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(expectedValue, ((TextField) layout.getComponent(1)).getValue());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(17, verticalLayout.getComponentCount());
        verifyButtonsLayout(verticalLayout.getComponent(16), "Close");
    }

    private void buildUdmBaselineDto() {
        udmBaselineDto = new UdmBaselineDto();
        udmBaselineDto.setId("144678d6-81d9-4cdd-98f7-a1a0f4563fd1");
        udmBaselineDto.setPeriod(202006);
        udmBaselineDto.setUsageOrigin(UdmUsageOriginEnum.SS);
        udmBaselineDto.setOriginalDetailId("b1ab74db-4e47-44f5-bb47-bed2fb1730f6");
        udmBaselineDto.setWrWrkInst(123456789L);
        udmBaselineDto.setSystemTitle("Brain surgery");
        udmBaselineDto.setDetailLicenseeClassId(26);
        udmBaselineDto.setDetailLicenseeClassName("Law Firms");
        udmBaselineDto.setAggregateLicenseeClassId(26);
        udmBaselineDto.setAggregateLicenseeClassName("Law Firms");
        udmBaselineDto.setSurveyCountry("United States");
        udmBaselineDto.setChannel(UdmChannelEnum.CCC);
        udmBaselineDto.setTypeOfUse("Book");
        udmBaselineDto.setAnnualizedCopies(new BigDecimal("10.00000"));
        udmBaselineDto.setCreateUser("user@copyright.com");
        udmBaselineDto.setCreateDate(
            Date.from(LocalDate.of(2020, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        udmBaselineDto.setUpdateUser("wuser@copyright.com");
        udmBaselineDto.setUpdateDate(
            Date.from(LocalDate.of(2020, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}
