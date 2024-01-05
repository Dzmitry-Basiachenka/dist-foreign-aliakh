package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.ISummaryMarketReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ISummaryMarketReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Widget for exporting summary of market report.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 8/27/2018
 *
 * @author Ihar Suvorau
 */
public class SummaryMarketReportWidget extends Window implements ISummaryMarketReportWidget {

    private static final long serialVersionUID = -5455990222491417006L;

    private ISummaryMarketReportController controller;
    private List<UsageBatch> batches;
    private CheckBoxGroup<UsageBatch> checkBoxGroup;
    private ListDataProvider<UsageBatch> listDataProvider;
    private Button exportButton;
    private SearchWidget searchWidget;

    @Override
    @SuppressWarnings("unchecked")
    public ISummaryMarketReportWidget init() {
        setWidth(350, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "summary-market-report-window");
        Panel panel = buildPanel();
        HorizontalLayout buttonsLayout = buildButtonsLayout();
        VerticalLayout content = new VerticalLayout(createSearchWidget(), panel, buttonsLayout);
        content.setExpandRatio(panel, 1f);
        content.setSizeFull();
        content.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(content);
        return this;
    }

    @Override
    public void setController(ISummaryMarketReportController controller) {
        this.controller = controller;
    }

    @Override
    public List<UsageBatch> getBatches() {
        return batches;
    }

    private Panel buildPanel() {
        listDataProvider = new ListDataProvider<>(controller.getUsageBatches());
        checkBoxGroup = new CheckBoxGroup<>(null, listDataProvider);
        checkBoxGroup.setItemCaptionGenerator(UsageBatch::getName);
        checkBoxGroup.setHtmlContentAllowed(true);
        checkBoxGroup.addValueChangeListener(event -> {
            batches = new ArrayList<>(event.getValue());
            exportButton.setEnabled(CollectionUtils.isNotEmpty(batches));
        });
        Panel panel = new Panel(checkBoxGroup);
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        panel.setSizeFull();
        return panel;
    }

    private SearchWidget createSearchWidget() {
        searchWidget = new SearchWidget(this::performSearch);
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.batch"));
        return searchWidget;
    }

    private void performSearch() {
        listDataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            listDataProvider.addFilter(value -> StringUtils.contains(StringUtils.lowerCase(value.getName()),
                StringUtils.lowerCase(searchValue)));
        }
    }

    private HorizontalLayout buildButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button clearButton = Buttons.createButton("Clear");
        clearButton.addClickListener(event -> checkBoxGroup.clear());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        OnDemandFileDownloader downloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        downloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(exportButton, clearButton, closeButton);
        layout.setMargin(new MarginInfo(false, false, false, false));
        return layout;
    }
}
