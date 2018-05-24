package com.copyright.rup.dist.foreign.ui.common;

import com.vaadin.ui.JavaScript;

import java.util.function.Supplier;

/**
 * Wrapper to show HTML loading indicator with CSS selector '.v-loading-indicator'.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/23/2018
 *
 * @author Aliaksandr Liakh
 */
final class LoadingIndicatorWrapper {

    private static final String JAVA_SCRIPT_SHOW_LOADING_INDICATOR =
        "var elements = document.querySelectorAll('.v-loading-indicator'); " +
            "for(var i = 0; i < elements.length; i++) " +
            "{ elements[i].style.display = 'block'; }";

    private LoadingIndicatorWrapper() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Shows loading indicator during execution of a {@link Supplier}.
     *
     * @param supplier the supplier
     * @param <T>      the type of the supplier
     * @return result of the supplier
     */
    static <T> T wrap(Supplier<T> supplier) {
        JavaScript.getCurrent().execute(JAVA_SCRIPT_SHOW_LOADING_INDICATOR);
        return supplier.get();
    }
}
