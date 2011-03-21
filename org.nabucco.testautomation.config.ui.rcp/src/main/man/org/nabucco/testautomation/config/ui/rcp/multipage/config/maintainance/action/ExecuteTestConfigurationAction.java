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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.action;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.ui.rcp.communication.ConfigComponentServiceDelegateFactory;
import org.nabucco.testautomation.config.ui.rcp.communication.engine.TestEngineServiceDelegate;

/**
 * ExecuteTestConfigurationAction
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class ExecuteTestConfigurationAction {

    private TestConfiguration testConfiguration;

    public ExecuteTestConfigurationAction(TestConfiguration testConfiguration) {
        this.testConfiguration = testConfiguration;
    }

    public void run() {
        TestEngineServiceDelegate testEngineService;
        try {
            testEngineService = ConfigComponentServiceDelegateFactory.getInstance()
                    .getTestEngineService();
            TestExecutionMsg rq = new TestExecutionMsg();
            rq.setTestConfiguration(testConfiguration);
            testEngineService.executeTestConfiguration(rq);
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
        }
    }

}
