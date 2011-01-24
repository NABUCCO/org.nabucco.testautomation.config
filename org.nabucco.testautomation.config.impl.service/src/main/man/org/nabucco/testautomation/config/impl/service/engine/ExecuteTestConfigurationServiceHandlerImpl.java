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

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.impl.service.engine.visitor.TestConfigurationPreparationVisitor;
import org.nabucco.testautomation.engine.TestEngine;
import org.nabucco.testautomation.engine.base.context.TestContext;
import org.nabucco.testautomation.facade.datatype.engine.TestEngineConfiguration;
import org.nabucco.testautomation.facade.datatype.engine.TestExecutionInfo;
import org.nabucco.testautomation.facade.exception.engine.TestEngineException;

/**
 * ExecuteTestConfigurationServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ExecuteTestConfigurationServiceHandlerImpl extends
		ExecuteTestConfigurationServiceHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected TestInfoMsg executeTestConfiguration(TestExecutionMsg msg)
			throws TestEngineException {
		
		TestConfiguration testConfiguration = msg.getTestConfiguration();
		User user = this.getContext().getSubject().getUser();
		Code release = testConfiguration.getReleaseType();
		Code environment = testConfiguration.getEnvironmentType();
		
		try {
			TestConfigurationPreparationVisitor visitor = new TestConfigurationPreparationVisitor(this.getContext());
			visitor.visit(testConfiguration);
		} catch (ServiceException ex) {
			String error = "Error while resolving TestScripts: " + ex.getMessage();
			this.getLogger().error(ex, error);
			throw new TestEngineException(error);
		} catch (ConnectionException ex) {
			String error = "Could not connect to ScriptComponent: " + ex.getMessage();
			this.getLogger().error(ex, error);
			throw new TestEngineException(error);
		} catch (VisitorException ex) {
			String error = "Could not prepare TestConfiguration: " + ex.getMessage();
			this.getLogger().error(ex, error);
			throw new TestEngineException(error);
		}

		try {
			// Get TestEngineConfiguration
			TestEngineConfiguration testEngineConfiguration = TestEngineSupport
					.getTestEngineConfiguration(testConfiguration.getReleaseType(),
							testConfiguration.getEnvironmentType(), this.getContext(), user);
			TestEngineSupport
					.validateTestEngineConfigurationConfiguration(testEngineConfiguration);
			
			// Create TestContext
			TestContext testContext = TestEngineSupport.createTestContext(
					testEngineConfiguration, user, release, environment);

			// Clear cache
			TestEngineSupport.returnTestEngine(testEngineConfiguration);

			// Lookup TestEngine
			TestEngine engine = TestEngineSupport
					.getTestEngine(testEngineConfiguration);
		
			this.getLogger().info(
					"Start executing TestConfiguration '"
							+ testConfiguration.getName().getValue() + "' ...");
			TestExecutionInfo info = engine.executeTestConfiguration(
					testConfiguration, testContext);
			
			switch (info.getTestStatus()) {
			case FINISHED:
			case INITIALIZED:
			case RUNNING:
				this.getLogger().info("Execution started. JobId: " + info.getJobId().getValue());
				break;
			case REJECTED:
				this.getLogger().warning("Job rejected. TestEngine busy. Please try again later.");
				break;
			}
			
			TestInfoMsg rs = new TestInfoMsg();
			rs.setTestExecutionInfo(info);
			rs.setTestEngineConfiguration(testEngineConfiguration);
			return rs;
		} catch (RemoteException ex) {
			String error = "Could not connect to TestEngine: " + ex.getMessage();
			this.getLogger().error(ex, error);
			throw new TestEngineException(error);
		}
	}
	
}
