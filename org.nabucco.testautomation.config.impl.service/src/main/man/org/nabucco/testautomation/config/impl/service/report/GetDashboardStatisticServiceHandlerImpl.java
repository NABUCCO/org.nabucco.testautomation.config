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
package org.nabucco.testautomation.config.impl.service.report;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.testautomation.config.facade.message.DashboardStatisticMsg;
import org.nabucco.testautomation.config.impl.service.SchemaSupport;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * GetDashboardStatisticServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class GetDashboardStatisticServiceHandlerImpl extends GetDashboardStatisticServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected DashboardStatisticMsg getDashboardStatistic(EmptyServiceMessage msg) throws SearchException {

        try {
            NabuccoQuery<Long> query1 = super.getPersistenceManager().createQuery(
                    "SELECT count(c) FROM TestConfiguration c");

            Long result = query1.getSingleResult();
            DashboardStatisticMsg rs = new DashboardStatisticMsg();
            rs.setNumTestConfigurations(new Number(result.intValue()));

            NabuccoQuery<Object[]> query2 = super.getPersistenceManager().createQuery(
                    "SELECT e.schemaElementRefId, count(e) FROM TestConfigElement e GROUP BY e.schemaElementRefId");

            List<Object[]> resultList = query2.getResultList();

            for (Object[] longs : resultList) {
                SchemaElement schemaElement = SchemaSupport.getInstance().getSchemaElement((Long) longs[0],
                        getContext());

                if (schemaElement == null || longs.length != 2 || longs[0] == null || longs[1] == null) {
                    continue;
                }

                switch (schemaElement.getLevel()) {
                case ONE:
                    rs.setNumLevelOne(new Number(((Long) longs[1]).intValue()));
                    rs.setNameLevelOne(schemaElement.getName());
                    break;
                case TWO:
                    rs.setNumLevelTwo(new Number(((Long) longs[1]).intValue()));
                    rs.setNameLevelTwo(schemaElement.getName());
                    break;
                case THREE:
                    rs.setNumLevelThree(new Number(((Long) longs[1]).intValue()));
                    rs.setNameLevelThree(schemaElement.getName());
                    break;
                case FOUR:
                    rs.setNumLevelFour(new Number(((Long) longs[1]).intValue()));
                    rs.setNameLevelFour(schemaElement.getName());
                    break;
                case FIVE:
                    rs.setNumLevelFive(new Number(((Long) longs[1]).intValue()));
                    rs.setNameLevelFive(schemaElement.getName());
                    break;
                }
            }

            return rs;
        } catch (PersistenceException e) {
            throw new SearchException(e);
        }
    }

}
