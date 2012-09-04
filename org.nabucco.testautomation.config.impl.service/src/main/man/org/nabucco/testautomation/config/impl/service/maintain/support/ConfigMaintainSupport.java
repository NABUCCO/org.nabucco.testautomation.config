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
package org.nabucco.testautomation.config.impl.service.maintain.support;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;

/**
 * ConfigMaintainSupport
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ConfigMaintainSupport {

    private PersistenceManager persistenceManager;

    public ConfigMaintainSupport(PersistenceManager persistenceManager) throws PersistenceException {
        this.persistenceManager = persistenceManager;
        this.persistenceManager.reset();
    }

    public <T extends NabuccoDatatype> T maintain(T datatype) throws MaintainException {

        try {
            datatype = persistenceManager.persist(datatype);
        } catch (PersistenceException pe) {
            throw new MaintainException("Error maintaining "
                    + datatype.getClass().getSimpleName()
                    + "!", pe);
        }

        return datatype;
    }

    public <T extends NabuccoDatatype> List<T> maintainList(List<T> datatypeList)
            throws MaintainException {

        for (T datatype : datatypeList) {
            T maintain = maintain(datatype);
            datatypeList.set(datatypeList.indexOf(datatype), maintain);
        }

        return datatypeList;
    }

}
