/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.export;

import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.exporting.ExportException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.exporting.ExportRs;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.testautomation.config.facade.service.export.ExportConfig;

/**
 * ExportConfigImpl<p/>Export Service for Config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2011-02-04
 */
public class ExportConfigImpl extends ServiceSupport implements ExportConfig {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ExportConfig";

    private EntityManager entityManager;

    private ExportServiceHandler exportServiceHandler;

    /** Constructs a new ExportConfigImpl instance. */
    public ExportConfigImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.exportServiceHandler = injector.inject(ExportServiceHandler.getId());
        if ((this.exportServiceHandler != null)) {
            this.exportServiceHandler.setEntityManager(this.entityManager);
            this.exportServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<ExportRs> export(ServiceRequest<EmptyServiceMessage> rq)
            throws ExportException {
        if ((this.exportServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for export().");
            throw new InjectionException("No service implementation configured for export().");
        }
        ServiceResponse<ExportRs> rs;
        this.exportServiceHandler.init();
        rs = this.exportServiceHandler.invoke(rq);
        this.exportServiceHandler.finish();
        return rs;
    }
}
