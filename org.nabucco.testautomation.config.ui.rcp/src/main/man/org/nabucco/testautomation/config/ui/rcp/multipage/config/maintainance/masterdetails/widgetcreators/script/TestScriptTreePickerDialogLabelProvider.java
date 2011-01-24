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

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.nabucco.framework.plugin.base.layout.ImageProvider;

import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;
import org.nabucco.testautomation.script.facade.datatype.dictionary.base.Folder;
import org.nabucco.testautomation.script.ui.rcp.images.ScriptImageRegistry;

/**
 * TestScriptTreePickerDialogLabelProvider
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestScriptTreePickerDialogLabelProvider implements ILabelProvider {

	private static final String ICON_FOLDER = "/icons/folder.png";

	@Override
	public void dispose() {
	}

	@Override
	public void addListener(ILabelProviderListener arg0) {
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof TestScript) {
			return ImageProvider.createImage(ScriptImageRegistry.ICON_SCRIPT
					.getId());
		} else if (element instanceof Folder) {
			return ImageProvider.createImage(ICON_FOLDER);
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof TestScript) {
			TestScript testScript = (TestScript) element;
			if (testScript.getName() != null) {
				return testScript.getName().getValue();
			}
		} else if (element instanceof Folder) {
			Folder folder = (Folder) element;
			if (folder.getName() != null) {
				return folder.getName().getValue();
			}
		}
		return null;
	}

}
