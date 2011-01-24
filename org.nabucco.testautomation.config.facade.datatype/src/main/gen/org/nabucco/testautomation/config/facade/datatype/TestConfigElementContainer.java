/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.datatype;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Order;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;

/**
 * TestConfigElementContainer<p/>Container holding a TestConfigElement and its order position<p/>
 *
 * @version 1.0
 * @author sschmidt, PRODYNA AG, 2010-09-30
 */
public class TestConfigElementContainer extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "element", "orderIndex" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l0,n;m1,1;" };

    private TestConfigElement element;

    private Order orderIndex;

    /** Constructs a new TestConfigElementContainer instance. */
    public TestConfigElementContainer() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TestConfigElementContainer.
     */
    protected void cloneObject(TestConfigElementContainer clone) {
        super.cloneObject(clone);
        if ((this.getElement() != null)) {
            clone.setElement(this.getElement().cloneObject());
        }
        if ((this.getOrderIndex() != null)) {
            clone.setOrderIndex(this.getOrderIndex().cloneObject());
        }
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<TestConfigElement>(PROPERTY_NAMES[0],
                TestConfigElement.class, PROPERTY_CONSTRAINTS[0], this.element));
        properties.add(new BasetypeProperty<Order>(PROPERTY_NAMES[1], Order.class,
                PROPERTY_CONSTRAINTS[1], this.orderIndex));
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
        final TestConfigElementContainer other = ((TestConfigElementContainer) obj);
        if ((this.element == null)) {
            if ((other.element != null))
                return false;
        } else if ((!this.element.equals(other.element)))
            return false;
        if ((this.orderIndex == null)) {
            if ((other.orderIndex != null))
                return false;
        } else if ((!this.orderIndex.equals(other.orderIndex)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.element == null) ? 0 : this.element.hashCode()));
        result = ((PRIME * result) + ((this.orderIndex == null) ? 0 : this.orderIndex.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<TestConfigElementContainer>\n");
        appendable.append(super.toString());
        appendable.append((("<element>" + this.element) + "</element>\n"));
        appendable.append((("<orderIndex>" + this.orderIndex) + "</orderIndex>\n"));
        appendable.append("</TestConfigElementContainer>\n");
        return appendable.toString();
    }

    @Override
    public TestConfigElementContainer cloneObject() {
        TestConfigElementContainer clone = new TestConfigElementContainer();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method setElement.
     *
     * @param element the TestConfigElement.
     */
    public void setElement(TestConfigElement element) {
        this.element = element;
    }

    /**
     * Missing description at method getElement.
     *
     * @return the TestConfigElement.
     */
    public TestConfigElement getElement() {
        return this.element;
    }

    /**
     * Missing description at method getOrderIndex.
     *
     * @return the Order.
     */
    public Order getOrderIndex() {
        return this.orderIndex;
    }

    /**
     * Missing description at method setOrderIndex.
     *
     * @param orderIndex the Order.
     */
    public void setOrderIndex(Order orderIndex) {
        this.orderIndex = orderIndex;
    }

    /**
     * Missing description at method setOrderIndex.
     *
     * @param orderIndex the Integer.
     */
    public void setOrderIndex(Integer orderIndex) {
        if ((this.orderIndex == null)) {
            this.orderIndex = new Order();
        }
        this.orderIndex.setValue(orderIndex);
    }
}
