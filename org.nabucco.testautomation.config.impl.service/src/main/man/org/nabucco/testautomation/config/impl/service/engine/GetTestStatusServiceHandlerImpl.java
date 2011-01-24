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
package org.nabucco.testautomation.config.impl.service.engine;

import java.rmi.RemoteException;

import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.engine.TestEngine;
import org.nabucco.testautomation.facade.datatype.engine.TestEngineConfiguration;
import org.nabucco.testautomation.facade.datatype.engine.TestExecutionInfo;
import org.nabucco.testautomation.facade.exception.engine.TestEngineException;

/**
 * GetTestStatusServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class GetTestStatusServiceHandlerImpl extends
		GetTestStatusServiceHandler {

	private static final long serialVersionUID = 1L;

	@Override
	protected TestInfoMsg getTestStatus(TestInfoMsg msg)
			throws TestEngineException {

		TestEngineConfiguration config = msg.getTestEngineConfiguration();
		TestEngineSupport.validateTestEngineConfigurationConfiguration(config);
		TestEngine engine = TestEngineSupport.getTestEngine(config);
		TestExecutionInfo testExecutionInfo = msg.getTestExecutionInfo();

		try {
			TestExecutionInfo info = engine.getTestStatus(testExecutionInfo);
			msg.setTestExecutionInfo(info);
			return msg;
		} catch (RemoteException ex) {
			
			try {
				// Second try
				Thread.sleep(2000);
				TestExecutionInfo info = engine.getTestStatus(testExecutionInfo);
				msg.setTestExecutionInfo(info);
				return msg;
			} catch (InterruptedException ex1) {
				throw new TestEngineException(ex1);
			} catch (RemoteException ex2) {
				String name = config.getName() != null ? config.getName().getValue() : TestEngineSupport.N_A; 
				String host = config.getHost() != null ? config.getHost().getValue() : TestEngineSupport.N_A;
				String port = config.getPort() != null ? config.getPort().getValue() : TestEngineSupport.N_A;
				super.getLogger().error(
						"Could not request TestResult of Job "
									+ testExecutionInfo.getJobId().getValue()
									+ " from TestEngine '"
								+ name + "' ["
								+ host + ":"
								+ port + "]");
				throw new TestEngineException(ex);
			}
		}
	}
	
}
