package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Function;

/**
 * Contains common functionality for UDM usage window.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/24/2021
 *
 * @author Dzmitry Basiachenka
 */
public abstract class CommonUdmUsageWindow extends Window {

    private static final long serialVersionUID = -174850047266705547L;

    /**
     * Builds read only horizontal layout.
     *
     * @param caption caption value
     * @param getter  value provider
     * @param binder  binder
     * @return instance of {@link HorizontalLayout}
     */
    protected HorizontalLayout buildReadOnlyLayout(String caption, ValueProvider<UdmUsageDto, String> getter,
                                                   Binder<UdmUsageDto> binder) {
        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.setSizeFull();
        binder.forField(textField).bind(getter, null);
        return buildCommonLayout(textField, ForeignUi.getMessage(caption));
    }

    /**
     * Builds common horizontal layout.
     *
     * @param component    instance of {@link Component}
     * @param labelCaption label caption value
     * @return instance of {@link HorizontalLayout}
     */
    protected HorizontalLayout buildCommonLayout(Component component, String labelCaption) {
        Label label = new Label(labelCaption);
        label.addStyleName(Cornerstone.LABEL_BOLD);
        label.setWidth(165, Unit.PIXELS);
        HorizontalLayout layout = new HorizontalLayout(label, component);
        layout.setSizeFull();
        layout.setExpandRatio(component, 1);
        return layout;
    }

    /**
     * Gets a value provider that returns the name of the enum.
     *
     * @param getter bean getter
     * @return instance of {@code ValueProvider}
     */
    protected ValueProvider<UdmUsageDto, String> buildEnumNameValueProvider(Function<UdmUsageDto, Enum<?>> getter) {
        return usage -> getter.apply(usage).name();
    }

    /**
     * @return action reason value provider.
     */
    protected ValueProvider<UdmUsageDto, String> buildActionReasonValueProvider() {
        return usage ->
            Objects.nonNull(usage.getActionReason()) ? usage.getActionReason().getReason() : StringUtils.EMPTY;
    }

    /**
     * @return ineligible reason value provider.
     */
    protected ValueProvider<UdmUsageDto, String> buildIneligibleReasonValueProvider() {
        return usage ->
            Objects.nonNull(usage.getIneligibleReason()) ? usage.getIneligibleReason().getReason() : StringUtils.EMPTY;
    }

    /**
     * @return detail licensee class value provider.
     */
    protected ValueProvider<UdmUsageDto, String> buildDetailLicenseeClassValueProvider() {
        return usage -> String.format("%s - %s", usage.getDetailLicenseeClass().getId(),
            usage.getDetailLicenseeClass().getDescription());
    }
}
