package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.IDetailLicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.AggregateLicenseeClassValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ClassifiedUsageValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ClassifiedWrWrkInstValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DetailLicenseeClassValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.MarketPeriodValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.PublicationTypeValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ResearchedUsageValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ResearchedWrWrkInstValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.RightsholderWrWrkInstValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.WorkTitleStandardNumberValidator;
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
    @Autowired
    private IDetailLicenseeClassService detailLicenseeClassService;
    @Autowired
    private IPublicationTypeService publicationTypeService;
    @Autowired
    private IAaclFundPoolRepository aaclFundPoolRepository;

    /**
     * Initialized UsageCsvProcessor.
     *
     * @param productFamily product family
     * @return instance of {@link UsageCsvProcessor}.
     */
    public UsageCsvProcessor getUsageCsvProcessor(String productFamily) {
        UsageCsvProcessor processor = new UsageCsvProcessor(productFamily);
        processor.addBusinessValidators(new MarketPeriodValidator(), new RightsholderWrWrkInstValidator(),
            new WorkTitleStandardNumberValidator(), new WrWrkInstValidator(piIntegrationService));
        return processor;
    }

    /**
     * @return instance of {@link AaclUsageCsvProcessor}.
     */
    public AaclUsageCsvProcessor getAaclUsageCsvProcessor() {
        return new AaclUsageCsvProcessor();
    }

    /**
     * @return instance of {@link AaclFundPoolCsvProcessor}.
     */
    public AaclFundPoolCsvProcessor getAaclFundPoolCsvProcessor() {
        AaclFundPoolCsvProcessor processor = new AaclFundPoolCsvProcessor();
        processor.addBusinessValidators(new AggregateLicenseeClassValidator(aaclFundPoolRepository));
        return processor;
    }

    /**
     * @return instance of {@link ResearchedUsagesCsvProcessor}.
     */
    public ResearchedUsagesCsvProcessor getResearchedUsagesCsvProcessor() {
        ResearchedUsagesCsvProcessor processor = new ResearchedUsagesCsvProcessor();
        processor.addBusinessValidators(new ResearchedUsageValidator(usageService),
            new ResearchedWrWrkInstValidator(piIntegrationService));
        return processor;
    }

    /**
     * @return instance of {@link ResearchedUsagesCsvProcessor}.
     */
    public ClassifiedUsageCsvProcessor getClassifiedUsageCsvProcessor() {
        ClassifiedUsageCsvProcessor processor = new ClassifiedUsageCsvProcessor();
        processor.addBusinessValidators(
            new ClassifiedUsageValidator(usageService),
            new ClassifiedWrWrkInstValidator(piIntegrationService),
            new DetailLicenseeClassValidator(detailLicenseeClassService),
            new PublicationTypeValidator(publicationTypeService));
        return processor;
    }
}
