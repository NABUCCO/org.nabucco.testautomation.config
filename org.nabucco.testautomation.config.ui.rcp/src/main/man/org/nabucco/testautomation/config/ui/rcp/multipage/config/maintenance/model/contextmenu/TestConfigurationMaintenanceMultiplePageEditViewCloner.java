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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.contextmenu.CloneDatatypeMenuItemCloner;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.ui.rcp.communication.ConfigComponentServiceDelegateFactory;
import org.nabucco.testautomation.config.ui.rcp.communication.produce.ProduceConfigDelegate;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationElementFactory;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.message.PropertyMsg;
import org.nabucco.testautomation.property.ui.rcp.communication.PropertyComponentServiceDelegateFactory;
import org.nabucco.testautomation.property.ui.rcp.communication.produce.ProducePropertyDelegate;

/**
 * TestConfigurationMaintenanceMultiplePageEditViewCloner
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationMaintenanceMultiplePageEditViewCloner implements CloneDatatypeMenuItemCloner {

    private static ProduceConfigDelegate produceConfigDelegate;

    private static ProducePropertyDelegate producePropertyDelegate;

    static {
        try {
            produceConfigDelegate = ConfigComponentServiceDelegateFactory.getInstance().getProduceConfig();
            producePropertyDelegate = PropertyComponentServiceDelegateFactory.getInstance().getProduceProperty();
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
        }
    }

    @Override
    public Datatype clone(Datatype datatype) {

        if (datatype instanceof TestConfigElement) {

            TestConfigElement testConfigElement = (TestConfigElement) datatype;

            try {
                TestConfigElementMsg rq = new TestConfigElementMsg();
                rq.setTestConfigElement(testConfigElement);
                TestConfigElementMsg testConfigElementClone = produceConfigDelegate.produceTestConfigElementClone(rq);
                return testConfigElementClone.getTestConfigElement();
            } catch (ClientException e) {
                Activator.getDefault().logError(e);
            }
        } else if (datatype instanceof PropertyList) {

            PropertyList propertyList = (PropertyList) datatype;
            propertyList = TestConfigurationElementFactory.resolveProperty(propertyList);

            try {
                PropertyMsg rq = new PropertyMsg();
                rq.setProperty(propertyList);
                PropertyMsg rs = producePropertyDelegate.producePropertyClone(rq);
                return rs.getProperty();
            } catch (ClientException e) {
                Activator.getDefault().logError(e);
            }
        } else {
            Activator.getDefault().logError("No cloning implemented for:" + datatype);
        }
        return null;
    }
}
