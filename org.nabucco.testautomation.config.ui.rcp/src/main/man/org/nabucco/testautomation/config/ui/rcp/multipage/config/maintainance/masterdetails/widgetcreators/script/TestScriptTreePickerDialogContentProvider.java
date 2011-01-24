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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.script;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollection;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.newpicker.dialog.tree.TreePickerDialogContentProvider;

import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;
import org.nabucco.testautomation.script.facade.datatype.dictionary.base.Folder;
import org.nabucco.testautomation.script.facade.message.FolderMsg;
import org.nabucco.testautomation.script.ui.rcp.communication.ScriptComponentServiceDelegateFactory;
import org.nabucco.testautomation.script.ui.rcp.communication.search.SearchFolderDelegate;

/**
 * TestScriptTreePickerDialogContentProvider
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestScriptTreePickerDialogContentProvider extends TreePickerDialogContentProvider<Datatype> {

	 @Override
	    public boolean hasChildren(Object element) {
	        if (element instanceof Folder) {
	            List<Folder> children = ((Folder) element).getSubFolderList();
	            List<TestScript> testScripts = ((Folder) element).getTestScriptList();
	            if (this.isLazy(children) || this.isLazy(testScripts)) {
	                return true;
	            }
	            return (children.size() > 0 || testScripts.size() > 0);

	        } else if (element instanceof TestScript) {
	            return false;
	        }
	        return false;
	    }

	    @Override
	    public Datatype[] getChildren(Object element) {
	        if (element instanceof Folder) {
	        	Folder folder = (Folder) element;
	        	
	        	List<Folder> subFolderList = folder.getSubFolderList();
	        	List<TestScript> testScriptList = folder.getTestScriptList();

	        	List<Datatype> result = new ArrayList<Datatype>();
	            if (!this.isLazy(subFolderList)) {
	                result.addAll(subFolderList);
	            }
	            if (!this.isLazy(testScriptList)) {
	            	result.addAll(testScriptList);
	            }
	            return result.toArray(new Datatype[result.size()]);
	        }
	        return new Datatype[0];
	    }


		@Override
	    public Datatype[] getElements(Object element) {
	        if (element instanceof TestScriptTableMiniModel) {
	            Folder root = this.loadFolderRoots();
	            List<Datatype> result = new ArrayList<Datatype>();
	            result.add(root);
	            return result.toArray(new Datatype[result.size()]);
	        }

	        if (element instanceof List<?>) {

	            @SuppressWarnings("unchecked")
	            List<Datatype> elementList = (List<Datatype>) element;

	            if (!this.isLazy(elementList)) {
	                return elementList.toArray(new Datatype[elementList.size()]);
	            }
	        }
	        return new Folder[0];
	    }

	    /**
	     * Checks whether a collection is lazy or not.
	     * 
	     * @param collection
	     *            the collection to validate
	     * 
	     * @return <b>true</b> if the collection is lazy, <b>false</b> if not
	     */
	    private boolean isLazy(List<?> collection) {
	        if (collection instanceof NabuccoCollection<?>) {
	            NabuccoCollectionState state = ((NabuccoCollection<?>) collection).getState();
	            if (state == NabuccoCollectionState.LAZY) {
	                return true;
	            }
	        }
	        return false;
	    }

	    /**
	     * Loads all folder roots from the server.
	     * 
	     * @return the loaded folder roots
	     */
	    private Folder loadFolderRoots() {
	        try {
	        	SearchFolderDelegate searchFolderDelegate = ScriptComponentServiceDelegateFactory
	            	.getInstance().getSearchFolder();

	            FolderMsg rs = searchFolderDelegate.getFolderStructure(new EmptyServiceMessage());

	            return rs.getFolder();

	        } catch (ClientException e) {
	            Activator.getDefault().logError(e);
	        }

			return null;
	    }
	    
}
