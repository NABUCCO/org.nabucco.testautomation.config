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
package org.nabucco.testautomation.config.ui.rcp.communication.produce;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.testautomation.config.facade.message.AttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.DependencyMsg;
import org.nabucco.testautomation.config.facade.message.ProduceAttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.ProduceTestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.ProduceTestScriptContainerMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementContainerMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceConfig;

/**
 * ProduceConfigDelegate<p/>Service to produce and clone TestConfigElements<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceConfigDelegate extends ServiceDelegateSupport {

    private ProduceConfig service;

    /**
     * Constructs a new ProduceConfigDelegate instance.
     *
     * @param service the ProduceConfig.
     */
    public ProduceConfigDelegate(ProduceConfig service) {
        super();
        this.service = service;
    }

    /**
     * ProduceAttributeValue.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ProduceAttributeValueMsg.
     * @return the AttributeValueMsg.
     * @throws ClientException
     */
    public AttributeValueMsg produceAttributeValue(ProduceAttributeValueMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<ProduceAttributeValueMsg> request = new ServiceRequest<ProduceAttributeValueMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<AttributeValueMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceAttributeValue(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceConfig.class, "produceAttributeValue", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceConfig.produceAttributeValue");
    }

    /**
     * ProduceTestConfigElement.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ProduceTestConfigElementMsg.
     * @return the TestConfigElementMsg.
     * @throws ClientException
     */
    public TestConfigElementMsg produceTestConfigElement(ProduceTestConfigElementMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<ProduceTestConfigElementMsg> request = new ServiceRequest<ProduceTestConfigElementMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestConfigElementMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceTestConfigElement(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceConfig.class, "produceTestConfigElement", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceConfig.produceTestConfigElement");
    }

    /**
     * ProduceTestConfigElementClone.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TestConfigElementMsg.
     * @return the TestConfigElementMsg.
     * @throws ClientException
     */
    public TestConfigElementMsg produceTestConfigElementClone(TestConfigElementMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestConfigElementMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceTestConfigElementClone(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceConfig.class, "produceTestConfigElementClone", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceConfig.produceTestConfigElementClone");
    }

    /**
     * ProduceDependency.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TestConfigElementMsg.
     * @return the DependencyMsg.
     * @throws ClientException
     */
    public DependencyMsg produceDependency(TestConfigElementMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<DependencyMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceDependency(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceConfig.class, "produceDependency", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceConfig.produceDependency");
    }

    /**
     * ProduceTestConfigElementContainer.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TestConfigElementMsg.
     * @return the TestConfigElementContainerMsg.
     * @throws ClientException
     */
    public TestConfigElementContainerMsg produceTestConfigElementContainer(TestConfigElementMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestConfigElementContainerMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceTestConfigElementContainer(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceConfig.class, "produceTestConfigElementContainer", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceConfig.produceTestConfigElementContainer");
    }

    /**
     * ProduceTestConfiguration.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the EmptyServiceMessage.
     * @return the TestConfigurationMsg.
     * @throws ClientException
     */
    public TestConfigurationMsg produceTestConfiguration(EmptyServiceMessage message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<EmptyServiceMessage> request = new ServiceRequest<EmptyServiceMessage>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestConfigurationMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceTestConfiguration(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceConfig.class, "produceTestConfiguration", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceConfig.produceTestConfiguration");
    }

    /**
     * ProduceTestConfigurationClone.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TestConfigurationMsg.
     * @return the TestConfigurationMsg.
     * @throws ClientException
     */
    public TestConfigurationMsg produceTestConfigurationClone(TestConfigurationMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<TestConfigurationMsg> request = new ServiceRequest<TestConfigurationMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestConfigurationMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceTestConfigurationClone(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceConfig.class, "produceTestConfigurationClone", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceConfig.produceTestConfigurationClone");
    }

    /**
     * ProduceTestScriptContainer.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ProduceTestScriptContainerMsg.
     * @return the ProduceTestScriptContainerMsg.
     * @throws ClientException
     */
    public ProduceTestScriptContainerMsg produceTestScriptContainer(ProduceTestScriptContainerMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<ProduceTestScriptContainerMsg> request = new ServiceRequest<ProduceTestScriptContainerMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ProduceTestScriptContainerMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceTestScriptContainer(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceConfig.class, "produceTestScriptContainer", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ProduceConfig.produceTestScriptContainer");
    }
}
