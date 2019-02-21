package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class JobsApiController implements JobsApi {

    private final JobsApiDelegate delegate;

    @Autowired
    public JobsApiController(JobsApiDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public JobsApiDelegate getDelegate() {
        return delegate;
    }
}
