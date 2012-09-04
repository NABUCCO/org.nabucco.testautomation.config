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
package org.nabucco.testautomation.config.facade.service.engine;

import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.engine.ManualTestResultMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestResultMsg;
import org.nabucco.testautomation.settings.facade.exception.engine.TestEngineException;

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
    ServiceResponse<TestInfoMsg> getTestStatus(ServiceRequest<TestInfoMsg> rq) throws TestEngineException;

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
    ServiceResponse<TestInfoMsg> cancelTestConfiguration(ServiceRequest<TestInfoMsg> rq) throws TestEngineException;

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
