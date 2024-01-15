package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;

/**
 * Represents window with ability to exclude details by Payee.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/2019
 *
 * @author Uladzislau_Shalamitski
 */
public class FasExcludePayeeWidget extends CommonDialog implements IFasExcludePayeeWidget {

    private static final long serialVersionUID = -7110342271534547788L;
    private IFasExcludePayeeController controller;

    @Override
    public void setController(IFasExcludePayeeController controller) {
        this.controller = controller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FasExcludePayeeWidget init() {
        IFasExcludePayeeFilterWidget filterWidget = controller.getExcludePayeesFilterController().initWidget();
        super.setWidth("1200px");
        super.setHeight("500px");
        super.setHeaderTitle(ForeignUi.getMessage("window.exclude.payee"));
        super.setModalWindowProperties("exclude-details-by-payee-window", true);
        super.getFooter().add(createButtonsLayout());
        var splitPanel = new SplitLayout();
        splitPanel.setSizeFull();
        splitPanel.addToPrimary((FasExcludePayeeFilterWidget) filterWidget);
        splitPanel.setSplitterPosition(18);
        add(splitPanel);
        return this;
    }

    private HorizontalLayout createButtonsLayout() {
        var excludeDetails = Buttons.createButton(ForeignUi.getMessage("button.exclude_details"));
        var redesignateDetails = Buttons.createButton(ForeignUi.getMessage("button.redesignate_details"));
        var clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        VaadinUtils.setButtonsAutoDisabled(excludeDetails, redesignateDetails);
        return new HorizontalLayout(excludeDetails, redesignateDetails, clearButton, Buttons.createCloseButton(this));
    }
}
