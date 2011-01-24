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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StructuredSelection;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.newpicker.dialog.tree.TreePickerDialogContentProvider;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.TestConfigurationMaintainanceMultiplePageEditView;


/**
 * TestConfigElementTreePickerDialogContentProvider
 * 
 * @author Marco Sussek, PRODYNA AG
 */
public class TestConfigElementTreePickerDialogContentProvider extends
		TreePickerDialogContentProvider<Datatype> {

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	@Override
	public Datatype[] getElements(Object element) {
		if (element instanceof DependencyTableMiniModel) {
			List<Datatype> testConfigElements = this.loadTestConfigElements();
			return testConfigElements.toArray(new Datatype[testConfigElements
					.size()]);
		}

		return null;
	}

	private List<Datatype> loadTestConfigElements() {
		TestConfigurationMaintainanceMultiplePageEditView view = (TestConfigurationMaintainanceMultiplePageEditView) Activator
				.getDefault().getView(
						TestConfigurationMaintainanceMultiplePageEditView.ID);
		MasterDetailTreeNode selection = (MasterDetailTreeNode) ((StructuredSelection) view
				.getMasterDetailsBlock().getTreeViewer().getSelection())
				.getFirstElement();
		List<MasterDetailTreeNode> siblings = selection.getParent()
				.getChildren();

		List<Datatype> testConfigElements = new ArrayList<Datatype>();
		for (MasterDetailTreeNode masterDetailTreeNode : siblings) {
			if (masterDetailTreeNode.getDatatype() instanceof TestConfigElement) {
				testConfigElements.add(masterDetailTreeNode.getDatatype());
			}
		}

		return testConfigElements;
	}

	@Override
	public Datatype[] getChildren(Object parentElement) {
		return null;
	}

}
