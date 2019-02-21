package com.copyright.rup.dist.foreign.ui.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for redirects.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/20/2019
 *
 * @author Aliaksanr Liakh
 */
@Controller
public class RedirectController {

    /**
     * Redirects from "/" to "/swagger-ui.html".
     *
     * @return instance of {@link ModelAndView}
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView redirectToSwaggerUI() {
        return new ModelAndView("redirect:/api/swagger-ui.html");
    }
}
