package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.domain.Rightsholder;

import java.util.List;
import java.util.Set;

/**
 * Represents interface of repository for rightsholders.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 * @author Mikalai Bezmen
 */
public interface IRightsholderRepository {

    /**
     * @return list of RROs from all batches stored in DB.
     */
    List<Rightsholder> findRros();

    /**
     * Inserts {@link Rightsholder} into DB.
     *
     * @param rightsholder instance of {@link Rightsholder}
     */
    void insert(Rightsholder rightsholder);

    /**
     * Finds set of RRO and RH account numbers from usages, archived usages and usage batches tables.
     *
     * @return set of account numbers
     */
    Set<Long> findRightsholdersAccountNumbers();

    /**
     * Removes all rightsholders from df_rightsholder table.
     */
    void deleteAll();

    /**
     * Deletes {@link Rightsholder} from database table by account number.
     *
     * @param accountNumber acccount number of {@link Rightsholder}
     */
    void deleteRightsholderByAccountNumber(Long accountNumber);
}
