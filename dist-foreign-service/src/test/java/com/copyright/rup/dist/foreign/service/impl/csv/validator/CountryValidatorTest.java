package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Country;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Verifies {@link CountryValidator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/16/21
 *
 * @author Anton Azarenka
 */
@RunWith(Parameterized.class)
public class CountryValidatorTest {

    private final String country;
    private final boolean expectedResult;
    private final Map<String, Country> expectedCountries = ImmutableMap.of(
        "BLR", createCountry("Belarus", "BLR"),
        "CA", createCountry("Canada", "CND"));

    private CountryValidator validator;

    public CountryValidatorTest(String country, boolean expectedResult) {
        this.country = country;
        this.expectedResult = expectedResult;
    }

    @Before
    public void setUp() {
        IPrmIntegrationService prmIntegrationService = createMock(IPrmIntegrationService.class);
        expect(prmIntegrationService.getCountries()).andReturn(expectedCountries).once();
        replay(prmIntegrationService);
        validator = new CountryValidator(prmIntegrationService);
        verify(prmIntegrationService);
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {"International", true},
            {"international", false},
            {"belarus", false},
            {"Belarus", true},
            {"Canada", true},
            {"BLR", false},
        });
    }

    @Test
    public void testIsValid() {
        assertEquals(expectedResult, validator.isValid(buildUdmUsage(country)));
        assertEquals("Survey country is not found in PRM or does not match ISO Country Name standard",
            validator.getErrorMessage());
    }

    private Country createCountry(String name, String isoCode) {
        Country newCountry = new Country();
        newCountry.setName(name);
        newCountry.setIsoCode(isoCode);
        return newCountry;
    }

    private UdmUsage buildUdmUsage(String surveyCountry) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setSurveyCountry(surveyCountry);
        return udmUsage;
    }
}
