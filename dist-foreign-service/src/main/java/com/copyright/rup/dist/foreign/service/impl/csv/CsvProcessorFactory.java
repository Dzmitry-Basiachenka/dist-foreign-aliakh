package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.MarketPeriodValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ResearchedUsageValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.RightsholderWrWrkInstValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.WorkTitleValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.WrWrkInstValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Factory for CSV processors.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/03/2018
 *
 * @author Nikita Levyankov
 */
@Component
public class CsvProcessorFactory {

    @Autowired
    private IUsageService usageService;
    @Autowired
    @Qualifier("df.integration.piIntegrationCacheService")
    private IPiIntegrationService piIntegrationService;

    /**
     * Initialized UsageCsvProcessor.
     *
     * @param productFamily product family
     * @return instance of {@link UsageCsvProcessor}.
     */
    public UsageCsvProcessor getUsageCsvProcessor(String productFamily) {
        UsageCsvProcessor processor = new UsageCsvProcessor(productFamily);
        processor.addBusinessValidators(new MarketPeriodValidator(), new RightsholderWrWrkInstValidator(),
            new WorkTitleValidator(), new WrWrkInstValidator(piIntegrationService));
        return processor;
    }

    /**
     * @return instance of {@link ResearchedUsagesCsvProcessor}.
     */
    public ResearchedUsagesCsvProcessor getResearchedUsagesCsvProcessor() {
        ResearchedUsagesCsvProcessor processor = new ResearchedUsagesCsvProcessor();
        processor.addBusinessValidators(new ResearchedUsageValidator(usageService));
        return processor;
    }
}
