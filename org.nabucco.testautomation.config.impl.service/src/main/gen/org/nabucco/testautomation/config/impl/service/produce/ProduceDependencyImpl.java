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
import org.nabucco.testautomation.config.facade.message.DependencyMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceDependency;

/**
 * ProduceDependencyImpl<p/>Service to produce Dependency<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceDependencyImpl extends ServiceSupport implements ProduceDependency {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceDependency";

    private ProduceDependencyServiceHandler produceDependencyServiceHandler;

    /** Constructs a new ProduceDependencyImpl instance. */
    public ProduceDependencyImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.produceDependencyServiceHandler = injector.inject(ProduceDependencyServiceHandler
                .getId());
        if ((this.produceDependencyServiceHandler != null)) {
            this.produceDependencyServiceHandler.setEntityManager(null);
            this.produceDependencyServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<DependencyMsg> produceDependency(ServiceRequest<TestConfigElementMsg> rq)
            throws ProduceException {
        if ((this.produceDependencyServiceHandler == null)) {
            super.getLogger()
                    .error("No service implementation configured for produceDependency().");
            throw new InjectionException(
                    "No service implementation configured for produceDependency().");
        }
        ServiceResponse<DependencyMsg> rs;
        this.produceDependencyServiceHandler.init();
        rs = this.produceDependencyServiceHandler.invoke(rq);
        this.produceDependencyServiceHandler.finish();
        return rs;
    }
}
