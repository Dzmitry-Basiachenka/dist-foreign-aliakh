package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * Implementation of {@link IUdmUsageWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/26/2021
 *
 * @author Ihar Suvorau
 */
public class UdmUsageWidget extends HorizontalSplitPanel implements IUdmUsageWidget {

    private static final String EMPTY_STYLE_NAME = "empty-usages-grid";
    private static final String FOOTER_LABEL = "Usages Count: %s";
    private IUdmUsageController controller;
    private Grid<UdmUsageDto> udmUsagesGrid;
    private DataProvider<UdmUsageDto, Void> dataProvider;

    @Override
    @SuppressWarnings("unchecked")
    public IUdmUsageWidget init() {
        setSplitPosition(200, Unit.PIXELS);
        setFirstComponent(controller.initUsagesFilterWidget());
        setSecondComponent(initUsagesLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void refresh() {
        dataProvider.refreshAll();
    }

    @Override
    public void setController(IUdmUsageController controller) {
        this.controller = controller;
    }

    private VerticalLayout initUsagesLayout() {
        initUsagesGrid();
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), udmUsagesGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(udmUsagesGrid, 1);
        VaadinUtils.addComponentStyle(layout, "udm-usages-layout");
        return layout;
    }

    private HorizontalLayout initButtonsLayout() {
        //TODO apply permissions for load button
        Button loadButton = Buttons.createButton(ForeignUi.getMessage("button.load"));
        loadButton.addClickListener(item -> Windows.showModalWindow(new UdmBathUploadWindow(controller)));
        HorizontalLayout layout = new HorizontalLayout(loadButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "udm-usages-buttons");
        return layout;
    }

    private void initUsagesGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (0 < size) {
                    udmUsagesGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    udmUsagesGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                udmUsagesGrid.getFooterRow(0).getCell("detailId").setText(String.format(FOOTER_LABEL, size));
                return size;
            });
        udmUsagesGrid = new Grid<>(dataProvider);
        addColumns();
        udmUsagesGrid.setSelectionMode(Grid.SelectionMode.NONE);
        udmUsagesGrid.setSizeFull();
        VaadinUtils.addComponentStyle(udmUsagesGrid, "udm-usages-grid");
    }

    private void addColumns() {
        FooterRow footer = udmUsagesGrid.appendFooterRow();
        udmUsagesGrid.setFooterVisible(true);
        footer.getCell(addColumn(UdmUsageDto::getId, "table.column.detail_id", "detailId", false, 200))
            .setText(String.format(FOOTER_LABEL, 0));
        footer.join(
            addColumn(UdmUsageDto::getPeriod, "table.column.period", "period", true, 100),
            addColumn(UdmUsageDto::getUsageOrigin, "table.column.usage_origin", "usageOrigin", true, 100),
            addColumn(UdmUsageDto::getOriginalDetailId, "table.column.usage_detail_id", "usageDetailId", true, 130),
            addColumn(UdmUsageDto::getStatus, "table.column.usage_status", "status", true, 100),
            addColumn(UdmUsageDto::getAssignee, "table.column.assignee", "assignee", true, 100),
            addColumn(UdmUsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", true, 150),
            addColumn(UdmUsageDto::getRhName, "table.column.rh_account_name", "rhName", true, 150),
            addColumn(UdmUsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 100),
            addColumn(UdmUsageDto::getReportedTitle, "table.column.reported_title", "reportedTitle", true, 120),
            addColumn(UdmUsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 100),
            addColumn(UdmUsageDto::getReportedStandardNumber, "table.column.reported_standard_number",
                "reportedStandardNumber", true, 190),
            addColumn(UdmUsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 150),
            addColumn(UdmUsageDto::getReportedPubType, "table.column.reported_pub_type", "reportedPubType", true, 150),
            addColumn(UdmUsageDto::getPubFormat, "table.column.publication_format", "publicationFormat", true, 150),
            addColumn(UdmUsageDto::getArticle, "table.column.article", "article", true, 100),
            addColumn(UdmUsageDto::getLanguage, "table.column.language", "language", true, 100),
            addColumnWithoutSort(UdmUsageDto::getDetailLicenseeClassId, "table.column.det_lc_id", "detLcId", true, 100),
            addColumnWithoutSort(UdmUsageDto::getDetailLicenseeClassName, "table.column.det_lc_name", "detLcName", true,
                100),
            addColumn(UdmUsageDto::getCompanyId, "table.column.company_id", "companyId", true, 100),
            addColumn(UdmUsageDto::getCompanyName, "table.column.company_name", "companyName", true, 120),
            addColumn(UdmUsageDto::getSurveyRespondent, "table.column.survey_respondent", "surveyRespondent", true,
                150),
            addColumn(UdmUsageDto::getIpAddress, "table.column.ip_address", "ipAddress", true, 100),
            addColumn(UdmUsageDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", true, 120),
            addColumn(UdmUsageDto::getChannel, "table.column.channel", "channel", true, 100),
            addColumn(u -> getStringFromLocalDate(u.getUsageDate()), "table.column.usage_date", "usageDate", true, 100),
            addColumn(u -> getStringFromLocalDate(u.getSurveyStartDate()), "table.column.survey_start_date",
                "surveyStartDate", true, 130),
            addColumn(u -> getStringFromLocalDate(u.getSurveyEndDate()), "table.column.survey_end_date",
                "surveyEndDate", true, 130),
            addColumn(UdmUsageDto::getAnnualMultiplier, "table.column.annual_multiplier", "annualMultiplier", true,
                130),
            addColumn(UdmUsageDto::getStatisticalMultiplier, "table.column.statistical_multiplier",
                "statisticalMultiplier", true, 150),
            addColumn(UdmUsageDto::getReportedTypeOfUse, "table.column.reported_tou", "reportedTypeOfUse", true, 120),
            addColumn(UdmUsageDto::getQuantity, "table.column.quantity", "quantity", true, 100),
            addColumn(UdmUsageDto::getAnnualizedCopies, "table.column.annualized_copies", "annualizedCopies", true,
                130),
            addColumn(UdmUsageDto::getIneligibleReason, "table.column.ineligible_reason", "ineligibleReason", true,
                130),
            addColumn(u -> getStringFromDate(u.getCreateDate()), "table.column.load_date", "createDate", true, 100),
            addColumn(UdmUsageDto::getUpdateUser, "table.column.updated_by", "updateUser", true, 100),
            addColumn(u -> getStringFromDate(u.getUpdateDate()), "table.column.updated_date", "updateDate", true, 110));
    }

    //TODO remove after detail licensee class columns will be populated
    private Column<UdmUsageDto, ?> addColumnWithoutSort(ValueProvider<UdmUsageDto, ?> valueProvider,
                                                        String captionProperty, String columnId, boolean isHidable,
                                                        double width) {
        return udmUsagesGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(false)
            .setHidable(isHidable)
            .setWidth(width);
    }

    private Column<UdmUsageDto, ?> addColumn(ValueProvider<UdmUsageDto, ?> valueProvider, String captionProperty,
                                             String columnId, boolean isHidable, double width) {
        return udmUsagesGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(isHidable)
            .setWidth(width);
    }

    private String getStringFromLocalDate(LocalDate date) {
        return CommonDateUtils.format(date, RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? FastDateFormat.getInstance(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT).format(date)
            : StringUtils.EMPTY;
    }
}
