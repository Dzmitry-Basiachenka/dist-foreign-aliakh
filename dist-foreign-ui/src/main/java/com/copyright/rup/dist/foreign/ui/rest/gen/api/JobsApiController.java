package com.copyright.rup.dist.foreign.ui.rest.gen.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@Api(tags = {"Jobs"})
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
