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
package org.nabucco.testautomation.config.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.test.RuntimeTestSupport;
import org.nabucco.framework.common.dynamiccode.facade.component.DynamicCodeComponent;
import org.nabucco.framework.common.dynamiccode.facade.component.DynamicCodeComponentLocator;
import org.nabucco.framework.common.dynamiccode.facade.datatype.DynamicCodeCode;
import org.nabucco.framework.common.dynamiccode.facade.datatype.DynamicCodeCodeGroup;
import org.nabucco.framework.common.dynamiccode.facade.message.DynamicCodeCodeGroupListMsg;
import org.nabucco.framework.common.dynamiccode.facade.message.DynamicCodeCodeListMsg;
import org.nabucco.framework.common.dynamiccode.facade.message.maintain.DynamicCodeCodeGroupMaintainMsg;
import org.nabucco.framework.common.dynamiccode.facade.message.search.CodePathSearchMsg;
import org.nabucco.framework.common.dynamiccode.facade.message.search.DynamicCodeCodeGroupSearchMsg;
import org.nabucco.framework.common.dynamiccode.facade.service.maintain.MaintainDynamicCode;
import org.nabucco.framework.common.dynamiccode.facade.service.search.SearchDynamicCode;
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



public class TestConfigurationAndDynamicCodeTest extends RuntimeTestSupport {

	private MaintainTestConfiguration maintain;
	private SearchTestConfiguration search;
	private ProduceTestConfiguration produceTestConfiguration;
	private ProduceTestConfigElement produceTestConfigElement;
	
	private SearchDynamicCode searchDynamicCodeService;
	private MaintainDynamicCode maintainDynamicCodeService;

	@Before
	public void setUp() throws Exception {
		ConfigComponent component = super.getComponent(ConfigComponentLocator
				.getInstance());
		maintain = component.getMaintainTestConfiguration();
		search = component.getSearchTestConfiguration();
		produceTestConfiguration = component.getProduceTestConfiguration();
		produceTestConfigElement = component.getProduceTestConfigElement();
		
		DynamicCodeComponent dynamicCodeComponent = super.getComponent(DynamicCodeComponentLocator
                .getInstance());
		searchDynamicCodeService = dynamicCodeComponent.getSearchDynamicCode();
		maintainDynamicCodeService = dynamicCodeComponent.getMaintainDynamicCode();
	}
	
