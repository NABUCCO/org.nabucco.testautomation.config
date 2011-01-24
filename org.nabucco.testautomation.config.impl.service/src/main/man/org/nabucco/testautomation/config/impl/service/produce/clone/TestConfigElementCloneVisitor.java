/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.nabucco.testautomation.config.impl.service.produce.clone;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.datatype.visitor.TestConfigurationVisitor;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;

/**
 * TestConfigElementCloneVisitor
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestConfigElementCloneVisitor extends TestConfigurationVisitor {

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        super.visit(datatype);
    }

    @Override
    protected void visit(TestConfigElement element) throws VisitorException {
        
    	if (element.getReused() != null && element.getReused().getValue()) {
    		return;
    	}
    	resetDatatype(element);
        element.setSchemaElementRefId(element.getSchemaElement().getId());
        element.setReused(Boolean.FALSE);
        element.setElementKey((Key) null);
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

    private void resetDatatype(NabuccoDatatype datatype) {
        datatype.setDatatypeState(DatatypeState.INITIALIZED);
        datatype.setVersion(null);
        datatype.setId(null);
    }

}
