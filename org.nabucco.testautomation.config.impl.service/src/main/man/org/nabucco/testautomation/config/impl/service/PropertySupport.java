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
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.testautomation.config.facade.component.ConfigComponent;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.facade.component.TestautomationComponent;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyUsageType;
import org.nabucco.testautomation.facade.message.PropertyListMsg;
import org.nabucco.testautomation.facade.message.PropertyMsg;
import org.nabucco.testautomation.facade.message.PropertySearchMsg;
import org.nabucco.testautomation.facade.service.maintain.MaintainProperty;
import org.nabucco.testautomation.facade.service.search.SearchProperty;

/**
 * PropertySupport
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class PropertySupport {

	private static PropertySupport instance;
	
	private SearchProperty searchProperty;

	private MaintainProperty maintainProperty;
	
	private PropertySupport() throws ConnectionException, ServiceException {
		ConfigComponent configComponent = ConfigComponentLocator.getInstance().getComponent();
		TestautomationComponent testautomationComponent = configComponent.getTestautomationComponent();
		this.searchProperty = testautomationComponent.getSearchProperty();
		this.maintainProperty = testautomationComponent.getMaintainProperty();
	}
	
	public static synchronized PropertySupport getInstance() throws ServiceException, ConnectionException {
		
		if (instance == null) {
			instance = new PropertySupport();
		}
		return instance;
	}
	
	public void resolveProperties(TestConfigElement testConfigElement, ServiceMessageContext ctx) throws SearchException {
		
		// resolve PropertyList
		if (testConfigElement.getPropertyListRefId() != null) {
			PropertyList properties = getPropertyList(testConfigElement.getPropertyListRefId(), ctx);
			testConfigElement.setPropertyList(properties);
		}
	}
	
	public PropertyList maintainPropertyList(PropertyList property, ServiceMessageContext ctx) throws MaintainException {
        ServiceRequest<PropertyMsg> rq = new ServiceRequest<PropertyMsg>(ctx);
        PropertyMsg msg = new PropertyMsg();
        property.setUsageType(PropertyUsageType.TEST_CONFIG_ELEMENT_PARAM);
        msg.setProperty(property);
        rq.setRequestMessage(msg);
        PropertyMsg rs = this.maintainProperty.maintainProperty(rq).getResponseMessage();
        return (PropertyList) rs.getProperty();
    }
	
	private PropertyList getPropertyList(Long refId, ServiceMessageContext ctx) throws SearchException  {
		ServiceRequest<PropertySearchMsg> rq = new ServiceRequest<PropertySearchMsg>(ctx);
		PropertySearchMsg msg = new PropertySearchMsg();
		msg.setPropertyId(new Identifier(refId));
		rq.setRequestMessage(msg);
		PropertyListMsg rs = this.searchProperty.searchProperty(rq).getResponseMessage();
		List<Property> resultList = rs.getPropertyList();
		
		if (resultList.isEmpty()) {
			return null;
		}
		return (PropertyList) resultList.get(0);
	}
	
}
