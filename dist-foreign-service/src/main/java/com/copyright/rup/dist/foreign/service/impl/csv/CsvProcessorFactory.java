package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.AggregateLicenseeClassValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ClassifiedUsageValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ClassifiedWrWrkInstValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DetailLicenseeClassValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.MarketPeriodValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.PublicationTypeValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ResearchedUsageValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ResearchedWrWrkInstValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.RightsholderWrWrkInstValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.WorkPortionIdValidator;
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
    private ILicenseeClassService licenseeClassService;
    @Autowired
    private IPublicationTypeService publicationTypeService;
    @Autowired
    private ISalUsageService salUsageService;

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
        processor.addBusinessValidators(new AggregateLicenseeClassValidator(licenseeClassService));
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
            new DetailLicenseeClassValidator(licenseeClassService),
            new PublicationTypeValidator(publicationTypeService));
        return processor;
    }

    /**
     * @return instance of {@link SalUsageCsvProcessor}.
     */
    public SalUsageCsvProcessor getSalUsageCsvProcessor() {
        SalUsageCsvProcessor processor = new SalUsageCsvProcessor();
        processor.addBusinessValidators(
            new WrWrkInstValidator(piIntegrationService),
            new WorkPortionIdValidator(salUsageService));
        return processor;
    }
}
