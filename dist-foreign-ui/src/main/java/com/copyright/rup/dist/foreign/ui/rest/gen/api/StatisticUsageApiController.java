package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StatisticUsageApiController implements StatisticUsageApi {

    private final StatisticUsageApiDelegate delegate;

    @Autowired
    public StatisticUsageApiController(StatisticUsageApiDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public StatisticUsageApiDelegate getDelegate() {
        return delegate;
    }
}
