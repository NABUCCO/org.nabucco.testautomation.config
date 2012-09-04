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

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.testautomation.config.facade.service.engine.TestEngineService;
import org.nabucco.testautomation.config.facade.service.maintain.MaintainConfig;
import org.nabucco.testautomation.config.facade.service.produce.ProduceConfig;
import org.nabucco.testautomation.config.facade.service.report.ReportConfig;
import org.nabucco.testautomation.config.facade.service.resolve.ResolveConfig;
import org.nabucco.testautomation.config.facade.service.search.SearchConfig;

/**
 * ConfigComponent<p/>Component for testautomation config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-16
 */
public interface ConfigComponent extends Component {

    final String COMPONENT_NAME = "org.nabucco.testautomation.config";

    final String COMPONENT_PREFIX = "conf";

    final String JNDI_NAME = ((((JNDI_PREFIX + "/") + COMPONENT_NAME) + "/") + "org.nabucco.testautomation.config.facade.component.ConfigComponent");

    /**
     * Getter for the MaintainConfig.
     *
     * @return the MaintainConfig.
     * @throws ServiceException
     */
    MaintainConfig getMaintainConfig() throws ServiceException;

    /**
     * Getter for the ProduceConfig.
     *
     * @return the ProduceConfig.
     * @throws ServiceException
     */
    ProduceConfig getProduceConfig() throws ServiceException;

    /**
     * Getter for the SearchConfig.
     *
     * @return the SearchConfig.
     * @throws ServiceException
     */
    SearchConfig getSearchConfig() throws ServiceException;

    /**
     * Getter for the ResolveConfig.
     *
     * @return the ResolveConfig.
     * @throws ServiceException
     */
    ResolveConfig getResolveConfig() throws ServiceException;

    /**
     * Getter for the ReportConfig.
     *
     * @return the ReportConfig.
     * @throws ServiceException
     */
    ReportConfig getReportConfig() throws ServiceException;

    /**
     * Getter for the TestEngineService.
     *
     * @return the TestEngineService.
     * @throws ServiceException
     */
    TestEngineService getTestEngineService() throws ServiceException;
}
