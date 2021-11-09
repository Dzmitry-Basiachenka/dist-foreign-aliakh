package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping
public class JobsApiController implements JobsApi {

    private final JobsApiDelegate delegate;

    public JobsApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) JobsApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new JobsApiDelegate() {});
    }

    @Override
    public JobsApiDelegate getDelegate() {
        return delegate;
    }

}
