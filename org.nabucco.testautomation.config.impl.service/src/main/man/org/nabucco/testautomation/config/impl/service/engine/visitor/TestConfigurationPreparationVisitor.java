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
package org.nabucco.testautomation.config.impl.service.engine.visitor;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.documentation.Documentation;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.datatype.visitor.TestConfigurationVisitor;
import org.nabucco.testautomation.script.facade.component.ScriptComponentLocator;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;
import org.nabucco.testautomation.script.facade.message.TestScriptSearchMsg;
import org.nabucco.testautomation.script.facade.service.resolve.ResolveScript;

/**
 * TestConfigurationPreparationVisitor
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestConfigurationPreparationVisitor extends
		TestConfigurationVisitor {

	private final Map<Long, TestScript> scriptCache;
	
	private final ResolveScript resolve;
	
	private ServiceRequest<TestScriptSearchMsg> rq;
	
	private TestScriptSearchMsg msg;
	
	private ServiceMessageContext ctx;

	private Code environment;
	
	private Code release;
	
	private Code brand;
	
	public TestConfigurationPreparationVisitor(ServiceMessageContext ctx) throws ServiceException, ConnectionException {
		this.scriptCache = new HashMap<Long, TestScript>();
		this.resolve = ScriptComponentLocator.getInstance().getComponent().getResolveScript();
		this.ctx = ctx;
		this.rq = new ServiceRequest<TestScriptSearchMsg>(ctx);
		this.msg = new TestScriptSearchMsg();
	}
	
	@Override
	protected void visit(TestConfiguration element) throws VisitorException {
		this.environment = element.getEnvironmentType();
		this.release = element.getReleaseType();
		super.visit(element);
	}

	@Override
	protected void visit(TestConfigElement element) throws VisitorException {
		
		if (element.getBrandType() == null) {
			element.setBrandType(this.brand);
		} else {
			this.brand = element.getBrandType();
		}
		
		// Remove unnecessary data
		element.setDescription((Description) null);
		element.setDocumentation((Documentation) null);
		
		for (TestScriptContainer container : element.getTestScriptList()) {
			this.visit(container);
		}
		super.visit(element);
	}
	
	protected void visit(TestScriptContainer scriptContainer)
			throws VisitorException {

		Long scriptId = scriptContainer.getTestScriptRefId();
		
		if (scriptId == null) {
			return;
		}
		
		TestScript script = getTestScript(scriptId);
		scriptContainer.setTestScript(script);
	}
	
	private TestScript getTestScript(Long id) throws VisitorException {
		
		// Try get TestScript from Cache
		TestScript script = this.scriptCache.get(id);
		
		if (script != null) {
			return script;
		}
		
		// Not cached, get from ScriptComponent
		msg.setIdentifier(new Identifier(id));
		rq.setRequestMessage(msg);
		
		try {
			script = this.resolve.resolveTestScript(rq).getResponseMessage().getTestScript();
		} catch (ResolveException ex) {
			throw new VisitorException("Error resolving TestScript with Id " + id);
		}
		
		if (script == null) {
			throw new VisitorException("No TestScript found with Id " + id);
		} else {
			// Remove unnecessary data
			script.setDescription((Description) null);
			script.setFolder(null);
			
			// Put TestScript into cache
			this.scriptCache.put(script.getId(), script);
		}
		
		new TestScriptPreparationVisitor(this.environment, this.release,
				this.brand, this.ctx, this.scriptCache, this.resolve).visit(script);
		return script;
	}
	
}
