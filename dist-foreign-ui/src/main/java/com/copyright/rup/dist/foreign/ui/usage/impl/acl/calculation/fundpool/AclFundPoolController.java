package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.service.impl.csv.AclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
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
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;

    @Override
    public IAclFundPoolFilterWidget initAclFundPoolFilterWidget() {
        return aclFundPoolFilterController.initWidget();
    }

    @Override
    public boolean isFundPoolExist(String name) {
        //todo will implement later
        return false;
    }

    @Override
    public AclFundPoolCsvProcessor getCsvProcessor() {
        return csvProcessorFactory.getAclFundPoolCvsProcessor();
    }

    @Override
    public int loadFundPool(AclFundPool fundPool, List<AclFundPoolDetail> fundPoolDetails) {
        //todo will be implement later
        return fundPoolDetails.size();
    }

    @Override
    public IStreamSource getErrorResultStreamSource(String fileName,
                                                    ProcessingResult<AclFundPoolDetail> processingResult) {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("Error_for_%s", Files.getNameWithoutExtension(fileName)), null,
            processingResult::writeToFile);
    }

    @Override
    protected IAclFundPoolWidget instantiateWidget() {
        return new AclFundPoolWidget();
    }
}
