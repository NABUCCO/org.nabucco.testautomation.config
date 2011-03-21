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
package org.nabucco.testautomation.config.ui.rcp.command.config.execute;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.layout.ImageProvider;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestResultMsg;
import org.nabucco.testautomation.config.ui.rcp.command.config.execute.wizard.manualtest.ManualTestWizard;
import org.nabucco.testautomation.config.ui.rcp.communication.ConfigComponentServiceDelegateFactory;
import org.nabucco.testautomation.config.ui.rcp.communication.engine.TestEngineServiceDelegate;
import org.nabucco.testautomation.config.ui.rcp.images.ConfigImageRegistry;

import org.nabucco.testautomation.facade.datatype.engine.ExecutionStatusType;
import org.nabucco.testautomation.facade.datatype.engine.TestEngineConfiguration;
import org.nabucco.testautomation.facade.datatype.engine.TestExecutionInfo;
import org.nabucco.testautomation.facade.exception.engine.TestEngineException;
import org.nabucco.testautomation.result.facade.datatype.TestConfigurationResult;
import org.nabucco.testautomation.result.facade.datatype.status.TestConfigurationStatusType;
import org.nabucco.testautomation.result.ui.rcp.multipage.result.maintenance.TestConfigurationResultMaintenanceMultiPageEditView;

