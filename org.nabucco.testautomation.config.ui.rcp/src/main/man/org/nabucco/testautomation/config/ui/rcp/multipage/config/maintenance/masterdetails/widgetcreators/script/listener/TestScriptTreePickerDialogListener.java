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

import java.util.Set;

import org.eclipse.swt.events.TypedEvent;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.newpicker.dialog.PickerDialogSelectionListener;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.message.ProduceTestScriptContainerMsg;
import org.nabucco.testautomation.config.ui.rcp.communication.ConfigComponentServiceDelegateFactory;
import org.nabucco.testautomation.config.ui.rcp.communication.produce.ProduceConfigDelegate;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.TestScriptTableMiniModel;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;

/**
 * MetadataPickerDialogListener
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TestScriptTreePickerDialogListener implements PickerDialogSelectionListener {

    public static final String ID = "";

    private TestScriptTableMiniModel model;

    /**
     * Creates a new {@link TestScriptTreePickerDialogListener} instance.
     * 
     * @param model
     *            the mini model
     */
    public TestScriptTreePickerDialogListener(TestScriptTableMiniModel model) {
        if (model == null) {
            throw new IllegalArgumentException(
                    "Cannot create TestScriptTreePickerDialogListener for model [null].");
        }
        this.model = model;
    }

    /**
     * Adds a script to the model.
     * 
     * @param script
     *            the script
     */
    private void addScript(TestScript script) {
        try {
            ProduceConfigDelegate produceService = ConfigComponentServiceDelegateFactory
                    .getInstance().getProduceConfig();

            ProduceTestScriptContainerMsg rq = new ProduceTestScriptContainerMsg();
            rq.setTestScript(script);

            ProduceTestScriptContainerMsg rs = produceService.produceTestScriptContainer(rq);
            TestScriptContainer container = rs.getTestScriptContainer();

            this.model.addElement(container);

        } catch (ClientException e) {
            Activator.getDefault().logError("Cannot add TestScript" + script.getName() + ".");
            Activator.getDefault().logError(e);
        }
    }
    
    @Override
    public void elementSelected(TypedEvent event) {
        if (event.data instanceof Set<?>) {
            for (Object object : (Set<?>) event.data) {
                if (object instanceof TestScript) {
                    this.addScript((TestScript) object);
                }
            }
        } else if (event.data instanceof TestScript) {
            this.addScript((TestScript) event.data);
        }
    }
}
