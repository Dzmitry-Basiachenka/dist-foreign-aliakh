package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

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

    private Grid<String> udmUsagesGrid;

    @Override
    @SuppressWarnings("unchecked")
    public IUdmUsageWidget init() {
        setSplitPosition(200, Unit.PIXELS);
        setFirstComponent(initFiltersLayout());
        setSecondComponent(initUsagesLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void setController(IUdmUsageController controller) {
    }

    private VerticalLayout initFiltersLayout() {
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel());
        verticalLayout.setMargin(true);
        return verticalLayout;
    }

    //TODO {dbasiachenka} implement filter widget and move this label there
    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
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
        Button loadButton = Buttons.createButton(ForeignUi.getMessage("button.load"));
        HorizontalLayout layout = new HorizontalLayout(loadButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "udm-usages-buttons");
        return layout;
    }

    private void initUsagesGrid() {
        udmUsagesGrid = new Grid<>();
        addColumns();
        udmUsagesGrid.setSelectionMode(Grid.SelectionMode.NONE);
        udmUsagesGrid.setSizeFull();
        VaadinUtils.addComponentStyle(udmUsagesGrid, "udm-usages-grid");
    }

    private void addColumns() {
        FooterRow footer = udmUsagesGrid.appendFooterRow();
        udmUsagesGrid.setFooterVisible(true);
        footer.getCell(addColumn("table.column.detail_id", "detailId", false, 200)).setText("Usages Count: 0");
        footer.join(
            addColumn("table.column.period", "period", true, 100),
            addColumn("table.column.usage_origin", "usageOrigin", true, 100),
            addColumn("table.column.usage_detail_id", "usageDetailId", true, 130),
            addColumn("table.column.usage_status", "status", true, 100),
            addColumn("table.column.reported_title", "reportedTitle", true, 120),
            addColumn("table.column.system_title", "systemTitle", true, 100),
            addColumn("table.column.wr_wrk_inst", "wrWrkInst", true, 100),
            addColumn("table.column.reported_standard_number", "reportedStandardNumber", true, 190),
            addColumn("table.column.standard_number", "standardNumber", true, 150),
            addColumn("table.column.reported_pub_type", "reportedPubType", true, 150),
            addColumn("table.column.publication_format", "publicationFormat", true, 150),
            addColumn("table.column.article", "article", true, 100),
            addColumn("table.column.language", "language", true, 100),
            addColumn("table.column.det_lc_id", "detLcId", true, 100),
            addColumn("table.column.det_lc_name", "detLcName", true, 100),
            addColumn("table.column.company_id", "companyId", true, 100),
            addColumn("table.column.company_name", "companyName", true, 120),
            addColumn("table.column.survey_respondent", "surveyRespondent", true, 150),
            addColumn("table.column.ip_address", "ipAddress", true, 100),
            addColumn("table.column.survey_country", "surveyCountry", true, 120),
            addColumn("table.column.channel", "channel", true, 100),
            addColumn("table.column.usage_date", "usageDate", true, 100),
            addColumn("table.column.survey_start_date", "surveyStartDate", true, 130),
            addColumn("table.column.survey_end_date", "surveyEndDate", true, 130),
            addColumn("table.column.annual_multiplier", "annualMultiplier", true, 130),
            addColumn("table.column.statistical_multiplier", "statisticalMultiplier", true, 150),
            addColumn("table.column.reported_tou", "reportedTou", true, 120),
            addColumn("table.column.quantity", "quantity", true, 100),
            addColumn("table.column.annualized_copies", "annualizedCopies", true, 130),
            addColumn("table.column.ineligible_reason", "ineligibleReason", true, 130),
            addColumn("table.column.load_date", "loadDate", true, 100),
            addColumn("table.column.updated_by", "updateUser", true, 100),
            addColumn("table.column.updated_date", "updateDate", true, 110));
    }

    private Column<String, String> addColumn(String captionProperty, String columnId, boolean isHidable, double width) {
        return udmUsagesGrid.addColumn(val -> val)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(isHidable)
            .setWidth(width);
    }
}
