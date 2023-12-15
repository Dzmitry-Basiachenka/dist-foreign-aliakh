package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Modal window that provides information about errors during uploading file.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 */
public class ErrorUploadWindow extends Window {

    private static final long serialVersionUID = 1560651189615413754L;

    /**
     * Constructor.
     *
     * @param streamSource {@link IStreamSource}
     * @param labelCaption label caption
     */
    public ErrorUploadWindow(IStreamSource streamSource, String labelCaption) {
        super.setCaption(ForeignUi.getMessage("window.error"));
        super.setWidth(365, Unit.PIXELS);
        super.setHeight(150, Unit.PIXELS);
        super.setResizable(false);
        HorizontalLayout buttonsLayout = buildButtonsLayout(streamSource);
        var label = new Label(labelCaption, ContentMode.HTML);
        var layout = new VerticalLayout(label, buttonsLayout);
        layout.setMargin(true);
        layout.setSizeFull();
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        super.setContent(layout);
        VaadinUtils.addComponentStyle(this, "upload-error-window");
    }

    private HorizontalLayout buildButtonsLayout(IStreamSource streamSource) {
        var downloadButton = Buttons.createButton(ForeignUi.getMessage("button.download"));
        var fileDownloader = new OnDemandFileDownloader(streamSource.getSource());
        fileDownloader.extend(downloadButton);
        var closeButton = Buttons.createCloseButton(this);
        var buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(downloadButton, closeButton);
        return buttonsLayout;
    }
}
