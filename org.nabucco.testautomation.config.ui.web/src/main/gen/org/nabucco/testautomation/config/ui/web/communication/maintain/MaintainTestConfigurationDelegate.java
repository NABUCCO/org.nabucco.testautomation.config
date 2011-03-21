/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication.maintain;

import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
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
     * @param session the NabuccoSession.
     * @param rq the TestConfigurationMsg.
     * @return the TestConfigurationMsg.
     * @throws MaintainException
     */
    public TestConfigurationMsg maintainTestConfiguration(TestConfigurationMsg rq,
            NabuccoSession session) throws MaintainException {
        ServiceRequest<TestConfigurationMsg> request = new ServiceRequest<TestConfigurationMsg>(
                super.createServiceContext(session));
        request.setRequestMessage(rq);
        ServiceResponse<TestConfigurationMsg> rs;
        if ((service != null)) {
            rs = service.maintainTestConfiguration(request);
        } else {
            throw new MaintainException(
                    "Cannot execute service operation: MaintainTestConfiguration.maintainTestConfiguration");
        }
        return rs.getResponseMessage();
    }
}
