/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.search.config.model;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Description;
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
public class TestConfigurationSearchViewModel extends
        NabuccoComponentSearchViewModel<TestConfiguration> implements
        NabuccoComponentSearchParameter {

    public static final String ID = "org.nabucco.testautomation.config.ui.search.config.TestConfigSearchViewModel";

    private TestConfiguration testConfiguration;

    public static final String PROPERTY_TESTCONFIGURATION_NAME = "testConfigurationName";

    public static final String PROPERTY_TESTCONFIGURATION_DESCRIPTION = "testConfigurationDescription";

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
        if (((!oldVal.equals(newName)) && testConfiguration.getDatatypeState().equals(
                DatatypeState.PERSISTENT))) {
            testConfiguration.setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Getter for the TestConfigurationName.
     *
     * @return the String.
     */
    public String getTestConfigurationName() {
        if ((((testConfiguration == null) || (testConfiguration.getName() == null)) || (testConfiguration
                .getName().getValue() == null))) {
            return "";
        }
        return testConfiguration.getName().getValue();
    }

    /**
     * Setter for the TestConfigurationDescription.
     *
     * @param newDescription the String.
     */
    public void setTestConfigurationDescription(String newDescription) {
        if (((testConfiguration != null) && (testConfiguration.getDescription() == null))) {
            Description description = new Description();
            testConfiguration.setDescription(description);
        }
        String oldVal = testConfiguration.getDescription().getValue();
        testConfiguration.getDescription().setValue(newDescription);
        this.updateProperty(PROPERTY_TESTCONFIGURATION_DESCRIPTION, oldVal, newDescription);
        if (((!oldVal.equals(newDescription)) && testConfiguration.getDatatypeState().equals(
                DatatypeState.PERSISTENT))) {
            testConfiguration.setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Getter for the TestConfigurationDescription.
     *
     * @return the String.
     */
    public String getTestConfigurationDescription() {
        if ((((testConfiguration == null) || (testConfiguration.getDescription() == null)) || (testConfiguration
                .getDescription().getValue() == null))) {
            return "";
        }
        return testConfiguration.getDescription().getValue();
    }

    @Override
    public String getId() {
        return TestConfigurationSearchViewModel.ID;
    }
}
