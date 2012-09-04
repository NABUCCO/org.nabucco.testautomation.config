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
package org.nabucco.testautomation.config.ui.rcp.command.config.execute.wizard.manualtest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.nabucco.framework.base.facade.datatype.Data;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.detail.model.NTextMiniModel;
import org.nabucco.framework.plugin.base.layout.ImageProvider;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.ui.rcp.images.ConfigImageRegistry;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.ui.rcp.base.property.PropertyViewer;
import org.nabucco.testautomation.property.ui.rcp.base.text.OpenDialogListener;
import org.nabucco.testautomation.property.ui.rcp.images.PropertyImageRegistry;
import org.nabucco.testautomation.result.facade.datatype.manual.ManualTestResult;
import org.nabucco.testautomation.result.facade.datatype.trace.FileTrace;
import org.nabucco.testautomation.result.facade.datatype.trace.MessageTrace;
import org.nabucco.testautomation.result.facade.datatype.trace.ScreenshotTrace;
import org.nabucco.testautomation.result.ui.rcp.images.ResultImageRegistry;
import org.nabucco.testautomation.result.ui.rcp.multipage.result.maintenance.masterdetail.detail.screenshot.OpenScreenshotListener;
import org.nabucco.testautomation.settings.facade.datatype.engine.TestConfigElementLink;

