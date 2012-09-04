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
package org.nabucco.testautomation.config.impl.service.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.testautomation.config.facade.message.TestConfigElementListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.facade.service.search.SearchConfig;
import org.nabucco.testautomation.property.facade.message.QuickSearchMsg;
import org.nabucco.testautomation.property.facade.message.QuickSearchRs;

/**
 * SearchConfigImpl<p/>Config search service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class SearchConfigImpl extends ServiceSupport implements SearchConfig {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SearchConfig";

    private static Map<String, String[]> ASPECTS;

    private SearchTestConfigurationServiceHandler searchTestConfigurationServiceHandler;

    private SearchTestConfigElementServiceHandler searchTestConfigElementServiceHandler;

    private SearchReferencingTestConfigurationsServiceHandler searchReferencingTestConfigurationsServiceHandler;

    private QuickSearchServiceHandler quickSearchServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new SearchConfigImpl instance. */
    public SearchConfigImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.searchTestConfigurationServiceHandler = injector.inject(SearchTestConfigurationServiceHandler.getId());
        if ((this.searchTestConfigurationServiceHandler != null)) {
            this.searchTestConfigurationServiceHandler.setPersistenceManager(persistenceManager);
            this.searchTestConfigurationServiceHandler.setLogger(super.getLogger());
        }
        this.searchTestConfigElementServiceHandler = injector.inject(SearchTestConfigElementServiceHandler.getId());
        if ((this.searchTestConfigElementServiceHandler != null)) {
            this.searchTestConfigElementServiceHandler.setPersistenceManager(persistenceManager);
            this.searchTestConfigElementServiceHandler.setLogger(super.getLogger());
        }
        this.searchReferencingTestConfigurationsServiceHandler = injector
                .inject(SearchReferencingTestConfigurationsServiceHandler.getId());
        if ((this.searchReferencingTestConfigurationsServiceHandler != null)) {
            this.searchReferencingTestConfigurationsServiceHandler.setPersistenceManager(persistenceManager);
            this.searchReferencingTestConfigurationsServiceHandler.setLogger(super.getLogger());
        }
        this.quickSearchServiceHandler = injector.inject(QuickSearchServiceHandler.getId());
        if ((this.quickSearchServiceHandler != null)) {
            this.quickSearchServiceHandler.setPersistenceManager(persistenceManager);
            this.quickSearchServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("searchTestConfiguration", NO_ASPECTS);
            ASPECTS.put("searchTestConfigElement", NO_ASPECTS);
            ASPECTS.put("searchReferencingTestConfigurations", NO_ASPECTS);
            ASPECTS.put("quickSearch", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<TestConfigurationListMsg> searchTestConfiguration(
            ServiceRequest<TestConfigurationSearchMsg> rq) throws SearchException {
        if ((this.searchTestConfigurationServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchTestConfiguration().");
            throw new InjectionException("No service implementation configured for searchTestConfiguration().");
        }
        ServiceResponse<TestConfigurationListMsg> rs;
        this.searchTestConfigurationServiceHandler.init();
        rs = this.searchTestConfigurationServiceHandler.invoke(rq);
        this.searchTestConfigurationServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigElementListMsg> searchTestConfigElement(
            ServiceRequest<TestConfigElementSearchMsg> rq) throws SearchException {
        if ((this.searchTestConfigElementServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchTestConfigElement().");
            throw new InjectionException("No service implementation configured for searchTestConfigElement().");
        }
        ServiceResponse<TestConfigElementListMsg> rs;
        this.searchTestConfigElementServiceHandler.init();
        rs = this.searchTestConfigElementServiceHandler.invoke(rq);
        this.searchTestConfigElementServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigurationListMsg> searchReferencingTestConfigurations(
            ServiceRequest<TestConfigElementMsg> rq) throws SearchException {
        if ((this.searchReferencingTestConfigurationsServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchReferencingTestConfigurations().");
            throw new InjectionException(
                    "No service implementation configured for searchReferencingTestConfigurations().");
        }
        ServiceResponse<TestConfigurationListMsg> rs;
        this.searchReferencingTestConfigurationsServiceHandler.init();
        rs = this.searchReferencingTestConfigurationsServiceHandler.invoke(rq);
        this.searchReferencingTestConfigurationsServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<QuickSearchRs> quickSearch(ServiceRequest<QuickSearchMsg> rq) throws SearchException {
        if ((this.quickSearchServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for quickSearch().");
            throw new InjectionException("No service implementation configured for quickSearch().");
        }
        ServiceResponse<QuickSearchRs> rs;
        this.quickSearchServiceHandler.init();
        rs = this.quickSearchServiceHandler.invoke(rq);
        this.quickSearchServiceHandler.finish();
        return rs;
    }
}
