package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.repository.api.IUdmIneligibleReasonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies {@link UdmIneligibleReasonRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 07/08/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@Transactional
public class UdmIneligibleReasonRepositoryIntegrationTest {

    @Autowired
    private IUdmIneligibleReasonRepository udmIneligibleReasonRepository;

    @Test
    public void testFindAll() {
        List<UdmIneligibleReason> expectedIneligibleReasons = Arrays.asList(
            new UdmIneligibleReason("b60a726a-39e8-4303-abe1-6816da05b858", "Invalid survey"),
            new UdmIneligibleReason("0d5a129c-0f8f-4e48-98b2-8b980cdb9333", "Misc - See Comments"),
            new UdmIneligibleReason("b040d59b-72c7-42fc-99d2-d406d5ea60f3", "Multiple works found"),
            new UdmIneligibleReason("18fbee56-2f5c-450a-999e-54903c0bfb23", "No Reported Use"),
            new UdmIneligibleReason("fd2f2dea-4018-48ee-a630-b8dfedbe857b", "Public Domain"),
            new UdmIneligibleReason("53f8661b-bf27-47a4-b30c-7666c0df02c5", "RH cannot be determined"),
            new UdmIneligibleReason("9a3adbf4-9fdb-41ba-9701-97de34a120c4", "RH Denied/Refused/CRD/Lost"),
            new UdmIneligibleReason("b901cf15-e836-4b08-9732-191ee4bba14a", "Systematic Copying"),
            new UdmIneligibleReason("659f48e1-ef67-4d12-b568-436efdf4ec70", "Title cannot be determined"),
            new UdmIneligibleReason("cf1b711d-8c57-407c-b178-a8a2411c87e5", "Unauthorized use"),
            new UdmIneligibleReason("e837be16-43ad-4241-b0df-44ecceae2b46", "User-owned content"),
            new UdmIneligibleReason("a4df53dd-26d9-4a0e-956c-e95543707674", "Work not found")
        );
        List<UdmIneligibleReason> actualIneligibleReasons = udmIneligibleReasonRepository.findAll();
        assertEquals(expectedIneligibleReasons.size(), actualIneligibleReasons.size());
        IntStream.range(0, expectedIneligibleReasons.size())
            .forEach(index -> assertEquals(expectedIneligibleReasons.get(index), actualIneligibleReasons.get(index)));
    }
}
