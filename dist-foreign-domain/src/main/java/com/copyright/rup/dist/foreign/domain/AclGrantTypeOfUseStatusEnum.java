package com.copyright.rup.dist.foreign.domain;

/**
 * Enum for grant type of use statuses.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Anton Azarenka
 */
public enum AclGrantTypeOfUseStatusEnum {

    /**
     * Type of use status for PRINT.
     */
    PRINT {
        @Override
        public String toString() {
            return "Print Only";
        }
    },
    /**
     * Type of use status for DIGITAL.
     */
    DIGITAL {
        @Override
        public String toString() {
            return "Digital Only";
        }
    },
    /**
     * Type of use status for PRINT_DIGITAL.
     */
    PRINT_DIGITAL {
        @Override
        public String toString() {
            return "Print&Digital";
        }
    },
    /**
     * Type of use status for DIFFERENT_RH.
     */
    DIFFERENT_RH {
        @Override
        public String toString() {
            return "Different RH";
        }
    }
}
