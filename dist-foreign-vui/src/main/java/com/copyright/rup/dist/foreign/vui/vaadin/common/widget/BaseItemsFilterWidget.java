package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Widget represents combination of {@link Html} and {@link Button} which shows number of selected items.
 * By default label display (0) and user should use button for selecting items from window which will be shown.
 * After user select 2 items from window label value will be (2).
 * It allows you to add listeners on button and provide abstract method, which return {@link CommonFilterWindow}.
 * {@link CommonFilterWindow} will be shown after button click event;
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/10/17
 *
 * @param <T> bean type
 * @author Mikalai Bezmen
 */
public abstract class BaseItemsFilterWidget<T> extends HorizontalLayout {

    private IFilterSaveListener<T> saveListener;
    private Html label;
    private Button button;

    /**
     * Constructor.
     *
     * @param caption for button
     */
    public BaseItemsFilterWidget(String caption) {
        initButton(caption);
        initLabel();
        setSpacing(false);
        add(label, button);
    }

    /**
     * Adds a click listener to the button. The listener is called whenever the user clicks on button.
     *
     * @param listener the listener to be added
     */
    public void addButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        button.addClickListener(listener);
    }

    /**
     * Sets count label value to 0.
     */
    public void reset() {
        setValue(0);
    }

    /**
     * Adds filter save listener.
     *
     * @param listener {@link IFilterSaveListener}
     */
    public void addFilterSaveListener(IFilterSaveListener<T> listener) {
        saveListener = listener;
    }

    /**
     * @return shows on UI and returns {@link CommonFilterWindow}.
     */
    public abstract CommonFilterWindow<T> showFilterWindow();

    private void setValue(Integer value) {
        label.setHtmlContent(String.format("<span>(%s)</span>", value));
    }

    private void initLabel() {
        label = new Html(String.format("<span>(%s)</span>", 0));
        label.getElement().getStyle().set("padding", "0px 5px 0px 0px");
    }

    private void initButton(String caption) {
        button = Buttons.createLinkButton(caption);
        VaadinUtils.setButtonsAutoDisabled(button);
        addClickListener(event -> {
            CommonFilterWindow<T> filterWindow = showFilterWindow();
            filterWindow.setModalWindowProperties(StringUtils.EMPTY, false);
            if (Objects.nonNull(saveListener)) {
                filterWindow.addFilterSaveListener(saveListener);
            }
            filterWindow.addFilterSaveListener(
                (IFilterSaveListener<T>) saveEvent -> setValue(saveEvent.getSelectedItemsIds().size()));
        });
    }
}
