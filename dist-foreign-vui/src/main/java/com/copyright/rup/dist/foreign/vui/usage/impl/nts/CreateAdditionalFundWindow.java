package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IAdditionalFundBatchesFilterWindow;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IAdditionalFundFilteredBatchesWindow;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;

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
class CreateAdditionalFundWindow extends CommonDialog {

    private static final long serialVersionUID = 325443931908820135L;

    private final INtsUsageController controller;
    private final Set<String> batchIds;
    private final BigDecimal amount;
    private final IAdditionalFundBatchesFilterWindow batchesFilterWindow;
    private final IAdditionalFundFilteredBatchesWindow filteredBatchesWindow;
    private final Binder<FundPool> binder = new Binder<>();

    private TextField fundNameField;
    private TextArea commentArea;

    /**
     * Constructor.
     *
     * @param controller            instance of {@link INtsUsageController}
     * @param batchIds              set of batch ids
     * @param amount                gross amount
     * @param batchesFilterWindow   instance of {@link IAdditionalFundBatchesFilterWindow}
     * @param filteredBatchesWindow instance of {@link IAdditionalFundFilteredBatchesWindow}
     */
    CreateAdditionalFundWindow(INtsUsageController controller, Set<String> batchIds, BigDecimal amount,
                               IAdditionalFundBatchesFilterWindow batchesFilterWindow,
                               IAdditionalFundFilteredBatchesWindow filteredBatchesWindow) {
        this.controller = controller;
        this.batchIds = batchIds;
        this.amount = amount;
        this.batchesFilterWindow = batchesFilterWindow;
        this.filteredBatchesWindow = filteredBatchesWindow;
        super.setWidth("500px");
        super.setHeaderTitle(ForeignUi.getMessage("window.create_fund"));
        super.add(initRootLayout());
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("create-additional-fund-window", false);
    }

    private VerticalLayout initRootLayout() {
        initFundNameField();
        initCommentArea();
        fundNameField.focus();
        var rootLayout = VaadinUtils.initCommonVerticalLayout(fundNameField, commentArea);
        VaadinUtils.setPadding(rootLayout, 10, 10, 10, 10);
        return rootLayout;
    }

    private void initFundNameField() {
        fundNameField = new TextField(ForeignUi.getMessage("field.fund_name"));
        fundNameField.setRequiredIndicatorVisible(true);
        fundNameField.setWidthFull();
        binder.forField(fundNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.additionalFundExists(StringUtils.trim(value)),
                ForeignUi.getMessage("message.error.unique_name", "Fund"))
            .bind(FundPool::getName, FundPool::setName);
        VaadinUtils.addComponentStyle(fundNameField, "additional-fund-name");
    }

    private void initCommentArea() {
        commentArea = new TextArea(ForeignUi.getMessage("field.comment"));
        commentArea.setRequiredIndicatorVisible(true);
        commentArea.setWidthFull();
        binder.forField(commentArea)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(FundPool::getComment, FundPool::setComment);
        VaadinUtils.addComponentStyle(commentArea, "additional-fund-comment");
    }

    private HorizontalLayout initButtonsLayout() {
        var confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(event -> onConfirmButtonClicked());
        var cancelButton = Buttons.createButton(ForeignUi.getMessage("button.cancel"));
        cancelButton.addClickListener(event -> this.close());
        var closeButton = Buttons.createButton(ForeignUi.getMessage("button.close"));
        closeButton.addClickListener(event -> closeAllWindows());
        return new HorizontalLayout(confirmButton, cancelButton, closeButton);
    }

    private void closeAllWindows() {
        this.close();
        filteredBatchesWindow.close();
        batchesFilterWindow.close();
    }

    private void onConfirmButtonClicked() {
        if (binder.validate().isOk()) {
            var fundPool = new FundPool();
            fundPool.setId(RupPersistUtils.generateUuid());
            fundPool.setProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
            fundPool.setName(StringUtils.trimToEmpty(fundNameField.getValue()));
            fundPool.setComment(StringUtils.trimToEmpty(commentArea.getValue()));
            fundPool.setTotalAmount(amount);
            controller.createAdditionalFund(fundPool, batchIds);
            closeAllWindows();
        } else {
            Windows.showValidationErrorWindow();
        }
    }
}
