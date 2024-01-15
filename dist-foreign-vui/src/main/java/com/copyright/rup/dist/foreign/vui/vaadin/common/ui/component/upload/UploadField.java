package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.upload;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.upload.FailedEvent;
import com.vaadin.flow.component.upload.GeneratedVaadinUpload.UploadAbortEvent;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.StartedEvent;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Component provides functionality for uploading CSV files.
 * Contains text field for entering uploaded file name and {@link Upload} component.
 * Uses custom implementation of Upload.Receiver to provide the {@link Upload}
 * component an output stream to write the uploaded data.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/18/2017
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 * @author Dzmitry Basiachenka
 */
public class UploadField extends CustomField<String> {

    private final UploadReceiver receiver = new UploadReceiver();
    private final Upload upload = new Upload(receiver);
    private final String[] acceptedFileTypes;
    private String fileName;

    /**
     * Constructs widget.
     */
    public UploadField() {
        this("text/csv", ".csv");
    }

    /**
     * Constructs widget.
     *
     * @param acceptedFileTypes accepted file types
     */
    public UploadField(String... acceptedFileTypes) {
        this.acceptedFileTypes = acceptedFileTypes.clone();
        init();
    }

    @Override
    public String getValue() {
        return fileName;
    }

    /**
     * @return last uploaded file path.
     */
    public ByteArrayOutputStream getStreamToUploadedFile() {
        return receiver.streamToUploadedFile;
    }

    /**
     * Adds the upload success event listener.
     *
     * @param listener {@link ComponentEventListener} instance
     */
    public void addSucceededListener(ComponentEventListener<SucceededEvent> listener) {
        upload.addSucceededListener(listener);
    }

    /**
     * Adds the upload started event listener.
     *
     * @param listener {@link ComponentEventListener<StartedEvent>} instance
     */
    public void addStartedListener(ComponentEventListener<StartedEvent> listener) {
        upload.addStartedListener(listener);
    }

    /**
     * Adds the upload interrupted event listener.
     *
     * @param listener {@link ComponentEventListener<FailedEvent>} instance
     */
    public void addFailedListener(ComponentEventListener<FailedEvent> listener) {
        upload.addFailedListener(listener);
    }

    /**
     * Adds the upload abort event listener.
     *
     * @param listener {@link ComponentEventListener<UploadAbortEvent>} instance
     */
    public void addUploadAbortListener(ComponentEventListener listener) {
        ComponentUtil.addListener(upload, UploadAbortEvent.class, listener);
    }

    @Override
    protected String generateModelValue() {
        return fileName;
    }

    @Override
    protected void setPresentationValue(String newPresentationValue) {
        fileName = newPresentationValue;
    }

    private void init() {
        setLabel("File to Upload");
        add(initUploadComponent());
        VaadinUtils.setMaxComponentsWidth(this);
        addSucceededListener(event -> fileName = event.getFileName());
    }

    private Component initUploadComponent() {
        CustomInternationalization customI18N = new CustomInternationalization();
        upload.setI18n(customI18N);
        upload.setAcceptedFileTypes(acceptedFileTypes);
        upload.addFileRejectedListener(event -> Windows.showNotificationWindow(event.getErrorMessage()));
        addUploadAbortListener(event -> fileName = null);
        return upload;
    }

    /**
     * File receiver class.
     */
    static class UploadReceiver implements Receiver {

        private ByteArrayOutputStream streamToUploadedFile;

        @Override
        public ByteArrayOutputStream receiveUpload(String filename, String mimeType) {
            // initializes new stream when 'Browse' button clicked
            streamToUploadedFile = new ByteArrayOutputStream();
            return streamToUploadedFile;
        }
    }

    private static class CustomInternationalization extends UploadI18N {

        CustomInternationalization() {
            setDropFiles(new DropFiles().setOne("Drop file here").setMany("Drop files here"));
            setAddFiles(new AddFiles().setOne("Browse").setMany("Upload Files..."));
            setError(new Error().setTooManyFiles("Too Many Files")
                .setFileIsTooBig("File is Too Big")
                .setIncorrectFileType("Incorrect File Type"));
            setUploading(new Uploading()
                .setStatus(new Uploading.Status().setConnecting("Connecting...")
                    .setStalled("Stalled")
                    .setProcessing("Processing File...").setHeld("Queued"))
                .setRemainingTime(new Uploading.RemainingTime()
                    .setPrefix("remaining time: ")
                    .setUnknown("unknown remaining time"))
                .setError(new Uploading.Error()
                    .setServerUnavailable("Upload failed, please try again later")
                    .setUnexpectedServerError("Upload failed due to server error")
                    .setForbidden("Upload forbidden")));
            setUnits(new Units().setSize(List.of("B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB")));
        }
    }
}
