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
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * ProduceTestConfigElementMsg<p/>Message for producing a TestConfigElement<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-10-12
 */
public class ProduceTestConfigElementMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String SCHEMAELEMENT = "schemaElement";

    private SchemaElement schemaElement;

    /** Constructs a new ProduceTestConfigElementMsg instance. */
    public ProduceTestConfigElementMsg() {
        super();
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(SCHEMAELEMENT, PropertyDescriptorSupport.createDatatype(SCHEMAELEMENT,
                SchemaElement.class, 0, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPONENT));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public List<NabuccoProperty> getProperties() {
        List<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(
                ProduceTestConfigElementMsg.getPropertyDescriptor(SCHEMAELEMENT),
                this.schemaElement));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SCHEMAELEMENT) && (property.getType() == SchemaElement.class))) {
            this.setSchemaElement(((SchemaElement) property.getInstance()));
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
        final ProduceTestConfigElementMsg other = ((ProduceTestConfigElementMsg) obj);
        if ((this.schemaElement == null)) {
            if ((other.schemaElement != null))
                return false;
        } else if ((!this.schemaElement.equals(other.schemaElement)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.schemaElement == null) ? 0 : this.schemaElement
                .hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getSchemaElement.
     *
     * @return the SchemaElement.
     */
    public SchemaElement getSchemaElement() {
        return this.schemaElement;
    }

    /**
     * Missing description at method setSchemaElement.
     *
     * @param schemaElement the SchemaElement.
     */
    public void setSchemaElement(SchemaElement schemaElement) {
        this.schemaElement = schemaElement;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ProduceTestConfigElementMsg.class)
                .getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ProduceTestConfigElementMsg.class)
                .getAllProperties();
    }
}
