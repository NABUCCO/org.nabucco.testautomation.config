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
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.command.close.AbstractDeleteDatatypeHandler;
import org.nabucco.framework.plugin.base.model.BusinessModel;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.edit.config.model.TestConfigurationEditViewBusinessModel;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.TestConfigurationMaintenanceMultiPageEditView;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationMaintenanceMultiPageEditViewModel;
import org.nabucco.testautomation.property.facade.service.history.HistoryService;


/**
 * DeleteTestConfigCommandHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DeleteTestConfigHandlerImpl extends
        AbstractDeleteDatatypeHandler<TestConfigurationMaintenanceMultiPageEditView> implements
        DeleteTestConfigHandler {
    
    private static final String DIALOG_TITLE = "org.nabucco.testautomation.delete.dialog.info.title";

    private static final String DIALOG_MESSAGE = "org.nabucco.testautomation.delete.dialog.info.message";
    
    @Override
    public void deleteTestConfig() {
        String viewId = this.getId();
        
        TestConfigurationMaintenanceMultiPageEditViewModel model = super.getView(viewId).getModel();
        
        if(model.isDirty()) {
            Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
            MessageDialog.openWarning(shell, I18N.i18n(DIALOG_TITLE), I18N.i18n(DIALOG_MESSAGE));
            return;
        }
        super.run();
    }

    @Override
    public String getId() {
        return TestConfigurationMaintenanceMultiPageEditView.ID;
    }

    @Override
    protected boolean preClose(TestConfigurationMaintenanceMultiPageEditView view) throws ClientException {

        TestConfiguration testConfig = view.getModel().getTestConfiguration();

        testConfig.setDatatypeState(DatatypeState.DELETED);

        BusinessModel businessModel = Activator.getDefault().getModel()
                .getBusinessModel(TestConfigurationEditViewBusinessModel.ID);

        if (businessModel instanceof TestConfigurationEditViewBusinessModel) {
            ((TestConfigurationEditViewBusinessModel) businessModel).delete(testConfig);
        }
        
        // Remove HistoryEntry
        HistoryService.getInstance().removeHistoryEntry(testConfig.getIdentificationKey());

        return super.preClose(view);
    }

}
