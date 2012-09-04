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

/**
 * ConfigComponentJndiNames<p/>Component for testautomation config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-16
 */
public interface ConfigComponentJndiNames {

    final String COMPONENT_RELATION_SERVICE_LOCAL = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.component.ComponentRelationService/local";

    final String COMPONENT_RELATION_SERVICE_REMOTE = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.component.ComponentRelationService/remote";

    final String QUERY_FILTER_SERVICE_LOCAL = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.component.QueryFilterService/local";

    final String QUERY_FILTER_SERVICE_REMOTE = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.component.QueryFilterService/remote";

    final String MAINTAIN_CONFIG_LOCAL = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.maintain.MaintainConfig/local";

    final String MAINTAIN_CONFIG_REMOTE = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.maintain.MaintainConfig/remote";

    final String PRODUCE_CONFIG_LOCAL = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.produce.ProduceConfig/local";

    final String PRODUCE_CONFIG_REMOTE = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.produce.ProduceConfig/remote";

    final String SEARCH_CONFIG_LOCAL = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.search.SearchConfig/local";

    final String SEARCH_CONFIG_REMOTE = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.search.SearchConfig/remote";

    final String RESOLVE_CONFIG_LOCAL = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.resolve.ResolveConfig/local";

    final String RESOLVE_CONFIG_REMOTE = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.resolve.ResolveConfig/remote";

    final String REPORT_CONFIG_LOCAL = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.report.ReportConfig/local";

    final String REPORT_CONFIG_REMOTE = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.report.ReportConfig/remote";

    final String TEST_ENGINE_SERVICE_LOCAL = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.engine.TestEngineService/local";

    final String TEST_ENGINE_SERVICE_REMOTE = "nabucco/org.nabucco.testautomation.config/org.nabucco.testautomation.config.facade.service.engine.TestEngineService/remote";
}
