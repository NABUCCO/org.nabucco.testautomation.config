/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.communication.engine;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.plugin.base.logging.NabuccoLogMessage;
import org.nabucco.testautomation.config.facade.message.engine.ManualTestResultMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestResultMsg;
import org.nabucco.testautomation.config.facade.service.engine.TestEngineService;

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
     * @param rq the TestExecutionMsg.
     * @return the TestInfoMsg.
     * @throws ClientException
     */
    public TestInfoMsg executeTestConfiguration(TestExecutionMsg rq) throws ClientException {
        ServiceRequest<TestExecutionMsg> request = new ServiceRequest<TestExecutionMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestInfoMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.executeTestConfiguration(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(TestEngineServiceDelegate.class, "Service: ",
                                "TestEngineService.executeTestConfiguration", " Time: ", String
                                        .valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: TestEngineService.executeTestConfiguration");
    }

    /**
     * Getter for the TestStatus.
     *
     * @param rq the TestInfoMsg.
     * @return the TestInfoMsg.
     * @throws ClientException
     */
    public TestInfoMsg getTestStatus(TestInfoMsg rq) throws ClientException {
        ServiceRequest<TestInfoMsg> request = new ServiceRequest<TestInfoMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestInfoMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.getTestStatus(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(TestEngineServiceDelegate.class, "Service: ",
                                "TestEngineService.getTestStatus", " Time: ", String
                                        .valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: TestEngineService.getTestStatus");
    }

    /**
     * Getter for the TestConfigurationResult.
     *
     * @param rq the TestInfoMsg.
     * @return the TestResultMsg.
     * @throws ClientException
     */
    public TestResultMsg getTestConfigurationResult(TestInfoMsg rq) throws ClientException {
        ServiceRequest<TestInfoMsg> request = new ServiceRequest<TestInfoMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestResultMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.getTestConfigurationResult(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(TestEngineServiceDelegate.class, "Service: ",
                                "TestEngineService.getTestConfigurationResult", " Time: ", String
                                        .valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: TestEngineService.getTestConfigurationResult");
    }

    /**
     * CancelTestConfiguration.
     *
     * @param rq the TestInfoMsg.
     * @return the TestInfoMsg.
     * @throws ClientException
     */
    public TestInfoMsg cancelTestConfiguration(TestInfoMsg rq) throws ClientException {
        ServiceRequest<TestInfoMsg> request = new ServiceRequest<TestInfoMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestInfoMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.cancelTestConfiguration(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(TestEngineServiceDelegate.class, "Service: ",
                                "TestEngineService.cancelTestConfiguration", " Time: ", String
                                        .valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: TestEngineService.cancelTestConfiguration");
    }

    /**
     * ReturnManualTestResult.
     *
     * @param rq the ManualTestResultMsg.
     * @return the TestInfoMsg.
     * @throws ClientException
     */
    public TestInfoMsg returnManualTestResult(ManualTestResultMsg rq) throws ClientException {
        ServiceRequest<ManualTestResultMsg> request = new ServiceRequest<ManualTestResultMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestInfoMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.returnManualTestResult(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(TestEngineServiceDelegate.class, "Service: ",
                                "TestEngineService.returnManualTestResult", " Time: ", String
                                        .valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: TestEngineService.returnManualTestResult");
    }
}
