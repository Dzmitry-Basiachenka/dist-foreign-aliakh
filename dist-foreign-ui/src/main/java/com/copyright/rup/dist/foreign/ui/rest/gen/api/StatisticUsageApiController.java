package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping
public class StatisticUsageApiController implements StatisticUsageApi {

    private final StatisticUsageApiDelegate delegate;

    public StatisticUsageApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) StatisticUsageApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new StatisticUsageApiDelegate() {});
    }

    @Override
    public StatisticUsageApiDelegate getDelegate() {
        return delegate;
    }

}
