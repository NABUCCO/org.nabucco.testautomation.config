/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.component;

import org.nabucco.framework.base.facade.component.locator.ComponentLocator;
import org.nabucco.framework.base.facade.component.locator.ComponentLocatorSupport;

/**
 * Locator for ConfigComponent.
 *
 * @author NABUCCO Generator, PRODYNA AG
 */
public class ConfigComponentLocator extends ComponentLocatorSupport<ConfigComponent> implements
        ComponentLocator<ConfigComponent> {

    private static final String JNDI_NAME = ((((ComponentLocator.COMPONENTS + "/") + ConfigComponent.COMPONENT_NAME) + "/") + "org.nabucco.testautomation.config.facade.component.ConfigComponent");

    private static ConfigComponentLocator instance;

    /**
     * Constructs a new ConfigComponentLocator instance.
     *
     * @param component the Class<ConfigComponent>.
     * @param jndiName the String.
     */
    private ConfigComponentLocator(String jndiName, Class<ConfigComponent> component) {
        super(jndiName, component);
    }

    /**
     * Getter for the Instance.
     *
     * @return the ConfigComponentLocator.
     */
    public static ConfigComponentLocator getInstance() {
        if ((instance == null)) {
            instance = new ConfigComponentLocator(JNDI_NAME, ConfigComponent.class);
        }
        return instance;
    }
}
