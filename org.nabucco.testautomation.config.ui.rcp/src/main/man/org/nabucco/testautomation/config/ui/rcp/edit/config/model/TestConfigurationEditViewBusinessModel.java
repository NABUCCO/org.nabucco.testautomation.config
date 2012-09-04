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
import org.nabucco.testautomation.config.ui.rcp.communication.maintain.MaintainConfigDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.produce.ProduceConfigDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.resolve.ResolveConfigDelegate;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.message.SchemaConfigListMsg;
import org.nabucco.testautomation.schema.facade.message.SchemaConfigSearchMsg;
import org.nabucco.testautomation.schema.ui.rcp.communication.SchemaComponentServiceDelegateFactory;
import org.nabucco.testautomation.schema.ui.rcp.communication.search.SearchSchemaDelegate;

/**
 * TestConfigurationEditViewBusinessModel
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationEditViewBusinessModel implements BusinessModel, Loggable {

    public static String ID = "org.nabucco.testautomation.config.ui.rcp.edit.config.model.TestConfigurationEditViewBusinessModel";

    private ResolveConfigDelegate resolveConfigDelegate;

    private MaintainConfigDelegate maintainConfigDelegate;

    private ProduceConfigDelegate produceConfigDelegate;
    
    private SearchSchemaDelegate searchSchemaDelegate;
    
    /**
     * Saves a TestConfiguration .
     * 
     * @param testConfiguration
     *            the TestConfiguration
     * @return the TestConfiguration
     * @throws ClientException
     */
    public TestConfiguration save(final TestConfiguration testConfiguration) throws ClientException {
        init();
        TestConfigurationMsg request = createTestConfigurationMsg(testConfiguration);
        TestConfigurationMsg response = maintainConfigDelegate.maintainTestConfiguration(request);

        if (response != null) {
            return response.getTestConfiguration();
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
        init();
        TestConfigurationMsg request = this.createTestConfigurationMsg(testConfiguration);
        maintainConfigDelegate.maintainTestConfiguration(request);
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

    public TestConfiguration readTestConfiguration(TestConfiguration testConfiguration) throws ClientException {
        init();
        TestConfigurationSearchMsg rq = new TestConfigurationSearchMsg();
        rq.setId(new Identifier(testConfiguration.getId()));
        TestConfigurationListMsg response = resolveConfigDelegate.resolveTestConfiguration(rq);
        
        if (response != null && response.getTestConfigList().size() == 1) {
            return response.getTestConfigList().get(0);
        }
        return testConfiguration;
    }

    public TestConfigElement readTestConfigElement(TestConfigElement testConfigElement) throws ClientException {
        init();
        TestConfigElementSearchMsg rq = new TestConfigElementSearchMsg();
        rq.setId(new Identifier(testConfigElement.getId()));
        TestConfigElementMsg response = resolveConfigDelegate.resolveTestConfigElement(rq);
        
        if (response != null && response.getTestConfigElement() != null) {
            return response.getTestConfigElement();
        }
        return testConfigElement;
    }

    public List<SchemaConfig> getSchemaConfigList() {
        init();
        try {
            SchemaConfigSearchMsg rq = new SchemaConfigSearchMsg();
            SchemaConfigListMsg response = searchSchemaDelegate.searchSchemaConfig(rq);
            List<SchemaConfig> resultList = response.getSchemaConfigList();
            return resultList;
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
        }
        return new ArrayList<SchemaConfig>();
    }

    public TestConfiguration importDatatype(TestConfiguration testConfiguration) {
        init();
        try {
            TestConfigurationMsg rq = new TestConfigurationMsg();
            rq.setTestConfiguration(testConfiguration);
            rq.setImportConfig(new Flag(Boolean.TRUE));
            TestConfigurationMsg response = produceConfigDelegate.produceTestConfigurationClone(rq);
            
            if (response != null && response.getTestConfiguration() != null) {
                return response.getTestConfiguration();
            }
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
        }
        return testConfiguration;
    }
    
    private void init() {
        try {
            
            if (resolveConfigDelegate == null) {
                resolveConfigDelegate = ConfigComponentServiceDelegateFactory.getInstance().getResolveConfig();
            }
            
            if (maintainConfigDelegate == null) {
                maintainConfigDelegate = ConfigComponentServiceDelegateFactory.getInstance().getMaintainConfig();
            }
            
            if (produceConfigDelegate == null) {
                produceConfigDelegate = ConfigComponentServiceDelegateFactory.getInstance().getProduceConfig();
            }
            
            if (searchSchemaDelegate == null) {
                searchSchemaDelegate = SchemaComponentServiceDelegateFactory.getInstance().getSearchSchema();
            }
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
        }
    }

}
