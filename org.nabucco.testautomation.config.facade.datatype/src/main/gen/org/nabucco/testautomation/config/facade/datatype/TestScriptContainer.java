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
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;

/**
 * TestScriptContainer<p/>Container holding a TestScript and its order position<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-30-09
 */
public class TestScriptContainer extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "testScript", "orderIndex" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l0,n;m1,1;" };

    private TestScript testScript;

    private Long testScriptRefId;

    private Order orderIndex;

    /** Constructs a new TestScriptContainer instance. */
    public TestScriptContainer() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TestScriptContainer.
     */
    protected void cloneObject(TestScriptContainer clone) {
        super.cloneObject(clone);
        if ((this.getTestScript() != null)) {
            clone.setTestScript(this.getTestScript().cloneObject());
        }
        if ((this.getTestScriptRefId() != null)) {
            clone.setTestScriptRefId(this.getTestScriptRefId());
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
        properties.add(new DatatypeProperty<TestScript>(PROPERTY_NAMES[0], TestScript.class,
                PROPERTY_CONSTRAINTS[0], this.testScript));
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
        final TestScriptContainer other = ((TestScriptContainer) obj);
        if ((this.testScript == null)) {
            if ((other.testScript != null))
                return false;
        } else if ((!this.testScript.equals(other.testScript)))
            return false;
        if ((this.testScriptRefId == null)) {
            if ((other.testScriptRefId != null))
                return false;
        } else if ((!this.testScriptRefId.equals(other.testScriptRefId)))
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
        result = ((PRIME * result) + ((this.testScript == null) ? 0 : this.testScript.hashCode()));
        result = ((PRIME * result) + ((this.testScriptRefId == null) ? 0 : this.testScriptRefId
                .hashCode()));
        result = ((PRIME * result) + ((this.orderIndex == null) ? 0 : this.orderIndex.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<TestScriptContainer>\n");
        appendable.append(super.toString());
        appendable.append((("<testScript>" + this.testScript) + "</testScript>\n"));
        appendable.append((("<testScriptRefId>" + this.testScriptRefId) + "</testScriptRefId>\n"));
        appendable.append((("<orderIndex>" + this.orderIndex) + "</orderIndex>\n"));
        appendable.append("</TestScriptContainer>\n");
        return appendable.toString();
    }

    @Override
    public TestScriptContainer cloneObject() {
        TestScriptContainer clone = new TestScriptContainer();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method setTestScript.
     *
     * @param testScript the TestScript.
     */
    public void setTestScript(TestScript testScript) {
        this.testScript = testScript;
        if ((testScript != null)) {
            this.setTestScriptRefId(testScript.getId());
        } else {
            this.setTestScriptRefId(null);
        }
    }

    /**
     * Missing description at method getTestScript.
     *
     * @return the TestScript.
     */
    public TestScript getTestScript() {
        return this.testScript;
    }

    /**
     * Getter for the TestScriptRefId.
     *
     * @return the Long.
     */
    public Long getTestScriptRefId() {
        return this.testScriptRefId;
    }

    /**
     * Setter for the TestScriptRefId.
     *
     * @param testScriptRefId the Long.
     */
    public void setTestScriptRefId(Long testScriptRefId) {
        this.testScriptRefId = testScriptRefId;
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