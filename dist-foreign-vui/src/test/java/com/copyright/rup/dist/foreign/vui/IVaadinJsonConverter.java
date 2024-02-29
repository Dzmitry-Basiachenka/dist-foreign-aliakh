package com.copyright.rup.dist.foreign.vui;

import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.getDialogFooter;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.test.TestUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for JSON conversion of Vaadin components.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/17/2024
 *
 * @author Aliaksandr Liakh
 */
public interface IVaadinJsonConverter {

    String ICON = "icon";
    Set<String> IGNORED_PROPERTY_NAMES = Set.of(
        "_flowId",
        "draggable",
        "errorMessage",
        "flexGrow",
        "headerTitle",
        "i18n",
        "innerHTML",
        "invalid",
        "label",
        "noCloseOnEsc",
        "noCloseOnOutsideClick",
        "opened",
        "openOn",
        "pageSize",
        "path",
        "readonly",
        "required",
        "resizable",
        "stackTrace",
        "value",
        "width"
    );
    Set<String> IGNORED_ATTRIBUTE_NAMES = Set.of(
        "class",
        "disableonclick",
        ICON,
        "id",
        "slot",
        "style",
        "theme"
    );

    /**
     * Asserts component's JSON snapshot.
     *
     * @param fileName  expected JSON string filename
     * @param component instance of {@link Component}
     */
    static void assertJsonSnapshot(String fileName, Component component) {
        var expected = TestUtils.fileToString(IVaadinJsonConverter.class, fileName).trim();
        var actual = toJsonString(component).trim();
        assertEquals(expected, actual);
    }

    private static String toJsonString(Component component) {
        Map<String, Object> tree = toMap(component, new LinkedHashMap<>());
        try {
            return new ObjectMapper()
                .setSerializationInclusion(Include.NON_NULL)
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(tree);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException(e);
        }
    }

    private static Map<String, Object> toMap(Component component, Map<String, Object> tree) {
        putComponent(component, tree);
        putStyle(component, tree);
        putDialog(component, tree);
        putInterfaces(component, tree);
        putButton(component, tree);
        putIcon(component, tree);
        putComboBox(component, tree);
        putGrid(component, tree);
        putGridColumn(component, tree);
        putProperties(component, tree);
        putAttributes(component, tree);
        putChildren(component, tree);
        putDialogFooter(component, tree);
        return tree;
    }

    private static void putComponent(Component component, Map<String, Object> tree) {
        tree.put("javaClass", component.getClass().getName());
        if (component.getId().isPresent()) {
            tree.put("id", component.getId().get());
        }
        if (!component.isVisible()) {
            tree.put("invisible", !component.isVisible());
        }
    }

    private static void putStyle(Component component, Map<String, Object> tree) {
        if (component instanceof HasStyle) {
            var hasStyle = (HasStyle) component;
            var classes = hasStyle.getClassNames();
            if (!classes.isEmpty()) {
                tree.put("class", classes.toString());
            }
        }
        var element = component.getElement();
        tree.put("style", element.getAttribute("style"));
        tree.put("theme", element.getAttribute("theme"));
    }

    private static void putDialog(Component component, Map<String, Object> tree) {
        if (component instanceof Dialog) {
            var dialog = (Dialog) component;
            tree.put("headerTitle", dialog.getHeaderTitle());
            tree.put("isModal", dialog.isModal());
            tree.put("isDraggable", dialog.isDraggable());
            tree.put("isResizable", dialog.isResizable());
            tree.put("isCloseOnEsc", dialog.isCloseOnEsc());
            tree.put("isCloseOnOutsideClick", dialog.isCloseOnOutsideClick());
            tree.put("isOpened", dialog.isOpened());
        }
    }

    private static void putInterfaces(Component component, Map<String, Object> tree) {
        if (component instanceof HasEnabled) {
            var hasEnabled = (HasEnabled) component;
            if (!hasEnabled.isEnabled()) {
                put(tree, "enabled", hasEnabled.isEnabled());
            }
        }
        if (component instanceof HasLabel) {
            put(tree, "label", ((HasLabel) component).getLabel());
        }
        if (component instanceof HasText) {
            put(tree, "text", ((HasText) component).getText());
        }
        if (component instanceof HasSize) {
            var hasSize = (HasSize) component;
            put(tree, "minWidth", hasSize.getMinWidth());
            put(tree, "width", hasSize.getWidth());
            put(tree, "maxWidth", hasSize.getMaxWidth());
            put(tree, "minHeight", hasSize.getMinHeight());
            put(tree, "height", hasSize.getHeight());
            put(tree, "maxHeight", hasSize.getMaxHeight());
        }
        if (component instanceof HasValue) {
            var hasValue = (HasValue<?, ?>) component;
            put(tree, "value", hasValue.getValue());
            if (hasValue.isReadOnly()) {
                put(tree, "readonly", hasValue.isReadOnly());
            }
        }
        if (component instanceof HasValueAndElement) {
            var hasValueAndElement = (HasValueAndElement<?, ?>) component;
            put(tree, "requiredIndicatorVisible", hasValueAndElement.isRequiredIndicatorVisible());
        }
        if (component instanceof HasValidation) {
            var hasValidation = (HasValidation) component;
            tree.put("valid", !hasValidation.isInvalid());
            tree.put("errorMessage", hasValidation.getErrorMessage());
        }
    }

