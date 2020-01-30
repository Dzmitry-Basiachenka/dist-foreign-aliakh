package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.repository.api.IDetailLicenseeClassRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test for {@link IDetailLicenseeClassRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@Transactional
public class DetailLicenseeClassRepositoryIntegrationTest {

    @Autowired
    private IDetailLicenseeClassRepository detailLicenseeClassRepository;

    @Test
    public void testFindClassIdByDisciplineAndEnrollmentProfile() {
        assertEquals("173", detailLicenseeClassRepository.findLicenseeClassIdByDisciplineAndEnrollmentProfile("EXU4",
            "Arts & Humanities"));
    }

    @Test
    public void testFindClassIdByMixedCaseDisciplineAndEnrollmentProfile() {
        assertEquals("173", detailLicenseeClassRepository.findLicenseeClassIdByDisciplineAndEnrollmentProfile("exU4",
            "ARTS & HUMANITIES"));
    }

    @Test
    public void testFindClassIdRecordNotExist() {
        assertNull(detailLicenseeClassRepository.findLicenseeClassIdByDisciplineAndEnrollmentProfile("Null",
            "Arts & Humanities"));
    }
}
