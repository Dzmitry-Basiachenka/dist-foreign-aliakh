package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Verifies {@link UdmViewValueWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class UdmViewValueWindowTest {

    private static final String UDM_VALUE_UID = "12f2a318-46b1-4733-a0bf-8797d7e92bcf";
    private static final Integer VALUE_PERIOD = 202106;
    private static final UdmValueStatusEnum STATUS = UdmValueStatusEnum.NEW;
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final Long WR_WRK_INST = 122825347L;
    private static final Long RH_ACCOUNT_NUMBER = 1000023041L;
    private static final String RH_NAME = "American College of Physicians - Journals";
    private static final String SYSTEM_TITLE = "Wissenschaft & Forschung Japan";
    private static final String SYSTEM_STANDARD_NUMBER = "2192-3558";
    private static final Integer LAST_VALUE_PERIOD = 202012;
    private static final String LAST_PUB_TYPE = "BK2";
    private static final PublicationType PUBLICATION_TYPE;
    private static final BigDecimal LAST_PRICE_IN_USD = new BigDecimal("3651.21");
    private static final boolean LAST_PRICE_FLAG = false;
    private static final String LAST_PRICE_SOURCE = "last price source";
    private static final String LAST_PRICE_COMMENT = "last price comment";
    private static final BigDecimal PRICE = new BigDecimal("3000.00");
    private static final String PRICE_SOURCE = "price source";
    private static final Currency CURRENCY = new Currency("USD", "US Dollar");
    private static final String CURRENCY_CODE = CURRENCY.getCode();
    private static final String PRICE_TYPE = "Individual";
    private static final String PRICE_ACCESS_TYPE = "Print";
    private static final Integer PRICE_YEAR = 2021;
    private static final String PRICE_COMMENT = "price comment";
    private static final BigDecimal PRICE_IN_USD = new BigDecimal("3651.20");
    private static final boolean PRICE_FLAG = true;
    private static final BigDecimal CURRENCY_EXCHANGE_RATE = new BigDecimal("1.1628");
    private static final LocalDate CURRENCY_EXCHANGE_RATE_DATE = LocalDate.of(2020, 12, 31);
    private static final BigDecimal LAST_CONTENT = new BigDecimal("4.00");
    private static final boolean LAST_CONTENT_FLAG = true;
    private static final String LAST_CONTENT_SOURCE = "last content source";
    private static final String LAST_CONTENT_COMMENT = "last content comment";
    private static final BigDecimal CONTENT = new BigDecimal("3");
    private static final String CONTENT_SOURCE = "content source";
    private static final String CONTENT_COMMENT = "content comment";
    private static final boolean CONTENT_FLAG = false;
    private static final BigDecimal CONTENT_UNIT_PRICE = new BigDecimal("1550.40");
    private static final boolean CUP_FLAG = false;
    private static final String COMMENT = "comment";
    private static final String LAST_COMMENT = "last comment";
    private static final String USER_NAME = "user@copyright.com";

    static {
        PUBLICATION_TYPE = new PublicationType();
        PUBLICATION_TYPE.setName("BK2");
        PUBLICATION_TYPE.setDescription("Book series");
    }

    private UdmViewValueWindow window;
    private IUdmValueController controller;
    private UdmValueDto udmValue;

    @Before
    public void setUp() {
        buildUdmValueDto();
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IUdmValueController.class);
        expect(controller.getAllCurrencies()).andReturn(List.of(CURRENCY)).once();
    }

    @Test
    public void testConstructor() {
        setSpecialistExpectations();
        initViewWindow();
        verifyWindow(window, "View UDM Value", 960, 700, Unit.PIXELS);
        VerticalLayout verticalLayout = verifyRootLayout(window.getContent());
        verifyPanel(verticalLayout.getComponent(0));
    }

    @Test
    public void testUdmValueDataBinding() {
        setSpecialistExpectations();
        initViewWindow();
        VerticalLayout verticalLayout = getPanelContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        VerticalLayout row1 = (VerticalLayout) horizontalLayout.getComponent(0);
        assertEquals(2, row1.getComponentCount());
        Panel workPanel = (Panel) row1.getComponent(0);
        VerticalLayout workContent = (VerticalLayout) workPanel.getContent();
        assertEquals(5, workContent.getComponentCount());
        assertTextFieldValue(workContent.getComponent(0), SYSTEM_TITLE);
        assertTextFieldValue(workContent.getComponent(1), WR_WRK_INST.toString());
        assertTextFieldValue(workContent.getComponent(2), SYSTEM_STANDARD_NUMBER);
        assertTextFieldValue(workContent.getComponent(3), RH_NAME);
        assertTextFieldValue(workContent.getComponent(4), RH_ACCOUNT_NUMBER.toString());
        Panel pricePanel = (Panel) row1.getComponent(1);
        VerticalLayout priceContent = (VerticalLayout) pricePanel.getContent();
        assertEquals(15, priceContent.getComponentCount());
        assertTextFieldValue(priceContent.getComponent(0), PRICE.toString());
        assertTextFieldValue(priceContent.getComponent(1),
            String.format("%s - %s", CURRENCY.getCode(), CURRENCY.getDescription()));
        assertTextFieldValue(priceContent.getComponent(2), CURRENCY_EXCHANGE_RATE.toString());
        assertTextFieldValue(priceContent.getComponent(3), "12/31/2020");
        assertTextFieldValue(priceContent.getComponent(4), PRICE_IN_USD.toString());
        assertTextFieldValue(priceContent.getComponent(5), PRICE_TYPE);
        assertTextFieldValue(priceContent.getComponent(6), PRICE_ACCESS_TYPE);
        assertTextFieldValue(priceContent.getComponent(7), PRICE_YEAR.toString());
        assertTextFieldValue(priceContent.getComponent(8), PRICE_SOURCE);
        assertTextFieldValue(priceContent.getComponent(9), PRICE_COMMENT);
        assertTextFieldValue(priceContent.getComponent(10), "Y");
        assertTextFieldValue(priceContent.getComponent(11), LAST_PRICE_IN_USD.toString());
        assertTextFieldValue(priceContent.getComponent(12), LAST_PRICE_SOURCE);
        assertTextFieldValue(priceContent.getComponent(13), LAST_PRICE_COMMENT);
        assertTextFieldValue(priceContent.getComponent(14), "N");
        VerticalLayout row2 = (VerticalLayout) horizontalLayout.getComponent(1);
        assertEquals(5, row2.getComponentCount());
        Panel generalPanel = (Panel) row2.getComponent(0);
        VerticalLayout generalContent = (VerticalLayout) generalPanel.getContent();
        assertEquals(4, generalContent.getComponentCount());
        assertTextFieldValue(generalContent.getComponent(0), VALUE_PERIOD.toString());
        assertTextFieldValue(generalContent.getComponent(1), LAST_VALUE_PERIOD.toString());
        assertTextFieldValue(generalContent.getComponent(2), ASSIGNEE);
        assertTextFieldValue(generalContent.getComponent(3), STATUS.name());
        Panel pubTypePanel = (Panel) row2.getComponent(1);
        VerticalLayout pubTypeContent = (VerticalLayout) pubTypePanel.getContent();
        assertEquals(2, pubTypeContent.getComponentCount());
        assertTextFieldValue(pubTypeContent.getComponent(0),
            String.format("%s - %s", PUBLICATION_TYPE.getName(), PUBLICATION_TYPE.getDescription()));
        assertTextFieldValue(pubTypeContent.getComponent(1), LAST_PUB_TYPE);
        Panel contentPanel = (Panel) row2.getComponent(2);
        VerticalLayout contentContent = (VerticalLayout) contentPanel.getContent();
        assertEquals(10, contentContent.getComponentCount());
        assertTextFieldValue(contentContent.getComponent(0), "3.00");
        assertTextFieldValue(contentContent.getComponent(1), CONTENT_SOURCE);
        assertTextFieldValue(contentContent.getComponent(2), CONTENT_COMMENT);
        assertTextFieldValue(contentContent.getComponent(3), "N");
        assertTextFieldValue(contentContent.getComponent(4), LAST_CONTENT.toString());
        assertTextFieldValue(contentContent.getComponent(5), LAST_CONTENT_SOURCE);
        assertTextFieldValue(contentContent.getComponent(6), LAST_CONTENT_COMMENT);
        assertTextFieldValue(contentContent.getComponent(7), "Y");
        assertTextFieldValue(contentContent.getComponent(8), CONTENT_UNIT_PRICE.toString());
        assertTextFieldValue(contentContent.getComponent(9), "N");
        Panel commentPanel = (Panel) row2.getComponent(3);
        VerticalLayout commentContent = (VerticalLayout) commentPanel.getContent();
        assertEquals(2, commentContent.getComponentCount());
        assertTextFieldValue(commentContent.getComponent(0), COMMENT);
        assertTextFieldValue(commentContent.getComponent(1), LAST_COMMENT);
        Panel updatePanel = (Panel) row2.getComponent(4);
        VerticalLayout updateContent = (VerticalLayout) updatePanel.getContent();
        assertEquals(2, updateContent.getComponentCount());
        assertTextFieldValue(updateContent.getComponent(0), USER_NAME);
        assertTextFieldValue(updateContent.getComponent(1), "12/31/2020");
    }

    private VerticalLayout verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyButtonsLayout(verticalLayout.getComponent(1), "Close");
        return verticalLayout;
    }

    private void verifyPanel(Component component) {
        assertThat(component, instanceOf(Panel.class));
        Component panelContent = ((Panel) component).getContent();
        assertThat(panelContent, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(1, verticalLayout.getComponentCount());
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        verifyRow1(horizontalLayout);
        verifyRow2(horizontalLayout);
    }

    private void verifyRow1(HorizontalLayout horizontalLayout) {
        VerticalLayout row1 = (VerticalLayout) horizontalLayout.getComponent(0);
        Panel workPanel = (Panel) row1.getComponent(0);
        assertEquals("Work Information", workPanel.getCaption());
        VerticalLayout workContent = (VerticalLayout) workPanel.getContent();
        assertEquals(5, workContent.getComponentCount());
        verifyTextFieldLayout(workContent.getComponent(0), "System Title");
        verifyTextFieldLayout(workContent.getComponent(1), "Wr Wrk Inst");
        verifyTextFieldLayout(workContent.getComponent(2), "System Standard Number");
        verifyTextFieldLayout(workContent.getComponent(3), "RH Name");
        verifyTextFieldLayout(workContent.getComponent(4), "RH Account #");
        Panel pricePanel = (Panel) row1.getComponent(1);
        assertEquals("Price", pricePanel.getCaption());
        VerticalLayout priceContent = (VerticalLayout) pricePanel.getContent();
        assertEquals(15, priceContent.getComponentCount());
        verifyTextFieldLayout(priceContent.getComponent(0), "Price");
        verifyTextFieldLayout(priceContent.getComponent(1), "Currency");
        verifyTextFieldLayout(priceContent.getComponent(2), "Currency Exchange Rate");
        verifyTextFieldLayout(priceContent.getComponent(3), "Currency Exchange Rate Date");
        verifyTextFieldLayout(priceContent.getComponent(4), "Price in USD");
        verifyTextFieldLayout(priceContent.getComponent(5), "Price Type");
        verifyTextFieldLayout(priceContent.getComponent(6), "Price Access Type");
        verifyTextFieldLayout(priceContent.getComponent(7), "Price Year");
        verifyTextFieldLayout(priceContent.getComponent(8), "Price Source");
        verifyTextFieldLayout(priceContent.getComponent(9), "Price Comment");
        verifyTextFieldLayout(priceContent.getComponent(10), "Price Flag");
        verifyTextFieldLayout(priceContent.getComponent(11), "Last Price in USD");
        verifyTextFieldLayout(priceContent.getComponent(12), "Last Price Source");
        verifyTextFieldLayout(priceContent.getComponent(13), "Last Price Comment");
        verifyTextFieldLayout(priceContent.getComponent(14), "Last Price Flag");
    }

    private void verifyRow2(HorizontalLayout horizontalLayout) {
        VerticalLayout row2 = (VerticalLayout) horizontalLayout.getComponent(1);
        assertEquals(5, row2.getComponentCount());
        Panel generalPanel = (Panel) row2.getComponent(0);
        assertEquals("General", generalPanel.getCaption());
        VerticalLayout generalContent = (VerticalLayout) generalPanel.getContent();
        assertEquals(4, generalContent.getComponentCount());
        verifyTextFieldLayout(generalContent.getComponent(0), "Value Period");
        verifyTextFieldLayout(generalContent.getComponent(1), "Last Value Period");
        verifyTextFieldLayout(generalContent.getComponent(2), "Assignee");
        verifyTextFieldLayout(generalContent.getComponent(3), "Value Status");
        Panel pubTypePanel = (Panel) row2.getComponent(1);
        assertEquals("Publication Type", pubTypePanel.getCaption());
        VerticalLayout pubTypeContent = (VerticalLayout) pubTypePanel.getContent();
        assertEquals(2, pubTypeContent.getComponentCount());
        verifyTextFieldLayout(pubTypeContent.getComponent(0), "Pub Type");
        verifyTextFieldLayout(pubTypeContent.getComponent(1), "Last Pub Type");
        Panel contentPanel = (Panel) row2.getComponent(2);
        assertEquals("Content", contentPanel.getCaption());
        VerticalLayout contentContent = (VerticalLayout) contentPanel.getContent();
        assertEquals(10, contentContent.getComponentCount());
        verifyTextFieldLayout(contentContent.getComponent(0), "Content");
        verifyTextFieldLayout(contentContent.getComponent(1), "Content Source");
        verifyTextFieldLayout(contentContent.getComponent(2), "Content Comment");
        verifyTextFieldLayout(contentContent.getComponent(3), "Content Flag");
        verifyTextFieldLayout(contentContent.getComponent(4), "Last Content");
        verifyTextFieldLayout(contentContent.getComponent(5), "Last Content Source");
        verifyTextFieldLayout(contentContent.getComponent(6), "Last Content Comment");
        verifyTextFieldLayout(contentContent.getComponent(7), "Last Content Flag");
        verifyTextFieldLayout(contentContent.getComponent(8), "Content Unit Price");
        verifyTextFieldLayout(contentContent.getComponent(9), "CUP Flag");
        Panel commentPanel = (Panel) row2.getComponent(3);
        assertEquals("Comment", commentPanel.getCaption());
        VerticalLayout commentContent = (VerticalLayout) commentPanel.getContent();
        assertEquals(2, commentContent.getComponentCount());
        verifyTextFieldLayout(commentContent.getComponent(0), "Comment");
        verifyTextFieldLayout(commentContent.getComponent(1), "Last Comment");
        Panel updatePanel = (Panel) row2.getComponent(4);
        assertNull(updatePanel.getCaption());
        VerticalLayout updateContent = (VerticalLayout) updatePanel.getContent();
        assertEquals(2, updateContent.getComponentCount());
        verifyTextFieldLayout(updateContent.getComponent(0), "Updated By");
        verifyTextFieldLayout(updateContent.getComponent(1), "Updated Date");
    }

    private void verifyTextFieldLayout(Component component, String caption) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption, ContentMode.TEXT, 175);
        TextField textField = verifyTextField(layout.getComponent(1), null, StringUtils.EMPTY);
        assertTrue(textField.isReadOnly());
    }

    private void assertTextFieldValue(Component component, String expectedValue) {
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(expectedValue, ((TextField) layout.getComponent(1)).getValue());
    }

    private void buildUdmValueDto() {
        udmValue = new UdmValueDto();
        udmValue.setId(UDM_VALUE_UID);
        udmValue.setValuePeriod(VALUE_PERIOD);
        udmValue.setStatus(STATUS);
        udmValue.setAssignee(ASSIGNEE);
        udmValue.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        udmValue.setRhName(RH_NAME);
        udmValue.setWrWrkInst(WR_WRK_INST);
        udmValue.setSystemTitle(SYSTEM_TITLE);
        udmValue.setSystemStandardNumber(SYSTEM_STANDARD_NUMBER);
        udmValue.setLastValuePeriod(LAST_VALUE_PERIOD);
        udmValue.setLastPubType(LAST_PUB_TYPE);
        udmValue.setPublicationType(PUBLICATION_TYPE);
        udmValue.setLastPriceInUsd(LAST_PRICE_IN_USD);
        udmValue.setLastPriceFlag(LAST_PRICE_FLAG);
        udmValue.setLastPriceSource(LAST_PRICE_SOURCE);
        udmValue.setLastPriceComment(LAST_PRICE_COMMENT);
        udmValue.setPrice(PRICE);
        udmValue.setPriceSource(PRICE_SOURCE);
        udmValue.setCurrency(CURRENCY_CODE);
        udmValue.setPriceType(PRICE_TYPE);
        udmValue.setPriceAccessType(PRICE_ACCESS_TYPE);
        udmValue.setPriceYear(PRICE_YEAR);
        udmValue.setPriceComment(PRICE_COMMENT);
        udmValue.setPriceInUsd(PRICE_IN_USD);
        udmValue.setPriceFlag(PRICE_FLAG);
        udmValue.setCurrencyExchangeRate(CURRENCY_EXCHANGE_RATE);
        udmValue.setCurrencyExchangeRateDate(CURRENCY_EXCHANGE_RATE_DATE);
        udmValue.setLastContent(LAST_CONTENT);
        udmValue.setLastContentFlag(LAST_CONTENT_FLAG);
        udmValue.setLastContentSource(LAST_CONTENT_SOURCE);
        udmValue.setLastContentComment(LAST_CONTENT_COMMENT);
        udmValue.setContent(CONTENT);
        udmValue.setContentSource(CONTENT_SOURCE);
        udmValue.setContentComment(CONTENT_COMMENT);
        udmValue.setContentFlag(CONTENT_FLAG);
        udmValue.setContentUnitPrice(CONTENT_UNIT_PRICE);
        udmValue.setContentUnitPriceFlag(CUP_FLAG);
        udmValue.setComment(COMMENT);
        udmValue.setLastComment(LAST_COMMENT);
        udmValue.setCreateDate(Date.from(LocalDate.of(2019, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        udmValue.setUpdateUser(USER_NAME);
        udmValue.setUpdateDate(Date.from(LocalDate.of(2020, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    private VerticalLayout getPanelContent() {
        return (VerticalLayout) ((Panel) ((VerticalLayout) window.getContent()).getComponent(0)).getContent();
    }

    private void initViewWindow() {
        replay(controller, ForeignSecurityUtils.class);
        window = new UdmViewValueWindow(controller, udmValue);
        verify(controller, ForeignSecurityUtils.class);
    }

    private void setSpecialistExpectations() {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(true);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(false);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(false);
    }
}
