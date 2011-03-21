/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.importing;

import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.importing.ImportException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.importing.ImportRq;
import org.nabucco.framework.base.facade.message.importing.ImportRs;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.testautomation.config.facade.service.importing.ImportConfig;

/**
 * ImportConfigImpl<p/>Service to import Config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2011-02-14
 */
public class ImportConfigImpl extends ServiceSupport implements ImportConfig {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ImportConfig";

    private EntityManager entityManager;

    private ImportConfigServiceHandler importConfigServiceHandler;

    /** Constructs a new ImportConfigImpl instance. */
    public ImportConfigImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.importConfigServiceHandler = injector.inject(ImportConfigServiceHandler.getId());
        if ((this.importConfigServiceHandler != null)) {
            this.importConfigServiceHandler.setEntityManager(this.entityManager);
            this.importConfigServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<ImportRs> importConfig(ServiceRequest<ImportRq> rq)
            throws ImportException {
        if ((this.importConfigServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for importConfig().");
            throw new InjectionException("No service implementation configured for importConfig().");
        }
        ServiceResponse<ImportRs> rs;
        this.importConfigServiceHandler.init();
        rs = this.importConfigServiceHandler.invoke(rq);
        this.importConfigServiceHandler.finish();
        return rs;
    }
}
