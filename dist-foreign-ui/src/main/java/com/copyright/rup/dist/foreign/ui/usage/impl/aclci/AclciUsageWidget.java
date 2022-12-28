package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;

import java.util.Objects;

/**
 * Implementation of {@link IAclciUsageWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsageWidget extends CommonUsageWidget implements IAclciUsageWidget {

    private final IAclciUsageController controller;
    private MenuBar usageBatchMenuBar;
    private MenuBar.MenuItem loadUsageBatchMenuItem;
    private Button updateUsagesButton;

    /**
     * Controller.
     *
     * @param controller {@link IAclciUsageController} instance
     */
    AclciUsageWidget(IAclciUsageController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        AclciUsageMediator mediator = new AclciUsageMediator();
        mediator.setLoadUsageBatchMenuItem(loadUsageBatchMenuItem);
        return mediator;
    }

    @Override
    protected void addGridColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 200);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", true, 165);
        addColumn(usage -> usage.getAclciUsage().getLicenseType(), "table.column.license_type",
            "licenseType", true, 150);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 200);
        addColumn(usage -> CommonDateUtils.format(usage.getPeriodEndDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.period_end_date", "periodEndDate", true, 115);
        addColumn(usage -> usage.getAclciUsage().getCoveragePeriod(), "table.column.coverage_period",
            "coveragePeriod", true, 130);
        addColumn(usage -> usage.getAclciUsage().getLicenseeAccountNumber(), "table.column.licensee_account_number",
            "licenseeAccountNumber", true, 150);
        addColumn(usage -> usage.getAclciUsage().getLicenseeName(), "table.column.licensee_name",
            "licenseeName", true, 300);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", true, 115);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", true, 300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 210);
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type",
            "standardNumberType", true, 155);
        addColumn(UsageDto::getWorkTitle, "table.column.reported_title", "workTitle", true, 300);
        addColumn(usage -> usage.getAclciUsage().getReportedMediaType(), "table.column.reported_media_type",
            "reportedMediaType", true, 150);
        addColumn(usageDto -> usageDto.getAclciUsage().getMediaTypeWeight(), "table.column.media_type_weight",
            "mediaTypeWeight", true, 130);
        addColumn(usage -> usage.getAclciUsage().getReportedArticle(), "table.column.reported_article",
            "reportedArticle", true, 240);
        addColumn(usage -> usage.getAclciUsage().getReportedStandardNumber(),
            "table.column.reported_standard_number_or_image_id_number", "reportedStandardNumber", true, 315);
        addColumn(usage -> usage.getAclciUsage().getReportedAuthor(), "table.column.reported_author",
            "reportedAuthor", true, 150);
        addColumn(usage -> usage.getAclciUsage().getReportedPublisher(), "table.column.reported_publisher",
            "reportedPublisher", true, 150);
        addColumn(usage -> usage.getAclciUsage().getReportedPublicationDate(), "table.column.reported_publication_date",
            "reportedPublicationDate", true, 200);
        addColumn(usage -> usage.getAclciUsage().getReportedGrade(), "table.column.reported_grade",
            "reportedGrade", true, 120);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 115);
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        initUsageBatchMenuBar();
        initUpdateUsagesButton();
        VaadinUtils.setButtonsAutoDisabled(updateUsagesButton);
        HorizontalLayout layout = new HorizontalLayout(usageBatchMenuBar, updateUsagesButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "usages-buttons");
        return layout;
    }

    @Override
    protected String getProductFamilySpecificScenarioValidationMessage() {
        return null; //TODO: implement
    }

    private void initUsageBatchMenuBar() {
        usageBatchMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            usageBatchMenuBar.addItem(ForeignUi.getMessage("menu.caption.usage_batch"), null, null);
        loadUsageBatchMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.load"), null,
            item -> Windows.showModalWindow(new AclciUsageBatchUploadWindow(controller)));
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "usage-batch-menu-bar");
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "v-menubar-df");
    }

    private void initUpdateUsagesButton() {
        updateUsagesButton = Buttons.createButton(ForeignUi.getMessage("button.update_usages"));
        updateUsagesButton.addClickListener(event -> onUpdateUsagesButtonClicked());
    }

    private void onUpdateUsagesButtonClicked() {
        String message = null;
        if (!controller.isValidStatusFilterApplied()) {
            message = ForeignUi.getMessage("message.error.status_not_applied_or_incorrect",
                UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.WORK_NOT_GRANTED);
        } else if (0 == controller.getBeansCount()) {
            message = ForeignUi.getMessage("message.error.update.empty_usages");
        }
        //TODO {dbasiachenka} add check to open window to update usages
        if (!Objects.isNull(message)) {
            Windows.showNotificationWindow(message);
        }
    }
}
