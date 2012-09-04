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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.listener;

import org.eclipse.swt.events.TypedEvent;
import org.nabucco.framework.plugin.base.component.picker.dialog.ElementPickerListener;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.TestScriptTableMiniModel;


/**
 * UpListener
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class UpListener implements ElementPickerListener {

    private TestScriptTableMiniModel model;

    /**
     * Creates a new {@link UpListener} instance.
     * 
     * @param model
     *            the model to modify
     */
    public UpListener(TestScriptTableMiniModel model) {
        this.model = model;
    }

    @Override
    public void elementSelected(TypedEvent event) {
        if (event.data instanceof TestScriptContainer) {
            this.model.pushUp((TestScriptContainer) event.data);
        }
    }

}
