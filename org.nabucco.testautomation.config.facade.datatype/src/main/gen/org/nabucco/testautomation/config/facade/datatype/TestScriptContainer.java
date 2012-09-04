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
package org.nabucco.testautomation.config.facade.datatype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Order;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;

/**
 * TestScriptContainer<p/>Container holding a TestScript and its order position<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-30-09
 */
public class TestScriptContainer extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String TESTSCRIPT = "testScript";

    public static final String ORDERINDEX = "orderIndex";

    private TestScript testScript;

    private Long testScriptRefId;

    private Order orderIndex;

    /** Constructs a new TestScriptContainer instance. */
    public TestScriptContainer() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TestScriptContainer.
     */
    protected void cloneObject(TestScriptContainer clone) {
        super.cloneObject(clone);
        if ((this.getTestScript() != null)) {
            clone.setTestScript(this.getTestScript().cloneObject());
        }
        if ((this.getTestScriptRefId() != null)) {
            clone.setTestScriptRefId(this.getTestScriptRefId());
        }
        if ((this.getOrderIndex() != null)) {
            clone.setOrderIndex(this.getOrderIndex().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(TESTSCRIPT, PropertyDescriptorSupport.createDatatype(TESTSCRIPT, TestScript.class, 3,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(ORDERINDEX,
                PropertyDescriptorSupport.createBasetype(ORDERINDEX, Order.class, 4, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TestScriptContainer.getPropertyDescriptor(TESTSCRIPT),
                this.getTestScript(), this.testScriptRefId));
        properties.add(super.createProperty(TestScriptContainer.getPropertyDescriptor(ORDERINDEX), this.orderIndex,
                null));
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
        } else if ((property.getName().equals(ORDERINDEX) && (property.getType() == Order.class))) {
            this.setOrderIndex(((Order) property.getInstance()));
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
        final TestScriptContainer other = ((TestScriptContainer) obj);
        if ((this.testScript == null)) {
            if ((other.testScript != null))
                return false;
        } else if ((!this.testScript.equals(other.testScript)))
            return false;
        if ((this.testScriptRefId == null)) {
            if ((other.testScriptRefId != null))
                return false;
        } else if ((!this.testScriptRefId.equals(other.testScriptRefId)))
            return false;
        if ((this.orderIndex == null)) {
            if ((other.orderIndex != null))
                return false;
        } else if ((!this.orderIndex.equals(other.orderIndex)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.testScript == null) ? 0 : this.testScript.hashCode()));
        result = ((PRIME * result) + ((this.testScriptRefId == null) ? 0 : this.testScriptRefId.hashCode()));
        result = ((PRIME * result) + ((this.orderIndex == null) ? 0 : this.orderIndex.hashCode()));
        return result;
    }

    @Override
    public TestScriptContainer cloneObject() {
        TestScriptContainer clone = new TestScriptContainer();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method setTestScript.
     *
     * @param testScript the TestScript.
     */
    public void setTestScript(TestScript testScript) {
        this.testScript = testScript;
        if ((testScript != null)) {
            this.setTestScriptRefId(testScript.getId());
        } else {
            this.setTestScriptRefId(null);
        }
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
     * Getter for the TestScriptRefId.
     *
     * @return the Long.
     */
    public Long getTestScriptRefId() {
        return this.testScriptRefId;
    }

    /**
     * Setter for the TestScriptRefId.
     *
     * @param testScriptRefId the Long.
     */
    public void setTestScriptRefId(Long testScriptRefId) {
        this.testScriptRefId = testScriptRefId;
    }

    /**
     * Missing description at method getOrderIndex.
     *
     * @return the Order.
     */
    public Order getOrderIndex() {
        return this.orderIndex;
    }

    /**
     * Missing description at method setOrderIndex.
     *
     * @param orderIndex the Order.
     */
    public void setOrderIndex(Order orderIndex) {
        this.orderIndex = orderIndex;
    }

    /**
     * Missing description at method setOrderIndex.
     *
     * @param orderIndex the Integer.
     */
    public void setOrderIndex(Integer orderIndex) {
        if ((this.orderIndex == null)) {
            if ((orderIndex == null)) {
                return;
            }
            this.orderIndex = new Order();
        }
        this.orderIndex.setValue(orderIndex);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TestScriptContainer.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TestScriptContainer.class).getAllProperties();
    }
}
