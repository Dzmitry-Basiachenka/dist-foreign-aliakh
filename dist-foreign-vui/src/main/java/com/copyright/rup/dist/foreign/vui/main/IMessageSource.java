package com.copyright.rup.dist.foreign.vui.main;

/**
 * Interface for message source.
 *
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 10/19/2023
 *
 * @author Aliaksandr Liakh
 */
public interface IMessageSource {

    /**
     * Gets string message by key and parameters.
     *
     * @param key        message key
     * @param parameters message parameters
     * @return the string message
     */
    String getStringMessage(String key, Object... parameters);
}
