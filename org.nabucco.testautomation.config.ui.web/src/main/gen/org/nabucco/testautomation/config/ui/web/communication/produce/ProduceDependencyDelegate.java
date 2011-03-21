/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication.produce;

import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
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
     * @param session the NabuccoSession.
     * @param rq the TestConfigElementMsg.
     * @return the DependencyMsg.
     * @throws ProduceException
     */
    public DependencyMsg produceDependency(TestConfigElementMsg rq, NabuccoSession session)
            throws ProduceException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext(session));
        request.setRequestMessage(rq);
        ServiceResponse<DependencyMsg> rs;
        if ((service != null)) {
            rs = service.produceDependency(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceDependency.produceDependency");
        }
        return rs.getResponseMessage();
    }
}
