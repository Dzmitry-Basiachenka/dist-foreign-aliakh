package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@Api(tags = {"StatisticUsage"})
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
