package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * Represents widget for populating AACL scenario parameters.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/11/2020
 *
 * @param <T> type of scenario parameter
 * @author Ihar Suvorau
 */
public class AaclScenarioParameterWidget<T> extends HorizontalLayout {

    private final Supplier<AaclCommonScenarioParameterWindow<T>> windowSupplier;
    private final Supplier<T> defaultParametersSupplier;
    private T appliedParameters;
    private Button button;

    /**
     * Constructor.
     *
     * @param caption                   button caption
     * @param defaultParametersSupplier supplier for getting default parameter
     * @param windowInitializer         supplier for window initialization
     */
    public AaclScenarioParameterWidget(String caption, Supplier<T> defaultParametersSupplier,
                                       Supplier<AaclCommonScenarioParameterWindow<T>> windowInitializer) {
        this.windowSupplier = windowInitializer;
        this.defaultParametersSupplier = defaultParametersSupplier;
        this.appliedParameters = defaultParametersSupplier.get();
        initButton(caption);
        addComponent(button);
        setExpandRatio(button, 1);
    }

    /**
     * @return applied parameters.
     */
    public T getAppliedParameters() {
        return appliedParameters;
    }

    /**
     * Sets applied parameters.
     *
     * @param appliedParameters applied parameters
     */
    public void setAppliedParameters(T appliedParameters) {
        this.appliedParameters = appliedParameters;
    }

    private void initButton(String caption) {
        button = Buttons.createButton(caption);
        button.addStyleName(ValoTheme.BUTTON_LINK);
        VaadinUtils.setButtonsAutoDisabled(button);
        button.addClickListener(event -> {
            AaclCommonScenarioParameterWindow<T> parameterWindow = windowSupplier.get();
            parameterWindow.setDefault(defaultParametersSupplier.get());
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
    public interface IParametersSaveListener<T> {
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
