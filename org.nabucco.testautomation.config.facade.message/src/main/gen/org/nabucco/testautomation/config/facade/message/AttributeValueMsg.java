/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;

/**
 * AttributeValueMsg<p/>Message for transportation of an AttributeValue<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class AttributeValueMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "attributeValue" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    private AttributeValue attributeValue;

    /** Constructs a new AttributeValueMsg instance. */
    public AttributeValueMsg() {
        super();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<AttributeValue>(PROPERTY_NAMES[0],
                AttributeValue.class, PROPERTY_CONSTRAINTS[0], this.attributeValue));
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
        final AttributeValueMsg other = ((AttributeValueMsg) obj);
        if ((this.attributeValue == null)) {
            if ((other.attributeValue != null))
                return false;
        } else if ((!this.attributeValue.equals(other.attributeValue)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.attributeValue == null) ? 0 : this.attributeValue
                .hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<AttributeValueMsg>\n");
        appendable.append(super.toString());
        appendable.append((("<attributeValue>" + this.attributeValue) + "</attributeValue>\n"));
        appendable.append("</AttributeValueMsg>\n");
        return appendable.toString();
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getAttributeValue.
     *
     * @return the AttributeValue.
     */
    public AttributeValue getAttributeValue() {
        return this.attributeValue;
    }

    /**
     * Missing description at method setAttributeValue.
     *
     * @param attributeValue the AttributeValue.
     */
    public void setAttributeValue(AttributeValue attributeValue) {
        this.attributeValue = attributeValue;
    }
}
