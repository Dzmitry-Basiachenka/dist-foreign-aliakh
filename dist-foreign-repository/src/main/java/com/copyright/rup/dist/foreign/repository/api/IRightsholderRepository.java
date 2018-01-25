package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.ICommonRightsholderRepository;

import java.util.List;

/**
 * Represents interface of repository for rightsholders.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
public interface IRightsholderRepository extends ICommonRightsholderRepository {

    /**
     * @return list of RROs from all batches stored in DB.
     */
    List<Rightsholder> findRros();

    /**
     * Deletes {@link Rightsholder} from database table by account number.
     *
     * @param accountNumber account number of {@link Rightsholder}
     */
    void deleteByAccountNumber(Long accountNumber);

    /**
     * @return list of distinct {@link Rightsholder}s from all usages.
     */
    List<Rightsholder> findFromUsages();
}
