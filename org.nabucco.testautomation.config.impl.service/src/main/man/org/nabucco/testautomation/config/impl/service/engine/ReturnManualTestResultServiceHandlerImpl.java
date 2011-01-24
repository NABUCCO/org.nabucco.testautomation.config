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

import org.nabucco.testautomation.config.facade.message.engine.ManualTestResultMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.engine.TestEngine;
import org.nabucco.testautomation.engine.base.client.ClientInteraction;
import org.nabucco.testautomation.engine.base.client.ManualTestResultInput;
import org.nabucco.testautomation.facade.datatype.engine.TestEngineConfiguration;
import org.nabucco.testautomation.facade.datatype.engine.TestExecutionInfo;
import org.nabucco.testautomation.facade.exception.engine.TestEngineException;

/**
 * ReturnManualTestResultServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ReturnManualTestResultServiceHandlerImpl extends
		ReturnManualTestResultServiceHandler {

	private static final long serialVersionUID = 1L;

	@Override
	protected TestInfoMsg returnManualTestResult(ManualTestResultMsg msg)
			throws TestEngineException {

		if (msg.getTestResult() == null) {
			throw new TestEngineException("No ManualTestResult supplied");
		}
		
		TestEngineConfiguration config = msg.getTestEngineConfiguration();
		TestEngineSupport.validateTestEngineConfigurationConfiguration(config);		
		TestEngine engine = TestEngineSupport.getTestEngine(config);
		TestExecutionInfo testExecutionInfo = msg.getTestExecutionInfo();
		ClientInteraction userInput = new ManualTestResultInput(msg.getTestResult());
		
		try {
			engine.setClientInteraction(testExecutionInfo, userInput);
			testExecutionInfo = engine.getTestStatus(testExecutionInfo);
		} catch (RemoteException ex) {
			throw new TestEngineException(ex);
		}
		
		TestInfoMsg rs = new TestInfoMsg();
		rs.setTestExecutionInfo(testExecutionInfo);
		rs.setTestEngineConfiguration(config);
		return rs;
	}
	
}
