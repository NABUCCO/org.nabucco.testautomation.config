/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.service.produce;

import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.ProduceTestScriptContainerMsg;

/**
 * ProduceTestScriptContainer<p/>Service to produce TestScriptContainer<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public interface ProduceTestScriptContainer extends Service {

    /**
     * Missing description at method produceTestScriptContainer.
     *
     * @param rq the ServiceRequest<ProduceTestScriptContainerMsg>.
     * @return the ServiceResponse<ProduceTestScriptContainerMsg>.
     * @throws ProduceException
     */
    ServiceResponse<ProduceTestScriptContainerMsg> produceTestScriptContainer(
            ServiceRequest<ProduceTestScriptContainerMsg> rq) throws ProduceException;
}