package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;
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

    private final Supplier<Window> windowSupplier;
    private Button button;
    private IParametersSaveListener<T> saveListener;

    /**
     * Constructor.
     *
     * @param caption           button caption
     * @param windowInitializer supplier for window initialization
     */
    public AaclScenarioParameterWidget(String caption, Supplier<Window> windowInitializer) {
        this.windowSupplier = windowInitializer;
        initButton(caption);
        addComponent(button);
        setExpandRatio(button, 1);
    }

    /**
     * Adds parameter save listener.
     *
     * @param listener {@link IParametersSaveListener}
     */
    public void addParameterSaveListener(IParametersSaveListener<T> listener) {
        saveListener = listener;
    }

    private void initButton(String caption) {
        button = Buttons.createButton(caption);
        button.addStyleName(ValoTheme.BUTTON_LINK);
        VaadinUtils.setButtonsAutoDisabled(button);
        button.addClickListener(event -> {
            Window filterWindow = windowSupplier.get();
            Windows.showModalWindow(filterWindow);
            filterWindow.addListener(ParametersSaveEvent.class, saveListener, IParametersSaveListener.SAVE_HANDLER);
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
