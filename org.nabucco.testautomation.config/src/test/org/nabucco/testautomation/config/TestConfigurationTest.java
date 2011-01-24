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
package org.nabucco.testautomation.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.test.RuntimeTestSupport;
import org.nabucco.testautomation.config.facade.component.ConfigComponent;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.facade.service.maintain.MaintainTestConfiguration;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestConfigElement;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestConfiguration;
import org.nabucco.testautomation.config.facade.service.search.SearchTestConfiguration;

import org.nabucco.testautomation.facade.datatype.base.HierarchyLevelType;
import org.nabucco.testautomation.schema.facade.component.SchemaComponent;
import org.nabucco.testautomation.schema.facade.component.SchemaComponentLocator;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.schema.facade.datatype.ScriptContainerType;
import org.nabucco.testautomation.schema.facade.message.SchemaConfigMsg;
import org.nabucco.testautomation.schema.facade.service.maintain.MaintainSchemaConfig;
import org.nabucco.testautomation.schema.facade.service.produce.ProduceSchemaConfig;


public class TestConfigurationTest extends RuntimeTestSupport {

	private MaintainTestConfiguration maintain;
	
	private MaintainSchemaConfig maintainSchema;
	
	private SearchTestConfiguration search;
	
	private ProduceTestConfiguration produceTestConfiguration;

	private ProduceTestConfigElement produceTestConfigElement;
	
	private ProduceSchemaConfig produce;

	@Before
	public void setUp() throws Exception {
		ConfigComponent component = super.getComponent(ConfigComponentLocator
				.getInstance());
		maintain = component.getMaintainTestConfiguration();
		search = component.getSearchTestConfiguration();
		produceTestConfiguration = component.getProduceTestConfiguration();
		produceTestConfigElement = component.getProduceTestConfigElement();
		
		SchemaComponent sc = super.getComponent(SchemaComponentLocator
				.getInstance());
		maintainSchema = sc.getMaintainSchemaConfig();
		produce = sc.getProduceSchemaConfig();
	}
	
	@Test
	public void testInsertTestConfiguration() throws Exception {
		
		SchemaConfig schemaConfig = getDefaultSchemaConfig();
		
		ServiceRequest<SchemaConfigMsg> schemaSr = new ServiceRequest<SchemaConfigMsg>(getServiceContext());
		SchemaConfigMsg schemaMsg = new SchemaConfigMsg();
		schemaMsg.setSchemaConfig(schemaConfig);
		schemaSr.setRequestMessage(schemaMsg);
		ServiceResponse<SchemaConfigMsg> schemaRs = maintainSchema.maintainSchemaConfig(schemaSr);
		schemaConfig = schemaRs.getResponseMessage().getSchemaConfig();
		testSheetSchema = schemaConfig.getSchemaElementList().get(0);
		testCaseSchema = testSheetSchema.getSchemaElementList().get(0);
		testStepSchema = testCaseSchema.getSchemaElementList().get(0);
		
		String name = "MyTestConfiguration";
		String description = "MyTestConfiguration";
		ServiceRequest<EmptyServiceMessage> sr = new ServiceRequest<EmptyServiceMessage>(getServiceContext());
		sr.setRequestMessage(new EmptyServiceMessage());
		ServiceResponse<TestConfigurationMsg> produceResponse = produceTestConfiguration.produceTestConfiguration(sr);
		TestConfiguration testConfiguration = produceResponse.getResponseMessage().getTestConfiguration();
		testConfiguration.setName(name);
		testConfiguration.setDescription(description);
		testConfiguration.setSchemaConfig(schemaConfig);
		testConfiguration.setEnvironmentTypeRefId(1L);
		testConfiguration.setReleaseTypeRefId(1L);
		
		ServiceRequest<EmptyServiceMessage> pRq = new ServiceRequest<EmptyServiceMessage>(getServiceContext());
		pRq.setRequestMessage(new EmptyServiceMessage());
		
//		TestSheet testSheet = (TestSheet) produceTestConfigElement.produceTestConfigElement(pRq).getResponseMessage().getTestConfigElement();
//		testSheet.setName("MyTestSheet");
//		testSheet.setElementKey("001");
//		testSheet.setSchemaElement(testSheetSchema);
//		testSheet.setAttributeListRefId(0L);
//		
//		TestCase testCase = (TestCase) produceTestConfigElement.produceTestConfigElement(pRq).getResponseMessage().getTestConfigElement();
//		testCase.setName("MyTestCase");
//		testCase.setElementKey("002");
//		testCase.setSchemaElement(testCaseSchema);
//		testCase.setAttributeListRefId(0L);
//		testSheet.getTestConfigElementList().add(testCase);
//		
//		TestStep testStep = (TestStep) produceTestConfigElement.produceTestConfigElement(pRq).getResponseMessage().getTestConfigElement();
//		testStep.setName("MyTestStep");
//		testStep.setElementKey("003");
//		testStep.setSchemaElement(testStepSchema);
//		testStep.setAttributeListRefId(0L);
//		testCase.getTestConfigElementList().add(testStep);
//		testConfiguration.getTestConfigElementList().add(testSheet);
		
		ServiceRequest<TestConfigurationMsg> rq = new ServiceRequest<TestConfigurationMsg>(getServiceContext());
		TestConfigurationMsg msg = new TestConfigurationMsg();
		msg.setTestConfiguration(testConfiguration);
		rq.setRequestMessage(msg);
		ServiceResponse<TestConfigurationMsg> rs = maintain.maintainTestConfiguration(rq);
		
		testConfiguration = rs.getResponseMessage().getTestConfiguration();
		assertNotNull(testConfiguration);
		assertNotNull(testConfiguration.getName());
		assertNotNull(testConfiguration.getDescription());
		assertEquals(name, testConfiguration.getName().getValue());
		assertEquals(description, testConfiguration.getDescription().getValue());
	}
	
