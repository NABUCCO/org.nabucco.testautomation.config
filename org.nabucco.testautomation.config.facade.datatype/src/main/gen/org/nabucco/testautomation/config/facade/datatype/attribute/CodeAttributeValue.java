/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.datatype.attribute;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;

/**
 * CodeAttributeValue<p/>The code value of an attribute<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-09
 */
public class CodeAttributeValue extends AttributeValue implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "value" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

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

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<Code>(PROPERTY_NAMES[0], Code.class,
                PROPERTY_CONSTRAINTS[0], this.value));
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
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<CodeAttributeValue>\n");
        appendable.append(super.toString());
        appendable.append((("<value>" + this.value) + "</value>\n"));
        appendable.append((("<valueRefId>" + this.valueRefId) + "</valueRefId>\n"));
        appendable.append("</CodeAttributeValue>\n");
        return appendable.toString();
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
}
