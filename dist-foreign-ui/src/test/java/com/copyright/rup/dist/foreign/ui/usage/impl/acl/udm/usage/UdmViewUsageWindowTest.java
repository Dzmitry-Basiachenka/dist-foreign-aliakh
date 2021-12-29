package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmUsageWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Verifies {@link UdmViewUsageWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/24/2021
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class UdmViewUsageWindowTest {

    private static final String UDM_USAGE_UID = "3bcc288a-7024-45bc-b79c-bddbe9673cda";
    private static final String UDM_USAGE_ORIGINAL_DETAIL_UID = "OGN674GHHSB0026";
    private static final String REPORTED_STANDARD_NUMBER = "0927-7766";
    private static final String STANDARD_NUMBER = "2192-3557";
    private static final String REPORTED_TYPE_OF_USE = "EMAIL_COPY";
    private static final String REPORTED_TITLE = "Colloids and surfaces. B, Biointerfaces";
    private static final String SYSTEM_TITLE = "Brain surgery";
    private static final String PUB_TYPE = "Journal";
    private static final String PUBLICATION_FORMAT = "Digital";
    private static final String ARTICLE = "Green chemistry";
    private static final String LANGUAGE = "English";
    private static final String SURVEY_COUNTRY = "United States";
    private static final LocalDate USAGE_DATE = LocalDate.of(2020, 12, 12);
    private static final LocalDate SURVEY_START_DATE = LocalDate.of(2019, 12, 12);
    private static final LocalDate SURVEY_END_DATE = LocalDate.of(2022, 12, 12);
    private static final String SURVEY_RESPONDENT = "61b3f9e4-5ae8-491f-a518-f5248ac030a3";
    private static final Long WR_WRK_INST = 122825347L;
    private static final Long RH_ACCOUNT_NUMBER = 1000023041L;
    private static final String RH_NAME = "American College of Physicians - Journals";
    private static final Long COMPANY_ID = 454984566L;
    private static final String COMPANY_NAME = "Skadden, Arps, Slate, Meagher & Flom LLP";
    private static final Long QUANTITY = 10L;
    private static final Integer ANNUAL_MULTIPLIER = 1;
    private static final BigDecimal STATISTICAL_MULTIPLIER = new BigDecimal("1.00000");
    private static final BigDecimal ANNUALIZED_COPIES = new BigDecimal("10.00000");
    private static final String IP_ADDRESS = "ip24.12.119.203";
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final String USER_NAME = "user@copyright.com";
    private static final String COMMENT = "Should be reviewed by Specialist";
    private static final String RESEARCH_URL = "google.com";
    private static final DetailLicenseeClass LICENSEE_CLASS = new DetailLicenseeClass(25, "Business Services");
    private static final UdmActionReason ACTION_REASON =
        new UdmActionReason("97fd8093-7f36-4a09-99f1-1bfe36a5c3f4", "Arbitrary RFA search result order");
    private static final UdmIneligibleReason INELIGIBLE_REASON =
        new UdmIneligibleReason("b60a726a-39e8-4303-abe1-6816da05b858", "Invalid survey");

    private CommonUdmUsageWindow window;
    private UdmUsageDto udmUsage;

    @Before
    public void setUp() {
        buildUdmUsageDto();
        mockStatic(ForeignSecurityUtils.class);
    }

    @Test
    public void testConstructorSpecialist() {
        setSpecialistExpectations();
        VerticalLayout verticalLayout = verifyWindowAndGetContent();
        verifyPanelSpecialistManagerView(verticalLayout.getComponent(0));
    }

    @Test
    public void testConstructorManager() {
        setManagerExpectations();
        VerticalLayout verticalLayout = verifyWindowAndGetContent();
        verifyPanelSpecialistManagerView(verticalLayout.getComponent(0));
    }

    @Test
    public void testConstructorViewOnly() {
        setViewOnlyExpectations();
        VerticalLayout verticalLayout = verifyWindowAndGetContent();
        verifyPanelSpecialistManagerView(verticalLayout.getComponent(0));
    }

    @Test
    public void testConstructorResearcher() {
        setResearcherExpectations();
        VerticalLayout verticalLayout = verifyWindowAndGetContent();
        verifyPanelResearcher(verticalLayout.getComponent(0));
    }

    @Test
    public void testUdmUsageDataBinding() {
        setSpecialistExpectations();
        initViewWindow();
        VerticalLayout verticalLayout =
            (VerticalLayout) ((Panel) ((VerticalLayout) window.getContent()).getComponent(0)).getContent();
        assertTextFieldValue(verticalLayout.getComponent(0), UDM_USAGE_UID);
        assertTextFieldValue(verticalLayout.getComponent(1), "202012");
        assertTextFieldValue(verticalLayout.getComponent(2), "SS");
        assertTextFieldValue(verticalLayout.getComponent(3), UDM_USAGE_ORIGINAL_DETAIL_UID);
        assertTextFieldValue(verticalLayout.getComponent(4), UsageStatusEnum.INELIGIBLE.name());
        assertTextFieldValue(verticalLayout.getComponent(5), ASSIGNEE);
        assertTextFieldValue(verticalLayout.getComponent(6), "1000023041");
        assertTextFieldValue(verticalLayout.getComponent(7), RH_NAME);
        assertTextFieldValue(verticalLayout.getComponent(8), "122825347");
        assertTextFieldValue(verticalLayout.getComponent(9), REPORTED_TITLE);
        assertTextFieldValue(verticalLayout.getComponent(10), SYSTEM_TITLE);
        assertTextFieldValue(verticalLayout.getComponent(11), REPORTED_STANDARD_NUMBER);
        assertTextFieldValue(verticalLayout.getComponent(12), STANDARD_NUMBER);
        assertTextFieldValue(verticalLayout.getComponent(13), PUB_TYPE);
        assertTextFieldValue(verticalLayout.getComponent(14), PUBLICATION_FORMAT);
        assertTextFieldValue(verticalLayout.getComponent(15), ARTICLE);
        assertTextFieldValue(verticalLayout.getComponent(16), LANGUAGE);
        assertTextFieldValue(verticalLayout.getComponent(17), ACTION_REASON.getReason());
        assertTextFieldValue(verticalLayout.getComponent(18), COMMENT);
        assertTextFieldValue(verticalLayout.getComponent(19), RESEARCH_URL);
        assertTextFieldValue(verticalLayout.getComponent(20), "454984566");
        assertTextFieldValue(verticalLayout.getComponent(21), COMPANY_NAME);
        assertTextFieldValue(verticalLayout.getComponent(22),
            String.format("%s - %s", LICENSEE_CLASS.getId(), LICENSEE_CLASS.getDescription()));
        assertTextFieldValue(verticalLayout.getComponent(23), SURVEY_RESPONDENT);
        assertTextFieldValue(verticalLayout.getComponent(24), IP_ADDRESS);
        assertTextFieldValue(verticalLayout.getComponent(25), SURVEY_COUNTRY);
        assertTextFieldValue(verticalLayout.getComponent(26), "CCC");
        assertTextFieldValue(verticalLayout.getComponent(27), "12/12/2020");
        assertTextFieldValue(verticalLayout.getComponent(28), "12/12/2019");
        assertTextFieldValue(verticalLayout.getComponent(29), "12/12/2022");
        assertTextFieldValue(verticalLayout.getComponent(30), "1");
        assertTextFieldValue(verticalLayout.getComponent(31), "1.00000");
        assertTextFieldValue(verticalLayout.getComponent(32), REPORTED_TYPE_OF_USE);
        assertTextFieldValue(verticalLayout.getComponent(33), "10");
        assertTextFieldValue(verticalLayout.getComponent(34), "10.00000");
        assertTextFieldValue(verticalLayout.getComponent(35), INELIGIBLE_REASON.getReason());
        assertTextFieldValue(verticalLayout.getComponent(36), "01/01/2016");
        assertTextFieldValue(verticalLayout.getComponent(37), USER_NAME);
        assertTextFieldValue(verticalLayout.getComponent(38), "12/12/2020");
    }

    private void buildUdmUsageDto() {
        udmUsage = new UdmUsageDto();
        udmUsage.setId(UDM_USAGE_UID);
        udmUsage.setPeriod(202012);
        udmUsage.setUsageOrigin(UdmUsageOriginEnum.SS);
        udmUsage.setOriginalDetailId(UDM_USAGE_ORIGINAL_DETAIL_UID);
        udmUsage.setStatus(UsageStatusEnum.INELIGIBLE);
        udmUsage.setAssignee(ASSIGNEE);
        udmUsage.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        udmUsage.setRhName(RH_NAME);
        udmUsage.setWrWrkInst(WR_WRK_INST);
        udmUsage.setReportedTitle(REPORTED_TITLE);
        udmUsage.setSystemTitle(SYSTEM_TITLE);
        udmUsage.setReportedStandardNumber(REPORTED_STANDARD_NUMBER);
        udmUsage.setStandardNumber(STANDARD_NUMBER);
        udmUsage.setReportedPubType(PUB_TYPE);
        udmUsage.setPubFormat(PUBLICATION_FORMAT);
        udmUsage.setArticle(ARTICLE);
        udmUsage.setLanguage(LANGUAGE);
        udmUsage.setActionReason(ACTION_REASON);
        udmUsage.setComment(COMMENT);
        udmUsage.setResearchUrl(RESEARCH_URL);
        udmUsage.setDetailLicenseeClass(LICENSEE_CLASS);
        udmUsage.setCompanyId(COMPANY_ID);
        udmUsage.setCompanyName(COMPANY_NAME);
        udmUsage.setSurveyRespondent(SURVEY_RESPONDENT);
        udmUsage.setIpAddress(IP_ADDRESS);
        udmUsage.setSurveyCountry(SURVEY_COUNTRY);
        udmUsage.setChannel(UdmChannelEnum.CCC);
        udmUsage.setUsageDate(USAGE_DATE);
        udmUsage.setSurveyStartDate(SURVEY_START_DATE);
        udmUsage.setSurveyEndDate(SURVEY_END_DATE);
        udmUsage.setAnnualMultiplier(ANNUAL_MULTIPLIER);
        udmUsage.setStatisticalMultiplier(STATISTICAL_MULTIPLIER);
        udmUsage.setReportedTypeOfUse(REPORTED_TYPE_OF_USE);
        udmUsage.setQuantity(QUANTITY);
        udmUsage.setAnnualizedCopies(ANNUALIZED_COPIES);
        udmUsage.setIneligibleReason(INELIGIBLE_REASON);
        udmUsage.setCreateDate(Date.from(LocalDate.of(2016, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        udmUsage.setUpdateUser(USER_NAME);
        udmUsage.setUpdateDate(Date.from(LocalDate.of(2020, 12, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    private void setSpecialistExpectations() {
        setPermissionsExpectations(true, false, false);
    }

    private void setManagerExpectations() {
        setPermissionsExpectations(false, true, false);
    }

    private void setResearcherExpectations() {
        setPermissionsExpectations(false, false, true);
    }

    private void setViewOnlyExpectations() {
        setPermissionsExpectations(false, false, false);
    }

    private void setPermissionsExpectations(boolean isSpecialist, boolean isManager, boolean isResearcher) {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(isSpecialist);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(isManager);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(isResearcher);
    }

    private VerticalLayout verifyWindowAndGetContent() {
        initViewWindow();
        verifyWindow(window, "View UDM Usage", 650, 700, Unit.PIXELS);
        return verifyRootLayout(window.getContent());
    }

    private void initViewWindow() {
        replay(ForeignSecurityUtils.class);
        window = new UdmViewUsageWindow(udmUsage);
        verify(ForeignSecurityUtils.class);
    }

    private VerticalLayout verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyButtonsLayout(verticalLayout.getComponent(1), "Close");
        return verticalLayout;
    }

    private void verifyPanelSpecialistManagerView(Component component) {
        assertTrue(component instanceof Panel);
        Component panelContent = ((Panel) component).getContent();
        assertTrue(panelContent instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(39, verticalLayout.getComponentCount());
        verifyTextFieldLayout(verticalLayout.getComponent(0), "Detail ID");
        verifyTextFieldLayout(verticalLayout.getComponent(1), "Period");
        verifyTextFieldLayout(verticalLayout.getComponent(2), "Usage Origin");
        verifyTextFieldLayout(verticalLayout.getComponent(3), "Usage Detail ID");
        verifyTextFieldLayout(verticalLayout.getComponent(4), "Detail Status");
        verifyTextFieldLayout(verticalLayout.getComponent(5), "Assignee");
        verifyTextFieldLayout(verticalLayout.getComponent(6), "RH Account #");
        verifyTextFieldLayout(verticalLayout.getComponent(7), "RH Name");
        verifyTextFieldLayout(verticalLayout.getComponent(8), "Wr Wrk Inst");
        verifyTextFieldLayout(verticalLayout.getComponent(9), "Reported Title");
        verifyTextFieldLayout(verticalLayout.getComponent(10), "System Title");
        verifyTextFieldLayout(verticalLayout.getComponent(11), "Reported Standard Number");
        verifyTextFieldLayout(verticalLayout.getComponent(12), "Standard Number");
        verifyTextFieldLayout(verticalLayout.getComponent(13), "Reported Pub Type");
        verifyTextFieldLayout(verticalLayout.getComponent(14), "Publication Format");
        verifyTextFieldLayout(verticalLayout.getComponent(15), "Article");
        verifyTextFieldLayout(verticalLayout.getComponent(16), "Language");
        verifyTextFieldLayout(verticalLayout.getComponent(17), "Action Reason");
        verifyTextFieldLayout(verticalLayout.getComponent(18), "Comment");
        verifyTextFieldLayout(verticalLayout.getComponent(19), "Research URL");
        verifyTextFieldLayout(verticalLayout.getComponent(20), "Company ID");
        verifyTextFieldLayout(verticalLayout.getComponent(21), "Company Name");
        verifyTextFieldLayout(verticalLayout.getComponent(22), "Detail Licensee Class");
        verifyTextFieldLayout(verticalLayout.getComponent(23), "Survey Respondent");
        verifyTextFieldLayout(verticalLayout.getComponent(24), "IP Address");
        verifyTextFieldLayout(verticalLayout.getComponent(25), "Survey Country");
        verifyTextFieldLayout(verticalLayout.getComponent(26), "Channel");
        verifyTextFieldLayout(verticalLayout.getComponent(27), "Usage Date");
        verifyTextFieldLayout(verticalLayout.getComponent(28), "Survey Start Date");
        verifyTextFieldLayout(verticalLayout.getComponent(29), "Survey End Date");
        verifyTextFieldLayout(verticalLayout.getComponent(30), "Annual Multiplier");
        verifyTextFieldLayout(verticalLayout.getComponent(31), "Statistical Multiplier");
        verifyTextFieldLayout(verticalLayout.getComponent(32), "Reported TOU");
        verifyTextFieldLayout(verticalLayout.getComponent(33), "Quantity");
        verifyTextFieldLayout(verticalLayout.getComponent(34), "Annualized Copies");
        verifyTextFieldLayout(verticalLayout.getComponent(35), "Ineligible Reason");
        verifyTextFieldLayout(verticalLayout.getComponent(36), "Load Date");
        verifyTextFieldLayout(verticalLayout.getComponent(37), "Updated By");
        verifyTextFieldLayout(verticalLayout.getComponent(38), "Updated Date");
    }

    private void verifyPanelResearcher(Component component) {
        assertTrue(component instanceof Panel);
        Component panelContent = ((Panel) component).getContent();
        assertTrue(panelContent instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(29, verticalLayout.getComponentCount());
        verifyTextFieldLayout(verticalLayout.getComponent(0), "Detail ID");
        verifyTextFieldLayout(verticalLayout.getComponent(1), "Period");
        verifyTextFieldLayout(verticalLayout.getComponent(2), "Usage Detail ID");
        verifyTextFieldLayout(verticalLayout.getComponent(3), "Detail Status");
        verifyTextFieldLayout(verticalLayout.getComponent(4), "Assignee");
        verifyTextFieldLayout(verticalLayout.getComponent(5), "RH Account #");
        verifyTextFieldLayout(verticalLayout.getComponent(6), "RH Name");
        verifyTextFieldLayout(verticalLayout.getComponent(7), "Wr Wrk Inst");
        verifyTextFieldLayout(verticalLayout.getComponent(8), "Reported Title");
        verifyTextFieldLayout(verticalLayout.getComponent(9), "System Title");
        verifyTextFieldLayout(verticalLayout.getComponent(10), "Reported Standard Number");
        verifyTextFieldLayout(verticalLayout.getComponent(11), "Standard Number");
        verifyTextFieldLayout(verticalLayout.getComponent(12), "Reported Pub Type");
        verifyTextFieldLayout(verticalLayout.getComponent(13), "Publication Format");
        verifyTextFieldLayout(verticalLayout.getComponent(14), "Article");
        verifyTextFieldLayout(verticalLayout.getComponent(15), "Language");
        verifyTextFieldLayout(verticalLayout.getComponent(16), "Action Reason");
        verifyTextFieldLayout(verticalLayout.getComponent(17), "Comment");
        verifyTextFieldLayout(verticalLayout.getComponent(18), "Research URL");
        verifyTextFieldLayout(verticalLayout.getComponent(19), "Detail Licensee Class");
        verifyTextFieldLayout(verticalLayout.getComponent(20), "Channel");
        verifyTextFieldLayout(verticalLayout.getComponent(21), "Usage Date");
        verifyTextFieldLayout(verticalLayout.getComponent(22), "Survey Start Date");
        verifyTextFieldLayout(verticalLayout.getComponent(23), "Survey End Date");
        verifyTextFieldLayout(verticalLayout.getComponent(24), "Reported TOU");
        verifyTextFieldLayout(verticalLayout.getComponent(25), "Ineligible Reason");
        verifyTextFieldLayout(verticalLayout.getComponent(26), "Load Date");
        verifyTextFieldLayout(verticalLayout.getComponent(27), "Updated By");
        verifyTextFieldLayout(verticalLayout.getComponent(28), "Updated Date");
    }

    private void assertTextFieldValue(Component component, String expectedValue) {
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(expectedValue, ((TextField) layout.getComponent(1)).getValue());
    }

    private void verifyTextFieldLayout(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption);
        Component textField = layout.getComponent(1);
        assertTrue(textField instanceof TextField);
        assertEquals(100, textField.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, textField.getWidthUnits());
        assertTrue(((TextField) textField).isReadOnly());
    }

    private void verifyLabel(Component component, String caption) {
        assertTrue(component instanceof Label);
        assertEquals(165, component.getWidth(), 0);
        assertEquals(Unit.PIXELS, component.getWidthUnits());
        assertEquals(caption, ((Label) component).getValue());
    }
}
