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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.wizard.schema;

import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTable;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableColumnInfo;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableParameter;

import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;


/**
 * SchemaSelectionWizardPage
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class SchemaSelectionWizardPage extends WizardPage {

	private static final String ID = "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.wizard.SchemaSelectionWizardPage";
	
	private List<SchemaConfig> schemas;
	
	private SchemaConfig selectedSchema;
	
	private NabuccoTable schemaTable;


	/**
	 * Constructs and initializes a new page instance.
	 */
	public SchemaSelectionWizardPage(List<SchemaConfig> schemas) {
		super(I18N.i18n(ID + ".SchemaSelectionPage.title"));
		this.schemas = schemas;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createControl(Composite parent) {
		Composite plate = new Composite(parent, SWT.NONE);
		plate.setLayout(new GridLayout(1, false));

		SchemaSelectionTableSorter tableSorter = new SchemaSelectionTableSorter();
		SchemaSelectionTableFilter filter = new SchemaSelectionTableFilter();
		SchemaSelectionTablContentProvider contentProvider = new SchemaSelectionTablContentProvider();
		
		
		this.schemaTable = new NabuccoTable(plate, new NabuccoTableParameter(tableSorter, filter, contentProvider, createColumnInfo()));
		this.schemaTable.getTableViewer().setInput(schemas);
		GridData layoutData = new GridData();
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.grabExcessVerticalSpace = true;
		this.schemaTable.setLayoutData(layoutData);

		/*
		 * logic
		 */		schemaTable.getTableViewer().addSelectionChangedListener(new ISelectionChangedListener() {
		
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				setPageComplete(validatePage());
				
			}
		});		setControl(plate);
		setPageComplete(false);
	}



	private boolean validatePage() {
		if (schemaTable.getSelected() != null && schemaTable.getSelected().getFirstElement() instanceof SchemaConfig) {
			this.selectedSchema = (SchemaConfig) this.schemaTable.getSelected().getFirstElement();
			setErrorMessage(null);
			return true;
		}
		setErrorMessage(I18N.i18n(ID + ".SchemaSelectionPage.noSelection"));
		return false;
	}


	public SchemaConfig getSelectedSchema() {
		return this.selectedSchema;
	}
	
	
    private NabuccoTableColumnInfo[] createColumnInfo() {
    	NabuccoTableColumnInfo nabuccoTableColumnInfo1 = new NabuccoTableColumnInfo(
                ID + ".schemaTable.nameColumn.title",
                ID + ".schemaTable.nameColumn.tooltip",
                200, new SchemaSelectionTableNameColumnLabelProvider());
    	nabuccoTableColumnInfo1.setResizable(true);
    	nabuccoTableColumnInfo1.setMoveable(false);
    	
    	NabuccoTableColumnInfo nabuccoTableColumnInfo2 = new NabuccoTableColumnInfo(
                ID + ".schemaTable.descriptionColumn.title",
                ID + ".schemaTable.descriptionColumn.tooltip",
                400, new SchemaSelectionTableDescriptionColumnLabelProvider());
    	nabuccoTableColumnInfo2.setResizable(true);
    	nabuccoTableColumnInfo2.setMoveable(false);
    	
        NabuccoTableColumnInfo[] result = new NabuccoTableColumnInfo[] {nabuccoTableColumnInfo1, nabuccoTableColumnInfo2};
        return result;
    }
}


