package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.ui.report.api.ITaxNotificationReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportController;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link AclReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclReportControllerTest {

    private IAclCommonReportController liabilitiesByAggLicClassReportController;
    private IAclCommonReportController liabilityDetailsReportController;
    private IAclCommonReportController liabilitiesByRhReportController;
    private ITaxNotificationReportController taxNotificationReportController;
    private AclReportController aclReportController;

    @Before
    public void setUp() {
        aclReportController = new AclReportController();
        liabilitiesByAggLicClassReportController = createMock(IAclCommonReportController.class);
        liabilityDetailsReportController = createMock(IAclCommonReportController.class);
        liabilitiesByRhReportController = createMock(IAclCommonReportController.class);
        taxNotificationReportController = createMock(ITaxNotificationReportController.class);
        Whitebox.setInternalState(aclReportController, "liabilitiesByAggLicClassReportController",
            liabilitiesByAggLicClassReportController);
        Whitebox.setInternalState(aclReportController, "liabilityDetailsReportController",
            liabilityDetailsReportController);
        Whitebox.setInternalState(aclReportController, "liabilitiesByRhReportController",
            liabilitiesByRhReportController);
        Whitebox.setInternalState(aclReportController, taxNotificationReportController);
    }

    @Test
    public void testGetAclLiabilitiesByAggLicClassReportController() {
        assertSame(liabilitiesByAggLicClassReportController,
            aclReportController.getAclLiabilitiesByAggLicClassReportController());
    }

    @Test
    public void testGetAclLiabilityDetailsReportController() {
        assertSame(liabilityDetailsReportController, aclReportController.getAclLiabilityDetailsReportController());
    }

    @Test
    public void testGetAclLiabilitiesByRhReportController() {
        assertSame(liabilitiesByRhReportController, aclReportController.getAclLiabilitiesByRhReportController());
    }

    @Test
    public void testGetTaxNotificationReportController() {
        assertSame(taxNotificationReportController, aclReportController.getTaxNotificationReportController());
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(aclReportController.instantiateWidget(), instanceOf(AclReportWidget.class));
    }
}
