package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

/**
 * Interface for controller for UDM baseline value filtering.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineValueFilterController extends IFilterController<IUdmBaselineValueFilterWidget> {

    /**
     * Gets all available periods for baseline value.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Gets list of all {@link PublicationType}s.
     *
     * @return list of {@link PublicationType}
     */
    List<PublicationType> getPublicationTypes();
}
