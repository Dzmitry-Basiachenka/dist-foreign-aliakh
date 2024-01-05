package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.event.SerializableEventListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
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
        super.addComponent(button);
        super.setExpandRatio(button, 1);
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
        button = Buttons.createButton(caption);
        button.addStyleName(ValoTheme.BUTTON_LINK);
        VaadinUtils.setButtonsAutoDisabled(button);
        button.addClickListener(event -> {
            CommonScenarioParameterWindow<T> parameterWindow = windowSupplier.get();
            parameterWindow.setDefault(defaultParameters);
            parameterWindow.setAppliedParameters(appliedParameters);
            Windows.showModalWindow(parameterWindow);
            parameterWindow.addListener(ParametersSaveEvent.class,
                (IParametersSaveListener<T>) saveEvent -> appliedParameters = saveEvent.getSavedParameters(),
                IParametersSaveListener.SAVE_HANDLER);
        });
    }

    /**
     * An event that occurs when user clicks 'Save' button on AACL scenario parameters window.
     *
     * @param <T> parameter type
     */
    public static class ParametersSaveEvent<T> extends Event {

        private static final long serialVersionUID = -5345394014667341431L;

        private final T savedParameters;

        /**
         * Constructor.
         *
         * @param source          event source
         * @param savedParameters saved parameters
         */
        public ParametersSaveEvent(Component source, T savedParameters) {
            super(source);
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
    public interface IParametersSaveListener<T> extends SerializableEventListener {
        /**
         * Listener method.
         */
        Method SAVE_HANDLER =
            ReflectTools.findMethod(IParametersSaveListener.class, "onSave", ParametersSaveEvent.class);

        /**
         * Handles {@link ParametersSaveEvent} events.
         *
         * @param event {@link ParametersSaveEvent}
         */
        void onSave(ParametersSaveEvent<T> event);
    }
}
