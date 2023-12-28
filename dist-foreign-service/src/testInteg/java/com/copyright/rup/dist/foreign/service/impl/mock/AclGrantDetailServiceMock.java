package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.service.impl.acl.AclGrantDetailService;
import org.apache.camel.util.concurrent.SynchronousExecutorService;
import org.powermock.reflect.Whitebox;

/**
 * Mock for {@link AclGrantDetailService} with synchronous executor service.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 12/21/2023
 *
 * @author Aliaksandr Liakh
 */
public class AclGrantDetailServiceMock extends AclGrantDetailService {

    /**
     * Constructor.
     */
    public AclGrantDetailServiceMock() {
        Whitebox.setInternalState(this, "executorService", new SynchronousExecutorService());
    }
}
