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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoDefaultTableSorter;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableColumnInfo;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableSorter;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.detail.widget.AbstractBaseTypeWidgetCreator;
import org.nabucco.framework.plugin.base.component.newpicker.composite.table.TablePickerComposite;
import org.nabucco.framework.plugin.base.component.newpicker.composite.table.TablePickerListener;
import org.nabucco.framework.plugin.base.component.newpicker.composite.table.TablePickerParameter;
import org.nabucco.framework.plugin.base.component.newpicker.composite.table.TablePickerRefreshListener;
import org.nabucco.framework.plugin.base.component.newpicker.dialog.tree.TreePickerDialog;
import org.nabucco.framework.plugin.base.component.newpicker.dialog.tree.TreePickerDialogLabel;
import org.nabucco.framework.plugin.base.component.newpicker.dialog.tree.TreePickerDialogParameter;
import org.nabucco.framework.plugin.base.model.ViewModel;
import org.nabucco.framework.plugin.base.view.NabuccoFormToolkit;
import org.nabucco.framework.plugin.base.view.NabuccoMessageManager;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.datatype.comparator.TestScriptSorter;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.listener.DownListener;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.listener.RemoveListener;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.listener.ReplaceListener;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.listener.TestScriptTreePickerDialogListener;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.listener.UpListener;
import org.nabucco.testautomation.property.ui.rcp.base.dialog.OwnerSelectionTreePickerDialog;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;
import org.nabucco.testautomation.script.ui.rcp.command.script.OpenTestScriptHandlerImpl;

