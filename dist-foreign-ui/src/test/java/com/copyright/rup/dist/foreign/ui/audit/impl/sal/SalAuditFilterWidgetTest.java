package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditFilterWidget;
import org.junit.Before;

/**
 * Verifies {@link SalAuditFilterWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
@SuppressWarnings("all") // TODO {aliakh} to remove when the class is implemented
public class SalAuditFilterWidgetTest {

    private CommonAuditFilterWidget widget;

    @Before
    public void setUp() {
        ISalAuditFilterController controller = createMock(ISalAuditFilterController.class);
        expect(controller.getProductFamily()).andReturn("SAL").times(2);
        replay(controller);
        widget = new SalAuditFilterWidget(controller);
        widget.setController(controller);
        widget.init();
        verify(controller);
    }
}
