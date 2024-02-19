package com.copyright.rup.dist.foreign.vui.vaadin.common.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Provides utilities to work with Vaadin.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/07/2023
 *
 * @author Anton Azarenka
 */
public final class VaadinUtils {

    private static final String UNDERSCORE = "_";
    private static final String WHITE_SPACE = " ";

    private VaadinUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Sets width 100% for Vaadin components.
     *
     * @param components components to set width.
     */
    public static void setFullComponentsWidth(HasSize... components) {
        Arrays.stream(components).filter(Objects::nonNull).forEach(HasSize::setWidthFull);
    }

    /**
     * Makes buttons to be disabled on click.
     * Button will be enabled after all click listeners attached to the button will be invoked.
     * IMPORTANT: should be called after click listeners for buttons were configured.
     *
     * @param buttons buttons.
     */
    public static void setButtonsAutoDisabled(Button... buttons) {
        Arrays.stream(buttons).filter(Objects::nonNull).forEach(button -> {
            button.setDisableOnClick(true);
            button.addClickListener(event -> button.setEnabled(true));
        });
    }

    /**
     * Makes buttons to be disabled on click.
     *
     * @param buttons buttons.
     */
    public static void setButtonsDisabledOnClick(Button... buttons) {
        Arrays.stream(buttons).filter(Objects::nonNull).forEach(button -> button.setDisableOnClick(true));
    }

    /**
     * Adds style and id for the given component.
     * Method is null safe. If component contains not null Id only style will be added.
     *
     * @param sourceComponent the source component.
     * @param styleName       style to add.
     */
    public static void addComponentStyle(Component sourceComponent, String styleName) {
        String componentId = StringUtils.replace(styleName, WHITE_SPACE, UNDERSCORE);
        if (StringUtils.isNotBlank(componentId)) {
            if (sourceComponent.getId().isEmpty()) {
                sourceComponent.setId(componentId);
            }
            if (sourceComponent instanceof HasStyle) {
                ((HasStyle) sourceComponent).addClassName(componentId);
            }
        }
    }

    /**
     * Adds themes and style and id for providing grid component.
     *
     * @param grid  instance of {@link  Grid}
     * @param style class name for css
     */
    public static void setGridProperties(Grid<?> grid, String style) {
        grid.addThemeVariants(
            GridVariant.LUMO_COMPACT,
            GridVariant.LUMO_COLUMN_BORDERS,
            GridVariant.LUMO_ROW_STRIPES
        );
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, style);
    }

    /**
     * Returns current date and time of the browser.
     *
     * @param optionalUI instance of {@link UI}
     * @return instance of {@link OffsetDateTime}
     */
    public static OffsetDateTime getClientCurrentDate(Optional<UI> optionalUI) {
        AtomicReference<OffsetDateTime> offsetDateTime = new AtomicReference<>();
        optionalUI.ifPresent(ui -> ui.getPage().retrieveExtendedClientDetails(extendedClientDetails -> {
            String timeZoneId = extendedClientDetails.getTimeZoneId();
            offsetDateTime.set(OffsetDateTime.now(ZoneId.of(timeZoneId)));
        }));
        return offsetDateTime.get();
    }

    /**
     * Sets padding for provides component.
     *
     * @param component instance of {@link Component}
     * @param top       top
     * @param right     right
     * @param bottom    bottom
     * @param left      left
     */
    public static void setPadding(Component component, double top, double right, double bottom, double left) {
        component.getElement()
            .getStyle()
            .set("padding", String.format("%spx %spx %spx %spx", top, right, bottom, left));
    }

    /**
     * Enables or disables elements.
     *
     * @param enabled  a boolean value specifying if the elements should be enabled or not
     * @param elements elements
     */
    public static void setComponentsEnabled(boolean enabled, HasEnabled... elements) {
        if (ArrayUtils.isNotEmpty(elements)) {
            for (HasEnabled element : elements) {
                element.setEnabled(enabled);
            }
        }
    }

    /**
     * Initializes vertical layout without spacing, padding and margin by default.
     *
     * @param components components to be added to vertical layout, can be empty
     * @return new instance of {@link VerticalLayout}
     */
    public static VerticalLayout initCommonVerticalLayout(Component... components) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setMargin(false);
        layout.setWidthFull();
        layout.add(components);
        return layout;
    }

    /**
     * Initializes size full vertical layout without spacing, padding and margin by default.
     *
     * @param components components to be added to vertical layout, can be empty
     * @return new instance of {@link VerticalLayout}
     */
    public static VerticalLayout initSizeFullVerticalLayout(Component... components) {
        var layout = initCommonVerticalLayout(components);
        layout.setHeightFull();
        return layout;
    }
}
