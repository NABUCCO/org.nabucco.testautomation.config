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
package org.nabucco.testautomation.config.ui.rcp.search.config.model;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Identifier;
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
import org.nabucco.testautomation.config.ui.rcp.communication.search.SearchConfigDelegate;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyType;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyUsageType;
import org.nabucco.testautomation.property.facade.message.PropertyListMsg;
import org.nabucco.testautomation.property.facade.message.PropertySearchMsg;
import org.nabucco.testautomation.property.ui.rcp.communication.PropertyComponentServiceDelegateFactory;
import org.nabucco.testautomation.property.ui.rcp.communication.search.SearchPropertyDelegate;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

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
    public TestConfigurationListViewBrowserElement search(NabuccoComponentSearchParameter searchViewModel) {
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
            SearchConfigDelegate searchDelegate = ConfigComponentServiceDelegateFactory.getInstance().getSearchConfig();
            TestConfigElementListMsg response = searchDelegate.searchTestConfigElement(rq);

            result = response.getTestConfigElementList();
            return result.toArray(new TestConfigElement[result.size()]);
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
        }
        return new TestConfigElement[] {};
    }

    public Property[] searchProperty(PropertyType propertyType, boolean shallow) {
        PropertySearchMsg rq = new PropertySearchMsg();
        rq.setPropertyType(propertyType);
        rq.setUsageType(PropertyUsageType.TEST_CONFIG_ELEMENT_PARAM);

        List<Property> result = null;

        try {
            SearchPropertyDelegate searchDelegate = PropertyComponentServiceDelegateFactory.getInstance()
                    .getSearchProperty();
            if (shallow) {
                PropertyListMsg response = searchDelegate.searchPropertyShallow(rq);
                result = response.getPropertyList();
            } else {
                PropertyListMsg response = searchDelegate.searchProperty(rq);
                result = response.getPropertyList();
            }
            return result.toArray(new Property[0]);
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
        }

        return new Property[] {};
    }

    @SuppressWarnings("unchecked")
    public <P extends Property> P resolveProperty(P property) {
        PropertySearchMsg rq = new PropertySearchMsg();
        rq.setPropertyId(new Identifier(property.getId()));

        try {
            SearchPropertyDelegate searchProperty = PropertyComponentServiceDelegateFactory.getInstance()
                    .getSearchProperty();
            PropertyListMsg rs = searchProperty.searchProperty(rq);
            return (P) rs.getPropertyList().first();
        } catch (ClientException ce) {
            Activator.getDefault().logError(ce);
        }
        return property;
    }
}
