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
import org.nabucco.testautomation.config.facade.message.TestConfigElementListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.facade.service.search.SearchTestConfigElement;

/**
 * SearchTestConfigElementImpl<p/>TestConfigElement search service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class SearchTestConfigElementImpl extends ServiceSupport implements SearchTestConfigElement {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SearchTestConfigElement";

    private EntityManager em;

    private SearchTestConfigElementServiceHandler searchTestConfigElementServiceHandler;

    private GetTestConfigElementServiceHandler getTestConfigElementServiceHandler;

    /** Constructs a new SearchTestConfigElementImpl instance. */
    public SearchTestConfigElementImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        this.searchTestConfigElementServiceHandler = injector
                .inject(SearchTestConfigElementServiceHandler.getId());
        if ((this.searchTestConfigElementServiceHandler != null)) {
            this.searchTestConfigElementServiceHandler.setEntityManager(this.em);
            this.searchTestConfigElementServiceHandler.setLogger(super.getLogger());
        }
        this.getTestConfigElementServiceHandler = injector
                .inject(GetTestConfigElementServiceHandler.getId());
        if ((this.getTestConfigElementServiceHandler != null)) {
            this.getTestConfigElementServiceHandler.setEntityManager(this.em);
            this.getTestConfigElementServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<TestConfigElementListMsg> searchTestConfigElement(
            ServiceRequest<TestConfigElementSearchMsg> rq) throws SearchException {
        if ((this.searchTestConfigElementServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for searchTestConfigElement().");
            throw new InjectionException(
                    "No service implementation configured for searchTestConfigElement().");
        }
        ServiceResponse<TestConfigElementListMsg> rs;
        this.searchTestConfigElementServiceHandler.init();
        rs = this.searchTestConfigElementServiceHandler.invoke(rq);
        this.searchTestConfigElementServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigElementMsg> getTestConfigElement(
            ServiceRequest<TestConfigElementSearchMsg> rq) throws SearchException {
        if ((this.getTestConfigElementServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for getTestConfigElement().");
            throw new InjectionException(
                    "No service implementation configured for getTestConfigElement().");
        }
        ServiceResponse<TestConfigElementMsg> rs;
        this.getTestConfigElementServiceHandler.init();
        rs = this.getTestConfigElementServiceHandler.invoke(rq);
        this.getTestConfigElementServiceHandler.finish();
        return rs;
    }
}
