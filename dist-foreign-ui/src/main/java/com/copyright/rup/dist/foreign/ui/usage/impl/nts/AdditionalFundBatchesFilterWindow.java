package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.IAdditionalFundBatchesFilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.ui.Button.ClickListener;

import java.util.List;

/**
 * Window to filter batches to create Additional Fund.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/27/2019
 *
 * @author Aliaksandr Liakh
 */
class AdditionalFundBatchesFilterWindow extends FilterWindow<UsageBatch> implements IAdditionalFundBatchesFilterWindow {

    /**
     * Constructor.
     *
     * @param controller instance of {@link IFilterWindowController}
     */
    AdditionalFundBatchesFilterWindow(IFilterWindowController<UsageBatch> controller) {
        super(ForeignUi.getMessage("window.batches_filter"), controller, "Continue", null,
            (ValueProvider<UsageBatch, List<String>>) batch -> List.of(batch.getName()));
        this.setSearchPromptString(ForeignUi.getMessage("prompt.batch"));
        VaadinUtils.addComponentStyle(this, "batches-filter-window");
    }

    /**
     * Updates {@link ClickListener} for the Save button.
     *
     * @param action instance of {@link Runnable}
     */
    void updateSaveButtonClickListener(Runnable action) {
        getSaveButtonRegistration().remove();
        getSaveButton().addClickListener((ClickListener) event -> {
            fireFilterSaveEvent();
            action.run();
        });
    }
}
