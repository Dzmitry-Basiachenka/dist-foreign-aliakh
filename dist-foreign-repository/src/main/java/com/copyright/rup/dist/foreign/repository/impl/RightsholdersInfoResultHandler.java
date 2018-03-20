package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.foreign.domain.Usage;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Result handler for mapping usages with rightsholder, participating status and payee account number on rightsholder
 * account number based on {@link ResultContext}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/16/18
 *
 * @author Ihar Suvorau
 */
class RightsholdersInfoResultHandler implements ResultHandler {

    private final Map<Long, Usage> rhToUsageMap = new HashMap<>();

    @Override
    public void handleResult(ResultContext context) {
        Usage usage = (Usage) context.getResultObject();
        rhToUsageMap.put(usage.getRightsholder().getAccountNumber(), usage);
    }

    /**
     * @return map where key is rightsholder account number, value is {@link Usage} with rightsholder, participating
     * status and payee account number.
     */
    Map<Long, Usage> getRhToUsageMap() {
        return rhToUsageMap;
    }
}
