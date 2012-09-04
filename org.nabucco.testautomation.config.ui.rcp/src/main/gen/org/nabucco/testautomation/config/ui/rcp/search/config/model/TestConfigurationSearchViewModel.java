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
package org.nabucco.testautomation.config.ui.rcp.search.config.model;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchParameter;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchViewModel;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

/**
 * TestConfigurationSearchViewModel<p/>Search view for datatype TestConfiguration<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class TestConfigurationSearchViewModel extends NabuccoComponentSearchViewModel<TestConfiguration> implements
        NabuccoComponentSearchParameter {

    public static final String ID = "org.nabucco.testautomation.config.ui.search.config.TestConfigurationSearchViewModel";

    private TestConfiguration testConfiguration;

    public static final String PROPERTY_TESTCONFIGURATION_NAME = "testConfigurationName";

    public static final String PROPERTY_TESTCONFIGURATION_IDENTIFICATIONKEY = "testConfigurationIdentificationKey";

    public static String TITLE = (ID + "Title");

    /**
     * Constructs a new TestConfigurationSearchViewModel instance.
     *
     * @param viewId the String.
     */
    public TestConfigurationSearchViewModel(String viewId) {
        super();
        correspondingListView = viewId;
        this.testConfiguration = new TestConfiguration();
    }

    @Override
    public String getSearchModelId() {
        return searchModelId;
    }

    @Override
    public NabuccoComponentSearchParameter getSearchParameter() {
        return this;
    }

    /**
     * Getter for the TestConfiguration.
     *
     * @return the TestConfiguration.
     */
    public TestConfiguration getTestConfiguration() {
        return this.testConfiguration;
    }

    /**
     * Setter for the TestConfigurationName.
     *
     * @param newName the String.
     */
    public void setTestConfigurationName(String newName) {
        if (((testConfiguration != null) && (testConfiguration.getName() == null))) {
            Name name = new Name();
            testConfiguration.setName(name);
        }
        String oldVal = testConfiguration.getName().getValue();
        testConfiguration.getName().setValue(newName);
        this.updateProperty(PROPERTY_TESTCONFIGURATION_NAME, oldVal, newName);
        if (((!oldVal.equals(newName)) && testConfiguration.getDatatypeState().equals(DatatypeState.PERSISTENT))) {
            testConfiguration.setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Getter for the TestConfigurationName.
     *
     * @return the String.
     */
    public String getTestConfigurationName() {
        if ((((testConfiguration == null) || (testConfiguration.getName() == null)) || (testConfiguration.getName()
                .getValue() == null))) {
            return "";
        }
        return testConfiguration.getName().getValue();
    }

    /**
     * Setter for the TestConfigurationIdentificationKey.
     *
     * @param newIdentificationKey the String.
     */
    public void setTestConfigurationIdentificationKey(String newIdentificationKey) {
        if (((testConfiguration != null) && (testConfiguration.getIdentificationKey() == null))) {
            Key identificationKey = new Key();
            testConfiguration.setIdentificationKey(identificationKey);
        }
        String oldVal = testConfiguration.getIdentificationKey().getValue();
        testConfiguration.getIdentificationKey().setValue(newIdentificationKey);
        this.updateProperty(PROPERTY_TESTCONFIGURATION_IDENTIFICATIONKEY, oldVal, newIdentificationKey);
        if (((!oldVal.equals(newIdentificationKey)) && testConfiguration.getDatatypeState().equals(
                DatatypeState.PERSISTENT))) {
            testConfiguration.setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Getter for the TestConfigurationIdentificationKey.
     *
     * @return the String.
     */
    public String getTestConfigurationIdentificationKey() {
        if ((((testConfiguration == null) || (testConfiguration.getIdentificationKey() == null)) || (testConfiguration
                .getIdentificationKey().getValue() == null))) {
            return "";
        }
        return testConfiguration.getIdentificationKey().getValue();
    }

    @Override
    public String getId() {
        return TestConfigurationSearchViewModel.ID;
    }
}
