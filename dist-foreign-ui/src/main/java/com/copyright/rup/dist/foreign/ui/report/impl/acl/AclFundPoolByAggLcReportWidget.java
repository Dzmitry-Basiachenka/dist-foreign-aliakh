package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclFundPoolByAggLcReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclFundPoolByAggLcReportWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool.AclFundPoolNameFilterWidget;
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

import java.util.ArrayList;

/**
 * Implementation of {@link IAclFundPoolByAggLcReportWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/05/2022
 *
 * @author Ihar Suvorau
 */
public class AclFundPoolByAggLcReportWidget extends Window implements IAclFundPoolByAggLcReportWidget {

    private static final String EMPTY_FILTER_STYLE = "empty-item-filter-widget";

    private final Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
    private IAclFundPoolByAggLcReportController controller;

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
    public void setController(IAclFundPoolByAggLcReportController controller) {
        this.controller = controller;
    }

    private ComponentContainer initRootLayout() {
        PeriodFilterWidget periodFilterWidget = new PeriodFilterWidget(ArrayList::new);
        periodFilterWidget.addStyleName(EMPTY_FILTER_STYLE);
        VaadinUtils.setMaxComponentsWidth(periodFilterWidget);
        AclFundPoolNameFilterWidget fundPoolNameFilterWidget = new AclFundPoolNameFilterWidget(ArrayList::new);
        fundPoolNameFilterWidget.addStyleName(EMPTY_FILTER_STYLE);
        fundPoolNameFilterWidget.setEnabled(false);
        periodFilterWidget.addFilterSaveListener(event -> {
            fundPoolNameFilterWidget.reset();
            fundPoolNameFilterWidget.setEnabled(CollectionUtils.isNotEmpty(event.getSelectedItemsIds()));
            applyFilterEmptyStyle(periodFilterWidget, event.getSelectedItemsIds().size());
        });
        fundPoolNameFilterWidget.addFilterSaveListener(event -> {
            exportButton.setEnabled(CollectionUtils.isNotEmpty(event.getSelectedItemsIds()));
            applyFilterEmptyStyle(fundPoolNameFilterWidget, event.getSelectedItemsIds().size());
        });
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout verticalLayout = new VerticalLayout(periodFilterWidget, fundPoolNameFilterWidget, buttonsLayout);
        verticalLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        VaadinUtils.addComponentStyle(periodFilterWidget, "acl-report-period-filter-widget");
        VaadinUtils.addComponentStyle(fundPoolNameFilterWidget, "acl-report-fund-pool-filter-widget");
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
