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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Map;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailBlock;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailHelper;
import org.nabucco.framework.plugin.base.component.multipage.view.MultiPageEditView;
import org.nabucco.framework.plugin.base.component.multipage.xml.XMLEditorPage;
import org.nabucco.framework.plugin.base.component.multipage.xml.example.XmlDefaultPage;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.TestConfigurationMaintenanceMasterDetailBlock;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationCopyPasteHandler;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationMaintenanceMultiPageEditViewModel;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationTreeDragAndDropHandler;
import org.nabucco.testautomation.property.facade.service.history.HistoryEntry;
import org.nabucco.testautomation.property.facade.service.history.HistoryEntryType;
import org.nabucco.testautomation.property.facade.service.history.HistoryService;

/**
 * TestConfigurationMaintenanceMultiPageEditView
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationMaintenanceMultiPageEditView extends
        MultiPageEditView<TestConfigurationMaintenanceMultiPageEditViewModel> {

    public static final String ID = "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.TestConfigurationMaintenanceMultiPageEditView";

    public static final String TITLE = ID + ".title";

    public static final String TAB_TITLE = ID + ".tabTitle";

    private static final String TOOLTIP_DELETE = "Delete";

    private static final String TOOLTIP_SAVE = "Save";

    private static final String TOOLTIP_IMPORT = "Import";

    private static final String TOOLTIP_RELOAD = "Reload";

    private TestConfigurationMaintenanceMasterDetailBlock masterDetailBlock;

    /**
     * Creates a new {@link TestConfigurationMaintenanceMultiPageEditView} instance.
     */
    public TestConfigurationMaintenanceMultiPageEditView() throws ClientException {
        model = new TestConfigurationMaintenanceMultiPageEditViewModel();
        TestConfiguration testConfiguration = new TestConfiguration();
        testConfiguration.setName("");
        model.setTestConfiguration(testConfiguration);
        model.setCopyPasteHandler(new TestConfigurationCopyPasteHandler());
        model.setDragAndDropHandler(new TestConfigurationTreeDragAndDropHandler());
    }

    @Override
    protected String getManagedFormTitle() {
        return I18N.i18n(TITLE, getValues());
    }

    @Override
    public MasterDetailBlock<TestConfigurationMaintenanceMultiPageEditViewModel> getMasterDetailsBlock() {
        if (masterDetailBlock == null) {
            masterDetailBlock = new TestConfigurationMaintenanceMasterDetailBlock(model, getMessageManager(), this);
        }
        return masterDetailBlock;
    }

    @Override
    public XMLEditorPage getXMLPage() {
        return new XmlDefaultPage();
    }

    public Map<String, Serializable> getValues() {
        return model.getValues();
    }

    @Override
    public String getNewPartName() {
        return I18N.i18n(TAB_TITLE, getValues());
    }

    @Override
    public void postOpen() {
        // Activate or deactivate Reload Button
        refreshReloadButtonState();
        super.model.addPropertyChangeListener("datatype", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                refreshReloadButtonState();
            }
        });

        // Add HistoryEntry
        TestConfiguration testConfiguration = super.model.getTestConfiguration();

        if (testConfiguration.getDatatypeState() != DatatypeState.INITIALIZED) {
            HistoryEntry entry = new HistoryEntry(HistoryEntryType.TEST_CONFIGURATION, new Identifier(
                    testConfiguration.getId()), testConfiguration.getIdentificationKey(), testConfiguration.getName(),
                    testConfiguration.getDescription());
            HistoryService.getInstance().addHistoryEntry(entry);
        }
    }

    private void refreshReloadButtonState() {
        // TODO Develop general concept in plugin.base
        final ToolBar toolBar = ((ToolBarManager) getToolBarManager()).getControl();
        ToolItem reloadItem = null;
        ToolItem importItem = null;
        ToolItem saveItem = null;
        ToolItem deleteItem = null;

        for (ToolItem item : toolBar.getItems()) {

            String toolTip = item.getToolTipText();
            if (toolTip != null) {
                if (toolTip.equals(TOOLTIP_RELOAD)) {
                    reloadItem = item;
                } else if (toolTip.equals(TOOLTIP_IMPORT)) {
                    importItem = item;
                } else if (toolTip.equals(TOOLTIP_SAVE)) {
                    saveItem = item;
                } else if (toolTip.equals(TOOLTIP_DELETE)) {
                    deleteItem = item;
                }
            }
        }

        if (reloadItem != null) {
            if (super.getModel().getTestConfiguration().getDatatypeState() == DatatypeState.INITIALIZED
                    || !MasterDetailHelper.isDatatypeEditable(super.getModel().getTestConfiguration())) {
                reloadItem.setEnabled(false);
            } else {
                reloadItem.setEnabled(true);
            }
        }
        if (importItem != null) {
            if (MasterDetailHelper.isImportPossible(super.getModel().getTestConfiguration())) {
                importItem.setEnabled(true);
            } else {
                importItem.setEnabled(false);
            }
        }
        if (saveItem != null) {
            if (MasterDetailHelper.isDatatypeEditable(super.getModel().getTestConfiguration())) {
                saveItem.setEnabled(true);
            } else {
                saveItem.setEnabled(false);
            }
        }
        if (deleteItem != null) {
            if (MasterDetailHelper.isDatatypeEditable(super.getModel().getTestConfiguration())) {
                deleteItem.setEnabled(true);
            } else {
                deleteItem.setEnabled(false);
            }
        }
    }

    @Override
    public String getId() {
        return ID;
    }

}
