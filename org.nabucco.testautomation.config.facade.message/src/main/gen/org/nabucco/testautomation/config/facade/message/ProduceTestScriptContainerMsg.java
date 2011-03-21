/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message;

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
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;

/**
 * ProduceTestScriptContainerMsg<p/>Message for producing a TestScriptContainer<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-10-12
 */
public class ProduceTestScriptContainerMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String TESTSCRIPT = "testScript";

    public static final String TESTSCRIPTCONTAINER = "testScriptContainer";

    private TestScript testScript;

    private TestScriptContainer testScriptContainer;

    /** Constructs a new ProduceTestScriptContainerMsg instance. */
    public ProduceTestScriptContainerMsg() {
        super();
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(TESTSCRIPT, PropertyDescriptorSupport.createDatatype(TESTSCRIPT,
                TestScript.class, 0, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPONENT));
        propertyMap.put(TESTSCRIPTCONTAINER, PropertyDescriptorSupport.createDatatype(
                TESTSCRIPTCONTAINER, TestScriptContainer.class, 1, PROPERTY_CONSTRAINTS[1], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public List<NabuccoProperty> getProperties() {
        List<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(
                ProduceTestScriptContainerMsg.getPropertyDescriptor(TESTSCRIPT), this.testScript));
        properties.add(super.createProperty(
                ProduceTestScriptContainerMsg.getPropertyDescriptor(TESTSCRIPTCONTAINER),
                this.testScriptContainer));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TESTSCRIPT) && (property.getType() == TestScript.class))) {
            this.setTestScript(((TestScript) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TESTSCRIPTCONTAINER) && (property.getType() == TestScriptContainer.class))) {
            this.setTestScriptContainer(((TestScriptContainer) property.getInstance()));
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
        final ProduceTestScriptContainerMsg other = ((ProduceTestScriptContainerMsg) obj);
        if ((this.testScript == null)) {
            if ((other.testScript != null))
                return false;
        } else if ((!this.testScript.equals(other.testScript)))
            return false;
        if ((this.testScriptContainer == null)) {
            if ((other.testScriptContainer != null))
                return false;
        } else if ((!this.testScriptContainer.equals(other.testScriptContainer)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.testScript == null) ? 0 : this.testScript.hashCode()));
        result = ((PRIME * result) + ((this.testScriptContainer == null) ? 0
                : this.testScriptContainer.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getTestScript.
     *
     * @return the TestScript.
     */
    public TestScript getTestScript() {
        return this.testScript;
    }

    /**
     * Missing description at method setTestScript.
     *
     * @param testScript the TestScript.
     */
    public void setTestScript(TestScript testScript) {
        this.testScript = testScript;
    }

    /**
     * Missing description at method getTestScriptContainer.
     *
     * @return the TestScriptContainer.
     */
    public TestScriptContainer getTestScriptContainer() {
        return this.testScriptContainer;
    }

    /**
     * Missing description at method setTestScriptContainer.
     *
     * @param testScriptContainer the TestScriptContainer.
     */
    public void setTestScriptContainer(TestScriptContainer testScriptContainer) {
        this.testScriptContainer = testScriptContainer;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ProduceTestScriptContainerMsg.class)
                .getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ProduceTestScriptContainerMsg.class)
                .getAllProperties();
    }
}
