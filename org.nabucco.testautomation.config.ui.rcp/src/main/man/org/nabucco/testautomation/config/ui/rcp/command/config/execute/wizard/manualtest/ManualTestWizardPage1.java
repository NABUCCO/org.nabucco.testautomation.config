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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;

import org.nabucco.testautomation.result.facade.datatype.manual.ManualTestResult;


/**
 * MainTestResultPageWizardPage
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class ManualTestWizardPage1 extends WizardPage {

	private static final String ID = "org.nabucco.testautomation.config.ui.rcp.command.config.execute.wizard.manualtest.ManualTestWizardPage1";
	private TestConfigElement testConfigElement;

	/**
	 * Constructs and initializes a new page instance.
	 * @param testConfigElement
	 */
	public ManualTestWizardPage1(ManualTestResult manualTestResult, TestConfigElement testConfigElement) {
		super("");
		this.testConfigElement = testConfigElement;
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

		//
		Label label = new Label(plate, SWT.NULL);
		label.setText(this.testConfigElement.getSchemaElement().getName().getValue());

		Text testConfigElementName = new Text(plate, SWT.BORDER | SWT.READ_ONLY);
		testConfigElementName.setText(this.testConfigElement.getElementKey().getValue() + " - " + this.testConfigElement.getName().getValue());
		GridData testConfigElementNameData = new GridData();
//		testConfigElementNameData.grabExcessHorizontalSpace = true;
		testConfigElementName.setLayoutData(testConfigElementNameData);
		testConfigElementName.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		// Place holder for free space
		Composite placeholderComposite = new Composite(plate, SWT.BORDER_DASH);
		GridLayout placeholderLayout = new GridLayout();
		placeholderLayout.marginTop =  5;
		placeholderComposite.setLayout(placeholderLayout);
		GridData placeHolderLayoutData = new GridData();
		placeHolderLayoutData.horizontalSpan = 2;
		placeholderComposite.setLayoutData(placeHolderLayoutData);
		
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
		
		// TestConfigElement Description TextArea
		label = new Label(textAreasComposite, SWT.NULL);
		label.setText(I18N.i18n(ID + ".TestConfigElement.description"));
		
		int style = SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY;
		GridData areaslayoutData = new GridData();
		areaslayoutData.grabExcessHorizontalSpace = true;
		areaslayoutData.horizontalAlignment = SWT.FILL;
		areaslayoutData.grabExcessVerticalSpace = true;
		areaslayoutData.verticalAlignment = SWT.FILL;

		Text testConfigElementDescriptionArea = new Text(textAreasComposite, style);
		if(this.testConfigElement.getDescription() != null && this.testConfigElement.getDescription().getValue() != null 
				&& this.testConfigElement.getDescription().getValue().length() > 0){
			testConfigElementDescriptionArea.setText(this.testConfigElement.getDescription().getValue());
		} else {
			testConfigElementDescriptionArea.setText(I18N.i18n(ID + ".TestConfigElement.noDescription"));
		}
		testConfigElementDescriptionArea.setLayoutData(areaslayoutData);
		testConfigElementDescriptionArea.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		// TestConfigElement Documentation TextArea
		placeholderComposite = new Composite(textAreasComposite, SWT.NULL);
		placeholderComposite.setLayout(placeholderLayout);
		label = new Label(textAreasComposite, SWT.NULL);
		label.setText(I18N.i18n(ID + ".TestConfigElement.documentation"));
		
		Text testConfigElementDocumentationArea = new Text(textAreasComposite, style);
		if(this.testConfigElement.getDocumentation() != null && this.testConfigElement.getDocumentation().getValue() != null 
				&& this.testConfigElement.getDocumentation().getValue().length() > 0){
			testConfigElementDocumentationArea.setText(this.testConfigElement.getDocumentation().getValue());
		} else {
			testConfigElementDocumentationArea.setText(I18N.i18n(ID + ".TestConfigElement.noDocumentation"));
		}
		testConfigElementDocumentationArea.setLayoutData(areaslayoutData);
		testConfigElementDocumentationArea.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		setControl(plate);
		setPageComplete(true);
	}

	
	
	
}


