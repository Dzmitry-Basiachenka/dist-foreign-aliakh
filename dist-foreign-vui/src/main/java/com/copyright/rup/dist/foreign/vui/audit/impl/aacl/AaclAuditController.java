package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditController;

import com.vaadin.flow.data.provider.QuerySortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IAaclAuditController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclAuditController extends CommonAuditController implements IAaclAuditController {

    private static final long serialVersionUID = -2423045440172687438L;

    @Autowired
    private IAaclAuditFilterController filterController;

    @Override
    public ICommonAuditFilterController getAuditFilterController() {
        return filterController;
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        //TODO: will implement later
        return getStreamSourceHandler().getCsvStreamSource(() -> "export_usage_audit_", temp -> {});
    }

    @Override
    public int getSize() {
        //TODO: will implement later
        return 0;
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        //TODO: will implement later
        return new ArrayList<>();
    }

    @Override
    protected ICommonAuditWidget instantiateWidget() {
        //TODO: will implement later
        return new AaclAuditWidget();
    }
}
