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
package org.nabucco.testautomation.config.impl.service.produce;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.message.DependencyMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;


/**
 * ProduceDependencyServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ProduceDependencyServiceHandlerImpl extends ProduceDependencyServiceHandler {

	private static final long serialVersionUID = 1L;

	@Override
	protected DependencyMsg produceDependency(
			TestConfigElementMsg msg) throws ProduceException {

		TestConfigElement element = msg.getTestConfigElement();
		DependencyMsg rs = new DependencyMsg();
		Dependency dependency = new Dependency();
		dependency.setDatatypeState(DatatypeState.INITIALIZED);
		dependency.setElement(element);
		rs.setDependency(dependency);
		return rs;
	}

}
