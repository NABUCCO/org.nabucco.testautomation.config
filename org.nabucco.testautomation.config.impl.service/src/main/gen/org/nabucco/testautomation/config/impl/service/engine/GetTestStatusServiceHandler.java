/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.engine;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.handler.ServiceHandler;
import org.nabucco.framework.base.impl.service.handler.ServiceHandlerSupport;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.facade.exception.engine.TestEngineException;

/**
 * GetTestStatusServiceHandler<p/>The Service to interact with a remote TestEngine<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-06-25
 */
public abstract class GetTestStatusServiceHandler extends ServiceHandlerSupport implements
        ServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.testautomation.config.impl.service.engine.GetTestStatusServiceHandler";

    /** Constructs a new GetTestStatusServiceHandler instance. */
    public GetTestStatusServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<TestInfoMsg>.
     * @return the ServiceResponse<TestInfoMsg>.
     * @throws TestEngineException
     */
    protected ServiceResponse<TestInfoMsg> invoke(ServiceRequest<TestInfoMsg> rq)
            throws TestEngineException {
        ServiceResponse<TestInfoMsg> rs;
        TestInfoMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.getTestStatus(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<TestInfoMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (TestEngineException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            TestEngineException wrappedException = new TestEngineException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new TestEngineException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method getTestStatus.
     *
     * @param msg the TestInfoMsg.
     * @return the TestInfoMsg.
     * @throws TestEngineException
     */
    protected abstract TestInfoMsg getTestStatus(TestInfoMsg msg) throws TestEngineException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
