package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

/**
 * Interface for controller for UDM value filtering.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmValueFilterController extends IFilterController<IUdmValueFilterWidget> {

    /**
     * @return list of assignees.
     */
    List<String> getAssignees();
}
