package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.ui.status.api.IUdmBatchStatusController;
import com.copyright.rup.vaadin.widget.api.IController;

/**
 * Interface for UDM tab controller.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/27/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmController extends IController<IUdmWidget> {

    /**
     * @return instance of {@link IUdmUsageController}.
     */
    IUdmUsageController getUdmUsageController();

    /**
     * @return instance of {@link IUdmValueController}.
     */
    IUdmValueController getUdmValueController();

    /**
     * @return instance of {@link IUdmProxyValueController}.
     */
    IUdmProxyValueController getUdmProxyValueController();

    /**
     * @return instance of {@link IUdmBaselineController}
     */
    IUdmBaselineController getUdmBaselineController();

    /**
     * @return instance of {@link IUdmBaselineValueController}
     */
    IUdmBaselineValueController getUdmBaselineValueController();

    /**
     * @return instance of {@link IUdmBatchStatusController}
     */
    IUdmBatchStatusController getUdmBatchStatusController();
}
