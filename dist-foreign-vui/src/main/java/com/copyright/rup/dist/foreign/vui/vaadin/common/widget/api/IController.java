package com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api;

import com.vaadin.flow.function.SerializableEventListener;

/**
 * Common interface that represents Controller for UI components.
 * Controller provides convenient way to initialize widget.
 * The basic interface provides ability to refresh the widget.
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 1/25/13
 *
 * @param <T> widget instance.
 * @author Nikita Levyankov
 * @author Aliaksandr_Radkevich
 * @author Anton Azarenka
 */
public interface IController<T extends IWidget> extends SerializableEventListener {

    /**
     * @return the initialized widget.
     */
    T getWidget();

    /**
     * Performs initialization of component.
     *
     * @return the instance of initialized component.
     */
    T initWidget();

    /**
     * Method is similar to {@link #initWidget()}.
     * The difference is in the boolean flag which is used to retrieve already created widget instead of initializing
     * new instance every time.
     *
     * @param useCached true - to reuse current instance of widget, false - {@link IWidget#init()} will be called.
     * @return the widget instance.
     */
    T initWidget(boolean useCached);

    /**
     * Refreshes the widget.
     *
     * @see IRefreshable
     */
    void refreshWidget();

    /**
     * Gets flag that shows whether widget was initialized or not.
     *
     * @return true if was initialized, false - not.
     */
    boolean isWidgetInitialized();
}
