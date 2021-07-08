package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.repository.api.IUdmActionReasonRepository;
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
 * Verifies {@link UdmActionReasonRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 07/08/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@Transactional
public class UdmActionReasonRepositoryIntegrationTest {

    @Autowired
    private IUdmActionReasonRepository udmActionReasonRepository;

    @Test
    public void testFindAll() {
        List<UdmActionReason> expectedActionReasons = Arrays.asList(
            new UdmActionReason("e27a95b4-2a3a-456c-a39e-5bac4ce2503e",
                "Accepting reported work based on user location"),
            new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content"),
            new UdmActionReason("97fd8093-7f36-4a09-99f1-1bfe36a5c3f4", "Arbitrary RFA search result order"),
            new UdmActionReason("dad6911c-3695-419c-96c0-adf8792699e3", "Assigned a Wr Wrk Inst"),
            new UdmActionReason("8842c02a-a867-42b0-9533-681122e7478f", "Assigned Rights"),
            new UdmActionReason("04b99a98-56d3-4f59-bfcb-2c72d18ebbbc", "Created new work"),
            new UdmActionReason("be6ece83-4739-479d-b468-5dcea822e1f8", "Incorrect/inappropriate Det Lic Class"),
            new UdmActionReason("d7258aa1-801c-408f-8fff-685e5519a8db", "Metadata does not match Wr Wrk Inst"),
            new UdmActionReason("5c4e5450-f566-4b88-bf40-cfc9cec7e69b", "Misc - See Comments"),
            new UdmActionReason("4b6b3058-7608-433a-8041-3e5ad2601735", "Multiple works found"),
            new UdmActionReason("afbe0339-c246-44a9-94ef-499a089aa939",
                "Replaced deleted work ID with surviving work ID"),
            new UdmActionReason("89072d4d-ff4c-485b-995f-02f6dea61d61", "RH cannot be determined"),
            new UdmActionReason("bf3e9fdb-0573-4c4a-8194-741d433c6b56", "RH not participating"),
            new UdmActionReason("f6ca01a9-b658-4258-86e6-d5ccd21fbe2c", "RH or work type on RRO’s Excluded list"),
            new UdmActionReason("bcb471ca-47c3-42a9-a4bc-18dc40e22c0f", "Survey length incorrectly documented"),
            new UdmActionReason("dd47a747-d6f6-4b53-af85-a4eb456d9b2e", "Survey location suggests wrong work chosen"),
            new UdmActionReason("61c3e557-6f26-491c-879c-b03d91927f73", "Title cannot be determined"),
            new UdmActionReason("2993db0e-8ac9-46af-a4b0-10deefe4af7d", "Unidentifiable publisher"),
            new UdmActionReason("401560c0-4e38-4515-b515-d32c6d3dc4f7", "Used existing format"),
            new UdmActionReason("ccbd22af-32bf-4162-8145-d49eae14c800", "User is not reporting a Mkt Rsch Rpt")
        );
        List<UdmActionReason> actualActionReasons = udmActionReasonRepository.findAll();
        assertEquals(expectedActionReasons.size(), actualActionReasons.size());
        IntStream.range(0, expectedActionReasons.size())
            .forEach(index -> assertEquals(expectedActionReasons.get(index), actualActionReasons.get(index)));
    }
}
