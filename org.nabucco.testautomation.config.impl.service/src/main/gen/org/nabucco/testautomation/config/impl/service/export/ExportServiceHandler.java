/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.export;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.exporting.ExportException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.exporting.ExportRs;
import org.nabucco.framework.base.impl.service.handler.ServiceHandler;
import org.nabucco.framework.base.impl.service.handler.ServiceHandlerSupport;

/**
 * ExportServiceHandler<p/>Export Service for Config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2011-02-04
 */
public abstract class ExportServiceHandler extends ServiceHandlerSupport implements ServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.testautomation.config.impl.service.export.ExportServiceHandler";

    /** Constructs a new ExportServiceHandler instance. */
    public ExportServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<EmptyServiceMessage>.
     * @return the ServiceResponse<ExportRs>.
     * @throws ExportException
     */
    protected ServiceResponse<ExportRs> invoke(ServiceRequest<EmptyServiceMessage> rq)
            throws ExportException {
        ServiceResponse<ExportRs> rs;
        ExportRs msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.export(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<ExportRs>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (ExportException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            ExportException wrappedException = new ExportException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new ExportException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method export.
     *
     * @param msg the EmptyServiceMessage.
     * @return the ExportRs.
     * @throws ExportException
     */
    protected abstract ExportRs export(EmptyServiceMessage msg) throws ExportException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
