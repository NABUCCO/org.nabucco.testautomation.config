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

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.nabucco.framework.base.facade.datatype.log.LogTrace;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;

import org.nabucco.testautomation.result.facade.datatype.manual.ManualTestResult;
import org.nabucco.testautomation.result.facade.datatype.status.TestConfigElementStatusType;


/**
 * MainTestResultPageWizardPage
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class ManualTestWizardPage2 extends WizardPage {

	private static final String ID = "org.nabucco.testautomation.config.ui.rcp.command.config.execute.wizard.manualtest.ManualTestWizardPage2";
	
	private ManualTestResult manualTestResult;

	/**
	 * Constructs and initializes a new page instance.
	 * @param manualTestResult 
	 * @param testConfigElement2 
	 */
	public ManualTestWizardPage2(ManualTestResult manualTestResult, TestConfigElement testConfigElement) {
		super("");
		this.manualTestResult = manualTestResult;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createControl(Composite parent) {
		Composite plate = new Composite(parent, SWT.NONE);
		GridLayout plateGridLayout = new GridLayout(2, false);
		plateGridLayout.marginTop = 8;
		plate.setLayout(plateGridLayout);

		// Name 
		Label label = new Label(plate, SWT.NULL);
		label.setText(I18N.i18n(ID + ".name"));
		
		final Text nameText = new Text(plate, SWT.BORDER);
		nameText.setText(this.manualTestResult.getName().getValue());
		nameText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				manualTestResult.setName(nameText.getText());
				
			}
		});
		
		// Place holder for free space
		Composite placeholderComposite = new Composite(plate, SWT.NULL);
		GridLayout placeholderLayout = new GridLayout();
		placeholderLayout.marginTop =  5;
		placeholderComposite.setLayout(placeholderLayout);
		
		// Result Selection
		Group radioGroup = new Group(plate, SWT.SHADOW_IN);
		radioGroup.setText(I18N.i18n(ID + ".result"));
	    radioGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
	    GridData radioGroupLayoutData = new GridData();
	    radioGroupLayoutData.horizontalSpan = 2;
	    radioGroup.setLayoutData(radioGroupLayoutData);
	    
	    Button passedButton = new Button(radioGroup, SWT.RADIO);
		passedButton.setText(TestConfigElementStatusType.PASSED.name());
		passedButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				manualTestResult.setStatus(TestConfigElementStatusType.PASSED);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
				
			}
		});
	    
		Button failedButton = new Button(radioGroup, SWT.RADIO);
		failedButton.setText(TestConfigElementStatusType.FAILED.name());
		failedButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				manualTestResult.setStatus(TestConfigElementStatusType.FAILED);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
				
			}
		});
		
		Button skippedButton = new Button(radioGroup, SWT.RADIO);
		skippedButton.setText(TestConfigElementStatusType.SKIPPED.name());
		skippedButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				manualTestResult.setStatus(TestConfigElementStatusType.SKIPPED);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
				
			}
		});
		
		// Place holder for free space
		placeholderComposite = new Composite(plate, SWT.NULL);
		placeholderLayout = new GridLayout();
		placeholderLayout.marginTop =  5;
		placeholderComposite.setLayout(placeholderLayout);
		
		// Text areas composite
		Composite textAreasComposite = new Composite(plate, SWT.BORDER);
		GridLayout textAreasLayout = new GridLayout(1, false);
		textAreasComposite.setLayout(textAreasLayout);
		GridData textAreasCompositeLayoutData = new GridData();
		textAreasCompositeLayoutData.horizontalSpan = 2;
		textAreasCompositeLayoutData.grabExcessHorizontalSpace = true;
		textAreasCompositeLayoutData.horizontalAlignment = SWT.FILL;
		textAreasCompositeLayoutData.grabExcessVerticalSpace = true;
		textAreasCompositeLayoutData.verticalAlignment = SWT.FILL;
		textAreasComposite.setLayoutData(textAreasCompositeLayoutData);
		
		// Message
		label = new Label(textAreasComposite, SWT.NULL);
		label.setText(I18N.i18n(ID + ".message"));
		
		int style = SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL;
		GridData areaslayoutData = new GridData();
		areaslayoutData.grabExcessHorizontalSpace = true;
		areaslayoutData.horizontalAlignment = SWT.FILL;
		areaslayoutData.grabExcessVerticalSpace = true;
		areaslayoutData.verticalAlignment = SWT.FILL;

		final Text messageArea = new Text(textAreasComposite, style);
		messageArea.setLayoutData(areaslayoutData);
		messageArea.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				manualTestResult.setUserMessage(messageArea.getText());
			}
		});
		
		// ErrorMessage
		label = new Label(textAreasComposite, SWT.NULL);
		label.setText(I18N.i18n(ID + ".errorMessage"));

		final Text errorMessageArea = new Text(textAreasComposite, style);
		errorMessageArea.setLayoutData(areaslayoutData);
		errorMessageArea.setEnabled(false);
		errorMessageArea.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				manualTestResult.setUserErrorMessage(errorMessageArea.getText());
			}
		});
		
		
		// Logic
		passedButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				manualTestResult.setStatus(TestConfigElementStatusType.PASSED);
				errorMessageArea.setEnabled(false);
				messageArea.setEnabled(true);
				manualTestResult.setUserErrorMessage((LogTrace) null);
				manualTestResult.setUserMessage(messageArea.getText());
				setPageComplete(validatePage());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
				
			}
		});
	    
		failedButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				manualTestResult.setStatus(TestConfigElementStatusType.FAILED);
				errorMessageArea.setEnabled(true);
				messageArea.setEnabled(false);
				manualTestResult.setUserErrorMessage(errorMessageArea.getText());
				manualTestResult.setUserMessage((LogTrace) null);
				setPageComplete(validatePage());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
				
			}
		});
		
		skippedButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				manualTestResult.setStatus(TestConfigElementStatusType.SKIPPED);
				errorMessageArea.setEnabled(false);
				messageArea.setEnabled(true);
				manualTestResult.setUserErrorMessage((LogTrace) null);
				manualTestResult.setUserMessage(messageArea.getText());
				setPageComplete(validatePage());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
				
			}
		});
		
		
		setControl(plate);
		setPageComplete(false);
	}


	private boolean validatePage(){
		return this.manualTestResult.getStatus() != null;
	}

	
	

}


