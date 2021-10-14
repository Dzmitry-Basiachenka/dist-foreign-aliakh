package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Window to edit UDM value.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/13/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmEditValueWindow extends Window {

    private final Binder<UdmValueDto> binder = new Binder<>();
    private final IUdmValueController controller;
    private final UdmValueDto udmValue;
    private final Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
    private final ClickListener saveButtonClickListener;
    private final TextField valuePeriodField = new TextField();
    private final ComboBox<UdmValueStatusEnum> valueStatusComboBox = new ComboBox<>();
    private final TextField wrWrkInstField = new TextField();
    private final ComboBox<PublicationType> pubTypeComboBox = new ComboBox<>();
    private final TextField priceField = new TextField();
    private final ComboBox<Currency> currencyComboBox = new ComboBox<>();
    private final ComboBox<String> priceTypeComboBox = new ComboBox<>();
    private final ComboBox<String> priceAccessTypeComboBox = new ComboBox<>();
    private final TextField priceYearField = new TextField();
    private final TextField priceSourceField = new TextField();
    private final TextField priceCommentField = new TextField();
    private final TextField contentField = new TextField();
    private final TextField contentSourceField = new TextField();
    private final TextField contentCommentField = new TextField();
    private final TextField commentField = new TextField();

    /**
     * Constructor.
     *
     * @param valueController  instance of {@link IUdmValueController}
     * @param selectedUdmValue UDM value to be displayed on the window
     * @param clickListener    action that should be performed after Save button was clicked
     */
    public UdmEditValueWindow(IUdmValueController valueController, UdmValueDto selectedUdmValue,
                              ClickListener clickListener) {
        controller = valueController;
        udmValue = selectedUdmValue;
        saveButtonClickListener = clickListener;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.edit_udm_value"));
        setResizable(false);
        setWidth(960, Unit.PIXELS);
        setHeight(700, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "edit-udm-value-window");
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        VerticalLayout editFieldsLayout = new VerticalLayout();
        editFieldsLayout.addComponents(
            new HorizontalLayout(
                buildVerticalLayoutWithFixedWidth(
                    new Panel(ForeignUi.getMessage("label.work_information"), new VerticalLayout(
                        buildReadOnlyLayout("label.system_title", UdmValueDto::getSystemTitle),
                        buildWrWrkInstLayout(),
                        buildReadOnlyLayout("label.system_standard_number", UdmValueDto::getSystemStandardNumber),
                        buildReadOnlyLayout("label.standard_number_type", UdmValueDto::getStandardNumberType),
                        buildReadOnlyLayout("label.rh_name", UdmValueDto::getRhName),
                        buildReadOnlyLayout("label.rh_account_number",
                            value -> Objects.toString(value.getRhAccountNumber()))
                    ))),
                buildVerticalLayoutWithFixedWidth(
                    new Panel(ForeignUi.getMessage("label.general"), new VerticalLayout(
                        buildValuePeriodLayout(),
                        buildReadOnlyLayout("label.last_value_period",
                            value -> Objects.toString(value.getLastValuePeriod())),
                        buildReadOnlyLayout("label.assignee", UdmValueDto::getAssignee),
                        initValueStatusLayout()
                    )),
                    new Panel(ForeignUi.getMessage("label.publication_type"), new VerticalLayout(
                        buildPubTypeLayout(),
                        buildReadOnlyLayout("label.last_pub_type", UdmValueDto::getLastPubType)
                    ))
                )
            ),
            new HorizontalLayout(
                buildVerticalLayoutWithFixedWidth(
                    new Panel(ForeignUi.getMessage("label.price"), new VerticalLayout(
                        buildPriceLayout(),
                        buildCurrencyLayout(),
                        buildReadOnlyLayout("label.currency_exchange_rate",
                            value -> Objects.toString(value.getCurrencyExchangeRate())),
                        buildReadOnlyLayout("label.currency_exchange_rate_date",
                            value -> getStringFromLocalDate(value.getCurrencyExchangeRateDate())),
                        buildReadOnlyLayout("label.price_in_usd", value -> Objects.toString(value.getPriceInUsd())),
                        buildPriceTypeLayout(),
                        buildPriceAccessTypeLayout(),
                        buildPriceYearLayout(),
                        buildPriceSourceLayout(),
                        buildPriceCommentLayout(),
                        buildReadOnlyLayout("label.price_flag",
                            value -> BooleanUtils.toYNString(value.isPriceFlag())),
                        buildReadOnlyLayout("label.last_price_in_usd",
                            value -> Objects.toString(value.getLastPriceInUsd())),
                        buildReadOnlyLayout("label.last_price_source", UdmValueDto::getLastPriceSource),
                        buildReadOnlyLayout("label.last_price_comment", UdmValueDto::getLastPriceComment),
                        buildReadOnlyLayout("label.last_price_flag",
                            value -> BooleanUtils.toYNString(value.isLastPriceFlag()))
                    ))),
                buildVerticalLayoutWithFixedWidth(
                    new Panel(ForeignUi.getMessage("label.content"), new VerticalLayout(
                        buildContentLayout(),
                        buildContentSourceLayout(),
                        buildContentCommentLayout(),
                        buildReadOnlyLayout("label.content_flag",
                            value -> BooleanUtils.toYNString(value.isContentFlag())),
                        buildReadOnlyLayout("label.last_content", value -> Objects.toString(value.getLastContent())),
                        buildReadOnlyLayout("label.last_content_source", UdmValueDto::getLastContentSource),
                        buildReadOnlyLayout("label.last_content_comment", UdmValueDto::getLastContentComment),
                        buildReadOnlyLayout("label.last_content_flag",
                            value -> BooleanUtils.toYNString(value.isLastContentFlag())),
                        buildReadOnlyLayout("label.content_unit_price",
                            value -> Objects.toString(value.getContentUnitPrice()))
                    )),
                    new Panel(ForeignUi.getMessage("label.comment"), new VerticalLayout(
                        buildCommentLayout()
                    ))
                )
            ),
            buildReadOnlyLayout("label.updated_by", UdmValueDto::getUpdateUser),
            buildReadOnlyLayout("label.updated_date", usage -> getStringFromDate(usage.getUpdateDate()))
        );
        Panel panel = new Panel(editFieldsLayout);
        panel.setSizeFull();
        editFieldsLayout.setMargin(new MarginInfo(true));
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(panel, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(panel, 1f);
        rootLayout.setSizeFull();
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        binder.validate();
        binder.readBean(udmValue);
        binder.addValueChangeListener(event -> saveButton.setEnabled(binder.hasChanges()));
        return rootLayout;
    }

    private VerticalLayout buildVerticalLayoutWithFixedWidth(Component... children) {
        VerticalLayout verticalLayout = new VerticalLayout(children);
        verticalLayout.setWidth(450, Unit.PIXELS);
        return verticalLayout;
    }

    private HorizontalLayout buildReadOnlyLayout(String caption, ValueProvider<UdmValueDto, String> getter) {
        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.setSizeFull();
        binder.forField(textField).bind(getter, null);
        return buildCommonLayout(textField, ForeignUi.getMessage(caption));
    }

    private HorizontalLayout buildCommonLayout(Component component, String labelCaption) {
        Label label = new Label(labelCaption);
        label.addStyleName(Cornerstone.LABEL_BOLD);
        label.setWidth(175, Unit.PIXELS);
        HorizontalLayout layout = new HorizontalLayout(label, component);
        layout.setSizeFull();
        layout.setExpandRatio(component, 1);
        return layout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        saveButton.setEnabled(false);
        saveButton.addClickListener(event -> {
            try {
                binder.writeBean(udmValue);
                controller.updateValue(udmValue); // TODO add other arguments when implemented
                saveButtonClickListener.buttonClick(event);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(Arrays.asList(valuePeriodField, valueStatusComboBox, wrWrkInstField,
                    pubTypeComboBox, priceField, currencyComboBox, priceTypeComboBox, priceAccessTypeComboBox,
                    priceYearField, priceSourceField, priceCommentField, contentField, contentSourceField,
                    contentCommentField, commentField
                ));
            }
        });
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        discardButton.addClickListener(event -> binder.readBean(udmValue));
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }

    private HorizontalLayout buildValuePeriodLayout() {
        String fieldName = ForeignUi.getMessage("label.value_period");
        valuePeriodField.setSizeFull();
        // TODO add validation
        VaadinUtils.addComponentStyle(valuePeriodField, "udm-value-edit-value-period-field");
        return buildCommonLayout(valuePeriodField, fieldName);
    }

    private HorizontalLayout initValueStatusLayout() {
        valueStatusComboBox.setSizeFull();
        valueStatusComboBox.setEmptySelectionAllowed(false);
        valueStatusComboBox.setItems(UdmValueStatusEnum.NEW,
            UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD,
            UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE,
            UdmValueStatusEnum.NEEDS_FURTHER_REVIEW,
            UdmValueStatusEnum.RESEARCH_COMPLETE);
        valueStatusComboBox.setSelectedItem(udmValue.getStatus());
        VaadinUtils.addComponentStyle(valueStatusComboBox, "udm-value-edit-value-status-combo-box");
        return buildCommonLayout(valueStatusComboBox, ForeignUi.getMessage("label.value_status"));
    }

    private HorizontalLayout buildWrWrkInstLayout() {
        wrWrkInstField.setSizeFull();
        // TODO add validation
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-value-edit-wr-wrk-inst-field");
        return buildCommonLayout(wrWrkInstField, ForeignUi.getMessage("label.wr_wrk_inst"));
    }

    private HorizontalLayout buildPubTypeLayout() {
        pubTypeComboBox.setSizeFull();
        List<PublicationType> publicationTypes = controller.getPublicationTypes();
        pubTypeComboBox.setItems(publicationTypes);
        pubTypeComboBox.setPageLength(12);
        pubTypeComboBox.setItemCaptionGenerator(pubType -> Objects.nonNull(pubType.getName())
            ? String.format("%s - %s", pubType.getName(), pubType.getDescription())
            : StringUtils.EMPTY);
        // TODO pubTypeComboBox.setSelectedItem
        pubTypeComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(pubTypeComboBox, "udm-value-edit-pub-type-combo-box");
        return buildCommonLayout(pubTypeComboBox, ForeignUi.getMessage("label.pub_type"));
    }

    private HorizontalLayout buildPriceLayout() {
        priceField.setSizeFull();
        // TODO add validation
        VaadinUtils.addComponentStyle(priceField, "udm-value-edit-price-field");
        return buildCommonLayout(priceField, ForeignUi.getMessage("label.price"));
    }

    private HorizontalLayout buildCurrencyLayout() {
        currencyComboBox.setSizeFull();
        Map<String, String> currencyCodesToCurrencyNamesMap = controller.getCurrencyCodesToCurrencyNamesMap();
        currencyComboBox.setItems(currencyCodesToCurrencyNamesMap
            .entrySet()
            .stream()
            .map(currency -> new Currency(currency.getKey(), currency.getValue()))
            .collect(Collectors.toList()));
        currencyComboBox.setItemCaptionGenerator(
            currency -> String.format("%s - %s", currency.getCode(), currency.getDescription()));
        currencyComboBox.setSelectedItem(Objects.isNull(udmValue.getCurrency())
            ? null
            : new Currency(udmValue.getCurrency(), currencyCodesToCurrencyNamesMap.get(udmValue.getCurrency())));
        // TODO add validation
        VaadinUtils.addComponentStyle(currencyComboBox, "udm-value-edit-currency-combo-box");
        return buildCommonLayout(currencyComboBox, ForeignUi.getMessage("label.currency"));
    }

    private HorizontalLayout buildPriceTypeLayout() {
        priceTypeComboBox.setSizeFull();
        priceTypeComboBox.setItems(); // TODO load items
        VaadinUtils.addComponentStyle(priceTypeComboBox, "udm-value-edit-price-type-combo-box");
        return buildCommonLayout(priceTypeComboBox, ForeignUi.getMessage("label.price_type"));
    }

    private HorizontalLayout buildPriceAccessTypeLayout() {
        priceAccessTypeComboBox.setSizeFull();
        priceAccessTypeComboBox.setItems(); // TODO load items
        VaadinUtils.addComponentStyle(priceAccessTypeComboBox, "udm-value-edit-price-access-type-combo-box");
        return buildCommonLayout(priceAccessTypeComboBox, ForeignUi.getMessage("label.price_access_type"));
    }

    private HorizontalLayout buildPriceYearLayout() {
        priceYearField.setSizeFull();
        // TODO add validation
        VaadinUtils.addComponentStyle(priceYearField, "udm-value-edit-price-year-field");
        return buildCommonLayout(priceYearField, ForeignUi.getMessage("label.price_year"));
    }

    private HorizontalLayout buildPriceSourceLayout() {
        priceSourceField.setSizeFull();
        // TODO add validation
        VaadinUtils.addComponentStyle(priceSourceField, "udm-value-edit-price-source-field");
        return buildCommonLayout(priceSourceField, ForeignUi.getMessage("label.price_source"));
    }

    private HorizontalLayout buildPriceCommentLayout() {
        priceCommentField.setSizeFull();
        // TODO add validation
        VaadinUtils.addComponentStyle(priceCommentField, "udm-value-edit-price-comment-field");
        return buildCommonLayout(priceCommentField, ForeignUi.getMessage("label.price_comment"));
    }

    private HorizontalLayout buildContentLayout() {
        contentField.setSizeFull();
        // TODO add validation
        VaadinUtils.addComponentStyle(contentField, "udm-value-edit-content-field");
        return buildCommonLayout(contentField, ForeignUi.getMessage("label.content"));
    }

    private HorizontalLayout buildContentSourceLayout() {
        contentSourceField.setSizeFull();
        // TODO add validation
        VaadinUtils.addComponentStyle(contentSourceField, "udm-value-edit-content-source-field");
        return buildCommonLayout(contentSourceField, ForeignUi.getMessage("label.content_source"));
    }

    private HorizontalLayout buildContentCommentLayout() {
        contentCommentField.setSizeFull();
        // TODO add validation
        VaadinUtils.addComponentStyle(contentCommentField, "udm-value-edit-content-comment-field");
        return buildCommonLayout(contentCommentField, ForeignUi.getMessage("label.content_comment"));
    }

    private HorizontalLayout buildCommentLayout() {
        commentField.setSizeFull();
        // TODO add validation
        VaadinUtils.addComponentStyle(commentField, "udm-value-edit-comment-field");
        return buildCommonLayout(commentField, ForeignUi.getMessage("label.comment"));
    }

    private String getStringFromLocalDate(LocalDate date) {
        return CommonDateUtils.format(date, RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? FastDateFormat.getInstance(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT).format(date)
            : StringUtils.EMPTY;
    }
}
