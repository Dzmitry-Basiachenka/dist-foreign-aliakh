package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component;

import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.function.SerializableConsumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

/**
 * Represents class for managing maximize and resize modal window.
 *
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 05/17/2023
 *
 * @author Anton Azarenka
 */
public class MaximizeModalWindowManager {

    private static final String SET_PROPERTY_IN_OVERLAY_JS = "this.$.overlay.$.overlay.style[$0]=$1";
    private static final String MAX_WIDTH = "101.7%";
    private static final String MAX_HEIGHT = "103.7%";
    private final Position initialPosition = new Position("-15px", "-15px");
    private final Dialog window;
    private String defaultWidth;
    private String defaultHeight;
    private Button resizeButton;
    private Position lastPosition;

    /**
     * Constructor.
     *
     * @param window instance of {@link Dialog}
     */
    public MaximizeModalWindowManager(Dialog window) {
        this.window = window;
        this.defaultWidth = window.getWidth();
        this.defaultHeight = window.getHeight();
        this.window.setMaxWidth(MAX_WIDTH);
        this.window.setMaxHeight(MAX_HEIGHT);
        initManager();
    }

    /**
     * Does maximize or restore window to before size and position.
     */
    public void resizeWidow() {
        changeLastPosition(position -> lastPosition = position);
        if (MAX_WIDTH.equals(window.getWidth())) {
            restoreWindow();
        } else {
            maximizeWindow();
        }
    }

    /**
     * @return resize button.
     */
    public Button getResizeButton() {
        return resizeButton;
    }

    private void initManager() {
        initResizeButton();
        window.addResizeListener(event -> {
            this.defaultHeight = event.getHeight();
            this.defaultWidth = event.getWidth();
        });
    }

    private void maximizeWindow() {
        enablePositioning(true);
        setPosition(initialPosition);
        window.setWidth(MAX_WIDTH);
        window.setHeight(MAX_HEIGHT);
        window.setDraggable(false);
        resizeButton.setTooltipText("Restore Down");
        resizeButton.setIcon(new Icon(VaadinIcon.SQUARE_SHADOW));
    }

    private void restoreWindow() {
        if (Objects.nonNull(lastPosition)) {
            setPosition(lastPosition);
        }
        enablePositioning(Objects.nonNull(lastPosition) && !lastPosition.equals(initialPosition));
        window.setWidth(defaultWidth);
        window.setHeight(defaultHeight);
        window.setDraggable(true);
        resizeButton.setTooltipText("Maximize");
        resizeButton.setIcon(new Icon(VaadinIcon.THIN_SQUARE));
    }

    /**
     * Sets position.
     *
     * @param position position
     */
    protected void setPosition(Position position) {
        window.getElement().executeJs(SET_PROPERTY_IN_OVERLAY_JS, "left", position.getLeft());
        window.getElement().executeJs(SET_PROPERTY_IN_OVERLAY_JS, "top", position.getTop());
    }

    /**
     * Enables positioning.
     *
     * @param positioningEnabled positioning enabled flag
     */
    protected void enablePositioning(boolean positioningEnabled) {
        window.getElement()
            .executeJs(SET_PROPERTY_IN_OVERLAY_JS, "align-self", positioningEnabled ? "flex-start" : "unset");
        window.getElement()
            .executeJs(SET_PROPERTY_IN_OVERLAY_JS, "position", positioningEnabled ? "absolute" : "relative");
    }

    private void changeLastPosition(SerializableConsumer<Position> consumer) {
        window.getElement()
            .executeJs(
                "return [this.$.overlay.$.overlay.style['top'], this.$.overlay.$.overlay.style['left']]"
            )
            .then(String.class, overlayPosition -> {
                    String[] topLeftPositions = StringUtils.split(overlayPosition, ',');
                    if (topLeftPositions.length == 2 && topLeftPositions[0] != null && topLeftPositions[1] != null) {
                        Position position = new Position(topLeftPositions[0].trim(), topLeftPositions[1].trim());
                        consumer.accept(position);
                    }
                }
            );
    }

    private void initResizeButton() {
        resizeButton = new Button(new Icon(VaadinIcon.THIN_SQUARE), event -> resizeWidow());
        resizeButton.setTooltipText("Maximize");
        VaadinUtils.addComponentStyle(resizeButton, "button-resize");
        VaadinUtils.addComponentStyle(resizeButton, "v-window-maximizebox");
        VaadinUtils.addComponentStyle(resizeButton, "v-window-restorebox");
    }

    /**
     * Position.
     */
    protected static class Position {

        private final String top;
        private final String left;

        /**
         * Constructor.
         *
         * @param top  Y position
         * @param left X position
         */
        public Position(String top, String left) {
            this.top = top;
            this.left = left;
        }

        public String getTop() {
            return top;
        }

        public String getLeft() {
            return left;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (null == obj || getClass() != obj.getClass()) {
                return false;
            }
            Position position = (Position) obj;
            return new EqualsBuilder().append(top, position.top)
                .append(left, position.left)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(top)
                .append(left)
                .toHashCode();
        }
    }
}
