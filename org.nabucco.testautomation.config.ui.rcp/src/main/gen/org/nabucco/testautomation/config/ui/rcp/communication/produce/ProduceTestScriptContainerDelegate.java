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
import org.nabucco.testautomation.config.facade.message.ProduceTestScriptContainerMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestScriptContainer;

/**
 * ProduceTestScriptContainerDelegate<p/>Service to produce TestScriptContainer<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceTestScriptContainerDelegate extends ServiceDelegateSupport {

    private ProduceTestScriptContainer service;

    /**
     * Constructs a new ProduceTestScriptContainerDelegate instance.
     *
     * @param service the ProduceTestScriptContainer.
     */
    public ProduceTestScriptContainerDelegate(ProduceTestScriptContainer service) {
        super();
        this.service = service;
    }

    /**
     * ProduceTestScriptContainer.
     *
     * @param rq the ProduceTestScriptContainerMsg.
     * @return the ProduceTestScriptContainerMsg.
     * @throws ClientException
     */
    public ProduceTestScriptContainerMsg produceTestScriptContainer(ProduceTestScriptContainerMsg rq)
            throws ClientException {
        ServiceRequest<ProduceTestScriptContainerMsg> request = new ServiceRequest<ProduceTestScriptContainerMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<ProduceTestScriptContainerMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.produceTestScriptContainer(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(ProduceTestScriptContainerDelegate.class,
                                "Service: ",
                                "ProduceTestScriptContainer.produceTestScriptContainer", " Time: ",
                                String.valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceTestScriptContainer.produceTestScriptContainer");
    }
}
