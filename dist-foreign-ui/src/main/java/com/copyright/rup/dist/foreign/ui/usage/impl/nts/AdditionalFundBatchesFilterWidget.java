package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.IAdditionalFundBatchesFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Widget to filter batches to create Additional Fund.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/27/2019
 *
 * @author Aliaksandr Liakh
 */
public class AdditionalFundBatchesFilterWidget implements IAdditionalFundBatchesFilterWidget,
    IFilterWindowController<UsageBatch>  {

    private static final long serialVersionUID = -2452045965750249648L;

    private final Supplier<List<UsageBatch>> supplier;
    private final List<UsageBatch> selectedUsageBatches = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param supplier supplier of {@link UsageBatch}'es
     */
    AdditionalFundBatchesFilterWidget(Supplier<List<UsageBatch>> supplier) {
        this.supplier = supplier;
    }

    @Override
    public List<UsageBatch> loadBeans() {
        return supplier.get();
    }

    @Override
    public Class<UsageBatch> getBeanClass() {
        return UsageBatch.class;
    }

    @Override
    public String getBeanItemCaption(UsageBatch usageBatch) {
        return usageBatch.getName();
    }

    @Override
    public void onSave(FilterSaveEvent<UsageBatch> event) {
        Set<UsageBatch> itemsIds = event.getSelectedItemsIds();
        selectedUsageBatches.clear();
        if (CollectionUtils.isNotEmpty(itemsIds)) {
            selectedUsageBatches.addAll(itemsIds);
            selectedUsageBatches.sort(Comparator.comparing(UsageBatch::getUpdateDate).reversed());
        }
    }

    @Override
    public List<UsageBatch> getSelectedUsageBatches() {
        return selectedUsageBatches;
    }
}
