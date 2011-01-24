/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message.engine;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

/**
 * TestExecutionMsg<p/>Message to start the execution of a TestConfiguration<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-06-25
 */
public class TestExecutionMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "user", "testConfiguration" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    private User user;

    private TestConfiguration testConfiguration;

    /** Constructs a new TestExecutionMsg instance. */
    public TestExecutionMsg() {
        super();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<User>(PROPERTY_NAMES[0], User.class,
                PROPERTY_CONSTRAINTS[0], this.user));
        properties.add(new DatatypeProperty<TestConfiguration>(PROPERTY_NAMES[1],
                TestConfiguration.class, PROPERTY_CONSTRAINTS[1], this.testConfiguration));
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
        final TestExecutionMsg other = ((TestExecutionMsg) obj);
        if ((this.user == null)) {
            if ((other.user != null))
                return false;
        } else if ((!this.user.equals(other.user)))
            return false;
        if ((this.testConfiguration == null)) {
            if ((other.testConfiguration != null))
                return false;
        } else if ((!this.testConfiguration.equals(other.testConfiguration)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.user == null) ? 0 : this.user.hashCode()));
        result = ((PRIME * result) + ((this.testConfiguration == null) ? 0 : this.testConfiguration
                .hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<TestExecutionMsg>\n");
        appendable.append(super.toString());
        appendable.append((("<user>" + this.user) + "</user>\n"));
        appendable
                .append((("<testConfiguration>" + this.testConfiguration) + "</testConfiguration>\n"));
        appendable.append("</TestExecutionMsg>\n");
        return appendable.toString();
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getUser.
     *
     * @return the User.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Missing description at method setUser.
     *
     * @param user the User.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Missing description at method getTestConfiguration.
     *
     * @return the TestConfiguration.
     */
    public TestConfiguration getTestConfiguration() {
        return this.testConfiguration;
    }

    /**
     * Missing description at method setTestConfiguration.
     *
     * @param testConfiguration the TestConfiguration.
     */
    public void setTestConfiguration(TestConfiguration testConfiguration) {
        this.testConfiguration = testConfiguration;
    }
}
