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
package org.nabucco.testautomation.config.impl.service;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.script.facade.component.ScriptComponent;
import org.nabucco.testautomation.script.facade.component.ScriptComponentLocator;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;
import org.nabucco.testautomation.script.facade.message.TestScriptListMsg;
import org.nabucco.testautomation.script.facade.message.TestScriptMsg;
import org.nabucco.testautomation.script.facade.message.TestScriptSearchMsg;
import org.nabucco.testautomation.script.facade.service.resolve.ResolveScript;
import org.nabucco.testautomation.script.facade.service.search.SearchScript;

/**
 * ScriptSupport
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ScriptSupport {

	private static ScriptSupport instance;
	
	private SearchScript search;

	private ResolveScript resolve;
	
	private ScriptSupport() throws ConnectionException, ServiceException {
		ScriptComponent scriptComponent = ScriptComponentLocator.getInstance().getComponent();
		this.search = scriptComponent.getSearchScript();
		this.resolve = scriptComponent.getResolveScript();
	}
	
	public static synchronized ScriptSupport getInstance() throws ServiceException, ConnectionException {
		
		if (instance == null) {
			instance = new ScriptSupport();
		}
		return instance;
	}
	
	public void resolveTestScripts(TestConfigElement testConfigElement,
			boolean loadTestScripts, ServiceMessageContext ctx)
 throws SearchException, ResolveException {
	
		// Load TestScripts deep
		if (loadTestScripts) {
			ServiceRequest<TestScriptSearchMsg> rq = new ServiceRequest<TestScriptSearchMsg>(ctx);
			TestScriptSearchMsg msg = new TestScriptSearchMsg();
			
			for (TestScriptContainer container : testConfigElement.getTestScriptList()) {
				msg.setIdentifier(new Identifier(container.getTestScriptRefId()));
				rq.setRequestMessage(msg);
				TestScriptMsg rs = this.resolve.resolveTestScript(rq).getResponseMessage();
				TestScript testScript = rs.getTestScript();
				container.setTestScript(testScript);
			}
		} else { // Load shallow
			ServiceRequest<TestScriptSearchMsg> rq = new ServiceRequest<TestScriptSearchMsg>(ctx);
			TestScriptSearchMsg msg = new TestScriptSearchMsg();
			
			for (TestScriptContainer container : testConfigElement.getTestScriptList()) {
				msg.setIdentifier(new Identifier(container.getTestScriptRefId()));
				rq.setRequestMessage(msg);
				TestScriptListMsg rs = this.search.searchTestScript(rq).getResponseMessage();
				
				if (!rs.getTestScriptList().isEmpty()) {
					TestScript testScript = rs.getTestScriptList().get(0);
					container.setTestScript(testScript);
				}
			}
		}
	}
	
}
