package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.repository.api.ILicenseeClassRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource(properties = {"test.liquibase.changelog=licensee-class-repository-test-data-init.groovy"})
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
    public void testFindAggregateLicenseeClassesByProductFamily() throws IOException {
        List<AggregateLicenseeClass> expectedClasses =
            loadExpectedAggregateClasses("expected_aggregate_licensee_classes.json");
        List<AggregateLicenseeClass> actualClasses =
            licenseeClassRepository.findAggregateLicenseeClassesByProductFamily("AACL");
        assertEquals(expectedClasses.size(), actualClasses.size());
        IntStream.range(0, expectedClasses.size())
            .forEach(i -> verifyAggregateLicenseeClass(expectedClasses.get(i), actualClasses.get(i)));
    }

    @Test
    public void testFindDetailLicenseeClassesByProductFamily() throws IOException {
        List<DetailLicenseeClass> expectedDetailsMapping = loadExpectedClasses("expected_detail_licensee_classes.json");
        List<DetailLicenseeClass> actualDetailsMapping =
            licenseeClassRepository.findDetailLicenseeClassesByProductFamily("AACL");
        assertEquals(expectedDetailsMapping.size(), actualDetailsMapping.size());
        IntStream.range(0, expectedDetailsMapping.size())
            .forEach(i -> verifyLicenseeClass(expectedDetailsMapping.get(i), actualDetailsMapping.get(i)));
    }

    @Test
    public void testFindDetailLicenseeClassesByScenarioId() throws IOException {
        List<DetailLicenseeClass> expectedDetailsMapping =
            loadExpectedClasses("expected_detail_licensee_classes_by_scenario_id.json");
        List<DetailLicenseeClass> actualDetailsMapping =
            licenseeClassRepository.findDetailLicenseeClassesByScenarioId("66d10c81-705e-4996-89f4-11e1635c4c31");
        assertEquals(expectedDetailsMapping.size(), actualDetailsMapping.size());
        IntStream.range(0, expectedDetailsMapping.size())
            .forEach(i -> verifyLicenseeClass(expectedDetailsMapping.get(i), actualDetailsMapping.get(i)));
    }

    private List<AggregateLicenseeClass> loadExpectedAggregateClasses(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), "json/aacl/" + fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<List<AggregateLicenseeClass>>() {
        });
    }

    private List<DetailLicenseeClass> loadExpectedClasses(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), "json/aacl/" + fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<List<DetailLicenseeClass>>() {
        });
    }

    private void verifyLicenseeClass(DetailLicenseeClass expected, DetailLicenseeClass actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDiscipline(), actual.getDiscipline());
        assertEquals(expected.getEnrollmentProfile(), actual.getEnrollmentProfile());
        verifyAggregateLicenseeClass(expected.getAggregateLicenseeClass(), actual.getAggregateLicenseeClass());
    }

    private void verifyAggregateLicenseeClass(AggregateLicenseeClass expected, AggregateLicenseeClass actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getEnrollmentProfile(), actual.getEnrollmentProfile());
        assertEquals(expected.getDiscipline(), actual.getDiscipline());
    }
}
