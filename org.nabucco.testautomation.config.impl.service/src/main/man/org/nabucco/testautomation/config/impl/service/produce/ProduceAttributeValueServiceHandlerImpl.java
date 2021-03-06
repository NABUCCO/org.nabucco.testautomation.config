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
package org.nabucco.testautomation.config.impl.service.produce;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.config.facade.datatype.attribute.CodeAttributeValue;
import org.nabucco.testautomation.config.facade.datatype.attribute.TextAttributeValue;
import org.nabucco.testautomation.config.facade.message.AttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.ProduceAttributeValueMsg;
import org.nabucco.testautomation.schema.facade.datatype.attribute.Attribute;

/**
 * ProduceAttributeValueServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ProduceAttributeValueServiceHandlerImpl extends ProduceAttributeValueServiceHandler {

	private static final long serialVersionUID = 1L;

	@Override
	protected AttributeValueMsg produceAttributeValue(
			ProduceAttributeValueMsg msg) throws ProduceException {
		
		AttributeValueMsg rs = new AttributeValueMsg();
		Attribute attribute = msg.getAttribute();
		AttributeValue value = null; 
		
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
		rs.setAttributeValue(value);
		return rs;
	}

}
