package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Modal window that provides functionality for viewing and deleting {@link AclGrantSet}s.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/21/2022
 *
 * @author Aliaksandr Liakh
 */
public class ViewAclGrantSetWindow extends Window implements SearchWidget.ISearchController {

    private static final long serialVersionUID = -7704194142628469332L;

    private final SearchWidget searchWidget;
    private final IAclGrantDetailController controller;

    private Grid<AclGrantSet> grid;
    private Button deleteButton;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclGrantDetailController}
     */
    public ViewAclGrantSetWindow(IAclGrantDetailController controller) {
        this.controller = controller;
        super.setWidth(1200, Unit.PIXELS);
        super.setHeight(550, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.view_grant_set.search"));
        initGrantSetsGrid();
        var buttonsLayout = initButtons();
        initMediator();
        var layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        super.setContent(layout);
        super.setCaption(ForeignUi.getMessage("window.view_acl_grant_set"));
        VaadinUtils.addComponentStyle(this, "view-acl-grant-set-window");
    }

    @Override
    public void performSearch() {
        ListDataProvider<AclGrantSet> dataProvider = (ListDataProvider<AclGrantSet>) grid.getDataProvider();
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(grantSet -> StringUtils.containsIgnoreCase(grantSet.getName(), searchValue));
        }
        // Gets round an issue when Vaadin does not recalculate columns widths once vertical scroll is disappeared
        grid.recalculateColumnWidths();
    }

    private void initGrantSetsGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(controller.getAllAclGrantSets());
        grid.addSelectionListener(
            event -> deleteButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        grid.setSizeFull();
        addGridColumns();
        VaadinUtils.addComponentStyle(grid, "view-acl-grant-set-grid");
    }

    private void addGridColumns() {
        grid.addColumn(AclGrantSet::getName)
            .setCaption(ForeignUi.getMessage("table.column.grant_set_name"))
            .setComparator((grantSet1, grantSet2) -> grantSet1.getName().compareToIgnoreCase(grantSet2.getName()))
            .setExpandRatio(1);
        grid.addColumn(AclGrantSet::getGrantPeriod)
            .setCaption(ForeignUi.getMessage("table.column.grant_period"))
            .setComparator((grantSet1, grantSet2) -> grantSet1.getGrantPeriod().compareTo(grantSet2.getGrantPeriod()))
            .setWidth(110);
        grid.addColumn(AclGrantSet::getLicenseType)
            .setCaption(ForeignUi.getMessage("table.column.license_type"))
            .setComparator((grantSet1, grantSet2) -> grantSet1.getLicenseType().compareTo(grantSet2.getLicenseType()))
            .setWidth(100);
        grid.addColumn(grantSet -> grantSet.getPeriods().stream()
                .sorted(Comparator.reverseOrder())
                .map(String::valueOf)
                .collect(Collectors.joining(", ")))
            .setCaption(ForeignUi.getMessage("table.column.periods"))
            .setComparator((grantSet1, grantSet2) -> grantSet1.getPeriods().toString()
                .compareTo(grantSet2.getPeriods().toString()))
            .setWidth(580);
        grid.addColumn(grantSet -> BooleanUtils.toYNString(grantSet.getEditable()))
            .setCaption(ForeignUi.getMessage("table.column.editable"))
            .setComparator((grantSet1, grantSet2) -> grantSet1.getEditable().compareTo(grantSet2.getEditable()))
            .setWidth(80);
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
        deleteButton.addClickListener(event ->
            deleteGrantSet(grid.getSelectedItems().stream().findFirst().orElse(null)));
        deleteButton.setEnabled(false);
        HorizontalLayout layout = new HorizontalLayout(deleteButton, closeButton);
        layout.setSpacing(true);
        VaadinUtils.setButtonsAutoDisabled(deleteButton);
        VaadinUtils.addComponentStyle(layout, "view-acl-grant-set-buttons");
        return layout;
    }

    private void initMediator() {
        ViewAclGrantSetMediator mediator = new ViewAclGrantSetMediator();
        mediator.setDeleteButton(deleteButton);
        mediator.applyPermissions();
    }

    private void deleteGrantSet(AclGrantSet grantSet) {
        List<String> scenarioNames = controller.getScenarioNamesAssociatedWithGrantSet(grantSet.getId());
        if (CollectionUtils.isEmpty(scenarioNames)) {
            Windows.showConfirmDialog(
                ForeignUi.getMessage("message.confirm.delete_action", grantSet.getName(), "grant set"),
                () -> {
                    controller.deleteAclGrantSet(grantSet);
                    grid.setItems(controller.getAllAclGrantSets());
                });
        } else {
            Windows.showNotificationWindow(
                buildNotificationMessage("message.error.delete_action", "Grant set", "scenario(s)", scenarioNames));
        }
    }

    private String buildNotificationMessage(String key, String param, String associatedField,
                                            List<String> associatedNames) {
        StringBuilder htmlNamesList = new StringBuilder("<ul>");
        for (String name : associatedNames) {
            htmlNamesList.append("<li>").append(name).append("</li>");
        }
        htmlNamesList.append("</ul>");
        return ForeignUi.getMessage(key, param, associatedField, htmlNamesList.toString());
    }
}
