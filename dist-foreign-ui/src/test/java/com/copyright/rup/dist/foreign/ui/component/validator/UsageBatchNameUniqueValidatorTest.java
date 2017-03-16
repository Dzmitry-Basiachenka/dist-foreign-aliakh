package com.copyright.rup.dist.foreign.ui.component.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link UsageBatchNameUniqueValidator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/16/17
 *
 * @author Mikalai Bezmen
 */
public class UsageBatchNameUniqueValidatorTest {

    private static final String USAGE_BATCH_NAME = "Usage Batch Name";
    private static final String USAGE_BATCH_NEW_NAME = "Usage Batch New Name";
    private UsageBatchNameUniqueValidator validator;
    private IUsagesController usagesController;

    @Before
    public void setUp() {
        usagesController = createMock(IUsagesController.class);
        validator = new UsageBatchNameUniqueValidator(usagesController);
    }

    @Test
    public void testIsValidValueStoredNull() {
        expect(usagesController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(true).once();
        replay(usagesController);
        assertFalse(validator.isValid(USAGE_BATCH_NAME));
        verify(usagesController);
    }

    @Test
    public void testIsValidValue() {
        expect(usagesController.usageBatchExists(USAGE_BATCH_NEW_NAME)).andReturn(true).once();
        replay(usagesController);
        assertFalse(validator.isValid(USAGE_BATCH_NEW_NAME));
        verify(usagesController);
    }

    @Test
    public void testIsValidValueScenarioNotExist() {
        expect(usagesController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).once();
        replay(usagesController);
        assertTrue(validator.isValidValue(USAGE_BATCH_NAME));
        verify(usagesController);
    }
}
