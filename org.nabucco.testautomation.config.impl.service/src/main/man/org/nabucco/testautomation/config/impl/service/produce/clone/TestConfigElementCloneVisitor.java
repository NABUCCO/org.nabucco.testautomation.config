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
package org.nabucco.testautomation.config.impl.service.produce.clone;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Version;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.config.facade.datatype.visitor.TestConfigurationVisitor;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyContainer;
import org.nabucco.testautomation.property.facade.datatype.base.TestAutomationDatatype;

/**
 * TestConfigElementCloneVisitor
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestConfigElementCloneVisitor extends TestConfigurationVisitor {

    private Map<Long, TestConfigElement> elementMap = new HashMap<Long, TestConfigElement>();

    @Override
    protected void visit(TestConfigElement element) throws VisitorException {

        if (element.getReused() != null && element.getReused().getValue()) {
            return;
        }
        elementMap.put(element.getId(), element);
        resetDatatype(element);
        element.setSchemaElementRefId(element.getSchemaElement().getId());
        element.setReused(Boolean.FALSE);
        super.visit(element);
    }

    protected void visit(TestConfiguration element) throws VisitorException {
        resetDatatype(element);
        super.visit(element);
    }

    @Override
    protected void visit(TestConfigElementContainer element) throws VisitorException {
        resetDatatype(element);
        super.visit(element);
    }

    protected void visit(Property property) throws VisitorException {
        resetDatatype(property);
        super.visit(property);
    }

    protected void visit(PropertyContainer property) throws VisitorException {
        resetDatatype(property);
        super.visit(property);
    }

    protected void visit(TestScriptContainer testScript) throws VisitorException {
        resetDatatype(testScript);
        super.visit(testScript);
    }

    protected void visit(AttributeValue attribute) throws VisitorException {
        resetDatatype(attribute);
        super.visit(attribute);
    }

    protected void visit(Dependency dependency) throws VisitorException {
        resetDatatype(dependency);
        TestConfigElement element = this.elementMap.get(dependency.getElement().getId());

        if (element != null) {
            dependency.setElement(element);
        }

        super.visit(dependency);
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {

        if (datatype instanceof Dependency) {
            // Only visit Dependency and not the contained TestConfigElement
            this.visit((Dependency) datatype);
        } else if (datatype instanceof TestScriptContainer) {
            // Only visit Dependency and not the contained TestConfigElement
            this.visit((TestScriptContainer) datatype);
        } else {
            super.visit(datatype);
        }
    }

    private void resetDatatype(NabuccoDatatype datatype) {
        datatype.setDatatypeState(DatatypeState.INITIALIZED);
        datatype.setVersion((Version) null);
        datatype.setId((Identifier) null);

        if (datatype instanceof TestAutomationDatatype) {
            TestAutomationDatatype taDatatype = (TestAutomationDatatype) datatype;
            taDatatype.setIdentificationKey("");
        }
    }

}
