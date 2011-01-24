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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.dependency;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.comparator.DependencySorter;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.dependency.listeners.RemoveListener;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.dependency.listeners.TestConfigElementTreePickerDialogListener;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.script.listener.TestScriptTreePickerDialogListener;


/**
 * DependencyTableWidgetCreator
 * 
 * @author Marco Sussek, PRODYNA AG
 */
public class DependencyTableWidgetCreator extends
		AbstractBaseTypeWidgetCreator<List<TestConfigElement>> {

	private static final String ID = "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.script.DependencyTable";

	private static final String TITLE = ID + ".dialogTitle";

	private static final String SHELL_TITLE = ID + ".shellTitle";

	private static final String MESSAGE = ID + ".dialogMessage";

	public static final String MESSAGE_TABLE = ID + ".tableMessage";

	public static final String MESSAGE_COMBO = ID + ".dialogCombo";

	public static final String PATH_LABEL = ID + ".dialogPath";

	public static final String COLUMN_NAME_LABEL = ID + ".column.name.label";

	public static final String COLUMN_NAME_TOOLTIP = ID
			+ ".column.name.tooltip";

	private static final String TREE_TITLE = ID + ".treeTitle";

	private static final String COLUMN_KEY_TOOLTIP = ID + ".column.key.tooltip";

	private static final String COLUMN_KEY_LABEL = ID + ".column.key.label";

	/**
	 * Creates a new {@link DependencyTableWidgetCreator} instance.
	 * 
	 * @param nft
	 *            the form toolkit
	 */
	public DependencyTableWidgetCreator(NabuccoFormToolkit nft) {
		super(nft);
	}

	@Override
	protected Control createWidget(Composite parent,
			List<TestConfigElement> script, Method method,
			Object testConfigElement, boolean readOnly, ViewModel viewModel,
			NabuccoMessageManager messageManager, String propertyName) {

		if (testConfigElement instanceof TestConfigElement) {
			return layoutTreeDialogPickerTable(parent,
					(TestConfigElement) testConfigElement, viewModel);
		}

		return null;
	}

	/**
	 * Layout the script table.
	 * 
	 * @param parent
	 *            the parent composite
	 * 
	 * @return the layouted table
	 */
	private Control layoutTreeDialogPickerTable(Composite parent,
			TestConfigElement testConfigElement, ViewModel viewModel) {

		DependencyTableMiniModel model = new DependencyTableMiniModel(
				viewModel, testConfigElement);

		TablePickerComposite<TreePickerDialog> composite = this
				.createTreeDialogPickerTable(parent, model);

		TablePickerRefreshListener listener = new TablePickerRefreshListener(
				composite);
		model.addPropertyChangeListener(
				DependencyTableMiniModel.PROPERTY_DEPENDS_ON_LIST, listener);

		return composite;
	}

	/**
	 * Create the table widget and listeners.
	 * 
	 * @param parent
	 *            the parent composite
	 * @param model
	 *            the model
	 * 
	 * @return the table picker composite
	 */
	private TablePickerComposite<TreePickerDialog> createTreeDialogPickerTable(
			Composite parent, DependencyTableMiniModel model) {
		TablePickerParameter outerTableParameter = this.createParameters(model);

		TreePickerDialogLabel label = new TreePickerDialogLabel(TITLE, MESSAGE,
				SHELL_TITLE, "Tree", TREE_TITLE);

		TreePickerDialogParameter treeDialogParameter = new TreePickerDialogParameter(
				label, model);

		treeDialogParameter
				.setContentProvider(new TestConfigElementTreePickerDialogContentProvider());
		treeDialogParameter
				.setLabelProvider(new TestConfigElementTreePickerDialogLabelProvider());

		TreePickerDialog dialog = new TreePickerDialog(parent.getShell(),
				treeDialogParameter);

		dialog.addSelectionListener(TestScriptTreePickerDialogListener.ID,
				new TestConfigElementTreePickerDialogListener(model));

		return new TablePickerComposite<TreePickerDialog>(parent, SWT.NONE
				| TablePickerComposite.ADD_BUTTON
				| TablePickerComposite.REMOVE_BUTTON, 150, outerTableParameter,
				dialog);
	}

	/**
	 * Create the table widget parameters.
	 * 
	 * @param model
	 *            the model
	 * 
	 * @return the parameters
	 */
	private TablePickerParameter createParameters(DependencyTableMiniModel model) {

		NabuccoTableSorter tableSorter = this.createTableSorter();
		NabuccoTableColumnInfo[] columnInfo = this.createTableColumnInfo();

		DependencyTableContentProvider tableContent = new DependencyTableContentProvider(
				model);

		return new TablePickerParameter(columnInfo, tableSorter,
				new DependencyTableFilter(), tableContent,
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
	private TablePickerListener createListeners(DependencyTableMiniModel model) {
		RemoveListener removeListener = new RemoveListener(model);

		return new TablePickerListener(null, removeListener, null, null);
	}

	/**
	 * Creates a comparator for the script table.
	 * 
	 * @return the table sorter
	 */
	private NabuccoTableSorter createTableSorter() {
		List<Comparator<Dependency>> result = new ArrayList<Comparator<Dependency>>();
		result.add(new DependencySorter());
		return new NabuccoDefaultTableSorter<Dependency>(result);
	}

	/**
	 * Create column information for the picker table.
	 * 
	 * @return the column information
	 */
	private NabuccoTableColumnInfo[] createTableColumnInfo() {
		NabuccoTableColumnInfo info1 = new NabuccoTableColumnInfo(
				COLUMN_KEY_LABEL, COLUMN_KEY_TOOLTIP, 75, SWT.LEFT,
				SWT.CENTER, new DependencyTableKeyColumnLabelProvider());

		info1.setResizable(true);
		info1.setMoveable(false);

		NabuccoTableColumnInfo info2 = new NabuccoTableColumnInfo(
				COLUMN_NAME_LABEL, COLUMN_NAME_TOOLTIP, 250, SWT.LEFT,
				SWT.CENTER, new DependencyTableNameColumnLabelProvider());

		info2.setResizable(true);
		info2.setMoveable(false);

		return new NabuccoTableColumnInfo[] { info1, info2 };
	}

}
