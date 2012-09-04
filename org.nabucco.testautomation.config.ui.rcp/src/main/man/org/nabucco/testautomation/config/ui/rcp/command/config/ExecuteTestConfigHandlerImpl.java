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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.command.AbstractOpenViewHandler;
import org.nabucco.framework.plugin.base.model.ViewModel;
import org.nabucco.framework.plugin.base.view.ManagedFormViewPart;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.engine.TestExecutionMsg;
import org.nabucco.testautomation.config.facade.message.engine.TestInfoMsg;
import org.nabucco.testautomation.config.ui.rcp.command.config.execute.ExecutionConstants;
import org.nabucco.testautomation.config.ui.rcp.command.config.execute.TestConfigExecutionJob;
import org.nabucco.testautomation.config.ui.rcp.communication.ConfigComponentServiceDelegateFactory;
import org.nabucco.testautomation.config.ui.rcp.communication.engine.TestEngineServiceDelegate;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationMaintenanceMultiPageEditViewModel;
import org.nabucco.testautomation.result.ui.rcp.multipage.result.maintenance.TestConfigurationResultMaintenanceMultiPageEditView;
import org.nabucco.testautomation.settings.facade.datatype.engine.TestEngineConfiguration;
import org.nabucco.testautomation.settings.facade.datatype.engine.TestExecutionInfo;

/**
 * ExecuteTestConfigHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ExecuteTestConfigHandlerImpl extends
        AbstractOpenViewHandler<TestConfigurationResultMaintenanceMultiPageEditView> implements
        ExecuteTestConfigHandler, ExecutionConstants {

    @Override
    public void executeTestConfig() {

        try {

            if (this.viewIsDirty()) {
                boolean saveSuccessful = this.save();

                if (!saveSuccessful) {
                    Activator.getDefault().logWarning("Cannot start execution for dirty view!");
                    return;
                }
            }

            TestInfoMsg response = this.startExecution();

            this.startJob(response.getTestExecutionInfo(), response.getTestEngineConfiguration());

        } catch (Exception e) {
            Activator.getDefault().logError("Test Execution did not finish successful!");
            Activator.getDefault().logError(e);
        }
    }

    /**
     * Checks the current view for dirty state.
     * 
     * @return <b>true</b> if the current view is dirty, <b/>false</b> if not
     */
    private boolean viewIsDirty() {
        ManagedFormViewPart<?> view = Activator.getDefault().getModel().getCurrentView();

        if (view != null && view.getModel() != null) {
            return view.getModel().isDirty();
        }

        return false;
    }

    /**
     * Save the current TestConfiguration view.
     * 
     * @return <b>true</b> if the view has been successfully saved, <b>false</b> if not
     */
    private boolean save() {
        boolean save = this.showRejectDialog(DIALOG_DIRTY_TITLE, DIALOG_DIRTY_MESSAGE);
        
        if (!save) {
            return false;
        }
        
        SaveTestConfigCommand saveCommand = new SaveTestConfigCommand();
        saveCommand.run();

        return !this.viewIsDirty();
    }

    /**
     * Start the execution process.
     * 
     * @return the response message
     * 
     * @throws Exception
     *             when the execution did not finish normally
     */
    private TestInfoMsg startExecution() throws Exception {
        TestConfiguration testConfiguration = this.getTestConfiguration();

        TestExecutionMsg request = new TestExecutionMsg();
        request.setTestConfiguration(testConfiguration);
        Subject subject = Activator.getDefault().getModel().getSecurityModel().getSubject();

        if (subject == null || subject.getUser() == null) {
            Activator.getDefault().logWarning("Starting execution for unauthenticated user.");
        } else {
            request.setUser(subject.getUser());
        }

        TestEngineServiceDelegate testEngineService = ConfigComponentServiceDelegateFactory
                .getInstance().getTestEngineService();

        return testEngineService.executeTestConfiguration(request);
    }

    /**
     * Load the current selected test configuration.
     * 
     * @return the current test configuration
     */
    private TestConfiguration getTestConfiguration() {
        ViewModel viewModel = Activator.getDefault().getModel().getCurrentViewModel();

        if (viewModel instanceof TestConfigurationMaintenanceMultiPageEditViewModel) {
            TestConfigurationMaintenanceMultiPageEditViewModel model = (TestConfigurationMaintenanceMultiPageEditViewModel) viewModel;

            return model.getTestConfiguration();
        }

        return null;
    }

    /**
     * Initializes the execution queue.
     * 
     * @param engineInfo
     *            the engine information
     * @param engineConfiguration
     *            the engine configuration
     * 
     * @throws ClientException
     */
    private void startJob(TestExecutionInfo engineInfo, TestEngineConfiguration engineConfiguration)
            throws ClientException {

        if (engineInfo == null) {
            throw new IllegalArgumentException("Cannot Start Job for arguments [null].");
        }

        if (engineInfo.getTestStatus() == null) {
            throw new IllegalArgumentException("Cannot Start Job for status [null].");
        }

        switch (engineInfo.getTestStatus()) {

        case REJECTED:
            Activator.getDefault().logError("Test Execution rejected.");
            boolean retry = this.showRejectDialog(DIALOG_REJECTED_TITLE, DIALOG_REJECTED_MESSAGE);

            if (retry) {
                this.executeTestConfig();
            }

            break;

        case INTERRUPTED:
            Activator.getDefault().logWarning("Test Execution interrupted.");
            break;

        default:
            
            if (engineConfiguration == null) {
                throw new IllegalArgumentException("Cannot Start Job because of missing TestEngineConfiguration");
            }
            
            Activator.getDefault().logInfo("Test Execution started successfully.");

            TestConfigurationResultMaintenanceMultiPageEditView view = this.openView();

            this.scheduleJob(engineConfiguration, engineInfo, view);

            break;
        }
    }

    /**
     * Opens the TestResult view.
     * 
     * @return the opened view
     */
    private TestConfigurationResultMaintenanceMultiPageEditView openView() {
        String viewId = TestConfigurationResultMaintenanceMultiPageEditView.ID;

        super.openView(viewId);

        ManagedFormViewPart<?> view = Activator.getDefault().getView(viewId);

        if (view instanceof TestConfigurationResultMaintenanceMultiPageEditView) {
            return (TestConfigurationResultMaintenanceMultiPageEditView) view;
        }

        return null;
    }

    /**
     * Shows the error dialog.
     * 
     * @param title
     *            the I18N key for the dialog title
     * @param message
     *            the I18N key for the dialog message
     * 
     * @return <b>true</b> when ok clicked, <b>false</b> otherwise
     */
    private boolean showRejectDialog(String title, String message) {
        Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
        return MessageDialog.openConfirm(shell, I18N.i18n(title), I18N.i18n(message));
    }

    /**
     * Starts the execution Job.
     * 
     * @param configuration
     *            the engine configuration
     * @param info
     *            the current execution information
     * @param view
     *            the view
     * 
     * @return the started job
     * 
     * @throws ClientException
     */
    private TestConfigExecutionJob scheduleJob(TestEngineConfiguration configuration,
            TestExecutionInfo info, TestConfigurationResultMaintenanceMultiPageEditView view)
            throws ClientException {

        if (view == null) {
            throw new IllegalArgumentException("Cannot Start Job for view [null].");
        }

        Name name = configuration.getName();

        TestConfigExecutionJob job = new TestConfigExecutionJob(name.getValue(), configuration,
                info, view);

        job.setUser(true);
        job.schedule();

        return job;
    }
}
