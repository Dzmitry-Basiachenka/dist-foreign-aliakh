package com.copyright.rup.dist.foreign.ui.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.Iterator;

/**
 * Verifies {@link CsvUploadComponent}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/21/2017
 *
 * @author Mikita Hladkikh
 */
public class CsvUploadComponentTest {

    private CsvUploadComponent csvUploadComponent;

    @Before
    public void setUp() {
        csvUploadComponent = new CsvUploadComponent();
        assertNotNull(csvUploadComponent);
    }

    @Test
    public void testConstructor() {
        verifyUpload(csvUploadComponent.getUpload());
        verifyFileNameTextField(csvUploadComponent.getFileNameField());
        verifySucceededListener(csvUploadComponent);
    }

    @Test
    public void testGetStreamToUploadedFile() {
        assertNull(csvUploadComponent.getStreamToUploadedFile());
        csvUploadComponent.getUpload().getReceiver().receiveUpload("file.csv", "text/csv");
        assertNotNull(csvUploadComponent.getStreamToUploadedFile());
    }

    @Test
    public void testCsvFileExtensionValidator() {
        CsvUploadComponent.CsvFileExtensionValidator validator = new CsvUploadComponent.CsvFileExtensionValidator();
        assertEquals("File extension is incorrect", validator.getErrorMessage());
        assertFalse(validator.isValid(StringUtils.EMPTY));
        assertFalse(validator.isValid("file.cs"));
        assertFalse(validator.isValid("filecsv"));
        assertTrue(validator.isValid("file.csv"));
    }

    private void verifySucceededListener(CsvUploadComponent uploadComponent) {
        Upload upload = Whitebox.getInternalState(uploadComponent, Upload.class);
        Collection<?> listeners = upload.getListeners(SucceededEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(1, listeners.size());
        Object listener = listeners.iterator().next();
        assertTrue(listener instanceof SucceededListener);
        ((SucceededListener) listener).uploadSucceeded(new SucceededEvent(upload, "filename.csv", "type", 0));
        assertEquals("filename.csv", uploadComponent.getFileName());
    }

    private void verifyUpload(Upload upload) {
        assertNotNull(upload);
        assertEquals("Browse", upload.getButtonCaption());
        assertTrue(upload.isImmediate());
        assertNotNull(upload.getReceiver());
        assertEquals(CsvUploadComponent.UploadReceiver.class, upload.getReceiver().getClass());
    }

    private void verifyFileNameTextField(TextField fileNameTextField) {
        assertEquals("File to Upload", fileNameTextField.getCaption());
        assertTrue(fileNameTextField.isRequired());
        assertEquals(100, fileNameTextField.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, fileNameTextField.getWidthUnits());
        assertEquals(0, fileNameTextField.getMaxLength());
        verifyValidators(fileNameTextField.getValidators());
    }

    private void verifyValidators(Collection<Validator> validators) {
        assertNotNull(validators);
        assertEquals(2, validators.size());
        Iterator<Validator> iterator = validators.iterator();
        assertTrue(iterator.next() instanceof StringLengthValidator);
        assertTrue(iterator.next() instanceof CsvUploadComponent.CsvFileExtensionValidator);
    }
}
