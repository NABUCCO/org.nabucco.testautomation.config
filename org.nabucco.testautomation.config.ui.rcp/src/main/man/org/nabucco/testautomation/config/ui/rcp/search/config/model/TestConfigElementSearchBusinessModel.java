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
package org.nabucco.testautomation.config.ui.rcp.search.config.model;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchModel;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchParameter;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.message.TestConfigElementListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.ui.rcp.browser.config.TestConfigurationListViewBrowserElement;
import org.nabucco.testautomation.config.ui.rcp.communication.ConfigComponentServiceDelegateFactory;
import org.nabucco.testautomation.config.ui.rcp.communication.search.SearchTestConfigElementDelegate;

import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyType;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyUsageType;
import org.nabucco.testautomation.facade.message.PropertyListMsg;
import org.nabucco.testautomation.facade.message.PropertySearchMsg;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.ui.rcp.communication.TestautomationComponentServiceDelegateFactory;
import org.nabucco.testautomation.ui.rcp.communication.search.SearchPropertyDelegate;

/**
 * Does the search for TestConfigElement.
 * 
 * @author Markus Jorroch, PRODYNA AG
 * 
 */
public class TestConfigElementSearchBusinessModel implements NabuccoComponentSearchModel {

    public static final String ID = "org.nabucco.testautomation.config.ui.search.config.model.TestConfigElementSearchBusinessModel";

    /**
     * {@inheritDoc}
     */
    @Override
    public TestConfigurationListViewBrowserElement search(
            NabuccoComponentSearchParameter searchViewModel) {
        return null;
    }

    public TestConfigElement[] searchConfigElement(SchemaElement schemaElement) {
       return searchConfigElement(schemaElement, null);
    }
    
    public TestConfigElement[] searchConfigElement(SchemaElement schemaElement, Owner owner) {
        TestConfigElementSearchMsg rq = new TestConfigElementSearchMsg();
        rq.setSchemaElement(schemaElement);

        List<TestConfigElement> result = null;
        try {
            SearchTestConfigElementDelegate searchDelegate = ConfigComponentServiceDelegateFactory
                    .getInstance().getSearchTestConfigElement();
            TestConfigElementListMsg response = searchDelegate.searchTestConfigElement(rq);

            result = response.getTestConfigElementList();
            return result.toArray(new TestConfigElement[result.size()]);
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
        }
        return new TestConfigElement[] {};
    }

    public Property[] searchProperty(PropertyType propertyType) {
        return searchProperty(propertyType, null);
    }
    
    public Property[] searchProperty(PropertyType propertyType, Owner owner) {
        PropertySearchMsg rq = new PropertySearchMsg();
        rq.setPropertyType(propertyType);
        rq.setUsageType(PropertyUsageType.TEST_CONFIG_ELEMENT_PARAM);
        rq.setOwner(owner);

        List<Property> result = null;
        try {
            SearchPropertyDelegate searchDelegate = TestautomationComponentServiceDelegateFactory
                    .getInstance().getSearchProperty();
            PropertyListMsg response = searchDelegate.searchProperty(rq);

            result = response.getPropertyList();
            return result.toArray(new Property[0]);
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
        }
        return new Property[] {};
    }

}
