package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget provides functionality for configuring items filter widget for Publication Types.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 01/19/2022
 *
 * @author Dzmitry Basiachenka
 */
public class PublicationTypeFilterWidget extends BaseUdmItemsFilterWidget<PublicationType>
    implements IFilterWindowController<PublicationType> {

    private static final String NULL = "NULL";

    private final Supplier<List<PublicationType>> supplier;
    private final Set<PublicationType> selectedItemsIds = new HashSet<>();

    /**
     * Constructor.
     *
     * @param supplier {@link PublicationType}s list supplier
     */
    public PublicationTypeFilterWidget(Supplier<List<PublicationType>> supplier) {
        super(ForeignUi.getMessage("label.pub_types"));
        this.supplier = supplier;
    }

    @Override
    public void reset() {
        super.reset();
        selectedItemsIds.clear();
    }

    @Override
    public List<PublicationType> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<PublicationType> getBeanClass() {
        return PublicationType.class;
    }

    @Override
    public String getBeanItemCaption(PublicationType publicationType) {
        return Objects.nonNull(publicationType.getName()) ? publicationType.getNameAndDescription() : NULL;
    }

    @Override
    public void onSave(FilterSaveEvent<PublicationType> event) {
        Set<PublicationType> itemsIds = event.getSelectedItemsIds();
        selectedItemsIds.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedItemsIds.addAll(itemsIds);
        }
    }

    @Override
    public FilterWindow<PublicationType> showFilterWindow() {
        FilterWindow<PublicationType> filterWindow =
            Windows.showFilterWindow(ForeignUi.getMessage("window.publication_types_filter"), this,
                (ValueProvider<PublicationType, List<String>>) pubType ->
                    Arrays.asList(ObjectUtils.defaultIfNull(pubType.getName(), NULL), pubType.getDescription()));
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        filterWindow.setSelectAllButtonVisible();
        filterWindow.setSearchPromptString(ForeignUi.getMessage("prompt.publication_type"));
        VaadinUtils.addComponentStyle(filterWindow, "publication-type-filter-window");
        return filterWindow;
    }
}
