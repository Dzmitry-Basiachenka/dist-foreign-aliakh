package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IUdmBaselineWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBaselineWidget extends HorizontalSplitPanel implements IUdmBaselineWidget {

    private static final String EMPTY_STYLE_NAME = "empty-baseline-grid";
    private static final String FOOTER_LABEL = "Usages Count: %s";

    private boolean hasSpecialistPermission;
    private IUdmBaselineController controller;
    private Grid<UdmBaselineDto> udmBaselineGrid;
    private MultiSelectionModelImpl<UdmBaselineDto> gridSelectionModel;
    private Button deleteButton;

    @Override
    public void refresh() {
        udmBaselineGrid.deselectAll();
        udmBaselineGrid.getDataProvider().refreshAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public UdmBaselineWidget init() {
        hasSpecialistPermission = ForeignSecurityUtils.hasSpecialistPermission();
        setSplitPosition(270, Unit.PIXELS);
        setFirstComponent(controller.initBaselineFilterWidget());
        setSecondComponent(initBaselineLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void setController(IUdmBaselineController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        UdmBaselineMediator mediator = new UdmBaselineMediator();
        mediator.setDeleteButton(deleteButton);
        return mediator;
    }

    private VerticalLayout initBaselineLayout() {
        initBaselineGrid();
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), udmBaselineGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(udmBaselineGrid, 1);
        VaadinUtils.addComponentStyle(layout, "udm-baseline-layout");
        return layout;
    }

    private void initBaselineGrid() {
        DataProvider<UdmBaselineDto, Void> dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (0 < size) {
                    udmBaselineGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    udmBaselineGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                switchSelectAllCheckBoxVisibility(size);
                udmBaselineGrid.getFooterRow(0).getCell("detailId").setText(String.format(FOOTER_LABEL, size));
                return size;
            });
        udmBaselineGrid = new Grid<>(dataProvider);
        addColumns();
        initSelectionMode();
        udmBaselineGrid.setSizeFull();
        initViewWindow();
        VaadinUtils.addComponentStyle(udmBaselineGrid, "udm-baseline-grid");
    }

    private void addColumns() {
        FooterRow footer = udmBaselineGrid.appendFooterRow();
        udmBaselineGrid.setFooterVisible(true);
        Column<UdmBaselineDto, ?> column = udmBaselineGrid.addColumn(UdmBaselineDto::getId)
            .setCaption(ForeignUi.getMessage("table.column.detail_id"))
            .setId("detailId")
            .setSortProperty("detailId")
            .setWidth(250);
        footer.getCell(column).setText(String.format(FOOTER_LABEL, 0));
        footer.join(
            addColumn(UdmBaselineDto::getPeriod, "table.column.period", "period", 100),
            addColumn(UdmBaselineDto::getUsageOrigin, "table.column.usage_origin", "usageOrigin", 100),
            addColumn(UdmBaselineDto::getOriginalDetailId, "table.column.usage_detail_id", "usageDetailId", 130),
            addColumn(UdmBaselineDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100),
            addColumn(UdmBaselineDto::getSystemTitle, "table.column.system_title", "systemTitle", 300),
            addColumn(UdmBaselineDto::getDetailLicenseeClassId, "table.column.det_lc_id", "detLcId", 100),
            addColumn(UdmBaselineDto::getDetailLicenseeClassName, "table.column.det_lc_name", "detLcName", 250),
            addColumn(UdmBaselineDto::getAggregateLicenseeClassId, "table.column.aggregate_licensee_class_id",
                "aggLcId", 100),
            addColumn(UdmBaselineDto::getAggregateLicenseeClassName, "table.column.aggregate_licensee_class_name",
                "aggLcName", 100),
            addColumn(UdmBaselineDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", 120),
            addColumn(UdmBaselineDto::getChannel, "table.column.channel", "channel", 100),
            addColumn(UdmBaselineDto::getReportedTypeOfUse, "table.column.reported_tou", "reportedTypeOfUse", 120),
            addColumn(UdmBaselineDto::getTypeOfUse, "table.column.tou", "typeOfUse", 120),
            addAnnualizedCopiesColumn(UdmBaselineDto::getAnnualizedCopies),
            addColumn(UdmBaselineDto::getCreateUser, "table.column.created_by", "createUser", 200),
            addColumn(u -> getStringFromDate(u.getCreateDate()), "table.column.created_date", "createDate", 110),
            addColumn(UdmBaselineDto::getUpdateUser, "table.column.updated_by", "updateUser", 150),
            addColumn(u -> getStringFromDate(u.getUpdateDate()), "table.column.updated_date", "updateDate", 110));
    }

    private Column<UdmBaselineDto, ?> addColumn(ValueProvider<UdmBaselineDto, ?> valueProvider, String captionProperty,
                                                String columnId, double width) {
        return udmBaselineGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private Column<UdmBaselineDto, ?> addAnnualizedCopiesColumn(Function<UdmBaselineDto, BigDecimal> function) {
        return udmBaselineGrid.addColumn(value -> BigDecimalUtils.formatCurrencyForGrid(function.apply(value)))
            .setStyleGenerator(item -> "v-align-right")
            .setCaption(ForeignUi.getMessage("table.column.annualized_copies"))
            .setId("annualizedCopies")
            .setSortable(true)
            .setSortProperty("annualizedCopies")
            .setHidable(true)
            .setWidth(130);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? FastDateFormat.getInstance(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT).format(date)
            : StringUtils.EMPTY;
    }

    private HorizontalLayout initButtonsLayout() {
        deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
        deleteButton.addClickListener(event -> showConfirmationWindow());
        deleteButton.setEnabled(false);
        VaadinUtils.setButtonsAutoDisabled(deleteButton);
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(controller.getExportUdmBaselineUsagesStreamSource().getSource());
        fileDownloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(deleteButton, exportButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "udm-baseline-buttons");
        return layout;
    }

    private void initViewWindow() {
        udmBaselineGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                UdmBaselineDto udmBaselineDto = event.getItem();
                ViewBaselineWindow window = new ViewBaselineWindow(udmBaselineDto);
                window.addCloseListener(closeEvent -> udmBaselineGrid.deselect(udmBaselineDto));
                Windows.showModalWindow(window);
                udmBaselineGrid.select(udmBaselineDto);
            }
        });
    }

    private void initSelectionMode() {
        if (hasSpecialistPermission) {
            gridSelectionModel = (MultiSelectionModelImpl<UdmBaselineDto>)
                udmBaselineGrid.setSelectionMode(Grid.SelectionMode.MULTI);
            udmBaselineGrid.addSelectionListener(event ->
                deleteButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        } else {
            udmBaselineGrid.setSelectionMode(SelectionMode.SINGLE);
        }
    }

    private void switchSelectAllCheckBoxVisibility(int beansCount) {
        if (hasSpecialistPermission) {
            gridSelectionModel.setSelectAllCheckBoxVisibility(
                0 == beansCount || beansCount > controller.getUdmRecordThreshold()
                    ? MultiSelectionModel.SelectAllCheckBoxVisibility.HIDDEN
                    : MultiSelectionModel.SelectAllCheckBoxVisibility.VISIBLE);
            gridSelectionModel.beforeClientResponse(false);
        }
    }

    private void showConfirmationWindow() {
        Windows.showConfirmDialogWithReason(
            ForeignUi.getMessage("window.confirm"),
            ForeignUi.getMessage("message.confirm.remove_from_baseline"),
            ForeignUi.getMessage("button.yes"),
            ForeignUi.getMessage("button.cancel"),
            reason -> {
                Set<String> usageIds = udmBaselineGrid.getSelectedItems()
                    .stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toSet());
                controller.deleteFromBaseline(usageIds, reason);
                refresh();
            },
            Arrays.asList(new RequiredValidator(),
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1000), 0, 1000)));
    }
}
