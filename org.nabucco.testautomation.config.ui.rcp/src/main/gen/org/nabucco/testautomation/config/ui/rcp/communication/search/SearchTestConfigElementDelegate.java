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
import org.nabucco.testautomation.config.facade.message.TestConfigElementListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.facade.service.search.SearchTestConfigElement;

/**
 * SearchTestConfigElementDelegate<p/>TestConfigElement search service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class SearchTestConfigElementDelegate extends ServiceDelegateSupport {

    private SearchTestConfigElement service;

    /**
     * Constructs a new SearchTestConfigElementDelegate instance.
     *
     * @param service the SearchTestConfigElement.
     */
    public SearchTestConfigElementDelegate(SearchTestConfigElement service) {
        super();
        this.service = service;
    }

    /**
     * SearchTestConfigElement.
     *
     * @param rq the TestConfigElementSearchMsg.
     * @return the TestConfigElementListMsg.
     * @throws ClientException
     */
    public TestConfigElementListMsg searchTestConfigElement(TestConfigElementSearchMsg rq)
            throws ClientException {
        ServiceRequest<TestConfigElementSearchMsg> request = new ServiceRequest<TestConfigElementSearchMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementListMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.searchTestConfigElement(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(SearchTestConfigElementDelegate.class, "Service: ",
                                "SearchTestConfigElement.searchTestConfigElement", " Time: ",
                                String.valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: SearchTestConfigElement.searchTestConfigElement");
    }

    /**
     * Getter for the TestConfigElement.
     *
     * @param rq the TestConfigElementSearchMsg.
     * @return the TestConfigElementMsg.
     * @throws ClientException
     */
    public TestConfigElementMsg getTestConfigElement(TestConfigElementSearchMsg rq)
            throws ClientException {
        ServiceRequest<TestConfigElementSearchMsg> request = new ServiceRequest<TestConfigElementSearchMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.getTestConfigElement(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(SearchTestConfigElementDelegate.class, "Service: ",
                                "SearchTestConfigElement.getTestConfigElement", " Time: ", String
                                        .valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: SearchTestConfigElement.getTestConfigElement");
    }
}
