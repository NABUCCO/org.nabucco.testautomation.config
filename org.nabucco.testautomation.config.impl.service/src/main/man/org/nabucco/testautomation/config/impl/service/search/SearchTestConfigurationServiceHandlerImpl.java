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

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.impl.service.search.QuerySupport;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.impl.service.DynamicCodeSupport;


/**
 * SearchTestConfigurationServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class SearchTestConfigurationServiceHandlerImpl extends SearchTestConfigurationServiceHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	public TestConfigurationListMsg searchTestConfiguration(
			TestConfigurationSearchMsg msg)
			throws SearchException {

		StringBuilder queryString = new StringBuilder();
		queryString.append("select c from TestConfiguration c");

		List<String> filter = new ArrayList<String>();

		if (msg.getOwner() != null && msg.getOwner().getValue() != null) {
			filter.add("c.owner = :owner");
		}

		if (msg.getName() != null && msg.getName().getValue() != null) {
			filter.add("c.name LIKE '" + msg.getName().getValue() + "%'");
		}
		
		if (msg.getDescription() != null && msg.getDescription().getValue() != null) {
			filter.add("c.description LIKE :description");
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
		queryString.append(" order by c.name");

		Query query = super.getEntityManager().createQuery(
				queryString.toString());
		
		if (msg.getOwner() != null && msg.getOwner().getValue() != null) {
			query.setParameter("owner", msg.getOwner());
		}

		if (msg.getDescription() != null && msg.getDescription().getValue() != null) {
			query.setParameter("description", QuerySupport.searchParameter(msg.getDescription()));
		}

		@SuppressWarnings("unchecked")
		List<TestConfiguration> resultList = query.getResultList();
		TestConfigurationListMsg rs = new TestConfigurationListMsg();
		super.getEntityManager().clear();
		
		for (TestConfiguration testConfiguration : resultList) {
			testConfiguration.setDatatypeState(DatatypeState.PERSISTENT);
			rs.getTestConfigList().add(testConfiguration);
		}
		
		// get the actual DynamicCode instances from the DynamicCode component via the RefIds
        resolveDynamicCodes(rs.getTestConfigList());
        
		return rs;
	}
	
    private void resolveDynamicCodes(List<TestConfiguration> list) throws SearchException {

        try {
            for (TestConfiguration element : list) {

                Long dynamicCodeCodeId;
                Code code;

                // resolve environmentType
                dynamicCodeCodeId = element.getEnvironmentTypeRefId();
                if (dynamicCodeCodeId != null) {
                    code = DynamicCodeSupport.getInstance().searchDynamicCode(dynamicCodeCodeId, this.getContext());
                    element.setEnvironmentType(code);
                }
                
                // resolve releaseType
                dynamicCodeCodeId = element.getReleaseTypeRefId();
                if (dynamicCodeCodeId != null) {
                    code = DynamicCodeSupport.getInstance().searchDynamicCode(dynamicCodeCodeId, this.getContext());
                    element.setReleaseType(code);
                }
            }
        } catch (Exception e) {
            throw new SearchException(e);
        }
    }

}
