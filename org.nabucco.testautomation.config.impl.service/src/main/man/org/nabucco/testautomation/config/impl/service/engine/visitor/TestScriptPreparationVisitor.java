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
package org.nabucco.testautomation.config.impl.service.engine.visitor;

import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.testautomation.script.facade.datatype.dictionary.Action;
import org.nabucco.testautomation.script.facade.datatype.visitor.TestScriptVisitor;

/**
 * TestScriptPreparationVisitor
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestScriptPreparationVisitor extends TestScriptVisitor {

	private Code environment;
	
	private Code release;
	
	private Code brand;
	
	public TestScriptPreparationVisitor(Code environment, Code release,
			Code brand) {
		super();
		this.environment = environment;
		this.release = release;
		this.brand = brand;
	}

	@Override
	protected void visit(Action action) {
		MetadataPreparationVisitor visitor = new MetadataPreparationVisitor(this.environment, this.release, this.brand);
		visitor.visit(action.getMetadata());
		super.visit(action);
	}

}
