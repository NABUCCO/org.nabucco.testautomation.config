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
import org.nabucco.testautomation.config.facade.message.TestConfigElementContainerMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;

/**
 * ProduceTestConfigElementContainerServiceHandler<p/>Service to produce TestConfigElementContainer<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public abstract class ProduceTestConfigElementContainerServiceHandler extends ServiceHandlerSupport
        implements ServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.testautomation.config.impl.service.produce.ProduceTestConfigElementContainerServiceHandler";

    /** Constructs a new ProduceTestConfigElementContainerServiceHandler instance. */
    public ProduceTestConfigElementContainerServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<TestConfigElementMsg>.
     * @return the ServiceResponse<TestConfigElementContainerMsg>.
     * @throws ProduceException
     */
    protected ServiceResponse<TestConfigElementContainerMsg> invoke(
            ServiceRequest<TestConfigElementMsg> rq) throws ProduceException {
        ServiceResponse<TestConfigElementContainerMsg> rs;
        TestConfigElementContainerMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.produceTestConfigElementContainer(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<TestConfigElementContainerMsg>(rq.getContext());
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
            throw new ProduceException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method produceTestConfigElementContainer.
     *
     * @param msg the TestConfigElementMsg.
     * @return the TestConfigElementContainerMsg.
     * @throws ProduceException
     */
    protected abstract TestConfigElementContainerMsg produceTestConfigElementContainer(
            TestConfigElementMsg msg) throws ProduceException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
