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
package org.nabucco.testautomation.config.impl.service;

import java.util.List;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.testautomation.config.facade.component.ConfigComponent;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.schema.facade.component.SchemaComponent;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.schema.facade.message.SchemaConfigListMsg;
import org.nabucco.testautomation.schema.facade.message.SchemaConfigSearchMsg;
import org.nabucco.testautomation.schema.facade.message.SchemaElementListMsg;
import org.nabucco.testautomation.schema.facade.message.SchemaElementSearchMsg;
import org.nabucco.testautomation.schema.facade.service.search.SearchSchemaConfig;
import org.nabucco.testautomation.schema.facade.service.search.SearchSchemaElement;

/**
 * SchemaSupport
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class SchemaSupport {

	private static SchemaSupport instance;
	
	private SearchSchemaConfig searchSchemaConfig;

	private SearchSchemaElement searchSchemaElement;
	
	private SchemaSupport() throws ConnectionException, ServiceException {
		ConfigComponent configComponent = ConfigComponentLocator.getInstance().getComponent();
		SchemaComponent schemaComponent = configComponent.getSchemaComponent();
		this.searchSchemaElement = schemaComponent.getSearchSchemaElement();
		this.searchSchemaConfig = schemaComponent.getSearchSchemaConfig();
	}
	
	public static synchronized SchemaSupport getInstance() throws ServiceException, ConnectionException {
		
		if (instance == null) {
			instance = new SchemaSupport();
		}
		return instance;
	}
	
	public void resolveSchemaElement(TestConfigElement testConfigElement, ServiceMessageContext ctx) throws SearchException {
		
		ServiceRequest<SchemaElementSearchMsg> rq = new ServiceRequest<SchemaElementSearchMsg>(ctx);
		SchemaElementSearchMsg msg = new SchemaElementSearchMsg();
		msg.setId(new Identifier(testConfigElement.getSchemaElementRefId()));
		rq.setRequestMessage(msg);
		SchemaElementListMsg rs = this.searchSchemaElement.searchSchemaElement(rq).getResponseMessage();
		List<SchemaElement> schemaList = rs.getSchemaElementList();
		
		if (schemaList.isEmpty()) {
			return;
		}
		testConfigElement.setSchemaElement(schemaList.get(0));
	}
	
	public void resolveSchemaConfig(TestConfiguration testConfiguration, ServiceMessageContext ctx) throws SearchException {

		ServiceRequest<SchemaConfigSearchMsg> rq = new ServiceRequest<SchemaConfigSearchMsg>(ctx);
		SchemaConfigSearchMsg msg = new SchemaConfigSearchMsg();
		msg.setId(new Identifier(testConfiguration.getSchemaConfigRefId()));
		rq.setRequestMessage(msg);
		SchemaConfigListMsg rs = this.searchSchemaConfig.searchSchemaConfig(rq).getResponseMessage();
		List<SchemaConfig> schemaConfigList = rs.getSchemaConfigList();
		
		if (schemaConfigList.isEmpty()) {
			return;
		}
		testConfiguration.setSchemaConfig(schemaConfigList.get(0));	
	}
	
}
