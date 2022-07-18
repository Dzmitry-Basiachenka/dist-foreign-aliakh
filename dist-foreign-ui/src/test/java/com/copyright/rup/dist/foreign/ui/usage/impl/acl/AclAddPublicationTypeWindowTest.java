package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;

/**
 * Verifies {@link AclAddPublicationTypeWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/18/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class AclAddPublicationTypeWindowTest {

    private static final String PUBLICATION_TYPE_UID = "2fe9c0a0-7672-4b56-bc64-9d4125fecf6e";
    private static final String PUBLICATION_TYPE_NAME = "Book";
    private static final Integer PUBLICATION_TYPE_PERIOD = 202206;
    private static final String PUBLICATION_TYPE_WEIGHT = "1.00";

    private final List<PublicationType> publicationTypes = Collections.singletonList(buildPublicationType());
    private IAclScenariosController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclScenariosController.class);
    }

    @Test
    public void testComponentStructure() {
        expect(controller.getPublicationTypes()).andReturn(publicationTypes).once();
        replay(controller, ForeignSecurityUtils.class);
        AclAddPublicationTypeWindow window = new AclAddPublicationTypeWindow(controller);
        assertEquals("Add Pub Type Weight", window.getCaption());
        assertEquals(200, window.getWidth(), 0);
        assertEquals("acl-add-pub-type-weight-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(4, content.getComponentCount());
        verifyComboBox(content.getComponent(0), "Pub Type", false, buildPublicationType());
        verifyTextField(content.getComponent(1), "Period (YYYYMM)");
        verifyTextField(content.getComponent(2), "Weight");
        verifyButtonsLayout(content.getComponent(3), "Confirm", "Cancel");
        verify(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testConfirmButtonClickListener() {
        expect(controller.getPublicationTypes()).andReturn(publicationTypes).once();
        replay(controller);
        TestAclAddPublicationTypeWindow window = new TestAclAddPublicationTypeWindow(controller);
        VerticalLayout content = (VerticalLayout) window.getContent();
        ComboBox<PublicationType> pubTypeComboBox = (ComboBox<PublicationType>) content.getComponent(0);
        pubTypeComboBox.setSelectedItem(buildPublicationType());
        TextField pubTypePeriodField = (TextField) content.getComponent(1);
        pubTypePeriodField.setValue(PUBLICATION_TYPE_PERIOD.toString());
        TextField pubTypeWeightField = (TextField) content.getComponent(2);
        pubTypeWeightField.setValue(PUBLICATION_TYPE_WEIGHT);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(3);
        Button confirmButton = (Button) buttonsLayout.getComponent(0);
        ClickListener listener = (ClickListener) confirmButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(window));
        EventObject event = window.getEventObject();
        assertNotNull(event);
        assertTrue(event instanceof ParametersSaveEvent);
        ParametersSaveEvent<AclPublicationType> scenarioCreateEvent = (ParametersSaveEvent<AclPublicationType>) event;
        AclPublicationType publicationType = scenarioCreateEvent.getSavedParameters();
        assertEquals(PUBLICATION_TYPE_UID, publicationType.getId());
        assertEquals(PUBLICATION_TYPE_NAME, publicationType.getName());
        assertEquals(PUBLICATION_TYPE_PERIOD, publicationType.getPeriod());
        assertEquals(new BigDecimal(PUBLICATION_TYPE_WEIGHT), publicationType.getWeight());
        assertEquals(window, scenarioCreateEvent.getSource());
        assertTrue(window.isClosed());
        verify(controller);
    }

    @Test
    public void testConfirmButtonClickListenerValidationError() {
        mockStatic(Windows.class);
        Capture<Collection<? extends AbstractComponent>> componentsCapture = newCapture();
        Windows.showValidationErrorWindow(capture(componentsCapture));
        expectLastCall().once();
        expect(controller.getPublicationTypes()).andReturn(publicationTypes).once();
        replay(controller, Windows.class);
        TestAclAddPublicationTypeWindow window = new TestAclAddPublicationTypeWindow(controller);
        VerticalLayout content = (VerticalLayout) window.getContent();
        ComboBox<PublicationType> pubTypeComboBox = (ComboBox<PublicationType>) content.getComponent(0);
        TextField pubTypePeriodField = (TextField) content.getComponent(1);
        TextField pubTypeWeightField = (TextField) content.getComponent(2);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(3);
        Button confirmButton = (Button) buttonsLayout.getComponent(0);
        ClickListener listener = (ClickListener) confirmButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(window));
        EventObject event = window.getEventObject();
        assertNotNull(event);
        assertFalse(event instanceof ParametersSaveEvent);
        assertFalse(window.isClosed());
        assertEquals(Arrays.asList(pubTypeComboBox, pubTypePeriodField, pubTypeWeightField),
            componentsCapture.getValue());
        verify(controller, Windows.class);
    }

    private PublicationType buildPublicationType() {
        PublicationType publicationType = new PublicationType();
        publicationType.setId(PUBLICATION_TYPE_UID);
        publicationType.setName(PUBLICATION_TYPE_NAME);
        publicationType.setWeight(new BigDecimal(PUBLICATION_TYPE_WEIGHT));
        return publicationType;
    }

    private static class TestAclAddPublicationTypeWindow extends AclAddPublicationTypeWindow {

        private EventObject eventObject;
        private boolean closed;

        TestAclAddPublicationTypeWindow(IAclScenariosController controller) {
            super(controller);
        }

        EventObject getEventObject() {
            return eventObject;
        }

        boolean isClosed() {
            return closed;
        }

        @Override
        protected void fireEvent(EventObject event) {
            this.eventObject = event;
        }

        @Override
        public void close() {
            this.closed = true;
        }
    }
}
