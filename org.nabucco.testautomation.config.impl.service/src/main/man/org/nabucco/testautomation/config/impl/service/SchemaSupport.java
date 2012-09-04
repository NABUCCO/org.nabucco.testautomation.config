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
package org.nabucco.testautomation.config.impl.service;

import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.testautomation.schema.facade.component.SchemaComponentLocator;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.schema.facade.message.SchemaConfigListMsg;
import org.nabucco.testautomation.schema.facade.message.SchemaConfigSearchMsg;
import org.nabucco.testautomation.schema.facade.message.SchemaElementListMsg;
import org.nabucco.testautomation.schema.facade.message.SchemaElementSearchMsg;
import org.nabucco.testautomation.schema.facade.service.search.SearchSchema;

/**
 * SchemaSupport
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class SchemaSupport {

    private static SchemaSupport instance;

    private SearchSchema search;

    private SchemaSupport() {
    }

    public static synchronized SchemaSupport getInstance() {

        if (instance == null) {
            instance = new SchemaSupport();
        }
        return instance;
    }

    public SchemaConfig getSchemaConfig(Long id, ServiceMessageContext ctx) throws SearchException {

        if (search == null) {
            try {
                search = SchemaComponentLocator.getInstance().getComponent().getSearchSchema();
            } catch (Exception e) {
                throw new SearchException(e);
            }
        }
        
        ServiceRequest<SchemaConfigSearchMsg> rq = new ServiceRequest<SchemaConfigSearchMsg>(ctx);
        SchemaConfigSearchMsg msg = new SchemaConfigSearchMsg();
        msg.setId(new Identifier(id));
        rq.setRequestMessage(msg);
        SchemaConfigListMsg rs = this.search.searchSchemaConfig(rq).getResponseMessage();
        NabuccoList<SchemaConfig> schemaConfigList = rs.getSchemaConfigList();

        if (schemaConfigList.isEmpty()) {
            return null;
        }
        return schemaConfigList.first();
    }
    
    public SchemaElement getSchemaElement(Long id, ServiceMessageContext ctx) throws SearchException {

        if (search == null) {
            try {
                search = SchemaComponentLocator.getInstance().getComponent().getSearchSchema();
            } catch (Exception e) {
                throw new SearchException(e);
            }
        }
        
        ServiceRequest<SchemaElementSearchMsg> rq = new ServiceRequest<SchemaElementSearchMsg>(ctx);
        SchemaElementSearchMsg msg = new SchemaElementSearchMsg();
        msg.setId(new Identifier(id));
        rq.setRequestMessage(msg);
        SchemaElementListMsg rs = this.search.searchSchemaElement(rq).getResponseMessage();
        NabuccoList<SchemaElement> schemaElementList = rs.getSchemaElementList();

        if (schemaElementList.isEmpty()) {
            return null;
        }
        return schemaElementList.first();
    }

}
