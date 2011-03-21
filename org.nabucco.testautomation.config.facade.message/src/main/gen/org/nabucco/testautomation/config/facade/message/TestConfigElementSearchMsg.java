/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.facade.datatype.base.HierarchyLevelType;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * TestConfigElementSearchMsg<p/>Message for searching TestConfigElements<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class TestConfigElementSearchMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;m0,1;", "l0,n;m0,1;",
            "l0,255;m0,1;", "l0,255;m0,1;", "m0,1;", "m0,1;", "l0,n;m1,1;" };

    public static final String OWNER = "owner";

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String LEVEL = "level";

    public static final String SCHEMAELEMENT = "schemaElement";

    public static final String LOADTESTSCRIPTS = "loadTestScripts";

    private Owner owner;

    private Identifier id;

    private Name name;

    private Description description;

    private HierarchyLevelType level;

    private SchemaElement schemaElement;

    private Flag loadTestScripts;

    /** Constructs a new TestConfigElementSearchMsg instance. */
    public TestConfigElementSearchMsg() {
        super();
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(OWNER, PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 0,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(ID, PropertyDescriptorSupport.createBasetype(ID, Identifier.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(NAME, PropertyDescriptorSupport.createBasetype(NAME, Name.class, 2,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION,
                Description.class, 3, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(LEVEL, PropertyDescriptorSupport.createEnumeration(LEVEL,
                HierarchyLevelType.class, 4, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(SCHEMAELEMENT, PropertyDescriptorSupport.createDatatype(SCHEMAELEMENT,
                SchemaElement.class, 5, PROPERTY_CONSTRAINTS[5], false,
                PropertyAssociationType.COMPONENT));
        propertyMap.put(LOADTESTSCRIPTS, PropertyDescriptorSupport.createBasetype(LOADTESTSCRIPTS,
                Flag.class, 6, PROPERTY_CONSTRAINTS[6], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public List<NabuccoProperty> getProperties() {
        List<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(
                TestConfigElementSearchMsg.getPropertyDescriptor(OWNER), this.owner));
        properties.add(super.createProperty(TestConfigElementSearchMsg.getPropertyDescriptor(ID),
                this.id));
        properties.add(super.createProperty(TestConfigElementSearchMsg.getPropertyDescriptor(NAME),
                this.name));
        properties.add(super.createProperty(
                TestConfigElementSearchMsg.getPropertyDescriptor(DESCRIPTION), this.description));
        properties.add(super.createProperty(
                TestConfigElementSearchMsg.getPropertyDescriptor(LEVEL), this.level));
        properties
                .add(super.createProperty(
                        TestConfigElementSearchMsg.getPropertyDescriptor(SCHEMAELEMENT),
                        this.schemaElement));
        properties.add(super.createProperty(
                TestConfigElementSearchMsg.getPropertyDescriptor(LOADTESTSCRIPTS),
                this.loadTestScripts));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ID) && (property.getType() == Identifier.class))) {
            this.setId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LEVEL) && (property.getType() == HierarchyLevelType.class))) {
            this.setLevel(((HierarchyLevelType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SCHEMAELEMENT) && (property.getType() == SchemaElement.class))) {
            this.setSchemaElement(((SchemaElement) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOADTESTSCRIPTS) && (property.getType() == Flag.class))) {
            this.setLoadTestScripts(((Flag) property.getInstance()));
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
        final TestConfigElementSearchMsg other = ((TestConfigElementSearchMsg) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.id == null)) {
            if ((other.id != null))
                return false;
        } else if ((!this.id.equals(other.id)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.level == null)) {
            if ((other.level != null))
                return false;
        } else if ((!this.level.equals(other.level)))
            return false;
        if ((this.schemaElement == null)) {
            if ((other.schemaElement != null))
                return false;
        } else if ((!this.schemaElement.equals(other.schemaElement)))
            return false;
        if ((this.loadTestScripts == null)) {
            if ((other.loadTestScripts != null))
                return false;
        } else if ((!this.loadTestScripts.equals(other.loadTestScripts)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.level == null) ? 0 : this.level.hashCode()));
        result = ((PRIME * result) + ((this.schemaElement == null) ? 0 : this.schemaElement
                .hashCode()));
        result = ((PRIME * result) + ((this.loadTestScripts == null) ? 0 : this.loadTestScripts
                .hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getOwner.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Missing description at method setOwner.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Missing description at method getId.
     *
     * @return the Identifier.
     */
    public Identifier getId() {
        return this.id;
    }

    /**
     * Missing description at method setId.
     *
     * @param id the Identifier.
     */
    public void setId(Identifier id) {
        this.id = id;
    }

    /**
     * Missing description at method getName.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Missing description at method setName.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Missing description at method getDescription.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Missing description at method setDescription.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Missing description at method getLevel.
     *
     * @return the HierarchyLevelType.
     */
    public HierarchyLevelType getLevel() {
        return this.level;
    }

    /**
     * Missing description at method setLevel.
     *
     * @param level the HierarchyLevelType.
     */
    public void setLevel(HierarchyLevelType level) {
        this.level = level;
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
     * Missing description at method getLoadTestScripts.
     *
     * @return the Flag.
     */
    public Flag getLoadTestScripts() {
        return this.loadTestScripts;
    }

    /**
     * Missing description at method setLoadTestScripts.
     *
     * @param loadTestScripts the Flag.
     */
    public void setLoadTestScripts(Flag loadTestScripts) {
        this.loadTestScripts = loadTestScripts;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TestConfigElementSearchMsg.class)
                .getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TestConfigElementSearchMsg.class)
                .getAllProperties();
    }
}
