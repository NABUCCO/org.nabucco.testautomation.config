/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.service.produce;

import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.TestConfigElementContainerMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;

/**
 * ProduceTestConfigElementContainer<p/>Service to produce TestConfigElementContainer<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public interface ProduceTestConfigElementContainer extends Service {

    /**
     * Missing description at method produceTestConfigElementContainer.
     *
     * @param rq the ServiceRequest<TestConfigElementMsg>.
     * @return the ServiceResponse<TestConfigElementContainerMsg>.
     * @throws ProduceException
     */
    ServiceResponse<TestConfigElementContainerMsg> produceTestConfigElementContainer(
            ServiceRequest<TestConfigElementMsg> rq) throws ProduceException;
}
