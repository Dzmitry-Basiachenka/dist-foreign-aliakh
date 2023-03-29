package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclFundPoolByAggLcReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclFundPoolByAggLcReportWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool.AclFundPoolItemFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.BaseItemsFilterWidget;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IAclFundPoolByAggLcReportWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/22/2023
 *
 * @author Ihar Suvorau
 */
public class AclFundPoolByAggLcReportWidget extends Window implements IAclFundPoolByAggLcReportWidget {

    private static final String EMPTY_FILTER_STYLE = "empty-item-filter-widget";

    private final Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
    private IAclFundPoolByAggLcReportController controller;
    private Set<String> fundPoolIds = new HashSet<>();

    @Override
    @SuppressWarnings("unchecked")
    public IAclFundPoolByAggLcReportWidget init() {
        setContent(initRootLayout());
        setResizable(false);
        setWidth(350, Unit.PIXELS);
        setHeight(125, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "acl-report-window");
        return this;
    }

    @Override
    public Set<String> getFundPoolIds() {
        return fundPoolIds;
    }

    @Override
    public void setController(IAclFundPoolByAggLcReportController controller) {
        this.controller = controller;
    }

    private ComponentContainer initRootLayout() {
        PeriodFilterWidget periodFilterWidget = new PeriodFilterWidget(controller::getPeriods);
        periodFilterWidget.addStyleName(EMPTY_FILTER_STYLE);
        VaadinUtils.setMaxComponentsWidth(periodFilterWidget);
        AclFundPoolItemFilterWidget fundPoolFilterWidget = new AclFundPoolItemFilterWidget(() ->
            controller.getFundPools(periodFilterWidget.getSelectedItemsIds()));
        fundPoolFilterWidget.addStyleName(EMPTY_FILTER_STYLE);
        fundPoolFilterWidget.setEnabled(false);
        periodFilterWidget.addFilterSaveListener(event -> {
            fundPoolFilterWidget.reset();
            fundPoolFilterWidget.loadBeans();
            fundPoolFilterWidget.setEnabled(CollectionUtils.isNotEmpty(event.getSelectedItemsIds()));
            applyFilterEmptyStyle(periodFilterWidget, event.getSelectedItemsIds().size());
            exportButton.setEnabled(false);
        });
        fundPoolFilterWidget.addFilterSaveListener(event -> {
            Set<AclFundPool> selectedItems = event.getSelectedItemsIds();
            exportButton.setEnabled(CollectionUtils.isNotEmpty(selectedItems));
            applyFilterEmptyStyle(fundPoolFilterWidget, selectedItems.size());
            fundPoolIds = selectedItems.stream().map(BaseEntity::getId).collect(Collectors.toSet());
        });
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout verticalLayout = new VerticalLayout(periodFilterWidget, fundPoolFilterWidget, buttonsLayout);
        verticalLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        VaadinUtils.addComponentStyle(periodFilterWidget, "acl-report-period-filter-widget");
        VaadinUtils.addComponentStyle(fundPoolFilterWidget, "acl-report-fund-pool-filter-widget");
        return verticalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        exportButton.setEnabled(false);
        Button closeButton = Buttons.createCloseButton(this);
        HorizontalLayout layout = new HorizontalLayout(exportButton, closeButton);
        OnDemandFileDownloader downloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        downloader.extend(exportButton);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        layout.setComponentAlignment(exportButton, Alignment.MIDDLE_LEFT);
        layout.setSizeFull();
        return layout;
    }

    private void applyFilterEmptyStyle(BaseItemsFilterWidget filterWidget, int size) {
        if (0 < size) {
            filterWidget.removeStyleName(EMPTY_FILTER_STYLE);
        } else {
            filterWidget.addStyleName(EMPTY_FILTER_STYLE);
        }
    }
}
