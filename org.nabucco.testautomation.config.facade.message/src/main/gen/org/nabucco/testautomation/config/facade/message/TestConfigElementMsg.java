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
package org.nabucco.testautomation.config.facade.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;

/**
 * TestConfigElementMsg<p/>Message for transporting a TestConfigElement<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class TestConfigElementMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String TESTCONFIGELEMENT = "testConfigElement";

    public static final String TESTCONFIGELEMENTCONTAINER = "testConfigElementContainer";

    private TestConfigElement testConfigElement;

    private TestConfigElementContainer testConfigElementContainer;

    /** Constructs a new TestConfigElementMsg instance. */
    public TestConfigElementMsg() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(TESTCONFIGELEMENT, PropertyDescriptorSupport.createDatatype(TESTCONFIGELEMENT,
                TestConfigElement.class, 0, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TESTCONFIGELEMENTCONTAINER, PropertyDescriptorSupport.createDatatype(
                TESTCONFIGELEMENTCONTAINER, TestConfigElementContainer.class, 1, PROPERTY_CONSTRAINTS[1], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TestConfigElementMsg.getPropertyDescriptor(TESTCONFIGELEMENT),
                this.getTestConfigElement()));
        properties.add(super.createProperty(TestConfigElementMsg.getPropertyDescriptor(TESTCONFIGELEMENTCONTAINER),
                this.getTestConfigElementContainer()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TESTCONFIGELEMENT) && (property.getType() == TestConfigElement.class))) {
            this.setTestConfigElement(((TestConfigElement) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TESTCONFIGELEMENTCONTAINER) && (property.getType() == TestConfigElementContainer.class))) {
            this.setTestConfigElementContainer(((TestConfigElementContainer) property.getInstance()));
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
        final TestConfigElementMsg other = ((TestConfigElementMsg) obj);
        if ((this.testConfigElement == null)) {
            if ((other.testConfigElement != null))
                return false;
        } else if ((!this.testConfigElement.equals(other.testConfigElement)))
            return false;
        if ((this.testConfigElementContainer == null)) {
            if ((other.testConfigElementContainer != null))
                return false;
        } else if ((!this.testConfigElementContainer.equals(other.testConfigElementContainer)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.testConfigElement == null) ? 0 : this.testConfigElement.hashCode()));
        result = ((PRIME * result) + ((this.testConfigElementContainer == null) ? 0 : this.testConfigElementContainer
                .hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getTestConfigElement.
     *
     * @return the TestConfigElement.
     */
    public TestConfigElement getTestConfigElement() {
        return this.testConfigElement;
    }

    /**
     * Missing description at method setTestConfigElement.
     *
     * @param testConfigElement the TestConfigElement.
     */
    public void setTestConfigElement(TestConfigElement testConfigElement) {
        this.testConfigElement = testConfigElement;
    }

    /**
     * Missing description at method getTestConfigElementContainer.
     *
     * @return the TestConfigElementContainer.
     */
    public TestConfigElementContainer getTestConfigElementContainer() {
        return this.testConfigElementContainer;
    }

    /**
     * Missing description at method setTestConfigElementContainer.
     *
     * @param testConfigElementContainer the TestConfigElementContainer.
     */
    public void setTestConfigElementContainer(TestConfigElementContainer testConfigElementContainer) {
        this.testConfigElementContainer = testConfigElementContainer;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TestConfigElementMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TestConfigElementMsg.class).getAllProperties();
    }
}
