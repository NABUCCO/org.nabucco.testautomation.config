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
import org.nabucco.testautomation.config.facade.message.DependencyMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceDependency;

/**
 * ProduceDependencyDelegate<p/>Service to produce Dependency<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceDependencyDelegate extends ServiceDelegateSupport {

    private ProduceDependency service;

    /**
     * Constructs a new ProduceDependencyDelegate instance.
     *
     * @param service the ProduceDependency.
     */
    public ProduceDependencyDelegate(ProduceDependency service) {
        super();
        this.service = service;
    }

    /**
     * ProduceDependency.
     *
     * @param rq the TestConfigElementMsg.
     * @return the DependencyMsg.
     * @throws ClientException
     */
    public DependencyMsg produceDependency(TestConfigElementMsg rq) throws ClientException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<DependencyMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.produceDependency(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(ProduceDependencyDelegate.class, "Service: ",
                                "ProduceDependency.produceDependency", " Time: ", String
                                        .valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceDependency.produceDependency");
    }
}
