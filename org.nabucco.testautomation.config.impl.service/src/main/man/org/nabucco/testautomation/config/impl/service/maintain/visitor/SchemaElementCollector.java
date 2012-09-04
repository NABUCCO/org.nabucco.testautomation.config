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
package org.nabucco.testautomation.config.impl.service.maintain.visitor;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.schema.facade.datatype.visitor.SchemaConfigVisitor;

/**
 * SchemaElementCollector
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class SchemaElementCollector extends SchemaConfigVisitor {

    private Map<Long, SchemaElement> schemaElementMap = new HashMap<Long, SchemaElement>();
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void visit(SchemaElement element) throws VisitorException {
        
        if (element == null || element.getId() == null) {
            return;
        }

        this.schemaElementMap.put(element.getId(), element);
        super.visit(element);
    }

    /**
     * Getter for the schemaElementMap.
     * 
     * @return Returns the schemaElementMap.
     */
    public Map<Long, SchemaElement> getSchemaElementMap() {
        return this.schemaElementMap;
    }

}
