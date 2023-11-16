package com.copyright.rup.dist.foreign.vui.main;

import com.vaadin.flow.spring.VaadinMVCWebAppInitializer;

import java.util.Collection;
import java.util.List;

/**
 * For configuring Vaadin Apps with Spring MVC.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 11/16/2023
 *
 * @author Anton Azarenka
 * @see <a href="https://vaadin.com/docs/latest/flow/integrations/spring/spring-mvc">Vaadin Doc</a>
 */
public class FdaWebInitializer extends VaadinMVCWebAppInitializer {

    @Override
    protected Collection<Class<?>> getConfigurationClasses() {
        return List.of(UiConfig.class);
    }
}
