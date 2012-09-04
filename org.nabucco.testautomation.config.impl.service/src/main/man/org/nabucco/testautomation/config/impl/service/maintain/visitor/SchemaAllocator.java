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

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.visitor.TestConfigurationVisitor;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * SchemaElementAllocator
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SchemaAllocator extends TestConfigurationVisitor {

    private Map<Long, SchemaConfig> schemaConfigMap = new HashMap<Long, SchemaConfig>();

    private Map<Long, SchemaElement> schemaElementMap = new HashMap<Long, SchemaElement>();

    /**
     * Creates a new {@link SchemaAllocator} instance.
     * 
     * @param schemaConfigMap
     *            the schema configurations to allocate
     * 
     * @param schemaElementMap
     *            the schema elements to allocate
     */
    SchemaAllocator(Map<Long, SchemaConfig> schemaConfigMap,
            Map<Long, SchemaElement> schemaElementMap) {
        this.schemaConfigMap = schemaConfigMap;
        this.schemaElementMap = schemaElementMap;
    }

    /**
     * Allocate schema elemtns to the given datatype tree.
     * 
     * @param datatype
     *            the datatype to traverse
     * 
     * @throws VisitorException
     *             if the datatype tree cannot be traversed
     */
    public void allocate(Datatype datatype) throws VisitorException {
        datatype.accept(this);
    }

    @Override
    protected void visit(TestConfiguration element) throws VisitorException {
        Long id = element.getSchemaConfigRefId();
        element.setSchemaConfig(this.schemaConfigMap.get(id));
        super.visit(element);
    }

    @Override
    protected void visit(TestConfigElement element) throws VisitorException {
        Long id = element.getSchemaElementRefId();
        element.setSchemaElement(this.schemaElementMap.get(id));
        super.visit(element);
    }

}
