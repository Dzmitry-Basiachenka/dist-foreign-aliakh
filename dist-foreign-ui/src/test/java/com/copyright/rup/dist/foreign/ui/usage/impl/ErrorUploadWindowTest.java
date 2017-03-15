package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;

import com.vaadin.server.Extension;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

/**
 * Verifies {@link ErrorUploadWindow}.
 *
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/22/17
 *
 * @author Ihar Suvorau
 */
public class ErrorUploadWindowTest {

    @Test
    public void testComponentStructure() {
        IUsagesController controller = createMock(IUsagesController.class);
        CsvProcessingResult csvProcessingResult = new CsvProcessingResult(Collections.emptyList(), StringUtils.EMPTY);
        expect(controller.getErrorResultStreamSource(csvProcessingResult))
            .andReturn(createMock(IStreamSource.class)).once();
        replay(controller);
        ErrorUploadWindow errorUploadWindow =
            new ErrorUploadWindow(controller.getErrorResultStreamSource(csvProcessingResult));
        verify(controller);
        assertEquals("upload-error-window", errorUploadWindow.getId());
        assertEquals("Error", errorUploadWindow.getCaption());
        assertEquals(365, errorUploadWindow.getWidth(), 0);
        assertEquals(Unit.PIXELS, errorUploadWindow.getWidthUnits());
        assertEquals(150, errorUploadWindow.getHeight(), 0);
        assertEquals(Unit.PIXELS, errorUploadWindow.getHeightUnits());
        VerticalLayout content = (VerticalLayout) errorUploadWindow.getContent();
        assertEquals(new MarginInfo(true), content.getMargin());
        assertTrue(content.isSpacing());
        assertEquals(2, content.getComponentCount());
        verifyLabel(content.getComponent(0));
        verifyButtons(content.getComponent(1));
    }

    private void verifyLabel(Component component) {
        assertEquals(Label.class, component.getClass());
        Label label = (Label) component;
        assertEquals("The file could not be uploaded.<br>Press Download button to see detailed list of errors.",
            label.getValue());
        assertEquals(ContentMode.HTML, label.getContentMode());
    }

    private void verifyButtons(Component component) {
        assertEquals(HorizontalLayout.class, component.getClass());
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        assertEquals(Button.class, horizontalLayout.getComponent(0).getClass());
        assertEquals(Button.class, horizontalLayout.getComponent(1).getClass());
        Button download = (Button) horizontalLayout.getComponent(0);
        assertEquals("Download", download.getCaption());
        Collection<Extension> extensions = download.getExtensions();
        assertTrue(CollectionUtils.isNotEmpty(extensions));
        assertEquals(1, extensions.size());
        assertTrue(extensions.iterator().next() instanceof OnDemandFileDownloader);
        assertEquals("Close", horizontalLayout.getComponent(1).getCaption());
    }
}
