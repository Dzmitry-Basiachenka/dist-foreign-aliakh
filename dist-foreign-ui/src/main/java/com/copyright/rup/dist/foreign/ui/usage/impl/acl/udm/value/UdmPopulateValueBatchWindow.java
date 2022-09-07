package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Window for populating value batches.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Uladzislau Shalamitski
 */
public class UdmPopulateValueBatchWindow extends Window {

    private final IUdmValueController controller;
    private final ComboBox<Integer> periodComboBox = new ComboBox<>(ForeignUi.getMessage("label.period"));
    private final Button continueButton = Buttons.createButton(ForeignUi.getMessage("button.continue"));

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmValueController}
     */
    public UdmPopulateValueBatchWindow(IUdmValueController controller) {
        this.controller = controller;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.populate_value_batch"));
        setResizable(false);
        setWidth(280, Unit.PIXELS);
        setHeight(120, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-value-populate-value-batch-window");
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        initPeriodCombobox();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        verticalLayout.addComponents(periodComboBox, buttonsLayout);
        verticalLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return verticalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button cancelButton = Buttons.createCancelButton(this);
        continueButton.setEnabled(false);
        continueButton.addClickListener(event -> {
            int populatedValuesCount = controller.populatesValueBatch(periodComboBox.getValue());
            close();
            Windows.showNotificationWindow(ForeignUi.getMessage("message.udm_value.populate", populatedValuesCount));
        });
        VaadinUtils.setButtonsAutoDisabled(continueButton);
        horizontalLayout.addComponents(continueButton, cancelButton);
        return horizontalLayout;
    }

    private void initPeriodCombobox() {
        VaadinUtils.setMaxComponentsWidth(periodComboBox);
        periodComboBox.setItems(controller.getBaselinePeriods());
        periodComboBox.setEmptySelectionAllowed(false);
        periodComboBox.addValueChangeListener(event -> continueButton.setEnabled(true));
        VaadinUtils.addComponentStyle(periodComboBox, "udm-value-populate-value-batch-period");
    }
}
