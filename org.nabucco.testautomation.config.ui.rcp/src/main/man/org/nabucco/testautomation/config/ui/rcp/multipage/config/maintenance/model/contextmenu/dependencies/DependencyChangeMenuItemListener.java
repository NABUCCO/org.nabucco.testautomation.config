package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.dependencies;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.multipage.model.MasterDetailAble;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.TestConfigurationMaintenanceMultiPageEditView;

public class DependencyChangeMenuItemListener implements SelectionListener {
	
	public enum Action {
		CHANGEALL,
		CHANGENOTSET,
		REMOVE
	}

	private TreeViewer treeViewer;
	private Action change;
	
	public static final String ID = "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.dependencies.DependencyChangeMenuItemListener"; 

    private static final String TITLE = ID + ".title";

    private static final String MESSAGE = ID + ".message";

	public DependencyChangeMenuItemListener(
			MasterDetailAble<? extends Datatype> model,
			MasterDetailTreeNode parentNode, TreeViewer treeViewer, Action changeAll) {
        this.treeViewer = treeViewer;
        this.change = changeAll;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {}

	@Override
	public void widgetSelected(SelectionEvent event) {
		try {
            Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
            boolean confirmed = MessageDialog.openConfirm(shell, I18N.i18n(TITLE), I18N.i18n(MESSAGE));
            if (confirmed) {
            	TreeSelection selection = (TreeSelection)this.treeViewer.getSelection();
            	List<MasterDetailTreeNode> children = ((MasterDetailTreeNode)selection.getFirstElement()).getChildren();
    			MasterDetailTreeNode previousSibling = null;
    			for (MasterDetailTreeNode child : children) {
    				changeDependency(child, previousSibling, change);
    				previousSibling = child;
    			}
                this.treeViewer.refresh();
            }
        } catch (Exception e) {
            Activator.getDefault().logError("Error removing child to tree node.");
            Activator.getDefault().logError(e);
        }
	}
	
	void changeDependency(MasterDetailTreeNode node, MasterDetailTreeNode previous, Action change) {
		Datatype datatype = node.getDatatype();
		List<MasterDetailTreeNode> children = node.getChildren();
		MasterDetailTreeNode previousSibling = null;
		for (MasterDetailTreeNode child : children) {
			changeDependency(child, previousSibling, change);
			previousSibling = child;
		}
		if (datatype instanceof TestConfigElement) {
			TestConfigElement tce = (TestConfigElement) datatype;
			if(tce.getSchemaElement().getHasDependencies().getValue()) {
				if(previous != null && !(previous.getDatatype() instanceof TestConfigElement)) {
					previous = null;
				}
				if(((change == Action.CHANGEALL || change == Action.REMOVE) && previous != null) || (change == Action.CHANGENOTSET && tce.getDependencyList().size() == 0 && previous != null)) {
					List<Dependency> list = tce.getDependencyList();
					switch (change) {
					case CHANGEALL:
						list.clear();
					case CHANGENOTSET:
						Dependency dependency = new Dependency();
						dependency.setElement((TestConfigElement)previous.getDatatype());
						dependency.setDatatypeState(DatatypeState.INITIALIZED);
						dependency.setOrderIndex(0);
						list.add(dependency);
						break;
					case REMOVE:
						list.clear();
					}
					tce.setDatatypeState(DatatypeState.MODIFIED);
				}
			}
			TestConfigurationMaintenanceMultiPageEditView view = (TestConfigurationMaintenanceMultiPageEditView)Activator.getDefault().getView("org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.TestConfigurationMaintenanceMultiPageEditView");
			view.getModel().setDirty(true);
		}
	}
}
