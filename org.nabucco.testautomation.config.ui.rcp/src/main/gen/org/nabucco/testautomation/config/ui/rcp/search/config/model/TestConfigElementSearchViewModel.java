/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.search.config.model;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchParameter;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchViewModel;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;

/**
 * TestConfigElementSearchViewModel<p/>Search view for datatype TestConfigElement<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2010-09-27
 */
public class TestConfigElementSearchViewModel extends
        NabuccoComponentSearchViewModel<TestConfigElement> implements
        NabuccoComponentSearchParameter {

    public static final String ID = "org.nabucco.testautomation.config.ui.search.config.TestConfigElementSearchViewModel";

    private TestConfigElement testConfigElement;

    public static final String PROPERTY_TESTCONFIGELEMENT_NAME = "testConfigElementName";

    public static final String PROPERTY_TESTCONFIGELEMENT_DESCRIPTION = "testConfigElementDescription";

    public static String TITLE = (ID + "Title");

    /**
     * Constructs a new TestConfigElementSearchViewModel instance.
     *
     * @param viewId the String.
     */
    public TestConfigElementSearchViewModel(String viewId) {
        super();
        correspondingListView = viewId;
        this.testConfigElement = new TestConfigElement();
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
     * Getter for the TestConfigElement.
     *
     * @return the TestConfigElement.
     */
    public TestConfigElement getTestConfigElement() {
        return this.testConfigElement;
    }

    /**
     * Setter for the TestConfigElementName.
     *
     * @param newName the String.
     */
    public void setTestConfigElementName(String newName) {
        if (((testConfigElement != null) && (testConfigElement.getName() == null))) {
            Name name = new Name();
            testConfigElement.setName(name);
        }
        String oldVal = testConfigElement.getName().getValue();
        testConfigElement.getName().setValue(newName);
        this.updateProperty(PROPERTY_TESTCONFIGELEMENT_NAME, oldVal, newName);
        if (((!oldVal.equals(newName)) && testConfigElement.getDatatypeState().equals(
                DatatypeState.PERSISTENT))) {
            testConfigElement.setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Getter for the TestConfigElementName.
     *
     * @return the String.
     */
    public String getTestConfigElementName() {
        if ((((testConfigElement == null) || (testConfigElement.getName() == null)) || (testConfigElement
                .getName().getValue() == null))) {
            return "";
        }
        return testConfigElement.getName().getValue();
    }

    /**
     * Setter for the TestConfigElementDescription.
     *
     * @param newDescription the String.
     */
    public void setTestConfigElementDescription(String newDescription) {
        if (((testConfigElement != null) && (testConfigElement.getDescription() == null))) {
            Description description = new Description();
            testConfigElement.setDescription(description);
        }
        String oldVal = testConfigElement.getDescription().getValue();
        testConfigElement.getDescription().setValue(newDescription);
        this.updateProperty(PROPERTY_TESTCONFIGELEMENT_DESCRIPTION, oldVal, newDescription);
        if (((!oldVal.equals(newDescription)) && testConfigElement.getDatatypeState().equals(
                DatatypeState.PERSISTENT))) {
            testConfigElement.setDatatypeState(DatatypeState.MODIFIED);
        }
    }

    /**
     * Getter for the TestConfigElementDescription.
     *
     * @return the String.
     */
    public String getTestConfigElementDescription() {
        if ((((testConfigElement == null) || (testConfigElement.getDescription() == null)) || (testConfigElement
                .getDescription().getValue() == null))) {
            return "";
        }
        return testConfigElement.getDescription().getValue();
    }

    @Override
    public String getId() {
        return TestConfigElementSearchViewModel.ID;
    }
}
