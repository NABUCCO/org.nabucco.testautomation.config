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

import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.testautomation.config.facade.exception.InvalidActionException;
import org.nabucco.testautomation.script.facade.datatype.dictionary.Action;
import org.nabucco.testautomation.script.facade.datatype.dictionary.EmbeddedTestScript;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import org.nabucco.testautomation.script.facade.datatype.visitor.TestScriptVisitor;
import org.nabucco.testautomation.script.facade.message.TestScriptSearchMsg;
import org.nabucco.testautomation.script.facade.service.resolve.ResolveScript;

/**
 * TestScriptPreparationVisitor
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestScriptPreparationVisitor extends TestScriptVisitor {

	private final Map<Long, TestScript> scriptCache;
	
	private final ResolveScript resolve;
	
	private ServiceRequest<TestScriptSearchMsg> rq;
	
	private TestScriptSearchMsg msg;
	
	private ServiceMessageContext ctx;
	
	private Code environment;
	
	private Code release;
	
	private Code brand;
	
	public TestScriptPreparationVisitor(Code environment, Code release,
			Code brand, ServiceMessageContext ctx, Map<Long, TestScript> scriptCache, ResolveScript resolve) {
		super();
		this.environment = environment;
		this.release = release;
		this.brand = brand;
		this.scriptCache = scriptCache;
		this.resolve = resolve;
		this.ctx = ctx;
		this.rq = new ServiceRequest<TestScriptSearchMsg>(this.ctx);
		this.msg = new TestScriptSearchMsg();
	}

	@Override
	protected void visit(Action action) throws VisitorException {
		Metadata metadata = action.getMetadata();
		
		if (metadata == null) {
		    throw new InvalidActionException("No Metadata defined on Action '" + action.getName() + "'");
		}

		MetadataPreparationVisitor visitor = new MetadataPreparationVisitor(this.environment, this.release, this.brand);
		visitor.visit(metadata);
		super.visit(action);
	}

	@Override
	protected void visit(EmbeddedTestScript embeddedScript) throws VisitorException {
		
		TestScript script = embeddedScript.getTestScript();
		
		if (script != null && script.getId() != null) {
			script = getTestScript(script.getId());
			embeddedScript.setTestScript(script);
		}		
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
			throw new VisitorException("Error loading TestScript with Id " + id);
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
