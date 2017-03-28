package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Modal window that provides information about errors during uploading file.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 */
public class ErrorUploadWindow extends Window {

    /**
     * Constructor.
     *
     * @param streamSource {@link IStreamSource}
     */
    ErrorUploadWindow(IStreamSource streamSource) {
        setCaption(ForeignUi.getMessage("window.caption.error"));
        setWidth(365, Unit.PIXELS);
        setHeight(150, Unit.PIXELS);
        setSizeFull();
        HorizontalLayout buttonsLayout = buildButtonsLayout(streamSource);
        Label label = new Label(ForeignUi.getMessage("label.upload_error"), ContentMode.HTML);
        VerticalLayout layout = new VerticalLayout(label, buttonsLayout);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "upload-error-window");
    }

    private HorizontalLayout buildButtonsLayout(IStreamSource streamSource) {
        Button downloadButton = Buttons.createButton("Download");
        OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader(streamSource);
        fileDownloader.extend(downloadButton);
        Button closeButton = Buttons.createCloseButton(this);
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(downloadButton, closeButton);
        buttonsLayout.setSpacing(true);
        return buttonsLayout;
    }
}
