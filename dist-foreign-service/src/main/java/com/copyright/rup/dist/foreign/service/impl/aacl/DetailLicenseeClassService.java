package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.dist.foreign.repository.api.IDetailLicenseeClassRepository;
import com.copyright.rup.dist.foreign.service.api.IDetailLicenseeClassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements {@link IDetailLicenseeClassService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/27/2020
 *
 * @author Anton Azarenka
 */
@Service
public class DetailLicenseeClassService implements IDetailLicenseeClassService {

    @Autowired
    private IDetailLicenseeClassRepository detailLicenseeClassRepository;

    @Override
    public boolean detailLicenceClassIdExist(String enrollmentProfile, String discipline) {
        return detailLicenseeClassRepository.isLicenseeClassIdExist(enrollmentProfile, discipline);
    }
}
