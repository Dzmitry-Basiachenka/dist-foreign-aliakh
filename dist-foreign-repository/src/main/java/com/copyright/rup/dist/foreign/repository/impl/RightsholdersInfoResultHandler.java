package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;

import org.apache.commons.lang3.tuple.Triple;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Result handler for building rightsholder information based on {@link ResultContext}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/16/18
 *
 * @author Ihar Suvorau
 */
class RightsholdersInfoResultHandler implements ResultHandler {

    private final Map<Long, Triple<String, Boolean, Long>> rhToIdParticipatingStatusAndPayee = new HashMap<>();

    @Override
    public void handleResult(ResultContext context) {
        Usage usage = (Usage) context.getResultObject();
        Rightsholder rightsholder = usage.getRightsholder();
        rhToIdParticipatingStatusAndPayee.put(rightsholder.getAccountNumber(),
            Triple.of(rightsholder.getId(), usage.isRhParticipating(), usage.getPayee().getAccountNumber()));
    }

    /**
     * @return map with rh information where key is rightsholder account number, value is {@link Triple} of
     * rightsholder id, participating status and payee account number.
     */
    Map<Long, Triple<String, Boolean, Long>> getRhToIdParticipatingStatusAndPayee() {
        return rhToIdParticipatingStatusAndPayee;
    }
}
