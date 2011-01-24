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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.xml;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.plugin.base.component.multipage.xml.DatatypeXMLEditorTextPartCreator;
import org.nabucco.framework.plugin.base.component.multipage.xml.XMLEditorTextPart;
import org.nabucco.framework.plugin.base.component.multipage.xml.XMLEditorTextPartCreatorImpl;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

import org.nabucco.testautomation.facade.datatype.property.base.PropertyComponent;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyComposite;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;

/**
 * WorkflowConditionXmlEditorPageCreator
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationMaintainanceXmlEditorPageCreator extends
        XMLEditorTextPartCreatorImpl<Datatype> {

    @Override
    protected XMLEditorTextPart createXMLNodeWithTypedDatatype(Datatype datatype,
            XMLEditorTextPart parent, DatatypeXMLEditorTextPartCreator builder) {

        XMLEditorTextPart result = null;

        if (datatype instanceof TestConfiguration) {

            TestConfiguration tc = (TestConfiguration) datatype;
            result = new XMLEditorTextPart(0, XmlFactory.createStartTag(tc),
                    XmlFactory.createEndTag(tc), XmlFactory.createContent(tc), null, datatype);

            for (TestConfigElementContainer container : tc.getTestConfigElementList()) {
                result.getChildren().add(builder.create(container.getElement(), result));
            }

        } else if (datatype instanceof TestConfigElement) {

            TestConfigElement tce = (TestConfigElement) datatype;
            result = new XMLEditorTextPart(0, XmlFactory.createStartTag(tce),
                    XmlFactory.createEndTag(tce), XmlFactory.createContent(tce), parent, datatype);

            if (tce.getPropertyList() != null) {
                result.getChildren().add(builder.create(tce.getPropertyList(), result));
            }

            for (TestConfigElementContainer container : tce.getTestConfigElementList()) {
                result.getChildren().add(builder.create(container.getElement(), result));
            }

        } else if (datatype instanceof PropertyComposite) {

            PropertyComposite property = (PropertyComposite) datatype;
            result = new XMLEditorTextPart(0, XmlFactory.createStartTag(property),
                    XmlFactory.createEndTag(property), XmlFactory.createContent(property), parent,
                    property);

            for (PropertyContainer propertyContainer : property.getPropertyList()) {
                result.getChildren().add(builder.create(propertyContainer.getProperty(), result));
            }

        } else if (datatype instanceof PropertyComponent) {

            PropertyComponent property = (PropertyComponent) datatype;
            result = new XMLEditorTextPart(0, XmlFactory.createStartTag(property),
                    XmlFactory.createEndTag(property), XmlFactory.createContent(property), parent,
                    property);
        }

        return result;
    }

}
