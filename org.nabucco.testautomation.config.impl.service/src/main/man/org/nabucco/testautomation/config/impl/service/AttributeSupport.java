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

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.config.facade.message.ProduceAttributeValueMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceAttributeValue;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.schema.facade.datatype.attribute.Attribute;

/**
 * AttributeSupport
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class AttributeSupport {

	private static AttributeSupport instance;
	
	private ProduceAttributeValue produce;
	
	private AttributeSupport() throws ServiceException, ConnectionException {
		produce = ConfigComponentLocator.getInstance().getComponent().getProduceAttributeValue();
	}
	
	public static synchronized AttributeSupport getInstance() throws ServiceException, ConnectionException {
		
		if (instance == null) {
			instance = new AttributeSupport();
		}
		return instance;
	}
	
	public void resolveAttributes(TestConfigElement element, ServiceMessageContext ctx) throws ServiceException {
    	
		SchemaElement schema = element.getSchemaElement();

		if (schema.getAttributeList().isEmpty()) {
			element.getAttributeValueList().size();
		}
		
    	for (Attribute attribute : schema.getAttributeList()) {
    		AttributeValue value = null;
    		
	    	for (AttributeValue tmp : element.getAttributeValueList()) {
    			if (attribute.getId() != null && attribute.getId().equals(tmp.getAttributeRefId())) {
    				tmp.setAttribute(attribute);
    				value = tmp;
    				break;
    			}
    		}    		
    	
    		// No AttributeValue found for Attribute
    		if (value == null) {
    			addAttributeValue(attribute, element, ctx);
    		} else {
    			value.setDatatypeState(DatatypeState.PERSISTENT);
    		}
    	}
    }
    	
    private void addAttributeValue(Attribute attribute, TestConfigElement element, ServiceMessageContext ctx) throws ServiceException {
    	AttributeValue value = produceAttributeValue(attribute, ctx);
    	
    	if (value != null) {
    		element.getAttributeValueList().add(value);
    		element.setDatatypeState(DatatypeState.MODIFIED);
    	}
    }
	
	private AttributeValue produceAttributeValue(Attribute attribute, ServiceMessageContext ctx) throws ProduceException {
		
		if (produce != null) {
			ServiceRequest<ProduceAttributeValueMsg> rq = new ServiceRequest<ProduceAttributeValueMsg>(ctx);
			ProduceAttributeValueMsg msg = new ProduceAttributeValueMsg();
			msg.setAttribute(attribute);
			rq.setRequestMessage(msg);
			return produce.produceAttributeValue(rq).getResponseMessage().getAttributeValue();
		} else {
			return null;
		}
	}
	
}
