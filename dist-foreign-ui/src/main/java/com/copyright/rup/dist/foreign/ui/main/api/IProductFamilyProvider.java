package com.copyright.rup.dist.foreign.ui.main.api;

import java.io.Serializable;

/**
 * Provides globally selected product family.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/4/19
 *
 * @author Stanislau Rudak
 */
public interface IProductFamilyProvider extends Serializable{

    /**
     * @return selected product family.
     */
    String getSelectedProductFamily();

    /**
     * Sets product family selection.
     *
     * @param productFamily product family
     */
    void setProductFamily(String productFamily);
}
