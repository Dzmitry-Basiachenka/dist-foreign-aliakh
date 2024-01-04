package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RightsholderTypeOfUsePair;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailPayeeService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IAclGrantDetailPayeeService}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/04/2024
 *
 * @author Aliaksandr Liakh
 */
@Service
public class AclGrantDetailPayeeService implements IAclGrantDetailPayeeService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IAclGrantDetailRepository aclGrantDetailRepository;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;

    @Transactional
    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void populatePayees(String grantSetId) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Populate payees in ACL grant set. Started. GrantSetId={}, UserName={}", grantSetId, userName);
        List<RightsholderTypeOfUsePair> rightsholderTypeOfUsePairs = rightsholderService.getByAclGrantSetId(grantSetId);
        Set<String> rightsholdersIds = rightsholderTypeOfUsePairs.stream()
            .map(pair -> pair.getRightsholder().getId())
            .collect(Collectors.toSet());
        Map<String, Map<String, Rightsholder>> rollUps = prmIntegrationService.getRollUps(rightsholdersIds);
        LOGGER.info("Populate payees in ACL grant set. Roll-ups read. GrantSetId={}, RollUpsCount={}",
            grantSetId, rollUps.size());
        Set<Long> payeeAccountNumbers = rightsholderTypeOfUsePairs.stream()
            .map(pair -> {
                Rightsholder rightsholder = pair.getRightsholder();
                String typeOfUse = pair.getTypeOfUse();
                String productFamily = FdaConstants.ACL_PRODUCT_FAMILY + typeOfUse;
                Rightsholder payee = PrmRollUpService.getPayee(rollUps, rightsholder, productFamily);
                Long payeeAccountNumber = payee.getAccountNumber();
                aclGrantDetailRepository.updatePayeeAccountNumber(grantSetId, rightsholder.getAccountNumber(),
                    typeOfUse, payeeAccountNumber, userName);
                return payeeAccountNumber;
            })
            .collect(Collectors.toSet());
        rightsholderService.updateRightsholders(payeeAccountNumbers);
        LOGGER.info("Populate payees for ACL grant set. Finished. GrantSetId={}", grantSetId);
    }

    @Transactional
    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void populatePayees(Collection<AclGrantDetailDto> grantDetailDtos) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Populate payees in ACL grant details. Started. GrantDetailCount={}, UserName={}",
            grantDetailDtos.size(), userName);
        Set<Long> rhAccountNumbers = grantDetailDtos.stream()
            .map(AclGrantDetail::getRhAccountNumber)
            .collect(Collectors.toSet());
        Map<Long, String> rhAccountNumbersToIds =
            rightsholderService.findRightsholderIdsByAccountNumbers(rhAccountNumbers);
        Set<String> rightsholdersIds = Set.copyOf(rhAccountNumbersToIds.values());
        Map<String, Map<String, Rightsholder>> rollUps = prmIntegrationService.getRollUps(rightsholdersIds);
        LOGGER.info("Populate payees in ACL grant details. Roll-ups read. GrantDetailCount={}, RollUpsCount={}",
            grantDetailDtos.size(), rollUps.size());
        Set<Long> payeeAccountNumbers = grantDetailDtos.stream()
            .map(grantDetail -> {
                Rightsholder rightsholder = new Rightsholder();
                rightsholder.setId(rhAccountNumbersToIds.get(grantDetail.getRhAccountNumber()));
                rightsholder.setAccountNumber(grantDetail.getRhAccountNumber());
                String typeOfUse = grantDetail.getTypeOfUse();
                String productFamily = FdaConstants.ACL_PRODUCT_FAMILY + typeOfUse;
                Rightsholder payee = PrmRollUpService.getPayee(rollUps, rightsholder, productFamily);
                Long payeeAccountNumber = payee.getAccountNumber();
                aclGrantDetailRepository.updatePayeeAccountNumberById(grantDetail.getId(), payeeAccountNumber,
                    userName);
                return payeeAccountNumber;
            })
            .collect(Collectors.toSet());
        rightsholderService.updateRightsholders(payeeAccountNumbers);
        LOGGER.info("Populate payees for ACL grant details. Finished. GrantDetailCount={}", grantDetailDtos.size());
    }
}
