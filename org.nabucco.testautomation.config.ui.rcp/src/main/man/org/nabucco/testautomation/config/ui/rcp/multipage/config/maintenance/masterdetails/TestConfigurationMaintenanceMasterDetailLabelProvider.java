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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.layout.I18NLabelProviderContributor;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.images.ConfigImageRegistry;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyContainer;
import org.nabucco.testautomation.property.ui.rcp.images.PropertyImageRegistry;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * TestConfigurationMaintenanceMasterDetailLabelProvider
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationMaintenanceMasterDetailLabelProvider implements I18NLabelProviderContributor {

    private static final String NAME = "name";

    private static final String TEST_CONFIGURATION_MASTER_DETAILS_TREE = "TestConfigurationMasterDetailsTree";

    private static TestConfigurationMaintenanceMasterDetailLabelProvider instance = new TestConfigurationMaintenanceMasterDetailLabelProvider();

    private TestConfigurationMaintenanceMasterDetailLabelProvider() {
    }

    public static TestConfigurationMaintenanceMasterDetailLabelProvider getInstance() {
        return instance;
    }

    @Override
    public Map.Entry<String, Map<String, ? extends Serializable>> getText(final Object element) {
        Map.Entry<String, Map<String, ? extends Serializable>> result = null;
        if (element instanceof MasterDetailTreeNode) {
            MasterDetailTreeNode treeNode = (MasterDetailTreeNode) element;
            result = getText(treeNode.getDatatype());
        }
        return result;
    }

    /**
     * String representing a special datatype.
     * 
     * @param datatype
     * @return
     */
    private Map.Entry<String, Map<String, ? extends Serializable>> getText(final Datatype datatype) {
        Map.Entry<String, Map<String, ? extends Serializable>> result;

        if (datatype instanceof TestConfiguration) {
            TestConfiguration testConfiguration = (TestConfiguration) datatype;
            result = createEntry(TEST_CONFIGURATION_MASTER_DETAILS_TREE, NAME, testConfiguration.getName().getValue());

        } else if (datatype instanceof TestConfigElement) {
            TestConfigElement testConfigElement = (TestConfigElement) datatype;
            result = createEntry(TEST_CONFIGURATION_MASTER_DETAILS_TREE, NAME, testConfigElement.getName().getValue());

        } else if (datatype instanceof Property) {
            Property property = (Property) datatype;
            result = createEntry(TEST_CONFIGURATION_MASTER_DETAILS_TREE, NAME, property.getName().getValue());

        } else {
            result = new AbstractMap.SimpleEntry<String, Map<String, ? extends Serializable>>(datatype.getClass()
                    .getSimpleName(), null);
        }

        return result;
    }

    private SimpleEntry<String, Map<String, ? extends Serializable>> createEntry(final String i18n, String key,
            String value) {
        return new AbstractMap.SimpleEntry<String, Map<String, ? extends Serializable>>(i18n, createMap(key, value));
    }

    /**
     * @param string
     * @param name
     * @return
     */
    private Map<String, ? extends Serializable> createMap(final String key, final String value) {
        Map<String, String> result = new HashMap<String, String>();
        result.put(key, value);
        return result;
    }

    @Override
    public String getImage(Object element) {
        if (element instanceof MasterDetailTreeNode) {

            MasterDetailTreeNode treeNode = (MasterDetailTreeNode) element;
            Datatype datatype = treeNode.getDatatype();
            return getImage(datatype);
        } else if (element instanceof Datatype) {
            return getImage((Datatype) element);
        }
        return null;
    }

    private String getImage(Datatype datatype) {

        if (datatype instanceof TestConfigElementContainer) {
            datatype = ((TestConfigElementContainer) datatype).getElement();
        } else if (datatype instanceof PropertyContainer) {
            datatype = ((PropertyContainer) datatype).getProperty();
        }

        if (datatype instanceof TestConfiguration) {
            return ConfigImageRegistry.ICON_CONFIG.getId();
        } else if (datatype instanceof TestConfigElement) {
            TestConfigElement tce = (TestConfigElement) datatype;
            SchemaElement schema = tce.getSchemaElement();

            switch (schema.getLevel()) {
            case ONE:
                return ConfigImageRegistry.ICON_SHEET.getId();
            case TWO:
                return ConfigImageRegistry.ICON_CASE.getId();
            default:
                return ConfigImageRegistry.ICON_STEP.getId();
            }

        } else if (datatype instanceof Property) {
            Property property = (Property) datatype;

            switch (property.getType()) {
            case LIST:
                return PropertyImageRegistry.ICON_PROPERTY_LIST.getId();
            case TEXT:
                return PropertyImageRegistry.ICON_PROPERTY_STRING.getId();
            case NUMERIC:
                return PropertyImageRegistry.ICON_PROPERTY_NUMERIC.getId();
            case XML:
                return PropertyImageRegistry.ICON_PROPERTY_XML.getId();
            case DATE:
                return PropertyImageRegistry.ICON_PROPERTY_DATE.getId();
            case BOOLEAN:
                return PropertyImageRegistry.ICON_PROPERTY_BOOLEAN.getId();
            case FILE:
                return PropertyImageRegistry.ICON_PROPERTY_FILE.getId();
            default:
                return PropertyImageRegistry.ICON_PROPERTY.getId();
            }
        }
        return null;
    }
}
