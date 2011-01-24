/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.communication.maintain;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.plugin.base.logging.NabuccoLogMessage;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.facade.service.maintain.MaintainTestConfiguration;

/**
 * MaintainTestConfigurationDelegate<p/>TestConfiguration maintenance service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class MaintainTestConfigurationDelegate extends ServiceDelegateSupport {

    private MaintainTestConfiguration service;

    /**
     * Constructs a new MaintainTestConfigurationDelegate instance.
     *
     * @param service the MaintainTestConfiguration.
     */
    public MaintainTestConfigurationDelegate(MaintainTestConfiguration service) {
        super();
        this.service = service;
    }

    /**
     * MaintainTestConfiguration.
     *
     * @param rq the TestConfigurationMsg.
     * @return the TestConfigurationMsg.
     * @throws ClientException
     */
    public TestConfigurationMsg maintainTestConfiguration(TestConfigurationMsg rq)
            throws ClientException {
        ServiceRequest<TestConfigurationMsg> request = new ServiceRequest<TestConfigurationMsg>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigurationMsg> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.maintainTestConfiguration(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(MaintainTestConfigurationDelegate.class, "Service: ",
                                "MaintainTestConfiguration.maintainTestConfiguration", " Time: ",
                                String.valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException(
                "Cannot execute service operation: MaintainTestConfiguration.maintainTestConfiguration");
    }
}
