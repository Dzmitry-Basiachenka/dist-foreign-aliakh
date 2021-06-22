package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.themes.ValoTheme;

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
    private final boolean hasResearcherPermission = ForeignSecurityUtils.hasResearcherPermission();
    private IUdmUsageController controller;
    private Grid<UdmUsageDto> udmUsagesGrid;
    private DataProvider<UdmUsageDto, Void> dataProvider;
    private Button loadButton;
    private SearchWidget searchWidget;

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
    public IMediator initMediator() {
        UdmUsageMediator mediator = new UdmUsageMediator();
        mediator.setLoadButton(loadButton);
        return mediator;
    }

    @Override
    public void refresh() {
        dataProvider.refreshAll();
    }

    @Override
    public void setController(IUdmUsageController controller) {
        this.controller = controller;
    }

    @Override
    public String getSearchValue() {
        return StringUtils.defaultIfBlank(searchWidget.getSearchValue(), null);
    }

    private VerticalLayout initUsagesLayout() {
        initUsagesGrid();
        VerticalLayout layout = new VerticalLayout(initToolbarLayout(), udmUsagesGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(udmUsagesGrid, 1);
        VaadinUtils.addComponentStyle(layout, "udm-usages-layout");
        return layout;
    }

    private HorizontalLayout initToolbarLayout() {
        loadButton = Buttons.createButton(ForeignUi.getMessage("button.load"));
        loadButton.addClickListener(item -> Windows.showModalWindow(new UdmBatchUploadWindow(controller)));
        searchWidget = new SearchWidget(this::refresh);
        searchWidget.setPrompt(ForeignUi.getMessage(getSearchMessage()));
        searchWidget.setWidth(65, Unit.PERCENTAGE);
        HorizontalLayout toolbar = new HorizontalLayout(loadButton, searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbar);
        toolbar.setComponentAlignment(loadButton, Alignment.BOTTOM_LEFT);
        toolbar.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbar.setExpandRatio(searchWidget, 1f);
        toolbar.setMargin(true);
        VaadinUtils.addComponentStyle(toolbar, "udm-usages-toolbar");
        return toolbar;
    }

    private String getSearchMessage() {
        return hasResearcherPermission ? "prompt.udm_search_researcher" : "prompt.udm_search";
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
        Column<UdmUsageDto, ?> column = udmUsagesGrid.addComponentColumn(usage -> {
            String udmUsageId = usage.getId();
            Button button = Buttons.createButton(udmUsageId);
            button.addStyleName(ValoTheme.BUTTON_LINK);
            button.addClickListener(event -> controller.showUdmUsageHistory(udmUsageId));
            return button;
        })
            .setCaption(ForeignUi.getMessage("table.column.detail_id"))
            .setId("detailId")
            .setSortProperty("detailId")
            .setWidth(200);
        footer.getCell(column).setText(String.format(FOOTER_LABEL, 0));
        footer.join(
            addColumn(UdmUsageDto::getPeriod, "table.column.period", "period", 100, false),
            addColumn(UdmUsageDto::getUsageOrigin, "table.column.usage_origin", "usageOrigin", 100,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getOriginalDetailId, "table.column.usage_detail_id", "usageDetailId", 130, false),
            addColumn(UdmUsageDto::getStatus, "table.column.usage_status", "status", 100, false),
            addColumn(UdmUsageDto::getAssignee, "table.column.assignee", "assignee", 100, false),
            addColumn(UdmUsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 150, false),
            addColumn(UdmUsageDto::getRhName, "table.column.rh_account_name", "rhName", 150, false),
            addColumn(UdmUsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100, false),
            addColumn(UdmUsageDto::getReportedTitle, "table.column.reported_title", "reportedTitle", 120, false),
            addColumn(UdmUsageDto::getSystemTitle, "table.column.system_title", "systemTitle", 100, false),
            addColumn(UdmUsageDto::getReportedStandardNumber, "table.column.reported_standard_number",
                "reportedStandardNumber", 190, false),
            addColumn(UdmUsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", 150, false),
            addColumn(UdmUsageDto::getReportedPubType, "table.column.reported_pub_type", "reportedPubType", 150, false),
            addColumn(UdmUsageDto::getPubFormat, "table.column.publication_format", "publicationFormat", 150, false),
            addColumn(UdmUsageDto::getArticle, "table.column.article", "article", 100, false),
            addColumn(UdmUsageDto::getLanguage, "table.column.language", "language", 100, false),
            addColumn(UdmUsageDto::getComment, "table.column.comment", "comment", 200, false),
            addColumn(UdmUsageDto::getDetailLicenseeClassId, "table.column.det_lc_id", "detLcId", 100, false),
            addColumn(UdmUsageDto::getDetailLicenseeClassName, "table.column.det_lc_name", "detLcName", 100, false),
            addColumn(UdmUsageDto::getCompanyId, "table.column.company_id", "companyId", 100, hasResearcherPermission),
            addColumn(UdmUsageDto::getCompanyName, "table.column.company_name", "companyName", 120,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getSurveyRespondent, "table.column.survey_respondent", "surveyRespondent", 150,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getIpAddress, "table.column.ip_address", "ipAddress", 100,
                !ForeignSecurityUtils.hasManagerPermission()),
            addColumn(UdmUsageDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", 120,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getChannel, "table.column.channel", "channel", 100, false),
            addColumn(u -> getStringFromLocalDate(u.getUsageDate()), "table.column.usage_date", "usageDate", 100,
                false),
            addColumn(u -> getStringFromLocalDate(u.getSurveyStartDate()), "table.column.survey_start_date",
                "surveyStartDate", 130, false),
            addColumn(u -> getStringFromLocalDate(u.getSurveyEndDate()), "table.column.survey_end_date",
                "surveyEndDate", 130, false),
            addColumn(UdmUsageDto::getAnnualMultiplier, "table.column.annual_multiplier", "annualMultiplier", 130,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getStatisticalMultiplier, "table.column.statistical_multiplier",
                "statisticalMultiplier", 150, hasResearcherPermission),
            addColumn(UdmUsageDto::getReportedTypeOfUse, "table.column.reported_tou", "reportedTypeOfUse", 120, false),
            addColumn(UdmUsageDto::getQuantity, "table.column.quantity", "quantity", 100, hasResearcherPermission),
            addColumn(UdmUsageDto::getAnnualizedCopies, "table.column.annualized_copies", "annualizedCopies", 130,
                hasResearcherPermission),
            addColumn(UdmUsageDto::getIneligibleReason, "table.column.ineligible_reason", "ineligibleReason", 130,
                false),
            addColumn(u -> getStringFromDate(u.getCreateDate()), "table.column.load_date", "createDate", 100, false),
            addColumn(UdmUsageDto::getUpdateUser, "table.column.updated_by", "updateUser", 100, false),
            addColumn(u -> getStringFromDate(u.getUpdateDate()), "table.column.updated_date", "updateDate", 110,
                false));
    }

    private Column<UdmUsageDto, ?> addColumn(ValueProvider<UdmUsageDto, ?> valueProvider, String captionProperty,
                                             String columnId, double width, boolean isHidden) {
        return udmUsagesGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(!isHidden)
            .setHidden(isHidden)
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
