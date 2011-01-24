package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.TreeNodeDecorator;

public class TestConfigElementPropertyChangeListener implements PropertyChangeListener {


	private MasterDetailTreeNode masterDetailTreeNode;



	public TestConfigElementPropertyChangeListener(MasterDetailTreeNode masterDetailTreeNode){
		this.masterDetailTreeNode = masterDetailTreeNode;
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(masterDetailTreeNode.getDatatype() instanceof TestConfigElement){
			TreeNodeDecorator.decorateNode(masterDetailTreeNode, masterDetailTreeNode.getDatatype());
		}
	}

}
