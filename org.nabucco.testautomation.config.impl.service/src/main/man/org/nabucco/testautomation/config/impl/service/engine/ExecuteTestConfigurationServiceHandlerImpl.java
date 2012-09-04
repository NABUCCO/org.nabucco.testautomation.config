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
package org.nabucco.testautomation.config.impl.service.engine;

import java.rmi.RemoteException;
import java.util.List;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Tenant;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.impl.service.engine.visitor.TestConfigurationPreparationVisitor;
import org.nabucco.testautomation.engine.TestEngine;
import org.nabucco.testautomation.engine.base.context.TestContext;
import org.nabucco.testautomation.settings.facade.datatype.engine.ExecutionStatusType;
import org.nabucco.testautomation.settings.facade.datatype.engine.TestEngineConfiguration;
import org.nabucco.testautomation.settings.facade.datatype.engine.TestExecutionInfo;
import org.nabucco.testautomation.settings.facade.datatype.engine.TestExecutionMode;
import org.nabucco.testautomation.settings.facade.exception.engine.TestEngineException;

/**
 * ExecuteTestConfigurationServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ExecuteTestConfigurationServiceHandlerImpl extends ExecuteTestConfigurationServiceHandler {

    private static final long serialVersionUID = 1L;

    private TestEngineSupport testEngineSupport;

    @Override
    protected TestInfoMsg executeTestConfiguration(TestExecutionMsg msg) throws TestEngineException {

        TestConfiguration testConfiguration = msg.getTestConfiguration();
        Flag resolveTestConfiguration = msg.getResolveTestConfiguration();

        // Check, if TestConfiguration needs to be resolved before execution
        if (resolveTestConfiguration != null
                && resolveTestConfiguration.getValue() != null && resolveTestConfiguration.getValue().booleanValue()) {
            testConfiguration = resolveTestConfiguration(testConfiguration);
        }

        Tenant tenant = this.getContext().getSubject().getTenant();
        testEngineSupport = new TestEngineSupport(tenant);
        User user = msg.getUser();

        if (user == null) {
            user = this.getContext().getSubject().getUser();
        }

        try {
            TestConfigurationPreparationVisitor visitor = new TestConfigurationPreparationVisitor(this.getContext());
            visitor.visit(testConfiguration);
        } catch (ServiceException ex) {
            String error = "Error while resolving TestScripts: " + ex.getMessage();
            this.getLogger().error(ex, error);
            throw new TestEngineException(error, ex);
        } catch (ConnectionException ex) {
            String error = "Could not connect to ScriptComponent: " + ex.getMessage();
            this.getLogger().error(ex, error);
            throw new TestEngineException(error, ex);
        } catch (VisitorException ex) {
            String error = "Could not prepare TestConfiguration: " + ex.getMessage();
            this.getLogger().error(ex, error);
            throw new TestEngineException(error, ex);
        }

        TestExecutionMode mode = testEngineSupport.getMode();

        if (mode == null) {
            throw new TestEngineException("No TestExecutionMode configured !");
        }

        switch (mode) {
        case DEV:
            return executeInDevMode(testConfiguration, user);
        case PROD:
            return executeInProdMode(testConfiguration, user);
        default:
            throw new TestEngineException("Unsupported TestExecutionMode: " + mode);
        }
    }

    private TestInfoMsg executeInProdMode(TestConfiguration testConfiguration, User user) throws TestEngineException {

        // Get TestEngineConfiguration
        List<TestEngineConfiguration> testEngineConfigurationList = testEngineSupport.getTestEngineConfigurationList(
                testConfiguration.getReleaseType(), testConfiguration.getEnvironmentType(), this.getContext(), user);

        this.getLogger().info(
                "Start executing TestConfiguration '" + testConfiguration.getName().getValue() + "' [Mode:PROD]");
        TestExecutionInfo info = null;

        for (TestEngineConfiguration testEngineConfiguration : testEngineConfigurationList) {

            testEngineSupport.validateTestEngineConfiguration(testEngineConfiguration);

            // Create TestContext
            TestContext testContext = testEngineSupport.createTestContext(testEngineConfiguration, user,
                    testConfiguration.getReleaseType(), testConfiguration.getEnvironmentType());

            // Clear cache
            testEngineSupport.returnTestEngine(testEngineConfiguration);

            try {
                // Lookup TestEngine
                TestEngine engine = testEngineSupport.getTestEngine(testEngineConfiguration);

                info = engine.executeTestConfiguration(testConfiguration, testContext);

                switch (info.getTestStatus()) {
                case FINISHED:
                case INITIALIZED:
                case RUNNING:
                    this.getLogger().info("Execution started. JobId: " + info.getJobId().getValue());
                    TestInfoMsg rs = new TestInfoMsg();
                    rs.setTestExecutionInfo(info);
                    rs.setTestEngineConfiguration(testEngineConfiguration);
                    return rs;
                case REJECTED:
                    continue;
                }
            } catch (RemoteException ex) {
                String error = "Could not connect to TestEngine: " + ex.getMessage();
                this.getLogger().error(ex, error);
                continue;
            } catch (TestEngineException ex) {
                String error = "Could not connect to TestEngine: " + ex.getMessage();
                this.getLogger().error(ex, error);
                continue;
            }
        }

        this.getLogger().warning("All TestEngines are busy. Please try again later.");
        TestInfoMsg rs = new TestInfoMsg();
        info = new TestExecutionInfo();
        info.setTestStatus(ExecutionStatusType.REJECTED);
        rs.setTestExecutionInfo(info);
        return rs;
    }

    private TestInfoMsg executeInDevMode(TestConfiguration testConfiguration, User user) throws TestEngineException {

        try {
            // Get TestEngineConfiguration
            TestEngineConfiguration testEngineConfiguration = testEngineSupport
                    .getTestEngineConfiguration(testConfiguration.getReleaseType(),
                            testConfiguration.getEnvironmentType(), this.getContext(), user);
            testEngineSupport.validateTestEngineConfiguration(testEngineConfiguration);

            // Create TestContext
            TestContext testContext = testEngineSupport.createTestContext(testEngineConfiguration, user,
                    testConfiguration.getReleaseType(), testConfiguration.getEnvironmentType());

            // Clear cache
            testEngineSupport.returnTestEngine(testEngineConfiguration);

            // Lookup TestEngine
            TestEngine engine = testEngineSupport.getTestEngine(testEngineConfiguration);

            this.getLogger().info(
                    "Start executing TestConfiguration '" + testConfiguration.getName().getValue() + "' [Mode:DEV]");
            TestExecutionInfo info = engine.executeTestConfiguration(testConfiguration, testContext);

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
    
    /**
     * @param testConfiguration
     * @return
     * @throws TestEngineException
     */
    private TestConfiguration resolveTestConfiguration(TestConfiguration testConfiguration) throws TestEngineException {
        ServiceRequest<TestConfigurationSearchMsg> rq = new ServiceRequest<TestConfigurationSearchMsg>(getContext());
        TestConfigurationSearchMsg msg = new TestConfigurationSearchMsg();
        msg.setId(new Identifier(testConfiguration.getId()));
        rq.setRequestMessage(msg);
        try {
            NabuccoList<TestConfiguration> testConfigList = ConfigComponentLocator.getInstance().getComponent()
                    .getResolveConfig().resolveTestConfiguration(rq).getResponseMessage().getTestConfigList();

            if (testConfigList.isEmpty()) {
                throw new TestEngineException("Could not resolve TestConfiguration '"
                        + testConfiguration.getIdentificationKey() + "'");
            }
            return testConfigList.get(0);
        } catch (Exception e) {
            throw new TestEngineException("Could not resolve TestConfiguration '"
                    + testConfiguration.getIdentificationKey() + "'", e);
        }
    }

}
