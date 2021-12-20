package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link PublicationFormatFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 06/10/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class PublicationFormatFilterWidgetTest {

    private static final String PUBLICATION_FORMAT = "Digital";
    private final PublicationFormatFilterWidget publicationFormatFilterWidget =
        new PublicationFormatFilterWidget(() -> Collections.singletonList(PUBLICATION_FORMAT), Collections.emptySet());

    @Test
    public void testLoadBeans() {
        List<String> publicationFormats = publicationFormatFilterWidget.loadBeans();
        assertEquals(1, publicationFormats.size());
        assertEquals(PUBLICATION_FORMAT, publicationFormats.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, publicationFormatFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(PUBLICATION_FORMAT, publicationFormatFilterWidget.getBeanItemCaption(PUBLICATION_FORMAT));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(PUBLICATION_FORMAT)).once();
        replay(filterSaveEvent);
        publicationFormatFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }
}
