package com.copyright.rup.dist.foreign.vui.vaadin.common.ui;

import com.google.common.base.Predicate;
import com.google.common.collect.Range;
import com.vaadin.flow.server.WebBrowser;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Represents class to define browser.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 10/13/14
 *
 * @author Siarhei Sabetski
 */
public class Browser implements Predicate<WebBrowser> {

    private final BrowserType browserType;
    private final Range<Integer> versions;

    /**
     * Constructor.
     *
     * @param browserType the type of browser.
     * @param versions    range of browser's major versions.
     */
    public Browser(BrowserType browserType, Range<Integer> versions) {
        this.browserType = browserType;
        this.versions = versions;
    }

    @Override
    public boolean apply(WebBrowser input) {
        return browserType.apply(input) && versions.contains(input.getBrowserMajorVersion());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Browser that = (Browser) obj;
        return new EqualsBuilder()
            .append(this.browserType, that.browserType)
            .append(this.versions, that.versions)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(browserType)
            .append(versions)
            .toHashCode();
    }

    @Override
    public String toString() {
        return browserType.toString();
    }

    /**
     * Represents enum with browsers types.
     * <p/>
     * Copyright (C) 2014 copyright.com
     * <p/>
     * Date: 10/10/14
     *
     * @author Siarhei Sabetski
     */
    public enum BrowserType implements Predicate<WebBrowser> {

        /**
         * Internet Explorer.
         */
        IE {
            @Override
            public boolean apply(WebBrowser webBrowser) {
                return webBrowser.isIE();
            }
        },
        /**
         * Chrome.
         */
        CHROME {
            @Override
            public boolean apply(WebBrowser webBrowser) {
                return webBrowser.isChrome();
            }
        },
        /**
         * Firefox.
         */
        FIREFOX {
            @Override
            public boolean apply(WebBrowser webBrowser) {
                return webBrowser.isFirefox();
            }
        },
        /**
         * Opera.
         */
        OPERA {
            @Override
            public boolean apply(WebBrowser webBrowser) {
                return webBrowser.isOpera();
            }
        },
        /**
         * Safari.
         */
        SAFARI {
            @Override
            public boolean apply(WebBrowser webBrowser) {
                return webBrowser.isSafari();
            }
        }
    }
}
