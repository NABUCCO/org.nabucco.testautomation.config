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

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.command.create.AbstractCreateViewHandler;
import org.nabucco.testautomation.config.ui.rcp.command.config.CreateTestConfigCommandHandler;
import org.nabucco.testautomation.config.ui.rcp.edit.config.model.TestConfigurationEditViewBusinessModel;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.TestConfigurationMaintenanceMultiPageEditView;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationMaintenanceMultiPageEditViewModel;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationMaintenanceMultiPageEditViewModelHandler;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.wizard.schema.CreateTestConfigurationWizard;

import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;

/**
 * CreateTestConfigCommandHandlerImpl
 * 
 * @author Markus Jorroch, PRODYNA AG, 2010-06-09
 * @author Silas Schwarz, PRODYNA AG
 */
public class CreateTestConfigCommandHandlerImpl
        extends
        AbstractCreateViewHandler<TestConfigurationMaintenanceMultiPageEditViewModel, TestConfigurationMaintenanceMultiPageEditView>
        implements CreateTestConfigCommandHandler {

	private static final String ID = "org.nabucco.testautomation.config.ui.rcp.command.config.CreateTestConfigCommandHandler";
	
    private TestConfigurationMaintenanceMultiPageEditViewModelHandler handler = new TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl();
    
    private SchemaConfig schemaConfig;

    @Override
    public void createTestConfigCommand() {
    	List<SchemaConfig> schemas = new TestConfigurationEditViewBusinessModel().getSchemaConfigList();
        if(schemas == null || schemas.size() == 0){
        	MessageDialog.openError(null, I18N.i18n(ID + ".noSchemaErrorDialog.title"),
                    I18N.i18n(ID + ".noSchemaErrorDialog.message"));
        	return;
        } else if(schemas.size() == 1){
        	schemaConfig = schemas.get(0);
        	run();
        } else if(schemas.size() > 1){
        	CreateTestConfigurationWizard wizard = new CreateTestConfigurationWizard(schemas);
        	wizard.init(Activator.getDefault().getWorkbench(), StructuredSelection.EMPTY);
        	WizardDialog wizardDialog = new WizardDialog(
        			Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
        	wizardDialog.open();
        	this.schemaConfig = wizard.getSelectedSchema();
        	if(this.schemaConfig != null){
        		run();
        	}
        }
        
    }

    @Override
    public void run() {
    	super.openView(getEditViewId());
    }

    

	@Override
    protected String getEditViewId() {
        return TestConfigurationMaintenanceMultiPageEditView.ID;
    }

    @Override
    protected void updateModel(TestConfigurationMaintenanceMultiPageEditViewModel model) throws ClientException {
    	model.setTestConfiguration(handler.createDefaultDatatype(this.schemaConfig));
    }
}
