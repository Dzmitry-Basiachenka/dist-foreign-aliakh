package com.copyright.rup.dist.foreign.vui.usage.impl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Verifies {@link ErrorUploadWindow}.
 *
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/22/2017
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UI.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ErrorUploadWindowTest {

    @Test
    public void testComponentStructure() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> fileSource =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(fileSource).once();
        replay(streamSource, ui, UI.class);
        var window = new ErrorUploadWindow(streamSource,
            "The file could not be uploaded.<br>Press Download button to see detailed list of errors");
        verify(streamSource, ui, UI.class);
        verifyWindow(window, "Error", "550px", "220px", Unit.PIXELS, false);
        assertFalse(window.isResizable());
        assertEquals("upload-error-window", window.getId().get());
        var component = getDialogContent(window);
        var content = (VerticalLayout) component;
        assertEquals(1, content.getComponentCount());
        assertFalse(content.isMargin());
        verifyLabel(content.getComponentAt(0));
        var footerLayout = getFooterLayout(window);
        assertEquals(OnDemandFileDownloader.class, footerLayout.getComponentAt(0).getClass());
        verifyButton(footerLayout.getComponentAt(1), "Close", true);
    }

    private void verifyLabel(Component label) {
        assertEquals(Html.class, label.getClass());
        assertEquals("The file could not be uploaded.<br>Press Download button to see detailed list of errors",
            ((Html) label).getInnerHtml());
    }
}
