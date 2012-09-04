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
package org.nabucco.testautomation.config.impl.service;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.config.facade.datatype.attribute.CodeAttributeValue;
import org.nabucco.testautomation.config.facade.datatype.attribute.TextAttributeValue;
import org.nabucco.testautomation.config.impl.service.resolve.Attribute2ElementLink;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.schema.facade.datatype.attribute.Attribute;

/**
 * AttributeSupport
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class AttributeSupport {

    private List<Attribute2ElementLink> linkList = new ArrayList<Attribute2ElementLink>();

    public List<Attribute2ElementLink> getAttribute2ElementLinkList() {
        return this.linkList;
    }

    /**
     * Resolves the AttrbiuteValue-list of the given TestConfigElement.
     * 
     * @param element the element to be resolved
     * @throws ProduceException thrown, if a new AttributeValue cannot be produced
     */
    public void resolveAttributes(TestConfigElement element) throws ProduceException {

        SchemaElement schema = element.getSchemaElement();
        element.getAttributeValueList().size();

        for (Attribute attribute : schema.getAttributeList()) {
            AttributeValue value = null;

            for (AttributeValue tmp : element.getAttributeValueList()) {
                if (attribute.getId() != null && attribute.getId().equals(tmp.getAttributeRefId())) {
                    tmp.setAttribute(attribute);
                    value = tmp;
                    break;
                }
            }

            // No AttributeValue found for Attribute
            if (value == null) {
                addAttributeValue(attribute, element);
            }
        }
    }

    private void addAttributeValue(Attribute attribute, TestConfigElement element) throws ProduceException {

        AttributeValue value = produceAttributeValue(attribute);

        if (element != null && value != null) {
            this.linkList.add(new Attribute2ElementLink(element, value));
        }
    }

    private AttributeValue produceAttributeValue(Attribute attribute)
            throws ProduceException {

        AttributeValue value;
        switch (attribute.getType()) {
        case CODE:
            value = new CodeAttributeValue();
            break;
        case STRING:
            value = new TextAttributeValue();
            break;
        default:
            throw new ProduceException("Unsupported AttributeType: " + attribute.getType());
        }

        value.setDatatypeState(DatatypeState.INITIALIZED);
        value.setAttribute(attribute);
        return value;
    }

}
