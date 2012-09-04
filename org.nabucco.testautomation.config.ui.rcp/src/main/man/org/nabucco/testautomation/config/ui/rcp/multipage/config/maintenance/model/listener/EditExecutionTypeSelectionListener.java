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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.visitor.EditExecutionTypeVisitor;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.TestConfigurationMaintenanceMultiPageEditView;
import org.nabucco.testautomation.result.facade.datatype.ExecutionType;

/**
 * EditExecutionTypeSelectionListener
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class EditExecutionTypeSelectionListener implements SelectionListener {

    private ExecutionType executionType;

    private TestConfigElement element;

    /**
     * @param model
     * @param treeViewer
     * @param elementPickerParameter
     * @param labelForDialog
     */
    public EditExecutionTypeSelectionListener(TestConfigElement element, ExecutionType executionType) {
        this.executionType = executionType;
        this.element = element;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
        // Nothing to do here!
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {

        EditExecutionTypeVisitor visitor = new EditExecutionTypeVisitor(this.executionType);
        try {
            this.element.accept(visitor);

            if (visitor.isDirty()) {
                TestConfigurationMaintenanceMultiPageEditView view = (TestConfigurationMaintenanceMultiPageEditView) Activator
                        .getDefault()
                        .getView(
                                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.TestConfigurationMaintenanceMultiPageEditView");
                view.getModel().setDirty(true);
            }
        } catch (VisitorException e) {
            e.printStackTrace();
        }

    }
}