	@Test
	public void testDynamicCodeInteraction() throws Exception {

	    // created nested code groups if necessary
	    if (!checkForNestedCodeGroups()) {
	        createNestedCodeGroups();
	    }

	    String name = "MyTestConfiguration";
	    String description = "MyTestConfiguration";
	    ServiceRequest<EmptyServiceMessage> sr = new ServiceRequest<EmptyServiceMessage>(getServiceContext());
	    sr.setRequestMessage(new EmptyServiceMessage());
	    ServiceResponse<TestConfigurationMsg> produceResponse = produceTestConfiguration.produceTestConfiguration(sr);
	    TestConfiguration testConfiguration = produceResponse.getResponseMessage().getTestConfiguration();
	    testConfiguration.setName(name);
	    testConfiguration.setDescription(description);
	    testConfiguration.setSchemaConfigRefId(1L);

	    
	    String codePath;
	    List<DynamicCodeCode> codes;

	    // set environment to TEST
        codePath = testConfiguration.getEnvironmentTypeCodePath().getValue();
        codes = getMatchingDynamicCodesFromDynamicCodeComponent(codePath);
        for (Code code : codes) {
            if (code.getName().getValue().equals("TEST")) {
                testConfiguration.setEnvironmentType(code);
                break;
            }
        }

	    // set release type to MAJOR
	    codePath = testConfiguration.getReleaseTypeCodePath().getValue();
        codes = getMatchingDynamicCodesFromDynamicCodeComponent(codePath);	    
        for (Code code : codes) {
            if (code.getName().getValue().equals("MAJOR")) {
                testConfiguration.setReleaseType(code);
                break;
            }
        }

	    ServiceRequest<EmptyServiceMessage> pRq = new ServiceRequest<EmptyServiceMessage>(getServiceContext());
	    pRq.setRequestMessage(new EmptyServiceMessage());

//	    TestSheet testSheet = (TestSheet) produceTestConfigElement.produceTestConfigElement(pRq).getResponseMessage().getTestConfigElement();
//	    testSheet.setName("MyTestSheet");
//	    testSheet.setElementKey("001");
//	    testSheet.setSchemaElementRefId(3L);
//	    testSheet.setAttributeListRefId(0L);
//
//	    TestCase testCase = (TestCase) produceTestConfigElement.produceTestConfigElement(pRq).getResponseMessage().getTestConfigElement();
//	    testCase.setName("MyTestCase");
//	    testCase.setElementKey("002");
//	    testCase.setSchemaElementRefId(2L);
//	    testCase.setAttributeListRefId(0L);
//	    testSheet.getTestConfigElementList().add(testCase);
//
//	    TestStep testStep = (TestStep) produceTestConfigElement.produceTestConfigElement(pRq).getResponseMessage().getTestConfigElement();
//	    testStep.setName("MyTestStep");
//	    testStep.setElementKey("003");
//	    testStep.setSchemaElementRefId(1L);
//	    testStep.setAttributeListRefId(0L);
//	    testCase.getTestConfigElementList().add(testStep);
//	    testConfiguration.getTestConfigElementList().add(testSheet);

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

	    // search for the test configuration
	    ServiceRequest<TestConfigurationSearchMsg> searchRq = 
	        new ServiceRequest<TestConfigurationSearchMsg>(getServiceContext());
	    TestConfigurationSearchMsg searchRqMsg = new TestConfigurationSearchMsg();
	    searchRqMsg.setName(new Name(name));
	    searchRqMsg.setId(null);
	    searchRqMsg.setDescription(null);
	    searchRq.setRequestMessage(searchRqMsg);
	    ServiceResponse<TestConfigurationListMsg> searchRs = search.searchTestConfiguration(searchRq);
	    TestConfiguration loadedTestConfiguration = searchRs.getResponseMessage().getTestConfigList().get(0);
	    assertNotNull(loadedTestConfiguration);
	    assertEquals(name, testConfiguration.getName().getValue());
	    assertEquals("TEST", testConfiguration.getEnvironmentType().getName().getValue());
	    assertEquals("MAJOR", testConfiguration.getReleaseType().getName().getValue());
	}

