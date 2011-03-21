/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.datatype.attribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.testautomation.schema.facade.datatype.attribute.Attribute;

/**
 * AttributeValue<p/>The base type for the value of a custom attribute<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-09
 */
public abstract class AttributeValue extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String ATTRIBUTE = "attribute";

    private Attribute attribute;

    private Long attributeRefId;

    /** Constructs a new AttributeValue instance. */
    public AttributeValue() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the AttributeValue.
     */
    protected void cloneObject(AttributeValue clone) {
        super.cloneObject(clone);
        if ((this.getAttribute() != null)) {
            clone.setAttribute(this.getAttribute().cloneObject());
        }
        if ((this.getAttributeRefId() != null)) {
            clone.setAttributeRefId(this.getAttributeRefId());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class)
                .getPropertyMap());
        propertyMap.put(ATTRIBUTE, PropertyDescriptorSupport.createDatatype(ATTRIBUTE,
                Attribute.class, 2, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPONENT));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty> getProperties() {
        List<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(AttributeValue.getPropertyDescriptor(ATTRIBUTE),
                this.attribute, this.attributeRefId));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ATTRIBUTE) && (property.getType() == Attribute.class))) {
            this.setAttribute(((Attribute) property.getInstance()));
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
        final AttributeValue other = ((AttributeValue) obj);
        if ((this.attribute == null)) {
            if ((other.attribute != null))
                return false;
        } else if ((!this.attribute.equals(other.attribute)))
            return false;
        if ((this.attributeRefId == null)) {
            if ((other.attributeRefId != null))
                return false;
        } else if ((!this.attributeRefId.equals(other.attributeRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.attribute == null) ? 0 : this.attribute.hashCode()));
        result = ((PRIME * result) + ((this.attributeRefId == null) ? 0 : this.attributeRefId
                .hashCode()));
        return result;
    }

    @Override
    public abstract AttributeValue cloneObject();

    /**
     * Missing description at method setAttribute.
     *
     * @param attribute the Attribute.
     */
    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
        if ((attribute != null)) {
            this.setAttributeRefId(attribute.getId());
        } else {
            this.setAttributeRefId(null);
        }
    }

    /**
     * Missing description at method getAttribute.
     *
     * @return the Attribute.
     */
    public Attribute getAttribute() {
        return this.attribute;
    }

    /**
     * Getter for the AttributeRefId.
     *
     * @return the Long.
     */
    public Long getAttributeRefId() {
        return this.attributeRefId;
    }

    /**
     * Setter for the AttributeRefId.
     *
     * @param attributeRefId the Long.
     */
    public void setAttributeRefId(Long attributeRefId) {
        this.attributeRefId = attributeRefId;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(AttributeValue.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(AttributeValue.class).getAllProperties();
    }
}
