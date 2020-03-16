package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.repository.api.ILicenseeClassRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Integration test for {@link ILicenseeClassRepository}.
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
public class LicenseeClassRepositoryIntegrationTest {

    @Autowired
    private ILicenseeClassRepository licenseeClassRepository;

    @Test
    public void testDetailLicenseeClassIdExists() {
        assertTrue(licenseeClassRepository.detailLicenseeClassExists("EXU4", "Arts & Humanities"));
    }

    @Test
    public void testDetailLicenseeClassIdExistsWithMixedCase() {
        assertTrue(licenseeClassRepository.detailLicenseeClassExists("exU4", "ARTS & HUMANITIES"));
    }

    @Test
    public void testDetailLicenseeClassIdExistsRecordNotExist() {
        assertFalse(licenseeClassRepository.detailLicenseeClassExists("Null", "Arts & Humanities"));
    }

    @Test
    public void testFindAggregateLicenseeClasses() throws IOException {
        List<AggregateLicenseeClass> expectedClasses = loadExpectedClasses("expected_aggregate_licensee_classes.json");
        List<AggregateLicenseeClass> actualClasses = licenseeClassRepository.findAggregateLicenseeClasses();
        assertEquals(expectedClasses.size(), actualClasses.size());
        IntStream.range(0, expectedClasses.size())
            .forEach(i -> verifyAggregateLicenseeClass(expectedClasses.get(i), actualClasses.get(i)));
    }

    private List<AggregateLicenseeClass> loadExpectedClasses(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), "json/aacl/" + fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<AggregateLicenseeClass>>() {});
    }

    private void verifyAggregateLicenseeClass(AggregateLicenseeClass expected, AggregateLicenseeClass actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getEnrollmentProfile(), actual.getEnrollmentProfile());
        assertEquals(expected.getDiscipline(), actual.getDiscipline());
    }
}
