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

import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;


/**
 * Attribute2ElementLink
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class Attribute2ElementLink {

    private TestConfigElement element;
    
    private AttributeValue attributeValue;

    public Attribute2ElementLink(TestConfigElement element, AttributeValue attributeValue) {
        super();
        this.element = element;
        this.attributeValue = attributeValue;
    }

    /**
     * @return the element
     */
    public TestConfigElement getElement() {
        return element;
    }

    /**
     * @return the attributeValue
     */
    public AttributeValue getAttributeValue() {
        return attributeValue;
    }
    
}
