package com.copyright.rup.dist.foreign.ui.main;

import com.copyright.rup.common.logging.RupLogUtils;

import org.slf4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Context listener which will be used to avoid memory leaks during container shutdown.
 * Performs deregister of all sql Drivers.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
public class ContextDestroyListener implements ServletContextListener {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //stub method
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                DriverManager.deregisterDriver(drivers.nextElement());
            }
        } catch (Exception e) {
            LOGGER.error("Exception caught while deregister JDBC drivers", e);
        }
    }
}
