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
package org.nabucco.testautomation.config.ui.rcp.edit.config.model;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.logging.Loggable;
import org.nabucco.framework.plugin.base.model.BusinessModel;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.ui.rcp.communication.ConfigComponentServiceDelegateFactory;
import org.nabucco.testautomation.config.ui.rcp.communication.maintain.MaintainTestConfigurationDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.produce.ProduceTestConfigurationDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.search.SearchTestConfigElementDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.search.SearchTestConfigurationDelegate;

import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.message.SchemaConfigListMsg;
import org.nabucco.testautomation.schema.facade.message.SchemaConfigSearchMsg;
import org.nabucco.testautomation.schema.ui.rcp.communication.SchemaComponentServiceDelegateFactory;
import org.nabucco.testautomation.schema.ui.rcp.communication.search.SearchSchemaConfigDelegate;

/**
 * TestConfigurationEditViewBusinessModel
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationEditViewBusinessModel implements BusinessModel, Loggable {

	public static String ID = "org.nabucco.testautomation.config.ui.rcp.edit.config.model.TestConfigurationEditViewBusinessModel";

	/**
	 * Saves a TestConfiguration .
	 * 
	 * @param testConfiguration
	 *            the TestConfiguration
	 * @return the TestConfiguration
	 * @throws ClientException 
	 */
	public TestConfiguration save(final TestConfiguration testConfiguration) throws ClientException {
		ConfigComponentServiceDelegateFactory configComponentServiceDelegateFactory = ConfigComponentServiceDelegateFactory
		.getInstance();
		try{
			MaintainTestConfigurationDelegate maintainTestConfigurationDelegate = configComponentServiceDelegateFactory
			.getMaintainTestConfiguration();

			TestConfigurationMsg request = createTestConfigurationMsg(testConfiguration);
			TestConfigurationMsg response = maintainTestConfigurationDelegate
			.maintainTestConfiguration(request);

			if (response != null) {
				return response.getTestConfiguration();
			}
		} catch (ClientException e){
			throw e; // for debugging issues
		}
		return testConfiguration;
	}

	/**
	 * Deletes a TestConfiguration.
	 * 
	 * @param testConfiguration
	 *            the test configuration to remove
	 * @throws ClientException
	 */
	public void delete(TestConfiguration testConfiguration) throws ClientException {
		ConfigComponentServiceDelegateFactory configComponent = ConfigComponentServiceDelegateFactory
		.getInstance();

		MaintainTestConfigurationDelegate maintainDelegate = configComponent
		.getMaintainTestConfiguration();

		TestConfigurationMsg request = this.createTestConfigurationMsg(testConfiguration);
		maintainDelegate.maintainTestConfiguration(request);

	}

	private TestConfigurationMsg createTestConfigurationMsg(TestConfiguration testConfiguration) {
		TestConfigurationMsg msg = new TestConfigurationMsg();
		msg.setTestConfiguration(testConfiguration);
		return msg;
	}

	@Override
	public String getID() {
		return TestConfigurationEditViewBusinessModel.ID;
	}

	public TestConfiguration readTestConfiguration(TestConfiguration testConfiguration) {
		try {
			ConfigComponentServiceDelegateFactory configComponentServiceDelegateFactory = ConfigComponentServiceDelegateFactory
			.getInstance();
			SearchTestConfigurationDelegate searchTestConfigurationDelegate = configComponentServiceDelegateFactory
			.getSearchTestConfiguration();

			TestConfigurationSearchMsg rq = new TestConfigurationSearchMsg();
			rq.setId(new Identifier(testConfiguration.getId()));
			TestConfigurationListMsg response = searchTestConfigurationDelegate
			.getTestConfiguration(rq);
			if (response != null && response.getTestConfigList().size() == 1) {
				return response.getTestConfigList().get(0);
			}
		} catch (ClientException e) {
			Activator.getDefault().logError(e);
		}
		return testConfiguration;
	}

	public TestConfigElement readTestConfigElement(TestConfigElement testConfigElement) {
		try {
			ConfigComponentServiceDelegateFactory configComponentServiceDelegateFactory = ConfigComponentServiceDelegateFactory
			.getInstance();
			SearchTestConfigElementDelegate searchTestConfigElementDelegate = configComponentServiceDelegateFactory
			.getSearchTestConfigElement();

			TestConfigElementSearchMsg rq = new TestConfigElementSearchMsg();
			rq.setId(new Identifier(testConfigElement.getId()));
			TestConfigElementMsg response = searchTestConfigElementDelegate
			.getTestConfigElement(rq);
			if (response != null && response.getTestConfigElement() != null) {
				return response.getTestConfigElement();
			}
		} catch (ClientException e) {
			Activator.getDefault().logError(e);
		}
		return testConfigElement;
	}

	public PropertyList readPropertyList(PropertyList propertyList) {
		return propertyList;
	}

	public List<SchemaConfig> getSchemaConfigList() {
		SchemaComponentServiceDelegateFactory schemaComponentServiceDelegateFactory = SchemaComponentServiceDelegateFactory
		.getInstance();
		SearchSchemaConfigDelegate searchSchemaConfigDelegate;
		try {
			searchSchemaConfigDelegate = schemaComponentServiceDelegateFactory.getSearchSchemaConfig();
			SchemaConfigSearchMsg rq = new SchemaConfigSearchMsg();
			rq.setOwner(Activator.getDefault().getModel().getSecurityModel().getSubject().getOwner());
			SchemaConfigListMsg response = searchSchemaConfigDelegate.searchSchemaConfig(rq);
			List<SchemaConfig> resultList = response.getSchemaConfigList();
			return resultList;
		} catch (ClientException e) {
			Activator.getDefault().logError(e);
		}
		return new ArrayList<SchemaConfig>();
	}

	public TestConfiguration importDatatype(TestConfiguration testConfiguration) {
		try {
			ConfigComponentServiceDelegateFactory configComponentServiceDelegateFactory = ConfigComponentServiceDelegateFactory
			.getInstance();
			ProduceTestConfigurationDelegate produceTestConfigurationDelegate = configComponentServiceDelegateFactory
			.getProduceTestConfiguration();

			TestConfigurationMsg rq = new TestConfigurationMsg();
			rq.setTestConfiguration(testConfiguration);
			rq.setImportConfig(new Flag(Boolean.TRUE));
			TestConfigurationMsg response = produceTestConfigurationDelegate
			.produceTestConfigurationClone(rq);
			if (response != null && response.getTestConfiguration() != null) {
				return response.getTestConfiguration();
			}
		} catch (ClientException e) {
			Activator.getDefault().logError(e);
		}
		return testConfiguration;
	}

}
