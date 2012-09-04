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
package org.nabucco.testautomation.config.facade.service.search;

import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.testautomation.config.facade.message.TestConfigElementListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.property.facade.message.QuickSearchMsg;
import org.nabucco.testautomation.property.facade.message.QuickSearchRs;

/**
 * SearchConfig<p/>Config search service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public interface SearchConfig extends Service {

    /**
     * Missing description at method searchTestConfiguration.
     *
     * @param rq the ServiceRequest<TestConfigurationSearchMsg>.
     * @return the ServiceResponse<TestConfigurationListMsg>.
     * @throws SearchException
     */
    ServiceResponse<TestConfigurationListMsg> searchTestConfiguration(ServiceRequest<TestConfigurationSearchMsg> rq)
            throws SearchException;

    /**
     * Missing description at method searchTestConfigElement.
     *
     * @param rq the ServiceRequest<TestConfigElementSearchMsg>.
     * @return the ServiceResponse<TestConfigElementListMsg>.
     * @throws SearchException
     */
    ServiceResponse<TestConfigElementListMsg> searchTestConfigElement(ServiceRequest<TestConfigElementSearchMsg> rq)
            throws SearchException;

    /**
     * Missing description at method searchReferencingTestConfigurations.
     *
     * @param rq the ServiceRequest<TestConfigElementMsg>.
     * @return the ServiceResponse<TestConfigurationListMsg>.
     * @throws SearchException
     */
    ServiceResponse<TestConfigurationListMsg> searchReferencingTestConfigurations(
            ServiceRequest<TestConfigElementMsg> rq) throws SearchException;

    /**
     * Missing description at method quickSearch.
     *
     * @param rq the ServiceRequest<QuickSearchMsg>.
     * @return the ServiceResponse<QuickSearchRs>.
     * @throws SearchException
     */
    ServiceResponse<QuickSearchRs> quickSearch(ServiceRequest<QuickSearchMsg> rq) throws SearchException;
}
