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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.base.impl.service.maintain.PersistenceCleaner;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.comparator.TestConfigElementSorter;
import org.nabucco.testautomation.config.facade.datatype.comparator.TestScriptSorter;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.impl.service.AttributeSupport;
import org.nabucco.testautomation.config.impl.service.PropertySupport;
import org.nabucco.testautomation.config.impl.service.SchemaSupport;
import org.nabucco.testautomation.config.impl.service.ScriptSupport;
import org.nabucco.testautomation.config.impl.service.maintain.visitor.SchemaElementCollector;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * ResolveTestConfigurationServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ResolveTestConfigurationServiceHandlerImpl extends ResolveTestConfigurationServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final TestConfigElementSorter elementSorter = new TestConfigElementSorter();

    private static final TestScriptSorter scriptSorter = new TestScriptSorter();

    private List<TestConfigElement> elementList;

    private Map<Long, SchemaElement> schemaElementMap;

    private AttributeSupport attributeSupport;

    private PropertySupport propertySupport;

    @Override
    public TestConfigurationListMsg resolveTestConfiguration(TestConfigurationSearchMsg msg) throws ResolveException {

        if (msg.getId() == null || msg.getId().getValue() == null) {
            throw new ResolveException("Mandatory Identifier is null");
        }

        try {
            String queryString = "FROM TestConfiguration c WHERE c.id = :id";
            NabuccoQuery<Object> query = super.getPersistenceManager().createQuery(queryString.toString());
            query.setParameter("id", msg.getId().getValue());
            TestConfiguration result = (TestConfiguration) query.getSingleResult();

            if (result == null) {
                throw new ResolveException("TestConfiguration with id '" + msg.getId() + "' not found");
            }

            boolean loadTestScripts = false;
            Flag loadTestScriptsFlag = msg.getLoadTestScripts();

            if (loadTestScriptsFlag != null
                    && loadTestScriptsFlag.getValue() != null && loadTestScriptsFlag.getValue().booleanValue()) {
                loadTestScripts = true;
            }

            this.elementList = new ArrayList<TestConfigElement>();
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
            elementSorter.sort(result.getTestConfigElementList());

            for (TestConfigElement element : this.elementList) {
                scriptSorter.sort(element.getTestScriptList());
            }

            TestConfigurationListMsg rs = new TestConfigurationListMsg();
            rs.getTestConfigList().add(result);
            return rs;
        } catch (VisitorException e) {
            throw new ResolveException(e);
        } catch (PersistenceException e) {
            throw new ResolveException(e);
        } catch (ServiceException e) {
            throw new ResolveException(e);
        } finally {
            this.elementList = null;
            this.attributeSupport = null;
            this.propertySupport = null;
        }
    }

    private void resolve(TestConfiguration testConfiguration, boolean loadTestScripts) throws ResolveException {

        try {
            SchemaConfig schemaConfig = SchemaSupport.getInstance().getSchemaConfig(
                    testConfiguration.getSchemaConfigRefId(), this.getContext());
            testConfiguration.setSchemaConfig(schemaConfig);
            SchemaElementCollector schemaCollector = new SchemaElementCollector();
            schemaConfig.accept(schemaCollector);
            this.schemaElementMap = schemaCollector.getSchemaElementMap();
        } catch (SearchException e) {
            throw new ResolveException("Could not resolve SchemaConfig for TestConfiguration ["
                    + testConfiguration.getId() + "]", e);
        } catch (VisitorException e) {
            throw new ResolveException("Could not resolve SchemaElements for TestConfiguration ["
                    + testConfiguration.getId() + "]", e);
        }

        // Resolve Children
        for (TestConfigElementContainer child : testConfiguration.getTestConfigElementList()) {
            resolve(child, loadTestScripts);
        }
    }

    private void resolve(TestConfigElementContainer container, boolean loadTestScripts) throws ResolveException {

        TestConfigElement element = container.getElement();
        element.setSchemaElement(this.schemaElementMap.get(element.getSchemaElementRefId()));

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
        if (element.getTestScriptList().size() > 0) {
            this.elementList.add(element);

            try {
                ScriptSupport.getInstance().resolveTestScripts(element, false, super.getContext());
            } catch (Exception e) {
                super.getLogger().error(e,
                        "Could not resolve TestScripts for TestConfigElement '" + element.getName() + "' [",
                        element.getId() + "]");
                throw new ResolveException(e);
            }
        }

        // Resolve children
        for (TestConfigElementContainer child : element.getTestConfigElementList()) {
            resolve(child, loadTestScripts);
        }
    }

}
