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
package org.nabucco.testautomation.config.service;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.test.RuntimeTestSupport;
import org.nabucco.testautomation.config.facade.component.ConfigComponent;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;

import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;

/**
 * TestCloneProduce
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public class TestCloneProduce extends RuntimeTestSupport {

    private ConfigComponent component;

    @Before
    public void setUp() throws Exception {
        component = getComponent(ConfigComponentLocator.getInstance());

    }

    @Test
    public void testClone() throws Exception {
        final ServiceRequest<TestConfigElementSearchMsg> rq = new ServiceRequest<TestConfigElementSearchMsg>(
                getServiceContext());
        final TestConfigElementSearchMsg requestMessage = new TestConfigElementSearchMsg();
        rq.setRequestMessage(requestMessage);
        final List<TestConfigElement> testConfigElementList = component
                .getSearchTestConfigElement().searchTestConfigElement(rq).getResponseMessage()
                .getTestConfigElementList();
        final Iterator<TestConfigElement> iterator = testConfigElementList.iterator();
        while (iterator.hasNext()) {
            final TestConfigElement next = iterator.next();
            final ServiceRequest<TestConfigElementMsg> cloneRq = new ServiceRequest<TestConfigElementMsg>(
                    getServiceContext());
            final TestConfigElementMsg cloneRequestMessage = new TestConfigElementMsg();
            cloneRequestMessage.setTestConfigElement(next);
            cloneRq.setRequestMessage(cloneRequestMessage);
            final TestConfigElementMsg responseMessage = component.getProduceTestConfigElement()
                    .produceTestConfigElementClone(cloneRq).getResponseMessage();
            final TestConfigElement clonedConfigElement = responseMessage
                    .getTestConfigElement();
            Assert.assertEquals(null, clonedConfigElement.getId());
            Assert.assertEquals(DatatypeState.INITIALIZED, clonedConfigElement.getDatatypeState());

            final PropertyList propertyList = clonedConfigElement.getPropertyList();
            if (propertyList != null) {
                for (final PropertyContainer current : propertyList.getPropertyList()) {
                    Assert.assertNull(current.getProperty().getId());
                    Assert.assertNull(current.getVersion());
                    Assert.assertEquals(DatatypeState.INITIALIZED, current.getDatatypeState());
                }
            }
        }
    }
}
