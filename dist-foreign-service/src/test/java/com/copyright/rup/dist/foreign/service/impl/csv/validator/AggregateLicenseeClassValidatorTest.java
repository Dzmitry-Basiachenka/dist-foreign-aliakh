package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;

import org.junit.Test;

import java.util.Collections;

/**
 * Validates {@link AggregateLicenseeClassValidator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
public class AggregateLicenseeClassValidatorTest {

    @Test
    public void testIsValidTrue() {
        FundPoolDetail detail = new FundPoolDetail();
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        detail.setAggregateLicenseeClass(aggregateLicenseeClass);
        aggregateLicenseeClass.setId(1);
        IAaclFundPoolRepository aaclFundPoolRepository = createMock(IAaclFundPoolRepository.class);
        expect(aaclFundPoolRepository.findAggregateLicenseeClassIds()).andReturn(Collections.singleton(1)).once();
        replay(aaclFundPoolRepository);
        assertTrue(new AggregateLicenseeClassValidator(aaclFundPoolRepository).isValid(detail));
        verify(aaclFundPoolRepository);
    }

    @Test
    public void testIsValidFalse() {
        FundPoolDetail detail = new FundPoolDetail();
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        detail.setAggregateLicenseeClass(aggregateLicenseeClass);
        aggregateLicenseeClass.setId(2);
        IAaclFundPoolRepository aaclFundPoolRepository = createMock(IAaclFundPoolRepository.class);
        expect(aaclFundPoolRepository.findAggregateLicenseeClassIds()).andReturn(Collections.singleton(1)).once();
        replay(aaclFundPoolRepository);
        assertFalse(new AggregateLicenseeClassValidator(aaclFundPoolRepository).isValid(detail));
        verify(aaclFundPoolRepository);
    }

    @Test
    public void testGetErrorMessage() {
        IAaclFundPoolRepository aaclFundPoolRepository = createMock(IAaclFundPoolRepository.class);
        assertEquals("Aggregate Licensee Class with such ID doesn't exist in the system",
            new AggregateLicenseeClassValidator(aaclFundPoolRepository).getErrorMessage());
    }
}
