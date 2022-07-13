package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Builder for {@link CreateAclScenarioIntegrationTest}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/05/2022
 *
 * @author Anton Azarenka
 */
@Component
public class CreateAclScenarioIntegrationTestBuilder {

    @Autowired
    private IAclScenarioService scenarioService;
    @Autowired
    private IAclUsageRepository usageRepository;
    @Autowired
    private IAclScenarioRepository scenarioRepository;
    @Autowired
    private ServiceTestHelper testHelper;

    private AclScenario expectedScenario;
    private String pathToScenarioDetailsToUpload;

    CreateAclScenarioIntegrationTestBuilder withExpectedScenario(AclScenario scenario) {
        this.expectedScenario = scenario;
        return this;
    }

    CreateAclScenarioIntegrationTestBuilder withLicenseeClasses(List<DetailLicenseeClass> licenseeClasses) {
        this.expectedScenario.setDetailLicenseeClasses(licenseeClasses);
        return this;
    }

    CreateAclScenarioIntegrationTestBuilder withUsageAges(List<UsageAge> usageAges) {
        this.expectedScenario.setUsageAges(usageAges);
        return this;
    }

    CreateAclScenarioIntegrationTestBuilder withPublicationTypes(List<AclPublicationType> publicationTypes) {
        this.expectedScenario.setPublicationTypes(publicationTypes);
        return this;
    }

    CreateAclScenarioIntegrationTestBuilder withAclScenarioDetails(String pathToScenarioDetails) {
        this.pathToScenarioDetailsToUpload = pathToScenarioDetails;
        return this;
    }

    Runner build() {
        return new Runner();
    }

    /**
     * Test runner class.
     */
    class Runner {

        void run() throws IOException {
            scenarioService.insertScenario(expectedScenario);
            AclScenario scenario = scenarioRepository.findById(expectedScenario.getId());
            testHelper.verifyAclScenario(expectedScenario, scenario);
            List<AclScenarioDetail> scenarioDetails =
                usageRepository.findScenarioDetailsByScenarioId(scenario.getId());
            testHelper.verifyAclScenarioDetails(pathToScenarioDetailsToUpload, scenarioDetails);
        }
    }
}
