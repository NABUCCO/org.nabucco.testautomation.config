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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.ProduceTestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementContainerMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.ui.rcp.communication.ConfigComponentServiceDelegateFactory;
import org.nabucco.testautomation.config.ui.rcp.communication.produce.ProduceTestConfigElementContainerDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.produce.ProduceTestConfigElementDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.produce.ProduceTestConfigurationDelegate;
import org.nabucco.testautomation.config.ui.rcp.search.config.model.TestConfigElementSearchBusinessModel;

import org.nabucco.testautomation.facade.datatype.property.BooleanProperty;
import org.nabucco.testautomation.facade.datatype.property.DateProperty;
import org.nabucco.testautomation.facade.datatype.property.DoubleProperty;
import org.nabucco.testautomation.facade.datatype.property.FileProperty;
import org.nabucco.testautomation.facade.datatype.property.IntegerProperty;
import org.nabucco.testautomation.facade.datatype.property.LongProperty;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.StringProperty;
import org.nabucco.testautomation.facade.datatype.property.XmlProperty;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyType;
import org.nabucco.testautomation.facade.message.ProducePropertyMsg;
import org.nabucco.testautomation.facade.message.PropertyMsg;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.ui.rcp.communication.TestautomationComponentServiceDelegateFactory;
import org.nabucco.testautomation.ui.rcp.communication.produce.ProducePropertyDelegate;

