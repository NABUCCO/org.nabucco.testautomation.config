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

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;

import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;

/**
 * TestScriptTableFolderColumnLabelProvider
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestScriptTableFolderColumnLabelProvider implements ILabelProvider {

    @Override
    public Image getImage(Object arg0) {
        return null;
    }

    @Override
    public String getText(Object element) {
        if (element instanceof TestScriptContainer) {
            return labelScriptContainer((TestScriptContainer) element);
        } else if (element instanceof TestScript) {
            return labelScript((TestScript) element);
        }
        return "";
    }

    /**
     * Create label for a test script container.
     * 
     * @param container
     *            the script to label
     * 
     * @return the label
     */
    private String labelScriptContainer(TestScriptContainer container) {
        if (container.getTestScript() != null
                && container.getTestScript().getFolder() != null) {
            return container.getTestScript().getFolder().getName().getValue();
        }
        return "";
    }

    /**
     * Create label for a test script.
     * 
     * @param script
     *            the script to label
     * 
     * @return the label
     */
    private String labelScript(TestScript script) {
        if (script.getFolder() != null && script.getFolder().getName() != null) {
            return script.getFolder().getName().getValue();
        }
        return "";
    }

    @Override
    public void addListener(ILabelProviderListener arg0) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isLabelProperty(Object arg0, String arg1) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener arg0) {
    }

}
