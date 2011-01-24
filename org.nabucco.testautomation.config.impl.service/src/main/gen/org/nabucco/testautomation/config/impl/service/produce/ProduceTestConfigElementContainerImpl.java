/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.produce;

import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.testautomation.config.facade.message.TestConfigElementContainerMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestConfigElementContainer;

/**
 * ProduceTestConfigElementContainerImpl<p/>Service to produce TestConfigElementContainer<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceTestConfigElementContainerImpl extends ServiceSupport implements
        ProduceTestConfigElementContainer {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceTestConfigElementContainer";

    private ProduceTestConfigElementContainerServiceHandler produceTestConfigElementContainerServiceHandler;

    /** Constructs a new ProduceTestConfigElementContainerImpl instance. */
    public ProduceTestConfigElementContainerImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.produceTestConfigElementContainerServiceHandler = injector
                .inject(ProduceTestConfigElementContainerServiceHandler.getId());
        if ((this.produceTestConfigElementContainerServiceHandler != null)) {
            this.produceTestConfigElementContainerServiceHandler.setEntityManager(null);
            this.produceTestConfigElementContainerServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<TestConfigElementContainerMsg> produceTestConfigElementContainer(
            ServiceRequest<TestConfigElementMsg> rq) throws ProduceException {
        if ((this.produceTestConfigElementContainerServiceHandler == null)) {
            super.getLogger()
                    .error("No service implementation configured for produceTestConfigElementContainer().");
            throw new InjectionException(
                    "No service implementation configured for produceTestConfigElementContainer().");
        }
        ServiceResponse<TestConfigElementContainerMsg> rs;
        this.produceTestConfigElementContainerServiceHandler.init();
        rs = this.produceTestConfigElementContainerServiceHandler.invoke(rq);
        this.produceTestConfigElementContainerServiceHandler.finish();
        return rs;
    }
}
