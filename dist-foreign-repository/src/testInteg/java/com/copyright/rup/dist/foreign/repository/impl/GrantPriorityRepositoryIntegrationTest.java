package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.GrantPriority;
import com.copyright.rup.dist.common.repository.api.IGrantPriorityRepository;

import com.copyright.rup.dist.common.test.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies {@link com.copyright.rup.dist.common.repository.impl.GrantPriorityRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/23/2017
 *
 * @author Pavel Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
public class GrantPriorityRepositoryIntegrationTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private IGrantPriorityRepository grantPriorityRepository;

    @Test
    public void testFindByProductFamilyAacl() {
        testFindByProductFamily("AACL", "json/grant_priorities_aacl.json");
    }

    @Test
    public void testFindByProductFamilyAclUdmUsage() {
        testFindByProductFamily("ACL_UDM_USAGE", "json/grant_priorities_acl_udm_usage.json");
    }

    @Test
    public void testFindByProductFamilyAclUdmValue() {
        testFindByProductFamily("ACL_UDM_VALUE", "json/grant_priorities_acl_udm_value.json");
    }

    @Test
    public void testFindByProductFamilyFas() {
        testFindByProductFamily("FAS", "json/grant_priorities_fas.json");
    }

    @Test
    public void testFindByProductFamilyFas2() {
        testFindByProductFamily("FAS2", "json/grant_priorities_fas2.json");
    }

    @Test
    public void testFindByProductFamilyNts() {
        testFindByProductFamily("NTS", "json/grant_priorities_nts.json");
    }

    @Test
    public void testFindByProductFamilySal() {
        testFindByProductFamily("SAL", "json/grant_priorities_sal.json");
    }

    private void testFindByProductFamily(String productFamily, String fileName) {
        List<GrantPriority> expectedGrantPriorities = loadExpectedGrantPriorities(fileName);
        List<GrantPriority> actualGrantPriorities = grantPriorityRepository.findByProductFamily(productFamily);
        IntStream.range(0, expectedGrantPriorities.size())
            .forEach(i -> assertGrantPriority(expectedGrantPriorities.get(i), actualGrantPriorities.get(i)));
    }

    private void assertGrantPriority(GrantPriority expected, GrantPriority actual) {
        assertEquals(expected.getProductFamily(), actual.getProductFamily());
        assertEquals(expected.getGrantProductFamily(), actual.getGrantProductFamily());
        assertEquals(expected.getTypeOfUse(), actual.getTypeOfUse());
        assertEquals(expected.getLicenseType(), actual.getLicenseType());
        assertEquals(expected.getPriority(), actual.getPriority());
    }

    private List<GrantPriority> loadExpectedGrantPriorities(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            return OBJECT_MAPPER.readValue(content, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
