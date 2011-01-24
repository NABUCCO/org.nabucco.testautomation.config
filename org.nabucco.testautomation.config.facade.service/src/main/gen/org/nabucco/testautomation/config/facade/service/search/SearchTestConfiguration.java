/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.service.search;

import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;

/**
 * SearchTestConfiguration<p/>TestConfiguration search service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public interface SearchTestConfiguration extends Service {

    /**
     * Missing description at method searchTestConfiguration.
     *
     * @param rq the ServiceRequest<TestConfigurationSearchMsg>.
     * @return the ServiceResponse<TestConfigurationListMsg>.
     * @throws SearchException
     */
    ServiceResponse<TestConfigurationListMsg> searchTestConfiguration(
            ServiceRequest<TestConfigurationSearchMsg> rq) throws SearchException;

    /**
     * Missing description at method getTestConfiguration.
     *
     * @param rq the ServiceRequest<TestConfigurationSearchMsg>.
     * @return the ServiceResponse<TestConfigurationListMsg>.
     * @throws SearchException
     */
    ServiceResponse<TestConfigurationListMsg> getTestConfiguration(
            ServiceRequest<TestConfigurationSearchMsg> rq) throws SearchException;
}
