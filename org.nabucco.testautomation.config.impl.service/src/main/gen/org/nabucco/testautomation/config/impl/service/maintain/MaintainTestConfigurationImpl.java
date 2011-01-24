/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.maintain;

import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.facade.service.maintain.MaintainTestConfiguration;

/**
 * MaintainTestConfigurationImpl<p/>TestConfiguration maintenance service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class MaintainTestConfigurationImpl extends ServiceSupport implements
        MaintainTestConfiguration {

    private static final long serialVersionUID = 1L;

    private static final String ID = "MaintainTestConfiguration";

    private EntityManager em;

    private MaintainTestConfigurationServiceHandler maintainTestConfigurationServiceHandler;

    /** Constructs a new MaintainTestConfigurationImpl instance. */
    public MaintainTestConfigurationImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.maintainTestConfigurationServiceHandler = injector
                .inject(MaintainTestConfigurationServiceHandler.getId());
        if ((this.maintainTestConfigurationServiceHandler != null)) {
            this.maintainTestConfigurationServiceHandler.setEntityManager(this.em);
            this.maintainTestConfigurationServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<TestConfigurationMsg> maintainTestConfiguration(
            ServiceRequest<TestConfigurationMsg> rq) throws MaintainException {
        if ((this.maintainTestConfigurationServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for maintainTestConfiguration().");
            throw new InjectionException(
                    "No service implementation configured for maintainTestConfiguration().");
        }
        ServiceResponse<TestConfigurationMsg> rs;
        this.maintainTestConfigurationServiceHandler.init();
        rs = this.maintainTestConfigurationServiceHandler.invoke(rq);
        this.maintainTestConfigurationServiceHandler.finish();
        return rs;
    }
}
