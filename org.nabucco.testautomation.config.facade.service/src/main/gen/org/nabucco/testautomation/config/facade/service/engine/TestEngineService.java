/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.service.engine;

import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.engine.ManualTestResultMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestResultMsg;
import org.nabucco.testautomation.facade.exception.engine.TestEngineException;

/**
 * TestEngineService<p/>The Service to interact with a remote TestEngine<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-06-25
 */
public interface TestEngineService extends Service {

    /**
     * Missing description at method executeTestConfiguration.
     *
     * @param rq the ServiceRequest<TestExecutionMsg>.
     * @return the ServiceResponse<TestInfoMsg>.
     * @throws TestEngineException
     */
    ServiceResponse<TestInfoMsg> executeTestConfiguration(ServiceRequest<TestExecutionMsg> rq)
            throws TestEngineException;

    /**
     * Missing description at method getTestStatus.
     *
     * @param rq the ServiceRequest<TestInfoMsg>.
     * @return the ServiceResponse<TestInfoMsg>.
     * @throws TestEngineException
     */
    ServiceResponse<TestInfoMsg> getTestStatus(ServiceRequest<TestInfoMsg> rq)
            throws TestEngineException;

    /**
     * Missing description at method getTestConfigurationResult.
     *
     * @param rq the ServiceRequest<TestInfoMsg>.
     * @return the ServiceResponse<TestResultMsg>.
     * @throws TestEngineException
     */
    ServiceResponse<TestResultMsg> getTestConfigurationResult(ServiceRequest<TestInfoMsg> rq)
            throws TestEngineException;

    /**
     * Missing description at method cancelTestConfiguration.
     *
     * @param rq the ServiceRequest<TestInfoMsg>.
     * @return the ServiceResponse<TestInfoMsg>.
     * @throws TestEngineException
     */
    ServiceResponse<TestInfoMsg> cancelTestConfiguration(ServiceRequest<TestInfoMsg> rq)
            throws TestEngineException;

    /**
     * Missing description at method returnManualTestResult.
     *
     * @param rq the ServiceRequest<ManualTestResultMsg>.
     * @return the ServiceResponse<TestInfoMsg>.
     * @throws TestEngineException
     */
    ServiceResponse<TestInfoMsg> returnManualTestResult(ServiceRequest<ManualTestResultMsg> rq)
            throws TestEngineException;
}
