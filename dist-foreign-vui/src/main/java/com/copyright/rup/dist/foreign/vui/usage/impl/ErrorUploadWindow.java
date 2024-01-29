package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Modal window that provides information about errors during uploading file.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/2017
 *
 * @author Ihar Suvorau
 */
public class ErrorUploadWindow extends Dialog {

    private static final long serialVersionUID = 1560651189615413754L;

    /**
     * Constructor.
     *
     * @param streamSource {@link IStreamSource}
     * @param labelCaption label caption
     */
    public ErrorUploadWindow(IStreamSource streamSource, String labelCaption) {
        super.setHeaderTitle(ForeignUi.getMessage("window.error"));
        super.setWidth("550px");
        super.setHeight("220px");
        super.setResizable(false);
        var label = new Html(String.format("<div>%s</div>", labelCaption));
        var layout = new VerticalLayout(label);
        layout.setMargin(false);
        layout.setSizeFull();
        super.add(layout);
        super.getFooter().add(buildButtonsLayout(streamSource));
        VaadinUtils.addComponentStyle(this, "upload-error-window");
    }

    private HorizontalLayout buildButtonsLayout(IStreamSource streamSource) {
        var downloadButton = Buttons.createButton(ForeignUi.getMessage("button.download"));
        var fileDownloader = new OnDemandFileDownloader(streamSource.getSource());
        fileDownloader.extend(downloadButton);
        var closeButton = Buttons.createCloseButton(this);
        var buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(fileDownloader, closeButton);
        buttonsLayout.setSpacing(true);
        return buttonsLayout;
    }
}
