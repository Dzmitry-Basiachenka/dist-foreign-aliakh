package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.function.Supplier;

/**
 * Represents widget for populating ACL scenario parameters.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/13/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclPublicationTypeWeightsParameterWidget extends HorizontalLayout {

    private static final long serialVersionUID = -3706221935455642033L;

    private final Supplier<AclPublicationTypeWeightsWindow> windowSupplier;

    private List<AclPublicationType> appliedParameters;
    private Button button;

    /**
     * Constructor.
     *
     * @param caption           button caption
     * @param defaultParameters default parameters
     * @param windowInitializer supplier for window initialization
     */
    public AclPublicationTypeWeightsParameterWidget(String caption, List<AclPublicationType> defaultParameters,
                                                    Supplier<AclPublicationTypeWeightsWindow> windowInitializer) {
        this.windowSupplier = windowInitializer;
        this.appliedParameters = defaultParameters;
        initButton(caption);
        super.addComponent(button);
        super.setExpandRatio(button, 1);
    }

    public List<AclPublicationType> getAppliedParameters() {
        return appliedParameters;
    }

    public void setAppliedParameters(List<AclPublicationType> appliedParameters) {
        this.appliedParameters = appliedParameters;
    }

    private void initButton(String caption) {
        button = Buttons.createButton(caption);
        button.addStyleName(ValoTheme.BUTTON_LINK);
        VaadinUtils.setButtonsAutoDisabled(button);
        button.addClickListener(event -> {
            AclPublicationTypeWeightsWindow parameterWindow = windowSupplier.get();
            parameterWindow.setAppliedParameters(appliedParameters);
            Windows.showModalWindow(parameterWindow);
            parameterWindow.addListener(ParametersSaveEvent.class,
                (IParametersSaveListener<List<AclPublicationType>>)
                    saveEvent -> appliedParameters = saveEvent.getSavedParameters(),
                IParametersSaveListener.SAVE_HANDLER);
        });
    }
}
