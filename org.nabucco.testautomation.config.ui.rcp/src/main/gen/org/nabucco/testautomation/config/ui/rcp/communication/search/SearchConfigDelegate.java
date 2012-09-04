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
package org.nabucco.testautomation.config.ui.rcp.communication.search;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.testautomation.config.facade.message.TestConfigElementListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.facade.service.search.SearchConfig;
import org.nabucco.testautomation.property.facade.message.QuickSearchMsg;
import org.nabucco.testautomation.property.facade.message.QuickSearchRs;

/**
 * SearchConfigDelegate<p/>Config search service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class SearchConfigDelegate extends ServiceDelegateSupport {

    private SearchConfig service;

    /**
     * Constructs a new SearchConfigDelegate instance.
     *
     * @param service the SearchConfig.
     */
    public SearchConfigDelegate(SearchConfig service) {
        super();
        this.service = service;
    }

    /**
     * SearchTestConfiguration.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TestConfigurationSearchMsg.
     * @return the TestConfigurationListMsg.
     * @throws ClientException
     */
    public TestConfigurationListMsg searchTestConfiguration(TestConfigurationSearchMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<TestConfigurationSearchMsg> request = new ServiceRequest<TestConfigurationSearchMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestConfigurationListMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchTestConfiguration(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchConfig.class, "searchTestConfiguration", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: SearchConfig.searchTestConfiguration");
    }

    /**
     * SearchTestConfigElement.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TestConfigElementSearchMsg.
     * @return the TestConfigElementListMsg.
     * @throws ClientException
     */
    public TestConfigElementListMsg searchTestConfigElement(TestConfigElementSearchMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<TestConfigElementSearchMsg> request = new ServiceRequest<TestConfigElementSearchMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestConfigElementListMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchTestConfigElement(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchConfig.class, "searchTestConfigElement", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: SearchConfig.searchTestConfigElement");
    }

    /**
     * SearchReferencingTestConfigurations.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TestConfigElementMsg.
     * @return the TestConfigurationListMsg.
     * @throws ClientException
     */
    public TestConfigurationListMsg searchReferencingTestConfigurations(TestConfigElementMsg message,
            ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TestConfigurationListMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchReferencingTestConfigurations(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchConfig.class, "searchReferencingTestConfigurations", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: SearchConfig.searchReferencingTestConfigurations");
    }

    /**
     * QuickSearch.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the QuickSearchMsg.
     * @return the QuickSearchRs.
     * @throws ClientException
     */
    public QuickSearchRs quickSearch(QuickSearchMsg message, ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<QuickSearchMsg> request = new ServiceRequest<QuickSearchMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<QuickSearchRs> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.quickSearch(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchConfig.class, "quickSearch", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: SearchConfig.quickSearch");
    }
}
