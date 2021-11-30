package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents filter window for UDM proxy values.
 * Contains {@link CheckBoxGroup} component, search toolbar, buttons toolbar.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 11/27/21
 *
 * @param <T> type of items
 * @author Uladzislau Shalamitski
 */
public class UdmCommonProxyValueFilterWindow<T> extends CommonFilterWindow<T> {

    private final IFilterWindowController<T> controller;
    private final Collection<T> filterItems;
    private final SearchWidget searchWidget;
    private CheckBoxGroup<T> checkBoxGroup;
    private ListDataProvider<T> listDataProvider;

    /**
     * Constructor.
     *
     * @param caption    window caption
     * @param controller {@link IFilterWindowController} instance
     * @param values     searchable listDataProvider properties.
     */
    @SafeVarargs
    public UdmCommonProxyValueFilterWindow(String caption, IFilterWindowController<T> controller,
                                           ValueProvider<T, List<String>>... values) {
        super(caption);
        this.controller = Objects.requireNonNull(controller);
        filterItems = controller.loadBeans();
        addFilterSaveListener(controller);
        searchWidget = new SearchWidget(() -> performSearch(values));
        VerticalLayout content = new VerticalLayout();
        content.addComponent(searchWidget);
        HorizontalLayout buttonsLayout = initButtonsLayout();
        Panel panel = initItemsPanel();
        content.addComponents(panel, buttonsLayout);
        content.setExpandRatio(panel, 1f);
        content.setSizeFull();
        content.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(content);
        setWidth(450, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        setResizable(false);
    }

    /**
     * Sets selected items ids.
     *
     * @param selectedItemsIds collection of selected items ids
     */
    public void setSelectedItemsIds(Set<T> selectedItemsIds) {
        if (CollectionUtils.isNotEmpty(selectedItemsIds)) {
            checkBoxGroup.setValue(selectedItemsIds);
        } else {
            selectAll();
        }
    }

    /**
     * @return collection (Set) of selected items ids.
     */
    public Set<T> getSelectedItemsIds() {
        return checkBoxGroup.getValue();
    }

    /**
     * Sets search widget prompt string.
     *
     * @param promptString prompt string to be displayed when search field is empty
     */
    public void setSearchPromptString(String promptString) {
        searchWidget.setPrompt(promptString);
    }

    /**
     * Fires an {@link FilterSaveEvent} with selected values.
     */
    void fireFilterSaveEvent() {
        fireEvent(new FilterSaveEvent<>(this, checkBoxGroup.getValue()));
    }

    /**
     * Performs search in the listDataProvider when user clicks on search icon.
     *
     * @param values searchable listDataProvider properties
     */
    @SuppressWarnings("unchecked")
    void performSearch(ValueProvider<T, List<String>>... values) {
        listDataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            Arrays.asList(values).forEach(provider -> listDataProvider.addFilter(
                value -> provider.apply(value)
                    .stream()
                    .anyMatch(field -> StringUtils.containsIgnoreCase(field, searchValue))));
        }
    }

    private Panel initItemsPanel() {
        listDataProvider = new ListDataProvider<>(filterItems);
        checkBoxGroup = new CheckBoxGroup<>(null, listDataProvider);
        checkBoxGroup.setItemCaptionGenerator(controller::getBeanItemCaption);
        checkBoxGroup.setHtmlContentAllowed(true);
        checkBoxGroup.setItemCaptionGenerator(Object::toString);
        Panel panel = new Panel(checkBoxGroup);
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        panel.setSizeFull();
        return panel;
    }

    private HorizontalLayout initButtonsLayout() {
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            UI.getCurrent().removeWindow(this);
            fireFilterSaveEvent();
        });
        Button selectAllButton = Buttons.createButton(ForeignUi.getMessage("button.select_all"));
        selectAllButton.addClickListener(clickEvent -> selectAll());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> checkBoxGroup.clear());
        return new HorizontalLayout(saveButton, selectAllButton, clearButton, Buttons.createCloseButton(this));
    }

    @SuppressWarnings("unchecked")
    private void selectAll() {
        filterItems.forEach(item -> checkBoxGroup.select(item));
    }
}
