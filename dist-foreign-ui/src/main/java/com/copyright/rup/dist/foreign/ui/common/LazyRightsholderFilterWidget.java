package com.copyright.rup.dist.foreign.ui.common;


import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterController;
import com.copyright.rup.dist.foreign.ui.common.LazyRightsholderFilterWindow.IRightsholderFilterSaveListener;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import java.util.HashSet;
import java.util.Set;

/**
 * Widget represents combination of {@link Label} and {@link Button} which shows number of selected items.
 * It allows you to add listeners on button.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/02/18
 *
 * @author Ihar Suvorau
 */
public class LazyRightsholderFilterWidget extends HorizontalLayout {

    private final IAuditFilterController controller;
    private Set<Rightsholder> selectedItems = new HashSet<>();
    private IRightsholderFilterSaveListener saveListener;
    private Label label;
    private Button button;

    /**
     * Constructor.
     *
     * @param caption    for button
     * @param controller instance of {@link IAuditFilterController}
     */
    public LazyRightsholderFilterWidget(String caption, IAuditFilterController controller) {
        this.controller = controller;
        initButton(caption);
        initLabel();
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        addComponents(label, button);
        setExpandRatio(button, 1);
    }

    /**
     * Adds a click listener to the button. The listener is called whenever the user clicks on button.
     *
     * @param clickListener the listener to be added
     */
    public void addClickListener(ClickListener clickListener) {
        button.addClickListener(clickListener);
    }

    /**
     * Adds filter save listener.
     *
     * @param listener {@link IRightsholderFilterSaveListener}
     */
    public void addFilterSaveListener(IRightsholderFilterSaveListener<Rightsholder> listener) {
        saveListener = listener;
    }

    /**
     * Sets count label value to 0.
     */
    public void reset() {
        selectedItems = new HashSet<>();
        setValue(0);
    }

    private void setValue(Integer value) {
        label.setValue(String.format("(%s)", value));
    }

    private void initLabel() {
        label = new Label();
        label.setSizeUndefined();
        setValue(0);
    }

    private void initButton(String caption) {
        button = Buttons.createButton(caption);
        button.addStyleName(ValoTheme.BUTTON_LINK);
        VaadinUtils.setButtonsAutoDisabled(button);
        addClickListener(event -> {
            LazyRightsholderFilterWindow filterWindow = new LazyRightsholderFilterWindow(caption, controller);
            filterWindow.setSelectedItemsIds(selectedItems);
            Windows.showModalWindow(filterWindow);
            filterWindow.addFilterSaveListener(saveListener);
            filterWindow.addFilterSaveListener(saveEvent -> {
                this.selectedItems = saveEvent.getSelectedItemsIds();
                setValue(saveEvent.getSelectedItemsIds().size());
            });
        });
    }
}
