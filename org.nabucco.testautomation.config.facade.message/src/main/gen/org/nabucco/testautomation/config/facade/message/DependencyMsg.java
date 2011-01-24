/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.config.facade.datatype.Dependency;

/**
 * DependencyMsg<p/>Message for transporting a Dependency<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class DependencyMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "dependency" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    private Dependency dependency;

    /** Constructs a new DependencyMsg instance. */
    public DependencyMsg() {
        super();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<Dependency>(PROPERTY_NAMES[0], Dependency.class,
                PROPERTY_CONSTRAINTS[0], this.dependency));
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
        final DependencyMsg other = ((DependencyMsg) obj);
        if ((this.dependency == null)) {
            if ((other.dependency != null))
                return false;
        } else if ((!this.dependency.equals(other.dependency)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.dependency == null) ? 0 : this.dependency.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<DependencyMsg>\n");
        appendable.append(super.toString());
        appendable.append((("<dependency>" + this.dependency) + "</dependency>\n"));
        appendable.append("</DependencyMsg>\n");
        return appendable.toString();
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getDependency.
     *
     * @return the Dependency.
     */
    public Dependency getDependency() {
        return this.dependency;
    }

    /**
     * Missing description at method setDependency.
     *
     * @param dependency the Dependency.
     */
    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }
}
