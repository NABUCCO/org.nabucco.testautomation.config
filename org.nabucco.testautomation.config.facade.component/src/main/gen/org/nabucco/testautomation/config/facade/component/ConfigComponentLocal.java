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
import org.nabucco.testautomation.config.facade.service.engine.TestEngineService;
import org.nabucco.testautomation.config.facade.service.maintain.MaintainConfig;
import org.nabucco.testautomation.config.facade.service.produce.ProduceConfig;
import org.nabucco.testautomation.config.facade.service.report.ReportConfig;
import org.nabucco.testautomation.config.facade.service.resolve.ResolveConfig;
import org.nabucco.testautomation.config.facade.service.search.SearchConfig;

/**
 * ConfigComponentLocal<p/>Component for testautomation config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-16
 */
public interface ConfigComponentLocal extends ConfigComponent {

    /**
     * Getter for the ComponentRelationServiceLocal.
     *
     * @return the ComponentRelationService.
     * @throws ServiceException
     */
    ComponentRelationService getComponentRelationServiceLocal() throws ServiceException;

    /**
     * Getter for the QueryFilterServiceLocal.
     *
     * @return the QueryFilterService.
     * @throws ServiceException
     */
    QueryFilterService getQueryFilterServiceLocal() throws ServiceException;

    /**
     * Getter for the MaintainConfigLocal.
     *
     * @return the MaintainConfig.
     * @throws ServiceException
     */
    MaintainConfig getMaintainConfigLocal() throws ServiceException;

    /**
     * Getter for the ProduceConfigLocal.
     *
     * @return the ProduceConfig.
     * @throws ServiceException
     */
    ProduceConfig getProduceConfigLocal() throws ServiceException;

    /**
     * Getter for the SearchConfigLocal.
     *
     * @return the SearchConfig.
     * @throws ServiceException
     */
    SearchConfig getSearchConfigLocal() throws ServiceException;

    /**
     * Getter for the ResolveConfigLocal.
     *
     * @return the ResolveConfig.
     * @throws ServiceException
     */
    ResolveConfig getResolveConfigLocal() throws ServiceException;

    /**
     * Getter for the ReportConfigLocal.
     *
     * @return the ReportConfig.
     * @throws ServiceException
     */
    ReportConfig getReportConfigLocal() throws ServiceException;

    /**
     * Getter for the TestEngineServiceLocal.
     *
     * @return the TestEngineService.
     * @throws ServiceException
     */
    TestEngineService getTestEngineServiceLocal() throws ServiceException;
}
