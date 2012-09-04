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
package org.nabucco.testautomation.config.facade.component;

import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.testautomation.config.facade.component.ConfigComponent;
import org.nabucco.testautomation.config.facade.service.engine.TestEngineService;
import org.nabucco.testautomation.config.facade.service.maintain.MaintainConfig;
import org.nabucco.testautomation.config.facade.service.produce.ProduceConfig;
import org.nabucco.testautomation.config.facade.service.report.ReportConfig;
import org.nabucco.testautomation.config.facade.service.resolve.ResolveConfig;
import org.nabucco.testautomation.config.facade.service.search.SearchConfig;

/**
 * ConfigComponentLocalProxy<p/>Component for testautomation config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-16
 */
public class ConfigComponentLocalProxy implements ConfigComponent {

    private static final long serialVersionUID = 1L;

    private final ConfigComponentLocal delegate;

    /**
     * Constructs a new ConfigComponentLocalProxy instance.
     *
     * @param delegate the ConfigComponentLocal.
     */
    public ConfigComponentLocalProxy(ConfigComponentLocal delegate) {
        super();
        if ((delegate == null)) {
            throw new IllegalArgumentException("Cannot create local proxy for component [null].");
        }
        this.delegate = delegate;
    }

    @Override
    public String getId() {
        return this.delegate.getId();
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public String getJndiName() {
        return this.delegate.getJndiName();
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return this.delegate.getComponentRelationServiceLocal();
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return this.delegate.getQueryFilterServiceLocal();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public MaintainConfig getMaintainConfig() throws ServiceException {
        return this.delegate.getMaintainConfigLocal();
    }

    @Override
    public ProduceConfig getProduceConfig() throws ServiceException {
        return this.delegate.getProduceConfigLocal();
    }

    @Override
    public SearchConfig getSearchConfig() throws ServiceException {
        return this.delegate.getSearchConfigLocal();
    }

    @Override
    public ResolveConfig getResolveConfig() throws ServiceException {
        return this.delegate.getResolveConfigLocal();
    }

    @Override
    public ReportConfig getReportConfig() throws ServiceException {
        return this.delegate.getReportConfigLocal();
    }

    @Override
    public TestEngineService getTestEngineService() throws ServiceException {
        return this.delegate.getTestEngineServiceLocal();
    }
}
