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

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.property.facade.component.PropertyComponent;
import org.nabucco.testautomation.property.facade.component.PropertyComponentLocator;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyUsageType;
import org.nabucco.testautomation.property.facade.message.PropertyListMsg;
import org.nabucco.testautomation.property.facade.message.PropertyMsg;
import org.nabucco.testautomation.property.facade.message.PropertySearchMsg;
import org.nabucco.testautomation.property.facade.service.maintain.MaintainProperty;
import org.nabucco.testautomation.property.facade.service.search.SearchProperty;

/**
 * PropertySupport
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class PropertySupport {

    private SearchProperty searchProperty;

    private MaintainProperty maintainProperty;

    private ServiceMessageContext ctx;

    private Map<Long, PropertyList> propertyCache = new HashMap<Long, PropertyList>();

    public PropertySupport(ServiceMessageContext ctx) throws ServiceException {
        
        PropertyComponent testautomationComponent;
        try {
            testautomationComponent = PropertyComponentLocator.getInstance().getComponent();
        } catch (ConnectionException e) {
            throw new ServiceException(e);
        }
        this.searchProperty = testautomationComponent.getSearchProperty();
        this.maintainProperty = testautomationComponent.getMaintainProperty();
        this.ctx = ctx;
    }

    public void resolveProperties(TestConfigElement testConfigElement)
            throws SearchException {

        // resolve PropertyList
        Long propertyListRefId = testConfigElement.getPropertyListRefId();

        if (propertyListRefId != null) {

            // check PropertyCache
            if (this.propertyCache.containsKey(propertyListRefId)) {
                testConfigElement.setPropertyList(this.propertyCache.get(propertyListRefId));
            } else {
                PropertyList propertyList = getPropertyList(propertyListRefId);
                testConfigElement.setPropertyList(propertyList);
                this.propertyCache.put(propertyList.getId(), propertyList);
            }
        }
    }

    public PropertyList maintainPropertyList(PropertyList property) throws MaintainException {
        
        if (this.propertyCache.containsKey(property.getId())) {
            return this.propertyCache.get(property.getId());
        } else {
            ServiceRequest<PropertyMsg> rq = new ServiceRequest<PropertyMsg>(this.ctx);
            PropertyMsg msg = new PropertyMsg();
            property.setUsageType(PropertyUsageType.TEST_CONFIG_ELEMENT_PARAM);
            msg.setProperty(property);
            rq.setRequestMessage(msg);
            PropertyMsg rs = this.maintainProperty.maintainProperty(rq).getResponseMessage();
            property = (PropertyList) rs.getProperty();
            this.propertyCache.put(property.getId(), property);
            return property;            
        }
    }

    private PropertyList getPropertyList(Long refId) throws SearchException {
        ServiceRequest<PropertySearchMsg> rq = new ServiceRequest<PropertySearchMsg>(this.ctx);
        PropertySearchMsg msg = new PropertySearchMsg();
        msg.setPropertyId(new Identifier(refId));
        rq.setRequestMessage(msg);
        PropertyListMsg rs = this.searchProperty.searchProperty(rq).getResponseMessage();
        NabuccoList<Property> resultList = rs.getPropertyList();

        if (resultList.isEmpty()) {
            return null;
        }
        return (PropertyList) resultList.first();
    }

}
