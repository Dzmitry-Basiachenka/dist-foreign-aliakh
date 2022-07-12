package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Window for ACL Publication Type Weights.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/13/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclPublicationTypeWeightsWindow extends Window {

    private List<AclPublicationType> currentValues;
    private Grid<AclPublicationType> grid;
    private final boolean isEditable;

    /**
     * Constructor.
     *
     * @param isEditable {@code true} if window should be in edit mode, otherwise {@code false}
     */
    public AclPublicationTypeWeightsWindow(boolean isEditable) {
        this.isEditable = isEditable;
        setWidth(525, Unit.PIXELS);
        setHeight(250, Unit.PIXELS);
        setResizable(false);
        initGrid();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout mainLayout = new VerticalLayout(grid, buttonsLayout);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        mainLayout.setExpandRatio(grid, 1);
        mainLayout.setSizeFull();
        setContent(mainLayout);
        setCaption(ForeignUi.getMessage("window.publication_type_weights"));
        VaadinUtils.addComponentStyle(this, "acl-publication-type-weight-window");
    }

    /**
     * Sets applied parameters for the window.
     *
     * @param params to set
     */
    public void setAppliedParameters(List<AclPublicationType> params) {
        currentValues = params.stream().map(AclPublicationType::new).collect(Collectors.toList());
        grid.setItems(currentValues);
    }

    /**
     * Fires specified {@link ParametersSaveEvent}.
     *
     * @param parametersSaveEvent instance of {@link ParametersSaveEvent}
     */
    public void fireParametersSaveEvent(ParametersSaveEvent<List<AclPublicationType>> parametersSaveEvent) {
        fireEvent(parametersSaveEvent);
    }

    private void initGrid() {
        grid = new Grid<>();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(AclPublicationType::getName)
            .setCaption(ForeignUi.getMessage("table.column.publication_type"))
            .setSortable(false);
        grid.addColumn(AclPublicationType::getPeriod)
            .setCaption(ForeignUi.getMessage("table.column.period"))
            .setSortable(false);
        grid.addColumn(item -> CurrencyUtils.format(item.getWeight(), null))
            .setCaption(ForeignUi.getMessage("table.column.weight"))
            .setSortable(false);
        VaadinUtils.addComponentStyle(grid, "acl-publication-type-weight-grid");
    }

    private HorizontalLayout initButtonsLayout() {
        Button saveButton = new Button(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            fireParametersSaveEvent(new ParametersSaveEvent<>(this, currentValues));
            close();
        });
        saveButton.setVisible(isEditable);
        return new HorizontalLayout(saveButton, Buttons.createCloseButton(this));
    }
}
