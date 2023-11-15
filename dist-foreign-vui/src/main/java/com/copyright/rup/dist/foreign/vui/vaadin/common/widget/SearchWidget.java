package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Widget that provides search functionality.
 * <p>
 * Copyright (C) 2016 copyright.com
 * <p>
 * Date: 10/26/2016
 *
 * @author Nikita Levyankov
 */
public class SearchWidget extends HorizontalLayout {

    private final TextField searchField = new TextField();
    private final ISearchController controller;
    private Button searchButton;
    private String appliedSearchValue = StringUtils.EMPTY;

    /**
     * Constructs widget.
     *
     * @param controller an instance of {@link ISearchController}
     */
    public SearchWidget(ISearchController controller) {
        this.controller = Objects.requireNonNull(controller);
        init();
    }

    /**
     * @return the value of search field.
     */
    public String getSearchValue() {
        return appliedSearchValue;
    }

    /**
     * Sets search value.
     *
     * @param searchValue search value
     */
    public void setSearchValue(String searchValue) {
        searchField.setValue(searchValue);
        appliedSearchValue = searchValue;
    }

    /**
     * Sets prompt to the search field.
     *
     * @param prompt the prompt
     */
    public void setPrompt(String prompt) {
        searchField.setPlaceholder(prompt);
    }

    /**
     * Clears search value.
     */
    public void clearSearchValue() {
        searchField.clear();
        appliedSearchValue = StringUtils.EMPTY;
    }

    /**
     * @return {@link ISearchController} instance.
     */
    ISearchController getController() {
        return controller;
    }

    private void init() {
        VaadinUtils.addComponentStyle(this, "search-toolbar");
        VaadinUtils.setMaxComponentsWidth(this);
        initSearchField();
        initButtons();
        VaadinUtils.setMaxComponentsWidth(this);
        setSpacing(false);
    }

    private void initButtons() {
        searchButton = new Button(VaadinIcon.SEARCH.create(), event -> performSearch());
        searchButton.getElement().getStyle().set("margin", "4px 1px 1px 1px");
        searchButton.setTooltipText("Search");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchButton.addClassName("button-search");
        Button clearButton = new Button(VaadinIcon.CLOSE_SMALL.create(), event -> {
            searchField.clear();
            performSearch();
        });
        clearButton.setTooltipText("Clear");
        clearButton.addClassName("button-clear");
        add(searchButton, clearButton);
    }

    private void initSearchField() {
        VaadinUtils.setMaxComponentsWidth(searchField);
        searchField.addFocusListener(event -> searchButton.addClickShortcut(Key.ENTER));
        add(searchField);
    }

    private void performSearch() {
        searchField.setValue(StringUtils.strip(searchField.getValue()));
        appliedSearchValue = searchField.getValue();
        controller.performSearch();
    }

    /**
     * Interface to be implemented in order to provide search functionality for the widget.
     */
    public interface ISearchController {

        /**
         * Handles click on search button.
         */
        void performSearch();
    }
}
