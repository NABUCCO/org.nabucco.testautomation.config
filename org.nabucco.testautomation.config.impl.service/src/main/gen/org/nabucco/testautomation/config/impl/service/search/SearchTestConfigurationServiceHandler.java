/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.search;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.handler.ServiceHandler;
import org.nabucco.framework.base.impl.service.handler.ServiceHandlerSupport;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;

/**
 * SearchTestConfigurationServiceHandler<p/>TestConfiguration search service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public abstract class SearchTestConfigurationServiceHandler extends ServiceHandlerSupport implements
        ServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.testautomation.config.impl.service.search.SearchTestConfigurationServiceHandler";

    /** Constructs a new SearchTestConfigurationServiceHandler instance. */
    public SearchTestConfigurationServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<TestConfigurationSearchMsg>.
     * @return the ServiceResponse<TestConfigurationListMsg>.
     * @throws SearchException
     */
    protected ServiceResponse<TestConfigurationListMsg> invoke(
            ServiceRequest<TestConfigurationSearchMsg> rq) throws SearchException {
        ServiceResponse<TestConfigurationListMsg> rs;
        TestConfigurationListMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.searchTestConfiguration(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<TestConfigurationListMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (SearchException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            SearchException wrappedException = new SearchException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new SearchException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method searchTestConfiguration.
     *
     * @param msg the TestConfigurationSearchMsg.
     * @return the TestConfigurationListMsg.
     * @throws SearchException
     */
    protected abstract TestConfigurationListMsg searchTestConfiguration(
            TestConfigurationSearchMsg msg) throws SearchException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
