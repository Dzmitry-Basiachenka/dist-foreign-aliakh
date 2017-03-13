package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.lazytable.IBeanLoader;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Interface for controller for {@link IUsagesWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/2017
 *
 * @author Mikita Hladkikh
 */
public interface IUsagesController extends IController<IUsagesWidget>, IBeanLoader<UsageDto>, IStreamSource {

    /**
     * {@link #onFilterChanged(FilterChangedEvent)}.
     */
    Method ON_FILTER_CHANGED =
        ReflectTools.findMethod(IUsagesController.class, "onFilterChanged", FilterChangedEvent.class);

    /**
     * Initializes {@link IUsagesFilterWidget}.
     *
     * @return initialized {@link IUsagesFilterWidget}
     */
    IUsagesFilterWidget initUsagesFilterWidget();

    /**
     * Handles changes of filter.
     *
     * @param event event
     */
    void onFilterChanged(FilterChangedEvent event);

    /**
     * Gets RRO name from PRM by account number.
     *
     * @param rroAccountNumber RRO account number
     * @return RRO name
     */
    String getRroName(Long rroAccountNumber);

    /**
     * Checks whether usage batch with provided name already exists or not.
     *
     * @param name usage batch name
     * @return {@code true} - if usage batch exists, {@code false} - otherwise
     */
    boolean usageBatchExists(String name);

    /**
     * @return list of {@link UsageBatch}es available for deleting.
     */
    List<UsageBatch> getUsageBatches();

    /**
     * Deletes {@link UsageBatch} and all it's details.
     *
     * @param usageBatch {@link UsageBatch} to delete
     */
    void deleteUsageBatch(UsageBatch usageBatch);

    /**
     * Gets a list of scenarios names that are associated with at least one
     * {@link com.copyright.rup.dist.foreign.domain.Usage} from the {@link UsageBatch} with given id.
     *
     * @param batchId {@link UsageBatch} id
     * @return a list of scenarios names
     */
    List<String> getScenariosNamesAssociatedWithUsageBatch(String batchId);

    /**
     * Inserts usage batch and it's usages.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int loadUsageBatch(UsageBatch usageBatch, List<Usage> usages);
}
