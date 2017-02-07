package com.copyright.rup.dist.foreign.ui.common.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * FakeDataGenerator class.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/18/17
 *
 * @author Aliaksei Pchelnikau
 */
public final class FakeDataGenerator {

    private FakeDataGenerator() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * @return usage batches.
     */
    public static List<UsageBatch> getUsageBatches() {
        List<UsageBatch> result = Lists.newArrayList();
        List<Rightsholder> rros = getRros();
        result.add(buildUsageBatch(rros.get(0), LocalDate.of(2016, 11, 8), "1"));
        result.add(buildUsageBatch(rros.get(0), LocalDate.of(2016, 5, 7), "2"));
        result.add(buildUsageBatch(rros.get(1), LocalDate.of(2015, 5, 20), "3"));
        result.add(buildUsageBatch(rros.get(2), LocalDate.of(2016, 1, 3), "4"));
        return result;
    }

    /**
     * @return RROs.
     */
    public static List<Rightsholder> getRros() {
        List<Rightsholder> result = Lists.newArrayList();
        result.add(buildRightsholder(1000017000L, "Copyright Licensing Agency"));
        result.add(buildRightsholder(7000017000L, "Copyright Agency Limited"));
        result.add(buildRightsholder(2000017000L, "Access Copyright"));
        return result;
    }

    private static UsageBatch buildUsageBatch(Rightsholder rro, LocalDate paymentDate, String id) {
        UsageBatch result = new UsageBatch();
        result.setId(id);
        result.setName(rro.getAccountNumber() + " " + DateTimeFormatter.ofPattern("MM/dd/yyyy").format(paymentDate));
        result.setRro(rro);
        result.setPaymentDate(paymentDate);
        result.setFiscalYear(paymentDate.getYear());
        return result;
    }

    private static Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder result = new Rightsholder();
        result.setAccountNumber(accountNumber);
        result.setName(name);
        return result;
    }
}
