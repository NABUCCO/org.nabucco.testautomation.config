/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
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

import javax.persistence.Query;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.impl.service.search.QuerySupport;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.message.TestConfigElementListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.impl.service.DynamicCodeSupport;
import org.nabucco.testautomation.config.impl.service.SchemaSupport;
import org.nabucco.testautomation.facade.datatype.base.HierarchyLevelType;
import org.nabucco.testautomation.schema.facade.component.SchemaComponentLocator;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.schema.facade.message.SchemaElementListMsg;
import org.nabucco.testautomation.schema.facade.message.SchemaElementSearchMsg;
import org.nabucco.testautomation.schema.facade.service.search.SearchSchemaElement;

/**
 * SearchTestConfigElementServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class SearchTestConfigElementServiceHandlerImpl extends SearchTestConfigElementServiceHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	public TestConfigElementListMsg searchTestConfigElement(
			TestConfigElementSearchMsg msg) throws SearchException {

		StringBuilder queryString = new StringBuilder();
		queryString.append("select e from TestConfigElement e");

		List<String> filter = new ArrayList<String>();

		// filter for Id
		if (msg.getId() != null && msg.getId().getValue() != null) {
			filter.add("e.id = :id");
		}

		// filter for Name
		if (msg.getName() != null && msg.getName().getValue() != null) {
			filter.add("e.name LIKE '" + msg.getName().getValue() + "%'");
		}

		// filter for Description
		if (msg.getDescription() != null && msg.getDescription().getValue() != null) {
			filter.add("e.description LIKE :description");
		}
		
		// filter for SchemaElement
		if (msg.getSchemaElement() != null && msg.getSchemaElement().getId() != null) {
			SchemaElement schemaElement = msg.getSchemaElement();
			filter.add("e.schemaElementRefId = " + schemaElement.getId());
		}

		// filter for Level
		if (msg.getLevel() != null) {
			List<SchemaElement> schemaElements = getSchemaElements(msg
					.getLevel());

			if (schemaElements != null && !schemaElements.isEmpty()) {
				StringBuilder schemaFilter = new StringBuilder();
				schemaFilter.append("(");
				int size = schemaElements.size();
				int c = 0;

				for (SchemaElement schemaElement : schemaElements) {
					schemaFilter.append("e.schemaElementRefId = "
							+ schemaElement.getId());

					if (++c < size) {
						schemaFilter.append(" OR ");
					}
				}
				schemaFilter.append(")");
				filter.add(schemaFilter.toString());
			}
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
		queryString.append(" order by e.name");

		Query query = super.getEntityManager().createQuery(
				queryString.toString());

		if (msg.getId() != null && msg.getId().getValue() != null) {
			query.setParameter("id", msg.getId().getValue());
		}
		
		if (msg.getDescription() != null && msg.getDescription().getValue() != null) {
			query.setParameter("description", QuerySupport.searchParameter(msg.getDescription()));
		}

		@SuppressWarnings("unchecked")
		List<TestConfigElement> resultList = query.getResultList();
		TestConfigElementListMsg rs = new TestConfigElementListMsg();

		// get the actual DynamicCode instances from the DynamicCode component via the RefIds
        resolveDynamicCodes(resultList);

        for (TestConfigElement testConfigElement : resultList) {
        	
        	if (msg.getSchemaElement() != null) {
        		testConfigElement.setSchemaElement(msg.getSchemaElement());
        	} else {
        		try {
        			SchemaSupport.getInstance().resolveSchemaElement(testConfigElement, super.getContext());
        		} catch (Exception e) {
        			super.getLogger().error(e, "Could not resolve SchemaElement for TestConfigElement '" 
        					+ testConfigElement.getName().getValue() + "' (", testConfigElement.getId() + ")");
        		}
        	}
        	
        	testConfigElement.setDatatypeState(DatatypeState.PERSISTENT);
        	rs.getTestConfigElementList().add(testConfigElement);
        }
		return rs;
	}
	
	private List<SchemaElement> getSchemaElements(HierarchyLevelType level) {

		try {
			SearchSchemaElement schemaService = SchemaComponentLocator
					.getInstance().getComponent().getSearchSchemaElement();
			ServiceRequest<SchemaElementSearchMsg> msg = new ServiceRequest<SchemaElementSearchMsg>(
					this.getContext());
			SchemaElementSearchMsg rq = new SchemaElementSearchMsg();
			rq.setLevel(level);
			msg.setRequestMessage(rq);
			SchemaElementListMsg rs = schemaService.searchSchemaElement(msg)
					.getResponseMessage();
			return rs.getSchemaElementList();
		} catch (ServiceException ex) {
			getLogger().error(ex);
		} catch (ConnectionException ex) {
			getLogger().error(ex);
		}
		return null;
	}
	
	private void resolveDynamicCodes(List<TestConfigElement> list) throws SearchException {

        try {
            for (TestConfigElement element : list) {
                Long dynamicCodeCodeId;
                Code code;

                // resolve brandType
                dynamicCodeCodeId = element.getBrandTypeRefId();

                if (dynamicCodeCodeId != null) {
                    code = DynamicCodeSupport.getInstance().searchDynamicCode(dynamicCodeCodeId, this.getContext());
                    element.setBrandType(code);
                }
            }
        } catch (Exception e) {
            throw new SearchException(e);
        }
    }

}