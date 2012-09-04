/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.testautomation.config.impl.service.search;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.property.facade.service.search.WildcardSupport;

/**
 * SearchTestConfigurationServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class SearchTestConfigurationServiceHandlerImpl extends SearchTestConfigurationServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    public TestConfigurationListMsg searchTestConfiguration(TestConfigurationSearchMsg msg) throws SearchException {

        StringBuilder queryString = new StringBuilder();
        queryString.append("FROM TestConfiguration c");

        List<String> filter = new ArrayList<String>();

        if (msg.getOwner() != null && msg.getOwner().getValue() != null) {
            filter.add("c.owner = :owner");
        }

        if (msg.getName() != null && msg.getName().getValue() != null) {
            filter.add("c.name LIKE '" + WildcardSupport.parse(msg.getName().getValue()) + "'");
        }

        if (msg.getTestConfigKey() != null && msg.getTestConfigKey().getValue() != null) {
            filter.add("c.identificationKey LIKE '" + WildcardSupport.parse(msg.getTestConfigKey().getValue()) + "'");
        }

        if (msg.getDescription() != null && msg.getDescription().getValue() != null) {
            filter.add("c.description LIKE '" + WildcardSupport.parse(msg.getDescription().getValue()) + "'");
        }

        // append filter criteria
        int filterSize = filter.size();

        if (filterSize > 0) {
            queryString.append(" WHERE ");
            int c = 1;
            for (String str : filter) {
                queryString.append(str);

                if (c++ < filterSize) {
                    queryString.append(" AND ");
                }
            }
        }
        queryString.append(" ORDER BY c.name");

        try {
            NabuccoQuery<TestConfiguration> query = super.getPersistenceManager().createQuery(queryString.toString());

            if (msg.getOwner() != null && msg.getOwner().getValue() != null) {
                query.setParameter("owner", msg.getOwner());
            }

            List<TestConfiguration> resultList = query.getResultList();
            TestConfigurationListMsg rs = new TestConfigurationListMsg();
            super.getPersistenceManager().clear();

            for (TestConfiguration testConfiguration : resultList) {
                rs.getTestConfigList().add(testConfiguration);
            }

            return rs;
        } catch (PersistenceException e) {
            throw new SearchException(e);
        }
    }

}
