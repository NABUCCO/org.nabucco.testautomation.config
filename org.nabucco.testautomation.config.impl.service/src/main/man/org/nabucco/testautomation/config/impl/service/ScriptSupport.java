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
package org.nabucco.testautomation.config.impl.service;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.testautomation.config.facade.component.ConfigComponent;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.script.facade.component.ScriptComponent;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;
import org.nabucco.testautomation.script.facade.message.TestScriptListMsg;
import org.nabucco.testautomation.script.facade.message.TestScriptMsg;
import org.nabucco.testautomation.script.facade.message.TestScriptSearchMsg;
import org.nabucco.testautomation.script.facade.service.search.SearchTestScript;

/**
 * ScriptSupport
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ScriptSupport {

	private static ScriptSupport instance;
	
	private SearchTestScript searchTestScript;
	
	private ScriptSupport() throws ConnectionException, ServiceException {
		ConfigComponent configComponent = ConfigComponentLocator.getInstance().getComponent();
		ScriptComponent scriptComponent = configComponent.getScriptComponent();
		this.searchTestScript = scriptComponent.getSearchTestScript();
	}
	
	public static synchronized ScriptSupport getInstance() throws ServiceException, ConnectionException {
		
		if (instance == null) {
			instance = new ScriptSupport();
		}
		return instance;
	}
	
	public void resolveTestScripts(TestConfigElement testConfigElement,
			boolean loadTestScripts, ServiceMessageContext ctx)
			throws SearchException {
	
		// Load TestScripts deep
		if (loadTestScripts) {
			ServiceRequest<TestScriptSearchMsg> rq = new ServiceRequest<TestScriptSearchMsg>(ctx);
			TestScriptSearchMsg msg = new TestScriptSearchMsg();
			
			for (TestScriptContainer container : testConfigElement.getTestScriptList()) {
				container.setDatatypeState(DatatypeState.PERSISTENT);
				msg.setIdentifier(new Identifier(container.getTestScriptRefId()));
				rq.setRequestMessage(msg);
				TestScriptMsg rs = this.searchTestScript.getTestScript(rq).getResponseMessage();
				TestScript testScript = rs.getTestScript();
				container.setTestScript(testScript);
			}
		} else { // Load shallow
			ServiceRequest<TestScriptSearchMsg> rq = new ServiceRequest<TestScriptSearchMsg>(ctx);
			TestScriptSearchMsg msg = new TestScriptSearchMsg();
			
			for (TestScriptContainer container : testConfigElement.getTestScriptList()) {
				container.setDatatypeState(DatatypeState.PERSISTENT);
				msg.setIdentifier(new Identifier(container.getTestScriptRefId()));
				rq.setRequestMessage(msg);
				TestScriptListMsg rs = this.searchTestScript.searchTestScript(rq).getResponseMessage();
				
				if (!rs.getTestScriptList().isEmpty()) {
					TestScript testScript = rs.getTestScriptList().get(0);
					container.setTestScript(testScript);
				}
			}
		}
	}
	
}
