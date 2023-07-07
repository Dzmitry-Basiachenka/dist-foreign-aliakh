package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.camel.IConsumer;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.LdmtDetail;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Consumer to handle list of {@link LdmtDetail}s from Oracle.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Aliaksandr Liakh
 */
@Component("df.service.ldmtDetailsConsumer")
public class LdmtDetailsConsumer implements IConsumer<List<LdmtDetail>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IAclFundPoolService aclFundPoolService;

    @Override
    @Profiled(tag = "LdmtDetailConsumer.consume")
    public void consume(List<LdmtDetail> ldmtDetails) {
        LOGGER.info("Consume LDMT details from Oracle. Started. DetailsCount={}", LogUtils.size(ldmtDetails));
        aclFundPoolService.insertAclFundPoolDetails(ldmtDetails
            .stream()
            .map(ldmtDetail -> {
                AclFundPoolDetail fundPoolDetail = new AclFundPoolDetail();
                fundPoolDetail.setId(RupPersistUtils.generateUuid());
                fundPoolDetail.getDetailLicenseeClass().setId(ldmtDetail.getDetailLicenseeClassId());
                fundPoolDetail.setLicenseType(ldmtDetail.getLicenseType());
                fundPoolDetail.setTypeOfUse(ldmtDetail.getTypeOfUse());
                fundPoolDetail.setGrossAmount(ldmtDetail.getGrossAmount().setScale(2, RoundingMode.HALF_UP));
                fundPoolDetail.setNetAmount(ldmtDetail.getNetAmount().setScale(2, RoundingMode.HALF_UP));
                fundPoolDetail.setLdmtFlag(true);
                return fundPoolDetail;
            })
            .collect(Collectors.toList()));
        LOGGER.info("Consume LDMT details from Oracle. Finished. DetailsCount={}", LogUtils.size(ldmtDetails));
    }
}
