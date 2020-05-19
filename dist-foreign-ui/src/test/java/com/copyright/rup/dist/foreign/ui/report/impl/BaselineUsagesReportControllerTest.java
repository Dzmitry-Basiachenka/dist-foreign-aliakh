package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.IBaselineUsagesReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.IScenarioReportWidget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Verifies {@link BaselineUsagesReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 5/19/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class BaselineUsagesReportControllerTest {

    private final BaselineUsagesReportController controller = new BaselineUsagesReportController();

    @Test
    public void testInstantiateWidget() {
        IBaselineUsagesReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(BaselineUsagesReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        IScenarioReportWidget widget = createMock(IScenarioReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        expect(OffsetDateTime.now()).andReturn(now).once();
        replay(OffsetDateTime.class, widget);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("baseline_usages_report_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        verify(OffsetDateTime.class, widget);
    }
}
