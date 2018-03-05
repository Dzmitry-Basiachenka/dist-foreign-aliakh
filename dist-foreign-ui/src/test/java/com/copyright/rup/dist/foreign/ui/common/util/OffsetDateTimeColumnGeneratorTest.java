package com.copyright.rup.dist.foreign.ui.common.util;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.VaadinUtils;

import com.vaadin.data.Container;
import com.vaadin.ui.Table;
import com.vaadin.util.ReflectTools;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.OffsetDateTime;

/**
 * Verify {@link OffsetDateTimeColumnGenerator}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/05/18
 *
 * @author Pavel Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(VaadinUtils.class)
public class OffsetDateTimeColumnGeneratorTest {

    private OffsetDateTimeColumnGenerator columnGenerator;

    @Before
    public void setUp() {
        columnGenerator = new OffsetDateTimeColumnGenerator();
        mockStatic(VaadinUtils.class, ReflectTools.findMethod(VaadinUtils.class, "getContainerPropertyValue",
            Container.class, Object.class, Object.class, Class.class));
    }

    @Test
    public void testDateColumnGeneratorWithNotBlankDate() {
        OffsetDateTime date = OffsetDateTime.now().withYear(2000).withMonth(02).withDayOfMonth(29);
        expect(VaadinUtils.getContainerPropertyValue(anyObject(Table.class), anyObject(Object.class),
            anyObject(Object.class), eq(OffsetDateTime.class))).andReturn(date).once();
        replay(VaadinUtils.class);
        assertEquals("02/29/2000", columnGenerator.generateCell(null, null, null));
        verify(VaadinUtils.class);
    }

    @Test
    public void testDateColumnGeneratorWithBlankDate() {
        expect(VaadinUtils.getContainerPropertyValue(anyObject(Table.class), anyObject(Object.class),
            anyObject(Object.class), eq(OffsetDateTime.class))).andReturn(null).once();
        replay(VaadinUtils.class);
        assertEquals(StringUtils.EMPTY, columnGenerator.generateCell(null, null, null));
        verify(VaadinUtils.class);
    }
}
