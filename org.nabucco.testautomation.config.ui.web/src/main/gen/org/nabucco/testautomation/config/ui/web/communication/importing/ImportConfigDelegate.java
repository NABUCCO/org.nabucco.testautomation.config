/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication.importing;

import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.importing.ImportException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.importing.ImportRq;
import org.nabucco.framework.base.facade.message.importing.ImportRs;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.testautomation.config.facade.service.importing.ImportConfig;

/**
 * ImportConfigDelegate<p/>Service to import Config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2011-02-14
 */
public class ImportConfigDelegate extends ServiceDelegateSupport {

    private ImportConfig service;

    /**
     * Constructs a new ImportConfigDelegate instance.
     *
     * @param service the ImportConfig.
     */
    public ImportConfigDelegate(ImportConfig service) {
        super();
        this.service = service;
    }

    /**
     * ImportConfig.
     *
     * @param session the NabuccoSession.
     * @param rq the ImportRq.
     * @return the ImportRs.
     * @throws ImportException
     */
    public ImportRs importConfig(ImportRq rq, NabuccoSession session) throws ImportException {
        ServiceRequest<ImportRq> request = new ServiceRequest<ImportRq>(
                super.createServiceContext(session));
        request.setRequestMessage(rq);
        ServiceResponse<ImportRs> rs;
        if ((service != null)) {
            rs = service.importConfig(request);
        } else {
            throw new ImportException("Cannot execute service operation: ImportConfig.importConfig");
        }
        return rs.getResponseMessage();
    }
}
