/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication.produce;

import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
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
     * @throws ProduceException
     */
    public ProduceTestScriptContainerMsg produceTestScriptContainer(ProduceTestScriptContainerMsg rq)
            throws ProduceException {
        ServiceRequest<ProduceTestScriptContainerMsg> request = new ServiceRequest<ProduceTestScriptContainerMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<ProduceTestScriptContainerMsg> rs;
        if ((service != null)) {
            rs = service.produceTestScriptContainer(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceTestScriptContainer.produceTestScriptContainer");
        }
        return rs.getResponseMessage();
    }

    /**
     * ProduceTestScriptContainer.
     *
     * @param subject the Subject.
     * @param rq the ProduceTestScriptContainerMsg.
     * @return the ProduceTestScriptContainerMsg.
     * @throws ProduceException
     */
    public ProduceTestScriptContainerMsg produceTestScriptContainer(
            ProduceTestScriptContainerMsg rq, Subject subject) throws ProduceException {
        ServiceRequest<ProduceTestScriptContainerMsg> request = new ServiceRequest<ProduceTestScriptContainerMsg>(
                super.createServiceContext(subject));
        request.setRequestMessage(rq);
        ServiceResponse<ProduceTestScriptContainerMsg> rs;
        if ((service != null)) {
            rs = service.produceTestScriptContainer(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceTestScriptContainer.produceTestScriptContainer");
        }
        return rs.getResponseMessage();
    }
}
