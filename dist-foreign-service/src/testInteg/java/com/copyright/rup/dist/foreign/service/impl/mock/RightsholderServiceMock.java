package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderOrganizationService;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderService;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;
import com.copyright.rup.dist.foreign.service.impl.RightsholderService;

import org.apache.camel.util.concurrent.SynchronousExecutorService;
import org.powermock.reflect.Whitebox;

/**
 * Mock for {@link RightsholderService} with synchronous executor service.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/22/2019
 *
 * @author Anton Azarenka
 */
public class RightsholderServiceMock extends RightsholderService {

    /**
     * Constructor.
     *
     * @param rightsholderRepository             an instance of {@link IRightsholderRepository}
     * @param prmRightsholderService             an instance of {@link IPrmRightsholderService}
     * @param prmRightsholderOrganizationService an instance of {@link IPrmRightsholderOrganizationService}
     */
    public RightsholderServiceMock(IRightsholderRepository rightsholderRepository,
                                   IPrmRightsholderService prmRightsholderService,
                                   IPrmRightsholderOrganizationService prmRightsholderOrganizationService) {
        super(rightsholderRepository, prmRightsholderService, prmRightsholderOrganizationService);
        Whitebox.setInternalState(this, "executorService", new SynchronousExecutorService());
    }
}