    private static void putButton(Component component, Map<String, Object> tree) {
        if (component instanceof Button) {
            var button = (Button) component;
            tree.put("disableOnClick", button.isDisableOnClick());
            var icon = (Icon) button.getIcon();
            if (Objects.nonNull(icon)) {
                put(tree, ICON, toMap(icon, new LinkedHashMap<>()));
            }
        }
    }

    private static void putIcon(Component component, Map<String, Object> tree) {
        if (component instanceof Icon) {
            var icon = (Icon) component;
            var element = icon.getElement();
            put(tree, ICON, element.getAttribute(ICON));
            put(tree, "slot", element.getAttribute("slot"));
        }
    }

    private static void putComboBox(Component component, Map<String, Object> tree) {
        if (component instanceof ComboBox) {
            var comboBox = (ComboBox<?>) component;
            put(tree, "allowCustomValue", comboBox.isAllowCustomValue());
            DataProvider<?, ?> dataProvider = comboBox.getDataProvider();
            if (dataProvider instanceof ListDataProvider) {
                ListDataProvider<?> listDataProvider = (ListDataProvider<?>) comboBox.getDataProvider();
                Collection<?> items = listDataProvider.getItems();
                put(tree, "items", List.copyOf(items));
            }
        }
    }

    private static void putGrid(Component component, Map<String, Object> tree) {
        if (component instanceof Grid) {
            var grid = (Grid<?>) component;
            put(tree, "pageSize", grid.getPageSize());
        }
    }

    private static void putGridColumn(Component component, Map<String, Object> tree) {
        if (component instanceof Column) {
            var column = (Column<?>) component;
            if (column.getId().isPresent()) {
                put(tree, "id", column.getId());
            }
            put(tree, "key", column.getKey());
            put(tree, "headerText", column.getHeaderText());
            put(tree, "textAlign", column.getTextAlign());
            put(tree, "flexGrow", column.getFlexGrow());
            put(tree, "width", column.getWidth());
            put(tree, "autoWidth", column.isAutoWidth());
            put(tree, "sortable", column.isSortable());
            put(tree, "resizable", column.isResizable());
        }
    }

    private static void putProperties(Component component, Map<String, Object> tree) {
        component
            .getElement()
            .getPropertyNames()
            .forEach(propertyName -> {
                String propertyValue = component.getElement().getProperty(propertyName);
                if (Objects.nonNull(propertyValue)
                    && !IGNORED_PROPERTY_NAMES.contains(propertyName)
                    && !propertyValue.isEmpty()) {
                    put(tree, "property_" + propertyName, propertyValue);
                }
            });
    }

    private static void putAttributes(Component component, Map<String, Object> tree) {
        component
            .getElement()
            .getAttributeNames()
            .forEach(attributeName -> {
                String attributeValue = component.getElement().getAttribute(attributeName);
                if (Objects.nonNull(attributeValue)
                    && !IGNORED_ATTRIBUTE_NAMES.contains(attributeName)
                    && !attributeValue.isEmpty()) {
                    put(tree, "attribute_" + attributeName, attributeValue);
                }
            });
    }

    private static void putChildren(Component component, Map<String, Object> tree) {
        List<Map<String, Object>> children = component
            .getChildren()
            .map(child -> toMap(child, new LinkedHashMap<>()))
            .collect(Collectors.toList());
        if (!children.isEmpty()) {
            tree.put("children", children);
        }
    }

    private static void putDialogFooter(Component component, Map<String, Object> tree) {
        if (component instanceof Dialog) {
            getDialogFooter((Dialog) component)
                .ifPresent(value -> tree.put("footer", toMap(value, new LinkedHashMap<>())));
        }
    }

    private static void put(Map<String, Object> tree, String key, Object value) {
        if (Objects.nonNull(value)) {
            tree.put(key, value);
        }
    }
}
