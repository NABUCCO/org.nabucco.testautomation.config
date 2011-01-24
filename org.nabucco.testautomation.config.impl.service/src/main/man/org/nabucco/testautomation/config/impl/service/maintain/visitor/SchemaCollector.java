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
package org.nabucco.testautomation.config.impl.service.maintain.visitor;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.visitor.TestConfigurationVisitor;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * SchemaElementCollector
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SchemaCollector extends TestConfigurationVisitor {

    private Map<Long, SchemaConfig> schemaConfigMap = new HashMap<Long, SchemaConfig>();

    private Map<Long, SchemaElement> schemaElementMap = new HashMap<Long, SchemaElement>();

    @Override
    protected void visit(TestConfiguration element) throws VisitorException {
        if (element == null) {
            return;
        }
        if (element.getSchemaConfig() == null || element.getSchemaConfigRefId() == null) {
            return;
        }

        Long id = element.getSchemaConfigRefId();
        SchemaConfig schemaConfig = element.getSchemaConfig();

        this.schemaConfigMap.put(id, schemaConfig);

        super.visit(element);
    }

    @Override
    protected void visit(TestConfigElement element) throws VisitorException {
        if (element == null) {
            return;
        }
        if (element.getSchemaElement() == null || element.getSchemaElementRefId() == null) {
            return;
        }

        Long id = element.getSchemaElementRefId();
        SchemaElement schemaElement = element.getSchemaElement();

        this.schemaElementMap.put(id, schemaElement);

        super.visit(element);
    }

    /**
     * Getter for the schemaConfigMap.
     * 
     * @return Returns the schemaConfigMap.
     */
    public Map<Long, SchemaConfig> getSchemaConfigMap() {
        return this.schemaConfigMap;
    }

    /**
     * Getter for the schemaElementMap.
     * 
     * @return Returns the schemaElementMap.
     */
    public Map<Long, SchemaElement> getSchemaElementMap() {
        return this.schemaElementMap;
    }

    /**
     * Creates a new schema allocator with collected schema elements.
     * 
     * @return the allocator
     */
    public SchemaAllocator createAllocator() {
        return new SchemaAllocator(this.getSchemaConfigMap(), this.getSchemaElementMap());
    }

}
