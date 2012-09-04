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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.schema.facade.component.SchemaComponentLocator;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.schema.facade.message.SchemaElementSearchMsg;

/**
 * SearchReferencingTestConfigurationsServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class SearchReferencingTestConfigurationsServiceHandlerImpl extends
        SearchReferencingTestConfigurationsServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected TestConfigurationListMsg searchReferencingTestConfigurations(TestConfigElementMsg msg)
            throws SearchException {

        TestConfigElement testConfigElement = msg.getTestConfigElement();

        if (testConfigElement == null || testConfigElement.getId() == null) {
            throw new SearchException("No or invalid TestConfigElement");
        }

        SchemaElement schemaElement = testConfigElement.getSchemaElement();

        // SchemaElement with HierarchyLevelType is mandatory for this search !
        if (schemaElement == null || schemaElement.getLevel() == null) {
            schemaElement = resolveSchemaElement(testConfigElement);
        }

        String baseQuery = "SELECT * FROM conf_test_configuration c LEFT JOIN (conf_test_config_element_container d, conf_test_config_element e) ON (c.id=d.test_configuration_test_config_element_list_id AND d.element_id=e.id) ";
        String subQuery = "WHERE element_id IN (SELECT test_config_element_test_config_element_list_id FROM conf_test_config_element_container ";
        String elementId = "WHERE element_id = :id";

        StringBuilder queryString = new StringBuilder(baseQuery);
        int numSubQueries = 0;

        switch (schemaElement.getLevel()) {
        case ONE:
            numSubQueries = 0;
            break;
        case TWO:
            numSubQueries = 1;
            break;
        case THREE:
            numSubQueries = 2;
            break;
        case FOUR:
            numSubQueries = 3;
            break;
        case FIVE:
            numSubQueries = 4;
            break;
        }

        for (int i = 0; i < numSubQueries; i++) {
            queryString.append(subQuery);
        }

        queryString.append(elementId);

        for (int i = 0; i < numSubQueries; i++) {
            queryString.append(")");
        }

        try {
            NabuccoQuery<TestConfiguration> query = super.getPersistenceManager().createNativeQuery(
                    queryString.toString(), TestConfiguration.class);
            query.setParameter("id", testConfigElement.getId());

            List<TestConfiguration> resultList = query.getResultList();
            TestConfigurationListMsg rs = new TestConfigurationListMsg();

            rs.getTestConfigList().addAll(resultList);
            return rs;
        } catch (PersistenceException e) {
            throw new SearchException(e);
        }
    }

    /**
     * @param testConfigElement
     * @throws SearchException
     */
    private SchemaElement resolveSchemaElement(TestConfigElement testConfigElement) throws SearchException {
        try {
            ServiceRequest<SchemaElementSearchMsg> schemaRq = new ServiceRequest<SchemaElementSearchMsg>(getContext());
            SchemaElementSearchMsg schemaMsg = new SchemaElementSearchMsg();
            schemaMsg.setId(new Identifier(testConfigElement.getSchemaElementRefId()));
            schemaRq.setRequestMessage(schemaMsg);
            return SchemaComponentLocator.getInstance().getComponent().getSearchSchema().searchSchemaElement(schemaRq)
                    .getResponseMessage().getSchemaElementList().get(0);
        } catch (Exception e) {
            throw new SearchException("Could not resolve SchemaElement for TestConfigElement '"
                    + testConfigElement.getIdentificationKey() + "'");
        }
    }

}
