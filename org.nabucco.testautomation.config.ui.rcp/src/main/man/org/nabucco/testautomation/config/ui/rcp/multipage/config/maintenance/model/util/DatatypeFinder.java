/*
 * Copyright 2012 PRODYNA AG
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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.util;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;

/**
 * Find a datatype of a given type and id in the master detail tree. When the given datatype exists
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeFinder<D extends NabuccoDatatype> extends DatatypeVisitor {

    private final Class<D> type;

    private final Long id;

    /** The resulting datatype. */
    private D datatype;

    /**
     * Creates a new {@link DatatypeFinder} instance.
     * 
     * @param datype
     *            another instance of the datatype to find
     */
    @SuppressWarnings("unchecked")
    public DatatypeFinder(D datatype) {
        this(datatype != null ? (Class<D>) datatype.getClass() : null, datatype != null ? datatype.getId() : null);
    }

    /**
     * Creates a new {@link DatatypeFinder} instance.
     * 
     * @param type
     *            the datatype class
     * @param id
     *            the datatype id
     */
    public DatatypeFinder(Class<D> type, Identifier id) {
        this(type, id != null ? id.getValue() : null);
    }

    /**
     * Creates a new {@link DatatypeFinder} instance.
     * 
     * @param type
     *            the datatype class
     * @param id
     *            the datatype id
     */
    public DatatypeFinder(Class<D> type, Long id) {
        if (type == null) {
            throw new IllegalArgumentException("Cannot search datatype of type 'null'.");
        }
        if (id == null) {
            throw new IllegalArgumentException("Cannot search datatype with id 'null'.");
        }
        this.type = type;
        this.id = id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void visit(Datatype datatype) throws VisitorException {
        if (datatype instanceof NabuccoDatatype) {

            NabuccoDatatype nabuccoDatatype = (NabuccoDatatype) datatype;

            if (this.type.isAssignableFrom(datatype.getClass())) {

                if (this.id.equals(nabuccoDatatype.getId())) {
                    this.datatype = (D) datatype;
                    return;
                }
            }
        }

        super.visit(datatype);
    }

    /**
     * Getter for the datatype.
     * 
     * @return Returns the datatype.
     */
    public Datatype getDatatype() {
        return this.datatype;
    }

    /**
     * Search the datatype starting from the given root datatype.
     * 
     * @param root
     *            the root datatype to start visitation
     * 
     * @return the found datatype, or null if no datatype was found
     * 
     * @throws ClientException
     *             when searching the datatype raises an exception
     */
    public D find(Datatype root) throws ClientException {
        if (root == null) {
            return null;
        }

        try {
            root.accept(this);
        } catch (Exception e) {
            Activator.getDefault().logError(e);
            throw new ClientException("Error searching datatype '"
                    + this.type.getName() + "' with id '" + this.id + "'.", e);
        }

        return this.datatype;
    }
}
