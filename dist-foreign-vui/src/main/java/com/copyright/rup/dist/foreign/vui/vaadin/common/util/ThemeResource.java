package com.copyright.rup.dist.foreign.vui.vaadin.common.util;

import com.vaadin.flow.component.html.Image;

/**
 * A helper to get resources from theme folder.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/07/2023
 *
 * @author Anton Azarenka
 */
public final class ThemeResource {

    private static final String THEME_RESOURCE_PREFIX = "./themes/dist/";

    private ThemeResource() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Get the image from the src relative to the theme folder.
     *
     * @param src image url, relative to the theme folder
     * @return the image
     */
    public static Image getImage(String src) {
        return getImage(src, "");
    }

    /**
     * Get the image from the src relative to the theme folder.
     *
     * @param src image url, relative to the theme folder
     * @param alt the alternate text
     * @return the image
     */
    public static Image getImage(String src, String alt) {
        return new Image(THEME_RESOURCE_PREFIX + src, alt);
    }
}
