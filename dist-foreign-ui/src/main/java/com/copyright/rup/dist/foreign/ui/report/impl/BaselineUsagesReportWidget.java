package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IBaselineUsagesReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IBaselineUsagesReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

/**
 * Widget for exporting baseline usages.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/19/2020
 *
 * @author Ihar Suvorau
 */
public class BaselineUsagesReportWidget extends Window implements IBaselineUsagesReportWidget {

    private IBaselineUsagesReportController controller;
    private TextField numberOfBaselineYearsField;
    private final Binder<Integer> binder = new Binder<>();
    private Button exportButton;

    @Override
    @SuppressWarnings("unchecked")
    public IBaselineUsagesReportWidget init() {
        initNumberOfBaselineYearsField();
        VerticalLayout content = new VerticalLayout(numberOfBaselineYearsField, getButtonsLayout());
        content.setSpacing(false);
        VaadinUtils.setMaxComponentsWidth(content);
        setContent(content);
        setWidth(300, Unit.PIXELS);
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "baseline-usages-report-window");
        return this;
    }

    @Override
    public void setController(IBaselineUsagesReportController controller) {
        this.controller = controller;
    }

    @Override
    public int getNumberOfBaselineYears() {
        return Integer.parseInt(StringUtils.trim(numberOfBaselineYearsField.getValue()));
    }

    private void initNumberOfBaselineYearsField() {
        numberOfBaselineYearsField = new TextField(ForeignUi.getMessage("label.number_of_baseline_years"));
        numberOfBaselineYearsField.setRequiredIndicatorVisible(true);
        binder.forField(numberOfBaselineYearsField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.empty"))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)) || Integer.parseInt(
                StringUtils.trim(value)) >= 0, "Field value should contain positive numeric values only")
            .withConverter(new StringToIntegerConverter("Field value can not be converted"))
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
        VaadinUtils.addComponentStyle(numberOfBaselineYearsField, "number-of-baseline-years-field");
        numberOfBaselineYearsField.setWidth(100, Unit.PERCENTAGE);
        numberOfBaselineYearsField.addValueChangeListener(value -> exportButton.setEnabled(binder.isValid()));
    }

    private HorizontalLayout getButtonsLayout() {
        Button closeButton = Buttons.createCloseButton((Window) controller.getWidget());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        OnDemandFileDownloader downloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        downloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(exportButton, closeButton);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        layout.setComponentAlignment(exportButton, Alignment.MIDDLE_LEFT);
        layout.setMargin(new MarginInfo(true, false, false, false));
        layout.setSizeFull();
        return layout;
    }
}
