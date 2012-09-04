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
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * DashboardStatisticMsg<p/>Message for transportation statistics<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2011-12-22
 */
public class DashboardStatisticMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;",
            "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;" };

    public static final String NUMTESTCONFIGURATIONS = "numTestConfigurations";

    public static final String NUMLEVELONE = "numLevelOne";

    public static final String NUMLEVELTWO = "numLevelTwo";

    public static final String NUMLEVELTHREE = "numLevelThree";

    public static final String NUMLEVELFOUR = "numLevelFour";

    public static final String NUMLEVELFIVE = "numLevelFive";

    public static final String NAMELEVELONE = "nameLevelOne";

    public static final String NAMELEVELTWO = "nameLevelTwo";

    public static final String NAMELEVELTHREE = "nameLevelThree";

    public static final String NAMELEVELFOUR = "nameLevelFour";

    public static final String NAMELEVELFIVE = "nameLevelFive";

    private Number numTestConfigurations;

    private Number numLevelOne;

    private Number numLevelTwo;

    private Number numLevelThree;

    private Number numLevelFour;

    private Number numLevelFive;

    private Name nameLevelOne;

    private Name nameLevelTwo;

    private Name nameLevelThree;

    private Name nameLevelFour;

    private Name nameLevelFive;

    /** Constructs a new DashboardStatisticMsg instance. */
    public DashboardStatisticMsg() {
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
        propertyMap.put(NUMTESTCONFIGURATIONS, PropertyDescriptorSupport.createBasetype(NUMTESTCONFIGURATIONS,
                Number.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(NUMLEVELONE,
                PropertyDescriptorSupport.createBasetype(NUMLEVELONE, Number.class, 1, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(NUMLEVELTWO,
                PropertyDescriptorSupport.createBasetype(NUMLEVELTWO, Number.class, 2, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(NUMLEVELTHREE, PropertyDescriptorSupport.createBasetype(NUMLEVELTHREE, Number.class, 3,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap
                .put(NUMLEVELFOUR, PropertyDescriptorSupport.createBasetype(NUMLEVELFOUR, Number.class, 4,
                        PROPERTY_CONSTRAINTS[4], false));
        propertyMap
                .put(NUMLEVELFIVE, PropertyDescriptorSupport.createBasetype(NUMLEVELFIVE, Number.class, 5,
                        PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(NAMELEVELONE,
                PropertyDescriptorSupport.createBasetype(NAMELEVELONE, Name.class, 6, PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(NAMELEVELTWO,
                PropertyDescriptorSupport.createBasetype(NAMELEVELTWO, Name.class, 7, PROPERTY_CONSTRAINTS[7], false));
        propertyMap
                .put(NAMELEVELTHREE, PropertyDescriptorSupport.createBasetype(NAMELEVELTHREE, Name.class, 8,
                        PROPERTY_CONSTRAINTS[8], false));
        propertyMap.put(NAMELEVELFOUR,
                PropertyDescriptorSupport.createBasetype(NAMELEVELFOUR, Name.class, 9, PROPERTY_CONSTRAINTS[9], false));
        propertyMap.put(NAMELEVELFIVE, PropertyDescriptorSupport.createBasetype(NAMELEVELFIVE, Name.class, 10,
                PROPERTY_CONSTRAINTS[10], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DashboardStatisticMsg.getPropertyDescriptor(NUMTESTCONFIGURATIONS),
                this.numTestConfigurations));
        properties
                .add(super.createProperty(DashboardStatisticMsg.getPropertyDescriptor(NUMLEVELONE), this.numLevelOne));
        properties
                .add(super.createProperty(DashboardStatisticMsg.getPropertyDescriptor(NUMLEVELTWO), this.numLevelTwo));
        properties.add(super.createProperty(DashboardStatisticMsg.getPropertyDescriptor(NUMLEVELTHREE),
                this.numLevelThree));
        properties.add(super.createProperty(DashboardStatisticMsg.getPropertyDescriptor(NUMLEVELFOUR),
                this.numLevelFour));
        properties.add(super.createProperty(DashboardStatisticMsg.getPropertyDescriptor(NUMLEVELFIVE),
                this.numLevelFive));
        properties.add(super.createProperty(DashboardStatisticMsg.getPropertyDescriptor(NAMELEVELONE),
                this.nameLevelOne));
        properties.add(super.createProperty(DashboardStatisticMsg.getPropertyDescriptor(NAMELEVELTWO),
                this.nameLevelTwo));
        properties.add(super.createProperty(DashboardStatisticMsg.getPropertyDescriptor(NAMELEVELTHREE),
                this.nameLevelThree));
        properties.add(super.createProperty(DashboardStatisticMsg.getPropertyDescriptor(NAMELEVELFOUR),
                this.nameLevelFour));
        properties.add(super.createProperty(DashboardStatisticMsg.getPropertyDescriptor(NAMELEVELFIVE),
                this.nameLevelFive));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NUMTESTCONFIGURATIONS) && (property.getType() == Number.class))) {
            this.setNumTestConfigurations(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NUMLEVELONE) && (property.getType() == Number.class))) {
            this.setNumLevelOne(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NUMLEVELTWO) && (property.getType() == Number.class))) {
            this.setNumLevelTwo(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NUMLEVELTHREE) && (property.getType() == Number.class))) {
            this.setNumLevelThree(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NUMLEVELFOUR) && (property.getType() == Number.class))) {
            this.setNumLevelFour(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NUMLEVELFIVE) && (property.getType() == Number.class))) {
            this.setNumLevelFive(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAMELEVELONE) && (property.getType() == Name.class))) {
            this.setNameLevelOne(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAMELEVELTWO) && (property.getType() == Name.class))) {
            this.setNameLevelTwo(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAMELEVELTHREE) && (property.getType() == Name.class))) {
            this.setNameLevelThree(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAMELEVELFOUR) && (property.getType() == Name.class))) {
            this.setNameLevelFour(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAMELEVELFIVE) && (property.getType() == Name.class))) {
            this.setNameLevelFive(((Name) property.getInstance()));
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
        final DashboardStatisticMsg other = ((DashboardStatisticMsg) obj);
        if ((this.numTestConfigurations == null)) {
            if ((other.numTestConfigurations != null))
                return false;
        } else if ((!this.numTestConfigurations.equals(other.numTestConfigurations)))
            return false;
        if ((this.numLevelOne == null)) {
            if ((other.numLevelOne != null))
                return false;
        } else if ((!this.numLevelOne.equals(other.numLevelOne)))
            return false;
        if ((this.numLevelTwo == null)) {
            if ((other.numLevelTwo != null))
                return false;
        } else if ((!this.numLevelTwo.equals(other.numLevelTwo)))
            return false;
        if ((this.numLevelThree == null)) {
            if ((other.numLevelThree != null))
                return false;
        } else if ((!this.numLevelThree.equals(other.numLevelThree)))
            return false;
        if ((this.numLevelFour == null)) {
            if ((other.numLevelFour != null))
                return false;
        } else if ((!this.numLevelFour.equals(other.numLevelFour)))
            return false;
        if ((this.numLevelFive == null)) {
            if ((other.numLevelFive != null))
                return false;
        } else if ((!this.numLevelFive.equals(other.numLevelFive)))
            return false;
        if ((this.nameLevelOne == null)) {
            if ((other.nameLevelOne != null))
                return false;
        } else if ((!this.nameLevelOne.equals(other.nameLevelOne)))
            return false;
        if ((this.nameLevelTwo == null)) {
            if ((other.nameLevelTwo != null))
                return false;
        } else if ((!this.nameLevelTwo.equals(other.nameLevelTwo)))
            return false;
        if ((this.nameLevelThree == null)) {
            if ((other.nameLevelThree != null))
                return false;
        } else if ((!this.nameLevelThree.equals(other.nameLevelThree)))
            return false;
        if ((this.nameLevelFour == null)) {
            if ((other.nameLevelFour != null))
                return false;
        } else if ((!this.nameLevelFour.equals(other.nameLevelFour)))
            return false;
        if ((this.nameLevelFive == null)) {
            if ((other.nameLevelFive != null))
                return false;
        } else if ((!this.nameLevelFive.equals(other.nameLevelFive)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.numTestConfigurations == null) ? 0 : this.numTestConfigurations.hashCode()));
        result = ((PRIME * result) + ((this.numLevelOne == null) ? 0 : this.numLevelOne.hashCode()));
        result = ((PRIME * result) + ((this.numLevelTwo == null) ? 0 : this.numLevelTwo.hashCode()));
        result = ((PRIME * result) + ((this.numLevelThree == null) ? 0 : this.numLevelThree.hashCode()));
        result = ((PRIME * result) + ((this.numLevelFour == null) ? 0 : this.numLevelFour.hashCode()));
        result = ((PRIME * result) + ((this.numLevelFive == null) ? 0 : this.numLevelFive.hashCode()));
        result = ((PRIME * result) + ((this.nameLevelOne == null) ? 0 : this.nameLevelOne.hashCode()));
        result = ((PRIME * result) + ((this.nameLevelTwo == null) ? 0 : this.nameLevelTwo.hashCode()));
        result = ((PRIME * result) + ((this.nameLevelThree == null) ? 0 : this.nameLevelThree.hashCode()));
        result = ((PRIME * result) + ((this.nameLevelFour == null) ? 0 : this.nameLevelFour.hashCode()));
        result = ((PRIME * result) + ((this.nameLevelFive == null) ? 0 : this.nameLevelFive.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getNumTestConfigurations.
     *
     * @return the Number.
     */
    public Number getNumTestConfigurations() {
        return this.numTestConfigurations;
    }

    /**
     * Missing description at method setNumTestConfigurations.
     *
     * @param numTestConfigurations the Number.
     */
    public void setNumTestConfigurations(Number numTestConfigurations) {
        this.numTestConfigurations = numTestConfigurations;
    }

    /**
     * Missing description at method getNumLevelOne.
     *
     * @return the Number.
     */
    public Number getNumLevelOne() {
        return this.numLevelOne;
    }

    /**
     * Missing description at method setNumLevelOne.
     *
     * @param numLevelOne the Number.
     */
    public void setNumLevelOne(Number numLevelOne) {
        this.numLevelOne = numLevelOne;
    }

    /**
     * Missing description at method getNumLevelTwo.
     *
     * @return the Number.
     */
    public Number getNumLevelTwo() {
        return this.numLevelTwo;
    }

    /**
     * Missing description at method setNumLevelTwo.
     *
     * @param numLevelTwo the Number.
     */
    public void setNumLevelTwo(Number numLevelTwo) {
        this.numLevelTwo = numLevelTwo;
    }

    /**
     * Missing description at method getNumLevelThree.
     *
     * @return the Number.
     */
    public Number getNumLevelThree() {
        return this.numLevelThree;
    }

    /**
     * Missing description at method setNumLevelThree.
     *
     * @param numLevelThree the Number.
     */
    public void setNumLevelThree(Number numLevelThree) {
        this.numLevelThree = numLevelThree;
    }

    /**
     * Missing description at method getNumLevelFour.
     *
     * @return the Number.
     */
    public Number getNumLevelFour() {
        return this.numLevelFour;
    }

    /**
     * Missing description at method setNumLevelFour.
     *
     * @param numLevelFour the Number.
     */
    public void setNumLevelFour(Number numLevelFour) {
        this.numLevelFour = numLevelFour;
    }

    /**
     * Missing description at method getNumLevelFive.
     *
     * @return the Number.
     */
    public Number getNumLevelFive() {
        return this.numLevelFive;
    }

    /**
     * Missing description at method setNumLevelFive.
     *
     * @param numLevelFive the Number.
     */
    public void setNumLevelFive(Number numLevelFive) {
        this.numLevelFive = numLevelFive;
    }

    /**
     * Missing description at method getNameLevelOne.
     *
     * @return the Name.
     */
    public Name getNameLevelOne() {
        return this.nameLevelOne;
    }

    /**
     * Missing description at method setNameLevelOne.
     *
     * @param nameLevelOne the Name.
     */
    public void setNameLevelOne(Name nameLevelOne) {
        this.nameLevelOne = nameLevelOne;
    }

    /**
     * Missing description at method getNameLevelTwo.
     *
     * @return the Name.
     */
    public Name getNameLevelTwo() {
        return this.nameLevelTwo;
    }

    /**
     * Missing description at method setNameLevelTwo.
     *
     * @param nameLevelTwo the Name.
     */
    public void setNameLevelTwo(Name nameLevelTwo) {
        this.nameLevelTwo = nameLevelTwo;
    }

    /**
     * Missing description at method getNameLevelThree.
     *
     * @return the Name.
     */
    public Name getNameLevelThree() {
        return this.nameLevelThree;
    }

    /**
     * Missing description at method setNameLevelThree.
     *
     * @param nameLevelThree the Name.
     */
    public void setNameLevelThree(Name nameLevelThree) {
        this.nameLevelThree = nameLevelThree;
    }

    /**
     * Missing description at method getNameLevelFour.
     *
     * @return the Name.
     */
    public Name getNameLevelFour() {
        return this.nameLevelFour;
    }

    /**
     * Missing description at method setNameLevelFour.
     *
     * @param nameLevelFour the Name.
     */
    public void setNameLevelFour(Name nameLevelFour) {
        this.nameLevelFour = nameLevelFour;
    }

    /**
     * Missing description at method getNameLevelFive.
     *
     * @return the Name.
     */
    public Name getNameLevelFive() {
        return this.nameLevelFive;
    }

    /**
     * Missing description at method setNameLevelFive.
     *
     * @param nameLevelFive the Name.
     */
    public void setNameLevelFive(Name nameLevelFive) {
        this.nameLevelFive = nameLevelFive;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DashboardStatisticMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DashboardStatisticMsg.class).getAllProperties();
    }
}
