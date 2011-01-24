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
package org.nabucco.testautomation.config.ui.rcp.command.config.execute.wizard.manualtest;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;

import org.nabucco.testautomation.result.facade.datatype.manual.ManualTestResult;

/**
 * DetailsPageWizardPage
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class ManualTestWizardPage4 extends WizardPage {

	private static final String ID = "org.nabucco.testautomation.config.ui.rcp.command.config.execute.wizard.manualtest.ManualTestWizardPage4";

	private ListViewer listViewer;

	private List<String> fileNames = new ArrayList<String>();

	/**
	 * Constructs and initializes a new page instance.
	 * 
	 * @param manualTestResult
	 */
	public ManualTestWizardPage4(ManualTestResult manualTestResult, TestConfigElement testConfigElement) {
		super("");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite plate = new Composite(parent, SWT.NONE);
		plate.setLayout(new GridLayout(1, false));

		listViewer = new ListViewer(plate);
		GridData listViewerLayoutData = new GridData();
		listViewerLayoutData.grabExcessHorizontalSpace = true;
		listViewerLayoutData.horizontalAlignment = SWT.FILL;
		listViewerLayoutData.grabExcessVerticalSpace = true;
		listViewerLayoutData.verticalAlignment = SWT.FILL;
		listViewer.getControl().setLayoutData(listViewerLayoutData);

		listViewer.setContentProvider(new IStructuredContentProvider() {
			
			public Object[] getElements(Object inputElement) {
				return fileNames.toArray();
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		});

		listViewer.setInput(fileNames);

		listViewer.setLabelProvider(new LabelProvider() {
			public Image getImage(Object element) {
				return null;
			}

			public String getText(Object element) {
				return ((String) element);
			}
		});
		
		addButtons(plate);

		setControl(plate);
		setPageComplete(true);
	}

	private void addButtons(Composite plate) {
		Composite composite = new Composite(plate, SWT.NULL);
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.spacing = 2;

		composite.setLayout(fillLayout);

		Button buttonAdd = new Button(composite, SWT.PUSH);
		buttonAdd.setText(I18N.i18n(ID + ".AddButtonLabel"));

		Button buttonRemove = new Button(composite, SWT.PUSH);
		buttonRemove.setText(I18N.i18n(ID + ".RemoveButtonLabel"));

		buttonAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(Activator.getDefault().getWorkbench()
						.getDisplay().getActiveShell(), SWT.OPEN);
				String filename = fd.open();
				if (filename != null) {
					fileNames.add(filename);
				}
				listViewer.refresh(false);
			}
		});


		buttonRemove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) listViewer.getSelection();
				String fileName = (String) selection.getFirstElement();
				if (fileName != null) {
					fileNames.remove(fileName);
					listViewer.refresh(false);
				}
			}
		});
	}

	public List<String> getFileNames(){
		return this.fileNames;
	}
	
}
