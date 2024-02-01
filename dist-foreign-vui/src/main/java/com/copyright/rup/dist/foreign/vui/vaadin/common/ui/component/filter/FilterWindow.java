package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.shared.Registration;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents modal window containing {@link CheckboxGroup} component, search toolbar (optional),
 * buttons toolbar. Window provides ability to search and select (with help of checkboxes) items of specified type.
 * Searching items is able only by properties present in listDataProvider and only when exact properties are specified
 * in constructor.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/13/17
 *
 * @param <T> bean type
 * @author Aliaksandr Radkevich
 */
public class FilterWindow<T> extends CommonFilterWindow<T> {

    private static final long serialVersionUID = -5049143488372574216L;

    private final IFilterWindowController<T> controller;
    private final Collection<T> filterItems;
    private CheckboxGroup<T> checkBoxGroup;
    private ListDataProvider<T> listDataProvider;
    private SearchWidget searchWidget;
    private Button saveButton;
    private Button selectAllButton;
    private Button clearButton;
    private Registration saveButtonRegistration;
    private Registration selectAllRegistration;
    private Registration clearButtonRegistration;

    /**
     * Constructor.
     *
     * @param caption    window caption
     * @param controller {@link IFilterWindowController} instance
     * @param values     searchable listDataProvider properties.
     *                   If none specified search is not allowed and search toolbar will not be displayed
     */
    @SafeVarargs
    public FilterWindow(String caption, IFilterWindowController<T> controller,
                        ValueProvider<T, List<String>>... values) {
        this(caption, controller, null, null, values);
    }

    /**
     * Constructor.
     *
     * @param caption            window caption
     * @param controller         {@link IFilterWindowController} instance
     * @param saveButtonCaption  caption of the Save button
     * @param clearButtonCaption caption of the Clear button
     * @param values             searchable listDataProvider properties.
     *                           If none specified search is not allowed and search toolbar will not be displayed
     */
    @SafeVarargs
    public FilterWindow(String caption, IFilterWindowController<T> controller,
                        String saveButtonCaption, String clearButtonCaption,
                        ValueProvider<T, List<String>>... values) {
        super(caption);
        this.filterItems = controller.loadBeans();
        this.controller = Objects.requireNonNull(controller);
        super.setWidth("450px");
        super.setHeight("400px");
        super.addFilterSaveListener(controller);
        super.add(initContent(values));
        super.getFooter().add(createButtonsLayout(saveButtonCaption, clearButtonCaption));
    }

    /**
     * Sets selected items ids.
     *
     * @param selectedItemsIds collection of selected items ids
     */
    public void setSelectedItemsIds(Set<T> selectedItemsIds) {
        checkBoxGroup.setValue(selectedItemsIds);
    }

    /**
     * @return collection (Set) of selected items ids.
     */
    public Set<T> getSelectedItemsIds() {
        return checkBoxGroup.getValue();
    }

    /**
     * Sets search widget prompt string. Only takes effect when searching is allowed.
     *
     * @param promptString prompt string to be displayed when search field is empty
     */
    public void setSearchPromptString(String promptString) {
        if (Objects.nonNull(searchWidget)) {
            searchWidget.setPrompt(promptString);
        }
    }

    /**
     * Sets SelectAll button visible.
     */
    public void setSelectAllButtonVisible() {
        selectAllButton.setVisible(true);
    }

    /**
     * Performs search in the listDataProvider when user clicks on search icon.
     *
     * @param values searchable listDataProvider properties
     */
    @SuppressWarnings("unchecked")
    public void performSearch(ValueProvider<T, List<String>>... values) {
        Set<T> selectedItems = checkBoxGroup.getValue();
        listDataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            Arrays.asList(values).forEach(provider -> listDataProvider.addFilter(
                value -> provider.apply(value)
                    .stream()
                    .anyMatch(field -> caseInsensitiveContains(field, searchValue))));
        }
        checkBoxGroup.setValue(selectedItems);
    }

    /**
     * @return instance of {@link CheckboxGroup}
     */
    protected CheckboxGroup<T> getCheckBoxGroup() {
        return checkBoxGroup;
    }

    /**
     * @return collection of items
     */
    protected Collection<T> getFilterItems() {
        return filterItems;
    }

    /**
     * Fires an {@link FilterSaveEvent} with selected values.
     */
    protected void fireFilterSaveEvent() {
        fireEvent(new FilterSaveEvent(this, checkBoxGroup.getValue()));
    }

    //{TODO vaadin23} check do we need all this methods once migration is completed
    protected Button getSaveButton() {
        return saveButton;
    }

    protected Button getSelectAllButton() {
        return selectAllButton;
    }

    protected Button getClearButton() {
        return clearButton;
    }

    protected Registration getSaveButtonRegistration() {
        return saveButtonRegistration;
    }

    protected Registration getSelectAllRegistration() {
        return selectAllRegistration;
    }

    protected Registration getClearButtonRegistration() {
        return clearButtonRegistration;
    }

    /**
     * Selects all items on window.
     */
    @SuppressWarnings("unchecked")
    protected void selectAll() {
        filterItems.forEach(item -> checkBoxGroup.select(item));
    }

    @SafeVarargs
    private VerticalLayout initContent(ValueProvider<T, List<String>>... values) {
        var content = VaadinUtils.initSizeFullVerticalLayout();
        VaadinUtils.setPadding(content, 0, 10, 0, 10);
        if (ArrayUtils.isNotEmpty(values)) {
            content.add(initSearchWidget(values));
        }
        var scroller = new Scroller(new Div(initItemsPanel()));
        scroller.setScrollDirection(ScrollDirection.BOTH);
        content.add(scroller);
        return content;
    }

    private HorizontalLayout createButtonsLayout(String saveButtonCaption, String clearButtonCaption) {
        saveButton = Buttons.createButton(Objects.nonNull(saveButtonCaption) ? saveButtonCaption : "Save");
        saveButtonRegistration = saveButton.addClickListener(event -> {
            fireFilterSaveEvent();
            close();
        });
        selectAllButton = Buttons.createButton("Select All");
        selectAllButton.setVisible(false);
        selectAllRegistration = selectAllButton.addClickListener(clickEvent -> selectAll());
        clearButton = Buttons.createButton(Objects.nonNull(clearButtonCaption) ? clearButtonCaption : "Clear");
        clearButtonRegistration = clearButton.addClickListener(event -> checkBoxGroup.clear());
        return new HorizontalLayout(saveButton, selectAllButton, clearButton, Buttons.createCloseButton(this));
    }

    private Section initItemsPanel() {
        initCheckboxGroup();
        Section section = new Section(checkBoxGroup);
        VaadinUtils.addComponentStyle(section, "scroller-panel");
        section.setSizeFull();
        return section;
    }

    private void initCheckboxGroup() {
        checkBoxGroup = new CheckboxGroup<>();
        listDataProvider = new ListDataProvider<>(Objects.requireNonNull(filterItems));
        checkBoxGroup.setItems(listDataProvider);
        checkBoxGroup.setItemLabelGenerator(controller::getBeanItemCaption);
        checkBoxGroup.addThemeVariants(CheckboxGroupVariant.MATERIAL_VERTICAL);
    }

    @SafeVarargs
    private SearchWidget initSearchWidget(ValueProvider<T, List<String>>... values) {
        searchWidget = new SearchWidget(() -> performSearch(values));
        setSearchPromptString("Enter search value");
        return searchWidget;
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        return StringUtils.contains(StringUtils.lowerCase(where), StringUtils.lowerCase(what));
    }
}
