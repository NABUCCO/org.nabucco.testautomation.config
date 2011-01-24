/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.service.produce;

import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.ProduceTestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;

/**
 * ProduceTestConfigElement<p/>Service to produce and clone TestConfigElements<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public interface ProduceTestConfigElement extends Service {

    /**
     * Missing description at method produceTestConfigElement.
     *
     * @param rq the ServiceRequest<ProduceTestConfigElementMsg>.
     * @return the ServiceResponse<TestConfigElementMsg>.
     * @throws ProduceException
     */
    ServiceResponse<TestConfigElementMsg> produceTestConfigElement(
            ServiceRequest<ProduceTestConfigElementMsg> rq) throws ProduceException;

    /**
     * Missing description at method produceTestConfigElementClone.
     *
     * @param rq the ServiceRequest<TestConfigElementMsg>.
     * @return the ServiceResponse<TestConfigElementMsg>.
     * @throws ProduceException
     */
    ServiceResponse<TestConfigElementMsg> produceTestConfigElementClone(
            ServiceRequest<TestConfigElementMsg> rq) throws ProduceException;
}
