/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.engine;

import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.testautomation.config.facade.message.engine.ManualTestResultMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestResultMsg;
import org.nabucco.testautomation.config.facade.service.engine.TestEngineService;
import org.nabucco.testautomation.facade.exception.engine.TestEngineException;

/**
 * TestEngineServiceImpl<p/>The Service to interact with a remote TestEngine<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-06-25
 */
public class TestEngineServiceImpl extends ServiceSupport implements TestEngineService {

    private static final long serialVersionUID = 1L;

    private static final String ID = "TestEngineService";

    private ExecuteTestConfigurationServiceHandler executeTestConfigurationServiceHandler;

    private GetTestStatusServiceHandler getTestStatusServiceHandler;

    private GetTestConfigurationResultServiceHandler getTestConfigurationResultServiceHandler;

    private CancelTestConfigurationServiceHandler cancelTestConfigurationServiceHandler;

    private ReturnManualTestResultServiceHandler returnManualTestResultServiceHandler;

    /** Constructs a new TestEngineServiceImpl instance. */
    public TestEngineServiceImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.executeTestConfigurationServiceHandler = injector
                .inject(ExecuteTestConfigurationServiceHandler.getId());
        if ((this.executeTestConfigurationServiceHandler != null)) {
            this.executeTestConfigurationServiceHandler.setEntityManager(null);
            this.executeTestConfigurationServiceHandler.setLogger(super.getLogger());
        }
        this.getTestStatusServiceHandler = injector.inject(GetTestStatusServiceHandler.getId());
        if ((this.getTestStatusServiceHandler != null)) {
            this.getTestStatusServiceHandler.setEntityManager(null);
            this.getTestStatusServiceHandler.setLogger(super.getLogger());
        }
        this.getTestConfigurationResultServiceHandler = injector
                .inject(GetTestConfigurationResultServiceHandler.getId());
        if ((this.getTestConfigurationResultServiceHandler != null)) {
            this.getTestConfigurationResultServiceHandler.setEntityManager(null);
            this.getTestConfigurationResultServiceHandler.setLogger(super.getLogger());
        }
        this.cancelTestConfigurationServiceHandler = injector
                .inject(CancelTestConfigurationServiceHandler.getId());
        if ((this.cancelTestConfigurationServiceHandler != null)) {
            this.cancelTestConfigurationServiceHandler.setEntityManager(null);
            this.cancelTestConfigurationServiceHandler.setLogger(super.getLogger());
        }
        this.returnManualTestResultServiceHandler = injector
                .inject(ReturnManualTestResultServiceHandler.getId());
        if ((this.returnManualTestResultServiceHandler != null)) {
            this.returnManualTestResultServiceHandler.setEntityManager(null);
            this.returnManualTestResultServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<TestInfoMsg> executeTestConfiguration(ServiceRequest<TestExecutionMsg> rq)
            throws TestEngineException {
        if ((this.executeTestConfigurationServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for executeTestConfiguration().");
            throw new InjectionException(
                    "No service implementation configured for executeTestConfiguration().");
        }
        ServiceResponse<TestInfoMsg> rs;
        this.executeTestConfigurationServiceHandler.init();
        rs = this.executeTestConfigurationServiceHandler.invoke(rq);
        this.executeTestConfigurationServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestInfoMsg> getTestStatus(ServiceRequest<TestInfoMsg> rq)
            throws TestEngineException {
        if ((this.getTestStatusServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for getTestStatus().");
            throw new InjectionException(
                    "No service implementation configured for getTestStatus().");
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
            super.getLogger().error(
                    "No service implementation configured for getTestConfigurationResult().");
            throw new InjectionException(
                    "No service implementation configured for getTestConfigurationResult().");
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
            super.getLogger().error(
                    "No service implementation configured for cancelTestConfiguration().");
            throw new InjectionException(
                    "No service implementation configured for cancelTestConfiguration().");
        }
        ServiceResponse<TestInfoMsg> rs;
        this.cancelTestConfigurationServiceHandler.init();
        rs = this.cancelTestConfigurationServiceHandler.invoke(rq);
        this.cancelTestConfigurationServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestInfoMsg> returnManualTestResult(
            ServiceRequest<ManualTestResultMsg> rq) throws TestEngineException {
        if ((this.returnManualTestResultServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for returnManualTestResult().");
            throw new InjectionException(
                    "No service implementation configured for returnManualTestResult().");
        }
        ServiceResponse<TestInfoMsg> rs;
        this.returnManualTestResultServiceHandler.init();
        rs = this.returnManualTestResultServiceHandler.invoke(rq);
        this.returnManualTestResultServiceHandler.finish();
        return rs;
    }
}
