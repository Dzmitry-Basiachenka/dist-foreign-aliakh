package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.repository.api.IUdmTypeOfUseRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Verifies {@link UdmTypeOfUseRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/20/2021
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@Transactional
public class UdmTypeOfUseRepositoryIntegrationTest {

    private static final String DIGITAL = "DIGITAL";
    private static final String PRINT = "PRINT";
    private static final String COPY_FOR_MYSELF = "COPY_FOR_MYSELF";
    private static final String EMAIL_COPY = "EMAIL_COPY";
    private static final String PRINT_COPIES = "PRINT_COPIES";
    private static final String DISPLAY_IN_POWERPOINT = "DISPLAY_IN_POWERPOINT";
    private static final String DISTRIBUTE_IN_POWERPOINT = "DISTRIBUTE_IN_POWERPOINT";
    private static final String FAX_PHOTOCOPIES = "FAX_PHOTOCOPIES";
    private static final String PHOTOCOPY_SHARING_OTHER = "PHOTOCOPY_SHARING_OTHER";
    private static final String SHARE_PHOTOCOPIES = "SHARE_PHOTOCOPIES";
    private static final String SHARE_SINGLE_ELECTRONIC_COPY = "SHARE_SINGLE_ELECTRONIC_COPY";
    private static final String STORE_COPY = "STORE_COPY";
    private static final String SUBMIT_ELECTRONIC_COPY = "SUBMIT_ELECTRONIC_COPY";
    private static final String SUBMIT_PHOTOCOPY = "SUBMIT_PHOTOCOPY";
    private static final String DIGITAL_SHARING_OTHER = "DIGITAL_SHARING_OTHER";

    @Autowired
    private IUdmTypeOfUseRepository udmTypeOfUseRepository;

    @Test
    public void testFindAllUdmTous() {
        List<String> expectedUdmTous = List.of(COPY_FOR_MYSELF, DIGITAL_SHARING_OTHER, DISPLAY_IN_POWERPOINT,
            DISTRIBUTE_IN_POWERPOINT, EMAIL_COPY, FAX_PHOTOCOPIES, PHOTOCOPY_SHARING_OTHER, PRINT_COPIES,
            SHARE_PHOTOCOPIES, SHARE_SINGLE_ELECTRONIC_COPY, STORE_COPY, SUBMIT_ELECTRONIC_COPY, SUBMIT_PHOTOCOPY);
        List<String> actualUdmTous = udmTypeOfUseRepository.findAllUdmTous();
        assertEquals(expectedUdmTous, actualUdmTous);
    }

    @Test
    public void testFindUdmTouToRmsTouMapping() {
        Map<String, String> result = udmTypeOfUseRepository.findUdmTouToRmsTouMap();
        assertEquals(13, result.size());
        assertEquals(DIGITAL, result.get(COPY_FOR_MYSELF));
        assertEquals(DIGITAL, result.get(EMAIL_COPY));
        assertEquals(PRINT, result.get(PRINT_COPIES));
        assertEquals(DIGITAL, result.get(DISPLAY_IN_POWERPOINT));
        assertEquals(DIGITAL, result.get(DISTRIBUTE_IN_POWERPOINT));
        assertEquals(PRINT, result.get(FAX_PHOTOCOPIES));
        assertEquals(PRINT, result.get(PHOTOCOPY_SHARING_OTHER));
        assertEquals(PRINT, result.get(SHARE_PHOTOCOPIES));
        assertEquals(DIGITAL, result.get(SHARE_SINGLE_ELECTRONIC_COPY));
        assertEquals(DIGITAL, result.get(STORE_COPY));
        assertEquals(DIGITAL, result.get(SUBMIT_ELECTRONIC_COPY));
        assertEquals(PRINT, result.get(SUBMIT_PHOTOCOPY));
        assertEquals(DIGITAL, result.get(DIGITAL_SHARING_OTHER));
    }
}
