package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredLongValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUpdateUsageWindow;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.LongField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ConfirmWindows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.LongRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

import java.util.List;

/**
 * Window to edit multiple FAS usages.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/13/2023
 *
 * @author Aliaksandr Liakh
 */
class FasEditMultipleUsagesWindow extends CommonDialog {

    private static final long serialVersionUID = 275482487974743826L;

    private final IFasUsageController controller;
    private final List<String> usageIds;
    private final Binder<Usage> binder = new Binder<>();
    private final IFasUpdateUsageWindow updateUsageWindow;
    private LongField wrWrkInstField;

    /**
     * Constructor.
     *
     * @param controller        instance of {@link IFasUsageController}
     * @param updateUsageWindow instance of {@link IFasUpdateUsageWindow}
     * @param usageIds          list of usage ids
     */
    FasEditMultipleUsagesWindow(IFasUsageController controller, IFasUpdateUsageWindow updateUsageWindow,
                                List<String> usageIds) {
        this.controller = controller;
        this.updateUsageWindow = updateUsageWindow;
        this.usageIds = usageIds;
        super.add(initRootLayout());
        super.setHeaderTitle(ForeignUi.getMessage("window.multiple.edit_fas_fas2_usages"));
        super.setResizable(false);
        super.setWidth("400px");
        super.setHeight("215px");
        super.getFooter().add(initButtonsLayout());
        VaadinUtils.addComponentStyle(this, "multiple-edit-fas-usages-window");
        binder.validate();
    }

    private VerticalLayout initRootLayout() {
        return new VerticalLayout(initWrWrkInstField()); //TODO get rid of VerticalLayout
    }

    private LongField initWrWrkInstField() {
        wrWrkInstField = new LongField(ForeignUi.getMessage("label.wr_wrk_inst"));
        wrWrkInstField.setSizeFull();
        binder.forField(wrWrkInstField)
            .withValidator(new RequiredLongValidator())
            .withValidator(
                new LongRangeValidator(ForeignUi.getMessage("field.error.number_length", 9), 1L, 999999999L))
            .bind(Usage::getWrWrkInst, Usage::setWrWrkInst);
        wrWrkInstField.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(wrWrkInstField, "wr-wrk-inst-field");
        return wrWrkInstField;
    }

    private HorizontalLayout initButtonsLayout() {
        var saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        var closeButton = Buttons.createCloseButton(this);
        saveButton.addClickListener(event -> {
            if (binder.isValid()) {
                Long wrWrkInst = wrWrkInstField.getValue();
                ConfirmWindows.showConfirmDialogWithReason(
                    ForeignUi.getMessage("window.confirm"),
                    ForeignUi.getMessage("message.confirm.update_usages"),
                    ForeignUi.getMessage("button.yes"),
                    ForeignUi.getMessage("button.cancel"),
                    reason -> {
                        controller.updateUsages(usageIds, wrWrkInst, reason);
                        controller.refreshWidget();
                        updateUsageWindow.close();
                        this.close();
                    }, new StringLengthValidator(ForeignUi.getMessage("field.error.empty.length", 1024), 1, 1024));
            } else {
                Windows.showValidationErrorWindow();
            }
        });
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        return new HorizontalLayout(saveButton, closeButton);
    }
}
