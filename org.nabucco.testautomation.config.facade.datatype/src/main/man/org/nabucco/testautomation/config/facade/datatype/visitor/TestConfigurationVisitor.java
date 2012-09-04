/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.testautomation.config.facade.datatype.visitor;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyContainer;

/**
 * TestConfigurationVisitor
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestConfigurationVisitor extends DatatypeVisitor {

    @Override
    public void visit(Datatype datatype) throws VisitorException {

        if (datatype instanceof TestConfiguration) {
            this.visit((TestConfiguration) datatype);
        } else if (datatype instanceof TestConfigElement) {
            this.visit((TestConfigElement) datatype);
        } else if (datatype instanceof TestConfigElementContainer) {
            this.visit((TestConfigElementContainer) datatype);
        } else if (datatype instanceof Property) {
            this.visit((Property) datatype);
        } else if (datatype instanceof PropertyContainer) {
        	this.visit((PropertyContainer) datatype);
        } else if (datatype instanceof TestScriptContainer) {
        	this.visit((TestScriptContainer) datatype);
        } else if (datatype instanceof AttributeValue) {
        	this.visit((AttributeValue) datatype);
        } else if (datatype instanceof Dependency) {
        	this.visit((Dependency) datatype);
        }
        super.visit(datatype);
    }

    /**
     * Visit {@link TestConfiguration} instances.
     * 
     * @param element
     *            the element to visit
     */
    protected void visit(TestConfiguration element) throws VisitorException {
    }

    /**
     * Visit {@link TestConfiguration} instances.
     * 
     * @param element
     *            the element to visit
     */
    protected void visit(TestConfigElementContainer element) throws VisitorException {
    }

    /**
     * Visit {@link TestConfigElement} instances.
     * 
     * @param element
     *            the element to visit
     */
    protected void visit(TestConfigElement element) throws VisitorException {
    }
    
    /**
     * Visit {@link Property} instances.
     * 
     * @param element
     *            the element to visit
     */
    protected void visit(Property element) throws VisitorException {
    }
    
    /**
     * Visit {@link PropertyContainer} instances.
     * 
     * @param element
     *            the element to visit
     */
    protected void visit(PropertyContainer element) throws VisitorException {
    }
    
    /**
     * Visit {@link TestScriptContainer} instances.
     * 
     * @param element
     *            the element to visit
     */
    protected void visit(TestScriptContainer element) throws VisitorException {
    }
    
    /**
     * Visit {@link AttributeValue} instances.
     * 
     * @param element
     *            the element to visit
     */
    protected void visit(AttributeValue element) throws VisitorException {
    }
    
    /**
     * Visit {@link Dependency} instances.
     * 
     * @param element
     *            the element to visit
     */
    protected void visit(Dependency element) throws VisitorException {
    }
    
}
