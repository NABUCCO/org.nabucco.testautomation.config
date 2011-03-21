/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication.produce;

import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.testautomation.config.facade.message.AttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.ProduceAttributeValueMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceAttributeValue;

/**
 * ProduceAttributeValueDelegate<p/>Service to produce AttributeValues<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceAttributeValueDelegate extends ServiceDelegateSupport {

    private ProduceAttributeValue service;

    /**
     * Constructs a new ProduceAttributeValueDelegate instance.
     *
     * @param service the ProduceAttributeValue.
     */
    public ProduceAttributeValueDelegate(ProduceAttributeValue service) {
        super();
        this.service = service;
    }

    /**
     * ProduceAttributeValue.
     *
     * @param session the NabuccoSession.
     * @param rq the ProduceAttributeValueMsg.
     * @return the AttributeValueMsg.
     * @throws ProduceException
     */
    public AttributeValueMsg produceAttributeValue(ProduceAttributeValueMsg rq,
            NabuccoSession session) throws ProduceException {
        ServiceRequest<ProduceAttributeValueMsg> request = new ServiceRequest<ProduceAttributeValueMsg>(
                super.createServiceContext(session));
        request.setRequestMessage(rq);
        ServiceResponse<AttributeValueMsg> rs;
        if ((service != null)) {
            rs = service.produceAttributeValue(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceAttributeValue.produceAttributeValue");
        }
        return rs.getResponseMessage();
    }
}
