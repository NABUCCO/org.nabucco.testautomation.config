/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.importing;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.importing.ImportException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.importing.ImportRq;
import org.nabucco.framework.base.facade.message.importing.ImportRs;
import org.nabucco.framework.base.impl.service.handler.ServiceHandler;
import org.nabucco.framework.base.impl.service.handler.ServiceHandlerSupport;

/**
 * ImportConfigServiceHandler<p/>Service to import Config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2011-02-14
 */
public abstract class ImportConfigServiceHandler extends ServiceHandlerSupport implements
        ServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.testautomation.config.impl.service.importing.ImportConfigServiceHandler";

    /** Constructs a new ImportConfigServiceHandler instance. */
    public ImportConfigServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<ImportRq>.
     * @return the ServiceResponse<ImportRs>.
     * @throws ImportException
     */
    protected ServiceResponse<ImportRs> invoke(ServiceRequest<ImportRq> rq) throws ImportException {
        ServiceResponse<ImportRs> rs;
        ImportRs msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.importConfig(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<ImportRs>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (ImportException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            ImportException wrappedException = new ImportException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new ImportException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method importConfig.
     *
     * @param msg the ImportRq.
     * @return the ImportRs.
     * @throws ImportException
     */
    protected abstract ImportRs importConfig(ImportRq msg) throws ImportException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
