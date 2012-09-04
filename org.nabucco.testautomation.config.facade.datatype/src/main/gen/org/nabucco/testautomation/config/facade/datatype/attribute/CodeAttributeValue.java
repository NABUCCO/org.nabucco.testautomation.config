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
package org.nabucco.testautomation.config.facade.datatype.attribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;

/**
 * CodeAttributeValue<p/>The code value of an attribute<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-09
 */
public class CodeAttributeValue extends AttributeValue implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;" };

    public static final String VALUE = "value";

    private Code value;

    private Long valueRefId;

    /** Constructs a new CodeAttributeValue instance. */
    public CodeAttributeValue() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the CodeAttributeValue.
     */
    protected void cloneObject(CodeAttributeValue clone) {
        super.cloneObject(clone);
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
        }
        if ((this.getValueRefId() != null)) {
            clone.setValueRefId(this.getValueRefId());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(AttributeValue.class).getPropertyMap());
        propertyMap.put(VALUE, PropertyDescriptorSupport.createDatatype(VALUE, Code.class, 4, PROPERTY_CONSTRAINTS[0],
                false, PropertyAssociationType.COMPONENT));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(CodeAttributeValue.getPropertyDescriptor(VALUE), this.getValue(),
                this.valueRefId));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(VALUE) && (property.getType() == Code.class))) {
            this.setValue(((Code) property.getInstance()));
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
        final CodeAttributeValue other = ((CodeAttributeValue) obj);
        if ((this.value == null)) {
            if ((other.value != null))
                return false;
        } else if ((!this.value.equals(other.value)))
            return false;
        if ((this.valueRefId == null)) {
            if ((other.valueRefId != null))
                return false;
        } else if ((!this.valueRefId.equals(other.valueRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        result = ((PRIME * result) + ((this.valueRefId == null) ? 0 : this.valueRefId.hashCode()));
        return result;
    }

    @Override
    public CodeAttributeValue cloneObject() {
        CodeAttributeValue clone = new CodeAttributeValue();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method setValue.
     *
     * @param value the Code.
     */
    public void setValue(Code value) {
        this.value = value;
        if ((value != null)) {
            this.setValueRefId(value.getId());
        } else {
            this.setValueRefId(null);
        }
    }

    /**
     * Missing description at method getValue.
     *
     * @return the Code.
     */
    public Code getValue() {
        return this.value;
    }

    /**
     * Getter for the ValueRefId.
     *
     * @return the Long.
     */
    public Long getValueRefId() {
        return this.valueRefId;
    }

    /**
     * Setter for the ValueRefId.
     *
     * @param valueRefId the Long.
     */
    public void setValueRefId(Long valueRefId) {
        this.valueRefId = valueRefId;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(CodeAttributeValue.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(CodeAttributeValue.class).getAllProperties();
    }
}
