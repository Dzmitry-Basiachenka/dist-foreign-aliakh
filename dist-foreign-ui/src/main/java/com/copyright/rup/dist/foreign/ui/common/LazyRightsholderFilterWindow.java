package com.copyright.rup.dist.foreign.ui.common;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.event.SerializableEventListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.util.ReflectTools;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents modal window containing {@link Grid} component with lazy loading, search toolbar, buttons toolbar.
 * Window provides ability to search and select items of specified type.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/02/18
 *
 * @author Ihar Suvorau
 */
public class LazyRightsholderFilterWindow extends Window {

    private final ICommonAuditFilterController controller;
    private final SearchWidget searchWidget;
    private Grid<Rightsholder> grid;
    private DataProvider<Rightsholder, Void> dataProvider;

    /**
     * Constructor.
     *
     * @param caption    window caption
     * @param controller {@link ICommonAuditFilterController} instance
     */
    public LazyRightsholderFilterWindow(String caption, ICommonAuditFilterController controller) {
        super(ForeignUi.getMessage("window.filter_format", caption));
        this.controller = Objects.requireNonNull(controller);
        setWidth(450, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        setResizable(false);
        VerticalLayout content = new VerticalLayout();
        searchWidget = new SearchWidget(() -> dataProvider.refreshAll());
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.rightsholder"));
        content.addComponent(searchWidget);
        Panel panel = initItemsPanel();
        HorizontalLayout buttonsLayout = createButtonsLayout();
        content.addComponents(panel, buttonsLayout);
        content.setExpandRatio(panel, 1f);
        content.setSizeFull();
        content.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(content);
    }

    /**
     * Adds filter save listener.
     *
     * @param listener {@link IRightsholderFilterSaveListener}
     */
    public void addFilterSaveListener(IRightsholderFilterSaveListener listener) {
        addListener(RightsholdersFilterSaveEvent.class, listener, IRightsholderFilterSaveListener.SAVE_HANDLER);
    }

    /**
     * Sets selected items ids.
     *
     * @param selectedItemsIds collection of selected items ids
     */
    public void setSelectedItemsIds(Set<Rightsholder> selectedItemsIds) {
        selectedItemsIds.forEach(row -> grid.select(row));
    }

    /**
     * @return set of selected items ids.
     */
    @SuppressWarnings("unchecked")
    public Set<Rightsholder> getSelectedItemsIds() {
        return grid.getSelectedItems();
    }


    @SuppressWarnings("unchecked")
    private HorizontalLayout createButtonsLayout() {
        Button saveButton = Buttons.createButton("Save");
        saveButton.addClickListener(event -> {
            UI.getCurrent().removeWindow(this);
            fireEvent(new RightsholdersFilterSaveEvent<>(this, grid.getSelectedItems()));
        });
        Button clearButton = Buttons.createButton("Clear");
        clearButton.addClickListener(event -> grid.deselectAll());
        return new HorizontalLayout(saveButton, clearButton, Buttons.createCloseButton(this));
    }

    private Panel initItemsPanel() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(controller.getProductFamily(), getSearchValue(),
                query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getBeansCount(controller.getProductFamily(), getSearchValue()));
        grid = new Grid<>(dataProvider);
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addColumn(rightsholder -> {
            String rightsholderName =
                StringUtils.defaultIfBlank(rightsholder.getName(), ForeignUi.getMessage("message.error.rh_not_found"));
            return String.format("%s - %s", rightsholder.getAccountNumber(), rightsholderName);
        }, new HtmlRenderer())
            .setWidth(500);
        grid.setHeaderVisible(false);
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "lazy-rightsholders-grid");
        Panel panel = new Panel(grid);
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        panel.setSizeFull();
        return panel;
    }

    private String getSearchValue() {
        return StringUtils.defaultIfBlank(searchWidget.getSearchValue(), null);
    }

    /**
     * An event that occurs when user clicks 'Save' button on filter window.
     *
     * @param <T> items ids type
     */
    public class RightsholdersFilterSaveEvent<T> extends Event {

        private final Set<T> selectedItemsIds;

        /**
         * Constructor.
         *
         * @param source           event source
         * @param selectedItemsIds selected items ids
         */
        public RightsholdersFilterSaveEvent(Component source, Set<T> selectedItemsIds) {
            super(source);
            this.selectedItemsIds = selectedItemsIds;
        }

        /**
         * @return a set of selected items.
         */
        public Set<T> getSelectedItemsIds() {
            return selectedItemsIds;
        }
    }

    /**
     * Listener that handles {@link RightsholdersFilterSaveEvent} events.
     *
     * @param <T> items ids type
     */
    public interface IRightsholderFilterSaveListener<T> extends SerializableEventListener {

        /**
         * Listener method.
         */
        Method SAVE_HANDLER = ReflectTools.findMethod(IRightsholderFilterSaveListener.class, "onSave",
            RightsholdersFilterSaveEvent.class);

        /**
         * Handles {@link RightsholdersFilterSaveEvent} events.
         *
         * @param event {@link RightsholdersFilterSaveEvent}
         */
        void onSave(RightsholdersFilterSaveEvent<T> event);
    }
}