	@Test
	public void testGetTestConfiguration() throws Exception {
		ServiceRequest<TestConfigurationSearchMsg> rq = new ServiceRequest<TestConfigurationSearchMsg>(getServiceContext());
		TestConfigurationSearchMsg msg = new TestConfigurationSearchMsg();
		rq.setRequestMessage(msg);
		Identifier id = new Identifier(1L);
		msg.setId(id);
		TestConfigurationListMsg rs = search.getTestConfiguration(rq).getResponseMessage();
		TestConfiguration testConfig = rs.getTestConfigList().get(0);
		System.out.println(testConfig);
	}
		
	@Test
	public void testDeleteTestConfiguration() throws Exception {
		ServiceRequest<TestConfigurationSearchMsg> rq = new ServiceRequest<TestConfigurationSearchMsg>(getServiceContext());
		TestConfigurationSearchMsg msg = new TestConfigurationSearchMsg();
		rq.setRequestMessage(msg);
		Identifier id = new Identifier(32L);
		msg.setId(id);
		TestConfigurationListMsg rs = search.getTestConfiguration(rq).getResponseMessage();
		TestConfiguration testConfig = rs.getTestConfigList().get(0);
		
		testConfig.setDatatypeState(DatatypeState.DELETED);
		
		ServiceRequest<TestConfigurationMsg> mrq = new ServiceRequest<TestConfigurationMsg>(getServiceContext());
		TestConfigurationMsg deleteMsg = new TestConfigurationMsg();
		deleteMsg.setTestConfiguration(testConfig);
		mrq.setRequestMessage(deleteMsg);
		maintain.maintainTestConfiguration(mrq);
		
		System.out.println(testConfig);
	}
	
	
	private SchemaConfig schemaConfig;
	
	private SchemaElement testSheetSchema;
	
	private SchemaElement testCaseSchema;
	
	private SchemaElement testStepSchema;
	
	protected SchemaConfig getDefaultSchemaConfig() throws ProduceException {
		
		if (schemaConfig == null) {
			ServiceRequest<EmptyServiceMessage> rq = new ServiceRequest<EmptyServiceMessage>(getServiceContext());
			rq.setRequestMessage(new EmptyServiceMessage());
			ServiceResponse<SchemaConfigMsg> produceResponse = produce.produceSchemaConfig(rq);
			schemaConfig = produceResponse.getResponseMessage().getSchemaConfig();
	        schemaConfig.setName("ProdynaConfig");
	        schemaConfig.setDescription("Default SchemaConfig for Prodyna");
	
	        SchemaElement testSheetSchema = getTestSheetSchema();
	        SchemaElement testCaseSchema = getTestCaseSchema();
	        testSheetSchema.getSchemaElementList().add(testCaseSchema);
	
	        SchemaElement testStepSchema = getTestStepSchema();
	        testCaseSchema.getSchemaElementList().add(testStepSchema);
	
	        schemaConfig.getSchemaElementList().add(testSheetSchema);
		}
        return schemaConfig;
    }
	
	protected SchemaElement getTestSheetSchema() {
		
		if (testSheetSchema == null) {
			testSheetSchema = new SchemaElement();
	        testSheetSchema.setName("TestSheet");
	        testSheetSchema.setLevel(HierarchyLevelType.ONE);
	        testSheetSchema.setHasDependencies(Boolean.FALSE);
	        testSheetSchema.setPropertyContainer(Boolean.TRUE);
	        testSheetSchema.setScriptsAllowed(ScriptContainerType.NONE);
	        testSheetSchema.setSkipable(Boolean.FALSE);
		}
		return testSheetSchema;
	}
	
	protected SchemaElement getTestCaseSchema() {
		
		if (testCaseSchema == null) {
			testCaseSchema = new SchemaElement();
	        testCaseSchema.setName("TestCase");
	        testCaseSchema.setLevel(HierarchyLevelType.TWO);
	        testCaseSchema.setHasDependencies(Boolean.FALSE);
	        testCaseSchema.setPropertyContainer(Boolean.TRUE);
	        testCaseSchema.setScriptsAllowed(ScriptContainerType.NONE);
	        testCaseSchema.setSkipable(Boolean.FALSE);
		}
		return testCaseSchema;
	}

	protected SchemaElement getTestStepSchema() {
		
		if (testStepSchema == null) {
			testStepSchema = new SchemaElement();
	        testStepSchema.setName("TestStep");
	        testStepSchema.setLevel(HierarchyLevelType.THREE);
	        testStepSchema.setHasDependencies(Boolean.TRUE);
	        testStepSchema.setPropertyContainer(Boolean.TRUE);
	        testStepSchema.setScriptsAllowed(ScriptContainerType.MANY);
	        testStepSchema.setSkipable(Boolean.TRUE);
		}
		return testStepSchema;
	}
	
}
