package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.Lists;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Window to create Additional Fund.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/01/2019
 *
 * @author Aliaksandr Liakh
 */
class CreateAdditionalFundWindow extends Window {

    private final INtsUsageController controller;
    private final Set<String> batchIds;
    private final BigDecimal amount;
    private final AdditionalFundBatchesFilterWindow batchesFilterWindow;
    private final AdditionalFundFilteredBatchesWindow filteredBatchesWindow;
    private final Binder<FundPool> binder = new Binder<>();

    private TextField fundNameField;
    private TextArea commentArea;

    /**
     * Constructor.
     *
     * @param controller            instance of {@link INtsUsageController}
     * @param batchIds              set of ids of usage batches
     * @param amount                gross amount
     * @param batchesFilterWindow   instance of {@link AdditionalFundBatchesFilterWindow}
     * @param filteredBatchesWindow instance of {@link AdditionalFundFilteredBatchesWindow}
     */
    CreateAdditionalFundWindow(INtsUsageController controller, Set<String> batchIds, BigDecimal amount,
                               AdditionalFundBatchesFilterWindow batchesFilterWindow,
                               AdditionalFundFilteredBatchesWindow filteredBatchesWindow) {
        this.controller = controller;
        this.batchIds = batchIds;
        this.amount = amount;
        this.batchesFilterWindow = batchesFilterWindow;
        this.filteredBatchesWindow = filteredBatchesWindow;
        setResizable(false);
        setWidth(320, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.create_fund"));
        initPreServiceFeeFundNameField();
        initCommentArea();
        binder.validate();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(fundNameField, commentArea, buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        fundNameField.focus();
        setContent(layout);
    }

    private void initPreServiceFeeFundNameField() {
        fundNameField = new TextField(ForeignUi.getMessage("field.fund_name"));
        fundNameField.setRequiredIndicatorVisible(true);
        binder.forField(fundNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.additionalFundExists(StringUtils.trim(value)),
                ForeignUi.getMessage("message.error.unique_name", "Fund"))
            .bind(FundPool::getName, FundPool::setName);
        VaadinUtils.setMaxComponentsWidth(fundNameField);
    }

    private void initCommentArea() {
        commentArea = new TextArea(ForeignUi.getMessage("field.comment"));
        commentArea.setRequiredIndicatorVisible(true);
        binder.forField(commentArea)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(FundPool::getComment, FundPool::setComment);
        VaadinUtils.setMaxComponentsWidth(commentArea);
    }

    private HorizontalLayout initButtonsLayout() {
        Button confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(event -> onConfirmButtonClicked());
        Button cancelButton = Buttons.createButton(ForeignUi.getMessage("button.cancel"));
        cancelButton.addClickListener(event -> this.close());
        Button closeButton = Buttons.createButton(ForeignUi.getMessage("button.close"));
        closeButton.addClickListener(event -> closeAllWindows());
        VaadinUtils.setButtonsAutoDisabled(confirmButton);
        HorizontalLayout layout = new HorizontalLayout(confirmButton, cancelButton, closeButton);
        layout.setSpacing(true);
        return layout;
    }

    private void closeAllWindows() {
        this.close();
        filteredBatchesWindow.close();
        batchesFilterWindow.close();
    }

    private void onConfirmButtonClicked() {
        if (binder.isValid()) {
            FundPool fundPool = new FundPool();
            fundPool.setId(RupPersistUtils.generateUuid());
            fundPool.setProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
            fundPool.setName(StringUtils.trimToEmpty(fundNameField.getValue()));
            fundPool.setComment(StringUtils.trimToEmpty(commentArea.getValue()));
            fundPool.setTotalAmount(amount);
            controller.createAdditionalFund(fundPool, batchIds);
            closeAllWindows();
        } else {
            Windows.showValidationErrorWindow(Lists.newArrayList(fundNameField, commentArea));
        }
    }
}
