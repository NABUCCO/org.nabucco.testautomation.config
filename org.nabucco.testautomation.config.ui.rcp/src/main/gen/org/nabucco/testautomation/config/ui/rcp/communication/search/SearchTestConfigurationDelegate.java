/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.communication.search;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.plugin.base.logging.NabuccoLogMessage;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.facade.service.search.SearchTestConfiguration;

/**
 * SearchTestConfigurationDelegate<p/>TestConfiguration search service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class SearchTestConfigurationDelegate extends ServiceDelegateSupport {

    private SearchTestConfiguration service;

    /**
     * Constructs a new SearchTestConfigurationDelegate instance.
     *
     * @param service the SearchTestConfiguration.
     */
    public SearchTestConfigurationDelegate(SearchTestConfiguration service) {
        super();
        this.service = service;
    }

    /**
     * SearchTestConfiguration.
     *
     * @param rq the TestConfigurationSearchMsg.
     * @return the TestConfigurationListMsg.
     * @throws ClientException
     */
    public TestConfigurationListMsg searchTestConfiguration(TestConfigurationSearchMsg rq)
            throws ClientException {
        ServiceRequest<TestConfigurationSearchMsg> request = new ServiceRequest<TestConfigurationSearchMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigurationListMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.searchTestConfiguration(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(SearchTestConfigurationDelegate.class, "Service: ",
                                "SearchTestConfiguration.searchTestConfiguration", " Time: ",
                                String.valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: SearchTestConfiguration.searchTestConfiguration");
    }

    /**
     * Getter for the TestConfiguration.
     *
     * @param rq the TestConfigurationSearchMsg.
     * @return the TestConfigurationListMsg.
     * @throws ClientException
     */
    public TestConfigurationListMsg getTestConfiguration(TestConfigurationSearchMsg rq)
            throws ClientException {
        ServiceRequest<TestConfigurationSearchMsg> request = new ServiceRequest<TestConfigurationSearchMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigurationListMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.getTestConfiguration(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(SearchTestConfigurationDelegate.class, "Service: ",
                                "SearchTestConfiguration.getTestConfiguration", " Time: ", String
                                        .valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: SearchTestConfiguration.getTestConfiguration");
    }
}
