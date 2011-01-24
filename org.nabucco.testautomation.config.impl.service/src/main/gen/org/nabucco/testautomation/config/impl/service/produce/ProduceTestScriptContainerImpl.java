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
import org.nabucco.testautomation.config.facade.message.ProduceTestScriptContainerMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestScriptContainer;

/**
 * ProduceTestScriptContainerImpl<p/>Service to produce TestScriptContainer<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceTestScriptContainerImpl extends ServiceSupport implements
        ProduceTestScriptContainer {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceTestScriptContainer";

    private ProduceTestScriptContainerServiceHandler produceTestScriptContainerServiceHandler;

    /** Constructs a new ProduceTestScriptContainerImpl instance. */
    public ProduceTestScriptContainerImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.produceTestScriptContainerServiceHandler = injector
                .inject(ProduceTestScriptContainerServiceHandler.getId());
        if ((this.produceTestScriptContainerServiceHandler != null)) {
            this.produceTestScriptContainerServiceHandler.setEntityManager(null);
            this.produceTestScriptContainerServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<ProduceTestScriptContainerMsg> produceTestScriptContainer(
            ServiceRequest<ProduceTestScriptContainerMsg> rq) throws ProduceException {
        if ((this.produceTestScriptContainerServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for produceTestScriptContainer().");
            throw new InjectionException(
                    "No service implementation configured for produceTestScriptContainer().");
        }
        ServiceResponse<ProduceTestScriptContainerMsg> rs;
        this.produceTestScriptContainerServiceHandler.init();
        rs = this.produceTestScriptContainerServiceHandler.invoke(rq);
        this.produceTestScriptContainerServiceHandler.finish();
        return rs;
    }
}
