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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails;

import java.util.Map;

import org.eclipse.jface.viewers.IDecoration;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.ui.rcp.images.ConfigImageRegistry;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.TestConfigurationMaintainanceMultiplePageEditView;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.result.facade.datatype.ExecutionType;

public class TreeNodeDecorator {

	private static String ICON_REUSED = "icons/reused.png";
	private static String ICON_SKIPPED = ConfigImageRegistry.ICON_SKIP_8X8.getId();
	private static String ICON_MANUAL = "icons/hand_8x8.png";

	public static void decorateNode(MasterDetailTreeNode node, Datatype datatype) {
		boolean refresh = false;
		// Reused PropertyLists and ConfigElements should be decorated
		if (datatype instanceof TestConfigElementContainer) {
			TestConfigElement newTestConfigElement = ((TestConfigElementContainer) datatype).getElement();
			Map<Integer, String> decorations = node.getDecorations();
			if (newTestConfigElement.getReused() != null && newTestConfigElement.getReused().getValue()) {
				if (!decorations.containsKey(IDecoration.BOTTOM_RIGHT)) {
					decorations.put(IDecoration.BOTTOM_RIGHT, ICON_REUSED);
					refresh = true;
				}
			} else {
				if (decorations.containsKey(IDecoration.BOTTOM_RIGHT)) {
					decorations.remove(IDecoration.BOTTOM_RIGHT);
					refresh = true;
				}
			}
		}
		if (datatype instanceof TestConfigElement) {
			TestConfigElement newTestConfigElement = (TestConfigElement) datatype;
			Map<Integer, String> decorations = node.getDecorations();
			if (newTestConfigElement.getReused() != null && newTestConfigElement.getReused().getValue()) {
				if (!decorations.containsKey(IDecoration.BOTTOM_RIGHT)) {
					decorations.put(IDecoration.BOTTOM_RIGHT, ICON_REUSED);
					refresh = true;
				}
			} else {
				if (decorations.containsKey(IDecoration.BOTTOM_RIGHT)) {
					decorations.remove(IDecoration.BOTTOM_RIGHT);
					refresh = true;
				}
			}
		} else if (datatype instanceof PropertyList) {
			PropertyList newProperty = (PropertyList) datatype;
			Map<Integer, String> decorations = node.getDecorations();
			if (newProperty.getReused() != null && newProperty.getReused().getValue()) {
				if (!decorations.containsKey(IDecoration.BOTTOM_RIGHT)) {
					decorations.put(IDecoration.BOTTOM_RIGHT, ICON_REUSED);
					refresh = true;
				}
			} else {
				if (decorations.containsKey(IDecoration.BOTTOM_RIGHT)) {
					decorations.remove(IDecoration.BOTTOM_RIGHT);
					refresh = true;
				}
			}
		}

		// Decorate "skip" and "executionType"
		if (datatype instanceof TestConfigElement) {
			TestConfigElement testConfigElement = (TestConfigElement) datatype;
			Map<Integer, String> decorations = node.getDecorations();
			if (testConfigElement.getSkip() != null && testConfigElement.getSkip().getValue() != null && testConfigElement.getSkip().getValue()) {
				if (!decorations.containsKey(IDecoration.TOP_LEFT)) {
					decorations.put(IDecoration.TOP_LEFT, ICON_SKIPPED);
					refresh = true;
				}
			} else {
				if (decorations.containsKey(IDecoration.TOP_LEFT)) {
					decorations.remove(IDecoration.TOP_LEFT);
					refresh = true;
				}

			}
			if (testConfigElement.getExecutionType() != null && testConfigElement.getExecutionType() == ExecutionType.MANUAL) {
				if (!decorations.containsKey(IDecoration.TOP_RIGHT)) {
					decorations.put(IDecoration.TOP_RIGHT, ICON_MANUAL);
					refresh = true;
				}
			} else {
				if (decorations.containsKey(IDecoration.TOP_RIGHT)) {
					decorations.remove(IDecoration.TOP_RIGHT);
					refresh = true;
				}

			}
		}
		// refreshTree
		if (refresh) {
			TestConfigurationMaintainanceMultiplePageEditView view = (TestConfigurationMaintainanceMultiplePageEditView) Activator.getDefault().getView(
					TestConfigurationMaintainanceMultiplePageEditView.ID);
			view.getMasterDetailsBlock().getTreeViewer().refresh();
		}
	}

}
