/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.testautomation.config.ui.web.communication.engine;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.testautomation.config.facade.message.engine.ManualTestResultMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestResultMsg;
import org.nabucco.testautomation.config.facade.service.engine.TestEngineService;
import org.nabucco.testautomation.settings.facade.exception.engine.TestEngineException;

/**
 * TestEngineServiceDelegate<p/>The Service to interact with a remote TestEngine<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-06-25
 */
public class TestEngineServiceDelegate extends ServiceDelegateSupport {

    private TestEngineService service;

    /**
     * Constructs a new TestEngineServiceDelegate instance.
     *
     * @param service the TestEngineService.
     */
    public TestEngineServiceDelegate(TestEngineService service) {
        super();
        this.service = service;
    }

    /**
     * ExecuteTestConfiguration.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the TestExecutionMsg.
     * @return the TestInfoMsg.
     * @throws TestEngineException
     */
    public TestInfoMsg executeTestConfiguration(TestExecutionMsg message, NabuccoSession session,
            ServiceSubContext... subContexts) throws TestEngineException {
        ServiceRequest<TestExecutionMsg> request = new ServiceRequest<TestExecutionMsg>(super.createServiceContext(
                session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestInfoMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.executeTestConfiguration(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(TestEngineService.class, "executeTestConfiguration", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new TestEngineException("Cannot execute service operation: TestEngineService.executeTestConfiguration");
    }

    /**
     * Getter for the TestStatus.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the TestInfoMsg.
     * @return the TestInfoMsg.
     * @throws TestEngineException
     */
    public TestInfoMsg getTestStatus(TestInfoMsg message, NabuccoSession session, ServiceSubContext... subContexts)
            throws TestEngineException {
        ServiceRequest<TestInfoMsg> request = new ServiceRequest<TestInfoMsg>(super.createServiceContext(session,
                subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestInfoMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.getTestStatus(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(TestEngineService.class, "getTestStatus", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new TestEngineException("Cannot execute service operation: TestEngineService.getTestStatus");
    }

    /**
     * Getter for the TestConfigurationResult.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the TestInfoMsg.
     * @return the TestResultMsg.
     * @throws TestEngineException
     */
    public TestResultMsg getTestConfigurationResult(TestInfoMsg message, NabuccoSession session,
            ServiceSubContext... subContexts) throws TestEngineException {
        ServiceRequest<TestInfoMsg> request = new ServiceRequest<TestInfoMsg>(super.createServiceContext(session,
                subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestResultMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.getTestConfigurationResult(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(TestEngineService.class, "getTestConfigurationResult", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new TestEngineException("Cannot execute service operation: TestEngineService.getTestConfigurationResult");
    }

    /**
     * CancelTestConfiguration.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the TestInfoMsg.
     * @return the TestInfoMsg.
     * @throws TestEngineException
     */
    public TestInfoMsg cancelTestConfiguration(TestInfoMsg message, NabuccoSession session,
            ServiceSubContext... subContexts) throws TestEngineException {
        ServiceRequest<TestInfoMsg> request = new ServiceRequest<TestInfoMsg>(super.createServiceContext(session,
                subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestInfoMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.cancelTestConfiguration(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(TestEngineService.class, "cancelTestConfiguration", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new TestEngineException("Cannot execute service operation: TestEngineService.cancelTestConfiguration");
    }

    /**
     * ReturnManualTestResult.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the ManualTestResultMsg.
     * @return the TestInfoMsg.
     * @throws TestEngineException
     */
    public TestInfoMsg returnManualTestResult(ManualTestResultMsg message, NabuccoSession session,
            ServiceSubContext... subContexts) throws TestEngineException {
        ServiceRequest<ManualTestResultMsg> request = new ServiceRequest<ManualTestResultMsg>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestInfoMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.returnManualTestResult(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(TestEngineService.class, "returnManualTestResult", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new TestEngineException("Cannot execute service operation: TestEngineService.returnManualTestResult");
    }
}
