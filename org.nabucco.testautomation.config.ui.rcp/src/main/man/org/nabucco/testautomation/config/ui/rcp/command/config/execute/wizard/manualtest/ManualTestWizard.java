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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.layout.ImageProvider;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.visitor.FindManualTestResultVisitor;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.facade.message.engine.ManualTestResultMsg;
import org.nabucco.testautomation.config.ui.rcp.communication.ConfigComponentServiceDelegateFactory;
import org.nabucco.testautomation.config.ui.rcp.communication.search.SearchTestConfigElementDelegate;
import org.nabucco.testautomation.config.ui.rcp.images.ConfigImageRegistry;
import org.nabucco.testautomation.facade.datatype.engine.TestEngineConfiguration;
import org.nabucco.testautomation.facade.datatype.engine.TestExecutionInfo;
import org.nabucco.testautomation.result.facade.datatype.TestConfigurationResult;
import org.nabucco.testautomation.result.facade.datatype.manual.ManualState;
import org.nabucco.testautomation.result.facade.datatype.manual.ManualTestResult;
import org.nabucco.testautomation.result.facade.datatype.trace.FileTrace;
import org.nabucco.testautomation.result.facade.message.TraceMsg;
import org.nabucco.testautomation.result.ui.rcp.communication.ResultComponentServiceDelegateFactory;
import org.nabucco.testautomation.result.ui.rcp.communication.produce.ProduceTraceDelegate;



