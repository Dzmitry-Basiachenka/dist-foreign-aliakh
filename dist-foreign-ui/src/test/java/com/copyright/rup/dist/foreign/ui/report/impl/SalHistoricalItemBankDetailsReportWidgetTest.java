package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap;
import java.util.List;
import java.util.function.Supplier;

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
        List<SalLicensee> licensees = List.of(buildSalLicensee());
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
        expect(controller.getSalLicensees()).andReturn(List.of()).once();
        widget.setController(controller);
        replay(controller, streamSource);
        widget.init();
        verify(controller, streamSource);
        Binder<String> binder = Whitebox.getInternalState(widget, "stringBinder");
        TextField periodFrom = Whitebox.getInternalState(widget, PERIOD_FROM_FIELD);
        TextField periodTo = Whitebox.getInternalState(widget, PERIOD_TO_FIELD);
        validateFieldAndVerifyErrorMessage(periodFrom, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodTo, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodFrom, "1949", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodTo, "1949", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodFrom, "2100", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodTo, "2100", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        periodTo.setValue("2001");
        validateFieldAndVerifyErrorMessage(periodFrom, "2018", binder, null, true);
        periodFrom.setValue("2001");
        validateFieldAndVerifyErrorMessage(periodTo, "2018", binder, null, true);
        periodFrom.setValue("2005");
        validateFieldAndVerifyErrorMessage(periodTo, "2004", binder, INVALID_RELATION_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodTo, "2005", binder, null, true);
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
        UiTestHelper.verifyButtonsLayout(content.getComponent(2), "Export", "Clear", "Close");
    }

    private void verifyPeriodEndDate(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        Component periodEndDateFrom = horizontalLayout.getComponent(0);
        Component periodEndDateTo = horizontalLayout.getComponent(1);
        assertThat(periodEndDateFrom, instanceOf(TextField.class));
        verifyTextField(periodEndDateFrom, "Period End Date From");
        assertThat(periodEndDateTo, instanceOf(TextField.class));
        verifyTextField(periodEndDateTo, "Period End Date To");
    }

    private SalLicensee buildSalLicensee() {
        SalLicensee licensee = new SalLicensee();
        licensee.setAccountNumber(1114L);
        licensee.setName("Agway, Inc.");
        return licensee;
    }
}
