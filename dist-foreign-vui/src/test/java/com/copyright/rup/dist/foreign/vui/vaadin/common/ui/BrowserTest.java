package com.copyright.rup.dist.foreign.vui.vaadin.common.ui;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.google.common.collect.Range;
import com.vaadin.flow.server.WebBrowser;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link Browser}.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 10/13/14
 *
 * @author Siarhei Sabetski
 */
public class BrowserTest {

    private static final int CHROME_MIN_VERSION = 23;
    private WebBrowser webBrowser;

    @Before
    public void setUp() {
        webBrowser = createMock(WebBrowser.class);
    }

    @Test
    public void testApplyDifferentBrowser() {
        Browser browser = new Browser(Browser.BrowserType.CHROME, Range.all());
        expect(webBrowser.isChrome()).andReturn(false).once();
        replay(webBrowser);
        assertFalse(browser.apply(webBrowser));
        verify(webBrowser);
    }

    @Test
    public void testApplyDifferentMajorVersion() {
        Browser browser = new Browser(Browser.BrowserType.CHROME, Range.atLeast(CHROME_MIN_VERSION));
        expect(webBrowser.isChrome()).andReturn(true).once();
        expect(webBrowser.getBrowserMajorVersion()).andReturn(CHROME_MIN_VERSION - 1).once();
        replay(webBrowser);
        assertFalse(browser.apply(webBrowser));
        verify(webBrowser);
    }

    @Test
    public void testApply() {
        Browser browser = new Browser(Browser.BrowserType.CHROME, Range.atLeast(CHROME_MIN_VERSION));
        expect(webBrowser.isChrome()).andReturn(true).once();
        expect(webBrowser.getBrowserMajorVersion()).andReturn(CHROME_MIN_VERSION).once();
        replay(webBrowser);
        assertTrue(browser.apply(webBrowser));
        verify(webBrowser);
    }

    @Test
    public void testEquals() {
        Browser browser = new Browser(Browser.BrowserType.CHROME, Range.atLeast(CHROME_MIN_VERSION));
        assertEquals(browser, browser);
        assertNotEquals(browser, null);
        assertNotEquals(browser, new Browser(Browser.BrowserType.FIREFOX, Range.all()));
    }

    @Test
    public void testHashCode() {
        Browser browser = new Browser(Browser.BrowserType.CHROME, Range.atLeast(CHROME_MIN_VERSION));
        assertEquals(browser.hashCode(), browser.hashCode());
        assertNotEquals(browser.hashCode(), new Browser(Browser.BrowserType.FIREFOX, Range.all()).hashCode());
    }
}
