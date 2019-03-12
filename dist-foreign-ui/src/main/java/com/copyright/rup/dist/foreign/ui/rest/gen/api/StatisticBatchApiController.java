package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StatisticBatchApiController implements StatisticBatchApi {

    private final StatisticBatchApiDelegate delegate;

    @Autowired
    public StatisticBatchApiController(StatisticBatchApiDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public StatisticBatchApiDelegate getDelegate() {
        return delegate;
    }
}
