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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.TreeNodeDecorator;

/**
 * TestConfigElementPropertyChangeListener
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
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
