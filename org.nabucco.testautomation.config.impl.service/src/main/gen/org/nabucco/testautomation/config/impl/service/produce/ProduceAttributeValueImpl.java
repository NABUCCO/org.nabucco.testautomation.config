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
import org.nabucco.testautomation.config.facade.message.AttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.ProduceAttributeValueMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceAttributeValue;

/**
 * ProduceAttributeValueImpl<p/>Service to produce AttributeValues<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceAttributeValueImpl extends ServiceSupport implements ProduceAttributeValue {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceAttributeValue";

    private ProduceAttributeValueServiceHandler produceAttributeValueServiceHandler;

    /** Constructs a new ProduceAttributeValueImpl instance. */
    public ProduceAttributeValueImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.produceAttributeValueServiceHandler = injector
                .inject(ProduceAttributeValueServiceHandler.getId());
        if ((this.produceAttributeValueServiceHandler != null)) {
            this.produceAttributeValueServiceHandler.setEntityManager(null);
            this.produceAttributeValueServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<AttributeValueMsg> produceAttributeValue(
            ServiceRequest<ProduceAttributeValueMsg> rq) throws ProduceException {
        if ((this.produceAttributeValueServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for produceAttributeValue().");
            throw new InjectionException(
                    "No service implementation configured for produceAttributeValue().");
        }
        ServiceResponse<AttributeValueMsg> rs;
        this.produceAttributeValueServiceHandler.init();
        rs = this.produceAttributeValueServiceHandler.invoke(rq);
        this.produceAttributeValueServiceHandler.finish();
        return rs;
    }
}
