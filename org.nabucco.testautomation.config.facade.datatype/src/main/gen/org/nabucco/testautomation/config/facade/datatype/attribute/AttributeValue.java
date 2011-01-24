/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.datatype.attribute;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.testautomation.schema.facade.datatype.attribute.Attribute;

/**
 * AttributeValue<p/>The base type for the value of a custom attribute<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-09
 */
public abstract class AttributeValue extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "attribute" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

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

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<Attribute>(PROPERTY_NAMES[0], Attribute.class,
                PROPERTY_CONSTRAINTS[0], this.attribute));
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
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<AttributeValue>\n");
        appendable.append(super.toString());
        appendable.append((("<attribute>" + this.attribute) + "</attribute>\n"));
        appendable.append((("<attributeRefId>" + this.attributeRefId) + "</attributeRefId>\n"));
        appendable.append("</AttributeValue>\n");
        return appendable.toString();
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
}
