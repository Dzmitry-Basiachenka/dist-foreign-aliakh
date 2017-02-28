package com.copyright.rup.dist.foreign.ui.component;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.VaadinUtils;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededListener;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;

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
 */
// TODO {mhladkikh} move to rup-vaadin
public class CsvUploadComponent extends HorizontalLayout {

    private static final float FILE_NAME_FIELD_EXPAND_RATIO = 1;
    private ObjectProperty<String> fileNameProperty;
    private TextField fileNameField;
    private Upload upload;
    private UploadReceiver receiver = new UploadReceiver();

    /**
     * Constructs widget.
     */
    public CsvUploadComponent() {
        fileNameField = new TextField(ForeignUi.getMessage("field.file_name"));
        upload = new Upload(null, receiver);
        init();
    }

    /**
     * @return last uploaded file path.
     */
    public ByteArrayOutputStream getStreamToUploadedFile() {
        return receiver.getStreamToUploadedFile();
    }

    /**
     * @return file name field.
     */
    public TextField getFileNameField() {
        return fileNameField;
    }

    /**
     * @return file name.
     */
    public String getFileName() {
        return fileNameProperty.getValue();
    }

    /**
     * Tests the current value against registered validators if the field is not empty.
     *
     * @return {@code true} if all registered validators claim that the current value is valid
     * or if the field is empty and not required, {@code false} - otherwise
     */
    public boolean isValid() {
        return fileNameField.isValid();
    }

    /**
     * Adds the upload success event listener.
     *
     * @param listener {@link SucceededListener} instance
     */
    void addSucceededListener(SucceededListener listener) {
        upload.addSucceededListener(listener);
    }

    /**
     * Adds the upload started event listener.
     *
     * @param listener {@link StartedListener} instance
     */
    void addStartedListener(StartedListener listener) {
        upload.addStartedListener(listener);
    }

    /**
     * Adds the upload interrupted event listener.
     *
     * @param listener {@link FailedListener} instance
     */
    void addFailedListener(FailedListener listener) {
        upload.addFailedListener(listener);
    }

    /**
     * @return {@link Upload} instance.
     */
    Upload getUpload() {
        return upload;
    }

    private void setFileName(String fileName) {
        fileNameField.setReadOnly(false);
        fileNameField.setValue(fileName);
        fileNameField.setDescription(fileName);
        fileNameField.setReadOnly(true);
    }

    private void init() {
        initFileNameField();
        initUpload();
        HorizontalLayout uploadLayout = new HorizontalLayout();
        uploadLayout.addComponent(fileNameField);
        uploadLayout.addComponent(upload);
        uploadLayout.setComponentAlignment(upload, Alignment.BOTTOM_RIGHT);
        uploadLayout.setSpacing(true);
        VaadinUtils.setMaxComponentsWidth(uploadLayout);
        uploadLayout.setExpandRatio(fileNameField, FILE_NAME_FIELD_EXPAND_RATIO);
        addComponent(uploadLayout);
        addSucceededListener(event -> {
            setFileName(event.getFilename());
            fileNameField.setReadOnly(true);
        });
    }

    private void initUpload() {
        upload.setButtonCaption(ForeignUi.getMessage("button.browse"));
        upload.setImmediate(true);
    }

    private void initFileNameField() {
        fileNameProperty = new ObjectProperty<>(StringUtils.EMPTY);
        fileNameField = new TextField(ForeignUi.getMessage("field.file_to_upload"));
        fileNameField.setRequired(true);
        fileNameField.setRequiredError(ForeignUi.getMessage("field.error.empty"));
        fileNameField.addValidator(new CsvFileExtensionValidator());
        fileNameField.setPropertyDataSource(fileNameProperty);
        fileNameField.setMaxLength(0);
        VaadinUtils.setMaxComponentsWidth(fileNameField);
    }

    /**
     * File receiver class.
     */
    static class UploadReceiver implements Upload.Receiver {

        private ByteArrayOutputStream streamToUploadedFile;

        @Override
        public ByteArrayOutputStream receiveUpload(String filename, String mimeType) {
            // initializes new stream when 'Browse' button clicked
            streamToUploadedFile = new ByteArrayOutputStream();
            return streamToUploadedFile;
        }

        private ByteArrayOutputStream getStreamToUploadedFile() {
            return streamToUploadedFile;
        }
    }

    /**
     * Validator to check that file has CSV extension.
     */
    static class CsvFileExtensionValidator extends AbstractStringValidator {

        /**
         * Constructor.
         */
        CsvFileExtensionValidator() {
            super(ForeignUi.getMessage("error.upload_file.invalid_extension"));
        }

        @Override
        protected boolean isValidValue(String value) {
            return StringUtils.endsWith(value, ".csv");
        }
    }
}
