package com.copyright.rup.dist.foreign.vui.vaadin.common.util;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.vaadin.flow.component.html.Image;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link ThemeResource}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 07/12/2023
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ThemeResource.class})
public class ThemeResourceTest {

    private static final String FILE_NAME = "testImage.jpg";
    private static final String PATH = "./themes/dist/testImage.jpg";

    @Test
    public void testGetImage() {
        mockStatic(ThemeResource.class);
        Image image = new Image(PATH, "");
        expect(ThemeResource.getImage(FILE_NAME)).andReturn(image).once();
        replay(ThemeResource.class);
        Image actualImage = ThemeResource.getImage(FILE_NAME);
        assertNotNull(actualImage);
        assertEquals(PATH, actualImage.getSrc());
        assertFalse(actualImage.getAlt().isPresent());
        verify(ThemeResource.class);
    }
}
