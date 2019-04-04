package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
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
import java.util.List;

/**
 * Window to create NTS withdrawn fund pool.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/01/2019
 *
 * @author Aliaksandr Liakh
 */
class CreateWithdrawnFundPoolWindow extends Window {

    private final IUsagesController controller;
    private final List<String> batchIds;
    private final BigDecimal amount;
    private final WithdrawnBatchFilterWindow batchFilterWindow;
    private final WithdrawnFilteredBatchesWindow filteredBatchesWindow;
    private final Binder<WithdrawnFundPool> binder = new Binder<>();

    private TextField fundPoolNameField;
    private TextArea commentsArea;

    /**
     * Constructor.
     *
     * @param controller            instance of {@link IUsagesController}
     * @param batchIds              list of ids of usage batches
     * @param amount                gross amount
     * @param batchFilterWindow     instance of {@link WithdrawnBatchFilterWindow}
     * @param filteredBatchesWindow instance of {@link WithdrawnFilteredBatchesWindow}
     */
    CreateWithdrawnFundPoolWindow(IUsagesController controller, List<String> batchIds, BigDecimal amount,
                                  WithdrawnBatchFilterWindow batchFilterWindow,
                                  WithdrawnFilteredBatchesWindow filteredBatchesWindow) {
        this.controller = controller;
        this.batchIds = batchIds;
        this.amount = amount;
        this.batchFilterWindow = batchFilterWindow;
        this.filteredBatchesWindow = filteredBatchesWindow;
        setResizable(false);
        setWidth(320, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.create_fund_pool"));
        initFundPoolNameField();
        initCommentsArea();
        binder.validate();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(fundPoolNameField, commentsArea, buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        fundPoolNameField.focus();
        setContent(layout);
    }

    private void initFundPoolNameField() {
        fundPoolNameField = new TextField(ForeignUi.getMessage("field.fund_pool_name"));
        binder.forField(fundPoolNameField)
            .asRequired(ForeignUi.getMessage("field.error.empty"))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.getWithdrawnFundPoolService().fundPoolNameExists(value),
                ForeignUi.getMessage("message.error.unique_name", "Fund"))
            .bind(WithdrawnFundPool::getName, WithdrawnFundPool::setName);
        VaadinUtils.setMaxComponentsWidth(fundPoolNameField);
    }

    private void initCommentsArea() {
        commentsArea = new TextArea(ForeignUi.getMessage("field.comments"));
        binder.forField(commentsArea)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(WithdrawnFundPool::getComment, WithdrawnFundPool::setComment);
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
        batchFilterWindow.close();
    }

    private void onConfirmButtonClicked() {
        if (binder.isValid()) {
            WithdrawnFundPool fundPool = new WithdrawnFundPool();
            fundPool.setId(RupPersistUtils.generateUuid());
            fundPool.setName(StringUtils.trimToEmpty(fundPoolNameField.getValue()));
            fundPool.setComment(StringUtils.trimToEmpty(commentsArea.getValue()));
            fundPool.setAmount(amount);
            controller.getWithdrawnFundPoolService().create(fundPool, batchIds);
            closeAllWindows();
        } else {
            Windows.showValidationErrorWindow(Lists.newArrayList(fundPoolNameField, commentsArea));
        }
    }
}
