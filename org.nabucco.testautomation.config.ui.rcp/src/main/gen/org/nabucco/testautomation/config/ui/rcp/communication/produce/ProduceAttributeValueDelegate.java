/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.communication.produce;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.plugin.base.logging.NabuccoLogMessage;
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
     * @param rq the ProduceAttributeValueMsg.
     * @return the AttributeValueMsg.
     * @throws ClientException
     */
    public AttributeValueMsg produceAttributeValue(ProduceAttributeValueMsg rq)
            throws ClientException {
        ServiceRequest<ProduceAttributeValueMsg> request = new ServiceRequest<ProduceAttributeValueMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<AttributeValueMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.produceAttributeValue(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(ProduceAttributeValueDelegate.class, "Service: ",
                                "ProduceAttributeValue.produceAttributeValue", " Time: ", String
                                        .valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceAttributeValue.produceAttributeValue");
    }
}
