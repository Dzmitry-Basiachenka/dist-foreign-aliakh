package com.copyright.rup.dist.foreign.ui.main.api;

/**
 * Stores and provides globally selected product family.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/4/19
 *
 * @author Stanislau Rudak
 */
// TODO {srudak} check whether we need to keep separate interfaces
public interface ISettableProductFamilyProvider extends IProductFamilyProvider {

    /**
     * Sets product family selection.
     *
     * @param productFamily product family
     */
    void setProductFamily(String productFamily);
}
