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
import org.nabucco.testautomation.config.facade.message.ProduceTestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestConfigElement;

/**
 * ProduceTestConfigElementImpl<p/>Service to produce and clone TestConfigElements<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceTestConfigElementImpl extends ServiceSupport implements
        ProduceTestConfigElement {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceTestConfigElement";

    private ProduceTestConfigElementServiceHandler produceTestConfigElementServiceHandler;

    private ProduceTestConfigElementCloneServiceHandler produceTestConfigElementCloneServiceHandler;

    /** Constructs a new ProduceTestConfigElementImpl instance. */
    public ProduceTestConfigElementImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.produceTestConfigElementServiceHandler = injector
                .inject(ProduceTestConfigElementServiceHandler.getId());
        if ((this.produceTestConfigElementServiceHandler != null)) {
            this.produceTestConfigElementServiceHandler.setEntityManager(null);
            this.produceTestConfigElementServiceHandler.setLogger(super.getLogger());
        }
        this.produceTestConfigElementCloneServiceHandler = injector
                .inject(ProduceTestConfigElementCloneServiceHandler.getId());
        if ((this.produceTestConfigElementCloneServiceHandler != null)) {
            this.produceTestConfigElementCloneServiceHandler.setEntityManager(null);
            this.produceTestConfigElementCloneServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<TestConfigElementMsg> produceTestConfigElement(
            ServiceRequest<ProduceTestConfigElementMsg> rq) throws ProduceException {
        if ((this.produceTestConfigElementServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for produceTestConfigElement().");
            throw new InjectionException(
                    "No service implementation configured for produceTestConfigElement().");
        }
        ServiceResponse<TestConfigElementMsg> rs;
        this.produceTestConfigElementServiceHandler.init();
        rs = this.produceTestConfigElementServiceHandler.invoke(rq);
        this.produceTestConfigElementServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigElementMsg> produceTestConfigElementClone(
            ServiceRequest<TestConfigElementMsg> rq) throws ProduceException {
        if ((this.produceTestConfigElementCloneServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for produceTestConfigElementClone().");
            throw new InjectionException(
                    "No service implementation configured for produceTestConfigElementClone().");
        }
        ServiceResponse<TestConfigElementMsg> rs;
        this.produceTestConfigElementCloneServiceHandler.init();
        rs = this.produceTestConfigElementCloneServiceHandler.invoke(rq);
        this.produceTestConfigElementCloneServiceHandler.finish();
        return rs;
    }
}
