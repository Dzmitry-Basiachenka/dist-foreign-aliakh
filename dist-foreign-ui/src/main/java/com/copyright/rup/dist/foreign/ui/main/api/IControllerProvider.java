package com.copyright.rup.dist.foreign.ui.main.api;

import com.copyright.rup.vaadin.widget.api.IController;

import java.util.Optional;

/**
 * Provides {@link IController} instance.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/6/19
 *
 * @param <T> type of controller
 * @author Stanislau Rudak
 */
public interface IControllerProvider<T extends IController> {

    /**
     * @return optional {@link IController} instance.
     */
    Optional<T> getController();
}
