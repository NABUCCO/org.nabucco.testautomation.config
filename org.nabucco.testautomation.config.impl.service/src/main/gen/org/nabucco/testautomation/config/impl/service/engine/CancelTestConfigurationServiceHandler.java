/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.testautomation.config.impl.service.engine;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.settings.facade.exception.engine.TestEngineException;

/**
 * CancelTestConfigurationServiceHandler<p/>The Service to interact with a remote TestEngine<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-06-25
 */
public abstract class CancelTestConfigurationServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.testautomation.config.impl.service.engine.CancelTestConfigurationServiceHandler";

    /** Constructs a new CancelTestConfigurationServiceHandler instance. */
    public CancelTestConfigurationServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<TestInfoMsg>.
     * @return the ServiceResponse<TestInfoMsg>.
     * @throws TestEngineException
     */
    protected ServiceResponse<TestInfoMsg> invoke(ServiceRequest<TestInfoMsg> rq) throws TestEngineException {
        ServiceResponse<TestInfoMsg> rs;
        TestInfoMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.cancelTestConfiguration(rq.getRequestMessage());
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
     * Missing description at method cancelTestConfiguration.
     *
     * @param msg the TestInfoMsg.
     * @return the TestInfoMsg.
     * @throws TestEngineException
     */
    protected abstract TestInfoMsg cancelTestConfiguration(TestInfoMsg msg) throws TestEngineException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
