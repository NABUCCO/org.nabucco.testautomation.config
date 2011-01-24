/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.service.produce;

import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.AttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.ProduceAttributeValueMsg;

/**
 * ProduceAttributeValue<p/>Service to produce AttributeValues<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public interface ProduceAttributeValue extends Service {

    /**
     * Missing description at method produceAttributeValue.
     *
     * @param rq the ServiceRequest<ProduceAttributeValueMsg>.
     * @return the ServiceResponse<AttributeValueMsg>.
     * @throws ProduceException
     */
    ServiceResponse<AttributeValueMsg> produceAttributeValue(
            ServiceRequest<ProduceAttributeValueMsg> rq) throws ProduceException;
}
