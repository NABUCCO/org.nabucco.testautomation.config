/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.testautomation.config.facade.component;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.locator.ComponentLocator;
import org.nabucco.framework.base.facade.component.locator.ComponentLocatorSupport;

/**
 * Locator for ConfigComponent.
 *
 * @author NABUCCO Generator, PRODYNA AG
 */
public class ConfigComponentLocator extends ComponentLocatorSupport<ConfigComponent> implements
        ComponentLocator<ConfigComponent> {

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

    @Override
    public ConfigComponent getComponent() throws ConnectionException {
        ConfigComponent component = super.getComponent();
        if ((component instanceof ConfigComponentLocal)) {
            return new ConfigComponentLocalProxy(((ConfigComponentLocal) component));
        }
        return component;
    }

    /**
     * Getter for the Instance.
     *
     * @return the ConfigComponentLocator.
     */
    public static ConfigComponentLocator getInstance() {
        if ((instance == null)) {
            instance = new ConfigComponentLocator(ConfigComponent.JNDI_NAME, ConfigComponent.class);
        }
        return instance;
    }
}
