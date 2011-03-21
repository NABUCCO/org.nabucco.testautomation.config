/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.produce;

import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestConfiguration;

/**
 * ProduceTestConfigurationImpl<p/>Configuration produce service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceTestConfigurationImpl extends ServiceSupport implements
        ProduceTestConfiguration {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceTestConfiguration";

    private ProduceTestConfigurationServiceHandler produceTestConfigurationServiceHandler;

    private ProduceTestConfigurationCloneServiceHandler produceTestConfigurationCloneServiceHandler;

    /** Constructs a new ProduceTestConfigurationImpl instance. */
    public ProduceTestConfigurationImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.produceTestConfigurationServiceHandler = injector
                .inject(ProduceTestConfigurationServiceHandler.getId());
        if ((this.produceTestConfigurationServiceHandler != null)) {
            this.produceTestConfigurationServiceHandler.setEntityManager(null);
            this.produceTestConfigurationServiceHandler.setLogger(super.getLogger());
        }
        this.produceTestConfigurationCloneServiceHandler = injector
                .inject(ProduceTestConfigurationCloneServiceHandler.getId());
        if ((this.produceTestConfigurationCloneServiceHandler != null)) {
            this.produceTestConfigurationCloneServiceHandler.setEntityManager(null);
            this.produceTestConfigurationCloneServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<TestConfigurationMsg> produceTestConfiguration(
            ServiceRequest<EmptyServiceMessage> rq) throws ProduceException {
        if ((this.produceTestConfigurationServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for produceTestConfiguration().");
            throw new InjectionException(
                    "No service implementation configured for produceTestConfiguration().");
        }
        ServiceResponse<TestConfigurationMsg> rs;
        this.produceTestConfigurationServiceHandler.init();
        rs = this.produceTestConfigurationServiceHandler.invoke(rq);
        this.produceTestConfigurationServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigurationMsg> produceTestConfigurationClone(
            ServiceRequest<TestConfigurationMsg> rq) throws ProduceException {
        if ((this.produceTestConfigurationCloneServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for produceTestConfigurationClone().");
            throw new InjectionException(
                    "No service implementation configured for produceTestConfigurationClone().");
        }
        ServiceResponse<TestConfigurationMsg> rs;
        this.produceTestConfigurationCloneServiceHandler.init();
        rs = this.produceTestConfigurationCloneServiceHandler.invoke(rq);
        this.produceTestConfigurationCloneServiceHandler.finish();
        return rs;
    }
}
