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

import java.io.ByteArrayOutputStream;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.result.facade.datatype.manual.ManualTestResult;
import org.nabucco.testautomation.result.facade.datatype.trace.ScreenshotTrace;
import org.nabucco.testautomation.result.facade.message.TraceMsg;
import org.nabucco.testautomation.result.ui.rcp.communication.ResultComponentServiceDelegateFactory;
import org.nabucco.testautomation.result.ui.rcp.communication.produce.ProduceResultDelegate;


/**
 * DetailsPageWizardPage
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class ManualTestWizardPage3 extends WizardPage {

	private static final String ID = "org.nabucco.testautomation.config.ui.rcp.command.config.execute.wizard.manualtest.ManualTestWizardPage3";
	
	private ManualTestResult manualTestResult;
	
	private Image screenShot;

	private ScreenshotTrace screenshotTrace;

	/**
	 * Constructs and initializes a new page instance.
	 * @param manualTestResult 
	 */
	public ManualTestWizardPage3(ManualTestResult manualTestResult, TestConfigElement testConfigElement) {
		super("");
		this.manualTestResult = manualTestResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createControl(Composite parent) {
		final Composite plate = new Composite(parent, SWT.NONE);
		plate.setLayout(new GridLayout(4, false));

		final ScrolledComposite scrolledComposite = new ScrolledComposite(plate, SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.BORDER);
		scrolledComposite.setFocus();
		scrolledComposite.getVerticalBar().setIncrement(10);
		scrolledComposite.getHorizontalBar().setIncrement(10);
		GridData scrolledCompositeLayoutData = new GridData(SWT.FILL, SWT.FILL, true,
				true);
		scrolledCompositeLayoutData.horizontalSpan = 4;
		scrolledComposite.setLayoutData(scrolledCompositeLayoutData);
		final Canvas canvas = new Canvas(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(canvas);
		canvas.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		canvas.setBounds(0, 0, scrolledComposite.getSize().x, scrolledComposite.getSize().y);

		Label label = new Label(plate, SWT.NULL);
		label.setText(" " + I18N.i18n(ID + ".ScreenshotName") + " ");
		final Text screenshotNameText = new Text(plate, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.widthHint = 250;
		screenshotNameText.setLayoutData(gridData);
		screenshotNameText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
				if(screenshotTrace != null){
					screenshotTrace.setName(screenshotNameText.getText());
				}

			}
		});

		Button pasteScreenShotButton = new Button(plate, SWT.NULL);
		pasteScreenShotButton.setText(I18N.i18n(ID + ".PasteButtonLabel"));
		pasteScreenShotButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				final Clipboard clipboard = new Clipboard(Activator.getDefault().getWorkbench().getDisplay());
				ImageData imageData = (ImageData)clipboard.getContents(ImageTransfer.getInstance());
				if (imageData != null) {
					try {
						screenShot = new Image(Activator.getDefault().getWorkbench().getDisplay(), imageData);

						canvas.setBackgroundImage(screenShot);
						canvas.setBounds(screenShot.getBounds());

						if(screenshotTrace == null){
							ProduceResultDelegate produceTrace;
							produceTrace = ResultComponentServiceDelegateFactory.getInstance().getProduceResult();
							TraceMsg rs = produceTrace.produceScreenshotTrace(new EmptyServiceMessage());
							screenshotTrace = (ScreenshotTrace) rs.getTrace();	
							screenshotTrace.setName(screenshotNameText.getText());
						}
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						ImageLoader loader = new ImageLoader();
						loader.data = new ImageData[] { imageData };
						loader.save(out, SWT.IMAGE_PNG);
						screenshotTrace.setScreenshot(out.toByteArray());
						manualTestResult.getActionTraceList().clear();
						manualTestResult.getActionTraceList().add(screenshotTrace);
					} catch (ClientException e) {
						Activator.getDefault().logError(e);
					}
				} 
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
			}
		});

		Button removeScreenshotButton = new Button(plate, SWT.NULL);
		removeScreenshotButton.setText(I18N.i18n(ID + ".RemoveButtonLabel"));
		removeScreenshotButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(screenshotTrace != null){
					manualTestResult.getActionTraceList().clear();
					screenshotTrace = null;
					canvas.setBackgroundImage(new Image(Display.getCurrent(), scrolledComposite.getBounds()));
					canvas.setBounds(scrolledComposite.getBounds());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
			}
		});

		setControl(plate);
		setPageComplete(true);
	}



}

