/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication.produce;

import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestConfiguration;

/**
 * ProduceTestConfigurationDelegate<p/>Configuration produce service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceTestConfigurationDelegate extends ServiceDelegateSupport {

    private ProduceTestConfiguration service;

    /**
     * Constructs a new ProduceTestConfigurationDelegate instance.
     *
     * @param service the ProduceTestConfiguration.
     */
    public ProduceTestConfigurationDelegate(ProduceTestConfiguration service) {
        super();
        this.service = service;
    }

    /**
     * ProduceTestConfiguration.
     *
     * @param rq the EmptyServiceMessage.
     * @return the TestConfigurationMsg.
     * @throws ProduceException
     */
    public TestConfigurationMsg produceTestConfiguration(EmptyServiceMessage rq)
            throws ProduceException {
        ServiceRequest<EmptyServiceMessage> request = new ServiceRequest<EmptyServiceMessage>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigurationMsg> rs;
        if ((service != null)) {
            rs = service.produceTestConfiguration(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceTestConfiguration.produceTestConfiguration");
        }
        return rs.getResponseMessage();
    }

    /**
     * ProduceTestConfiguration.
     *
     * @param subject the Subject.
     * @param rq the EmptyServiceMessage.
     * @return the TestConfigurationMsg.
     * @throws ProduceException
     */
    public TestConfigurationMsg produceTestConfiguration(EmptyServiceMessage rq, Subject subject)
            throws ProduceException {
        ServiceRequest<EmptyServiceMessage> request = new ServiceRequest<EmptyServiceMessage>(
                super.createServiceContext(subject));
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigurationMsg> rs;
        if ((service != null)) {
            rs = service.produceTestConfiguration(request);
        } else {
            throw new ProduceException(
                    "Cannot execute service operation: ProduceTestConfiguration.produceTestConfiguration");
        }
        return rs.getResponseMessage();
    }
}
