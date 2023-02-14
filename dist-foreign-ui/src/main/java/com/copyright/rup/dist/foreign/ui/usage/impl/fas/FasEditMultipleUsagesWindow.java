package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * Window to edit multiple FAS usages.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/13/2023
 *
 * @author Aliaksandr Liakh
 */
class FasEditMultipleUsagesWindow extends Window {

    private final IFasUsageController controller;
    private final List<String> usageIds;
    private final Binder<Usage> binder = new Binder<>();
    private final FasUpdateUsageWindow updateUsageWindow;
    private TextField wrWrkInstField;

    /**
     * Constructor.
     *
     * @param controller        {@link IFasUsageController} instance
     * @param updateUsageWindow {@link FasUpdateUsageWindow} instance
     * @param usageIds          list of usage ids
     */
    FasEditMultipleUsagesWindow(IFasUsageController controller, FasUpdateUsageWindow updateUsageWindow,
                                List<String> usageIds) {
        this.controller = controller;
        this.updateUsageWindow = updateUsageWindow;
        this.usageIds = usageIds;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.multiple.edit_fas_usage"));
        setResizable(false);
        setWidth(280, Unit.PIXELS);
        setHeight(120, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "multiple-edit-fas-usages-window");
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = buildButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout(buildWrWrkInstField(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        binder.validate();
        return rootLayout;
    }

    private TextField buildWrWrkInstField() {
        wrWrkInstField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
        wrWrkInstField.setSizeFull();
        binder.forField(wrWrkInstField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.isNumeric(value.trim()),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9))
            .bind(usage -> Objects.toString(usage.getWrWrkInst()),
                (usage, value) -> usage.setWrWrkInst(Long.valueOf(value.trim())));
        wrWrkInstField.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(wrWrkInstField, "wr-wrk-inst-field");
        return wrWrkInstField;
    }

    private HorizontalLayout buildButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            if (binder.isValid()) {
                Long wrWrkInst = Long.valueOf(wrWrkInstField.getValue().trim());
                Windows.showConfirmDialogWithReason(
                    ForeignUi.getMessage("window.confirm"),
                    ForeignUi.getMessage("message.confirm.update_usages"),
                    ForeignUi.getMessage("button.yes"),
                    ForeignUi.getMessage("button.cancel"),
                    reason -> {
                        controller.updateUsages(usageIds, wrWrkInst, reason);
                        controller.refreshWidget();
                        updateUsageWindow.refreshDataProvider();
                        this.close();
                    }, new StringLengthValidator(ForeignUi.getMessage("field.error.empty.length", 1024), 1, 1024));
            } else {
                Windows.showValidationErrorWindow(List.of(wrWrkInstField));
            }
        });
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(saveButton, closeButton);
        return horizontalLayout;
    }
}
