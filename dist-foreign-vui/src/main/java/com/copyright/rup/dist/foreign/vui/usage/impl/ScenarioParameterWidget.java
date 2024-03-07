package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.function.Supplier;

/**
 * Represents widget for populating scenario parameters.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/11/2020
 *
 * @param <T> type of scenario parameter
 * @author Ihar Suvorau
 */
public class ScenarioParameterWidget<T> extends HorizontalLayout {

    private static final long serialVersionUID = -4039438710710040763L;

    private final Supplier<CommonScenarioParameterWindow<T>> windowSupplier;
    private T defaultParameters;
    private T appliedParameters;
    private Button button;

    /**
     * Constructor.
     *
     * @param caption           button caption
     * @param defaultParameters default parameters
     * @param windowInitializer supplier for window initialization
     */
    public ScenarioParameterWidget(String caption, T defaultParameters,
                                   Supplier<CommonScenarioParameterWindow<T>> windowInitializer) {
        this.windowSupplier = windowInitializer;
        this.defaultParameters = defaultParameters;
        this.appliedParameters = defaultParameters;
        initButton(caption);
        super.add(button);
    }

    public T getDefaultParameters() {
        return defaultParameters;
    }

    public void setDefaultParameters(T defaultParameters) {
        this.defaultParameters = defaultParameters;
    }

    public T getAppliedParameters() {
        return appliedParameters;
    }

    public void setAppliedParameters(T appliedParameters) {
        this.appliedParameters = appliedParameters;
    }

    private void initButton(String caption) {
        button = Buttons.createLinkButton(caption);
        button.addClickListener(event -> {
            CommonScenarioParameterWindow<T> parameterWindow = windowSupplier.get();
            parameterWindow.setDefault(defaultParameters);
            parameterWindow.setAppliedParameters(appliedParameters);
            IParametersSaveListener listener = (IParametersSaveListener<T>) saveEvent ->
                appliedParameters = (T) saveEvent.getSavedParameters();
            ComponentUtil.addListener(parameterWindow, ParametersSaveEvent.class, listener);
            Windows.showModalWindow(parameterWindow);
        });
    }

    /**
     * An event that occurs when user clicks 'Save' button on AACL scenario parameters window.
     *
     * @param <T> parameter type
     */
    public static class ParametersSaveEvent<T> extends ComponentEvent<Dialog> {

        private static final long serialVersionUID = -5345394014667341431L;

        private final T savedParameters;

        /**
         * Constructor.
         *
         * @param source          event source
         * @param savedParameters saved parameters
         */
        public ParametersSaveEvent(Dialog source, T savedParameters) {
            super(source, true);
            this.savedParameters = savedParameters;
        }

        /**
         * @return saved parameters.
         */
        public T getSavedParameters() {
            return savedParameters;
        }
    }

    /**
     * Listener that handles {@link ParametersSaveEvent} events.
     *
     * @param <T> items ids type
     */
    public interface IParametersSaveListener<T> extends ComponentEventListener<ParametersSaveEvent<T>> {

        @Override
        void onComponentEvent(ParametersSaveEvent<T> event);
    }
}
