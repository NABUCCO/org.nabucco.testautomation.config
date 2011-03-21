/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication.engine;

import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.testautomation.config.facade.message.engine.ManualTestResultMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestResultMsg;
import org.nabucco.testautomation.config.facade.service.engine.TestEngineService;
import org.nabucco.testautomation.facade.exception.engine.TestEngineException;

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
     * @param session the NabuccoSession.
     * @param rq the TestExecutionMsg.
     * @return the TestInfoMsg.
     * @throws TestEngineException
     */
    public TestInfoMsg executeTestConfiguration(TestExecutionMsg rq, NabuccoSession session)
            throws TestEngineException {
        ServiceRequest<TestExecutionMsg> request = new ServiceRequest<TestExecutionMsg>(
                super.createServiceContext(session));
        request.setRequestMessage(rq);
        ServiceResponse<TestInfoMsg> rs;
        if ((service != null)) {
            rs = service.executeTestConfiguration(request);
        } else {
            throw new TestEngineException(
                    "Cannot execute service operation: TestEngineService.executeTestConfiguration");
        }
        return rs.getResponseMessage();
    }

    /**
     * Getter for the TestStatus.
     *
     * @param session the NabuccoSession.
     * @param rq the TestInfoMsg.
     * @return the TestInfoMsg.
     * @throws TestEngineException
     */
    public TestInfoMsg getTestStatus(TestInfoMsg rq, NabuccoSession session)
            throws TestEngineException {
        ServiceRequest<TestInfoMsg> request = new ServiceRequest<TestInfoMsg>(
                super.createServiceContext(session));
        request.setRequestMessage(rq);
        ServiceResponse<TestInfoMsg> rs;
        if ((service != null)) {
            rs = service.getTestStatus(request);
        } else {
            throw new TestEngineException(
                    "Cannot execute service operation: TestEngineService.getTestStatus");
        }
        return rs.getResponseMessage();
    }

    /**
     * Getter for the TestConfigurationResult.
     *
     * @param session the NabuccoSession.
     * @param rq the TestInfoMsg.
     * @return the TestResultMsg.
     * @throws TestEngineException
     */
    public TestResultMsg getTestConfigurationResult(TestInfoMsg rq, NabuccoSession session)
            throws TestEngineException {
        ServiceRequest<TestInfoMsg> request = new ServiceRequest<TestInfoMsg>(
                super.createServiceContext(session));
        request.setRequestMessage(rq);
        ServiceResponse<TestResultMsg> rs;
        if ((service != null)) {
            rs = service.getTestConfigurationResult(request);
        } else {
            throw new TestEngineException(
                    "Cannot execute service operation: TestEngineService.getTestConfigurationResult");
        }
        return rs.getResponseMessage();
    }

    /**
     * CancelTestConfiguration.
     *
     * @param session the NabuccoSession.
     * @param rq the TestInfoMsg.
     * @return the TestInfoMsg.
     * @throws TestEngineException
     */
    public TestInfoMsg cancelTestConfiguration(TestInfoMsg rq, NabuccoSession session)
            throws TestEngineException {
        ServiceRequest<TestInfoMsg> request = new ServiceRequest<TestInfoMsg>(
                super.createServiceContext(session));
        request.setRequestMessage(rq);
        ServiceResponse<TestInfoMsg> rs;
        if ((service != null)) {
            rs = service.cancelTestConfiguration(request);
        } else {
            throw new TestEngineException(
                    "Cannot execute service operation: TestEngineService.cancelTestConfiguration");
        }
        return rs.getResponseMessage();
    }

    /**
     * ReturnManualTestResult.
     *
     * @param session the NabuccoSession.
     * @param rq the ManualTestResultMsg.
     * @return the TestInfoMsg.
     * @throws TestEngineException
     */
    public TestInfoMsg returnManualTestResult(ManualTestResultMsg rq, NabuccoSession session)
            throws TestEngineException {
        ServiceRequest<ManualTestResultMsg> request = new ServiceRequest<ManualTestResultMsg>(
                super.createServiceContext(session));
        request.setRequestMessage(rq);
        ServiceResponse<TestInfoMsg> rs;
        if ((service != null)) {
            rs = service.returnManualTestResult(request);
        } else {
            throw new TestEngineException(
                    "Cannot execute service operation: TestEngineService.returnManualTestResult");
        }
        return rs.getResponseMessage();
    }
}
