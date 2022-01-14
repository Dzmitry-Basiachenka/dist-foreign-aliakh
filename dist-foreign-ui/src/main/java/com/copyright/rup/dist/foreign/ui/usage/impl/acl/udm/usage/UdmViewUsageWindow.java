package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.ui.common.utils.DateUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmUsageWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Window to view UDM usage.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/24/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmViewUsageWindow extends CommonUdmUsageWindow {

    private final Binder<UdmUsageDto> binder = new Binder<>();
    private final UdmUsageDto udmUsage;

    /**
     * Constructor.
     *
     * @param selectedUdmUsage UDM usage to be displayed on the window
     */
    public UdmViewUsageWindow(UdmUsageDto selectedUdmUsage) {
        udmUsage = selectedUdmUsage;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.view_udm_usage"));
        setResizable(false);
        setWidth(650, Unit.PIXELS);
        setHeight(700, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "view-udm-usage-window");
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        VerticalLayout fieldsLayout = new VerticalLayout();
        fieldsLayout.addComponents(ForeignSecurityUtils.hasResearcherPermission()
            ? getComponentsForResearcher()
            : getComponentsForViewOnly());
        Panel panel = new Panel(fieldsLayout);
        panel.setSizeFull();
        fieldsLayout.setMargin(new MarginInfo(true));
        HorizontalLayout buttonsLayout = new HorizontalLayout(Buttons.createCloseButton(this));
        rootLayout.addComponents(panel, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(panel, 1f);
        rootLayout.setSizeFull();
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        binder.readBean(udmUsage);
        binder.validate();
        return rootLayout;
    }

    private Component[] getComponentsForResearcher() {
        return new Component[]{
            buildReadOnlyLayout("label.detail_id", UdmUsageDto::getId, binder),
            buildReadOnlyLayout("label.period", usage -> Objects.toString(usage.getPeriod()), binder),
            buildReadOnlyLayout("label.usage_detail_id", UdmUsageDto::getOriginalDetailId, binder),
            buildReadOnlyLayout("label.detail_status", usage -> usage.getStatus().name(), binder),
            buildReadOnlyLayout("label.assignee", UdmUsageDto::getAssignee, binder),
            buildReadOnlyLayout("label.rh_account_number",
                usage -> Objects.toString(usage.getRhAccountNumber(), StringUtils.EMPTY), binder),
            buildReadOnlyLayout("label.rh_name", UdmUsageDto::getRhName, binder),
            buildReadOnlyLayout("label.wr_wrk_inst",
                usage -> Objects.toString(usage.getWrWrkInst(), StringUtils.EMPTY), binder),
            buildReadOnlyLayout("label.reported_title", UdmUsageDto::getReportedTitle, binder),
            buildReadOnlyLayout("label.system_title", UdmUsageDto::getSystemTitle, binder),
            buildReadOnlyLayout("label.reported_standard_number", UdmUsageDto::getReportedStandardNumber, binder),
            buildReadOnlyLayout("label.standard_number", UdmUsageDto::getStandardNumber, binder),
            buildReadOnlyLayout("label.reported_pub_type", UdmUsageDto::getReportedPubType, binder),
            buildReadOnlyLayout("label.publication_format", UdmUsageDto::getPubFormat, binder),
            buildReadOnlyLayout("label.article", UdmUsageDto::getArticle, binder),
            buildReadOnlyLayout("label.language", UdmUsageDto::getLanguage, binder),
            buildReadOnlyLayout("label.action_reason_udm", buildActionReasonValueProvider(), binder),
            buildReadOnlyLayout("label.comment", UdmUsageDto::getComment, binder),
            buildReadOnlyLayout("label.research_url", UdmUsageDto::getResearchUrl, binder),
            buildReadOnlyLayout("label.det_lc", buildDetailLicenseeClassValueProvider(), binder),
            buildReadOnlyLayout("label.channel", usage -> usage.getChannel().name(), binder),
            buildReadOnlyLayout("label.usage_date", usage -> DateUtils.format(usage.getUsageDate()), binder),
            buildReadOnlyLayout("label.survey_start_date",
                usage -> DateUtils.format(usage.getSurveyStartDate()), binder),
            buildReadOnlyLayout("label.survey_end_date", usage -> DateUtils.format(usage.getSurveyEndDate()), binder),
            buildReadOnlyLayout("label.reported_tou", UdmUsageDto::getReportedTypeOfUse, binder),
            buildReadOnlyLayout("label.ineligible_reason", buildIneligibleReasonValueProvider(), binder),
            buildReadOnlyLayout("label.load_date", usage -> DateUtils.format(usage.getCreateDate()), binder),
            buildReadOnlyLayout("label.updated_by", UdmUsageDto::getUpdateUser, binder),
            buildReadOnlyLayout("label.updated_date", usage -> DateUtils.format(usage.getUpdateDate()), binder)
        };
    }

    private Component[] getComponentsForViewOnly() {
        return new Component[]{
            buildReadOnlyLayout("label.detail_id", UdmUsageDto::getId, binder),
            buildReadOnlyLayout("label.period", usage -> Objects.toString(usage.getPeriod()), binder),
            buildReadOnlyLayout("label.usage_origin", usage -> usage.getUsageOrigin().name(), binder),
            buildReadOnlyLayout("label.usage_detail_id", UdmUsageDto::getOriginalDetailId, binder),
            buildReadOnlyLayout("label.detail_status", usage -> usage.getStatus().name(), binder),
            buildReadOnlyLayout("label.assignee", UdmUsageDto::getAssignee, binder),
            buildReadOnlyLayout("label.rh_account_number",
                usage -> Objects.toString(usage.getRhAccountNumber(), StringUtils.EMPTY), binder),
            buildReadOnlyLayout("label.rh_name", UdmUsageDto::getRhName, binder),
            buildReadOnlyLayout("label.wr_wrk_inst",
                usage -> Objects.toString(usage.getWrWrkInst(), StringUtils.EMPTY), binder),
            buildReadOnlyLayout("label.reported_title", UdmUsageDto::getReportedTitle, binder),
            buildReadOnlyLayout("label.system_title", UdmUsageDto::getSystemTitle, binder),
            buildReadOnlyLayout("label.reported_standard_number", UdmUsageDto::getReportedStandardNumber, binder),
            buildReadOnlyLayout("label.standard_number", UdmUsageDto::getStandardNumber, binder),
            buildReadOnlyLayout("label.reported_pub_type", UdmUsageDto::getReportedPubType, binder),
            buildReadOnlyLayout("label.publication_format", UdmUsageDto::getPubFormat, binder),
            buildReadOnlyLayout("label.article", UdmUsageDto::getArticle, binder),
            buildReadOnlyLayout("label.language", UdmUsageDto::getLanguage, binder),
            buildReadOnlyLayout("label.action_reason_udm", buildActionReasonValueProvider(), binder),
            buildReadOnlyLayout("label.comment", UdmUsageDto::getComment, binder),
            buildReadOnlyLayout("label.research_url", UdmUsageDto::getResearchUrl, binder),
            buildReadOnlyLayout("label.company_id", usage -> Objects.toString(usage.getCompanyId(), StringUtils.EMPTY),
                binder),
            buildReadOnlyLayout("label.company_name", UdmUsageDto::getCompanyName, binder),
            buildReadOnlyLayout("label.det_lc", buildDetailLicenseeClassValueProvider(), binder),
            buildReadOnlyLayout("label.survey_respondent", UdmUsageDto::getSurveyRespondent, binder),
            buildReadOnlyLayout("label.ip_address", UdmUsageDto::getIpAddress, binder),
            buildReadOnlyLayout("label.survey_country", UdmUsageDto::getSurveyCountry, binder),
            buildReadOnlyLayout("label.channel", usage -> usage.getChannel().name(), binder),
            buildReadOnlyLayout("label.usage_date", usage -> DateUtils.format(usage.getUsageDate()), binder),
            buildReadOnlyLayout("label.survey_start_date", usage -> DateUtils.format(usage.getSurveyStartDate()),
                binder),
            buildReadOnlyLayout("label.survey_end_date", usage -> DateUtils.format(usage.getSurveyEndDate()), binder),
            buildReadOnlyLayout("label.annual_multiplier",
                usage -> Objects.toString(usage.getAnnualMultiplier(), StringUtils.EMPTY), binder),
            buildReadOnlyLayout("label.statistical_multiplier",
                usage -> Objects.toString(usage.getStatisticalMultiplier(), StringUtils.EMPTY), binder),
            buildReadOnlyLayout("label.reported_tou", UdmUsageDto::getReportedTypeOfUse, binder),
            buildReadOnlyLayout("label.quantity", usage -> Objects.toString(usage.getQuantity(), StringUtils.EMPTY),
                binder),
            buildReadOnlyLayout("label.annualized_copies",
                usage -> Objects.toString(usage.getAnnualizedCopies(), StringUtils.EMPTY), binder),
            buildReadOnlyLayout("label.ineligible_reason", buildIneligibleReasonValueProvider(), binder),
            buildReadOnlyLayout("label.load_date", usage -> DateUtils.format(usage.getCreateDate()), binder),
            buildReadOnlyLayout("label.updated_by", UdmUsageDto::getUpdateUser, binder),
            buildReadOnlyLayout("label.updated_date", usage -> DateUtils.format(usage.getUpdateDate()), binder)
        };
    }
}
