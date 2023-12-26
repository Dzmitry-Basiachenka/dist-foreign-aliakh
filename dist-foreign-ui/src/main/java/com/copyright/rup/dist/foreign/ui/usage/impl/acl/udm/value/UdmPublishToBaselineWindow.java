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
 * Window for publishing values to baseline.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/13/21
 *
 * @author Uladzislau Shalamitski
 */
public class UdmPublishToBaselineWindow extends Window {

    private static final long serialVersionUID = -4436516052770620410L;

    private final IUdmValueController controller;
    private final ComboBox<Integer> periodComboBox = new ComboBox<>(ForeignUi.getMessage("label.period"));
    private final Button continueButton = Buttons.createButton(ForeignUi.getMessage("button.continue"));
    private final ClickListener publishButtonClickListener;

    /**
     * Constructor.
     *
     * @param controller    instance of {@link IUdmValueController}
     * @param clickListener action that should be performed after Publish button was clicked
     */
    public UdmPublishToBaselineWindow(IUdmValueController controller, ClickListener clickListener) {
        this.controller = controller;
        this.publishButtonClickListener = clickListener;
        super.setContent(initRootLayout());
        super.setCaption(ForeignUi.getMessage("window.publish_baseline"));
        super.setResizable(false);
        super.setWidth(280, Unit.PIXELS);
        super.setHeight(120, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-value-publish-to-baseline-window");
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
            if (controller.isAllowedForPublishing(periodComboBox.getValue())) {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.udm_value.publish",
                    controller.publishToBaseline(periodComboBox.getValue())));
                publishButtonClickListener.buttonClick(event);
                close();
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.error.udm_value.publish"));
            }
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
        VaadinUtils.addComponentStyle(periodComboBox, "udm-value-publish-to-baseline-period");
    }
}
