package com.copyright.rup.dist.foreign.ui.common.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.UsageBatch;
import com.copyright.rup.dist.foreign.UsageDetail;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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
        List<Rightsholder> rros = getRROs();
        result.add(buildUsageBatch(rros.get(0), LocalDate.of(2016, 11, 8)));
        result.add(buildUsageBatch(rros.get(0), LocalDate.of(2016, 5, 7)));
        result.add(buildUsageBatch(rros.get(1), LocalDate.of(2015, 5, 20)));
        result.add(buildUsageBatch(rros.get(2), LocalDate.of(2016, 1, 3)));
        return result;
    }

    /**
     * @return RROs.
     */
    public static List<Rightsholder> getRROs() {
        List<Rightsholder> result = Lists.newArrayList();
        result.add(buildRightsholder(1000017000L, "Copyright Licensing Agency"));
        result.add(buildRightsholder(7000017000L, "Copyright Agency Limited"));
        result.add(buildRightsholder(2000017000L, "Access Copyright"));
        return result;
    }

    /**
     * @return usage details DTO.
     */
    public static List<UsageDetailDto> getUsageDetailDTOs() {
        List<UsageDetailDto> result = Lists.newArrayList();
        List<UsageBatch> usageBatches = getUsageBatches();
        Rightsholder rightsholder1 = buildRightsholder(2000017002L, "VG Wort, Verwertungsgesellschaft WORT");
        Rightsholder rightsholder2 =
            buildRightsholder(7000454170L, "Association of the Scientifica Medical Societies in Germany [T]");
        Rightsholder rightsholder3 = buildRightsholder(1000000322L, "American College of Physicians - Journals");
        UsageBatch batch1 = usageBatches.get(0);
        UsageBatch batch2 = usageBatches.get(1);
        UsageBatch batch3 = usageBatches.get(2);
        result.add(
            new UsageDetailDto(batch1, buildUsageDetail(156426671L, "Transporte mundial", rightsholder1, "56.21")));
        result.add(new UsageDetailDto(batch1, buildUsageDetail(158956997L, "Brioude", rightsholder1, "12.71")));
        result.add(new UsageDetailDto(batch1, buildUsageDetail(295042091L,
            "Standard Test Methods for Evaluating Properties of Wood-Base Fiber and Particle Panel Materials",
            rightsholder1, "23.01")));
        result.add(new UsageDetailDto(batch1, buildUsageDetail(158956997L, "Brioude", rightsholder1, "1112.71")));
        result.add(new UsageDetailDto(batch1, buildUsageDetail(283491771L, "East moves West", rightsholder2, "52.71")));
        result.add(new UsageDetailDto(batch2,
            buildUsageDetail(122516585L, "Concepts of information retrieval", rightsholder2, "654.21")));
        result
            .add(new UsageDetailDto(batch3, buildUsageDetail(122824345L, "ACP journal club", rightsholder3, "45.12")));
        result.add(new UsageDetailDto(batch3,
            buildUsageDetail(122839717L, "Annals of internal medicine", rightsholder3, "9.65")));
        return result;
    }

    private static UsageBatch buildUsageBatch(Rightsholder rro, LocalDate paymentDate) {
        UsageBatch result = new UsageBatch();
        result.setId(UUID.randomUUID().toString());
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

    private static UsageDetail buildUsageDetail(Long wrWrkInst, String workTitle, Rightsholder rightsholder,
                                                String grossAmount) {
        UsageDetail result = new UsageDetail();
        result.setId(UUID.randomUUID().toString());
        result.setWrWrkInst(wrWrkInst);
        result.setWorkTitle(workTitle);
        result.setRightsholder(rightsholder);
        result.setGrossAmount(new BigDecimal(grossAmount));
        return result;
    }
}
