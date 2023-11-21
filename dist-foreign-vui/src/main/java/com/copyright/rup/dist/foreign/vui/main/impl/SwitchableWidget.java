package com.copyright.rup.dist.foreign.vui.main.impl;

import com.copyright.rup.dist.foreign.vui.main.api.IControllerProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IRefreshable;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Allows to display different widgets depending on provides {@link IController} instance.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @param <W> type of widget
 * @param <C> type of controller
 * @author Stanislau Rudak
 */
class SwitchableWidget<W extends IWidget<C>, C extends IController<W>> extends VerticalLayout implements IRefreshable {

    private final IControllerProvider<C> controllerProvider;
    private final Consumer<W> listenerRegisterer;

    /**
     * Constructor.
     *
     * @param controllerProvider instance of {@link IControllerProvider}
     * @param listenerRegisterer a {@link Consumer} that adds listeners to the widget
     */
    public SwitchableWidget(IControllerProvider<C> controllerProvider, Consumer<W> listenerRegisterer) {
        this.controllerProvider = controllerProvider;
        this.listenerRegisterer = listenerRegisterer;
        initLayout();
    }

    /**
     * Updates widget content provided {@link IController} instance.
     *
     * @return {@code true} if the tab should be visible, {@code false} otherwise
     */
    public boolean updateProductFamily() {
        Optional<C> controller = controllerProvider.getController();
        if (controller.isPresent()) {
            W widget = controller.get().initWidget();
            listenerRegisterer.accept(widget);
            add((Component) widget);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void refresh() {
        getChildren().collect(Collectors.toSet()).forEach(component -> {
            if (component instanceof IRefreshable) {
                ((IRefreshable) component).refresh();
            }
        });
    }

    private void initLayout() {
        setClassName("switchable-widget");
        setSizeFull();
    }
}
