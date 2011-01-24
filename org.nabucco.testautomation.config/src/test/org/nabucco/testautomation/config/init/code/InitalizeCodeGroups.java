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
package org.nabucco.testautomation.config.init.code;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.test.RuntimeTestSupport;
import org.nabucco.framework.common.dynamiccode.facade.component.DynamicCodeComponent;
import org.nabucco.framework.common.dynamiccode.facade.component.DynamicCodeComponentLocator;
import org.nabucco.framework.common.dynamiccode.facade.datatype.DynamicCodeCode;
import org.nabucco.framework.common.dynamiccode.facade.datatype.DynamicCodeCodeGroup;
import org.nabucco.framework.common.dynamiccode.facade.message.DynamicCodeCodeGroupListMsg;
import org.nabucco.framework.common.dynamiccode.facade.message.maintain.DynamicCodeCodeGroupMaintainMsg;
import org.nabucco.framework.common.dynamiccode.facade.message.search.DynamicCodeCodeGroupSearchMsg;
import org.nabucco.framework.common.dynamiccode.facade.service.maintain.MaintainDynamicCode;
import org.nabucco.framework.common.dynamiccode.facade.service.search.SearchDynamicCode;
import org.nabucco.testautomation.config.facade.component.ConfigComponent;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;



public class InitalizeCodeGroups extends RuntimeTestSupport {
	
	private SearchDynamicCode searchDynamicCodeService;
	private MaintainDynamicCode maintainDynamicCodeService;

	@Before
	public void setUp() throws Exception {
		ConfigComponent component = super.getComponent(ConfigComponentLocator
				.getInstance());
		DynamicCodeComponent dynamicCodeComponent = super.getComponent(DynamicCodeComponentLocator
                .getInstance());
		searchDynamicCodeService = dynamicCodeComponent.getSearchDynamicCode();
		maintainDynamicCodeService = dynamicCodeComponent.getMaintainDynamicCode();
	}
	
	@Test
	public void testDynamicCodeInteraction() throws Exception {

	    // created nested code groups if necessary
	    if (!checkForNestedCodeGroups()) {
	        System.out.println("Creating code groups for org.nabucco.testautomation.config...");
	        createNestedCodeGroups();
	        System.out.println("Created code groups for org.nabucco.testautomation.config successfully.");
	    }
	    else {
	        System.out.println("At least code group 'testautomation' exists. Exiting...");
	    }
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
        
        DynamicCodeCodeGroup cg5 = new DynamicCodeCodeGroup();
        cg5.setName("brand");
        cg5.setOwner("NBC");
        cg5.setDescription("brand code group");
        cg5.setDatatypeState(DatatypeState.INITIALIZED);

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
        c11.setDescription("Minor release");
        c11.setDatatypeState(DatatypeState.INITIALIZED);

        cg4.getCodeList().add(c10);
        cg4.getCodeList().add(c11);

        // initialize codes for brand
        DynamicCodeCode c20 = new DynamicCodeCode();
        c20.setName("MASTER");
        c20.setOwner("NBC");
        c20.setDescription("Master Brand (example)");
        c20.setDatatypeState(DatatypeState.INITIALIZED);

        cg5.getCodeList().add(c20);

	    cg1.getCodeGroupList().add(cg2);
	    cg2.getCodeGroupList().add(cg3);
	    cg2.getCodeGroupList().add(cg4);
	    cg2.getCodeGroupList().add(cg5);
	    

	    DynamicCodeCodeGroupMaintainMsg msg = new DynamicCodeCodeGroupMaintainMsg();
	    msg.setCodeGroup(cg1);

	    ServiceRequest<DynamicCodeCodeGroupMaintainMsg> rq = new ServiceRequest<DynamicCodeCodeGroupMaintainMsg>(
	            RuntimeTestSupport.createServiceContext());
	    rq.setRequestMessage(msg);

	    ServiceResponse<DynamicCodeCodeGroupMaintainMsg> rs = maintainDynamicCodeService.maintainDynamicCodeCodeGroup(rq);
	}

	public boolean checkForNestedCodeGroups() throws ServiceException {

	    DynamicCodeCodeGroupSearchMsg msg = new DynamicCodeCodeGroupSearchMsg();
	    msg.setName(new Name("testautomation"));

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
	
}

