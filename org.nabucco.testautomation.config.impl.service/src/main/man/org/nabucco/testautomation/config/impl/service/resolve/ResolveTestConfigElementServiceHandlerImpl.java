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
package org.nabucco.testautomation.config.impl.service.resolve;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.maintain.PersistenceCleaner;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.comparator.TestConfigElementSorter;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.impl.service.AttributeSupport;
import org.nabucco.testautomation.config.impl.service.PropertySupport;
import org.nabucco.testautomation.config.impl.service.SchemaSupport;
import org.nabucco.testautomation.config.impl.service.ScriptSupport;

/**
 * ResolveTestConfigElementServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ResolveTestConfigElementServiceHandlerImpl extends ResolveTestConfigElementServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final TestConfigElementSorter elementSorter = new TestConfigElementSorter();

    private AttributeSupport attributeSupport;

    private PropertySupport propertySupport;

    @Override
    public TestConfigElementMsg resolveTestConfigElement(TestConfigElementSearchMsg msg) throws ResolveException {

        if (msg.getId() == null || msg.getId().getValue() == null) {
            throw new ResolveException("Mandatory Identifier is null");
        }

        try {
            String queryString = "FROM TestConfigElement c WHERE c.id = :id";
            NabuccoQuery<TestConfigElement> query = super.getPersistenceManager().createQuery(queryString.toString());
            query.setParameter("id", msg.getId().getValue());
            TestConfigElement result = (TestConfigElement) query.getSingleResult();

            if (result == null) {
                throw new ResolveException("TestConfigElement with id '" + msg.getId() + "' not found");
            }

            boolean loadTestScripts = false;
            Flag loadTestScriptsFlag = msg.getLoadTestScripts();

            if (loadTestScriptsFlag != null
                    && loadTestScriptsFlag.getValue() != null && loadTestScriptsFlag.getValue().booleanValue()) {
                loadTestScripts = true;
            }

            this.attributeSupport = new AttributeSupport();
            this.propertySupport = new PropertySupport(getContext());
            resolve(result, loadTestScripts);

            // Detach Entity
            this.getPersistenceManager().clear();
            result.accept(new PersistenceCleaner());

            // Add new AttributeValues if required
            for (Attribute2ElementLink link : this.attributeSupport.getAttribute2ElementLinkList()) {
                TestConfigElement element = link.getElement();
                element.getAttributeValueList().add(link.getAttributeValue());
                element.setDatatypeState(DatatypeState.MODIFIED);
            }

            // Sort
            elementSorter.sort(result);

            TestConfigElementMsg rs = new TestConfigElementMsg();
            rs.setTestConfigElement(result);
            return rs;
        } catch (VisitorException e) {
            throw new ResolveException(e);
        } catch (PersistenceException e) {
            throw new ResolveException(e);
        } catch (ServiceException e) {
            throw new ResolveException(e);
        } finally {
            this.attributeSupport = null;
            this.propertySupport = null;
        }
    }

    private void resolve(TestConfigElement element, boolean loadTestScripts) throws ResolveException {

        try {
            element.setSchemaElement(SchemaSupport.getInstance().getSchemaElement(element.getSchemaElementRefId(),
                    super.getContext()));
        } catch (Exception e) {
            super.getLogger().error(e,
                    "Could not resolve SchemaElement for TestConfigElement '" + element.getName() + "' [",
                    element.getId() + "]");
            throw new ResolveException(e);
        }
        try {
            this.propertySupport.resolveProperties(element);
        } catch (Exception e) {
            super.getLogger().error(e,
                    "Could not resolve Properties for TestConfigElement '" + element.getName() + "' [",
                    element.getId() + "]");
            throw new ResolveException(e);
        }

        // Resolve Attributes
        try {
            this.attributeSupport.resolveAttributes(element);
        } catch (Exception e) {
            super.getLogger().error(e,
                    "Could not resolve Attributes for TestConfigElement '" + element.getName() + "' [",
                    element.getId() + "]");
            throw new ResolveException(e);
        }

        // Resolve Dependencies
        element.getDependencyList().size();

        // Resolve TestScripts
        try {
            ScriptSupport.getInstance().resolveTestScripts(element, loadTestScripts, super.getContext());
        } catch (Exception e) {
            super.getLogger().error(e,
                    "Could not resolve TestScripts for TestConfigElement '" + element.getName() + "' [",
                    element.getId() + "]");
            throw new ResolveException(e);
        }

        // Resolve children
        for (TestConfigElementContainer child : element.getTestConfigElementList()) {
            resolve(child.getElement(), loadTestScripts);
        }
    }

}
