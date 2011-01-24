/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.service.produce;

import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;

/**
 * ProduceTestConfiguration<p/>Configuration produce service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public interface ProduceTestConfiguration extends Service {

    /**
     * Missing description at method produceTestConfiguration.
     *
     * @param rq the ServiceRequest<EmptyServiceMessage>.
     * @return the ServiceResponse<TestConfigurationMsg>.
     * @throws ProduceException
     */
    ServiceResponse<TestConfigurationMsg> produceTestConfiguration(
            ServiceRequest<EmptyServiceMessage> rq) throws ProduceException;
}
