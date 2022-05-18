package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.impl.csv.AclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.io.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IAclFundPoolController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/17/2022
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclFundPoolController extends CommonController<IAclFundPoolWidget> implements IAclFundPoolController {

    @Autowired
    private IAclFundPoolFilterController aclFundPoolFilterController;
    @Autowired
    private IAclFundPoolService fundPoolService;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IAclCalculationReportService aclCalculationReportService;

    @Override
    public IAclFundPoolFilterWidget initAclFundPoolFilterWidget() {
        IAclFundPoolFilterWidget widget = aclFundPoolFilterController.initWidget();
        widget.addListener(FilterChangedEvent.class, this, IAclFundPoolController.ON_FILTER_CHANGED);
        return widget;
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public boolean isFundPoolExist(String name) {
        return fundPoolService.fundPoolExists(name);
    }

    @Override
    public AclFundPoolCsvProcessor getCsvProcessor() {
        return csvProcessorFactory.getAclFundPoolCvsProcessor();
    }

    @Override
    public int loadManualFundPool(AclFundPool fundPool, List<AclFundPoolDetail> fundPoolDetails) {
        fundPoolService.insertManualAclFundPool(fundPool, fundPoolDetails);
        return fundPoolDetails.size();
    }

    @Override
    public int createLdmtFundPool(AclFundPool fundPool) {
        return fundPoolService.insertLdmtAclFundPool(fundPool);
    }

    @Override
    public boolean isLdmtDetailExist(String licenseType) {
        return fundPoolService.isLdmtDetailExist(licenseType);
    }

    @Override
    public List<AclFundPoolDetailDto> getDtos() {
        return fundPoolService.getDtosByFilter(aclFundPoolFilterController.getWidget().getAppliedFilter());
    }

    @Override
    public IStreamSource getErrorResultStreamSource(String fileName,
                                                    ProcessingResult<AclFundPoolDetail> processingResult) {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("Error_for_%s", Files.getNameWithoutExtension(fileName)), null,
            processingResult::writeToFile);
    }

    @Override
    public IStreamSource getExportAclFundPoolDetailsStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_fund_pool_details_",
            pos -> aclCalculationReportService.writeAclFundPoolDetailsCsvReport(
                aclFundPoolFilterController.getWidget().getAppliedFilter(), pos));
    }

    @Override
    public List<AclFundPool> getAllAclFundPools() {
        //TODO will implement later
        return new ArrayList<>();
    }

    @Override
    public void deleteAclFundPool(AclFundPool fundPool) {
        //TODO will implement later
    }

    @Override
    protected IAclFundPoolWidget instantiateWidget() {
        return new AclFundPoolWidget();
    }
}
