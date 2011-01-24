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
import org.nabucco.testautomation.config.facade.message.ProduceTestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestConfigElement;

/**
 * ProduceTestConfigElementDelegate<p/>Service to produce and clone TestConfigElements<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceTestConfigElementDelegate extends ServiceDelegateSupport {

    private ProduceTestConfigElement service;

    /**
     * Constructs a new ProduceTestConfigElementDelegate instance.
     *
     * @param service the ProduceTestConfigElement.
     */
    public ProduceTestConfigElementDelegate(ProduceTestConfigElement service) {
        super();
        this.service = service;
    }

    /**
     * ProduceTestConfigElement.
     *
     * @param rq the ProduceTestConfigElementMsg.
     * @return the TestConfigElementMsg.
     * @throws ClientException
     */
    public TestConfigElementMsg produceTestConfigElement(ProduceTestConfigElementMsg rq)
            throws ClientException {
        ServiceRequest<ProduceTestConfigElementMsg> request = new ServiceRequest<ProduceTestConfigElementMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.produceTestConfigElement(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(ProduceTestConfigElementDelegate.class, "Service: ",
                                "ProduceTestConfigElement.produceTestConfigElement", " Time: ",
                                String.valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceTestConfigElement.produceTestConfigElement");
    }

    /**
     * ProduceTestConfigElementClone.
     *
     * @param rq the TestConfigElementMsg.
     * @return the TestConfigElementMsg.
     * @throws ClientException
     */
    public TestConfigElementMsg produceTestConfigElementClone(TestConfigElementMsg rq)
            throws ClientException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.produceTestConfigElementClone(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(ProduceTestConfigElementDelegate.class, "Service: ",
                                "ProduceTestConfigElement.produceTestConfigElementClone",
                                " Time: ", String.valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceTestConfigElement.produceTestConfigElementClone");
    }
}
