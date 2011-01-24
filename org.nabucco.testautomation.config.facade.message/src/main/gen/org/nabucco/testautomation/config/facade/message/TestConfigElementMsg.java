/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;

/**
 * TestConfigElementMsg<p/>Message for transporting a TestConfigElement<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class TestConfigElementMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "testConfigElement",
            "testConfigElementContainer" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    private TestConfigElement testConfigElement;

    private TestConfigElementContainer testConfigElementContainer;

    /** Constructs a new TestConfigElementMsg instance. */
    public TestConfigElementMsg() {
        super();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<TestConfigElement>(PROPERTY_NAMES[0],
                TestConfigElement.class, PROPERTY_CONSTRAINTS[0], this.testConfigElement));
        properties.add(new DatatypeProperty<TestConfigElementContainer>(PROPERTY_NAMES[1],
                TestConfigElementContainer.class, PROPERTY_CONSTRAINTS[1],
                this.testConfigElementContainer));
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
        final TestConfigElementMsg other = ((TestConfigElementMsg) obj);
        if ((this.testConfigElement == null)) {
            if ((other.testConfigElement != null))
                return false;
        } else if ((!this.testConfigElement.equals(other.testConfigElement)))
            return false;
        if ((this.testConfigElementContainer == null)) {
            if ((other.testConfigElementContainer != null))
                return false;
        } else if ((!this.testConfigElementContainer.equals(other.testConfigElementContainer)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.testConfigElement == null) ? 0 : this.testConfigElement
                .hashCode()));
        result = ((PRIME * result) + ((this.testConfigElementContainer == null) ? 0
                : this.testConfigElementContainer.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<TestConfigElementMsg>\n");
        appendable.append(super.toString());
        appendable
                .append((("<testConfigElement>" + this.testConfigElement) + "</testConfigElement>\n"));
        appendable
                .append((("<testConfigElementContainer>" + this.testConfigElementContainer) + "</testConfigElementContainer>\n"));
        appendable.append("</TestConfigElementMsg>\n");
        return appendable.toString();
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getTestConfigElement.
     *
     * @return the TestConfigElement.
     */
    public TestConfigElement getTestConfigElement() {
        return this.testConfigElement;
    }

    /**
     * Missing description at method setTestConfigElement.
     *
     * @param testConfigElement the TestConfigElement.
     */
    public void setTestConfigElement(TestConfigElement testConfigElement) {
        this.testConfigElement = testConfigElement;
    }

    /**
     * Missing description at method getTestConfigElementContainer.
     *
     * @return the TestConfigElementContainer.
     */
    public TestConfigElementContainer getTestConfigElementContainer() {
        return this.testConfigElementContainer;
    }

    /**
     * Missing description at method setTestConfigElementContainer.
     *
     * @param testConfigElementContainer the TestConfigElementContainer.
     */
    public void setTestConfigElementContainer(TestConfigElementContainer testConfigElementContainer) {
        this.testConfigElementContainer = testConfigElementContainer;
    }
}