/**
 * TestConfigurationElementFactory
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationElementFactory {

	private static Map<Class<? extends Datatype>, Datatype> cache = new HashMap<Class<? extends Datatype>, Datatype>();

	private static Map<Class<? extends Property>, PropertyType> propertyTypeMap = new HashMap<Class<? extends Property>, PropertyType>();

	private static TestConfigElementSearchBusinessModel testConfigElementSearchBusinessModel = new TestConfigElementSearchBusinessModel();

	static {

		propertyTypeMap.put(PropertyList.class, PropertyType.LIST);
		propertyTypeMap.put(BooleanProperty.class, PropertyType.BOOLEAN);
		propertyTypeMap.put(DateProperty.class, PropertyType.DATE);
		propertyTypeMap.put(DoubleProperty.class, PropertyType.DOUBLE);
		propertyTypeMap.put(IntegerProperty.class, PropertyType.INTEGER);
		propertyTypeMap.put(LongProperty.class, PropertyType.LONG);
		propertyTypeMap.put(StringProperty.class, PropertyType.STRING);
		propertyTypeMap.put(XmlProperty.class, PropertyType.XML);
		propertyTypeMap.put(FileProperty.class, PropertyType.FILE);
		
	}

	public static TestConfiguration createTestConfiguration(SchemaConfig config) {
		TestConfiguration result = null;
		// Check cache
		Datatype datatype = cache.get(TestConfiguration.class);
		boolean foundInCache = false;
		if (datatype != null) {
			if (datatype instanceof TestConfiguration) {
				result = (TestConfiguration) datatype.cloneObject();
				result.setSchemaConfig(config);
				result.setSchemaConfigRefId(config.getId());
				foundInCache = true;
			} 
		}
		if (!foundInCache) {
			ConfigComponentServiceDelegateFactory configComponentServiceDelegateFactory = ConfigComponentServiceDelegateFactory
			.getInstance();
			try {
				// Produce
				ProduceTestConfigurationDelegate produceTestConfigurationDelegate = configComponentServiceDelegateFactory
				.getProduceTestConfiguration();
				TestConfigurationMsg response = produceTestConfigurationDelegate
				.produceTestConfiguration(new EmptyServiceMessage());
				TestConfiguration testConfiguration = response.getTestConfiguration();

				// Set SchemaConfig
				testConfiguration.setSchemaConfig(config);
				result = testConfiguration;
			} catch (ClientException e) {
				Activator.getDefault().logError(e);
			}
			// Update Cache
			Datatype clone = result.cloneObject();
			if (clone instanceof TestConfiguration) {
				TestConfiguration testConfiguration = (TestConfiguration) clone;
				testConfiguration.setSchemaConfig(null);
				testConfiguration.setSchemaConfigRefId(null);
			}
			cache.put(TestConfiguration.class, clone);
		}
		return result;
	}

	static TestConfigElement createTestConfigElement(SchemaElement schemaElement) {
			ConfigComponentServiceDelegateFactory configComponentServiceDelegateFactory = ConfigComponentServiceDelegateFactory
			.getInstance();
			try {
				// Produce
				ProduceTestConfigElementDelegate produceTestConfigElement = configComponentServiceDelegateFactory
				.getProduceTestConfigElement();
				ProduceTestConfigElementMsg produceMsg = new ProduceTestConfigElementMsg();
				produceMsg.setSchemaElement(schemaElement);
				TestConfigElementMsg response = produceTestConfigElement
				.produceTestConfigElement(produceMsg);
				TestConfigElement testConfigElement = response.getTestConfigElement();
				return testConfigElement;
			} catch (ClientException e) {
				Activator.getDefault().logError(e);
			}
		return null;
	}

	static PropertyContainer createProperty(Class<? extends Datatype> className, SchemaElement schemaElement) {
		PropertyContainer result = null;
		// Check cache
		Datatype datatype = cache.get(className);
		boolean foundInCache = false;
		if (datatype != null) {
			if (datatype instanceof PropertyContainer) {
				result = (PropertyContainer) datatype.cloneObject();
				foundInCache = true;
			}
		}
		if (!foundInCache) {
			try {
				if (propertyTypeMap.keySet().contains(className)) {
					TestautomationComponentServiceDelegateFactory testautomationComponentServiceDelegateFactory = TestautomationComponentServiceDelegateFactory
					.getInstance();
					ProducePropertyDelegate produceProperty = testautomationComponentServiceDelegateFactory
					.getProduceProperty();

					ProducePropertyMsg producePropertyMsg = new ProducePropertyMsg();
					producePropertyMsg.setPropertyType(propertyTypeMap.get(className));
					PropertyMsg response = produceProperty.produceProperty(producePropertyMsg);
					PropertyContainer propertyContainer = response.getPropertyContainer();
					result = propertyContainer;
				}
			} catch (ClientException e) {
				Activator.getDefault().logError(e);
			}
			// Update Cache
			Datatype clone = result.cloneObject();
			cache.put(className, clone);
		}
		return result;
	}

	public static Property[] getAllExistingProperties(Owner owner) {
		Property[] search = testConfigElementSearchBusinessModel.searchProperty(null, owner);
		return search;
	}

	public static Property[] getAllExistingPropertyLists(Owner owner) {
		Property[] search = testConfigElementSearchBusinessModel.searchProperty(PropertyType.LIST, owner);
		return search;
	}

	public static Datatype[] getExistingConfigElements(SchemaElement schemaElement, Owner owner) {
		TestConfigElement[] search = testConfigElementSearchBusinessModel
		.searchConfigElement(schemaElement, owner);
		return search;
	}

	public static TestConfigElementContainer createTestConfigElementContainer(
			TestConfigElement element) {
		try {
			ProduceTestConfigElementContainerDelegate produceTestConfigElementContainer = ConfigComponentServiceDelegateFactory
			.getInstance().getProduceTestConfigElementContainer();
			TestConfigElementMsg msg = new TestConfigElementMsg();
			msg.setTestConfigElement(element);
			TestConfigElementContainerMsg response = produceTestConfigElementContainer
			.produceTestConfigElementContainer(msg);
			return response.getTestConfigElementContainer();
		} catch (ClientException e) {
			Activator.getDefault().logError(e);
		}
		TestConfigElementContainer container = new TestConfigElementContainer();
		container.setDatatypeState(DatatypeState.INITIALIZED);
		container.setElement(element);
		return container;
	}

	public static PropertyContainer clone(Property propertyList) {
		TestautomationComponentServiceDelegateFactory testautomationComponentServiceDelegateFactory = TestautomationComponentServiceDelegateFactory.getInstance();
		ProducePropertyDelegate produceProperty;
		try {
			produceProperty = testautomationComponentServiceDelegateFactory.getProduceProperty();
			PropertyMsg rq = new PropertyMsg();
			rq.setProperty(propertyList);
			PropertyMsg rs = produceProperty.producePropertyClone(rq);
			return rs.getPropertyContainer();
		} catch (ClientException e) {
			Activator.getDefault().logError(e);
		}
		return null;
	}

	public static TestConfigElementContainer clone(TestConfigElement testConfigElement) {
		ConfigComponentServiceDelegateFactory componentServiceDelegateFactory = ConfigComponentServiceDelegateFactory
		.getInstance();
		ProduceTestConfigElementDelegate produceTestConfigElement;
		try {
			produceTestConfigElement = componentServiceDelegateFactory
			.getProduceTestConfigElement();
			TestConfigElementMsg rq = new TestConfigElementMsg();
			rq.setTestConfigElement(testConfigElement);
			TestConfigElementMsg testConfigElementClone = produceTestConfigElement
			.produceTestConfigElementClone(rq);
			return testConfigElementClone.getTestConfigElementContainer();
		} catch (ClientException e) {
			Activator.getDefault().logError(e);
		}
		return null;
	}

}
