package com.copyright.rup.dist.foreign.ui.common;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.CallbackDataProvider.CountCallback;
import com.vaadin.data.provider.CallbackDataProvider.FetchCallback;
import com.vaadin.data.provider.DataProvider;

/**
 * Creator of an instance of {@link CallbackDataProvider} that shows loading indicator during reading.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/24/2018
 *
 * @author Aliaksandr Liakh
 */
public final class LoadingIndicatorDataProvider {

    private LoadingIndicatorDataProvider() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Creates an instance of {@link CallbackDataProvider} that wraps both its callbacks with
     * {@link LoadingIndicatorWrapper#wrap}.
     *
     * @param fetchCallback function that returns a stream of items for a query
     * @param countCallback function that returns the number of items for a query
     * @param <T>           the type of the items
     * @return a new callback data provider
     */
    public static <T> CallbackDataProvider<T, Void> fromCallbacks(
        FetchCallback<T, Void> fetchCallback,
        CountCallback<T, Void> countCallback) {
        return DataProvider.fromCallbacks(
            query -> LoadingIndicatorWrapper.wrap(() -> fetchCallback.fetch(query)),
            query -> LoadingIndicatorWrapper.wrap(() -> countCallback.count(query))
        );
    }
}
