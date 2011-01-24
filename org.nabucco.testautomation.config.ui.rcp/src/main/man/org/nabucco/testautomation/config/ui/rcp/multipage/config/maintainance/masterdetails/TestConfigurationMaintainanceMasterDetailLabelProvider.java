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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails;

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

import org.nabucco.testautomation.facade.datatype.property.DoubleProperty;
import org.nabucco.testautomation.facade.datatype.property.IntegerProperty;
import org.nabucco.testautomation.facade.datatype.property.LongProperty;
import org.nabucco.testautomation.facade.datatype.property.StringProperty;
import org.nabucco.testautomation.facade.datatype.property.XmlProperty;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyComposite;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * TestConfigurationMaintainanceMasterDetailLabelProvider
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationMaintainanceMasterDetailLabelProvider implements
I18NLabelProviderContributor {

	private static final String ICON_SHEET = "icons/project.png";

	private static final String ICON_CASE = "icons/folder.png";

	private static final String ICON_PROPERTY = "icons/text.png";

	private static final String ICON_PROPERTY_LIST = "icons/browser_list.png";

	private static final String ICON_PROPERTY_STRING = "icons/text.png";

	private static final String ICON_PROPERTY_NUMERIC = "icons/calculator.png";

	private static final String ICON_PROPERTY_XML = "icons/xml.png";
	
	private static TestConfigurationMaintainanceMasterDetailLabelProvider instance = new TestConfigurationMaintainanceMasterDetailLabelProvider();
	
	private TestConfigurationMaintainanceMasterDetailLabelProvider(){
	}

	
	public static TestConfigurationMaintainanceMasterDetailLabelProvider getInstance(){
		return instance;
	}

	@Override
	public Map.Entry<String, Map<String, ? extends Serializable>> getText(
			final Object element) {
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
	private Map.Entry<String, Map<String, ? extends Serializable>> getText(
			final Datatype datatype) {
		Map.Entry<String, Map<String, ? extends Serializable>> result = new AbstractMap.SimpleEntry<String, Map<String, ? extends Serializable>>(
				datatype.toString(), null);

		if (datatype instanceof TestConfiguration) {
			TestConfiguration testConfiguration = (TestConfiguration) datatype;
			result = createEntry("TestConfigurationMasterDetailsTree", "name",
					testConfiguration.getName().getValue());

		} else if (datatype instanceof TestConfigElement) {
			TestConfigElement testConfigElement = (TestConfigElement) datatype;
			result = createEntry("TestConfigurationMasterDetailsTree", "name",
					testConfigElement.getName().getValue());

		} else if (datatype instanceof Property) {
			Property property = (Property) datatype;
			result = createEntry("TestConfigurationMasterDetailsTree", "name",
					property.getName().getValue());
		}
		return result;
	}

	private SimpleEntry<String, Map<String, ? extends Serializable>> createEntry(
			final String i18n, String key, String value) {
		return new AbstractMap.SimpleEntry<String, Map<String, ? extends Serializable>>(
				i18n, createMap(key, value));
	}

	/**
	 * @param string
	 * @param name
	 * @return
	 */
	private Map<String, ? extends Serializable> createMap(final String key,
			final String value) {
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
		} else if(element instanceof Datatype){
			return getImage((Datatype) element);
		}
		return null;
	}

	private String getImage(Datatype datatype) {
		if(datatype instanceof TestConfigElementContainer){
			datatype = ((TestConfigElementContainer) datatype).getElement();
		} else if(datatype instanceof PropertyContainer){
			datatype = ((PropertyContainer) datatype).getProperty();
		}


		if (datatype instanceof TestConfiguration) {
			return ConfigImageRegistry.ICON_CONFIG.getId();
		} else if (datatype instanceof TestConfigElement) {
			TestConfigElement tce = (TestConfigElement) datatype;
			SchemaElement schema = tce.getSchemaElement();

			switch (schema.getLevel()) {
			case ONE:
				return ICON_SHEET;
			case TWO:
				return ICON_CASE;
			default:
				return ConfigImageRegistry.ICON_STEP.getId();
			}

		} else if (datatype instanceof Property) {
			if (datatype instanceof PropertyComposite) {
				return ICON_PROPERTY_LIST;
			}
			if (datatype instanceof StringProperty) {
				return ICON_PROPERTY_STRING;
			}
			if (datatype instanceof IntegerProperty) {
				return ICON_PROPERTY_NUMERIC;
			}
			if (datatype instanceof LongProperty) {
				return ICON_PROPERTY_NUMERIC;
			}
			if (datatype instanceof DoubleProperty) {
				return ICON_PROPERTY_NUMERIC;
			}
			if (datatype instanceof XmlProperty) {
				return ICON_PROPERTY_XML;
			}
			return ICON_PROPERTY;
		}
		return null;
	}
}
