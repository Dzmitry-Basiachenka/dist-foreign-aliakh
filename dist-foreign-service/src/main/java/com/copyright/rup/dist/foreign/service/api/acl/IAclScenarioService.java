package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UsageAge;

import java.util.List;

/**
 * Interface for ACL scenario service.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 06/23/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclScenarioService {

    /**
     * Inserts {@link AclScenario}.
     *
     * @param aclScenario instance of {@link AclScenario}
     */
    void insertScenario(AclScenario aclScenario);

    /**
     * Inserts usage age weights of ACL scenario.
     *
     * @param usageAges  list of {@link UsageAge}s
     * @param scenarioId scenario uid
     * @param userName   username
     */
    void insertAclScenarioUsageAgeWeights(List<UsageAge> usageAges, String scenarioId, String userName);

    /**
     * Inserts licensee classes of ACL scenario.
     *
     * @param licenseeClasses list of {@link DetailLicenseeClass}s
     * @param scenarioId      scenario uid
     * @param userName        username
     */
    void insertAclScenarioLicenseeClasses(List<DetailLicenseeClass> licenseeClasses, String scenarioId,
                                          String userName);

    /**
     * Inserts publication type weight of ACL scenario.
     *
     * @param publicationTypes list of {@link AclPublicationType}s
     * @param scenarioId       scenario uid
     * @param userName         username
     */
    void insertAclScenarioPubTypeWeights(List<AclPublicationType> publicationTypes, String scenarioId, String userName);

    /**
     * Gets list of {@link AclScenario}s.
     *
     * @return list of {@link AclScenario}s
     */
    List<AclScenario> getScenarios();

    /**
     * Checks whether {@link AclScenario} with specified name already exists in database.
     *
     * @param scenarioName name of {@link AclScenario} to check
     * @return {@code true} if {@link AclScenario} with specified name already exists in database,
     * otherwise {@code false}
     */
    boolean aclScenarioExists(String scenarioName);

    /**
     * Gets {@link AclScenarioDto} by scenario id.
     *
     * @param scenarioId scenario id
     * @return instance of {@link AclScenarioDto}
     */
    AclScenarioDto getAclScenarioWithAmountsAndLastAction(String scenarioId);

    /**
     * Gets list of {@link AclScenarioDetailDto}s based on ACL scenario id and rightsholder account number.
     *
     * @param accountNumber selected rightsholder account number
     * @param scenarioId    scenario id
     * @param searchValue   search value
     * @param pageable      instance of {@link Pageable}
     * @param sort          instance of {@link Sort}
     * @return list of {@link AclScenarioDetailDto}s
     */
    List<AclScenarioDetailDto> getByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                                 String searchValue, Pageable pageable, Sort sort);

    /**
     * Gets count of {@link AclScenarioDetailDto}s based on ACL scenario id and rightsholder account number.
     *
     * @param accountNumber selected rightsholder account number
     * @param scenarioId    scenario id
     * @param searchValue   search value
     * @return count of {@link AclScenarioDetailDto}s
     */
    int getCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue);
}
