package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.repository.api.ILicenseeClassRepository;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.impl.aacl.LicenseeClassService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Validates {@link LicenseeClassService}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 01/27/2020
 *
 * @author Anton Azarenka
 */
public class LicenseeClassServiceTest {

    private static final String ENROLLMENT_PROFILE = "EXGP";
    private static final String DISCIPLINE = "Physical Sciences & Mathematics";
    private static final String AACL_PRODUCT_FAMILY = "AACL";

    private ILicenseeClassService licenseeClassService;
    private ILicenseeClassRepository licenseeClassRepository;

    @Before
    public void setUp() {
        licenseeClassService = new LicenseeClassService();
        licenseeClassRepository = createMock(ILicenseeClassRepository.class);
        Whitebox.setInternalState(licenseeClassService, "licenseeClassRepository", licenseeClassRepository);
    }

    @Test
    public void testGetAggregateLicenseeClasses() {
        List<AggregateLicenseeClass> aggregateLicenseeClasses = Collections.singletonList(new AggregateLicenseeClass());
        expect(licenseeClassRepository.findAggregateLicenseeClassesByProductFamily(AACL_PRODUCT_FAMILY))
            .andReturn(aggregateLicenseeClasses).once();
        replay(licenseeClassRepository);
        assertSame(aggregateLicenseeClasses, licenseeClassService.getAggregateLicenseeClasses(AACL_PRODUCT_FAMILY));
        verify(licenseeClassRepository);
    }

    @Test
    public void testGetDetailLicenseeClasses() {
        List<DetailLicenseeClass> detailLicenseeClasses = Collections.singletonList(new DetailLicenseeClass());
        expect(licenseeClassRepository.findDetailLicenseeClassesByProductFamily(AACL_PRODUCT_FAMILY))
            .andReturn(detailLicenseeClasses).once();
        replay(licenseeClassRepository);
        assertSame(detailLicenseeClasses, licenseeClassService.getDetailLicenseeClasses(AACL_PRODUCT_FAMILY));
        verify(licenseeClassRepository);
    }

    @Test
    public void testGetDetailLicenseeClassesByScenarioId() {
        String scenarioId = "43e6b6e8-4c80-40ba-9836-7b27b2bbca5f";
        List<DetailLicenseeClass> detailLicenseeClasses = Collections.singletonList(new DetailLicenseeClass());
        expect(licenseeClassRepository.findDetailLicenseeClassesByScenarioId(scenarioId))
            .andReturn(detailLicenseeClasses).once();
        replay(licenseeClassRepository);
        assertSame(detailLicenseeClasses, licenseeClassService.getDetailLicenseeClassesByScenarioId(scenarioId));
        verify(licenseeClassRepository);
    }

    @Test
    public void testAaclDetailLicenseeClassExist() {
        expect(licenseeClassRepository.aaclDetailLicenseeClassExists(ENROLLMENT_PROFILE, DISCIPLINE))
            .andReturn(true).once();
        replay(licenseeClassRepository);
        assertTrue(licenseeClassService.aaclDetailLicenseeClassExists(ENROLLMENT_PROFILE, DISCIPLINE));
        verify(licenseeClassRepository);
    }

    @Test
    public void testAaclDetailLicenseeClassExistWithDisciplineNull() {
        expect(licenseeClassRepository.aaclDetailLicenseeClassExists(ENROLLMENT_PROFILE, null)).andReturn(false).once();
        replay(licenseeClassRepository);
        assertFalse(licenseeClassService.aaclDetailLicenseeClassExists(ENROLLMENT_PROFILE, null));
        verify(licenseeClassRepository);
    }

    @Test
    public void testAaclDetailLicenseeClassExistWithEnrollmentProfileNull() {
        expect(licenseeClassRepository.aaclDetailLicenseeClassExists(null, DISCIPLINE)).andReturn(false).once();
        replay(licenseeClassRepository);
        assertFalse(licenseeClassService.aaclDetailLicenseeClassExists(null, DISCIPLINE));
        verify(licenseeClassRepository);
    }

    @Test
    public void testAaclDetailLicenseeClassExistWithEnrollmentProfileAndDisciplineNull() {
        expect(licenseeClassRepository.aaclDetailLicenseeClassExists(null, null)).andReturn(false).once();
        replay(licenseeClassRepository);
        assertFalse(licenseeClassService.aaclDetailLicenseeClassExists(null, null));
        verify(licenseeClassRepository);
    }

    @Test
    public void testDetailLicenseeClassExists() {
        expect(licenseeClassRepository.detailLicenseeClassExists(1)).andReturn(true).once();
        replay(licenseeClassRepository);
        assertTrue(licenseeClassService.detailLicenseeClassExists(1));
        verify(licenseeClassRepository);
    }
}