/**
 * ManualTestWizardPage1
 * 
 * @author Markus Jorroch, PRODYNA AG
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ManualTestWizardPage1 extends WizardPage {

	private static final String ID = "org.nabucco.testautomation.config.ui.rcp.command.config.execute.wizard.manualtest.ManualTestWizardPage1";
	
	private TestConfigElement testConfigElement;

	private ManualTestResult manualTestResult;
	
	private Code environment;

	private Code release;
	
	/**
	 * Constructs and initializes a new page instance.
	 * @param testConfigElement
	 */
	public ManualTestWizardPage1(ManualTestResult manualTestResult, TestConfigElement testConfigElement, Code environment, Code release) {
		super("");
		this.testConfigElement = testConfigElement;
		this.manualTestResult = manualTestResult;
		this.environment = environment;
		this.release = release;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createControl(Composite parent) {
		
		Composite page = new Composite(parent, SWT.NONE);
		GridLayout pageGridLayout = new GridLayout(2, false);
		pageGridLayout.marginTop = 0;
		pageGridLayout.marginBottom = 0;
		pageGridLayout.marginLeft = 0;
		pageGridLayout.marginRight = 0;
		pageGridLayout.marginWidth = 0;
		pageGridLayout.marginHeight = 0;
		page.setLayout(pageGridLayout);
		GridData pageLayoutData = new GridData();
		pageLayoutData.grabExcessHorizontalSpace = true;
		pageLayoutData.horizontalAlignment = SWT.FILL;
		pageLayoutData.grabExcessVerticalSpace = true;
		pageLayoutData.verticalAlignment = SWT.FILL;
		page.setLayoutData(pageLayoutData);
		
		Tree navigatorTree = new Tree(page, SWT.BORDER);
		GridLayout navigatorGridLayout = new GridLayout(1, false);
		navigatorGridLayout.marginTop = 0;
		navigatorGridLayout.marginBottom = 0;
		navigatorGridLayout.marginLeft = 0;
		navigatorGridLayout.marginRight = 0;
		navigatorGridLayout.marginWidth = 0;
		navigatorGridLayout.marginHeight = 0;
		navigatorTree.setLayout(navigatorGridLayout);
		GridData navigatorLayoutData = new GridData();
		navigatorLayoutData.grabExcessVerticalSpace = true;
		navigatorLayoutData.verticalAlignment = SWT.FILL;
		navigatorLayoutData.widthHint = 125;
		navigatorTree.setLayoutData(navigatorLayoutData);
		
		final TreeItem item1 = new TreeItem(navigatorTree, SWT.NONE);
		item1.setText((I18N.i18n(ID + ".navigator.documentation")));
		final TreeItem item2 = new TreeItem(item1, SWT.NONE);
		item2.setText((I18N.i18n(ID + ".navigator.property")));
		final TreeItem item3 = new TreeItem(navigatorTree, SWT.NONE);
		item3.setText((I18N.i18n(ID + ".navigator.runtime")));
		final TreeItem item4 = new TreeItem(item3, SWT.NONE);
		item4.setText((I18N.i18n(ID + ".navigator.screenshot")));
		final TreeItem item5 = new TreeItem(item3, SWT.NONE);
		item5.setText((I18N.i18n(ID + ".navigator.file")));
		final TreeItem item6 = new TreeItem(item3, SWT.NONE);
		item6.setText((I18N.i18n(ID + ".navigator.message")));
		item1.setExpanded(true);
		item3.setExpanded(true);

		final Composite stackPage = new Composite(page, SWT.NONE);
		final Composite page1 = createStackPage1(stackPage);
		final Composite page2 = createStackPage2(stackPage);
		final Composite page3 = createStackPage3(stackPage);
		final Composite page4 = createStackPage4(stackPage);
		final Composite page5 = createStackPage5(stackPage);
		final Composite page6 = createStackPage6(stackPage);
		final StackLayout stackPageLayout = new StackLayout();
		stackPageLayout.marginHeight = 0;
		stackPageLayout.marginWidth = 0;
		stackPageLayout.topControl = page1;
		stackPage.setLayout(stackPageLayout);
		
		navigatorTree.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Tree tree = (Tree) arg0.getSource();
				TreeItem selectedItem = tree.getSelection()[0];
				
				if (selectedItem == item1) {
					stackPageLayout.topControl = page1;
				} else if (selectedItem == item2) {
					stackPageLayout.topControl = page2;
				} else if (selectedItem == item3) {
					stackPageLayout.topControl = page3;
				} else if (selectedItem == item4) {
					stackPageLayout.topControl = page4;
				} else if (selectedItem == item5) {
					stackPageLayout.topControl = page5;
				} else if (selectedItem == item6) {
				    stackPageLayout.topControl = page6;
				}
				stackPage.layout();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
			
		});
		
		GridData plateLayoutData = new GridData();
		plateLayoutData.grabExcessHorizontalSpace = true;
		plateLayoutData.horizontalAlignment = SWT.FILL;
		plateLayoutData.grabExcessVerticalSpace = true;
		plateLayoutData.verticalAlignment = SWT.FILL;
		stackPage.setLayoutData(plateLayoutData);

		setControl(page);
		setPageComplete(true);
	}
	
	private Composite createStackPage1(Composite parent) {
		
		Composite page1 = new Composite(parent, SWT.NONE);
		GridLayout pageGridLayout = new GridLayout(1, false);
		pageGridLayout.marginTop = 0;
		pageGridLayout.marginBottom = 0;
		pageGridLayout.marginLeft = 0;
		pageGridLayout.marginRight = 0;
		pageGridLayout.marginWidth = 0;
		pageGridLayout.marginHeight = 0;
		page1.setLayout(pageGridLayout);
		GridData pageLayoutData = new GridData();
		pageLayoutData.grabExcessHorizontalSpace = true;
		pageLayoutData.horizontalAlignment = SWT.FILL;
		pageLayoutData.grabExcessVerticalSpace = true;
		pageLayoutData.verticalAlignment = SWT.FILL;
		page1.setLayoutData(pageLayoutData);
		
		Composite elementTreeComposite = new Composite(page1, SWT.NULL);
		GridLayout elementTreeLayout = new GridLayout(1, false);
		elementTreeLayout.marginTop = 0;
		elementTreeLayout.marginBottom = 0;
		elementTreeLayout.marginLeft = 0;
		elementTreeLayout.marginRight = 0;
		elementTreeLayout.marginWidth = 0;
		elementTreeLayout.marginHeight = 0;
		elementTreeComposite.setLayout(elementTreeLayout);
		
		GridData elementTreeLayoutData = new GridData();
		elementTreeLayoutData.grabExcessHorizontalSpace = true;
		elementTreeLayoutData.horizontalAlignment = SWT.FILL;
		elementTreeComposite.setLayoutData(elementTreeLayoutData);

		Tree elementTree = new Tree(elementTreeComposite, SWT.BORDER);
		TreeItem parentItem = null;
		
		for (TestConfigElementLink link : this.manualTestResult.getContextSnapshot().getCurrentTestConfigElementList()) {
			TreeItem item = null;
			
			if (parentItem == null) {
				item = new TreeItem(elementTree, SWT.NONE);
			} else {
				item = new TreeItem(parentItem, SWT.NONE);
				parentItem.setExpanded(true);
			}
			item.setText(link.getElementKey().getValue() + " - " + link.getElementName().getValue());
			
			if (link.getElementKey().equals(this.testConfigElement.getIdentificationKey())) {
				item.setImage(ImageProvider.createImage(ConfigImageRegistry.ICON_CURRENT_ELEMENT.getId()));
			}
			parentItem = item;
		}
		
		GridData testConfigElementNameData = new GridData();
		testConfigElementNameData.grabExcessHorizontalSpace = true;
		testConfigElementNameData.horizontalAlignment = SWT.FILL;
		testConfigElementNameData.grabExcessVerticalSpace = true;
		testConfigElementNameData.verticalAlignment = SWT.FILL;
		elementTree.setLayoutData(testConfigElementNameData);
		
		// Text areas composite
		Composite textAreasComposite = new Composite(page1, SWT.BORDER);
		GridLayout textAreasLayout = new GridLayout(1, false);
		textAreasComposite.setLayout(textAreasLayout);
		GridData textAreasCompositeLayoutData = new GridData();
		textAreasCompositeLayoutData.horizontalSpan = 2;
		textAreasCompositeLayoutData.grabExcessHorizontalSpace = true;
		textAreasCompositeLayoutData.horizontalAlignment = SWT.FILL;
		textAreasCompositeLayoutData.grabExcessVerticalSpace = true;
		textAreasCompositeLayoutData.verticalAlignment = SWT.FILL;
		textAreasComposite.setLayoutData(textAreasCompositeLayoutData);
		
		Composite plate2 = new Composite(textAreasComposite, SWT.NULL);
		GridLayout plate2GridLayout = new GridLayout(6, false);
		plate2GridLayout.marginTop = 0;
		plate2GridLayout.marginLeft = 0;
		plate2GridLayout.marginWidth = 0;
		plate2GridLayout.marginBottom = 0;
		plate2GridLayout.horizontalSpacing = 5;
		plate2.setLayout(plate2GridLayout);
		GridData plate2LayoutData = new GridData();
		plate2LayoutData.grabExcessHorizontalSpace = true;
		plate2LayoutData.horizontalAlignment = SWT.LEFT;
		plate2.setLayoutData(plate2LayoutData);
		
		Label brandLabel = new Label(plate2, SWT.NULL);
		brandLabel.setText((I18N.i18n(ID + ".TestConfigElement.brand")));
		
		Text brandTf = new Text(plate2, SWT.READ_ONLY);
		
		if (this.manualTestResult.getBrandType() != null) {
			brandTf.setText(this.manualTestResult.getBrandType().getName().getValue());
		} else {
			brandTf.setText((I18N.i18n(ID + ".TestConfigElement.noCode")));
		}
		brandTf.setLayoutData(new GridData());

		Label envLabel = new Label(plate2, SWT.NULL);
		envLabel.setText("  " + (I18N.i18n(ID + ".TestConfigElement.environment")));
		
		Text envTf = new Text(plate2, SWT.READ_ONLY);
		
		if (this.environment != null) {
			envTf.setText(this.environment.getName().getValue());
		} else {
			envTf.setText((I18N.i18n(ID + ".TestConfigElement.noCode")));
		}
		envTf.setLayoutData(new GridData());

		Label releaseLabel = new Label(plate2, SWT.NULL);
		releaseLabel.setText("  " + (I18N.i18n(ID + ".TestConfigElement.release")));
		
		Text releaseTf = new Text(plate2, SWT.READ_ONLY);
		
		if (this.release != null) {
			releaseTf.setText(this.release.getName().getValue());
		} else {
			releaseTf.setText((I18N.i18n(ID + ".TestConfigElement.noCode")));
		}
		releaseTf.setLayoutData(new GridData());
		
		Label separator = new Label(textAreasComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData separatorLayoutData = new GridData();
		separatorLayoutData.horizontalSpan = 2;
		separatorLayoutData.heightHint = 10;
		separatorLayoutData.grabExcessHorizontalSpace = true;
		separatorLayoutData.horizontalAlignment = SWT.FILL;
		separator.setLayoutData(separatorLayoutData);
		
		// TestConfigElement Description TextArea
		Label label = new Label(textAreasComposite, SWT.NULL);
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

		// TestConfigElement Documentation TextArea
		label = new Label(textAreasComposite, SWT.NULL);
		label.setText((I18N.i18n(ID + ".TestConfigElement.documentation")));
		
		Text testConfigElementDocumentationArea = new Text(textAreasComposite, style);
		
		if(this.testConfigElement.getDocumentation() != null && this.testConfigElement.getDocumentation().getValue() != null 
				&& this.testConfigElement.getDocumentation().getValue().length() > 0){
			testConfigElementDocumentationArea.setText(this.testConfigElement.getDocumentation().getValue());
		} else {
			testConfigElementDocumentationArea.setText(I18N.i18n(ID + ".TestConfigElement.noDocumentation"));
		}
		testConfigElementDocumentationArea.setLayoutData(areaslayoutData);
		
		return page1;
	}
	
	private Composite createStackPage2(Composite parent) {
		
		Composite page2 = new Composite(parent, SWT.NONE);
		GridLayout pageGridLayout = new GridLayout(1, false);
		pageGridLayout.marginTop = 0;
		pageGridLayout.marginBottom = 0;
		pageGridLayout.marginLeft = 0;
		pageGridLayout.marginRight = 0;
		pageGridLayout.marginWidth = 0;
		pageGridLayout.marginHeight = 0;
		page2.setLayout(pageGridLayout);
		GridData pageLayoutData = new GridData();
		pageLayoutData.grabExcessHorizontalSpace = true;
		pageLayoutData.horizontalAlignment = SWT.FILL;
		pageLayoutData.grabExcessVerticalSpace = true;
		pageLayoutData.verticalAlignment = SWT.FILL;
		page2.setLayoutData(pageLayoutData);
		
		PropertyViewer viewer = new PropertyViewer(page2, true);
		viewer.setLayoutData(pageLayoutData);
		viewer.setLinesVisible(true);
		
		if (this.manualTestResult != null && this.manualTestResult.getPropertyList() != null) {
			for (Property property : this.manualTestResult.getPropertyList().getPropertyList())
			viewer.addProperty(property);
		}
		return page2;
	}

	private Composite createStackPage3(Composite parent) {
		
		Composite page3 = new Composite(parent, SWT.NONE);
		GridLayout pageGridLayout = new GridLayout(1, false);
		pageGridLayout.marginTop = 0;
		pageGridLayout.marginBottom = 0;
		pageGridLayout.marginLeft = 0;
		pageGridLayout.marginRight = 0;
		pageGridLayout.marginWidth = 0;
		pageGridLayout.marginHeight = 0;
		page3.setLayout(pageGridLayout);
		GridData pageLayoutData = new GridData();
		pageLayoutData.grabExcessHorizontalSpace = true;
		pageLayoutData.horizontalAlignment = SWT.FILL;
		pageLayoutData.grabExcessVerticalSpace = true;
		pageLayoutData.verticalAlignment = SWT.FILL;
		page3.setLayoutData(pageLayoutData);
		
		PropertyViewer viewer = new PropertyViewer(page3);
		viewer.setLayoutData(pageLayoutData);
		viewer.setLinesVisible(true);
		
		if (this.manualTestResult != null && this.manualTestResult.getContextSnapshot() != null) {
			for (Property property : this.manualTestResult.getContextSnapshot().getPropertyList()) {
				viewer.addProperty(property);
			}
		}
		
		return page3;
	}
	
	private Composite createStackPage4(Composite parent) {
		
		Composite page4 = new Composite(parent, SWT.NONE);
		GridLayout pageGridLayout = new GridLayout(3, false);
		pageGridLayout.marginTop = 20;
		pageGridLayout.marginBottom = 0;
		pageGridLayout.marginLeft = 20;
		pageGridLayout.marginRight = 20;
		pageGridLayout.marginWidth = 0;
		pageGridLayout.marginHeight = 0;
		page4.setLayout(pageGridLayout);
		GridData pageLayoutData = new GridData();
		pageLayoutData.grabExcessHorizontalSpace = true;
		pageLayoutData.horizontalAlignment = SWT.FILL;
		pageLayoutData.grabExcessVerticalSpace = true;
		pageLayoutData.verticalAlignment = SWT.FILL;
		page4.setLayoutData(pageLayoutData);
		
		// Format button right
		GridData data;
		int index = 1;
		
		for (ScreenshotTrace screenshot : this.manualTestResult.getScreenshots()) {
			// Create Label
			Label label = new Label(page4, SWT.NONE);
			label.setText((index++) + ". ");
			
			Text nameTextField = new Text(page4, SWT.BORDER | SWT.READ_ONLY);
			String name = "";
			
			if (screenshot.getActionId() != null && screenshot.getActionId().getValue() != null) {
				name += screenshot.getActionId().getValue() + " - ";
			}
			name += screenshot.getName().getValue();
			nameTextField.setText(name);	
			data = new GridData();
			data.horizontalAlignment = SWT.LEFT;
			data.widthHint = 300;
			nameTextField.setLayoutData(data);
			
			// Create Button
			Button button = new Button(page4, SWT.NONE);
			button.setImage(ImageProvider.createImage(ResultImageRegistry.ICON_SCREENSHOT.getId()));
			button.addSelectionListener(new OpenScreenshotListener(screenshot, false));
			data = new GridData();
			data.horizontalAlignment = SWT.LEFT;
			button.setLayoutData(data);
		}
		
		return page4;
	}

	private Composite createStackPage5(Composite parent) {
		
		Composite page5 = new Composite(parent, SWT.NONE);
		GridLayout pageGridLayout = new GridLayout(3, false);
		pageGridLayout.marginTop = 20;
		pageGridLayout.marginBottom = 0;
		pageGridLayout.marginLeft = 20;
		pageGridLayout.marginRight = 20;
		pageGridLayout.marginWidth = 0;
		pageGridLayout.marginHeight = 0;
		page5.setLayout(pageGridLayout);
		GridData pageLayoutData = new GridData();
		pageLayoutData.grabExcessHorizontalSpace = true;
		pageLayoutData.horizontalAlignment = SWT.FILL;
		pageLayoutData.grabExcessVerticalSpace = true;
		pageLayoutData.verticalAlignment = SWT.FILL;
		page5.setLayoutData(pageLayoutData);
		
		// Format button right
		GridData data;
		int index = 1;

		for (FileTrace file : this.manualTestResult.getFiles()) {
			final Data fileContent = file.getFileContent();
			final String fileNameWithExtension = file.getName().getValue();

			// Create Label
			Label label = new Label(page5, SWT.NONE);
			label.setText((index++) + ". ");
			
			Text nameTextField = new Text(page5, SWT.BORDER | SWT.READ_ONLY);
			String name = "";
			
			if (file.getActionId() != null && file.getActionId().getValue() != null) {
				name += file.getActionId().getValue() + " - ";
			}
			name += file.getName().getValue();
			nameTextField.setText(name);	
			data = new GridData();
			data.horizontalAlignment = SWT.LEFT;
			data.widthHint = 300;
			nameTextField.setLayoutData(data);

			// Create Button
			Button button = new Button(page5, SWT.NONE);
			button.setImage(ImageProvider.createImage(ResultImageRegistry.ICON_FILE.getId()));
			button.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {

					try {
						String fileName = null ;
						String userHome = System.getProperty("java.io.tmpdir");
						File folder = new File(userHome);

						String extension = null;
						for(int i = fileNameWithExtension.length() - 1; i > 0; i-- ){
							if(fileNameWithExtension.charAt(i) == '.'){
								extension = fileNameWithExtension.substring(i);
								fileName = fileNameWithExtension.substring(0, i);
								break;
							}
						}
						File tempFile = File.createTempFile(fileName, extension, folder);

						FileOutputStream fos = new FileOutputStream(tempFile);
						fos.write(fileContent.getValue());
						fos.close();


						Program p = Program.findProgram(extension);
						if (p != null) {
							p.execute(tempFile.getAbsolutePath());
						} else {
							Activator.getDefault().logError("No system editor for type '" + extension + "' found");
						}

					} catch (IOException e) {
						Activator.getDefault().logError(e);
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// do nothing
				}
			});
			data = new GridData();
			data.horizontalAlignment = SWT.LEFT;
			button.setLayoutData(data);
		}
		
		return page5;
	}

	private Composite createStackPage6(Composite parent) {
		
		Composite page6 = new Composite(parent, SWT.NONE);
		GridLayout pageGridLayout = new GridLayout(6, false);
		pageGridLayout.marginTop = 20;
		pageGridLayout.marginBottom = 0;
		pageGridLayout.marginLeft = 20;
		pageGridLayout.marginRight = 20;
		pageGridLayout.marginWidth = 0;
		pageGridLayout.marginHeight = 0;
		page6.setLayout(pageGridLayout);
		GridData pageLayoutData = new GridData();
		pageLayoutData.grabExcessHorizontalSpace = true;
		pageLayoutData.horizontalAlignment = SWT.FILL;
		pageLayoutData.grabExcessVerticalSpace = true;
		pageLayoutData.verticalAlignment = SWT.FILL;
		page6.setLayoutData(pageLayoutData);
		
		// Format button right
		GridData data;
		int index = 1;

		for (MessageTrace message : this.manualTestResult.getMessages()) {
			
			// Create Label
			Label label = new Label(page6, SWT.NONE);
			label.setText((index++) + ". ");
			
			Text nameTextField = new Text(page6, SWT.BORDER | SWT.READ_ONLY);
			String name = "";
			
			if (message.getActionId() != null && message.getActionId().getValue() != null) {
				name += message.getActionId().getValue() + " - ";
			}
			name += message.getName().getValue();
			nameTextField.setText(name);	
			data = new GridData();
			data.horizontalAlignment = SWT.LEFT;
			data.widthHint = 300;
			nameTextField.setLayoutData(data);
			
			// Create Button for Request
			if (message.getRequest() != null && message.getRequest().getValue() != null) {
				Label rq = new Label(page6, SWT.NONE);
				rq.setText("RQ: ");
				
				Button button = new Button(page6, SWT.NONE);
				button.setImage(ImageProvider.createImage(PropertyImageRegistry.ICON_DETAILS_DIALOG.getId()));
				button.addSelectionListener(new OpenDialogListener(new NTextMiniModel(message.getRequest(), null, message), null, true));
				data = new GridData();
				data.horizontalAlignment = SWT.LEFT;
				button.setLayoutData(data);
			} else {
				new Label(page6, SWT.NONE);
				new Label(page6, SWT.NONE);
			}

			// Create Button for Response
			if (message.getResponse() != null && message.getResponse().getValue() != null) {
				Label rq = new Label(page6, SWT.NONE);
				rq.setText("RS: ");
				
				Button button = new Button(page6, SWT.NONE);
				button.setImage(ImageProvider.createImage(PropertyImageRegistry.ICON_DETAILS_DIALOG.getId()));
				button.addSelectionListener(new OpenDialogListener(new NTextMiniModel(message.getResponse(), null, message), null, true));
				data = new GridData();
				data.horizontalAlignment = SWT.LEFT;
				button.setLayoutData(data);
			} else {
				new Label(page6, SWT.NONE);
				new Label(page6, SWT.NONE);
			}
		}
		
		return page6;
	}
	
}


