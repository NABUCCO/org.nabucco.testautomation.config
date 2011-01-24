/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication.search;

import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
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
     * @throws SearchException
     */
    public TestConfigElementListMsg searchTestConfigElement(TestConfigElementSearchMsg rq)
            throws SearchException {
        ServiceRequest<TestConfigElementSearchMsg> request = new ServiceRequest<TestConfigElementSearchMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementListMsg> rs;
        if ((service != null)) {
            rs = service.searchTestConfigElement(request);
        } else {
            throw new SearchException(
                    "Cannot execute service operation: SearchTestConfigElement.searchTestConfigElement");
        }
        return rs.getResponseMessage();
    }

    /**
     * SearchTestConfigElement.
     *
     * @param subject the Subject.
     * @param rq the TestConfigElementSearchMsg.
     * @return the TestConfigElementListMsg.
     * @throws SearchException
     */
    public TestConfigElementListMsg searchTestConfigElement(TestConfigElementSearchMsg rq,
            Subject subject) throws SearchException {
        ServiceRequest<TestConfigElementSearchMsg> request = new ServiceRequest<TestConfigElementSearchMsg>(
                super.createServiceContext(subject));
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementListMsg> rs;
        if ((service != null)) {
            rs = service.searchTestConfigElement(request);
        } else {
            throw new SearchException(
                    "Cannot execute service operation: SearchTestConfigElement.searchTestConfigElement");
        }
        return rs.getResponseMessage();
    }

    /**
     * Getter for the TestConfigElement.
     *
     * @param rq the TestConfigElementSearchMsg.
     * @return the TestConfigElementMsg.
     * @throws SearchException
     */
    public TestConfigElementMsg getTestConfigElement(TestConfigElementSearchMsg rq)
            throws SearchException {
        ServiceRequest<TestConfigElementSearchMsg> request = new ServiceRequest<TestConfigElementSearchMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementMsg> rs;
        if ((service != null)) {
            rs = service.getTestConfigElement(request);
        } else {
            throw new SearchException(
                    "Cannot execute service operation: SearchTestConfigElement.getTestConfigElement");
        }
        return rs.getResponseMessage();
    }

    /**
     * Getter for the TestConfigElement.
     *
     * @param subject the Subject.
     * @param rq the TestConfigElementSearchMsg.
     * @return the TestConfigElementMsg.
     * @throws SearchException
     */
    public TestConfigElementMsg getTestConfigElement(TestConfigElementSearchMsg rq, Subject subject)
            throws SearchException {
        ServiceRequest<TestConfigElementSearchMsg> request = new ServiceRequest<TestConfigElementSearchMsg>(
                super.createServiceContext(subject));
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementMsg> rs;
        if ((service != null)) {
            rs = service.getTestConfigElement(request);
        } else {
            throw new SearchException(
                    "Cannot execute service operation: SearchTestConfigElement.getTestConfigElement");
        }
        return rs.getResponseMessage();
    }
}