/**
 * TestScriptTableWidgetCreator
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestScriptTableWidgetCreator extends AbstractBaseTypeWidgetCreator<List<TestScript>> {

    private static final String ID = "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.TestScriptTable";

    private static final String TITLE = ID + ".dialogTitle";

    private static final String SHELL_TITLE = ID + ".shellTitle";

    private static final String MESSAGE = ID + ".dialogMessage";

    public static final String MESSAGE_TABLE = ID + ".tableMessage";

    public static final String MESSAGE_COMBO = ID + ".dialogCombo";

    public static final String PATH_LABEL = ID + ".dialogPath";

    public static final String COLUMN_NAME_LABEL = ID + ".column.name.label";

    public static final String COLUMN_NAME_TOOLTIP = ID + ".column.name.tooltip";

    public static final String COLUMN_FOLDER_LABEL = ID + ".column.folder.label";

    public static final String COLUMN_FOLDER_TOOLTIP = ID + ".column.folder.tooltip";

    private static final String TREE_TITLE = ID + ".treeTitle";

    private static final String COLUMN_KEY_LABEL = ID + ".column.key.label";

    private static final String COLUMN_KEY_TOOLTIP = ID + ".column.key.tooltip";

    /**
     * Creates a new {@link TestScriptTableWidgetCreator} instance.
     * 
     * @param nft
     *            the form toolkit
     */
    public TestScriptTableWidgetCreator(NabuccoFormToolkit nft) {
        super(nft);
    }

    @Override
    protected Control createWidget(Composite parent, List<TestScript> script, Method method, Object testConfigElement,
            boolean readOnly, ViewModel viewModel, NabuccoMessageManager messageManager, String propertyName) {

        if (testConfigElement instanceof TestConfigElement) {
            return layoutTreeDialogPickerTable(parent, (TestConfigElement) testConfigElement, viewModel, readOnly);
        }

        return null;
    }

    /**
     * Layout the script table.
     * 
     * @param parent
     *            the parent composite
     * @param readOnly
     * 
     * @return the layouted table
     */
    private Control layoutTreeDialogPickerTable(Composite parent, TestConfigElement testConfigElement,
            ViewModel viewModel, boolean readOnly) {

        TestScriptTableMiniModel model = new TestScriptTableMiniModel(viewModel, testConfigElement);

        TablePickerComposite<TreePickerDialog> composite = this.TreeDialogPickerTable(parent, model, readOnly);
        composite.addDoubleClickListener(new IDoubleClickListener() {

            @Override
            public void doubleClick(DoubleClickEvent arg0) {
                TestScriptContainer testScriptContainer = (TestScriptContainer) ((IStructuredSelection) arg0
                        .getSelection()).getFirstElement();
                OpenTestScriptHandlerImpl openTestScriptHandlerImpl = new OpenTestScriptHandlerImpl(testScriptContainer
                        .getTestScriptRefId());
                openTestScriptHandlerImpl.run();
            }
        });

        TablePickerRefreshListener listener = new TablePickerRefreshListener(composite);
        model.addPropertyChangeListener(TestScriptTableMiniModel.PROPERTY_TEST_SCRIPT_LIST, listener);

        return composite;
    }

    /**
     * Create the table widget and listeners.
     * 
     * @param parent
     *            the parent composite
     * @param model
     *            the model
     * @param readOnly
     * 
     * @return the table picker composite
     */
    private TablePickerComposite<TreePickerDialog> TreeDialogPickerTable(Composite parent,
            TestScriptTableMiniModel model, boolean readOnly) {
        TablePickerParameter outerTableParameter = this.createParameters(model);

        TreePickerDialogLabel label = new TreePickerDialogLabel(TITLE, MESSAGE, SHELL_TITLE, "Tree", TREE_TITLE);

        TreePickerDialogParameter treeDialogParameter = new TreePickerDialogParameter(label, model);

        TestScriptTreePickerDialogContentProvider contentProvider = new TestScriptTreePickerDialogContentProvider();
        treeDialogParameter.setContentProvider(contentProvider);
        treeDialogParameter.setLabelProvider(new TestScriptTreePickerDialogLabelProvider());

        TreePickerDialog dialog = new OwnerSelectionTreePickerDialog(parent.getShell(), treeDialogParameter);

        dialog.addSelectionListener(TestScriptTreePickerDialogListener.ID,
                new TestScriptTreePickerDialogListener(model));

        return new TablePickerComposite<TreePickerDialog>(parent, SWT.NONE | TablePickerComposite.ALL_BUTTONS, 150,
                outerTableParameter, dialog, readOnly);
    }

    /**
     * Create the table widget parameters.
     * 
     * @param model
     *            the model
     * 
     * @return the parameters
     */
    private TablePickerParameter createParameters(TestScriptTableMiniModel model) {

        NabuccoTableSorter tableSorter = this.createTableSorter();
        NabuccoTableColumnInfo[] columnInfo = this.createTableColumnInfo();

        TestScriptTableContentProvider tableContent = new TestScriptTableContentProvider(model);

        return new TablePickerParameter(columnInfo, tableSorter, new TestScriptTableFilter(), tableContent,
                createListeners(model));
    }

    /**
     * Registers the table button listeners.
     * 
     * @param model
     *            the model to change
     * 
     * @return the listeners
     */
    private TablePickerListener createListeners(TestScriptTableMiniModel model) {
        RemoveListener removeListener = new RemoveListener(model);
        UpListener upListener = new UpListener(model);
        DownListener downListener = new DownListener(model);
        ReplaceListener replaceListener = new ReplaceListener(model);

        return new TablePickerListener(null, removeListener, upListener, downListener, replaceListener);
    }

    /**
     * Creates a comparator for the script table.
     * 
     * @return the table sorter
     */
    private NabuccoTableSorter createTableSorter() {
        List<Comparator<TestScriptContainer>> result = new ArrayList<Comparator<TestScriptContainer>>();
        result.add(new TestScriptSorter());
        return new NabuccoDefaultTableSorter<TestScriptContainer>(result);
    }

    /**
     * Create column information for the picker table.
     * 
     * @return the column information
     */
    private NabuccoTableColumnInfo[] createTableColumnInfo() {

        NabuccoTableColumnInfo info1 = new NabuccoTableColumnInfo(COLUMN_KEY_LABEL, COLUMN_KEY_TOOLTIP, 75, SWT.LEFT,
                SWT.CENTER, new TestScriptTableKeyColumnLabelProvider());

        info1.setResizable(true);
        info1.setMoveable(false);
        info1.setHidable(false);

        NabuccoTableColumnInfo info2 = new NabuccoTableColumnInfo(COLUMN_NAME_LABEL, COLUMN_NAME_TOOLTIP, 200,
                SWT.LEFT, SWT.CENTER, new TestScriptTableNameColumnLabelProvider());

        info2.setResizable(true);
        info2.setMoveable(false);
        info2.setHidable(false);

        NabuccoTableColumnInfo info3 = new NabuccoTableColumnInfo(COLUMN_FOLDER_LABEL, COLUMN_FOLDER_TOOLTIP, 200,
                SWT.LEFT, SWT.CENTER, new TestScriptTableFolderColumnLabelProvider());

        info3.setResizable(true);
        info3.setMoveable(false);

        return new NabuccoTableColumnInfo[] { info1, info2, info3 };
    }
}
