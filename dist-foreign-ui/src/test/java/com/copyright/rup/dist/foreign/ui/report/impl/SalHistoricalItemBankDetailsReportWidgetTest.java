package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportController;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Extension;
import com.vaadin.server.Sizeable;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link SalHistoricalItemBankDetailsReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/25/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalHistoricalItemBankDetailsReportWidgetTest {

    private static final String INVALID_PERIOD_ERROR_MESSAGE = "Field value should be in range from 1950 to 2099";
    private static final String INVALID_RELATION_ERROR_MESSAGE =
        "Field value should be greater or equal to Period End Date From";
    private static final String EMPTY_FIELD_ERROR_MESSAGE = "Field value should be specified";
    private static final String PERIOD_FROM_FIELD = "periodEndDateFromField";
    private static final String PERIOD_TO_FIELD = "periodEndDateToField";

    @Test
    public void testInit() {
        ISalHistoricalItemBankDetailsReportController controller = new SalHistoricalItemBankDetailsReportController();
        IUsageBatchService usageBatchService = createMock(IUsageBatchService.class);
        Whitebox.setInternalState(controller, usageBatchService);
        List<SalLicensee> licensees = Collections.singletonList(buildSalLicensee());
        expect(usageBatchService.getSalLicensees()).andReturn(licensees).once();
        replay(usageBatchService);
        SalHistoricalItemBankDetailsReportWidget widget =
            (SalHistoricalItemBankDetailsReportWidget) controller.initWidget();
        verify(usageBatchService);
        verifySize(widget);
        verifyContent(widget.getContent());
    }

    @Test
    public void testPeriodEndDateValidation() {
        SalHistoricalItemBankDetailsReportWidget widget = new SalHistoricalItemBankDetailsReportWidget();
        ISalHistoricalItemBankDetailsReportController controller =
            createMock(ISalHistoricalItemBankDetailsReportController.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new AbstractMap.SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getWidget()).andReturn(widget).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(controller.getSalLicensees()).andReturn(Collections.emptyList()).once();
        widget.setController(controller);
        replay(controller, streamSource);
        widget.init();
        verify(controller, streamSource);
        Binder<String> binder = Whitebox.getInternalState(widget, "stringBinder");
        TextField periodFrom = Whitebox.getInternalState(widget, PERIOD_FROM_FIELD);
        TextField periodTo = Whitebox.getInternalState(widget, PERIOD_TO_FIELD);
        verifyTextFieldValidationMessage(periodFrom, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR_MESSAGE, false);
        verifyTextFieldValidationMessage(periodTo, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR_MESSAGE, false);
        verifyTextFieldValidationMessage(periodFrom, "1949", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        verifyTextFieldValidationMessage(periodTo, "1949", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        verifyTextFieldValidationMessage(periodFrom, "2100", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        verifyTextFieldValidationMessage(periodTo, "2100", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        periodTo.setValue("2001");
        verifyTextFieldValidationMessage(periodFrom, "2018", binder, INVALID_PERIOD_ERROR_MESSAGE, true);
        periodFrom.setValue("2001");
        verifyTextFieldValidationMessage(periodTo, "2018", binder, INVALID_PERIOD_ERROR_MESSAGE, true);
        periodFrom.setValue("2005");
        verifyTextFieldValidationMessage(periodTo, "2004", binder, INVALID_RELATION_ERROR_MESSAGE, false);
        verifyTextFieldValidationMessage(periodTo, "2005", binder, INVALID_RELATION_ERROR_MESSAGE, true);
    }

    private void verifySize(SalHistoricalItemBankDetailsReportWidget widget) {
        assertEquals(400, widget.getWidth(), 0);
        assertEquals(-1.0, widget.getHeight(), 0);
        assertEquals(Unit.PIXELS, widget.getWidthUnits());
        assertEquals(Unit.PIXELS, widget.getHeightUnits());
    }

    private void verifyContent(Component component) {
        assertEquals(VerticalLayout.class, component.getClass());
        VerticalLayout content = (VerticalLayout) component;
        assertEquals(3, content.getComponentCount());
        verifyComboBox(content.getComponent(0), "Licensee", true, buildSalLicensee());
        verifyPeriodEndDate(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    private void verifyPeriodEndDate(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        Component periodEndDateFrom = horizontalLayout.getComponent(0);
        Component periodEndDateTo = horizontalLayout.getComponent(1);
        assertTrue(periodEndDateFrom instanceof TextField);
        verifyTextField(periodEndDateFrom, "Period End Date From");
        assertTrue(periodEndDateTo instanceof TextField);
        verifyTextField(periodEndDateTo, "Period End Date To");
    }

    private void verifyTextField(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(caption, component.getCaption());
        TextField textField = (TextField) component;
        assertEquals(100, textField.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, textField.getWidthUnits());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        Button exportButton = verifyButton(layout.getComponent(0), "Export", 0);
        Collection<Extension> extensions = exportButton.getExtensions();
        assertTrue(CollectionUtils.isNotEmpty(extensions));
        assertEquals(1, extensions.size());
        assertTrue(extensions.iterator().next() instanceof OnDemandFileDownloader);
        verifyButton(layout.getComponent(1), "Clear", 1);
        verifyButton(layout.getComponent(2), "Close", 1);
    }

    private Button verifyButton(Component component, String caption, int listenerCount) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(caption, button.getId());
        assertEquals(listenerCount, button.getListeners(ClickEvent.class).size());
        return button;
    }

    private void verifyTextFieldValidationMessage(TextField field, String value, Binder<String> binder, String message,
                                                  boolean isValid) {
        field.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages = errors
            .stream()
            .map(ValidationResult::getErrorMessage)
            .collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
    }

    private SalLicensee buildSalLicensee() {
        SalLicensee licensee = new SalLicensee();
        licensee.setAccountNumber(1114L);
        licensee.setName("Agway, Inc.");
        return licensee;
    }
}
