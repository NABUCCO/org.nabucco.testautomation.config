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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNodeCreatorForAllDatatypes;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNodeCreatorImpl;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyContainer;

/**
 * TestConfigurationMaintenanceMasterDetailTreeNodeCreator
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationMaintenanceMasterDetailTreeNodeCreator extends MasterDetailTreeNodeCreatorImpl<Datatype> {

    /**
     * {@inheritDoc}
     */
    @Override
    public MasterDetailTreeNode createNodeTyped(final Datatype dataType, final MasterDetailTreeNode parent,
            final MasterDetailTreeNodeCreatorForAllDatatypes builder) {
        MasterDetailTreeNode result = new MasterDetailTreeNode(dataType, parent);

        if (dataType instanceof TestConfiguration) {
            TestConfiguration testScript = (TestConfiguration) dataType;

            for (TestConfigElementContainer testConfigElementContainer : testScript.getTestConfigElementList()) {
                MasterDetailTreeNode child = MasterDetailTreeNodeCreatorForAllDatatypes.getInstance().create(
                        testConfigElementContainer.getElement(), result);
                result.getChildren().add(child);
            }
        } else if (dataType instanceof TestConfigElement) {
            TestConfigElement testConfigElementParent = (TestConfigElement) dataType;

            for (TestConfigElementContainer testConfigElementContainer : testConfigElementParent
                    .getTestConfigElementList()) {
                MasterDetailTreeNode child = MasterDetailTreeNodeCreatorForAllDatatypes.getInstance().create(
                        testConfigElementContainer.getElement(), result);
                result.getChildren().add(child);
            }
            // force property list node to index 0
            if (testConfigElementParent.getPropertyList() != null) {
                MasterDetailTreeNode child = MasterDetailTreeNodeCreatorForAllDatatypes.getInstance().create(
                        testConfigElementParent.getPropertyList(), result);
                result.getChildren().add(0, child);
            }
        } else if (dataType instanceof PropertyList) {
            PropertyList propertyList = (PropertyList) dataType;

            for (PropertyContainer prop : propertyList.getPropertyList()) {
                MasterDetailTreeNode child = MasterDetailTreeNodeCreatorForAllDatatypes.getInstance().create(
                        prop.getProperty(), result);
                result.getChildren().add(child);
            }
        }
        // Add Decorators
        TreeNodeDecorator.decorateNode(result, dataType);
        return result;
    }

}
