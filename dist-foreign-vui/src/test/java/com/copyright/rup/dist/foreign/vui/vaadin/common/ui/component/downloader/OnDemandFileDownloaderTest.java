package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

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
 * Verifies {@link OnDemandFileDownloader}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 04/13/2023
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UI.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*"})
public class OnDemandFileDownloaderTest {

    private static final String FILE_NAME = "file.txt";
    private static final InputStream INPUT_STREAM = new ByteArrayInputStream(new byte[]{});

    private OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader();

    @Test
    public void initTest() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(2);
        expect(ui.getUIId()).andReturn(1).times(2);
        Map.Entry<Supplier<String>, Supplier<InputStream>> fileSource =
            new SimpleImmutableEntry<>(() -> FILE_NAME, () -> INPUT_STREAM);
        replay(ui, UI.class);
        fileDownloader = new OnDemandFileDownloader(fileSource);
        assertNotNull(fileDownloader.getHref());
        assertEquals(0, fileDownloader.getChildren().count());
        fileDownloader.extend(new Button("Export"));
        assertEquals(1, fileDownloader.getChildren().count());
        verifyAll();
    }

    @Test
    public void setContentTest() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).times(2);
        expect(ui.getUIId()).andReturn(1).times(2);
        Map.Entry<Supplier<String>, Supplier<InputStream>> fileSource =
            new SimpleImmutableEntry<>(() -> FILE_NAME, () -> INPUT_STREAM);
        replay(ui, UI.class);
        fileDownloader.setResource(fileSource);
        assertNotNull(fileDownloader.getHref());
        assertEquals(0, fileDownloader.getChildren().count());
        fileDownloader.extend(new Button("Export"));
        assertEquals(1, fileDownloader.getChildren().count());
        verifyAll();
    }
}
