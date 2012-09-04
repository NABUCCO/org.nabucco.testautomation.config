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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.xml;

import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.property.facade.datatype.BooleanProperty;
import org.nabucco.testautomation.property.facade.datatype.DateProperty;
import org.nabucco.testautomation.property.facade.datatype.NumericProperty;
import org.nabucco.testautomation.property.facade.datatype.TextProperty;
import org.nabucco.testautomation.property.facade.datatype.XPathProperty;
import org.nabucco.testautomation.property.facade.datatype.XmlProperty;
import org.nabucco.testautomation.property.facade.datatype.base.Property;

/**
 * XmlFactory
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class XmlFactory {

    public static String createStartTag(TestConfiguration testConfiguration) {
        String startTag = "TestConfiguration name=\""
                + testConfiguration.getName().getValue() + "\"";
        return startTag;
    }

    public static String createEndTag(TestConfiguration testConfiguration) {
        String endTag = "TestConfiguration";
        return endTag;
    }

    public static String createContent(TestConfiguration testConfiguration) {
        return null;
    }

    public static String createStartTag(TestConfigElement testConfigElement) {
        String startTag = ""
                + testConfigElement.getSchemaElement().getName().getValue() + " name=\""
                + testConfigElement.getName().getValue() + "\"";
        return startTag;
    }

    public static String createEndTag(TestConfigElement testConfigElement) {
        String endTag = testConfigElement.getSchemaElement().getName().getValue() + "";
        return endTag;
    }

    public static String createContent(TestConfigElement testConfigElement) {
        return null;
    }

    public static String createStartTag(Property property) {
        String startTag = "Property name=\""
                + property.getName().getValue() + "\" type=\"" + property.getType() + "\"";
        return startTag;
    }

    public static String createEndTag(Property property) {
        String endTag = "Property";
        return endTag;
    }

    public static String createContent(Property property) {

        switch (property.getType()) {
        case BOOLEAN:
            return ((BooleanProperty) property).getValue().getValue().toString();
        case DATE:
            return ((DateProperty) property).getValue().getValue().toString();
        case NUMERIC:
            return ((NumericProperty) property).getValue().getValue().toString();
        case TEXT:
            return ((TextProperty) property).getValue().getValue().toString();
        case XPATH:
            return ((XPathProperty) property).getValue().getValue().toString();
        case XML:
            return "<![CDATA[" + ((XmlProperty) property).getValue() + "]]>";
        }
        return null;
    }

}
