package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.domain.GrantPriority;
import com.copyright.rup.dist.common.repository.api.IGrantPriorityRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
public class GrantPriorityRepositoryIntegrationTest {

    private static final String INTERNAL_DISTRIBUTION = "INTERNAL";
    private static final String ACADEMIC_MARKET = "ACADEMIC";

    @Autowired
    private IGrantPriorityRepository grantPriorityRepository;

    private String productFamily;

    @Test
    public void testFindByProductFamilyFas() {
        this.productFamily = "FAS";
        assertFindByProductFamily();
    }

    @Test
    public void testFindByProductFamilyFas2() {
        this.productFamily = "FAS2";
        assertFindByProductFamily();
    }

    @Test
    public void testFindByProductFamilyNts() {
        this.productFamily = "NTS";
        assertFindByProductFamily();
    }

    private void assertFindByProductFamily() {
        List<GrantPriority> actualGrantPriorities = grantPriorityRepository.findByProductFamily(productFamily);
        assertNotNull(actualGrantPriorities);
        assertEquals(7, CollectionUtils.size(actualGrantPriorities));
        List<GrantPriority> expectedGrantPriorities = Arrays.asList(
            buildGrantPriority("CORPORATE", "EXTERNAL", "NGT_PHOTOCOPY", 0),
            buildGrantPriority("CORPORATE", INTERNAL_DISTRIBUTION, "PRINT", 1),
            buildGrantPriority("CORPORATE", INTERNAL_DISTRIBUTION, "DIGITAL", 2),
            buildGrantPriority(ACADEMIC_MARKET, INTERNAL_DISTRIBUTION, "PRINT", 3),
            buildGrantPriority(ACADEMIC_MARKET, INTERNAL_DISTRIBUTION, "DIGITAL", 4),
            buildGrantPriority(ACADEMIC_MARKET, "EXTERNAL", "NGT_PRINT_COURSE_MATERIALS", 5),
            buildGrantPriority(ACADEMIC_MARKET, "EXTERNAL", "NGT_ELECTRONIC_COURSE_MATERIALS", 6)
        );
        IntStream.range(0, expectedGrantPriorities.size())
            .forEach(i -> assertGrantPriority(expectedGrantPriorities.get(i), actualGrantPriorities.get(i)));
    }

    private GrantPriority buildGrantPriority(String market, String distribution, String typeOfUse,
                                             Integer priority) {
        GrantPriority grantPriority = new GrantPriority();
        grantPriority.setProductFamily(productFamily);
        grantPriority.setMarket(market);
        grantPriority.setDistribution(distribution);
        grantPriority.setTypeOfUse(typeOfUse);
        grantPriority.setPriority(priority);
        return grantPriority;
    }

    private void assertGrantPriority(GrantPriority expected, GrantPriority actual) {
        assertNotNull(actual.getId());
        assertEquals(expected.getProductFamily(), actual.getProductFamily());
        assertEquals(expected.getMarket(), actual.getMarket());
        assertEquals(expected.getDistribution(), actual.getDistribution());
        assertEquals(expected.getTypeOfUse(), actual.getTypeOfUse());
        assertEquals(expected.getPriority(), actual.getPriority());
    }
}
