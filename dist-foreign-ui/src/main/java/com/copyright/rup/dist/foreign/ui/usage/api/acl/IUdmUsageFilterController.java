package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

/**
 * Interface for controller for UDM usage filtering.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/03/2021
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmUsageFilterController extends IFilterController<IUdmUsageFilterWidget> {

    /**
     * Gets all available periods.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Gets list of {@link UdmBatch}es.
     *
     * @return list of {@link UdmBatch}es
     */
    List<UdmBatch> getUdmBatches();

    /**
     * @return list of assignees.
     */
    List<String> getAssignees();

    /**
     * @return list of publication types.
     */
    List<String> getPublicationTypes();

    /**
     * @return list of publication formats.
     */
    List<String> getPublicationFormats();

    /**
     * Gets list of all reported type of uses.
     *
     * @return list of reported type of uses.
     */
    List<String> getReportedTypeOfUses();

    /**
     * @return list of detail licensee classes.
     */
    List<DetailLicenseeClass> getDetailLicenseeClasses();
}
