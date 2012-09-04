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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu;

import java.io.Serializable;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.contextmenu.ContextMenuItem;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.listener.EditExecutionTypeSelectionListener;
import org.nabucco.testautomation.result.facade.datatype.ExecutionType;

/**
 * EditExecutionTypeMenuItem
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ExecutionTypeMenuItem extends ContextMenuItem {

    /**
     * Creates a new {@link ExecutionTypeMenuItem} instance.
     * 
     * @param parent
     *            the menu parent
     * @param element
     *            the TestConfigElement to start
     * @param textKey
     *            the text key
     * @param values
     *            the I18N parameters
     */
    public ExecutionTypeMenuItem(Menu parent, TestConfigElement element, ExecutionType executionType, String textKey,
            Map<String, ? extends Serializable> values, Image image) {

        super(parent, values, textKey, image);
        EditExecutionTypeSelectionListener listener = new EditExecutionTypeSelectionListener(element, executionType);
        super.addSelectionListener(listener);
    }

}
