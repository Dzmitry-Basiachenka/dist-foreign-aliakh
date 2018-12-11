package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.dist.foreign.domain.Usage;

import java.util.List;

/**
 * Implementation of {@link AbstractUsageChainProcessor} to delete {@link Usage}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public class DeleteUsageProcessor extends AbstractUsageChainProcessor {

    @Override
    public void process(List<Usage> items) {
        /*
            1. Delete incoming usages
         */
    }
}
