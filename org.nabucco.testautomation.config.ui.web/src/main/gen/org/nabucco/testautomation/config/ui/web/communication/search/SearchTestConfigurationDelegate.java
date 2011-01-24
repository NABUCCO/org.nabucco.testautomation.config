/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication.search;

import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
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
     * @throws SearchException
     */
    public TestConfigurationListMsg searchTestConfiguration(TestConfigurationSearchMsg rq)
            throws SearchException {
        ServiceRequest<TestConfigurationSearchMsg> request = new ServiceRequest<TestConfigurationSearchMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigurationListMsg> rs;
        if ((service != null)) {
            rs = service.searchTestConfiguration(request);
        } else {
            throw new SearchException(
                    "Cannot execute service operation: SearchTestConfiguration.searchTestConfiguration");
        }
        return rs.getResponseMessage();
    }

    /**
     * SearchTestConfiguration.
     *
     * @param subject the Subject.
     * @param rq the TestConfigurationSearchMsg.
     * @return the TestConfigurationListMsg.
     * @throws SearchException
     */
    public TestConfigurationListMsg searchTestConfiguration(TestConfigurationSearchMsg rq,
            Subject subject) throws SearchException {
        ServiceRequest<TestConfigurationSearchMsg> request = new ServiceRequest<TestConfigurationSearchMsg>(
                super.createServiceContext(subject));
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigurationListMsg> rs;
        if ((service != null)) {
            rs = service.searchTestConfiguration(request);
        } else {
            throw new SearchException(
                    "Cannot execute service operation: SearchTestConfiguration.searchTestConfiguration");
        }
        return rs.getResponseMessage();
    }

    /**
     * Getter for the TestConfiguration.
     *
     * @param rq the TestConfigurationSearchMsg.
     * @return the TestConfigurationListMsg.
     * @throws SearchException
     */
    public TestConfigurationListMsg getTestConfiguration(TestConfigurationSearchMsg rq)
            throws SearchException {
        ServiceRequest<TestConfigurationSearchMsg> request = new ServiceRequest<TestConfigurationSearchMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigurationListMsg> rs;
        if ((service != null)) {
            rs = service.getTestConfiguration(request);
        } else {
            throw new SearchException(
                    "Cannot execute service operation: SearchTestConfiguration.getTestConfiguration");
        }
        return rs.getResponseMessage();
    }

    /**
     * Getter for the TestConfiguration.
     *
     * @param subject the Subject.
     * @param rq the TestConfigurationSearchMsg.
     * @return the TestConfigurationListMsg.
     * @throws SearchException
     */
    public TestConfigurationListMsg getTestConfiguration(TestConfigurationSearchMsg rq,
            Subject subject) throws SearchException {
        ServiceRequest<TestConfigurationSearchMsg> request = new ServiceRequest<TestConfigurationSearchMsg>(
                super.createServiceContext(subject));
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigurationListMsg> rs;
        if ((service != null)) {
            rs = service.getTestConfiguration(request);
        } else {
            throw new SearchException(
                    "Cannot execute service operation: SearchTestConfiguration.getTestConfiguration");
        }
        return rs.getResponseMessage();
    }
}