/**
 * The main create diagram wizard.
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class ManualTestWizard extends Wizard implements INewWizard {

	private static final String ID = "org.nabucco.testautomation.config.ui.rcp.command.config.execute.wizard.manualtest.ManualTestWizard";
	
	private ManualTestWizardPage1 manualTestWizardPage1;

	private ManualTestWizardPage2 manualTestWizardPage2;
	
	private ManualTestWizardPage3 manualTestWizardPage3;
	
	private ManualTestResult manualTestResult;
	
	private TestConfigElement testConfigElement;

	private TestEngineConfiguration configuration;

	private TestExecutionInfo info;

	private ManualTestWizardPage4 manualTestWizardPage4;
	
	private Code environment;

	private Code release;

	public ManualTestWizard(TestConfigurationResult testConfigurationResult, TestEngineConfiguration configuration, TestExecutionInfo info){
		this.configuration = configuration;
		this.info = info;
		this.environment = testConfigurationResult.getEnvironmentType();
		this.release = testConfigurationResult.getReleaseType();
		
		FindManualTestResultVisitor visitor = new FindManualTestResultVisitor();
		try {
			testConfigurationResult.accept(visitor);
		} catch (VisitorException e) {
			Activator.getDefault().logError(e);
		}
		manualTestResult = visitor.getManualTestResult();
		if(manualTestResult == null){
			throw new IllegalArgumentException("No waiting manual test result found.");
		}
		
		testConfigElement = loadTestConfigElement();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(I18N.i18n(ID + ".WindowTitle"));
		setDefaultPageImageDescriptor(ImageProvider.createImageDescriptor(ConfigImageRegistry.ICON_MANUAL_CHECK_64X64.getId()));
		setNeedsProgressMonitor(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPages() {
		manualTestWizardPage1 = new ManualTestWizardPage1(manualTestResult, testConfigElement, this.environment, this.release);
		manualTestWizardPage1.setTitle(I18N.i18n(ID + ".ManualTestWizardPage1.title"));
		manualTestWizardPage1.setDescription(I18N.i18n(ID + ".ManualTestWizardPage1.description"));
		manualTestWizardPage1.setImageDescriptor(ImageProvider.createImageDescriptor(ConfigImageRegistry.ICON_INFO_64X64.getId()));
		addPage(manualTestWizardPage1);
		manualTestWizardPage2 = new ManualTestWizardPage2(manualTestResult, testConfigElement);
		manualTestWizardPage2.setTitle(I18N.i18n(ID + ".ManualTestWizardPage2.title"));
		manualTestWizardPage2.setDescription(I18N.i18n(ID + ".ManualTestWizardPage2.description"));
		manualTestWizardPage2.setImageDescriptor(ImageProvider.createImageDescriptor(ConfigImageRegistry.ICON_MANUAL_CHECK_64X64.getId()));
		addPage(manualTestWizardPage2);
		manualTestWizardPage3 = new ManualTestWizardPage3(manualTestResult, testConfigElement);
		manualTestWizardPage3.setTitle(I18N.i18n(ID + ".ManualTestWizardPage3.title"));
		manualTestWizardPage3.setDescription(I18N.i18n(ID + ".ManualTestWizardPage3.description"));
		manualTestWizardPage3.setImageDescriptor(ImageProvider.createImageDescriptor(ConfigImageRegistry.ICON_DETAIL_64X64.getId()));
		addPage(manualTestWizardPage3);
		manualTestWizardPage4 = new ManualTestWizardPage4(manualTestResult, testConfigElement);
		manualTestWizardPage4.setTitle(I18N.i18n(ID + ".ManualTestWizardPage4.title"));
		manualTestWizardPage4.setDescription(I18N.i18n(ID + ".ManualTestWizardPage4.description"));
		manualTestWizardPage4.setImageDescriptor(ImageProvider.createImageDescriptor(ConfigImageRegistry.ICON_DETAIL_64X64.getId()));
		addPage(manualTestWizardPage4);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performFinish() {
		// check for attached files
		List<String> fileNames = this.manualTestWizardPage4.getFileNames();
		for (String fileName : fileNames) {
			File f = new File(fileName);
			if (f.exists()) {
				FileInputStream fis;
				try {
					fis = new FileInputStream(f);
					byte[] b = new byte[(int) f.length()];
					fis.read(b);
					
					ProduceTraceDelegate produceTrace = ResultComponentServiceDelegateFactory.getInstance().getProduceTrace();
					TraceMsg rs = produceTrace.produceFileTrace(new EmptyServiceMessage());
					FileTrace fileTrace = (FileTrace) rs.getTrace();
					fileTrace.setName(f.getName());
					fileTrace.setFileContent(b);
					this.manualTestResult.getActionTraceList().add(fileTrace);
				} catch (FileNotFoundException e) {
					Activator.getDefault().logError(e);
				} catch (IOException e) {
					Activator.getDefault().logError(e);
				} catch (ClientException e) {
					Activator.getDefault().logError(e);
				}
			}
		}
		
		manualTestResult.setState(ManualState.FINISHED);
		ManualTestResultMsg rq = new ManualTestResultMsg();
		rq.setTestEngineConfiguration(configuration);
		rq.setTestExecutionInfo(info);
		rq.setTestResult(manualTestResult);
		try {
			ConfigComponentServiceDelegateFactory.getInstance().getTestEngineService().returnManualTestResult(rq);
		} catch (ClientException e) {
			Activator.getDefault().logError(e);
		}
		return true;
	}
	

	@Override
	public boolean performCancel() {
		manualTestResult.setState(ManualState.ABORTED);
		ManualTestResultMsg rq = new ManualTestResultMsg();
		rq.setTestEngineConfiguration(configuration);
		rq.setTestExecutionInfo(info);
		rq.setTestResult(manualTestResult);
		try {
			ConfigComponentServiceDelegateFactory.getInstance().getTestEngineService().returnManualTestResult(rq);
		} catch (ClientException e) {
			Activator.getDefault().logError(e);
		}
		return super.performCancel();
	}
	

	private TestConfigElement loadTestConfigElement() {
		SearchTestConfigElementDelegate searchTestConfigElement;
		try {
			searchTestConfigElement = ConfigComponentServiceDelegateFactory.getInstance().getSearchTestConfigElement();
			TestConfigElementSearchMsg rq = new TestConfigElementSearchMsg();
			rq.setId(this.manualTestResult.getTestConfigElementId());
			TestConfigElementMsg response;
			response = searchTestConfigElement.getTestConfigElement(rq);
			return response.getTestConfigElement();
		} catch (ClientException e) {
			Activator.getDefault().logError(e);
		}
		return null;
	}
	
}
