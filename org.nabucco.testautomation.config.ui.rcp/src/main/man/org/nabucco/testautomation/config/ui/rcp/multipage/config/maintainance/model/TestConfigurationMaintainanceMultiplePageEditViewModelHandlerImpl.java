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
import java.util.Map;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableColumnInfo;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableSorter;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNodeCreatorForAllDatatypes;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.addDialog.AddDialogLabelProvider;
import org.nabucco.framework.plugin.base.component.multipage.xml.DatatypeXMLEditorTextPartCreator;
import org.nabucco.framework.plugin.base.component.multipage.xml.XMLEditorTextPart;
import org.nabucco.framework.plugin.base.component.picker.dialog.ElementPickerContentProvider;
import org.nabucco.framework.plugin.base.component.picker.dialog.ElementPickerDefaultContentProvider;
import org.nabucco.framework.plugin.base.component.picker.dialog.ElementPickerParameter;
import org.nabucco.framework.plugin.base.component.picker.dialog.LabelForDialog;
import org.nabucco.framework.plugin.base.logging.NabuccoLogMessage;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.comparator.TestConfigElementSorter;
import org.nabucco.testautomation.config.ui.rcp.edit.config.model.TestConfigurationEditViewBusinessModel;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.TestConfigurationMaintainanceMultiplePageEditView;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.TreeNodeDecorator;
import org.nabucco.testautomation.facade.datatype.comparator.PropertySorter;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;

