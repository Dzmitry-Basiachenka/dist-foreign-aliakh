package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.common.domain.Rightsholder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Holder of test RMM rightsholders.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/14/2019
 *
 * @author Aliaksandr Liakh
 */
final class TestPrmRightsholdersHolder {

    private static final List<Rightsholder> RIGHTSHOLDERS = List.of(
        buildRightsholder("c929c312-bd36-4469-8989-61b824ecefd6", 1000010022L, "Yale University Press"),
        buildRightsholder("671b8b20-7335-4938-988e-1da803efa6e3", 1000010029L, "Georg Thieme Verlag KG"),
        buildRightsholder("714b1321-210c-4982-a93b-74fc405ef211", 7000864232L,
            "APCHQ (Association provinciale des constructeurs d?habitation du Quebec)"),
        buildRightsholder("984f201d-e91e-4680-a8db-b2dc70e46bcb", 1000032105L, "Springer-Verlag Berlin/Heidelberg"),
        buildRightsholder("34ac2a6b-73fd-49e8-b97b-0c1b7031b5bf", 2000017000L,
            "CLA, The Copyright Licensing Agency Ltd."),
        buildRightsholder("e9a53f2c-6e7a-4b56-885d-fb6e49a770e0", 1000001233L,
            "Multi-Science Publishing Company Limited"),
        buildRightsholder("005f49ed-db6f-44d7-a9fb-e705b3c8c1d9", 2000184640L,
            "Nova Southeastern Univ, Wayne Huizenga Graduate Sc"),
        buildRightsholder("021a997e-1c64-491c-9cd8-5344a3b485f5", 7000405588L, "Fundacion Historica Tavera [T]"),
        buildRightsholder("953d5267-b611-48c8-9d67-3fd27012c2ed", 1000015778L, "Nature Publishing Group (Permissions)"),
        buildRightsholder("21075c02-54d7-43f7-b3e8-1858b1006d66", 1000003066L, "Hachette Book Group, Inc"));

    private static final Map<String, Rightsholder> RH_IDS_TO_RIGHTSHOLDERS = RIGHTSHOLDERS.stream()
        .collect(Collectors.toMap(Rightsholder::getId, rightsholder -> rightsholder));

    /**
     * Constructor.
     */
    private TestPrmRightsholdersHolder() {
    }

    static Rightsholder getByRhId(String rhId) {
        return RH_IDS_TO_RIGHTSHOLDERS.get(rhId);
    }

    private static Rightsholder buildRightsholder(String id, Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(id);
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
