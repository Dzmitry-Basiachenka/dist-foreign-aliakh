package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Usage widget for SAL product families.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalUsageWidget extends CommonUsageWidget implements ISalUsageWidget {

    private static final int EXPECTED_BATCH_SIZE = 1;
    private final ISalUsageController controller;
    private MenuBar usageBatchMenuBar;
    private MenuBar fundPoolMenuBar;
    private MenuBar.MenuItem loadItemBankMenuItem;
    private MenuBar.MenuItem loadUsageDataMenuItem;
    private MenuBar.MenuItem loadFundPoolMenuItem;
    private Button updateRightsholdersButton;
    private Button addToScenarioButton;
    private Button exportButton;

    /**
     * Controller.
     *
     * @param controller {@link ISalUsageController} instance
     */
    SalUsageWidget(ISalUsageController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        SalUsageMediator mediator = new SalUsageMediator();
        mediator.setLoadItemBankMenuItem(loadItemBankMenuItem);
        mediator.setLoadUsageDataMenuItem(loadUsageDataMenuItem);
        mediator.setLoadFundPoolMenuItem(loadFundPoolMenuItem);
        mediator.setUpdateRightsholdersButton(updateRightsholdersButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
        return mediator;
    }

    @Override
    protected void addGridColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", true, 115);
        addColumn(usageDto -> usageDto.getSalUsage().getDetailType(), "table.column.detail_type", "detailType", true,
            115);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 145);
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getPeriodEndDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.period_end_date", "periodEndDate", true, 115);
        addColumn(usage -> usage.getSalUsage().getLicenseeAccountNumber(), "table.column.licensee_account_number",
            "licenseeAccountNumber", true, 150);
        addColumn(usage -> usage.getSalUsage().getLicenseeName(), "table.column.licensee_name", "licenseeName", true,
            300);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", true, 115);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", true, 300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 140);
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", true,
            155);
        addColumn(usageDto -> usageDto.getSalUsage().getAssessmentName(), "table.column.assessment_name",
            "assessmentName", true, 180);
        addColumn(usageDto -> usageDto.getSalUsage().getAssessmentType(), "table.column.assessment_type",
            "assessmentType", true, 150);
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getSalUsage().getScoredAssessmentDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.scored_assessment_date", "scoredAssessmentDate",
            true, 200);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedWorkPortionId(),
            "table.column.reported_work_portion_id", "reportedWorkPortionId", true, 180);
        addColumn(UsageDto::getWorkTitle, "table.column.reported_title", "workTitle", true, 170);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedArticle(), "table.column.reported_article",
            "reportedArticle", true, 240);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedStandardNumber(),
            "table.column.reported_standard_number", "reportedStandardNumber", true, 200);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedAuthor(), "table.column.reported_author",
            "reportedAuthor", true, 150);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedPublisher(), "table.column.reported_publisher",
            "reportedPublisher", true, 150);
        addColumn(usageDto -> CommonDateUtils.format(usageDto.getSalUsage().getReportedPublicationDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.reported_publication_date",
            "reportedPublicationDate", true, 200);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedPageRange(), "table.column.reported_page_range",
            "reportedPageRange", true, 150);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedVolNumberSeries(),
            "table.column.reported_vol_number_series", "reportedVolNumberSeries", true, 200);
        addColumn(usageDto -> usageDto.getSalUsage().getReportedMediaType(), "table.column.reported_media_type",
            "reportedMediaType", true, 150);
        addColumn(usageDto -> usageDto.getSalUsage().getCoverageYear(), "table.column.coverage_year", "coverageYear",
            true, 115);
        addColumn(usageDto -> usageDto.getSalUsage().getQuestionIdentifier(), "table.column.question_identifier",
            "questionIdentifier", true, 150);
        addColumn(usageDto -> usageDto.getSalUsage().getGrade(), "table.column.grade", "grade", true, 115);
        addColumn(usageDto -> usageDto.getSalUsage().getGradeGroup(), "table.column.grade_group", "gradeGroup", true,
            115);
        addColumn(usageDto -> usageDto.getSalUsage().getStates(), "table.column.states", "states", true, 115);
        addColumn(usageDto -> usageDto.getSalUsage().getNumberOfViews(), "table.column.number_of_views",
            "numberOfViews", true, 150);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 115);
    }

    @Override
    protected String getProductFamilySpecificScenarioValidationMessage() {
        String message;
        UsageFilter appliedFilter = getFilterWidget().getAppliedFilter();
        Set<String> batchesIds = appliedFilter.getUsageBatchesIds();
        if (CollectionUtils.isEmpty(batchesIds)) {
            message = ForeignUi.getMessage("message.error.empty_usage_batches");
        } else if (EXPECTED_BATCH_SIZE != CollectionUtils.size(batchesIds)) {
            message = ForeignUi.getMessage("message.error.invalid_batch_size");
        } else if (Objects.nonNull(appliedFilter.getSalDetailType())) {
            message = ForeignUi.getMessage("message.error.invalid_detail_type_filter");
        } else {
            message = validateSelectedBatch(batchesIds);
        }
        return message;
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        initUsageBatchMenuBar();
        initFundPoolMenuBar();
        initUpdateRightsholdersButton();
        initAddToScenarioButton();
        initExportButton();
        VaadinUtils.setButtonsAutoDisabled(updateRightsholdersButton, addToScenarioButton);
        HorizontalLayout layout =
            new HorizontalLayout(usageBatchMenuBar, fundPoolMenuBar, updateRightsholdersButton, addToScenarioButton,
                exportButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "usages-buttons");
        return layout;
    }

    private void initUsageBatchMenuBar() {
        usageBatchMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            usageBatchMenuBar.addItem(ForeignUi.getMessage("menu.caption.usage_batch"), null, null);
        loadItemBankMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.load.item_bank"), null,
            item -> Windows.showModalWindow(new ItemBankUploadWindow(controller)));
        loadUsageDataMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.load.usage_data"), null,
            item -> Windows.showModalWindow(new UsageDataUploadWindow(controller)));
        menuItem.addItem(ForeignUi.getMessage("menu.item.view"), null,
            item -> Windows.showModalWindow(new ViewSalUsageBatchWindow(controller)));
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "usage-batch-menu-bar");
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "v-menubar-df");
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "v-menubar-df-sal");
    }

    private void initFundPoolMenuBar() {
        fundPoolMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            fundPoolMenuBar.addItem(ForeignUi.getMessage("menu.caption.fund_pool"), null, null);
        loadFundPoolMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.load"), null,
            item -> Windows.showModalWindow(new SalFundPoolLoadWindow(controller)));
        menuItem.addItem(ForeignUi.getMessage("menu.item.view"), null,
            item -> Windows.showModalWindow(new ViewSalFundPoolWindow(controller)));
        VaadinUtils.addComponentStyle(fundPoolMenuBar, "fund-pool-menu-bar");
        VaadinUtils.addComponentStyle(fundPoolMenuBar, "v-menubar-df");
        VaadinUtils.addComponentStyle(fundPoolMenuBar, "v-menubar-df-sal");
    }

    private void initUpdateRightsholdersButton() {
        updateRightsholdersButton = Buttons.createButton(ForeignUi.getMessage("button.update_rightsholders"));
        updateRightsholdersButton.addClickListener(event -> onUpdateRightsholdersButtonClicked());
    }

    private void initAddToScenarioButton() {
        addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        addToScenarioButton.addClickListener(event -> onAddToScenarioClicked(new CreateSalScenarioWindow(controller)));
    }

    private String validateSelectedBatch(Set<String> batchesIds) {
        String message = null;
        List<String> processingBatchesNames = controller.getProcessingBatchesNames(batchesIds);
        if (CollectionUtils.isNotEmpty(processingBatchesNames)) {
            message = ForeignUi.getMessage("message.error.processing_batches_names",
                processingBatchesNames.iterator().next());
        } else {
            List<String> ineligibleBatchNames = controller.getIneligibleBatchesNames(batchesIds);
            if (CollectionUtils.isNotEmpty(ineligibleBatchNames)) {
                message = ForeignUi.getMessage("message.error.batches_with_non_eligible_usages",
                    ineligibleBatchNames.iterator().next());
            }
        }
        return message;
    }

    private void initExportButton() {
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(controller.getExportUsagesStreamSource().getSource());
        fileDownloader.extend(exportButton);
    }

    private void onUpdateRightsholdersButtonClicked() {
        String message = null;
        if (0 == controller.getBeansCount()) {
            message = ForeignUi.getMessage("message.error.update_rightsholders.empty_usages");
        } else if (!controller.areValidFilteredUsageStatuses(UsageStatusEnum.RH_NOT_FOUND,
            UsageStatusEnum.WORK_NOT_GRANTED)) {
            message = ForeignUi.getMessage("message.error.invalid_usages_status", "RH_NOT_FOUND or WORK_NOT_GRANTED",
                "updated");
        }
        if (Objects.isNull(message)) {
            Windows.showModalWindow(new SalDetailForRightsholderUpdateWindow(controller));
        } else {
            Windows.showNotificationWindow(message);
        }
    }
}
