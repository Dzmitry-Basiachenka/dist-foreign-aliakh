package com.copyright.rup.dist.foreign.repository.api;

/**
 * Represents domain to hold property and direction for sorting.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/17
 *
 * @author Aliaksei Pchelnikau
 */
// TODO {mbezmen} move to dist-common
public class Sort {

    private String property;
    private String direction;

    /**
     * Constructor.
     *
     * @param property  property to sort
     * @param direction sorting direction
     */
    public Sort(String property, Direction direction) {
        this.property = property;
        this.direction = direction.getValue();
    }

    /**
     * @return property to sort.
     */
    public String getProperty() {
        return property;
    }

    /**
     * @return sorting direction.
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sort directions enum.
     */
    public enum Direction {
        /**
         * Ascending direction.
         */
        ASC("asc"),
        /**
         * Descending direction.
         */
        DESC("desc");

        private String value;

        /**
         * Constructor.
         *
         * @param value direction value
         */
        Direction(String value) {
            this.value = value;
        }

        /**
         * Returns direction by boolean parameter.
         *
         * @param ascending ascending
         * @return {@link #ASC} for true, {@link #DESC} for false.
         */
        public static Direction of(boolean ascending) {
            return ascending ? ASC : DESC;
        }

        /**
         * @return direction value.
         */
        public String getValue() {
            return value;
        }
    }
}
