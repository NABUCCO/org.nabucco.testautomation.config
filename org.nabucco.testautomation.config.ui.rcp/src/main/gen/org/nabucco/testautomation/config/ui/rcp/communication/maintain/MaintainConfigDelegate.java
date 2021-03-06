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
package org.nabucco.testautomation.config.ui.rcp.communication.maintain;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.facade.service.maintain.MaintainConfig;

/**
 * MaintainConfigDelegate<p/>Config maintenance service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class MaintainConfigDelegate extends ServiceDelegateSupport {

    private MaintainConfig service;

    /**
     * Constructs a new MaintainConfigDelegate instance.
     *
     * @param service the MaintainConfig.
     */
    public MaintainConfigDelegate(MaintainConfig service) {
        super();
        this.service = service;
    }

    /**
     * MaintainTestConfiguration.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TestConfigurationMsg.
     * @return the TestConfigurationMsg.
     * @throws ClientException
     */
    public TestConfigurationMsg maintainTestConfiguration(TestConfigurationMsg message,
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
                response = service.maintainTestConfiguration(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainConfig.class, "maintainTestConfiguration", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MaintainConfig.maintainTestConfiguration");
    }
}
