/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.facade.datatype.engine.TestEngineConfiguration;
import org.nabucco.testautomation.facade.datatype.engine.TestExecutionInfo;
import org.nabucco.testautomation.result.facade.datatype.manual.ManualTestResult;

/**
 * ManualTestResultMsg<p/>Message to transport a ManualTestResult-object<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-12-01
 */
public class ManualTestResultMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;" };

    public static final String TESTEXECUTIONINFO = "testExecutionInfo";

    public static final String TESTENGINECONFIGURATION = "testEngineConfiguration";

    public static final String TESTRESULT = "testResult";

    private TestExecutionInfo testExecutionInfo;

    private TestEngineConfiguration testEngineConfiguration;

    private ManualTestResult testResult;

    /** Constructs a new ManualTestResultMsg instance. */
    public ManualTestResultMsg() {
        super();
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(TESTEXECUTIONINFO, PropertyDescriptorSupport.createDatatype(
                TESTEXECUTIONINFO, TestExecutionInfo.class, 0, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPONENT));
        propertyMap.put(TESTENGINECONFIGURATION, PropertyDescriptorSupport.createDatatype(
                TESTENGINECONFIGURATION, TestEngineConfiguration.class, 1, PROPERTY_CONSTRAINTS[1],
                false, PropertyAssociationType.COMPONENT));
        propertyMap.put(TESTRESULT, PropertyDescriptorSupport.createDatatype(TESTRESULT,
                ManualTestResult.class, 2, PROPERTY_CONSTRAINTS[2], false,
                PropertyAssociationType.COMPONENT));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public List<NabuccoProperty> getProperties() {
        List<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(
                ManualTestResultMsg.getPropertyDescriptor(TESTEXECUTIONINFO),
                this.testExecutionInfo));
        properties.add(super.createProperty(
                ManualTestResultMsg.getPropertyDescriptor(TESTENGINECONFIGURATION),
                this.testEngineConfiguration));
        properties.add(super.createProperty(ManualTestResultMsg.getPropertyDescriptor(TESTRESULT),
                this.testResult));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TESTEXECUTIONINFO) && (property.getType() == TestExecutionInfo.class))) {
            this.setTestExecutionInfo(((TestExecutionInfo) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TESTENGINECONFIGURATION) && (property.getType() == TestEngineConfiguration.class))) {
            this.setTestEngineConfiguration(((TestEngineConfiguration) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TESTRESULT) && (property.getType() == ManualTestResult.class))) {
            this.setTestResult(((ManualTestResult) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final ManualTestResultMsg other = ((ManualTestResultMsg) obj);
        if ((this.testExecutionInfo == null)) {
            if ((other.testExecutionInfo != null))
                return false;
        } else if ((!this.testExecutionInfo.equals(other.testExecutionInfo)))
            return false;
        if ((this.testEngineConfiguration == null)) {
            if ((other.testEngineConfiguration != null))
                return false;
        } else if ((!this.testEngineConfiguration.equals(other.testEngineConfiguration)))
            return false;
        if ((this.testResult == null)) {
            if ((other.testResult != null))
                return false;
        } else if ((!this.testResult.equals(other.testResult)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.testExecutionInfo == null) ? 0 : this.testExecutionInfo
                .hashCode()));
        result = ((PRIME * result) + ((this.testEngineConfiguration == null) ? 0
                : this.testEngineConfiguration.hashCode()));
        result = ((PRIME * result) + ((this.testResult == null) ? 0 : this.testResult.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getTestExecutionInfo.
     *
     * @return the TestExecutionInfo.
     */
    public TestExecutionInfo getTestExecutionInfo() {
        return this.testExecutionInfo;
    }

    /**
     * Missing description at method setTestExecutionInfo.
     *
     * @param testExecutionInfo the TestExecutionInfo.
     */
    public void setTestExecutionInfo(TestExecutionInfo testExecutionInfo) {
        this.testExecutionInfo = testExecutionInfo;
    }

    /**
     * Missing description at method getTestEngineConfiguration.
     *
     * @return the TestEngineConfiguration.
     */
    public TestEngineConfiguration getTestEngineConfiguration() {
        return this.testEngineConfiguration;
    }

    /**
     * Missing description at method setTestEngineConfiguration.
     *
     * @param testEngineConfiguration the TestEngineConfiguration.
     */
    public void setTestEngineConfiguration(TestEngineConfiguration testEngineConfiguration) {
        this.testEngineConfiguration = testEngineConfiguration;
    }

    /**
     * Missing description at method getTestResult.
     *
     * @return the ManualTestResult.
     */
    public ManualTestResult getTestResult() {
        return this.testResult;
    }

    /**
     * Missing description at method setTestResult.
     *
     * @param testResult the ManualTestResult.
     */
    public void setTestResult(ManualTestResult testResult) {
        this.testResult = testResult;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ManualTestResultMsg.class)
                .getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ManualTestResultMsg.class).getAllProperties();
    }
}
