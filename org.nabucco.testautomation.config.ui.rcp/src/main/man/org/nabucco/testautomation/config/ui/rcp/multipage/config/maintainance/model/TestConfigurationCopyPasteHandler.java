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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.copypaste.AbstractCopyPasteHandler;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.copypaste.CopyPasteHandler;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.TestConfigurationMaintainanceMasterDetailTreeNodeCreator;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;

/**
 * TestConfigurationCopyPasteHandler
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationCopyPasteHandler extends AbstractCopyPasteHandler implements CopyPasteHandler {

	private TestConfigurationMaintainanceMasterDetailTreeNodeCreator treeNodeCreator;

	public TestConfigurationCopyPasteHandler(){
		this.treeNodeCreator = new TestConfigurationMaintainanceMasterDetailTreeNodeCreator();
		
	}

	@Override
	public void paste(MasterDetailTreeNode targetNode,
			Datatype copiedDatatype) {

		if(!super.validate(targetNode, copiedDatatype)){
			return;
		}
		
		Datatype targetDatatype = targetNode.getDatatype();
		Datatype clone = null;

		if(DataModelManager.isAllowedChild(targetNode.getDatatype(), copiedDatatype)){
			// Update datamodel
			if(targetDatatype instanceof TestConfiguration && copiedDatatype instanceof TestConfigElement){
				TestConfigElement copiedTestConfigElement = (TestConfigElement) copiedDatatype;
				TestConfigElementContainer testConfigElementContainer = TestConfigurationElementFactory.clone(copiedTestConfigElement);	
				clone = testConfigElementContainer.getElement();
				List<TestConfigElementContainer> testConfigElementList = ((TestConfiguration) targetDatatype).getTestConfigElementList();
				testConfigElementContainer.setOrderIndex(testConfigElementList.size());
				testConfigElementList.add(testConfigElementContainer);
			} else if(targetDatatype instanceof TestConfigElement){
				if(copiedDatatype instanceof TestConfigElement){
					TestConfigElement copiedTestConfigElement = (TestConfigElement) copiedDatatype;
					TestConfigElementContainer testConfigElementContainer = TestConfigurationElementFactory.clone(copiedTestConfigElement);	
					clone = testConfigElementContainer.getElement();
					List<TestConfigElementContainer> testConfigElementList = ((TestConfigElement) targetDatatype).getTestConfigElementList();
					testConfigElementContainer.setOrderIndex(testConfigElementList.size());
					testConfigElementList.add(testConfigElementContainer);
				} else if(copiedDatatype instanceof PropertyList){
					PropertyList copiedPropertyList = (PropertyList) copiedDatatype;
					PropertyContainer propertyContainer = TestConfigurationElementFactory.clone(copiedPropertyList);	
					clone = propertyContainer.getProperty();
					((TestConfigElement) targetDatatype).setPropertyList((PropertyList) clone);
				}
			} else if(targetDatatype instanceof PropertyList && copiedDatatype instanceof Property){
				Property copiedProperty = (Property) copiedDatatype;
				PropertyContainer propertyContainer = TestConfigurationElementFactory.clone(copiedProperty);
				clone = propertyContainer.getProperty();
				List<PropertyContainer> targetPropertyList = ((PropertyList) targetDatatype).getPropertyList();
				propertyContainer.setOrderIndex(targetPropertyList.size());
				targetPropertyList.add(propertyContainer);
			}
		} else {
			return;
		}

		if(clone == null){
			return;
		}
		// Update target TreeNode
		MasterDetailTreeNode newNode = treeNodeCreator.createNodeTyped(clone, targetNode, null);
		if(clone instanceof PropertyList){
			targetNode.getChildren().add(0, newNode);
		} else {
			targetNode.getChildren().add(newNode);
		}
		newNode.setViewModel(targetNode.getViewModel());

		// Update target DatatypeState
		if(targetDatatype.getDatatypeState() == DatatypeState.PERSISTENT){
			targetDatatype.setDatatypeState(DatatypeState.MODIFIED);
		}
	}

}

