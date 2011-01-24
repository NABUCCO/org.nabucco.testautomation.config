/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.communication.produce;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.plugin.base.logging.NabuccoLogMessage;
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
     * @throws ClientException
     */
    public TestConfigurationMsg produceTestConfiguration(EmptyServiceMessage rq)
            throws ClientException {
        ServiceRequest<EmptyServiceMessage> request = new ServiceRequest<EmptyServiceMessage>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigurationMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.produceTestConfiguration(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(ProduceTestConfigurationDelegate.class, "Service: ",
                                "ProduceTestConfiguration.produceTestConfiguration", " Time: ",
                                String.valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceTestConfiguration.produceTestConfiguration");
    }
}
