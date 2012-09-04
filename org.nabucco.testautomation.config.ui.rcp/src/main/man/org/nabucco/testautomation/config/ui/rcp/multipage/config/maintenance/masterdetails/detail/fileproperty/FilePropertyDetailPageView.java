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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.detail.fileproperty;

import java.util.Set;

import org.eclipse.ui.forms.IManagedForm;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailBlock;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.detail.GeneralDetailPageViewLayouter;
import org.nabucco.framework.plugin.base.view.ManagedFormViewPart;
import org.nabucco.framework.plugin.base.view.NabuccoMessageManager;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationMaintenanceMultiPageEditViewModel;
import org.nabucco.testautomation.property.ui.rcp.multipage.detail.PropertyDetailPageView;

/**
 * FilePropertyDetailPageView
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class FilePropertyDetailPageView extends PropertyDetailPageView<TestConfigurationMaintenanceMultiPageEditViewModel> {

    /**
     * Creates a new {@link FilePropertyDetailPageView} instance.
     * 
     * @param block
     *            the master detail block
     * @param managedForm
     *            the managed form
     * @param parentView
     *            the parent view
     * @param messageManager
     *            the message manager
     * @param masterId
     *            id of the master block
     * @param title
     *            the detail view title
     * @param invisibleProperties
     *            properties that are not visible
     * @param readOnlyProperties
     *            properties that are read-only
     */
    public FilePropertyDetailPageView(MasterDetailBlock<TestConfigurationMaintenanceMultiPageEditViewModel> block, IManagedForm managedForm,
            ManagedFormViewPart<TestConfigurationMaintenanceMultiPageEditViewModel> parentView, NabuccoMessageManager messageManager, String masterId,
            String title, Set<String> invisibleProperties, Set<String> readOnlyProperties) {
        super(block, managedForm, parentView, messageManager, masterId, title, invisibleProperties,
                readOnlyProperties);
    }

    @Override
    protected GeneralDetailPageViewLayouter getLayouter(String detailTitle) {
        return new FilePropertyDetailPageViewLayouter(detailTitle);
    }

}
