/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication.produce;

import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
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
     * @throws ProduceException
     */
    public TestConfigElementContainerMsg produceTestConfigElementContainer(TestConfigElementMsg rq)
            throws ProduceException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementContainerMsg> rs;
        if ((service != null)) {
            rs = service.produceTestConfigElementContainer(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceTestConfigElementContainer.produceTestConfigElementContainer");
        }
        return rs.getResponseMessage();
    }

    /**
     * ProduceTestConfigElementContainer.
     *
     * @param subject the Subject.
     * @param rq the TestConfigElementMsg.
     * @return the TestConfigElementContainerMsg.
     * @throws ProduceException
     */
    public TestConfigElementContainerMsg produceTestConfigElementContainer(TestConfigElementMsg rq,
            Subject subject) throws ProduceException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext(subject));
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementContainerMsg> rs;
        if ((service != null)) {
            rs = service.produceTestConfigElementContainer(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceTestConfigElementContainer.produceTestConfigElementContainer");
        }
        return rs.getResponseMessage();
    }
}
