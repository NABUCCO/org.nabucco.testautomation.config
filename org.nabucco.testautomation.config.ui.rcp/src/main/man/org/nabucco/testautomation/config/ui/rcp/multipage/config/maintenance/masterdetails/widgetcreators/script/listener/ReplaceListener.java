package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.listener;

import org.eclipse.swt.events.TypedEvent;
import org.nabucco.framework.plugin.base.component.picker.dialog.ElementPickerListener;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.TestScriptTableMiniModel;

public class ReplaceListener implements ElementPickerListener {

	private TestScriptTableMiniModel model;

	public ReplaceListener(TestScriptTableMiniModel model) {
		this.model = model;
	}

	@Override
	public void elementSelected(TypedEvent event) {
        if (event.data instanceof TestScriptContainer) {
            TestScriptContainer container = (TestScriptContainer) event.data;
            this.model.removeElement(container);
        }
	}

}
