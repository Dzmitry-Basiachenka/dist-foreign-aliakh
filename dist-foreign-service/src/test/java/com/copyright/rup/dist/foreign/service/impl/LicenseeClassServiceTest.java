package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.repository.api.ILicenseeClassRepository;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.impl.aacl.LicenseeClassService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

    private ILicenseeClassService licenseeClassService;
    private ILicenseeClassRepository licenseeClassRepository;

    @Before
    public void setUp() {
        licenseeClassService = new LicenseeClassService();
        licenseeClassRepository = createMock(ILicenseeClassRepository.class);
        Whitebox.setInternalState(licenseeClassService, "licenseeClassRepository", licenseeClassRepository);
    }

    @Test
    public void testIsDetailLicenceClassExist() {
        expect(licenseeClassRepository.detailLicenseeClassExists(ENROLLMENT_PROFILE, DISCIPLINE))
            .andReturn(true).once();
        replay(licenseeClassRepository);
        assertTrue(licenseeClassService.detailLicenceClassExists(ENROLLMENT_PROFILE, DISCIPLINE));
        verify(licenseeClassRepository);
    }

    @Test
    public void testIsDetailLicenceClassExistWithDisciplineNull() {
        expect(licenseeClassRepository.detailLicenseeClassExists(ENROLLMENT_PROFILE, null)).andReturn(false).once();
        replay(licenseeClassRepository);
        assertFalse(licenseeClassService.detailLicenceClassExists(ENROLLMENT_PROFILE, null));
        verify(licenseeClassRepository);
    }

    @Test
    public void testIsDetailLicenceClassExistWithEnrollmentProfileNull() {
        expect(licenseeClassRepository.detailLicenseeClassExists(null, DISCIPLINE)).andReturn(false).once();
        replay(licenseeClassRepository);
        assertFalse(licenseeClassService.detailLicenceClassExists(null, DISCIPLINE));
        verify(licenseeClassRepository);
    }

    @Test
    public void testIsDetailLicenceClassExistWithEnrollmentProfileAndDisciplineNull() {
        expect(licenseeClassRepository.detailLicenseeClassExists(null, null)).andReturn(false).once();
        replay(licenseeClassRepository);
        assertFalse(licenseeClassService.detailLicenceClassExists(null, null));
        verify(licenseeClassRepository);
    }
}
