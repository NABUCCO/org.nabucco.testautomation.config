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
package org.nabucco.testautomation.config.facade.service.produce;

import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.AttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.DependencyMsg;
import org.nabucco.testautomation.config.facade.message.ProduceAttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.ProduceTestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.ProduceTestScriptContainerMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementContainerMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;

/**
 * ProduceConfig<p/>Service to produce and clone TestConfigElements<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public interface ProduceConfig extends Service {

    /**
     * Missing description at method produceAttributeValue.
     *
     * @param rq the ServiceRequest<ProduceAttributeValueMsg>.
     * @return the ServiceResponse<AttributeValueMsg>.
     * @throws ProduceException
     */
    ServiceResponse<AttributeValueMsg> produceAttributeValue(ServiceRequest<ProduceAttributeValueMsg> rq)
            throws ProduceException;

    /**
     * Missing description at method produceTestConfigElement.
     *
     * @param rq the ServiceRequest<ProduceTestConfigElementMsg>.
     * @return the ServiceResponse<TestConfigElementMsg>.
     * @throws ProduceException
     */
    ServiceResponse<TestConfigElementMsg> produceTestConfigElement(ServiceRequest<ProduceTestConfigElementMsg> rq)
            throws ProduceException;

    /**
     * Missing description at method produceTestConfigElementClone.
     *
     * @param rq the ServiceRequest<TestConfigElementMsg>.
     * @return the ServiceResponse<TestConfigElementMsg>.
     * @throws ProduceException
     */
    ServiceResponse<TestConfigElementMsg> produceTestConfigElementClone(ServiceRequest<TestConfigElementMsg> rq)
            throws ProduceException;

    /**
     * Missing description at method produceDependency.
     *
     * @param rq the ServiceRequest<TestConfigElementMsg>.
     * @return the ServiceResponse<DependencyMsg>.
     * @throws ProduceException
     */
    ServiceResponse<DependencyMsg> produceDependency(ServiceRequest<TestConfigElementMsg> rq) throws ProduceException;

    /**
     * Missing description at method produceTestConfigElementContainer.
     *
     * @param rq the ServiceRequest<TestConfigElementMsg>.
     * @return the ServiceResponse<TestConfigElementContainerMsg>.
     * @throws ProduceException
     */
    ServiceResponse<TestConfigElementContainerMsg> produceTestConfigElementContainer(
            ServiceRequest<TestConfigElementMsg> rq) throws ProduceException;

    /**
     * Missing description at method produceTestConfiguration.
     *
     * @param rq the ServiceRequest<EmptyServiceMessage>.
     * @return the ServiceResponse<TestConfigurationMsg>.
     * @throws ProduceException
     */
    ServiceResponse<TestConfigurationMsg> produceTestConfiguration(ServiceRequest<EmptyServiceMessage> rq)
            throws ProduceException;

    /**
     * Missing description at method produceTestConfigurationClone.
     *
     * @param rq the ServiceRequest<TestConfigurationMsg>.
     * @return the ServiceResponse<TestConfigurationMsg>.
     * @throws ProduceException
     */
    ServiceResponse<TestConfigurationMsg> produceTestConfigurationClone(ServiceRequest<TestConfigurationMsg> rq)
            throws ProduceException;

    /**
     * Missing description at method produceTestScriptContainer.
     *
     * @param rq the ServiceRequest<ProduceTestScriptContainerMsg>.
     * @return the ServiceResponse<ProduceTestScriptContainerMsg>.
     * @throws ProduceException
     */
    ServiceResponse<ProduceTestScriptContainerMsg> produceTestScriptContainer(
            ServiceRequest<ProduceTestScriptContainerMsg> rq) throws ProduceException;
}
