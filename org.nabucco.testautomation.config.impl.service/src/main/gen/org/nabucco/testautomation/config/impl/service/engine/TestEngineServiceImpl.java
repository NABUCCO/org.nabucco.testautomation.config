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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.testautomation.config.facade.message.engine.ManualTestResultMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestResultMsg;
import org.nabucco.testautomation.config.facade.service.engine.TestEngineService;
import org.nabucco.testautomation.settings.facade.exception.engine.TestEngineException;

/**
 * TestEngineServiceImpl<p/>The Service to interact with a remote TestEngine<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-06-25
 */
public class TestEngineServiceImpl extends ServiceSupport implements TestEngineService {

    private static final long serialVersionUID = 1L;

    private static final String ID = "TestEngineService";

    private static Map<String, String[]> ASPECTS;

    private ExecuteTestConfigurationServiceHandler executeTestConfigurationServiceHandler;

    private GetTestStatusServiceHandler getTestStatusServiceHandler;

    private GetTestConfigurationResultServiceHandler getTestConfigurationResultServiceHandler;

    private CancelTestConfigurationServiceHandler cancelTestConfigurationServiceHandler;

    private ReturnManualTestResultServiceHandler returnManualTestResultServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new TestEngineServiceImpl instance. */
    public TestEngineServiceImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.executeTestConfigurationServiceHandler = injector.inject(ExecuteTestConfigurationServiceHandler.getId());
        if ((this.executeTestConfigurationServiceHandler != null)) {
            this.executeTestConfigurationServiceHandler.setPersistenceManager(persistenceManager);
            this.executeTestConfigurationServiceHandler.setLogger(super.getLogger());
        }
        this.getTestStatusServiceHandler = injector.inject(GetTestStatusServiceHandler.getId());
        if ((this.getTestStatusServiceHandler != null)) {
            this.getTestStatusServiceHandler.setPersistenceManager(persistenceManager);
            this.getTestStatusServiceHandler.setLogger(super.getLogger());
        }
        this.getTestConfigurationResultServiceHandler = injector.inject(GetTestConfigurationResultServiceHandler
                .getId());
        if ((this.getTestConfigurationResultServiceHandler != null)) {
            this.getTestConfigurationResultServiceHandler.setPersistenceManager(persistenceManager);
            this.getTestConfigurationResultServiceHandler.setLogger(super.getLogger());
        }
        this.cancelTestConfigurationServiceHandler = injector.inject(CancelTestConfigurationServiceHandler.getId());
        if ((this.cancelTestConfigurationServiceHandler != null)) {
            this.cancelTestConfigurationServiceHandler.setPersistenceManager(persistenceManager);
            this.cancelTestConfigurationServiceHandler.setLogger(super.getLogger());
        }
        this.returnManualTestResultServiceHandler = injector.inject(ReturnManualTestResultServiceHandler.getId());
        if ((this.returnManualTestResultServiceHandler != null)) {
            this.returnManualTestResultServiceHandler.setPersistenceManager(persistenceManager);
            this.returnManualTestResultServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("executeTestConfiguration", NO_ASPECTS);
            ASPECTS.put("getTestStatus", NO_ASPECTS);
            ASPECTS.put("getTestConfigurationResult", NO_ASPECTS);
            ASPECTS.put("cancelTestConfiguration", NO_ASPECTS);
            ASPECTS.put("returnManualTestResult", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<TestInfoMsg> executeTestConfiguration(ServiceRequest<TestExecutionMsg> rq)
            throws TestEngineException {
        if ((this.executeTestConfigurationServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for executeTestConfiguration().");
            throw new InjectionException("No service implementation configured for executeTestConfiguration().");
        }
        ServiceResponse<TestInfoMsg> rs;
        this.executeTestConfigurationServiceHandler.init();
        rs = this.executeTestConfigurationServiceHandler.invoke(rq);
        this.executeTestConfigurationServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestInfoMsg> getTestStatus(ServiceRequest<TestInfoMsg> rq) throws TestEngineException {
        if ((this.getTestStatusServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for getTestStatus().");
            throw new InjectionException("No service implementation configured for getTestStatus().");
        }
        ServiceResponse<TestInfoMsg> rs;
        this.getTestStatusServiceHandler.init();
        rs = this.getTestStatusServiceHandler.invoke(rq);
        this.getTestStatusServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestResultMsg> getTestConfigurationResult(ServiceRequest<TestInfoMsg> rq)
            throws TestEngineException {
        if ((this.getTestConfigurationResultServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for getTestConfigurationResult().");
            throw new InjectionException("No service implementation configured for getTestConfigurationResult().");
        }
        ServiceResponse<TestResultMsg> rs;
        this.getTestConfigurationResultServiceHandler.init();
        rs = this.getTestConfigurationResultServiceHandler.invoke(rq);
        this.getTestConfigurationResultServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestInfoMsg> cancelTestConfiguration(ServiceRequest<TestInfoMsg> rq)
            throws TestEngineException {
        if ((this.cancelTestConfigurationServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for cancelTestConfiguration().");
            throw new InjectionException("No service implementation configured for cancelTestConfiguration().");
        }
        ServiceResponse<TestInfoMsg> rs;
        this.cancelTestConfigurationServiceHandler.init();
        rs = this.cancelTestConfigurationServiceHandler.invoke(rq);
        this.cancelTestConfigurationServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestInfoMsg> returnManualTestResult(ServiceRequest<ManualTestResultMsg> rq)
            throws TestEngineException {
        if ((this.returnManualTestResultServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for returnManualTestResult().");
            throw new InjectionException("No service implementation configured for returnManualTestResult().");
        }
        ServiceResponse<TestInfoMsg> rs;
        this.returnManualTestResultServiceHandler.init();
        rs = this.returnManualTestResultServiceHandler.invoke(rq);
        this.returnManualTestResultServiceHandler.finish();
        return rs;
    }
}
