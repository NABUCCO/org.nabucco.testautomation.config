/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.produce;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.handler.ServiceHandler;
import org.nabucco.framework.base.impl.service.handler.ServiceHandlerSupport;
import org.nabucco.testautomation.config.facade.message.ProduceTestScriptContainerMsg;

/**
 * ProduceTestScriptContainerServiceHandler<p/>Service to produce TestScriptContainer<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public abstract class ProduceTestScriptContainerServiceHandler extends ServiceHandlerSupport
        implements ServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.testautomation.config.impl.service.produce.ProduceTestScriptContainerServiceHandler";

    /** Constructs a new ProduceTestScriptContainerServiceHandler instance. */
    public ProduceTestScriptContainerServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<ProduceTestScriptContainerMsg>.
     * @return the ServiceResponse<ProduceTestScriptContainerMsg>.
     * @throws ProduceException
     */
    protected ServiceResponse<ProduceTestScriptContainerMsg> invoke(
            ServiceRequest<ProduceTestScriptContainerMsg> rq) throws ProduceException {
        ServiceResponse<ProduceTestScriptContainerMsg> rs;
        ProduceTestScriptContainerMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.produceTestScriptContainer(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<ProduceTestScriptContainerMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (ProduceException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            ProduceException wrappedException = new ProduceException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new ProduceException(e.getMessage());
        }
    }

    /**
     * Missing description at method produceTestScriptContainer.
     *
     * @param msg the ProduceTestScriptContainerMsg.
     * @return the ProduceTestScriptContainerMsg.
     * @throws ProduceException
     */
    protected abstract ProduceTestScriptContainerMsg produceTestScriptContainer(
            ProduceTestScriptContainerMsg msg) throws ProduceException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
