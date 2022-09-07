package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Window to calculate UDM proxy values.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/25/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmCalculateProxyValuesWindow extends Window {

    private final IUdmValueController controller;
    private final ComboBox<Integer> periodComboBox = new ComboBox<>(ForeignUi.getMessage("label.period"));
    private final Button continueButton = Buttons.createButton(ForeignUi.getMessage("button.continue"));
    private final ClickListener continueButtonClickListener;

    /**
     * Constructor.
     *
     * @param controller    instance of {@link IUdmValueController}
     * @param clickListener action that should be performed after Save button was clicked
     */
    public UdmCalculateProxyValuesWindow(IUdmValueController controller, ClickListener clickListener) {
        this.controller = controller;
        this.continueButtonClickListener = clickListener;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.calculate_proxies"));
        setResizable(false);
        setWidth(280, Unit.PIXELS);
        setHeight(120, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-value-calculate-proxies-window");
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
            int updatedValuesCount = controller.calculateProxyValues(periodComboBox.getValue());
            continueButtonClickListener.buttonClick(event);
            close();
            Windows.showNotificationWindow(ForeignUi.getMessage("message.udm_proxy_value.calculate",
                updatedValuesCount));
        });
        VaadinUtils.setButtonsAutoDisabled(continueButton);
        horizontalLayout.addComponents(continueButton, cancelButton);
        return horizontalLayout;
    }

    private void initPeriodCombobox() {
        VaadinUtils.setMaxComponentsWidth(periodComboBox);
        periodComboBox.setItems(controller.getPeriods());
        periodComboBox.setEmptySelectionAllowed(false);
        periodComboBox.addValueChangeListener(event -> continueButton.setEnabled(true));
        VaadinUtils.addComponentStyle(periodComboBox, "udm-value-calculate-proxies-period");
    }
}
