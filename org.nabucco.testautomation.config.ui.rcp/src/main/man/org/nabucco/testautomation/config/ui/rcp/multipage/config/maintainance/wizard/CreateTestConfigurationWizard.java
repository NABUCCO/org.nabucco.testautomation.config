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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.wizard;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.plugin.base.layout.ImageProvider;
import org.nabucco.testautomation.config.ui.rcp.images.ConfigImageRegistry;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.wizard.schema.SchemaSelectionWizardPage;

import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;

/**
 * CreateTestConfigurationWizard
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class CreateTestConfigurationWizard extends Wizard implements INewWizard {

	private static final String ID = "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.wizard.CreateTestConfigurationWizard";
	
	private SchemaSelectionWizardPage schemaSelectionWizardPage;

	private List<SchemaConfig> schemas;
	
	public CreateTestConfigurationWizard(List<SchemaConfig> schemas){
		this.schemas = schemas;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(I18N.i18n(ID + ".WindowTitle"));
		setDefaultPageImageDescriptor(ImageProvider.createImageDescriptor(ConfigImageRegistry.ICON_SCHEMA_64X64.getId()));
		setNeedsProgressMonitor(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPages() {
		schemaSelectionWizardPage = new SchemaSelectionWizardPage(this.schemas);
		schemaSelectionWizardPage.setTitle(I18N.i18n(ID + ".SchemaSelectionPage.title"));
		schemaSelectionWizardPage.setDescription(I18N.i18n(ID + ".SchemaSelectionPage.description"));
		addPage(schemaSelectionWizardPage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performFinish() {
		SchemaConfig selectedSchema = schemaSelectionWizardPage.getSelectedSchema();
		return selectedSchema != null;
	}
	
	public SchemaConfig getSelectedSchema(){
		return this.schemaSelectionWizardPage.getSelectedSchema();
	}
}
