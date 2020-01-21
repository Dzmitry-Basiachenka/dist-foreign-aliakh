package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.NotificationWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;

/**
 * Usage widget for AACL product families.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
public class AaclUsageWidget extends CommonUsageWidget implements IAaclUsageWidget {

    private MenuBar usageBatchMenuBar;
    private MenuBar.MenuItem loadUsageBatchMenuItem;
    private Button sendForClassificationButton;
    private final IAaclUsageController controller;

    /**
     * Controller.
     *
     * @param controller {@link IAaclUsageController} instance
     */
    AaclUsageWidget(IAaclUsageController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        AaclUsageMediator mediator = new AaclUsageMediator();
        mediator.setLoadUsageBatchMenuItem(loadUsageBatchMenuItem);
        mediator.setSendForClassificationButton(sendForClassificationButton);
        return mediator;
    }

    @Override
    protected void addGridColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", true, 115);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 145);
        addColumn(usage -> CommonDateUtils.format(usage.getAaclUsage().getBatchPeriodEndDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.period_end_date", "paymentDate", true, 115);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", true, 115);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", true, 300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 140);
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", true,
            155);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass(), "table.column.detail_licensee_class_id",
            true, 140);
        addColumn(usage -> usage.getAaclUsage().getEnrollmentProfile(), "table.column.enrollment_profile",
            true, 140);
        addColumn(usage -> usage.getAaclUsage().getDiscipline(), "table.column.discipline",
            true, 140);
        addColumn(usage -> usage.getAaclUsage().getPublicationType(), "table.column.publication_type",
            true, 140);
        addColumn(usage -> usage.getAaclUsage().getInstitution(), "table.column.institution", "institution",
            true, 140);
        addColumn(usage -> usage.getAaclUsage().getUsagePeriod(), "table.column.usage_period", "usagePeriod",
            true, 140);
        addColumn(usage -> usage.getAaclUsage().getUsageSource(), "table.column.usage_source", "usageSource",
            true, 140);
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies", true, 140);
        addColumn(usage -> usage.getAaclUsage().getNumberOfPages(), "table.column.number_of_pages",
            "numberOfPages", true, 140);
        addColumn(usage -> usage.getAaclUsage().getRightLimitation(), "table.column.right_limitation",
            "rightLimitation", true, 200);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 200);
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        initUsageBatchMenuBar();
        initSendForClassificationButton();
        HorizontalLayout layout = new HorizontalLayout(usageBatchMenuBar, sendForClassificationButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "usages-buttons");
        return layout;
    }

    private void initUsageBatchMenuBar() {
        usageBatchMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem =
            usageBatchMenuBar.addItem(ForeignUi.getMessage("menu.caption.usage_batch"), null, null);
        loadUsageBatchMenuItem = menuItem.addItem(ForeignUi.getMessage("menu.item.load"), null,
            item -> Windows.showModalWindow(new AaclUsageBatchUploadWindow(controller)));
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "usage-batch-menu-bar");
        VaadinUtils.addComponentStyle(usageBatchMenuBar, "v-menubar-df");
    }

    private void initSendForClassificationButton() {
        sendForClassificationButton = Buttons.createButton(ForeignUi.getMessage("button.send_for_classification"));
        SendForClassificationFileDownloader sendForClassificationDownloader =
            new SendForClassificationFileDownloader(controller);
        sendForClassificationDownloader.extend(sendForClassificationButton);
        // Click listener and second isValidUsagesState() call were added due to problem with
        // modal window appearance in chrome browser
        sendForClassificationButton.addClickListener(event -> {
            if (!controller.isValidUsagesState(UsageStatusEnum.RH_FOUND)) {
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.error.invalid_usages_status", UsageStatusEnum.RH_FOUND,
                        "sent for classification"));
            } else {
                NotificationWindow window =
                    new NotificationWindow(ForeignUi.getMessage("message.download_in_progress"));
                window.addCloseListener(closeEvent -> controller.clearFilter());
                Windows.showModalWindow(window);
            }
        });
    }

    private static class SendForClassificationFileDownloader extends OnDemandFileDownloader {

        private final IAaclUsageController controller;

        /**
         * Controller.
         *
         * @param controller instance of {@link IAaclUsageController}
         */
        SendForClassificationFileDownloader(IAaclUsageController controller) {
            super(controller.getSendForClassificationUsagesStreamSource().getSource());
            this.controller = controller;
        }

        @Override
        public boolean handleConnectorRequest(VaadinRequest request, VaadinResponse response, String path) {
            return controller.isValidUsagesState(UsageStatusEnum.RH_FOUND)
                && super.handleConnectorRequest(request, response, path);
        }
    }
}
