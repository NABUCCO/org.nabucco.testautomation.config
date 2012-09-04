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
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.config.facade.message.AttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.ProduceAttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.ProduceTestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceConfig;
import org.nabucco.testautomation.result.facade.datatype.ExecutionType;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.schema.facade.datatype.attribute.Attribute;

/**
 * ProduceTestConfigElementServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ProduceTestConfigElementServiceHandlerImpl extends ProduceTestConfigElementServiceHandler {

    private static final long serialVersionUID = 1L;

    private ProduceConfig produceConfig = null;

    @Override
    public TestConfigElementMsg produceTestConfigElement(ProduceTestConfigElementMsg msg) throws ProduceException {

        SchemaElement schemaElement = msg.getSchemaElement();

        if (schemaElement == null) {
            throw new ProduceException("No SchemaElement defined in produce message");
        }

        TestConfigElementMsg rs = new TestConfigElementMsg();
        TestConfigElement testConfigElement = new TestConfigElement();
        testConfigElement.setDatatypeState(DatatypeState.INITIALIZED);
        testConfigElement.setIdentificationKey("");
        testConfigElement.setSchemaElement(schemaElement);
        testConfigElement.setReused(Boolean.FALSE);
        testConfigElement.setSkip(Boolean.FALSE);
        testConfigElement.setExecutionType(ExecutionType.AUTOMATED);

        if (schemaElement.getName() != null) {
            testConfigElement.setName(schemaElement.getName());
        }

        // Preset AttributeValues
        if (!schemaElement.getAttributeList().isEmpty()) {

            if (produceConfig == null) {
                try {
                    produceConfig = ConfigComponentLocator.getInstance().getComponent().getProduceConfig();
                } catch (Exception e) {
                    throw new ProduceException("Could not initialize service ProduceAttributeValue");
                }
            }

            for (Attribute attribute : schemaElement.getAttributeList()) {
                ServiceRequest<ProduceAttributeValueMsg> rq = new ServiceRequest<ProduceAttributeValueMsg>(getContext());
                ProduceAttributeValueMsg produceMsg = new ProduceAttributeValueMsg();
                produceMsg.setAttribute(attribute);
                rq.setRequestMessage(produceMsg);
                AttributeValueMsg produceRs = produceConfig.produceAttributeValue(rq).getResponseMessage();

                if (produceRs != null && produceRs.getAttributeValue() != null) {
                    AttributeValue value = produceRs.getAttributeValue();

                    if (value != null) {
                        testConfigElement.getAttributeValueList().add(value);
                    }
                }
            }
        }

        rs.setTestConfigElement(testConfigElement);
        return rs;
    }

}
