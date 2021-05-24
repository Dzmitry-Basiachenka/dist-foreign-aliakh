package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.AaclDetailLicenseeClassValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.AggregateLicenseeClassValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ClassifiedUsageValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ClassifiedWrWrkInstValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ItemBankWorkPortionIdValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.MarketPeriodValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.OriginalDetailIdValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.PublicationTypeValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.QuantityValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ReportedTypeOfUseValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ResearchedUsageValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ResearchedWrWrkInstValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.RightsholderWrWrkInstValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.SurveyDateValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.UdmWorkInfoValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.UsageDataGradeValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.UsageDataWorkPortionIdValidator;
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
    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private IUdmTypeOfUseService udmTypeOfUseService;

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
        processor.addBusinessValidators(new AggregateLicenseeClassValidator(licenseeClassService,
            FdaConstants.AACL_PRODUCT_FAMILY));
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
            new AaclDetailLicenseeClassValidator(licenseeClassService),
            new PublicationTypeValidator(publicationTypeService));
        return processor;
    }

    /**
     * @return instance of {@link SalItemBankCsvProcessor}.
     */
    public SalItemBankCsvProcessor getSalItemBankCsvProcessor() {
        SalItemBankCsvProcessor processor = new SalItemBankCsvProcessor();
        processor.addBusinessValidators(new ItemBankWorkPortionIdValidator(salUsageService));
        return processor;
    }

    /**
     * Builds {@link SalUsageDataCsvProcessor} instance.
     *
     * @param batchId batch id
     * @return instance of {@link SalUsageDataCsvProcessor}.
     */
    public SalUsageDataCsvProcessor getSalUsageDataCsvProcessor(String batchId) {
        SalUsageDataCsvProcessor processor = new SalUsageDataCsvProcessor();
        processor.addBusinessValidators(
            new UsageDataWorkPortionIdValidator(salUsageService, batchId),
            new UsageDataGradeValidator(salUsageService));
        return processor;
    }

    /**
     * @return instance of {@link UdmCsvProcessor}.
     */
    public UdmCsvProcessor getUdmCsvProcessor() {
        UdmCsvProcessor processor = new UdmCsvProcessor();
        processor.addBusinessValidators(new SurveyDateValidator(), new QuantityValidator(),
            new ReportedTypeOfUseValidator(udmTypeOfUseService), new OriginalDetailIdValidator(udmUsageService),
            new UdmWorkInfoValidator());
        return processor;
    }
}
