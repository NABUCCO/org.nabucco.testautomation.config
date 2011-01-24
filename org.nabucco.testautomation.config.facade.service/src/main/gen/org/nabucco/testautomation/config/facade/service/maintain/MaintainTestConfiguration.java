/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.service.maintain;

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;

/**
 * MaintainTestConfiguration<p/>TestConfiguration maintenance service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public interface MaintainTestConfiguration extends Service {

    /**
     * Missing description at method maintainTestConfiguration.
     *
     * @param rq the ServiceRequest<TestConfigurationMsg>.
     * @return the ServiceResponse<TestConfigurationMsg>.
     * @throws MaintainException
     */
    ServiceResponse<TestConfigurationMsg> maintainTestConfiguration(
            ServiceRequest<TestConfigurationMsg> rq) throws MaintainException;
}
