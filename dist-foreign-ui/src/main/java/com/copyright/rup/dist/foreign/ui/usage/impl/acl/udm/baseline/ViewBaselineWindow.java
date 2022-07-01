package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.Objects;

/**
 * Window to view UDM baseline.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/15/21
 *
 * @author Anton Azarenka
 */
public class ViewBaselineWindow extends Window implements IDateFormatter {

    private final UdmBaselineDto udmBaselineDto;
    private final Binder<UdmBaselineDto> binder = new Binder<>();

    /**
     * Constructor.
     *
     * @param selectedBaseline UDM baseline to be displayed on the window
     */
    public ViewBaselineWindow(UdmBaselineDto selectedBaseline) {
        udmBaselineDto = selectedBaseline;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.view_udm_baseline"));
        setResizable(false);
        setWidth(500, Unit.PIXELS);
        setHeight(700, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "view-udm-baseline-window");
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        HorizontalLayout buttonsLayout = new HorizontalLayout(Buttons.createCloseButton(this));
        rootLayout.addComponents(
            buildReadOnlyLayout("label.detail_id", UdmBaselineDto::getId),
            buildReadOnlyLayout("label.period", baseline -> Objects.toString(baseline.getPeriod())),
            buildReadOnlyLayout("label.usage_origin", baseline -> baseline.getUsageOrigin().name()),
            buildReadOnlyLayout("label.usage_detail_id", UdmBaselineDto::getOriginalDetailId),
            buildReadOnlyLayout("label.wr_wrk_inst", baseline -> Objects.toString(baseline.getWrWrkInst())),
            buildReadOnlyLayout("label.system_title", UdmBaselineDto::getSystemTitle),
            buildReadOnlyLayout("label.det_lc", baseline ->
                String.format("%s - %s", baseline.getDetailLicenseeClassId(), baseline.getDetailLicenseeClassName())),
            buildReadOnlyLayout("label.aggregate_licensee_class",
                baseline -> String.format("%s - %s", baseline.getAggregateLicenseeClassId(),
                    baseline.getAggregateLicenseeClassName())),
            buildReadOnlyLayout("label.survey_country", UdmBaselineDto::getSurveyCountry),
            buildReadOnlyLayout("label.channel", baseline -> baseline.getChannel().name()),
            buildReadOnlyLayout("label.tou", UdmBaselineDto::getTypeOfUse),
            buildReadOnlyLayout(
                "label.annualized_copies", baseline -> Objects.toString(baseline.getAnnualizedCopies())),
            buildReadOnlyLayout("label.created_by", UdmBaselineDto::getCreateUser),
            buildReadOnlyLayout("label.created_date", baseline -> toShortFormat(baseline.getCreateDate())),
            buildReadOnlyLayout("label.updated_by", UdmBaselineDto::getUpdateUser),
            buildReadOnlyLayout("label.updated_date", baseline -> toShortFormat(baseline.getUpdateDate())),
            buttonsLayout
        );
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setSizeFull();
        binder.readBean(udmBaselineDto);
        binder.validate();
        return rootLayout;
    }

    private HorizontalLayout buildReadOnlyLayout(String caption, ValueProvider<UdmBaselineDto, String> getter) {
        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.setSizeFull();
        binder.forField(textField).bind(getter, null);
        return buildCommonLayout(textField, ForeignUi.getMessage(caption));
    }

    private HorizontalLayout buildCommonLayout(Component component, String labelCaption) {
        Label label = new Label(labelCaption);
        label.addStyleName(Cornerstone.LABEL_BOLD);
        label.setWidth(165, Unit.PIXELS);
        HorizontalLayout layout = new HorizontalLayout(label, component);
        layout.setSizeFull();
        layout.setExpandRatio(component, 1);
        return layout;
    }
}
