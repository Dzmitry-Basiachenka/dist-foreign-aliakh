package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.vaadin.ui.Button;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclPublicationTypeWeightsParameterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/13/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AclPublicationTypeWeightsParameterWidgetTest {

    private static final String CAPTION = "Pub Type Weights";

    private final List<AclPublicationType> defaultParameters = Collections.singletonList(
        buildAclPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "Book", "1.00", 201506));
    private AclPublicationTypeWeightsParameterWidget widget;
    private AclPublicationTypeWeightsWindow window;

    @Before
    public void setUp() {
        window = new AclPublicationTypeWeightsWindow(false);
        widget = new AclPublicationTypeWeightsParameterWidget(CAPTION, defaultParameters, () -> window);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, widget.getComponentCount());
        verifyButton(widget.getComponent(0), CAPTION, true);
        assertSame(defaultParameters, widget.getAppliedParameters());
    }

    @Test
    public void testButtonClickListener() {
        mockStatic(Windows.class);
        Button button = (Button) widget.getComponent(0);
        Windows.showModalWindow(window);
        expectLastCall().once();
        replay(Windows.class);
        button.click();
        List<AclPublicationType> appliedParameters = Collections.singletonList(
            buildAclPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "Book", "2.00", 201506));
        window.fireParametersSaveEvent(new ParametersSaveEvent<>(window, appliedParameters));
        assertNotNull(window.getListeners(ParametersSaveEvent.class).iterator().next());
        assertSame(appliedParameters, widget.getAppliedParameters());
        verify(Windows.class);
    }

    private AclPublicationType buildAclPublicationType(String id, String name, String weight, Integer period) {
        AclPublicationType pubType = new AclPublicationType();
        pubType.setId(id);
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        pubType.setPeriod(period);
        return pubType;
    }
}
