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
package org.nabucco.testautomation.config.impl.component;

import org.nabucco.framework.base.facade.component.handler.PostConstructHandler;
import org.nabucco.framework.base.facade.component.handler.PreDestroyHandler;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.base.impl.component.ComponentSupport;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocal;
import org.nabucco.testautomation.config.facade.component.ConfigComponentRemote;
import org.nabucco.testautomation.config.facade.service.engine.TestEngineService;
import org.nabucco.testautomation.config.facade.service.maintain.MaintainConfig;
import org.nabucco.testautomation.config.facade.service.produce.ProduceConfig;
import org.nabucco.testautomation.config.facade.service.report.ReportConfig;
import org.nabucco.testautomation.config.facade.service.resolve.ResolveConfig;
import org.nabucco.testautomation.config.facade.service.search.SearchConfig;

/**
 * ConfigComponentImpl<p/>Component for testautomation config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-16
 */
public class ConfigComponentImpl extends ComponentSupport implements ConfigComponentLocal, ConfigComponentRemote {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ConfigComponent";

    /** Constructs a new ConfigComponentImpl instance. */
    public ConfigComponentImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PostConstructHandler handler = injector.inject(PostConstructHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No post construct handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PreDestroyHandler handler = injector.inject(PreDestroyHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No pre destroy handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return COMPONENT_NAME;
    }

    @Override
    public String getJndiName() {
        return JNDI_NAME;
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.COMPONENT_RELATION_SERVICE_REMOTE, ComponentRelationService.class);
    }

    @Override
    public ComponentRelationService getComponentRelationServiceLocal() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.COMPONENT_RELATION_SERVICE_LOCAL, ComponentRelationService.class);
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.QUERY_FILTER_SERVICE_REMOTE, QueryFilterService.class);
    }

    @Override
    public QueryFilterService getQueryFilterServiceLocal() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.QUERY_FILTER_SERVICE_LOCAL, QueryFilterService.class);
    }

    @Override
    public MaintainConfig getMaintainConfigLocal() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.MAINTAIN_CONFIG_LOCAL, MaintainConfig.class);
    }

    @Override
    public MaintainConfig getMaintainConfig() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.MAINTAIN_CONFIG_REMOTE, MaintainConfig.class);
    }

    @Override
    public ProduceConfig getProduceConfigLocal() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.PRODUCE_CONFIG_LOCAL, ProduceConfig.class);
    }

    @Override
    public ProduceConfig getProduceConfig() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.PRODUCE_CONFIG_REMOTE, ProduceConfig.class);
    }

    @Override
    public SearchConfig getSearchConfigLocal() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.SEARCH_CONFIG_LOCAL, SearchConfig.class);
    }

    @Override
    public SearchConfig getSearchConfig() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.SEARCH_CONFIG_REMOTE, SearchConfig.class);
    }

    @Override
    public ResolveConfig getResolveConfigLocal() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.RESOLVE_CONFIG_LOCAL, ResolveConfig.class);
    }

    @Override
    public ResolveConfig getResolveConfig() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.RESOLVE_CONFIG_REMOTE, ResolveConfig.class);
    }

    @Override
    public ReportConfig getReportConfigLocal() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.REPORT_CONFIG_LOCAL, ReportConfig.class);
    }

    @Override
    public ReportConfig getReportConfig() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.REPORT_CONFIG_REMOTE, ReportConfig.class);
    }

    @Override
    public TestEngineService getTestEngineServiceLocal() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.TEST_ENGINE_SERVICE_LOCAL, TestEngineService.class);
    }

    @Override
    public TestEngineService getTestEngineService() throws ServiceException {
        return super.lookup(ConfigComponentJndiNames.TEST_ENGINE_SERVICE_REMOTE, TestEngineService.class);
    }
}
