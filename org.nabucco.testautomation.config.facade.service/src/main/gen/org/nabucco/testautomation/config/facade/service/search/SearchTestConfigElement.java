/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.service.search;

import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.TestConfigElementListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;

/**
 * SearchTestConfigElement<p/>TestConfigElement search service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public interface SearchTestConfigElement extends Service {

    /**
     * Missing description at method searchTestConfigElement.
     *
     * @param rq the ServiceRequest<TestConfigElementSearchMsg>.
     * @return the ServiceResponse<TestConfigElementListMsg>.
     * @throws SearchException
     */
    ServiceResponse<TestConfigElementListMsg> searchTestConfigElement(
            ServiceRequest<TestConfigElementSearchMsg> rq) throws SearchException;

    /**
     * Missing description at method getTestConfigElement.
     *
     * @param rq the ServiceRequest<TestConfigElementSearchMsg>.
     * @return the ServiceResponse<TestConfigElementMsg>.
     * @throws SearchException
     */
    ServiceResponse<TestConfigElementMsg> getTestConfigElement(
            ServiceRequest<TestConfigElementSearchMsg> rq) throws SearchException;
}
