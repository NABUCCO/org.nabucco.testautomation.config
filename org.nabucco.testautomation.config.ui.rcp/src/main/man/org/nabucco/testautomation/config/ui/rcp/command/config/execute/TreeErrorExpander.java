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
package org.nabucco.testautomation.config.ui.rcp.command.config.execute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailBlock;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.path.MasterDetailTreePath;
import org.nabucco.framework.plugin.base.component.multipage.model.MultiPageEditViewModel;

import org.nabucco.testautomation.result.facade.datatype.TestConfigurationResult;
import org.nabucco.testautomation.result.facade.datatype.TestResult;
import org.nabucco.testautomation.result.facade.datatype.TestResultContainer;
import org.nabucco.testautomation.result.facade.datatype.TestScriptResult;
import org.nabucco.testautomation.result.facade.datatype.status.TestConfigElementStatusType;
import org.nabucco.testautomation.result.facade.datatype.status.TestConfigurationStatusType;
import org.nabucco.testautomation.result.facade.datatype.status.TestScriptStatusType;
import org.nabucco.testautomation.result.ui.rcp.multipage.result.maintenance.TestConfigurationResultMaintenanceMultiPageEditView;
import org.nabucco.testautomation.result.ui.rcp.multipage.result.maintenance.model.TestConfigurationResultMaintenanceMultiPageEditViewModel;

/**
 * TestResultViewUpdateThread
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TreeErrorExpander {


	/**
	 * Expands all error in the master detail tree of the given view.
	 * 
	 * @param view the view
	 * @param result the TestConfigurationResult to evaluate
	 */
	public void expandTree(
			TestConfigurationResultMaintenanceMultiPageEditView view,
			TestConfigurationResult result) throws ClientException {

		MasterDetailBlock<TestConfigurationResultMaintenanceMultiPageEditViewModel> masterDetailsBlock = view.getMasterDetailsBlock();
		TreeViewer treeViewer = masterDetailsBlock.getTreeViewer();

		TestConfigurationStatusType status = result.getStatus();

		if(status == TestConfigurationStatusType.FINISHED){
			// Expand to level 2
			treeViewer.collapseAll();
			treeViewer.expandToLevel(2);
			// Additionally expand all errors
			List<TestResultContainer> testResultList = result.getTestResultList();
			for (TestResultContainer testResultContainer : testResultList) {
				ArrayList<Integer> initialPath = new ArrayList<Integer>();
				initialPath.add(0);
				initialPath.add(testResultList.indexOf(testResultContainer));
				Set<TreePath> paths = getTreePathsToError(getRoot(treeViewer), testResultContainer.getResult(), initialPath);
				if(paths != null && paths.size() > 0){
					for (TreePath path : paths) {
						treeViewer.expandToLevel(path, 0);
					}
				}
			}
			return;
		} else {
			// Expand every path till TestScriptResult
			List<TestResultContainer> testResultList = result.getTestResultList();
			for (TestResultContainer testResultContainer : testResultList) {
				ArrayList<Integer> initialPath = new ArrayList<Integer>();
				initialPath.add(0);
				initialPath.add(testResultList.indexOf(testResultContainer));
				Set<TreePath> paths = getTreePathsToScriptResult(getRoot(treeViewer), testResultContainer.getResult(), initialPath);
				if(paths != null && paths.size() > 0){
					for (TreePath path : paths) {
						treeViewer.expandToLevel(path, 0);
					}
				}
			}
			return;
		}
	}

	private Set<TreePath> getTreePathsToError(MasterDetailTreeNode root, TestResult testResult, List<Integer> path) {
		Set<TreePath> result = new HashSet<TreePath>();
		if(testResult.getStatus() != TestConfigElementStatusType.FAILED){
			// If the current TestResult has not failed there is no path to display. Return an empty result set.
			return result;
		} else {
			// If the current TestResult has failed return current path
			if((testResult.getTestResultList() == null || testResult.getTestResultList().size() == 0) 
					&& (testResult.getTestScriptResultList() == null || testResult.getTestScriptResultList().size() == 0)){
				result.add((new MasterDetailTreePath(toIntArray(path))).getTreePath(root));
				return result;
			} else {
				// traverse children 
				List<TestResultContainer> testResultList = testResult.getTestResultList();
				for (TestResultContainer testResultContainer : testResultList) {
					List<Integer> newPath = new ArrayList<Integer>(path);
					newPath.add(testResultList.indexOf(testResultContainer));
					result.addAll(getTreePathsToError(root, testResultContainer.getResult(), newPath));
				}
				List<TestScriptResult> testScriptResultList = testResult.getTestScriptResultList();
				for (TestScriptResult testScriptResult : testScriptResultList) {
					List<Integer> newPath = new ArrayList<Integer>(path);
					newPath.add(testScriptResultList.indexOf(testScriptResult));
					result.addAll(getTreePathToError(root, testScriptResult, newPath)); 
				}
				return result;
			}
		} 
	}

	private Set<TreePath> getTreePathToError(MasterDetailTreeNode root, TestScriptResult testScriptResult, List<Integer> path) {
		Set<TreePath> result = new HashSet<TreePath>();
		if(testScriptResult.getStatus() != TestScriptStatusType.FAILED){
			// If the current TestResult has not failed there is no path to display. Return an empty result set.
			return result;
		} else {
			// If the current TestResult has failed return current path
			result.add((new MasterDetailTreePath(toIntArray(path))).getTreePath(root));
			return result;
		} 
	}


	private Set<TreePath> getTreePathsToScriptResult(MasterDetailTreeNode root, TestResult testResult, List<Integer> path) {
		Set<TreePath> result = new HashSet<TreePath>();
		if((testResult.getTestResultList() == null || testResult.getTestResultList().size() == 0) 
				&& (testResult.getTestScriptResultList() == null || testResult.getTestScriptResultList().size() == 0)){
			result.add((new MasterDetailTreePath(toIntArray(path))).getTreePath(root));
			return result;
		} else {
			// traverse children 
			List<TestResultContainer> testResultList = testResult.getTestResultList();
			for (TestResultContainer testResultContainer : testResultList) {
				List<Integer> newPath = new ArrayList<Integer>(path);
				newPath.add(testResultList.indexOf(testResultContainer));
				result.addAll(getTreePathsToScriptResult(root, testResultContainer.getResult(), newPath));
			}
			List<TestScriptResult> testScriptResultList = testResult.getTestScriptResultList();
			for (TestScriptResult testScriptResult : testScriptResultList) {
				List<Integer> newPath = new ArrayList<Integer>(path);
				newPath.add(testScriptResultList.indexOf(testScriptResult));
				result.add((new MasterDetailTreePath(toIntArray(newPath))).getTreePath(root)); 
			}
			return result;
		}
	}


	private int[] toIntArray(List<Integer> input){
		int[] pathAsArray = new int[input.size()];
		for(int i = 0; i < input.size(); i++){
			pathAsArray[i] = input.get(i);
		}
		return pathAsArray;
	}


	/**
	 * Retrieves the root node.
	 * 
	 * @param treeViewer
	 *            holding the tree
	 * 
	 * @return the root node
	 */
	private MasterDetailTreeNode getRoot(TreeViewer treeViewer) throws ClientException {
		Object input = treeViewer.getInput();
		if(input instanceof MultiPageEditViewModel){
			return ((MultiPageEditViewModel)input).getTreeStructure();
		}
		return null;
	}




}
