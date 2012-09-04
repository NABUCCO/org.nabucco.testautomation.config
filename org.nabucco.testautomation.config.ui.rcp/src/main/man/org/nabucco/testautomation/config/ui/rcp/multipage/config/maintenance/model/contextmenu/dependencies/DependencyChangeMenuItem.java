package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.dependencies;

import java.io.Serializable;
import java.util.Map;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.contextmenu.ContextMenuItem;
import org.nabucco.framework.plugin.base.component.multipage.model.MasterDetailAble;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.dependencies.DependencyChangeMenuItemListener.Action;

public class DependencyChangeMenuItem extends ContextMenuItem {

	public DependencyChangeMenuItem(Menu menu, MasterDetailTreeNode parentNode,
            MasterDetailAble<? extends Datatype> model, TreeViewer treeViewer, String textKey,
            Map<String, ? extends Serializable> values, Image image, Action remove) {
		super(menu, values, textKey, image);
        super.addSelectionListener(new DependencyChangeMenuItemListener(model, parentNode, treeViewer, remove));
	}

}
