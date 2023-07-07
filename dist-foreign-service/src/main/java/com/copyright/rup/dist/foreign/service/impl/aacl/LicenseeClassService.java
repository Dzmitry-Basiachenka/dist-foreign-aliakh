package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.repository.api.ILicenseeClassRepository;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import io.micrometer.core.annotation.Timed;

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
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class LicenseeClassService implements ILicenseeClassService {

    @Autowired
    private ILicenseeClassRepository licenseeClassRepository;

    @Override
    public boolean aaclDetailLicenseeClassExists(String enrollmentProfile, String discipline) {
        return licenseeClassRepository.aaclDetailLicenseeClassExists(enrollmentProfile, discipline);
    }

    @Override
    public boolean aclDetailLicenseeClassExists(Integer detailLicenseeClassId) {
        return licenseeClassRepository.detailLicenseeClassExists(detailLicenseeClassId);
    }

    @Override
    public List<AggregateLicenseeClass> getAggregateLicenseeClasses(String productFamily) {
        return licenseeClassRepository.findAggregateLicenseeClassesByProductFamily(productFamily);
    }

    @Override
    public List<DetailLicenseeClass> getDetailLicenseeClasses(String productFamily) {
        return licenseeClassRepository.findDetailLicenseeClassesByProductFamily(productFamily);
    }

    @Override
    public List<DetailLicenseeClass> getDetailLicenseeClassesByScenarioId(String scenarioId) {
        return licenseeClassRepository.findDetailLicenseeClassesByScenarioId(scenarioId);
    }
}
