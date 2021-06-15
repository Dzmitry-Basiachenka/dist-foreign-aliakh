package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;

/**
 * Verifies {@link UdmFiltersWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Ihar Suvorau
 */
public class UdmFiltersWindowTest {

    private static final LocalDate DATE_FROM = LocalDate.of(2021, 1, 1);
    private static final LocalDate DATE_TO = LocalDate.of(2021, 1, 2);
    private static final String COMPANY_NAME = "Skadden, Arps, Slate, Meagher & Flom LLP";
    private static final String SURVEY_COUNTRY = "United States";
    private static final String LANGUAGE = "English";

    private final UdmFiltersWindow window = new UdmFiltersWindow(createMock(IUdmUsageFilterController.class));

    @Before
    public void setUp() {
        window.setUdmUsageFilter(new UdmUsageFilter());
    }

    @Test
    public void testConstructor() {
        assertEquals("UDM additional filters", window.getCaption());
        assertEquals(550, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(560, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFilterOperatorChangeListener() {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        HorizontalLayout annualMultiplierLayout = (HorizontalLayout) verticalLayout.getComponent(8);
        TextField fromField = (TextField) annualMultiplierLayout.getComponent(0);
        TextField toField = (TextField) annualMultiplierLayout.getComponent(1);
        ComboBox<FilterOperatorEnum> filterOperatorComboBox =
            (ComboBox<FilterOperatorEnum>) annualMultiplierLayout.getComponent(2);
        assertEquals(FilterOperatorEnum.EQUALS, filterOperatorComboBox.getValue());
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        filterOperatorComboBox.setValue(FilterOperatorEnum.GREATER_THAN);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        filterOperatorComboBox.setValue(FilterOperatorEnum.LESS_THAN);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        filterOperatorComboBox.setValue(FilterOperatorEnum.BETWEEN);
        assertTrue(fromField.isEnabled());
        assertTrue(toField.isEnabled());
    }

    @Test
    public void testSaveButtonClickListener() {
        UdmUsageFilter appliedUsageFilter = window.getAppliedUsageFilter();
        assertTrue(appliedUsageFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(12);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), window.getAppliedUsageFilter());
    }

    @Test
    public void testClearButtonClickListener() {
        window.setUdmUsageFilter(buildExpectedFilter());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(12);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        assertTrue(window.getAppliedUsageFilter().isEmpty());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(13, verticalLayout.getComponentCount());
        verifyItemsFilterLayout(verticalLayout.getComponent(0), "Assignees", "Detail Licensee Classes");
        verifyItemsFilterLayout(verticalLayout.getComponent(1), "Reported Pub Types", "Types of Use");
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "Publication Formats");
        verifyDateFieldComponent(verticalLayout.getComponent(3), "Usage Date From", "Usage Date To");
        verifyDateFieldComponent(verticalLayout.getComponent(4), "Survey Start Date From", "Survey Start Date To");
        verifyChannelWrWkrInstLayout(verticalLayout.getComponent(5));
        verifyTextFieldLayout(verticalLayout.getComponent(6), "Company Id", "Company Name");
        verifyTextFieldLayout(verticalLayout.getComponent(7), "Survey Country", "Language");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(8), "Annual Multiplier From",
            "Annual Multiplier To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(9), "Annualized Copies From",
            "Annualized Copies To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(10), "Statistical Multiplier From",
            "Statistical Multiplier To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(11), "Quantity From", "Quantity To");
        verifyButtonsLayout(verticalLayout.getComponent(12));
    }

    private void verifyItemsFilterLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyItemsFilterWidget(layout.getComponent(0), firstCaption);
        verifyItemsFilterWidget(layout.getComponent(1), secondCaption);
    }

    private void verifyItemsFilterWidget(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals(caption, button.getCaption());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
    }

