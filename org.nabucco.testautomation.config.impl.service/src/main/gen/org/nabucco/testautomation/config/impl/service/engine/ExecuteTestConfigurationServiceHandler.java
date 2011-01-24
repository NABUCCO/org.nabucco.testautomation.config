/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.engine;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.handler.ServiceHandler;
import org.nabucco.framework.base.impl.service.handler.ServiceHandlerSupport;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.facade.exception.engine.TestEngineException;

/**
 * ExecuteTestConfigurationServiceHandler<p/>The Service to interact with a remote TestEngine<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-06-25
 */
public abstract class ExecuteTestConfigurationServiceHandler extends ServiceHandlerSupport
        implements ServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.testautomation.config.impl.service.engine.ExecuteTestConfigurationServiceHandler";

    /** Constructs a new ExecuteTestConfigurationServiceHandler instance. */
    public ExecuteTestConfigurationServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<TestExecutionMsg>.
     * @return the ServiceResponse<TestInfoMsg>.
     * @throws TestEngineException
     */
    protected ServiceResponse<TestInfoMsg> invoke(ServiceRequest<TestExecutionMsg> rq)
            throws TestEngineException {
        ServiceResponse<TestInfoMsg> rs;
        TestInfoMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.executeTestConfiguration(rq.getRequestMessage());
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
            throw new TestEngineException(e.getMessage());
        }
    }

    /**
     * Missing description at method executeTestConfiguration.
     *
     * @param msg the TestExecutionMsg.
     * @return the TestInfoMsg.
     * @throws TestEngineException
     */
    protected abstract TestInfoMsg executeTestConfiguration(TestExecutionMsg msg)
            throws TestEngineException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
