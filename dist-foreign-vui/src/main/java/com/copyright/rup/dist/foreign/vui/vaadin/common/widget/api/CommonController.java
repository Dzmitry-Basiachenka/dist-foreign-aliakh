package com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api;

import java.util.Objects;

/**
 * Abstract controller class that represents controller for {@link IWidget} instance.
 * Provides basic methods for instantiation, refreshing of the widget.
 * If widget implements {@link IMediatorProvider} interface
 * {@link IMediator#applyPermissions()} will be called after initializing the widget.
 * It allows to manage user permissions and hide/disable UI elements.
 * Has two different methods to initialize the widget: {@link #initWidget()} and {@link #initWidget(boolean)}. Second
 * method allows to get cached instance of widget if {@link Boolean#TRUE} was passed as the parameter.
 *
 * @param <T> widget class
 * @author Nikita Levyankov
 */
public abstract class CommonController<T extends IWidget> implements IController<T> {

    private T widget;
    private boolean widgetInitialized;

    @Override
    public final T getWidget() {
        if (null == widget) {
            newWidgetInstance();
        }
        return widget;
    }

    @Override
    public T initWidget() {
        return initWidget(false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T initWidget(boolean useCached) {
        if (widgetInitialized && useCached) {
            return getWidget();
        } else {
            IWidget iWidget = newWidgetInstance().init();
            if (iWidget instanceof IMediatorProvider) {
                IMediator mediator = Objects.requireNonNull(((IMediatorProvider) iWidget).initMediator());
                mediator.applyPermissions();
            }
            widgetInitialized = true;
            return (T) iWidget;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void refreshWidget() {
        if (getWidget() instanceof IRefreshable) {
            ((IRefreshable) getWidget()).refresh();
        }
    }

    @Override
    public boolean isWidgetInitialized() {
        return widgetInitialized;
    }

    /**
     * @return new widget instance. The subclasses should call widget constructor.
     */
    protected abstract T instantiateWidget();

    @SuppressWarnings("unchecked")
    private T newWidgetInstance() {
        widget = Objects.requireNonNull(instantiateWidget());
        widget.setController(this);
        return widget;
    }
}
