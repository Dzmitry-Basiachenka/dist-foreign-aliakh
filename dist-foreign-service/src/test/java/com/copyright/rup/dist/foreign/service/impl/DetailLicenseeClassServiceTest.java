package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.repository.api.IDetailLicenseeClassRepository;
import com.copyright.rup.dist.foreign.service.api.IDetailLicenseeClassService;
import com.copyright.rup.dist.foreign.service.impl.aacl.DetailLicenseeClassService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Validates {@link DetailLicenseeClassService}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 01/27/2020
 *
 * @author Anton Azarenka
 */
public class DetailLicenseeClassServiceTest {

    private static final String ENROLLMENT_PROFILE = "EXGP";
    private static final String DISCIPLINE = "Physical Sciences & Mathematics";
    private IDetailLicenseeClassService detailLicenseeClassService;
    private IDetailLicenseeClassRepository detailLicenseeClassRepository;

    @Before
    public void setUp() {
        detailLicenseeClassService = new DetailLicenseeClassService();
        detailLicenseeClassRepository = createMock(IDetailLicenseeClassRepository.class);
        Whitebox.setInternalState(detailLicenseeClassService, "detailLicenseeClassRepository",
            detailLicenseeClassRepository);
    }

    @Test
    public void testIsDetailLicenceClassExist() {
        expect(detailLicenseeClassRepository.findLicenseeClassIdByDisciplineAndEnrollmentProfile(ENROLLMENT_PROFILE,
            DISCIPLINE)).andReturn("206").once();
        replay(detailLicenseeClassRepository);
        assertTrue(detailLicenseeClassService.isDetailLicenceClassExist(ENROLLMENT_PROFILE, DISCIPLINE));
        verify(detailLicenseeClassRepository);
    }

    @Test
    public void testIsDetailLicenceClassExistWithDisciplineNull() {
        expect(detailLicenseeClassRepository.findLicenseeClassIdByDisciplineAndEnrollmentProfile(ENROLLMENT_PROFILE,
            null)).andReturn(null).once();
        replay(detailLicenseeClassRepository);
        assertFalse(detailLicenseeClassService.isDetailLicenceClassExist(ENROLLMENT_PROFILE, null));
        verify(detailLicenseeClassRepository);
    }

    @Test
    public void testIsDetailLicenceClassExistWithEnrollmentProfileNull() {
        expect(detailLicenseeClassRepository.findLicenseeClassIdByDisciplineAndEnrollmentProfile(null,
            DISCIPLINE)).andReturn(null).once();
        replay(detailLicenseeClassRepository);
        assertFalse(detailLicenseeClassService.isDetailLicenceClassExist(null, DISCIPLINE));
        verify(detailLicenseeClassRepository);
    }

    @Test
    public void testIsDetailLicenceClassExistWithEnrollmentProfileAndDisciplineNull() {
        expect(detailLicenseeClassRepository.findLicenseeClassIdByDisciplineAndEnrollmentProfile(null,
            null)).andReturn(null).once();
        replay(detailLicenseeClassRepository);
        assertFalse(detailLicenseeClassService.isDetailLicenceClassExist(null, null));
        verify(detailLicenseeClassRepository);
    }
}
