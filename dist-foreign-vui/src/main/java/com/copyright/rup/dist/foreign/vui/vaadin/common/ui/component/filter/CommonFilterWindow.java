package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.dialog.Dialog;

import java.util.Set;

/**
 * Represents common class for filter windows.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 11/24/2021
 *
 * @param <T> bean type
 * @author Ihar Suvorau
 */
public class CommonFilterWindow<T> extends CommonDialog {

    private static final long serialVersionUID = -6486755477136957336L;

    /**
     * Constructor.
     *
     * @param caption window caption
     */
    public CommonFilterWindow(String caption) {
        super.setHeaderTitle(caption);
    }

    /**
     * Adds filter save listener.
     *
     * @param listener {@link IFilterSaveListener}
     */
    public void addFilterSaveListener(IFilterSaveListener listener) {
        ComponentUtil.addListener(this, FilterSaveEvent.class, listener);
    }

    /**
     * An event that occurs when user clicks 'Save' button on filter window.
     */
    public static class FilterSaveEvent<T> extends ComponentEvent<Dialog> {

        private static final long serialVersionUID = -5899327809956259843L;

        private final Set<T> selectedItemsIds;

        /**
         * Constructor.
         *
         * @param source           event source
         * @param selectedItemsIds selected items ids
         */
        public FilterSaveEvent(Dialog source, Set<T> selectedItemsIds) {
            super(source, true);
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
     * Listener that handles {@link FilterSaveEvent} events.
     *
     * @param <T> items ids type
     */
    public interface IFilterSaveListener<T> extends ComponentEventListener<FilterSaveEvent<T>> {

        @Override
        void onComponentEvent(FilterSaveEvent<T> event);
    }
}
