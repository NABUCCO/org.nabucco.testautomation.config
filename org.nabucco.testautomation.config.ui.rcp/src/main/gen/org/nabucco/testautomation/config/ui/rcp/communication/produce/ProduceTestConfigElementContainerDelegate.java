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
import org.nabucco.testautomation.config.facade.message.TestConfigElementContainerMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestConfigElementContainer;

/**
 * ProduceTestConfigElementContainerDelegate<p/>Service to produce TestConfigElementContainer<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceTestConfigElementContainerDelegate extends ServiceDelegateSupport {

    private ProduceTestConfigElementContainer service;

    /**
     * Constructs a new ProduceTestConfigElementContainerDelegate instance.
     *
     * @param service the ProduceTestConfigElementContainer.
     */
    public ProduceTestConfigElementContainerDelegate(ProduceTestConfigElementContainer service) {
        super();
        this.service = service;
    }

    /**
     * ProduceTestConfigElementContainer.
     *
     * @param rq the TestConfigElementMsg.
     * @return the TestConfigElementContainerMsg.
     * @throws ClientException
     */
    public TestConfigElementContainerMsg produceTestConfigElementContainer(TestConfigElementMsg rq)
            throws ClientException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementContainerMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.produceTestConfigElementContainer(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator
                        .getDefault()
                        .logDebug(
                                new NabuccoLogMessage(
                                        ProduceTestConfigElementContainerDelegate.class,
                                        "Service: ",
                                        "ProduceTestConfigElementContainer.produceTestConfigElementContainer",
                                        " Time: ", String.valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceTestConfigElementContainer.produceTestConfigElementContainer");
    }
}
