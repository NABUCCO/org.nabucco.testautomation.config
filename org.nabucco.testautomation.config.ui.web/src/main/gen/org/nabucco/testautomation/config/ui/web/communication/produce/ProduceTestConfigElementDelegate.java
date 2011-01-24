/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication.produce;

import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
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
     * @throws ProduceException
     */
    public TestConfigElementMsg produceTestConfigElement(ProduceTestConfigElementMsg rq)
            throws ProduceException {
        ServiceRequest<ProduceTestConfigElementMsg> request = new ServiceRequest<ProduceTestConfigElementMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementMsg> rs;
        if ((service != null)) {
            rs = service.produceTestConfigElement(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceTestConfigElement.produceTestConfigElement");
        }
        return rs.getResponseMessage();
    }

    /**
     * ProduceTestConfigElement.
     *
     * @param subject the Subject.
     * @param rq the ProduceTestConfigElementMsg.
     * @return the TestConfigElementMsg.
     * @throws ProduceException
     */
    public TestConfigElementMsg produceTestConfigElement(ProduceTestConfigElementMsg rq,
            Subject subject) throws ProduceException {
        ServiceRequest<ProduceTestConfigElementMsg> request = new ServiceRequest<ProduceTestConfigElementMsg>(
                super.createServiceContext(subject));
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementMsg> rs;
        if ((service != null)) {
            rs = service.produceTestConfigElement(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceTestConfigElement.produceTestConfigElement");
        }
        return rs.getResponseMessage();
    }

    /**
     * ProduceTestConfigElementClone.
     *
     * @param rq the TestConfigElementMsg.
     * @return the TestConfigElementMsg.
     * @throws ProduceException
     */
    public TestConfigElementMsg produceTestConfigElementClone(TestConfigElementMsg rq)
            throws ProduceException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementMsg> rs;
        if ((service != null)) {
            rs = service.produceTestConfigElementClone(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceTestConfigElement.produceTestConfigElementClone");
        }
        return rs.getResponseMessage();
    }

    /**
     * ProduceTestConfigElementClone.
     *
     * @param subject the Subject.
     * @param rq the TestConfigElementMsg.
     * @return the TestConfigElementMsg.
     * @throws ProduceException
     */
    public TestConfigElementMsg produceTestConfigElementClone(TestConfigElementMsg rq,
            Subject subject) throws ProduceException {
        ServiceRequest<TestConfigElementMsg> request = new ServiceRequest<TestConfigElementMsg>(
                super.createServiceContext(subject));
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigElementMsg> rs;
        if ((service != null)) {
            rs = service.produceTestConfigElementClone(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceTestConfigElement.produceTestConfigElementClone");
        }
        return rs.getResponseMessage();
    }
}
