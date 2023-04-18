package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;

import com.vaadin.ui.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link ViewAclciUsageBatchMediator}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 04/18/2023
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ForeignSecurityUtils.class)
public class ViewAclciUsageBatchMediatorTest {

    private Button deleteButton;
    private ViewAclciUsageBatchMediator mediator;

    @Before
    public void setUp() {
        deleteButton = new Button();
        mediator = new ViewAclciUsageBatchMediator();
        mediator.setDeleteButton(deleteButton);
    }

    @Test
    public void testApplyDeleteUsagePermissions() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        replay(ForeignSecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteButton.isVisible());
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testApplyNonDeleteUsagePermissions() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(false).once();
        replay(ForeignSecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        verify(ForeignSecurityUtils.class);
    }
}
