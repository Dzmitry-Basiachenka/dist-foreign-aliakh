package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.DateUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmValueWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Window to view UDM value.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmViewValueWindow extends CommonUdmValueWindow {

    private final Binder<UdmValueDto> binder = new Binder<>();
    private final IUdmValueController controller;
    private final UdmValueDto udmValueDto;

    /**
     * Constructor.
     *
     * @param valueController  instance of {@link IUdmValueController}
     * @param selectedUdmValue UDM value to be displayed on the window
     */
    public UdmViewValueWindow(IUdmValueController valueController, UdmValueDto selectedUdmValue) {
        controller = valueController;
        udmValueDto = selectedUdmValue;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.view_udm_value"));
        setResizable(false);
        setWidth(960, Unit.PIXELS);
        setHeight(700, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "view-udm-value-window");
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        VerticalLayout editFieldsLayout = new VerticalLayout();
        editFieldsLayout.addComponents(
            new HorizontalLayout(
                initLeftColumn(),
                initRightColumn()
            )
        );
        Panel panel = new Panel(editFieldsLayout);
        panel.setSizeFull();
        editFieldsLayout.setMargin(false);
        HorizontalLayout buttonsLayout = new HorizontalLayout(Buttons.createCloseButton(this));
        rootLayout.addComponents(panel, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(panel, 1f);
        rootLayout.setSizeFull();
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        binder.validate();
        binder.readBean(udmValueDto);
        return rootLayout;
    }

    private VerticalLayout initLeftColumn() {
        return buildVerticalLayoutWithFixedWidth(
            new Panel(ForeignUi.getMessage("label.work_information"), new VerticalLayout(
                buildReadOnlyLayout("label.system_title", UdmValueDto::getSystemTitle, binder),
                buildReadOnlyLayout("label.wr_wrk_inst", bean -> Objects.toString(bean.getWrWrkInst()), binder),
                buildReadOnlyLayout("label.system_standard_number", UdmValueDto::getSystemStandardNumber, binder),
                buildReadOnlyLayout("label.rh_name", UdmValueDto::getRhName, binder),
                buildReadOnlyLayout("label.rh_account_number",
                    bean -> Objects.toString(bean.getRhAccountNumber()), binder)
            )),
            new Panel(ForeignUi.getMessage("label.price"), new VerticalLayout(
                buildReadOnlyLayout("label.price", fromBigDecimalToMoneyString(UdmValueDto::getPrice), binder),
                buildReadOnlyLayout("label.currency", buildCurrencyValueProvider(), binder),
                buildReadOnlyLayout("label.currency_exchange_rate",
                    fromBigDecimalToString(UdmValueDto::getCurrencyExchangeRate), binder),
                buildReadOnlyLayout("label.currency_exchange_rate_date",
                    fromLocalDateToString(UdmValueDto::getCurrencyExchangeRateDate), binder),
                buildReadOnlyLayout("label.price_in_usd", fromBigDecimalToMoneyString(UdmValueDto::getPriceInUsd),
                    binder),
                buildReadOnlyLayout("label.price_type", UdmValueDto::getPriceType, binder),
                buildReadOnlyLayout("label.price_access_type", UdmValueDto::getPriceAccessType, binder),
                buildReadOnlyLayout("label.price_year",
                    bean -> Objects.toString(bean.getPriceYear(), StringUtils.EMPTY), binder),
                buildReadOnlyLayout("label.price_source", UdmValueDto::getPriceSource, binder),
                buildReadOnlyLayout("label.price_comment", UdmValueDto::getPriceComment, binder),
                buildReadOnlyLayout("label.price_flag", fromBooleanToYNString(UdmValueDto::isPriceFlag), binder),
                buildReadOnlyLayout("label.last_price_in_usd",
                    fromBigDecimalToMoneyString(UdmValueDto::getLastPriceInUsd), binder),
                buildReadOnlyLayout("label.last_price_source", UdmValueDto::getLastPriceSource, binder),
                buildReadOnlyLayout("label.last_price_comment", UdmValueDto::getLastPriceComment, binder),
                buildReadOnlyLayout("label.last_price_flag",
                    bean -> BooleanUtils.toYNString(bean.isLastPriceFlag()), binder)
            ))
        );
    }

    private VerticalLayout initRightColumn() {
        return buildVerticalLayoutWithFixedWidth(
            new Panel(ForeignUi.getMessage("label.general"), new VerticalLayout(
                buildReadOnlyLayout("label.value_period", bean -> Objects.toString(bean.getValuePeriod()), binder),
                buildReadOnlyLayout("label.last_value_period",
                    bean -> Objects.toString(bean.getLastValuePeriod(), StringUtils.EMPTY), binder),
                buildReadOnlyLayout("label.assignee", UdmValueDto::getAssignee, binder),
                buildReadOnlyLayout("label.value_status", bean -> bean.getStatus().name(), binder)
            )),
            new Panel(ForeignUi.getMessage("label.publication_type"), new VerticalLayout(
                buildReadOnlyLayout("label.pub_type", buildPublicationTypeValueProvider(), binder),
                buildReadOnlyLayout("label.last_pub_type", UdmValueDto::getLastPubType, binder)
            )),
            new Panel(ForeignUi.getMessage("label.content"), new VerticalLayout(
                buildReadOnlyLayout("label.content", fromBigDecimalToMoneyString(UdmValueDto::getContent), binder),
                buildReadOnlyLayout("label.content_source", UdmValueDto::getContentSource, binder),
                buildReadOnlyLayout("label.content_comment", UdmValueDto::getContentComment, binder),
                buildReadOnlyLayout("label.content_flag", fromBooleanToYNString(UdmValueDto::isContentFlag), binder),
                buildReadOnlyLayout("label.last_content",
                    fromBigDecimalToMoneyString(UdmValueDto::getLastContent), binder),
                buildReadOnlyLayout("label.last_content_source", UdmValueDto::getLastContentSource, binder),
                buildReadOnlyLayout("label.last_content_comment", UdmValueDto::getLastContentComment, binder),
                buildReadOnlyLayout("label.last_content_flag",
                    bean -> BooleanUtils.toYNString(bean.isLastContentFlag()), binder),
                buildReadOnlyLayout("label.content_unit_price",
                    fromBigDecimalToMoneyString(UdmValueDto::getContentUnitPrice), binder)
            )),
            new Panel(ForeignUi.getMessage("label.comment"), new VerticalLayout(
                buildReadOnlyLayout("label.comment", UdmValueDto::getComment, binder)
            )),
            new Panel(new VerticalLayout(
                buildReadOnlyLayout("label.updated_by", UdmValueDto::getUpdateUser, binder),
                buildReadOnlyLayout("label.updated_date", bean -> DateUtils.format(bean.getUpdateDate()), binder)
            ))
        );
    }

    private VerticalLayout buildVerticalLayoutWithFixedWidth(Component... children) {
        VerticalLayout verticalLayout = new VerticalLayout(children);
        verticalLayout.setWidth(450, Unit.PIXELS);
        return verticalLayout;
    }

    private ValueProvider<UdmValueDto, String> buildPublicationTypeValueProvider() {
        return bean -> Objects.nonNull(bean.getPublicationType())
            ? String.format("%s - %s", bean.getPublicationType().getName(), bean.getPublicationType().getDescription())
            : StringUtils.EMPTY;
    }

    private ValueProvider<UdmValueDto, String> buildCurrencyValueProvider() {
        return bean -> {
            Currency beanCurrency = controller.getAllCurrencies().stream()
                .filter(currency -> currency.getCode().equals(bean.getCurrency()))
                .findFirst()
                .orElse(null);
            return Objects.nonNull(beanCurrency)
                ? String.format("%s - %s", beanCurrency.getCode(), beanCurrency.getDescription())
                : StringUtils.EMPTY;
        };
    }
}
