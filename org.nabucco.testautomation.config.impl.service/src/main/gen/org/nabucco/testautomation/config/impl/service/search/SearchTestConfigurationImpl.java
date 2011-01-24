/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.service.search;

import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.facade.service.search.SearchTestConfiguration;

/**
 * SearchTestConfigurationImpl<p/>TestConfiguration search service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class SearchTestConfigurationImpl extends ServiceSupport implements SearchTestConfiguration {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SearchTestConfiguration";

    private EntityManager em;

    private SearchTestConfigurationServiceHandler searchTestConfigurationServiceHandler;

    private GetTestConfigurationServiceHandler getTestConfigurationServiceHandler;

    /** Constructs a new SearchTestConfigurationImpl instance. */
    public SearchTestConfigurationImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.searchTestConfigurationServiceHandler = injector
                .inject(SearchTestConfigurationServiceHandler.getId());
        if ((this.searchTestConfigurationServiceHandler != null)) {
            this.searchTestConfigurationServiceHandler.setEntityManager(this.em);
            this.searchTestConfigurationServiceHandler.setLogger(super.getLogger());
        }
        this.getTestConfigurationServiceHandler = injector
                .inject(GetTestConfigurationServiceHandler.getId());
        if ((this.getTestConfigurationServiceHandler != null)) {
            this.getTestConfigurationServiceHandler.setEntityManager(this.em);
            this.getTestConfigurationServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<TestConfigurationListMsg> searchTestConfiguration(
            ServiceRequest<TestConfigurationSearchMsg> rq) throws SearchException {
        if ((this.searchTestConfigurationServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for searchTestConfiguration().");
            throw new InjectionException(
                    "No service implementation configured for searchTestConfiguration().");
        }
        ServiceResponse<TestConfigurationListMsg> rs;
        this.searchTestConfigurationServiceHandler.init();
        rs = this.searchTestConfigurationServiceHandler.invoke(rq);
        this.searchTestConfigurationServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigurationListMsg> getTestConfiguration(
            ServiceRequest<TestConfigurationSearchMsg> rq) throws SearchException {
        if ((this.getTestConfigurationServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for getTestConfiguration().");
            throw new InjectionException(
                    "No service implementation configured for getTestConfiguration().");
        }
        ServiceResponse<TestConfigurationListMsg> rs;
        this.getTestConfigurationServiceHandler.init();
        rs = this.getTestConfigurationServiceHandler.invoke(rq);
        this.getTestConfigurationServiceHandler.finish();
        return rs;
    }
}