    private void verifyDateFieldComponent(Component component, String captionFrom, String captionTo) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof LocalDateWidget);
        assertEquals(captionFrom, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof LocalDateWidget);
        assertEquals(captionTo, layout.getComponent(1).getCaption());
    }

    @SuppressWarnings("unchecked")
    private void verifyChannelWrWkrInstLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        ComboBox<UdmChannelEnum> channelComboBox = (ComboBox<UdmChannelEnum>) layout.getComponent(0);
        assertEquals(100, channelComboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, channelComboBox.getWidthUnits());
        assertEquals(channelComboBox.getCaption(), "Channel");
        verifyTextFieldComponent(layout.getComponent(1), "Wr Wrk Inst");
    }

    private void verifyFieldWithOperatorComponent(Component component, String captionFrom, String captionTo) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof TextField);
        assertEquals(captionFrom, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof TextField);
        assertEquals(captionTo, layout.getComponent(1).getCaption());
        assertTrue(layout.getComponent(2) instanceof ComboBox);
        assertEquals("Operator", layout.getComponent(2).getCaption());
    }

    private void verifyTextFieldLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyTextFieldComponent(layout.getComponent(0), firstCaption);
        verifyTextFieldComponent(layout.getComponent(1), secondCaption);
    }

    private void verifyTextFieldComponent(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Clear");
        verifyButton(layout.getComponent(2), "Close");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }

    private UdmUsageFilter buildExpectedFilter() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUsageDateFrom(DATE_FROM);
        filter.setUsageDateTo(DATE_TO);
        filter.setSurveyStartDateFrom(DATE_FROM);
        filter.setSurveyStartDateTo(DATE_TO);
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setWrWrkInst(243904752L);
        filter.setCompanyId(454984566L);
        filter.setCompanyName(COMPANY_NAME);
        filter.setSurveyCountry(SURVEY_COUNTRY);
        filter.setLanguage(LANGUAGE);
        filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 1, 10));
        filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, new BigDecimal("5.5"), null));
        filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, new BigDecimal("2.2"), null));
        filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 3, null));
        return filter;
    }

    @SuppressWarnings("unchecked")
    private void populateData() {
        ((LocalDateWidget) Whitebox.getInternalState(window, "usageDateFromWidget")).setValue(DATE_FROM);
        ((LocalDateWidget) Whitebox.getInternalState(window, "usageDateToWidget")).setValue(DATE_TO);
        ((LocalDateWidget) Whitebox.getInternalState(window, "surveyStartFromWidget")).setValue(DATE_FROM);
        ((LocalDateWidget) Whitebox.getInternalState(window, "surveyStartToWidget")).setValue(DATE_TO);
        ((ComboBox<UdmChannelEnum>) Whitebox.getInternalState(window, "channelComboBox")).setValue(UdmChannelEnum.CCC);
        ((TextField) Whitebox.getInternalState(window, "wrWrkInstField")).setValue("243904752");
        ((TextField) Whitebox.getInternalState(window, "companyIdField")).setValue("454984566");
        ((TextField) Whitebox.getInternalState(window, "companyNameField")).setValue(COMPANY_NAME);
        ((TextField) Whitebox.getInternalState(window, "surveyCountryField")).setValue(SURVEY_COUNTRY);
        ((TextField) Whitebox.getInternalState(window, "languageField")).setValue(LANGUAGE);
        ((TextField) Whitebox.getInternalState(window, "annualMultiplierFromField")).setValue("1");
        ((TextField) Whitebox.getInternalState(window, "annualMultiplierToField")).setValue("10");
        ((ComboBox<FilterOperatorEnum>) Whitebox.getInternalState(window,
            "annualMultiplierOperatorComboBox")).setValue(FilterOperatorEnum.BETWEEN);
        ((TextField) Whitebox.getInternalState(window, "annualizedCopiesFromField")).setValue("5.5");
        ((ComboBox<FilterOperatorEnum>) Whitebox.getInternalState(window,
            "annualizedCopiesOperatorComboBox")).setValue(FilterOperatorEnum.EQUALS);
        ((TextField) Whitebox.getInternalState(window, "statisticalMultiplierFromField")).setValue("2.2");
        ((ComboBox<FilterOperatorEnum>) Whitebox.getInternalState(window,
            "statisticalMultiplierOperatorComboBox")).setValue(FilterOperatorEnum.GREATER_THAN);
        ((TextField) Whitebox.getInternalState(window, "quantityFromField")).setValue("3");
        ((ComboBox<FilterOperatorEnum>) Whitebox.getInternalState(window, "quantityOperatorComboBox"))
            .setValue(FilterOperatorEnum.LESS_THAN);
    }
}
