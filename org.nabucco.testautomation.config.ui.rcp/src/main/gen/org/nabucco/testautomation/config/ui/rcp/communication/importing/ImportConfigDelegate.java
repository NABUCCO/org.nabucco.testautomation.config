/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.communication.importing;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.importing.ImportRq;
import org.nabucco.framework.base.facade.message.importing.ImportRs;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.plugin.base.logging.NabuccoLogMessage;
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
     * @param rq the ImportRq.
     * @return the ImportRs.
     * @throws ClientException
     */
    public ImportRs importConfig(ImportRq rq) throws ClientException {
        ServiceRequest<ImportRq> request = new ServiceRequest<ImportRq>(
                super.createServiceContext());
        request.setRequestMessage(rq);
        ServiceResponse<ImportRs> rs;
        if ((service != null)) {
            long start = System.currentTimeMillis();
            try {
                rs = service.importConfig(request);
                return rs.getResponseMessage();
            } catch (Exception exception) {
                super.processException(exception);
            } finally {
                long end = System.currentTimeMillis();
                Activator.getDefault().logDebug(
                        new NabuccoLogMessage(ImportConfigDelegate.class, "Service: ",
                                "ImportConfig.importConfig", " Time: ", String
                                        .valueOf((end - start)), "ms."));
            }
        }
        throw new ClientException("Cannot execute service operation: ImportConfig.importConfig");
    }
}
