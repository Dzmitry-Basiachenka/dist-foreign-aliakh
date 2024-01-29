package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.StreamResource;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Represents custom file downloader with possibility to generate file names on demand.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 04/13/2023
 *
 * @author Anton Azarenka
 */
public class OnDemandFileDownloader extends Anchor {

    private static final long serialVersionUID = 3737454227274730384L;

    /**
     * Default constructor.
     */
    public OnDemandFileDownloader() {
        super.getElement().setAttribute("download", true);
    }

    /**
     * Constructor.
     *
     * @param content instance of {@link Map.Entry} that has {@link Supplier} of file name as key
     *                and {@link Supplier} of {@link InputStream} of file content as value
     */
    public OnDemandFileDownloader(Map.Entry<Supplier<String>, Supplier<InputStream>> content) {
        this();
        setFileName(content.getKey().get());
        setResource(new StreamResource(content.getKey().get(), content.getValue()::get));
    }

    /**
     * Adds component for generating report.
     *
     * @param component instance of {@link Component}
     */
    public void extend(Component component) {
        removeAll();
        //TODO {vaadin23} investigate how we can add logic to call click on Anchor
        add(component);
    }

    /**
     * Sets filename and StreamResource for generating report. Use this method if you need
     * lazy initializing of resources.
     *
     * @param content instance of {@link Map.Entry} that has {@link Supplier} of file name as key
     *                and {@link Supplier} of {@link InputStream} of file content as value
     */
    public void setResource(Map.Entry<Supplier<String>, Supplier<InputStream>> content) {
        String fileName = content.getKey().get();
        setFileName(fileName);
        setResource(new StreamResource(fileName, content.getValue()::get));
    }

    private void setResource(StreamResource resource) {
        setHref(resource);
    }

    private void setFileName(String fileName) {
        getElement().setProperty("fileName", fileName);
    }
}
