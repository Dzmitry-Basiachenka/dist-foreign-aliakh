package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.repository.api.ILicenseeClassRepository;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements {@link ILicenseeClassService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/27/2020
 *
 * @author Anton Azarenka
 */
@Service
public class LicenseeClassService implements ILicenseeClassService {

    @Autowired
    private ILicenseeClassRepository licenseeClassRepository;

    @Override
    public boolean detailLicenceClassExists(String enrollmentProfile, String discipline) {
        return licenseeClassRepository.detailLicenseeClassExists(enrollmentProfile, discipline);
    }

    @Override
    public List<AggregateLicenseeClass> getAggregateLicenseeClasses() {
        return licenseeClassRepository.findAggregateLicenseeClasses();
    }
}