	/**
	 * Create nested code groups, so that there is one code group environment in 
	 * code path nabucco.testautomation.environment and one code group release in code path
	 * nabucco.testautomation.release. 
	 * 
	 * @throws ServiceException
	 */
	private void createNestedCodeGroups() throws ServiceException {

	    DynamicCodeCodeGroup cg1 = new DynamicCodeCodeGroup();
	    cg1.setName("nabucco");
	    cg1.setOwner("NBC");
	    cg1.setDescription("nabucco code group");
	    cg1.setDatatypeState(DatatypeState.INITIALIZED);

	    DynamicCodeCodeGroup cg2 = new DynamicCodeCodeGroup();
	    cg2.setName("testautomation");
	    cg2.setOwner("NBC");
	    cg2.setDescription("testautomation code group");
	    cg2.setDatatypeState(DatatypeState.INITIALIZED);

	    DynamicCodeCodeGroup cg3 = new DynamicCodeCodeGroup();
	    cg3.setName("environment");
	    cg3.setOwner("NBC");
	    cg3.setDescription("environment code group");
	    cg3.setDatatypeState(DatatypeState.INITIALIZED);
	    
	    DynamicCodeCodeGroup cg4 = new DynamicCodeCodeGroup();
        cg4.setName("release");
        cg4.setOwner("NBC");
        cg4.setDescription("release code group");
        cg4.setDatatypeState(DatatypeState.INITIALIZED);

        // initialize codes for environment
	    DynamicCodeCode c1 = new DynamicCodeCode();
	    c1.setName("DEV");
	    c1.setOwner("NBC");
	    c1.setDescription("Development environment");
	    c1.setDatatypeState(DatatypeState.INITIALIZED);

	    DynamicCodeCode c2 = new DynamicCodeCode();
	    c2.setName("TEST");
	    c2.setOwner("NBC");
	    c2.setDescription("Testing environment");
	    c2.setDatatypeState(DatatypeState.INITIALIZED);

	    DynamicCodeCode c3 = new DynamicCodeCode();
	    c3.setName("PROD");
	    c3.setOwner("NBC");
	    c3.setDescription("Production environment");
	    c3.setDatatypeState(DatatypeState.INITIALIZED);

	    cg3.getCodeList().add(c1);
	    cg3.getCodeList().add(c2);
	    cg3.getCodeList().add(c3);
	    
	    // initialize codes for release
	    DynamicCodeCode c10 = new DynamicCodeCode();
        c10.setName("MAJOR");
        c10.setOwner("NBC");
        c10.setDescription("Major release");
        c10.setDatatypeState(DatatypeState.INITIALIZED);
	    
        DynamicCodeCode c11 = new DynamicCodeCode();
        c11.setName("MINOR");
        c11.setOwner("NBC");
        c11.setDescription("MINOR release");
        c11.setDatatypeState(DatatypeState.INITIALIZED);
        
        cg4.getCodeList().add(c10);
        cg4.getCodeList().add(c11);

	    cg1.getCodeGroupList().add(cg2);
	    cg2.getCodeGroupList().add(cg3);
	    cg2.getCodeGroupList().add(cg4);

	    DynamicCodeCodeGroupMaintainMsg msg = new DynamicCodeCodeGroupMaintainMsg();
	    msg.setCodeGroup(cg1);

	    ServiceRequest<DynamicCodeCodeGroupMaintainMsg> rq = new ServiceRequest<DynamicCodeCodeGroupMaintainMsg>(
	            RuntimeTestSupport.createServiceContext());
	    rq.setRequestMessage(msg);

	    ServiceResponse<DynamicCodeCodeGroupMaintainMsg> rs = maintainDynamicCodeService.maintainDynamicCodeCodeGroup(rq);
	}

	public boolean checkForNestedCodeGroups() throws ServiceException {

	    DynamicCodeCodeGroupSearchMsg msg = new DynamicCodeCodeGroupSearchMsg();
	    msg.setName(new Name("environment"));

	    ServiceRequest<DynamicCodeCodeGroupSearchMsg> rq = new ServiceRequest<DynamicCodeCodeGroupSearchMsg>(
	            RuntimeTestSupport.createServiceContext());
	    rq.setRequestMessage(msg);

	    ServiceResponse<DynamicCodeCodeGroupListMsg> rs = searchDynamicCodeService.searchDynamicCodeCodeGroup(rq);

	    List<DynamicCodeCodeGroup> codeGroupList = rs.getResponseMessage()
	    .getDynamicCodeCodeGroupList();
	    if (codeGroupList.size() == 0) {
	        return false;
	    } else {
	        return true;
	    }
	}
	
	private List<DynamicCodeCode> getMatchingDynamicCodesFromDynamicCodeComponent(String codePath)
	throws Exception {

	    CodePathSearchMsg msg = new CodePathSearchMsg();
	    msg.setCodePath(new CodePath(codePath));

	    ServiceRequest<CodePathSearchMsg> rq = new ServiceRequest<CodePathSearchMsg>(
	            super.getServiceContext());
	    rq.setRequestMessage(msg);

	    ServiceResponse<DynamicCodeCodeListMsg> rs = searchDynamicCodeService.searchByCodePath(rq);

	    List<DynamicCodeCode> returnedDynamicCodeCodes = rs.getResponseMessage().getCodeList();
	    return returnedDynamicCodeCodes;
	}

}

