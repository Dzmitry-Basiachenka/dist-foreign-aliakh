package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StatisticApiController implements StatisticApi {

    private final StatisticApiDelegate delegate;

    @Autowired
    public StatisticApiController(StatisticApiDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public StatisticApiDelegate getDelegate() {
        return delegate;
    }
}
