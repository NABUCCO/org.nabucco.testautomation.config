/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;

/**
 * ProduceTestScriptContainerMsg<p/>Message for producing a TestScriptContainer<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-10-12
 */
public class ProduceTestScriptContainerMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "testScript", "testScriptContainer" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    private TestScript testScript;

    private TestScriptContainer testScriptContainer;

    /** Constructs a new ProduceTestScriptContainerMsg instance. */
    public ProduceTestScriptContainerMsg() {
        super();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<TestScript>(PROPERTY_NAMES[0], TestScript.class,
                PROPERTY_CONSTRAINTS[0], this.testScript));
        properties.add(new DatatypeProperty<TestScriptContainer>(PROPERTY_NAMES[1],
                TestScriptContainer.class, PROPERTY_CONSTRAINTS[1], this.testScriptContainer));
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
        final ProduceTestScriptContainerMsg other = ((ProduceTestScriptContainerMsg) obj);
        if ((this.testScript == null)) {
            if ((other.testScript != null))
                return false;
        } else if ((!this.testScript.equals(other.testScript)))
            return false;
        if ((this.testScriptContainer == null)) {
            if ((other.testScriptContainer != null))
                return false;
        } else if ((!this.testScriptContainer.equals(other.testScriptContainer)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.testScript == null) ? 0 : this.testScript.hashCode()));
        result = ((PRIME * result) + ((this.testScriptContainer == null) ? 0
                : this.testScriptContainer.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<ProduceTestScriptContainerMsg>\n");
        appendable.append(super.toString());
        appendable.append((("<testScript>" + this.testScript) + "</testScript>\n"));
        appendable
                .append((("<testScriptContainer>" + this.testScriptContainer) + "</testScriptContainer>\n"));
        appendable.append("</ProduceTestScriptContainerMsg>\n");
        return appendable.toString();
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
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
     * Missing description at method setTestScript.
     *
     * @param testScript the TestScript.
     */
    public void setTestScript(TestScript testScript) {
        this.testScript = testScript;
    }

    /**
     * Missing description at method getTestScriptContainer.
     *
     * @return the TestScriptContainer.
     */
    public TestScriptContainer getTestScriptContainer() {
        return this.testScriptContainer;
    }

    /**
     * Missing description at method setTestScriptContainer.
     *
     * @param testScriptContainer the TestScriptContainer.
     */
    public void setTestScriptContainer(TestScriptContainer testScriptContainer) {
        this.testScriptContainer = testScriptContainer;
    }
}
