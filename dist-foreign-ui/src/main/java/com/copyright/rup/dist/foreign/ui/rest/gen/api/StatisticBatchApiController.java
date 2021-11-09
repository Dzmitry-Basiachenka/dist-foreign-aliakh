package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping
public class StatisticBatchApiController implements StatisticBatchApi {

    private final StatisticBatchApiDelegate delegate;

    public StatisticBatchApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) StatisticBatchApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new StatisticBatchApiDelegate() {});
    }

    @Override
    public StatisticBatchApiDelegate getDelegate() {
        return delegate;
    }

}
