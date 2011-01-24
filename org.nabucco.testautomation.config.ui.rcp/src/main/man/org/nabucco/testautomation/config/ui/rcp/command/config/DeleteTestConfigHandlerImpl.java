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
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.command.close.AbstractDeleteDatatypeHandler;
import org.nabucco.framework.plugin.base.model.BusinessModel;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.command.config.DeleteTestConfigHandler;
import org.nabucco.testautomation.config.ui.rcp.edit.config.model.TestConfigurationEditViewBusinessModel;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.TestConfigurationMaintainanceMultiplePageEditView;


/**
 * DeleteTestConfigCommandHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DeleteTestConfigHandlerImpl extends
        AbstractDeleteDatatypeHandler<TestConfigurationMaintainanceMultiplePageEditView> implements
        DeleteTestConfigHandler {

    @Override
    public void deleteTestConfig() {
        super.run();
    }

    @Override
    public String getId() {
        return TestConfigurationMaintainanceMultiplePageEditView.ID;
    }

    @Override
    protected boolean preClose(TestConfigurationMaintainanceMultiplePageEditView view) {

        TestConfiguration testConfig = view.getModel().getTestConfiguration();

        testConfig.setDatatypeState(DatatypeState.DELETED);

        BusinessModel businessModel = Activator.getDefault().getModel()
                .getBusinessModel(TestConfigurationEditViewBusinessModel.ID);

        try {
            if (businessModel instanceof TestConfigurationEditViewBusinessModel) {
                ((TestConfigurationEditViewBusinessModel) businessModel).delete(testConfig);
            }
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
            return true;
        }

        return super.preClose(view);
    }

}
