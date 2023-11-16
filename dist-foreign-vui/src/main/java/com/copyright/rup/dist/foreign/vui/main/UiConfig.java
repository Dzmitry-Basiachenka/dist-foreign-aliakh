package com.copyright.rup.dist.foreign.vui.main;

import com.vaadin.flow.spring.annotation.EnableVaadin;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuration class for UI.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 11/16/2023
 *
 * @author Anton Azarenka
 */
@Configuration
@EnableVaadin
@EnableWebMvc
@ImportResource({
        "classpath:/dist-foreign-rest-auth-context.xml",
        "classpath:/dist-foreign-ui-context.xml",
        "classpath:/dist-foreign-ui-rest-context.xml"
})
public class UiConfig {
}
