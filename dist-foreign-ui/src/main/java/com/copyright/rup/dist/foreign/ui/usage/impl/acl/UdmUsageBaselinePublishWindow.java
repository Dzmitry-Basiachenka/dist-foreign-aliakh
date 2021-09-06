package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
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

import org.apache.commons.lang3.tuple.Pair;

/**
 * Window for publish to baseline.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/21
 *
 * @author Anton Azarenka
 */
public class UdmUsageBaselinePublishWindow extends Window {

    private final IUdmUsageController controller;
    private final Button publishButton = Buttons.createButton(ForeignUi.getMessage("button.publish_usage"));
    private final ComboBox<Integer> periodComboBox = new ComboBox<>(ForeignUi.getMessage("label.period"));

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmUsageController}
     */
    public UdmUsageBaselinePublishWindow(IUdmUsageController controller) {
        this.controller = controller;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.publish_baseline"));
        setResizable(false);
        setWidth(280, Unit.PIXELS);
        setHeight(120, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "publish_baseline-udm-usage-window");
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
        Button closeButton = Buttons.createCloseButton(this);
        publishButton.setEnabled(false);
        publishButton.addClickListener(event -> {
            Pair<Integer, Integer> publishedRemovedUdmUsages =
                controller.publishUdmUsagesToBaseline(periodComboBox.getValue());
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.udm_usage.publish", publishedRemovedUdmUsages.getLeft(),
                    publishedRemovedUdmUsages.getRight()));
        });
        horizontalLayout.addComponents(publishButton, closeButton);
        return horizontalLayout;
    }

    private void initPeriodCombobox() {
        VaadinUtils.setMaxComponentsWidth(periodComboBox);
        periodComboBox.setItems(controller.getPeriods());
        periodComboBox.setEmptySelectionAllowed(false);
        periodComboBox.addValueChangeListener(event -> publishButton.setEnabled(true));
        VaadinUtils.addComponentStyle(periodComboBox, "publish-udm-period");
    }
}