/**
 * TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl implements

TestConfigurationMaintainanceMultiplePageEditViewModelHandler {

	public static final String ID = "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl";

	private final TestConfigurationEditViewBusinessModel editBusinessModel = new TestConfigurationEditViewBusinessModel();

	private final TestConfigElementSorter elementSorter = new TestConfigElementSorter();

	private final PropertySorter propertySorter = new PropertySorter();

	public TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl() {
		super();
	}

	/**
	 * Return whether possible children are available.
	 */
	@Override
	public boolean hasPossibleChildren(Datatype datatype) {
		return DataModelManager.hasPossibleChildren(datatype);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Datatype[]> getPossibleChildren(Datatype datatype) {
		return DataModelManager.getPossibleChildren(datatype);
	}

	@Override
	public Menu getContextMenu(ISelection selection, TreeViewer treeViewer) {
		return DataModelManager.getContextMenu(selection, treeViewer, this);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public MasterDetailTreeNode addChild(final MasterDetailTreeNode parent, Datatype newChild) {
		MasterDetailTreeNode result = null;
		Datatype parentDatatype = parent.getDatatype();

		// Existing Datatypes have to be loaded full
		if (newChild.getDatatypeState() == DatatypeState.PERSISTENT) {
			if (newChild instanceof TestConfiguration) {
				newChild = editBusinessModel.readTestConfiguration((TestConfiguration) newChild);
			} else if (newChild instanceof TestConfigElement) {
				TestConfigElement newTestConfigElement = (TestConfigElement) newChild;
				newTestConfigElement = editBusinessModel.readTestConfigElement(newTestConfigElement);
				// set reused flag
				if (newTestConfigElement.getReused() == null || !newTestConfigElement.getReused().getValue()) {
					newTestConfigElement.setReused(true);
					if(newTestConfigElement.getDatatypeState() == DatatypeState.PERSISTENT){
						newTestConfigElement.setDatatypeState(DatatypeState.MODIFIED);
					}
				}
				newChild = TestConfigurationElementFactory.createTestConfigElementContainer((TestConfigElement) newTestConfigElement);

				// New produced containers have to get an order index
				if (parentDatatype instanceof TestConfiguration) {
					TestConfiguration parentTestConfiguration = (TestConfiguration) parentDatatype;
					((TestConfigElementContainer) newChild).setOrderIndex(parentTestConfiguration.getTestConfigElementList().size());
				} else if (parentDatatype instanceof TestConfigElement) {
					TestConfigElement parentTestConfigElement = (TestConfigElement) parentDatatype;
					((TestConfigElementContainer) newChild).setOrderIndex(parentTestConfigElement.getTestConfigElementList().size());
				}
			} else if (newChild instanceof PropertyList) {
				newChild = editBusinessModel.readPropertyList((PropertyList) newChild);
			}
		} else if (newChild instanceof TestConfigElement) {
			TestConfigElementContainer testConfigElementContainer = TestConfigurationElementFactory
			.createTestConfigElementContainer((TestConfigElement) newChild);

			// load existing TestConfigElement from DB including its children
			if (testConfigElementContainer.getElement().getDatatypeState() == DatatypeState.PERSISTENT) {
				testConfigElementContainer.setElement(editBusinessModel.readTestConfigElement(testConfigElementContainer.getElement()));
			}
			newChild = testConfigElementContainer;

			// New Datatypes have to get an order index
			if (parentDatatype instanceof TestConfiguration) {
				TestConfiguration parentTestConfiguration = (TestConfiguration) parentDatatype;
				testConfigElementContainer.setOrderIndex(parentTestConfiguration.getTestConfigElementList().size());
			} else if (parentDatatype instanceof TestConfigElement) {
				TestConfigElement parentTestConfigElement = (TestConfigElement) parentDatatype;
				testConfigElementContainer.setOrderIndex(parentTestConfigElement.getTestConfigElementList().size());
			}
		} else if (newChild instanceof PropertyContainer) {
			PropertyContainer propertyContainer = (PropertyContainer) newChild;

			// New Datatypes have to get an order
			if (parentDatatype instanceof PropertyList) {
				PropertyList parentPropertyList = (PropertyList) parentDatatype;
				propertyContainer.setOrderIndex(parentPropertyList.getPropertyList().size());
			}
		}

		// Only to composites children should be added
		if (newChild instanceof TestConfigElementContainer) {
			TestConfigElementContainer newTestConfigElementContainer = (TestConfigElementContainer) newChild;
			if (parentDatatype instanceof TestConfigElement) {
				TestConfigElement parentTestScriptComposite = (TestConfigElement) parentDatatype;
				result = addChild(parent, newTestConfigElementContainer.getElement());
				parentTestScriptComposite.getTestConfigElementList().add(newTestConfigElementContainer);
			} else if (parentDatatype instanceof TestConfiguration) {
				TestConfiguration parentTestScriptElement = (TestConfiguration) parentDatatype;
				result = addChild(parent, newTestConfigElementContainer.getElement());
				parentTestScriptElement.getTestConfigElementList().add(newTestConfigElementContainer);
			}
		} else if (newChild instanceof PropertyList) {
			PropertyList newProperty = (PropertyList) newChild;
			if (parentDatatype instanceof TestConfigElement) {
				TestConfigElement parentTestConfigElement = (TestConfigElement) parentDatatype;
				result = addChild(parent, newProperty);
				parentTestConfigElement.setPropertyList(newProperty);
			}
		} else if (newChild instanceof PropertyContainer) {
			PropertyContainer newPropertyContainer = (PropertyContainer) newChild;
			if (parentDatatype instanceof PropertyList) {
				PropertyList parentPropertyList = (PropertyList) parentDatatype;
				result = addChild(parent, newPropertyContainer.getProperty());
				parentPropertyList.getPropertyList().add(newPropertyContainer);
			} else if (parentDatatype instanceof TestConfigElement && newPropertyContainer.getProperty() instanceof PropertyList) {
				TestConfigElement parentTestConfigElement = (TestConfigElement) parentDatatype;
				PropertyList newPropertyList = (PropertyList) newPropertyContainer.getProperty();
				result = addChild(parent, newPropertyContainer.getProperty());
				parentTestConfigElement.setPropertyList(newPropertyList);
			}
		}
		if (result == null) {
			Activator.getDefault().logError(
					new NabuccoLogMessage(TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.class, "FAILED to add new child!"));
		}

		if(result.getDatatype() instanceof TestConfigElement){
			TestConfigurationMaintainanceMultiplePageEditView view = (TestConfigurationMaintainanceMultiplePageEditView) Activator.getDefault().getView(TestConfigurationMaintainanceMultiplePageEditView.ID);
			TestConfigElementPropertyChangeListener listener = new TestConfigElementPropertyChangeListener(result);
			view.getModel().addPropertyChangeListener("value", listener);
			view.getModel().addPropertyChangeListener("stringValue", listener);
		}


		// Add Decorators
		TreeNodeDecorator.decorateNode(result, newChild);
		return result;
	}

	private MasterDetailTreeNode addChild(final MasterDetailTreeNode parent, final TestConfigElement newChild) {
		MasterDetailTreeNode result = MasterDetailTreeNodeCreatorForAllDatatypes.getInstance().create(newChild, parent);
		parent.getChildren().add(result);
		Datatype datatype = parent.getDatatype();

		if (datatype.getDatatypeState() == DatatypeState.PERSISTENT) {
			datatype.setDatatypeState(DatatypeState.MODIFIED);
		}
		return result;
	}

	private MasterDetailTreeNode addChild(final MasterDetailTreeNode parent, final Property newChild) {
		MasterDetailTreeNode result = MasterDetailTreeNodeCreatorForAllDatatypes.getInstance().create(newChild, parent);
		// Property List has to be the first child in TestConfigElements
		if (parent.getDatatype() instanceof TestConfigElement) {
			parent.getChildren().add(0, result);
		} else {
			parent.getChildren().add(result);
		}
		Datatype datatype = parent.getDatatype();

		if (datatype.getDatatypeState() == DatatypeState.PERSISTENT) {
			datatype.setDatatypeState(DatatypeState.MODIFIED);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(ISelection child) {
		StructuredSelection ssel = (StructuredSelection) child;
		MasterDetailTreeNode nodeToRemove = (MasterDetailTreeNode) ssel.getFirstElement();
		MasterDetailTreeNode parentNode = nodeToRemove.getParent();
		if (parentNode == null) {
			return;
		}
		Datatype parentDatatype = parentNode.getDatatype();
		boolean removedFromDataModel = false;
		// This has to be done, because the same TestConfigElement could appear
		// more then one time
		// on the same level.
		// We need to find the correct one.
		int indexOfNodeToDelete = parentNode.getChildren().indexOf(nodeToRemove);
		if (parentDatatype instanceof TestConfiguration) {
			TestConfiguration parentTestConfiguration = (TestConfiguration) parentDatatype;
			List<TestConfigElementContainer> testConfigElementList = parentTestConfiguration.getTestConfigElementList();
			// decrease Order Of AllElements With Order Index Higher Than Index
			// Of Node To Delete
			updateOrdersAfterBeforeDeletion(indexOfNodeToDelete, testConfigElementList);
			testConfigElementList.remove(indexOfNodeToDelete);
			removedFromDataModel = true;
		} else if (parentDatatype instanceof TestConfigElement) {
			TestConfigElement parentTestConfigElement = (TestConfigElement) parentDatatype;
			// If there is a PropertyList node, we have to increase the index
			if (parentTestConfigElement.getPropertyList() != null) {
				indexOfNodeToDelete--;
			}
			if (nodeToRemove.getDatatype() instanceof PropertyList) {
				parentTestConfigElement.setPropertyList(null);
				removedFromDataModel = true;
			} else {
				List<TestConfigElementContainer> testConfigElementContainerList = parentTestConfigElement.getTestConfigElementList();
				TestConfigElementContainer containerToBeDeleted = testConfigElementContainerList.get(indexOfNodeToDelete);
				updateOrdersAfterBeforeDeletion(indexOfNodeToDelete, testConfigElementContainerList);
				testConfigElementContainerList.remove(containerToBeDeleted);
				removedFromDataModel = true;
			}
		} else if (parentDatatype instanceof PropertyList) {
			PropertyList propertyList = (PropertyList) parentDatatype;
			List<PropertyContainer> propertyListProperyList = propertyList.getPropertyList();
			decreaseOrderOfAllElementWithOrderIndexHigherThanIndexOfNodeToDelete(indexOfNodeToDelete, propertyListProperyList);
			propertyListProperyList.remove(indexOfNodeToDelete);
			removedFromDataModel = true;
		}

		if (removedFromDataModel) {
			if (parentDatatype.getDatatypeState() == DatatypeState.PERSISTENT) {
				parentDatatype.setDatatypeState(DatatypeState.MODIFIED);
			}
			parentNode.getChildren().remove(nodeToRemove);
		}
	}

	private void updateOrdersAfterBeforeDeletion(int indexOfNodeToDelete, List<TestConfigElementContainer> testConfigElementList) {
		for (TestConfigElementContainer testConfigElementContainer : testConfigElementList) {
			Integer order = testConfigElementContainer.getOrderIndex().getValue();
			if (order > indexOfNodeToDelete) {
				testConfigElementContainer.setOrderIndex(--order);
				if(testConfigElementContainer.getDatatypeState() == DatatypeState.PERSISTENT){
					testConfigElementContainer.setDatatypeState(DatatypeState.MODIFIED);
				}
			}
		}
	}

	private void decreaseOrderOfAllElementWithOrderIndexHigherThanIndexOfNodeToDelete(int indexOfNodeToDelete, List<PropertyContainer> propertyListProperyList) {
		for (PropertyContainer propertyContainer : propertyListProperyList) {
			Integer order = propertyContainer.getOrderIndex().getValue();
			if (order > indexOfNodeToDelete) {
				propertyContainer.setOrderIndex(--order);
				if(propertyContainer.getDatatypeState() == DatatypeState.PERSISTENT){
					propertyContainer.setDatatypeState(DatatypeState.MODIFIED);
				}
			}
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MasterDetailTreeNode createMasterDetailRepresentation(Datatype datatype) {
		return MasterDetailTreeNodeCreatorForAllDatatypes.getInstance().create(datatype, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public XMLEditorTextPart createXmlRepresentation(Datatype datatype) {
		return DatatypeXMLEditorTextPartCreator.getInstance().create(datatype, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TestConfiguration createDefaultDatatype(SchemaConfig config) {
		return (TestConfiguration) TestConfigurationElementFactory.createTestConfiguration(config);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AddDialogLabelProvider getAddDialogLabelProvider() {
		return new TestConfigurationMaintainanceMultiplePageAddDialogLabelProvider();
	}

	@Override
	public ElementPickerParameter getElementPickerParameter(Datatype datatype) {
		ElementPickerContentProvider contentProvider = new ElementPickerDefaultContentProvider(getPossibleChildren(datatype));
		ILabelProvider inputFieldLabelProvider = null;
		NabuccoTableSorter tableSorter = new ConfigSingleColumnTableSorter();
		NabuccoTableColumnInfo[] tableColumnInfo = createColumnInfo();
		ElementPickerParameter result = new ElementPickerParameter(tableSorter, inputFieldLabelProvider, contentProvider, tableColumnInfo);
		return result;
	}

	private NabuccoTableColumnInfo[] createColumnInfo() {
		NabuccoTableColumnInfo[] result = new NabuccoTableColumnInfo[] { new NabuccoTableColumnInfo(
				"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.title",
				"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.tooltip", 200, SWT.LEFT, SWT.LEFT,
				new TestConfigurationMaintainanceMultiplePageAddDialogLabelProvider()) };
		return result;
	}


	@Override
	public LabelForDialog getLabelForDialog() {
		LabelForDialog result = new LabelForDialog("org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.title",
				"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.message",
				"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.shellTitle",
				"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.messageTable",
				"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.messageCombo",
		"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.pathLabel");
		return result;
	}

	@Override
	public boolean up(ISelection selection) {
		TreeSelection treeSelection = (TreeSelection) selection;
		Object firstElement = treeSelection.getFirstElement();
		if (firstElement instanceof MasterDetailTreeNode) {
			MasterDetailTreeNode treeNode = (MasterDetailTreeNode) firstElement;
			Datatype datatypeToMove = treeNode.getDatatype();
			MasterDetailTreeNode parentTreeNode = treeNode.getParent();
			Datatype parentDatatype = parentTreeNode.getDatatype();
			int indexOfNodeToMove = parentTreeNode.getChildren().indexOf(treeNode);
			if (datatypeToMove instanceof TestConfigElement) {
				if (parentDatatype instanceof TestConfiguration) {
					TestConfiguration parentTestConfiguration = (TestConfiguration) parentDatatype;
					TestConfigElementContainer elementContainerToMove = parentTestConfiguration.getTestConfigElementList().get(indexOfNodeToMove);

					Integer currentOrder = elementContainerToMove.getOrderIndex().getValue();
					setContainerOrder(parentTestConfiguration, currentOrder - 1, currentOrder);
					elementContainerToMove.setOrderIndex(currentOrder - 1);
					if(elementContainerToMove.getDatatypeState() == DatatypeState.PERSISTENT){
						elementContainerToMove.setDatatypeState(DatatypeState.MODIFIED);
					}
					elementSorter.sort(parentTestConfiguration.getTestConfigElementList());
					return true;
				} else if (parentDatatype instanceof TestConfigElement) {
					TestConfigElement parentTestConfigElement = (TestConfigElement) parentDatatype;
					// catch the case that a property list is on first index of
					// the tree
					if (parentTestConfigElement.getPropertyList() != null) {
						indexOfNodeToMove--;
					}
					TestConfigElementContainer elementContainerToMove = parentTestConfigElement.getTestConfigElementList().get(indexOfNodeToMove);
					Integer currentOrder = elementContainerToMove.getOrderIndex().getValue();
					// catch the case that a property list is on first index of
					// the tree
					if (indexOfNodeToMove > 0) {
						setContainerOrder(parentTestConfigElement, currentOrder - 1, currentOrder);
						elementContainerToMove.setOrderIndex(currentOrder - 1);
						if(elementContainerToMove.getDatatypeState() == DatatypeState.PERSISTENT){
							elementContainerToMove.setDatatypeState(DatatypeState.MODIFIED);
						}
						elementSorter.sort(parentTestConfigElement.getTestConfigElementList());
						return true;
					}
				}
			} else if (datatypeToMove instanceof Property && parentDatatype instanceof PropertyList) {
				PropertyList parentPropertyList = (PropertyList) parentDatatype;
				PropertyContainer propertyContainer = parentPropertyList.getPropertyList().get(indexOfNodeToMove);

				Integer currentOrder = propertyContainer.getOrderIndex().getValue();
				setContainerOrder(parentPropertyList, currentOrder - 1, currentOrder);
				propertyContainer.setOrderIndex(currentOrder - 1);
				if(propertyContainer.getDatatypeState() == DatatypeState.PERSISTENT){
					propertyContainer.setDatatypeState(DatatypeState.MODIFIED);
				}
				propertySorter.sort(parentPropertyList);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean down(ISelection selection) {
		TreeSelection treeSelection = (TreeSelection) selection;
		Object firstElement = treeSelection.getFirstElement();
		if (firstElement instanceof MasterDetailTreeNode) {
			MasterDetailTreeNode treeNode = (MasterDetailTreeNode) firstElement;
			Datatype datatypeToMove = treeNode.getDatatype();
			MasterDetailTreeNode parentTreeNode = treeNode.getParent();
			Datatype parentDatatype = parentTreeNode.getDatatype();
			int indexOfNodeToMove = parentTreeNode.getChildren().indexOf(treeNode);
			if (datatypeToMove instanceof TestConfigElement) {
				if (parentDatatype instanceof TestConfiguration) {
					TestConfiguration parentTestConfiguration = (TestConfiguration) parentDatatype;
					TestConfigElementContainer elementContainerToMove = parentTestConfiguration.getTestConfigElementList().get(indexOfNodeToMove);

					Integer currentOrder = elementContainerToMove.getOrderIndex().getValue();
					setContainerOrder(parentTestConfiguration, currentOrder + 1, currentOrder);
					elementContainerToMove.setOrderIndex(currentOrder + 1);
					if(elementContainerToMove.getDatatypeState() == DatatypeState.PERSISTENT){
						elementContainerToMove.setDatatypeState(DatatypeState.MODIFIED);
					}
					elementSorter.sort(parentTestConfiguration.getTestConfigElementList());
					return true;
				} else if (parentDatatype instanceof TestConfigElement) {
					TestConfigElement parentTestConfigElement = (TestConfigElement) parentDatatype;
					// catch the case that a property list is on first index of
					// the tree
					if (parentTestConfigElement.getPropertyList() != null) {
						indexOfNodeToMove--;
					}
					TestConfigElementContainer elementContainerToMove = parentTestConfigElement.getTestConfigElementList().get(indexOfNodeToMove);
					Integer currentOrder = elementContainerToMove.getOrderIndex().getValue();
					// catch the case that a property list is on first index of
					// the tree
					setContainerOrder(parentTestConfigElement, currentOrder + 1, currentOrder);
					elementContainerToMove.setOrderIndex(currentOrder + 1);
					if(elementContainerToMove.getDatatypeState() == DatatypeState.PERSISTENT){
						elementContainerToMove.setDatatypeState(DatatypeState.MODIFIED);
					}
					elementSorter.sort(parentTestConfigElement.getTestConfigElementList());
					return true;
				}
			} else if (datatypeToMove instanceof Property && parentDatatype instanceof PropertyList) {
				PropertyList parentPropertyList = (PropertyList) parentDatatype;
				PropertyContainer propertyContainer = parentPropertyList.getPropertyList().get(indexOfNodeToMove);

				Integer currentOrder = propertyContainer.getOrderIndex().getValue();
				setContainerOrder(parentPropertyList, currentOrder + 1, currentOrder);
				propertyContainer.setOrderIndex(currentOrder + 1);
				if(propertyContainer.getDatatypeState() == DatatypeState.PERSISTENT){
					propertyContainer.setDatatypeState(DatatypeState.MODIFIED);
				}
				propertySorter.sort(parentPropertyList);
				return true;
			}
		}
		return false;
	}

	private void setContainerOrder(TestConfigElement parentTestConfigElement, int targetOrder, Integer orderToSet) {
		List<TestConfigElementContainer> testConfigElementList = parentTestConfigElement.getTestConfigElementList();
		for (TestConfigElementContainer testConfigElementContainer : testConfigElementList) {
			if (testConfigElementContainer.getOrderIndex().getValue() == targetOrder) {
				testConfigElementContainer.setOrderIndex(orderToSet);
				if(testConfigElementContainer.getDatatypeState() == DatatypeState.PERSISTENT){
					testConfigElementContainer.setDatatypeState(DatatypeState.MODIFIED);
				}
			}
		}
	}

	private void setContainerOrder(TestConfiguration parentTestConfiguration, int targetOrder, Integer orderToSet) {
		List<TestConfigElementContainer> testConfigElementList = parentTestConfiguration.getTestConfigElementList();
		for (TestConfigElementContainer testConfigElementContainer : testConfigElementList) {
			if (testConfigElementContainer.getOrderIndex().getValue() == targetOrder) {
				testConfigElementContainer.setOrderIndex(orderToSet);
				if(testConfigElementContainer.getDatatypeState() == DatatypeState.PERSISTENT){
					testConfigElementContainer.setDatatypeState(DatatypeState.MODIFIED);
				}
			}
		}
	}

	private void setContainerOrder(PropertyList parentPropertyList, int targetOrder, Integer orderToSet) {
		List<PropertyContainer> propertyContainerList = parentPropertyList.getPropertyList();
		for (PropertyContainer propertyContainer : propertyContainerList) {
			if (propertyContainer.getOrderIndex().getValue() == targetOrder) {
				propertyContainer.setOrderIndex(orderToSet);
				if(propertyContainer.getDatatypeState() == DatatypeState.PERSISTENT){
					propertyContainer.setDatatypeState(DatatypeState.MODIFIED);
				}
			}
		}
	}

}
