package com.copyright.rup.dist.foreign.vui.report.impl.fas;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.report.api.fas.ISummaryMarketReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.ISummaryMarketReportWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

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
public class SummaryMarketReportWidget extends CommonDialog implements ISummaryMarketReportWidget {

    private static final long serialVersionUID = -5455990222491417006L;

    private ISummaryMarketReportController controller;
    private List<UsageBatch> batches;
    private CheckboxGroup<UsageBatch> checkBoxGroup;
    private ListDataProvider<UsageBatch> listDataProvider;
    private Button exportButton;
    private SearchWidget searchWidget;
    private OnDemandFileDownloader downloader;

    @Override
    @SuppressWarnings("unchecked")
    public ISummaryMarketReportWidget init() {
        setWidth("400px");
        setHeight("450px");
        add(initContent());
        getFooter().add(buildButtonsLayout());
        setModalWindowProperties("summary-market-report-window", false);
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

    private Section buildPanel() {
        initCheckboxGroup();
        var section = new Section(checkBoxGroup);
        VaadinUtils.addComponentStyle(section, "scroller-panel");
        section.setSizeFull();
        return section;
    }

    private VerticalLayout initContent() {
        var scroller = new Scroller(new Div(buildPanel()));
        scroller.setScrollDirection(ScrollDirection.BOTH);
        var content = VaadinUtils.initSizeFullVerticalLayout(createSearchWidget(), scroller);
        VaadinUtils.setPadding(content, 0, 10, 0, 10);
        return content;
    }

    private void initCheckboxGroup() {
        listDataProvider = new ListDataProvider<>(controller.getUsageBatches());
        checkBoxGroup = new CheckboxGroup<>();
        checkBoxGroup.setItems(listDataProvider);
        checkBoxGroup.setItemLabelGenerator(UsageBatch::getName);
        checkBoxGroup.addValueChangeListener(event -> {
            batches = new ArrayList<>(event.getValue());
            exportButton.setEnabled(CollectionUtils.isNotEmpty(batches));
            if (CollectionUtils.isNotEmpty(batches)) {
                downloader.setResource(new CsvStreamSource(controller).getSource());
            } else {
                downloader.removeHref();
            }
        });
        checkBoxGroup.addThemeVariants(CheckboxGroupVariant.MATERIAL_VERTICAL);
    }

    private SearchWidget createSearchWidget() {
        searchWidget = new SearchWidget(this::performSearch);
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.batch"));
        return searchWidget;
    }

    private void performSearch() {
        listDataProvider.clearFilters();
        var searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            listDataProvider.addFilter(value -> StringUtils.contains(StringUtils.lowerCase(value.getName()),
                StringUtils.lowerCase(searchValue)));
        }
    }

    private HorizontalLayout buildButtonsLayout() {
        var closeButton = Buttons.createCloseButton(this);
        var clearButton = Buttons.createButton("Clear");
        clearButton.addClickListener(event -> checkBoxGroup.clear());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        downloader = new OnDemandFileDownloader();
        downloader.extend(exportButton);
        return new HorizontalLayout(downloader, clearButton, closeButton);
    }
}
