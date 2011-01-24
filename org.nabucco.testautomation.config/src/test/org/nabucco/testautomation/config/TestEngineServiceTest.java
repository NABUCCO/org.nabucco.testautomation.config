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

import org.junit.Before;
import org.junit.Test;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.test.RuntimeTestSupport;
import org.nabucco.testautomation.config.facade.component.ConfigComponent;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.facade.service.engine.TestEngineService;

import org.nabucco.testautomation.facade.datatype.base.HierarchyLevelType;
import org.nabucco.testautomation.facade.datatype.property.IntegerProperty;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.StringProperty;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.schema.facade.datatype.ScriptContainerType;
import org.nabucco.testautomation.script.facade.datatype.dictionary.Logger;
import org.nabucco.testautomation.script.facade.datatype.dictionary.Loop;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TextMessage;
import org.nabucco.testautomation.script.facade.datatype.dictionary.type.LoggerLevelType;


public class TestEngineServiceTest extends RuntimeTestSupport {

	private TestEngineService testEngineService;
	
	@Before
	public void setUp() throws Exception {
		ConfigComponent component = super.getComponent(ConfigComponentLocator
				.getInstance());
		testEngineService = component.getTestEngineService();
	}
	
	@Test
	public void testExecuteTestConfiguration() throws Exception {
		
		ServiceRequest<TestExecutionMsg> rq = new ServiceRequest<TestExecutionMsg>(getServiceContext());
		TestExecutionMsg msg = new TestExecutionMsg();
		
		msg.setTestConfiguration(createTestConfiguration());
		
		rq.setRequestMessage(msg);
		ServiceResponse<TestInfoMsg> rs = testEngineService.executeTestConfiguration(rq);
		System.out.println(rs.getResponseMessage().getTestExecutionInfo());
		
	}
	
	private TestConfiguration createTestConfiguration() {
		TestConfiguration config = new TestConfiguration();
        config.setName("RuntimeTestConfiguration");
        config.setDescription("My TestConfiguration");

        SchemaConfig schemaConfig = new SchemaConfig();
        schemaConfig.setName("ProdynaConfig");
        schemaConfig.setDescription("Default SchemaConfig for Prodyna");

        SchemaElement testSheetSchema = new SchemaElement();
        testSheetSchema.setName("TestSheet");
        testSheetSchema.setLevel(HierarchyLevelType.ONE);
        testSheetSchema.setHasDependencies(Boolean.FALSE);
        testSheetSchema.setPropertyContainer(Boolean.TRUE);
        testSheetSchema.setScriptsAllowed(ScriptContainerType.NONE);
        testSheetSchema.setSkipable(Boolean.FALSE);

        SchemaElement testCaseSchema = new SchemaElement();
        testCaseSchema.setName("TestCase");
        testCaseSchema.setLevel(HierarchyLevelType.TWO);
        testCaseSchema.setHasDependencies(Boolean.FALSE);
        testCaseSchema.setPropertyContainer(Boolean.TRUE);
        testCaseSchema.setScriptsAllowed(ScriptContainerType.NONE);
        testCaseSchema.setSkipable(Boolean.FALSE);
        testSheetSchema.getSchemaElementList().add(testCaseSchema);

        SchemaElement testStepSchema = new SchemaElement();
        testStepSchema.setName("TestStep");
        testStepSchema.setLevel(HierarchyLevelType.THREE);
        testStepSchema.setHasDependencies(Boolean.TRUE);
        testStepSchema.setPropertyContainer(Boolean.TRUE);
        testStepSchema.setScriptsAllowed(ScriptContainerType.MANY);
        testStepSchema.setSkipable(Boolean.TRUE);
        testCaseSchema.getSchemaElementList().add(testStepSchema);

        schemaConfig.getSchemaElementList().add(testSheetSchema);
        config.setSchemaConfig(schemaConfig);

        TestConfigElement testSheet = new TestConfigElement();
        testSheet.setSchemaElement(testSheetSchema);
        testSheet.setName("MyTestSheet");
        PropertyList testSheetProperties = new PropertyList();
        Property testSheetProp = createProperty("TestSheetProperty", "My value");
        testSheetProperties.setName("testSheetProperties");
//        testSheetProperties.getPropertyList().add(testSheetProp);
        testSheet.setPropertyList(testSheetProperties);

        TestConfigElement testCase = new TestConfigElement();
        testCase.setSchemaElement(testCaseSchema);
        testCase.setName("MyTestCase");
        PropertyList testCaseProperties = new PropertyList();
        Property testCaseProp = createProperty("TestCaseProperty", 4711);
        testCaseProperties.setName("testCaseProperties");
//        testCaseProperties.getPropertyList().add(testCaseProp);
        testCase.setPropertyList(testCaseProperties);

        TestConfigElement testStep1 = new TestConfigElement();
        testStep1.setSchemaElement(testStepSchema);
        testStep1.setName("MyTestStep 1");
        testStep1.setSkip(Boolean.FALSE);
        TestScriptContainer w1 = new TestScriptContainer();
//        w1.getTestScriptList().add(createTestScript());
//        testStep1.setTestScriptWrapper(w1);
        PropertyList testStepProperties = new PropertyList();
        Property testStepProp = createProperty("TestStepProperty", "My value");
        testStepProperties.setName("testSheetProperties");
//        testStepProperties.getPropertyList().add(testStepProp);
        testStep1.setPropertyList(testStepProperties);

        TestConfigElement testStep2 = new TestConfigElement();
        testStep2.setSchemaElement(testStepSchema);
        testStep2.setName("MyTestStep 2");
        testStep2.setSkip(Boolean.FALSE);
        testStep2.setPropertyList(testStepProperties);
//        TestScriptWrapper w2 = new TestScriptWrapper();
//        w2.getTestScriptList().add(createTestScript());
//        testStep2.setTestScriptWrapper(w2);

//        testSheet.getTestConfigElementList().add(testCase);
//        testCase.getTestConfigElementList().add(testStep1);
//        testCase.getTestConfigElementList().add(testStep2);
//        config.getTestConfigElementList().add(testSheet);
        return config;
	}
	
	private Property createProperty(String name, String value) {
        StringProperty prop = new StringProperty();
        prop.setName(name);
        prop.setValue(value);
        return prop;
    }

    private Property createProperty(String name, Integer value) {
        IntegerProperty prop = new IntegerProperty();
        prop.setName(name);
        prop.setValue(value);
        return prop;
    }

//    private TestContext createContext() {
//    	TestContext context =  new TestContext();
//        StringProperty user = new StringProperty();
//        user.setName(TestContext.TEST_USERNAME);
//        user.setValue("sschmidt");
//        StringProperty testProp = new StringProperty();
//        testProp.setName("TEST_PROP");
//        testProp.setValue("12345");
//        context.put(user);
//        context.put(testProp);
//        return context;
//    }
    
    private long id = 4711;
    
    private TestScript createTestScript() {
        TestScript script = new TestScript();
        script.setId(id++);
        script.setName("TestScriptName");
        Loop loop = new Loop();
        loop.setId(1L);
        loop.setMaxIterations(3);
        loop.setWait(1000L);
        Logger logger = new Logger();
        logger.setId(2L);
        logger.setLevel(LoggerLevelType.INFO);
        TextMessage message = new TextMessage();
        message.setId(3L);
        message.setText("TestMessage");
//        logger.getElementList().add(message);
//        loop.getElementList().add(logger);
//        script.getElementList().add(loop);
        return script;
    }
	
}
