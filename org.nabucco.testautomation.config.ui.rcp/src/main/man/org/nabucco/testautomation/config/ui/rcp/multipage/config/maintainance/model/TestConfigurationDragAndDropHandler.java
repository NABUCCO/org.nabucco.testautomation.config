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

import java.util.Arrays;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.draganddrop.AbstractDragAndDropHandler;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyType;
import org.nabucco.testautomation.facade.message.ProducePropertyMsg;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.script.facade.datatype.dictionary.base.TestScriptElement;
import org.nabucco.testautomation.ui.rcp.communication.TestautomationComponentServiceDelegateFactory;

public class TestConfigurationDragAndDropHandler extends AbstractDragAndDropHandler implements
org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.draganddrop.DragAndDropHandler {

	@Override
	public boolean validateDrop(MasterDetailTreeNode data,
			MasterDetailTreeNode target, int location) {

		// Movement to same node instance makes no sense
		Datatype targetDatatype = target.getDatatype();
		Datatype movedDatatype = data.getDatatype();
		if(movedDatatype == targetDatatype){
			return false;
		} if(location == 1 || location == 2){ 
			MasterDetailTreeNode parentNode = target.getParent();  
			if(parentNode == null){
				return false;
			}
			if(movedDatatype instanceof PropertyList && location == 2 && targetDatatype instanceof TestConfigElement){
				// PropertyList has to be the first element in Composites
				return false;
			}
			if(movedDatatype instanceof PropertyList && location == 2 && targetDatatype instanceof TestConfiguration){
				return false;
			}
			Datatype targetParentDatatype = parentNode.getDatatype();
			if(targetParentDatatype instanceof TestConfiguration){
				if(movedDatatype instanceof TestConfigElement){
					if(Arrays.asList(DataModelManager.getPossibleChildrenTypes(targetParentDatatype)).contains(movedDatatype.getClass())){
						if(checkSchema(parentNode, data)){
							return verifyHierarchy(data, target);
						}
					}
				} else if(movedDatatype instanceof PropertyList){
					// No before/after inserts for PropertyLists allowed if target parent is a TestConfiguration
					return false;
				}
				return false;
			}
			if(targetParentDatatype instanceof TestConfigElement){
				TestConfigElement targetParentTestConfigElement = (TestConfigElement) targetParentDatatype;
				if(movedDatatype instanceof TestConfigElement){
					if(targetParentTestConfigElement.getPropertyList() == null || location == 2){
						if(Arrays.asList(DataModelManager.getPossibleChildrenTypes(targetParentDatatype)).contains(movedDatatype.getClass())){
							if(checkSchema(parentNode, data)){
								return verifyHierarchy(data, target);
							}
						}
					} else {
						int targetIndex = parentNode.getChildren().indexOf(target);
						if(targetIndex == 0){
							// it is not allowed to insert to position 0, when a propertyList exists. 
							return false;
						}
					}
				} else if(movedDatatype instanceof PropertyList){
					// No before/after inserts for PropertyLists allowed if target parent is a TesScriptElement
					return false;
				}
				return false;
			} else if(targetParentDatatype instanceof PropertyList && movedDatatype instanceof Property){
				return verifyHierarchy(data, target);
			}
		} else if(location == 3){
			if(targetDatatype instanceof TestConfiguration){
				if(movedDatatype instanceof TestConfigElement){
					if(Arrays.asList(DataModelManager.getPossibleChildrenTypes(targetDatatype)).contains(movedDatatype.getClass())){
						if(checkSchema(target, data)){
							return verifyHierarchy(data, target);
						}
					}
				} 
				return false;
			}
			if(targetDatatype instanceof TestConfigElement){
				TestConfigElement targetTestConfigElement = (TestConfigElement) targetDatatype;
				if(movedDatatype instanceof TestConfigElement){
					if(Arrays.asList(DataModelManager.getPossibleChildrenTypes(targetDatatype)).contains(movedDatatype.getClass())){
						if(checkSchema(target, data)){
							return verifyHierarchy(data, target);
						}
					}
				} else if(movedDatatype instanceof PropertyList){
					if(targetTestConfigElement.getPropertyList() == null){
						if(Arrays.asList(DataModelManager.getPossibleChildrenTypes(targetDatatype)).contains(movedDatatype.getClass())){
							if(checkSchema(target, data)){
								return verifyHierarchy(data, target);
							}
						}
					}
					return false;
				}
				return false;
			} else if(targetDatatype instanceof PropertyList && movedDatatype instanceof Property){
				return verifyHierarchy(data, target);
			}
		}
		return false;
	}

	
	
	private boolean checkSchema(MasterDetailTreeNode parent, MasterDetailTreeNode data) {
		if(parent.getDatatype() instanceof TestConfiguration){
			if(data.getDatatype() instanceof TestConfigElement){
				TestConfigElement element = (TestConfigElement) data.getDatatype();
				TestConfiguration testConfiguration = (TestConfiguration) parent.getDatatype();
				SchemaConfig schemaConfig = testConfiguration.getSchemaConfig();
				SchemaElement schemaElement = element.getSchemaElement();
				if(schemaConfig.getSchemaElementList().contains(schemaElement)){
					return true;
				}
			}
		} else if(parent.getDatatype() instanceof TestConfigElement){
			TestConfigElement parentElement = (TestConfigElement) parent.getDatatype();
			if(data.getDatatype() instanceof TestConfigElement){
				TestConfigElement element = (TestConfigElement) data.getDatatype();
				SchemaElement parentSchemaElement = parentElement.getSchemaElement();
				SchemaElement schemaElement = element.getSchemaElement();
				if(parentSchemaElement.getSchemaElementList().contains(schemaElement)){
					return true;
				}
			} else if(data.getDatatype() instanceof PropertyList){
				return (parentElement.getSchemaElement().getPropertyContainer() != null && parentElement.getSchemaElement().getPropertyContainer().getValue() != null
						&& parentElement.getSchemaElement().getPropertyContainer().getValue());
			}
		}
		return false;
	}

	
	private boolean verifyHierarchy(MasterDetailTreeNode nodeToMove,
			MasterDetailTreeNode targetNode) {

		while(targetNode.getParent() != null){
			targetNode = targetNode.getParent();
			if(targetNode == nodeToMove){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean postDrop(MasterDetailTreeNode movedNode, MasterDetailTreeNode targetNode, int location) {
		// Perform changes in data model
		if(location == 1 || location == 2){ 
			Datatype targetParentDatatype = targetNode.getParent().getDatatype();

			if(targetParentDatatype instanceof TestConfiguration){
				TestConfiguration targetTestConfiguration = (TestConfiguration) targetParentDatatype;
				Datatype movedDatatype = movedNode.getDatatype();
				if(movedDatatype instanceof TestConfigElement){
					// Find container in old parent datatype
					int indexOfMovedNodeInOldParent = movedNode.getParent().getChildren().indexOf(movedNode);

					TestConfigElementContainer testConfigElementContainerToMove;
					if(movedNode.getParent().getDatatype() instanceof TestConfiguration){
						testConfigElementContainerToMove = ((TestConfiguration) movedNode.getParent().getDatatype()).getTestConfigElementList().get(indexOfMovedNodeInOldParent);
					} else if(movedNode.getParent().getDatatype() instanceof TestConfigElement){
						// handle PropertyList case
						if(((TestConfigElement) movedNode.getParent().getDatatype()).getPropertyList() != null){
							indexOfMovedNodeInOldParent--;
						}
						testConfigElementContainerToMove = ((TestConfigElement) movedNode.getParent().getDatatype()).getTestConfigElementList().get(indexOfMovedNodeInOldParent);
					} else {
						Activator.getDefault().logDebug("Fatal Error. Parent of moved node is no TestConfiguration ore TestConfigElement!");
						return false;
					}
					
					// Find index to insert to
					int indexToInsertTo = targetNode.getParent().getChildren().indexOf(targetNode);
					if(location == 2){
						indexToInsertTo++;
					}
					// Insert
					targetTestConfiguration.getTestConfigElementList().add(indexToInsertTo, testConfigElementContainerToMove);
					// Update order index of inserted container
					DataModelManager.normalizeOrderIndicies(targetTestConfiguration, false);
					// Update datatype state
					if(targetTestConfiguration.getDatatypeState() == DatatypeState.PERSISTENT){
						targetTestConfiguration.setDatatypeState(DatatypeState.MODIFIED);
					}
				} else if(movedDatatype instanceof PropertyList){
					// No before/after inserts for PropertyLists allowed if target parent is a TesScriptElement
					return false;
				}
			} else if(targetParentDatatype instanceof TestConfigElement){
				TestConfigElement targetParentTestConfigElement = (TestConfigElement) targetParentDatatype;
				Datatype movedDatatype = movedNode.getDatatype();
				if(movedDatatype instanceof TestConfigElement){
					// Find container in old parent datatype
					int indexOfMovedNodeInOldParent = movedNode.getParent().getChildren().indexOf(movedNode);

					TestConfigElementContainer testConfigElementContainerToMove;
					if(movedNode.getParent().getDatatype() instanceof TestConfiguration){
						testConfigElementContainerToMove = ((TestConfiguration) movedNode.getParent().getDatatype()).getTestConfigElementList().get(indexOfMovedNodeInOldParent);
					} else if(movedNode.getParent().getDatatype() instanceof TestConfigElement){
						// handle PropertyList case
						if(((TestConfigElement) movedNode.getParent().getDatatype()).getPropertyList() != null){
							indexOfMovedNodeInOldParent--;
						}
						testConfigElementContainerToMove = ((TestConfigElement) movedNode.getParent().getDatatype()).getTestConfigElementList().get(indexOfMovedNodeInOldParent);
					} else {
						Activator.getDefault().logDebug("Fatal Error. Parent of moved node is no TestConfiguration ore TestConfigElement!");
						return false;
					}
					
					// Find index to insert to
					int indexToInsertTo = targetNode.getParent().getChildren().indexOf(targetNode);
					if(location == 2){
						indexToInsertTo++;
					}
					// Insert
					targetParentTestConfigElement.getTestConfigElementList().add(indexToInsertTo, testConfigElementContainerToMove);
					// Update order index of inserted container
					DataModelManager.normalizeOrderIndicies(targetParentTestConfigElement, false);
					// Update datatype state
					if(targetParentTestConfigElement.getDatatypeState() == DatatypeState.PERSISTENT){
						targetParentTestConfigElement.setDatatypeState(DatatypeState.MODIFIED);
					}
				} else if(movedDatatype instanceof PropertyList){
					// No before/after inserts for PropertyLists allowed if target parent is a TesScriptElement
					return false;
				}
			} else if(targetParentDatatype instanceof PropertyList){
				PropertyList targetPropertyList = (PropertyList) targetParentDatatype;
				int indexOfMovedNodeInOldParent = movedNode.getParent().getChildren().indexOf(movedNode);
				PropertyContainer propertyContainer = ((PropertyList) movedNode.getParent().getDatatype()).getPropertyList().get(indexOfMovedNodeInOldParent);
				// Find index to insert to
				int indexToInsertTo = targetNode.getParent().getChildren().indexOf(targetNode);
				if(location == 2){
					indexToInsertTo++;
				}
				// Insert
				targetPropertyList.getPropertyList().add(indexToInsertTo, propertyContainer);
				org.nabucco.testautomation.ui.rcp.model.property.DataModelManager.normalizeOrderIndicies(targetPropertyList, false);
				if(targetPropertyList.getDatatypeState() == DatatypeState.PERSISTENT){
					targetPropertyList.setDatatypeState(DatatypeState.MODIFIED);
				}
			}
		}

		Datatype targetDatatype = targetNode.getDatatype();
		if(location == 3){

			if(targetDatatype instanceof TestConfiguration){
				TestConfiguration targetTestConfiguration = (TestConfiguration) targetDatatype;
				Datatype movedDatatype = movedNode.getDatatype();
				if(movedDatatype instanceof TestConfigElement){
					// Find container in old parent datatype
					int indexOfMovedNodeInOldParent = movedNode.getParent().getChildren().indexOf(movedNode);

					TestConfigElementContainer testConfigElementContainerToMove;
					if(movedNode.getParent().getDatatype() instanceof TestConfiguration){
						testConfigElementContainerToMove = ((TestConfiguration) movedNode.getParent().getDatatype()).getTestConfigElementList().get(indexOfMovedNodeInOldParent);
					} else if(movedNode.getParent().getDatatype() instanceof TestConfigElement){
						// handle PropertyList case
						if(((TestConfigElement) movedNode.getParent().getDatatype()).getPropertyList() != null){
							indexOfMovedNodeInOldParent--;
						}
						testConfigElementContainerToMove = ((TestConfigElement) movedNode.getParent().getDatatype()).getTestConfigElementList().get(indexOfMovedNodeInOldParent);
					} else {
						Activator.getDefault().logDebug("Fatal Error. Parent of moved node is no TestConfiguration ore TestConfigElement!");
						return false;
					}

					// Insert
					targetTestConfiguration.getTestConfigElementList().add(testConfigElementContainerToMove);
					// Update order index of inserted container
					DataModelManager.normalizeOrderIndicies(targetTestConfiguration, false);
					// Update datatype state
					if(targetTestConfiguration.getDatatypeState() == DatatypeState.PERSISTENT){
						targetTestConfiguration.setDatatypeState(DatatypeState.MODIFIED);
					}
				}
			} else if(targetDatatype instanceof TestConfigElement){
				TestConfigElement targetTestConfigElement = (TestConfigElement) targetDatatype;
				Datatype movedDatatype = movedNode.getDatatype();
				if(movedDatatype instanceof TestConfigElement){
					// Find container in old parent datatype
					int indexOfMovedNodeInOldParent = movedNode.getParent().getChildren().indexOf(movedNode);

					TestConfigElementContainer testConfigElementContainerToMove;
					if(movedNode.getParent().getDatatype() instanceof TestConfiguration){
						testConfigElementContainerToMove = ((TestConfiguration) movedNode.getParent().getDatatype()).getTestConfigElementList().get(indexOfMovedNodeInOldParent);
					} else if(movedNode.getParent().getDatatype() instanceof TestConfigElement){
						// handle PropertyList case
						if(((TestConfigElement) movedNode.getParent().getDatatype()).getPropertyList() != null){
							indexOfMovedNodeInOldParent--;
						}
						testConfigElementContainerToMove = ((TestConfigElement) movedNode.getParent().getDatatype()).getTestConfigElementList().get(indexOfMovedNodeInOldParent);
					} else {
						Activator.getDefault().logDebug("Fatal Error. Parent of moved node is no TestConfiguration ore TestConfigElement!");
						return false;
					}
					// Insert
					targetTestConfigElement.getTestConfigElementList().add(testConfigElementContainerToMove);
					// Update order index of inserted container
					DataModelManager.normalizeOrderIndicies(targetTestConfigElement, false);
					// Update datatype state
					if(targetTestConfigElement.getDatatypeState() == DatatypeState.PERSISTENT){
						targetTestConfigElement.setDatatypeState(DatatypeState.MODIFIED);
					}
				} else if(movedDatatype instanceof PropertyList){
					PropertyList movedList = (PropertyList) movedDatatype;
					targetTestConfigElement.setPropertyList(movedList);
					if(targetTestConfigElement.getDatatypeState() == DatatypeState.PERSISTENT){
						targetTestConfigElement.setDatatypeState(DatatypeState.MODIFIED);
					}
				}
			} else if(targetDatatype instanceof PropertyList){
				PropertyList targetPropertyList = (PropertyList) targetDatatype;
				int indexOfMovedNodeInOldParent = movedNode.getParent().getChildren().indexOf(movedNode);
				PropertyContainer propertyContainer; 

				if(movedNode.getParent().getDatatype() instanceof TestScriptElement){
					ProducePropertyMsg rq = new ProducePropertyMsg();
					rq.setPropertyType(PropertyType.LIST);
					try {
						propertyContainer = TestautomationComponentServiceDelegateFactory.getInstance().getProduceProperty().produceProperty(rq).getPropertyContainer();
					} catch (ClientException e) {
						Activator.getDefault().logError("Cannot produce PropertyContainer"); 
						return false;
					}
					propertyContainer.setProperty((Property) movedNode.getDatatype());
				} else {
					propertyContainer = ((PropertyList) movedNode.getParent().getDatatype()).getPropertyList().get(indexOfMovedNodeInOldParent);
				}
				targetPropertyList.getPropertyList().add(propertyContainer);
				if(targetPropertyList.getDatatypeState() == DatatypeState.PERSISTENT){
					targetPropertyList.setDatatypeState(DatatypeState.MODIFIED);
				}
			}
		}
		// Perform changes in Tree - We have to do this last, because changes in the tree could influence the indices needed for proper datamodel change
		return this.performUiDrop(movedNode, targetNode, location);
	}

	@Override
	public void dragFinished(MasterDetailTreeNode movedNode) {
		MasterDetailTreeNode parentNode = movedNode.getParent();
		Datatype droppedDatatype = movedNode.getDatatype();
		Datatype parentDatatype = parentNode.getDatatype();
		if(parentDatatype instanceof TestConfiguration){
			TestConfiguration parentTestConfiguration = (TestConfiguration) parentDatatype;
			if(droppedDatatype instanceof TestConfigElement){
				int indexOf = parentNode.getChildren().indexOf(movedNode);

				if(movedNode.getParent().getDatatype() instanceof TestConfiguration){
					// no index change necessary
				} else if(movedNode.getParent().getDatatype() instanceof TestConfigElement){
					// handle PropertyList case
					if(((TestConfigElement) movedNode.getParent().getDatatype()).getPropertyList() != null){
						indexOf--;
					}
				}

				parentTestConfiguration.getTestConfigElementList().remove(indexOf);
				if(parentTestConfiguration.getDatatypeState() == DatatypeState.PERSISTENT){
					parentTestConfiguration.setDatatypeState(DatatypeState.MODIFIED);
				}
				// this must stand after the "indexOf" call
				parentNode.getChildren().remove(movedNode);
				// Normalize order indices
				DataModelManager.normalizeOrderIndicies(parentTestConfiguration, false);
				return;
			} 
		} else if(parentDatatype instanceof TestConfigElement){
			TestConfigElement parentTestConfigElement = (TestConfigElement) parentDatatype;
			if(droppedDatatype instanceof TestConfigElement){
				int indexOf = parentNode.getChildren().indexOf(movedNode);

				if(movedNode.getParent().getDatatype() instanceof TestConfiguration){
					// no index change necessary
				} else if(movedNode.getParent().getDatatype() instanceof TestConfigElement){
					// handle PropertyList case
					if(((TestConfigElement) movedNode.getParent().getDatatype()).getPropertyList() != null){
						indexOf--;
					}
				}

				parentTestConfigElement.getTestConfigElementList().remove(indexOf);
				if(parentTestConfigElement.getDatatypeState() == DatatypeState.PERSISTENT){
					parentTestConfigElement.setDatatypeState(DatatypeState.MODIFIED);
				}
				// this must stand after the "indexOf" call
				parentNode.getChildren().remove(movedNode);
				// Normalize order indices
				DataModelManager.normalizeOrderIndicies(parentTestConfigElement, false);
				return;
			} else if(droppedDatatype instanceof PropertyList){
				parentTestConfigElement.setPropertyList(null);
				parentNode.getChildren().remove(0);
				if(parentTestConfigElement.getDatatypeState() == DatatypeState.PERSISTENT){
					parentTestConfigElement.setDatatypeState(DatatypeState.MODIFIED);
				} 
				return;
			}
		} else if(parentDatatype instanceof PropertyList){
			PropertyList parentPropertyList = (PropertyList) parentDatatype;
			int indexOf = parentNode.getChildren().indexOf(movedNode);
			parentPropertyList.getPropertyList().remove(indexOf);
			// this must stand after the "indexOf" call
			parentNode.getChildren().remove(movedNode);
			// Normalize order indices
			org.nabucco.testautomation.ui.rcp.model.property.DataModelManager.normalizeOrderIndicies(parentPropertyList, false);
			if(parentPropertyList.getDatatypeState() == DatatypeState.PERSISTENT){
				parentPropertyList.setDatatypeState(DatatypeState.MODIFIED);
			}
			return;
		}
		Activator.getDefault().logDebug("Fatal Error. Parent node of dragged Element is no TestScriptElement or PropertyList!");
	}

	@Override
	public boolean performUiDrop(MasterDetailTreeNode movedNode, MasterDetailTreeNode targetNode, int location) {
		MasterDetailTreeNode newNode;
		if(location == 1 || location == 2) {
			newNode = new MasterDetailTreeNode(movedNode.getDatatype(), targetNode.getParent());
			newNode.setViewModel(targetNode.getViewModel());

			// Find index to insert to
			int indexToInsertTo = targetNode.getParent().getChildren().indexOf(targetNode);
			if(location == 2){
				indexToInsertTo++;
			}
			targetNode.getParent().getChildren().add(indexToInsertTo, newNode);
		} else if(location == 3){ 
			newNode = new MasterDetailTreeNode(movedNode.getDatatype(), targetNode);
			newNode.setViewModel(targetNode.getViewModel());
			if(movedNode.getDatatype() instanceof PropertyList){
				targetNode.getChildren().add(0, newNode);
			} else {
				targetNode.getChildren().add(newNode);
			}
		} else {
			return false;
		}
		List<MasterDetailTreeNode> childrenOfMovedNode = movedNode.getChildren();
		newNode.getChildren().addAll(childrenOfMovedNode);
		for (MasterDetailTreeNode childOfMovedNode : childrenOfMovedNode) {
			childOfMovedNode.setParent(newNode);
		}
		return true;
	}

	@Override
	public boolean validateExternalDrop(MasterDetailTreeNode target, int location) {
		return false;
	}

	@Override
	public boolean postExternalDatatypeDrop(Datatype data, MasterDetailTreeNode targetNode, int location) {
		return false;
	}


}