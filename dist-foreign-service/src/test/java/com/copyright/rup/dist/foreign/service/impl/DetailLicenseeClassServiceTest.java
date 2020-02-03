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
        expect(detailLicenseeClassRepository.isLicenseeClassIdExist(ENROLLMENT_PROFILE,
            DISCIPLINE)).andReturn(true).once();
        replay(detailLicenseeClassRepository);
        assertTrue(detailLicenseeClassService.detailLicenceClassIdExist(ENROLLMENT_PROFILE, DISCIPLINE));
        verify(detailLicenseeClassRepository);
    }

    @Test
    public void testIsDetailLicenceClassExistWithDisciplineNull() {
        expect(detailLicenseeClassRepository.isLicenseeClassIdExist(ENROLLMENT_PROFILE,
            null)).andReturn(false).once();
        replay(detailLicenseeClassRepository);
        assertFalse(detailLicenseeClassService.detailLicenceClassIdExist(ENROLLMENT_PROFILE, null));
        verify(detailLicenseeClassRepository);
    }

    @Test
    public void testIsDetailLicenceClassExistWithEnrollmentProfileNull() {
        expect(detailLicenseeClassRepository.isLicenseeClassIdExist(null,
            DISCIPLINE)).andReturn(false).once();
        replay(detailLicenseeClassRepository);
        assertFalse(detailLicenseeClassService.detailLicenceClassIdExist(null, DISCIPLINE));
        verify(detailLicenseeClassRepository);
    }

    @Test
    public void testIsDetailLicenceClassExistWithEnrollmentProfileAndDisciplineNull() {
        expect(detailLicenseeClassRepository.isLicenseeClassIdExist(null,
            null)).andReturn(false).once();
        replay(detailLicenseeClassRepository);
        assertFalse(detailLicenseeClassService.detailLicenceClassIdExist(null, null));
        verify(detailLicenseeClassRepository);
    }
}
