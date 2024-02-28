package com.copyright.rup.dist.foreign.vui.report.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.LocalDateWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
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
 * Verifies {@link UndistributedLiabilitiesReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UI.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class UndistributedLiabilitiesReportWidgetTest {

    private UndistributedLiabilitiesReportWidget widget;

    @Before
    public void setUp() {
        UndistributedLiabilitiesReportController controller =
            createMock(UndistributedLiabilitiesReportController.class);
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(source).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        widget = new UndistributedLiabilitiesReportWidget();
        expect(controller.instantiateWidget()).andReturn(widget).once();
        widget.setController(controller);
        replay(UI.class, ui, controller, streamSource);
        widget.init();
        verify(UI.class, ui, controller, streamSource);
    }

    @Test
    public void testInit() {
        verifyWindow(widget, StringUtils.EMPTY, "650px", null, Unit.PIXELS, false);
        assertEquals(VerticalLayout.class, UiTestHelper.getDialogContent(widget).getClass());
        var content = (VerticalLayout) UiTestHelper.getDialogContent(widget);
        assertEquals(1, content.getComponentCount());
        var firstComponent = content.getComponentAt(0);
        assertEquals(LocalDateWidget.class, firstComponent.getClass());
        assertEquals("Payment Date To", ((LocalDateWidget) firstComponent).getLabel());
        verifyButtonsLayout(UiTestHelper.getFooterComponent(widget, 1));
        assertEquals("report-undistributed-liabilities-window", widget.getClassName());
        assertEquals("report-undistributed-liabilities-window", widget.getId().orElseThrow());
    }

    public void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        var fileDownloader = layout.getComponentAt(0);
        assertThat(fileDownloader, instanceOf(OnDemandFileDownloader.class));
        assertEquals("Export", ((Button) fileDownloader.getChildren().findFirst().get()).getText());
        var closeButton = layout.getComponentAt(1);
        assertThat(closeButton, instanceOf(Button.class));
        assertEquals("Close", ((Button) closeButton).getText());
    }
}
