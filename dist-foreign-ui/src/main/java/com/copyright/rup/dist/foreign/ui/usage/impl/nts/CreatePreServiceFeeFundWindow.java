package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.PreServiceFeeFund;
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
 * Window to create Pre-Service fee fund.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/01/2019
 *
 * @author Aliaksandr Liakh
 */
class CreatePreServiceFeeFundWindow extends Window {

    private final INtsUsageController controller;
    private final Set<String> batchIds;
    private final BigDecimal amount;
    private final PreServiceFeeFundBatchesFilterWindow batchesFilterWindow;
    private final PreServiceFeeFundFilteredBatchesWindow filteredBatchesWindow;
    private final Binder<PreServiceFeeFund> binder = new Binder<>();

    private TextField fundNameField;
    private TextArea commentsArea;

    /**
     * Constructor.
     *
     * @param controller            instance of {@link INtsUsageController}
     * @param batchIds              set of ids of usage batches
     * @param amount                gross amount
     * @param batchesFilterWindow   instance of {@link PreServiceFeeFundBatchesFilterWindow}
     * @param filteredBatchesWindow instance of {@link PreServiceFeeFundFilteredBatchesWindow}
     */
    CreatePreServiceFeeFundWindow(INtsUsageController controller, Set<String> batchIds, BigDecimal amount,
                                  PreServiceFeeFundBatchesFilterWindow batchesFilterWindow,
                                  PreServiceFeeFundFilteredBatchesWindow filteredBatchesWindow) {
        this.controller = controller;
        this.batchIds = batchIds;
        this.amount = amount;
        this.batchesFilterWindow = batchesFilterWindow;
        this.filteredBatchesWindow = filteredBatchesWindow;
        setResizable(false);
        setWidth(320, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.create_fund"));
        initFundPoolNameField();
        initCommentsArea();
        binder.validate();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(fundNameField, commentsArea, buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        fundNameField.focus();
        setContent(layout);
    }

    private void initFundPoolNameField() {
        fundNameField = new TextField(ForeignUi.getMessage("field.fund_name"));
        binder.forField(fundNameField)
            .asRequired(ForeignUi.getMessage("field.error.empty"))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.fundPoolExists(StringUtils.trim(value)),
                ForeignUi.getMessage("message.error.unique_name", "Fund"))
            .bind(PreServiceFeeFund::getName, PreServiceFeeFund::setName);
        VaadinUtils.setMaxComponentsWidth(fundNameField);
    }

    private void initCommentsArea() {
        commentsArea = new TextArea(ForeignUi.getMessage("field.comments"));
        binder.forField(commentsArea)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(PreServiceFeeFund::getComment, PreServiceFeeFund::setComment);
        VaadinUtils.setMaxComponentsWidth(commentsArea);
    }

    private HorizontalLayout initButtonsLayout() {
        Button confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(event -> onConfirmButtonClicked());
        Button cancelButton = Buttons.createButton(ForeignUi.getMessage("button.cancel"));
        cancelButton.addClickListener(event -> this.close());
        Button closeButton = Buttons.createButton(ForeignUi.getMessage("button.close"));
        closeButton.addClickListener(event -> closeAllWindows());
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
            PreServiceFeeFund fundPool = new PreServiceFeeFund();
            fundPool.setId(RupPersistUtils.generateUuid());
            fundPool.setName(StringUtils.trimToEmpty(fundNameField.getValue()));
            fundPool.setComment(StringUtils.trimToEmpty(commentsArea.getValue()));
            fundPool.setAmount(amount);
            controller.createPreServiceFeeFund(fundPool, batchIds);
            closeAllWindows();
        } else {
            Windows.showValidationErrorWindow(Lists.newArrayList(fundNameField, commentsArea));
        }
    }
}
