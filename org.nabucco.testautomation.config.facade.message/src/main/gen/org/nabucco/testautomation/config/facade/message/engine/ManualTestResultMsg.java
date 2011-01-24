/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message.engine;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
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

    private static final String[] PROPERTY_NAMES = { "testExecutionInfo",
            "testEngineConfiguration", "testResult" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;" };

    private TestExecutionInfo testExecutionInfo;

    private TestEngineConfiguration testEngineConfiguration;

    private ManualTestResult testResult;

    /** Constructs a new ManualTestResultMsg instance. */
    public ManualTestResultMsg() {
        super();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<TestExecutionInfo>(PROPERTY_NAMES[0],
                TestExecutionInfo.class, PROPERTY_CONSTRAINTS[0], this.testExecutionInfo));
        properties.add(new DatatypeProperty<TestEngineConfiguration>(PROPERTY_NAMES[1],
                TestEngineConfiguration.class, PROPERTY_CONSTRAINTS[1],
                this.testEngineConfiguration));
        properties.add(new DatatypeProperty<ManualTestResult>(PROPERTY_NAMES[2],
                ManualTestResult.class, PROPERTY_CONSTRAINTS[2], this.testResult));
        return properties;
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
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<ManualTestResultMsg>\n");
        appendable.append(super.toString());
        appendable
                .append((("<testExecutionInfo>" + this.testExecutionInfo) + "</testExecutionInfo>\n"));
        appendable
                .append((("<testEngineConfiguration>" + this.testEngineConfiguration) + "</testEngineConfiguration>\n"));
        appendable.append((("<testResult>" + this.testResult) + "</testResult>\n"));
        appendable.append("</ManualTestResultMsg>\n");
        return appendable.toString();
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
}