/**
 * TestConfigExecutionJob
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TestConfigExecutionJob extends Job implements ExecutionConstants {

	private TestEngineConfiguration configuration;

	private TestExecutionInfo info;

	private TestEngineServiceDelegate service;

	private TestConfigurationResultMaintenanceMultiPageEditView view;

	private static final int SLEEP_TIME = 2000;
	
	private boolean manualTestResultFinished = false;

	/**
	 * Creates a new {@link TestConfigExecutionJob} instance.
	 * 
	 * @param name
	 *            the job name
	 * @param configuration
	 *            the test engine configurationb
	 * @param info
	 *            the test engine information
	 * 
	 * @throws ClientException
	 *             when the remote server is unavailable
	 */
	public TestConfigExecutionJob(String name,
			TestEngineConfiguration configuration, TestExecutionInfo info,
			TestConfigurationResultMaintenanceMultiPageEditView view)
			throws ClientException {
		super(name);

		if (configuration == null) {
			throw new IllegalStateException(
					"Cannot start execution with TestEngineConfiguration [null].");
		}
		if (info == null) {
			throw new IllegalStateException(
					"Cannot start execution with TestExecutionInfo [null].");
		}
		if (view == null) {
			throw new IllegalStateException(
					"Cannot start execution for View [null].");
		}

		this.configuration = configuration;
		this.info = info;
		this.view = view;

		this.init();
	}

	/**
	 * Initializes the execution job.
	 * 
	 * @throws ServiceException
	 *             when the engine service is unavailable
	 * @throws ClientException
	 */
	private void init() throws ClientException {
		this.service = ConfigComponentServiceDelegateFactory.getInstance()
				.getTestEngineService();
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		try {
			this.execute(0);

		} catch (Exception e) {
			Activator.getDefault().logError(e);
			return Status.CANCEL_STATUS;
		}

		return Status.OK_STATUS;
	}

	/**
	 * Getter for the configuration.
	 * 
	 * @return Returns the configuration.
	 */
	public TestEngineConfiguration getConfiguration() {
		return this.configuration;
	}

	/**
	 * Getter for the info.
	 * 
	 * @return Returns the info.
	 */
	public TestExecutionInfo getInfo() {
		return this.info;
	}

	/**
	 * Executes the TestEngine with the given sleep time.
	 * 
	 * @param sleepTime
	 *            the milliseconds to sleep
	 * 
	 * @throws Exception
	 *             whether the execution did not end normally
	 */
	private void execute(int sleepTime) throws Exception {

		this.sleep(sleepTime);
		TestConfigurationResult result = this.retrieveResult();

		if (result == null) {

			Activator.getDefault().logWarning(
					"Execution hung up unexpectedly, retry.");

			this.retrieveStatus();

			if (this.info == null) {
				Activator.getDefault().logWarning(
						"Execution hung up unexpectedly, info [null].");
				this.showErrorDialog();
				return;
			}

			ExecutionStatusType status = this.info.getTestStatus();
			if (status != ExecutionStatusType.RUNNING) {
				Activator.getDefault().logWarning(
						"Execution hung up unexpectedly, status [" + status
								+ "].");
				this.showErrorDialog();
				return;
			}

			result = this.retrieveResult();

			if (result == null) {
				Activator.getDefault().logWarning(
						"Execution hung up unexpectedly, result [null].");

				this.showErrorDialog();
				return;
			}
		}

		this.evaluate(result);
	}

	/**
	 * Evaluate the test result.
	 * 
	 * @param result
	 *            the result to evaluate
	 * 
	 * @throws Exception
	 */
	private void evaluate(TestConfigurationResult result) throws Exception {
		TestConfigurationStatusType status = result.getStatus();

		if (status == null) {
			throw new IllegalStateException(
					"Execution did not finish successful, status [null].");
		}

		Activator.getDefault().logDebug(
				"TestEngine Execution Status: " + status);

		switch (status) {

		case CANCELLED:
		case FINISHED:
			this.updateModel(result);
			break;

		case INITIALIZED:
			this.updateModel(result);
			this.execute(SLEEP_TIME);
			break;

		case RUNNING:
			this.updateModel(result);
			this.execute(SLEEP_TIME);
			break;
			
		case WAITING:
			userRequest(result);
			this.execute(0);
			break;

		default:
			break;
		}
	}

	private boolean userRequest(final TestConfigurationResult result) {
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				ManualTestWizard wizard = new ManualTestWizard(result, configuration, info);
	        	wizard.init(Activator.getDefault().getWorkbench(), StructuredSelection.EMPTY);
	        	WizardDialog wizardDialog = new WizardDialog(null, wizard);
	        	wizardDialog.setBlockOnOpen(true);
	        	wizardDialog.setPageSize(600, 450);
	        	wizardDialog.setTitleImage(ImageProvider.createImage(ConfigImageRegistry.ICON_CONFIG.getId()));
	        	wizardDialog.open();
			}
		});
		return manualTestResultFinished;
	}

	/**
	 * Updates the test result view model.
	 * 
	 * @param result
	 *            the new result
	 */
	private void updateModel(final TestConfigurationResult result) {
		Runnable runnable = new TestResultModelUpdateThread(this.view, result);
		Display.getDefault().asyncExec(runnable);
	}

	/**
	 * Retrieves the current engine status.
	 * 
	 * @throws TestEngineException
	 *             when the execution did not end normally
	 * @throws ClientException
	 */
	private void retrieveStatus() throws ClientException {

		TestInfoMsg request = new TestInfoMsg();
		request.setTestExecutionInfo(this.info);
		request.setTestEngineConfiguration(this.configuration);

		TestInfoMsg response = this.service.getTestStatus(request);

		// if (response.getTestExecutionInfo() == null) {
		// Activator.getDefault().logWarning(
		// "Execution did not finish successful, TestExecutionInfo was [null].");
		// }
		// if (response.getTestEngineConfiguration() == null) {
		// Activator.getDefault().logWarning(
		// "Execution did not finish successful, TestEngineConfiguration was [null].");
		// }

		this.info = response.getTestExecutionInfo();
		this.configuration = response.getTestEngineConfiguration();
	}

	/**
	 * Retrieves the current engine result.
	 * 
	 * @return the result
	 * 
	 * @throws TestEngineException
	 *             when the execution did not end normally
	 * @throws ClientException
	 */
	private TestConfigurationResult retrieveResult() throws ClientException {

		TestInfoMsg request = new TestInfoMsg();
		request.setTestExecutionInfo(this.info);
		request.setTestEngineConfiguration(this.configuration);

		TestResultMsg response = this.service
				.getTestConfigurationResult(request);

		return response.getTestResult();
	}

	/**
	 * Sets the current job asleep for the amount of milliseconds.
	 * 
	 * @param sleepTime
	 *            the milliseconds to sleep
	 * 
	 * @throws InterruptedException
	 */
	private void sleep(int sleepTime) throws InterruptedException {
		if (sleepTime > 0) {
			Thread.sleep(sleepTime);
		}
	}

	/**
	 * Shows the error dialog.
	 * 
	 * @return <b>true</b> when ok clicked, <b>false</b> otherwise
	 */
	private void showErrorDialog() {
		Runnable runnable = new TestResultErrorDialogThread();
		Display.getDefault().asyncExec(runnable);
	}

	@Override
	protected void canceling() {
		if (configuration != null && info != null && service != null) {
			TestInfoMsg rq = new TestInfoMsg();
			rq.setTestEngineConfiguration(configuration);
			rq.setTestExecutionInfo(info);

			try {
				service.cancelTestConfiguration(rq);
			} catch (ClientException e) {
				Activator.getDefault().logError(e);
			}
		}
	}
}
