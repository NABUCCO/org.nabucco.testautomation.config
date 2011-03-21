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
package org.nabucco.testautomation.config.impl.service.produce;

import org.nabucco.framework.base.facade.component.NabuccoInstance;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;


/**
 * ProduceTestConfigurationServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ProduceTestConfigurationServiceHandlerImpl extends ProduceTestConfigurationServiceHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public TestConfigurationMsg produceTestConfiguration(EmptyServiceMessage msg)
			throws ProduceException {

		TestConfigurationMsg rs = new TestConfigurationMsg();
		TestConfiguration testConfiguration = new TestConfiguration();
		testConfiguration.setDatatypeState(DatatypeState.INITIALIZED);
		testConfiguration.setName("Enter name ...");
		testConfiguration.setOwner(NabuccoInstance.getInstance().getOwner());
		rs.setTestConfiguration(testConfiguration);
		return rs;
	}

}
