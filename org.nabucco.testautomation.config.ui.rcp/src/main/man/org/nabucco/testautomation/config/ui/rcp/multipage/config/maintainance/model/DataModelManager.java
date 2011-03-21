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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableColumnInfo;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableSorter;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailHelper;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.contextmenu.CloneDatatypeMenuItem;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.contextmenu.NewDatatypeMenuItem;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.contextmenu.RemoveDatatypeMenuItem;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.contextmenu.SelectDatatypeMenuItem;
import org.nabucco.framework.plugin.base.component.picker.dialog.ElementPickerContentProvider;
import org.nabucco.framework.plugin.base.component.picker.dialog.ElementPickerParameter;
import org.nabucco.framework.plugin.base.layout.ImageProvider;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.images.ConfigImageRegistry;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.TestConfigurationMaintainanceMasterDetailLabelProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.contextmenu.ConfigElementCloneElementPickerContentProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.contextmenu.ConfigElementSelectElementPickerContentProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.contextmenu.PropertyListCloneElementPickerContentProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.contextmenu.PropertyListSelectElementPickerContentProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.contextmenu.TestConfigurationMaintainanceMultiplePageSelectCloneKeyColumnDialogLabelProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.contextmenu.TestConfigurationMaintainanceMultiplePageSelectCloneNameColumnDialogLabelProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.contextmenu.TestConfigurationMaintainanceMultiplePageSelectCloneOwnerColumnDialogLabelProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.contextmenu.TestConfigurationMaintenanceMultiplePageEditViewCloner;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.listener.ExecuteSelectionListener;
import org.nabucco.testautomation.facade.datatype.base.HierarchyLevelType;
import org.nabucco.testautomation.facade.datatype.property.BooleanProperty;
import org.nabucco.testautomation.facade.datatype.property.DateProperty;
import org.nabucco.testautomation.facade.datatype.property.DoubleProperty;
import org.nabucco.testautomation.facade.datatype.property.FileProperty;
import org.nabucco.testautomation.facade.datatype.property.IntegerProperty;
import org.nabucco.testautomation.facade.datatype.property.LongProperty;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.StringProperty;
import org.nabucco.testautomation.facade.datatype.property.XmlProperty;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * DataModelManager
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class DataModelManager {

	private static final String NEW_DATATYPE = "New Configuration Element";

	private static final String SCHEMA_ELEMENT = ".SchemaElement";

	private static final String CLONE_ELEMENT = ".Clone";

	private static final String SELECT_ELEMENT = ".Select";

	private static final String NEW_ELEMENT = ".New";

	private static final String PROPERTY_LIST = ".PropertyList";

	private static final String EXECUTE = ".Execute";

	private static final String REMOVE = ".Remove";

	public static boolean isAllowedChild(Datatype parent, Datatype child) {

		if (parent instanceof TestConfiguration && child instanceof TestConfigElement) {
			return ((TestConfigElement) child).getSchemaElement().getLevel() == HierarchyLevelType.ONE;
		} else if (parent instanceof TestConfigElement) {
			if (child instanceof TestConfigElement) {
				return ((((TestConfigElement) child).getSchemaElement().getLevel().ordinal() - 1) == ((TestConfigElement) parent).getSchemaElement().getLevel()
						.ordinal());
			} else if (child instanceof PropertyList) {
				return ((TestConfigElement) parent).getPropertyList() == null;
			}
		} else if (parent instanceof PropertyList && child instanceof Property) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends Datatype>[] getPossibleChildrenTypes(Datatype datatype) {
		Class<? extends Datatype>[] result;
		if (datatype instanceof TestConfiguration) {
			result = new Class[] { TestConfigElement.class };
		} else if (datatype instanceof TestConfigElement) {
			result = new Class[] { TestConfigElement.class, PropertyList.class };
		} else if (datatype instanceof PropertyList) {
			result = getPossibleChildrenTypes((PropertyList) datatype);
		} else if (datatype instanceof Property) {
			result = getPossibleChildrenTypes((Property) datatype);
		} else {
			result = new Class[] {};
		}
		return result;
	}

	public static Map<String, Datatype[]> getPossibleChildren(Datatype datatype) {
		HashMap<String, Datatype[]> result = new HashMap<String, Datatype[]>();
		if (datatype instanceof TestConfiguration) {
			SchemaConfig schemaConfig = ((TestConfiguration) datatype).getSchemaConfig();

			// Possible new ConfigurationElements
			List<SchemaElement> possibleSchemaElements = schemaConfig.getSchemaElementList();
			List<Datatype> possibleChildrenList = new ArrayList<Datatype>();

			// ConfigElements
			for (SchemaElement schemaElement : possibleSchemaElements) {
				possibleChildrenList.add(TestConfigurationElementFactory.createTestConfigElement(schemaElement));
			}
			result.put(NEW_DATATYPE, possibleChildrenList.toArray(new Datatype[result.size()]));

		} else if (datatype instanceof TestConfigElement) {
			TestConfigElement testConfigElement = (TestConfigElement) datatype;
			SchemaElement parentSchemaElement = testConfigElement.getSchemaElement();

			// Possible new ConfigurationElements
			List<SchemaElement> possibleSchemaElements = parentSchemaElement.getSchemaElementList();
			List<Datatype> possibleChildrenList = new ArrayList<Datatype>();

			for (SchemaElement schemaElement : possibleSchemaElements) {
				possibleChildrenList.add(TestConfigurationElementFactory.createTestConfigElement(schemaElement));
			}
			if (possibleChildrenList.size() > 0) {
				result.put(NEW_DATATYPE, possibleChildrenList.toArray(new Datatype[result.size()]));
			}
		} else if (datatype instanceof PropertyList) {
			Class<? extends Datatype>[] possibleChildrenTypes = getPossibleChildrenTypes(datatype);
			// Produce elements
			Set<Datatype> children = new HashSet<Datatype>();
			for (Class<? extends Datatype> clazz : possibleChildrenTypes) {
				children.add(TestConfigurationElementFactory.createProperty(clazz, null));
			}
			Datatype[] possibleChildrenInstances = children.toArray(new Datatype[0]);
			result.put(NEW_DATATYPE, possibleChildrenInstances);
		}
		return result;
	}

	public static boolean hasPossibleChildren(Datatype datatype) {
		if (datatype instanceof TestConfiguration || datatype instanceof PropertyList) {
			return true;
		} else if (datatype instanceof TestConfigElement) {
			TestConfigElement testConfigElement = (TestConfigElement) datatype;
			SchemaElement parentSchemaElement = testConfigElement.getSchemaElement();

			// Possible new ConfigurationElements
			List<SchemaElement> possibleSchemaElements = parentSchemaElement.getSchemaElementList();
			if (possibleSchemaElements != null && possibleSchemaElements.size() > 0) {
				return true;
			}
		}
		return false;
	}

	public static Menu getContextMenu(ISelection selection, TreeViewer treeViewer, TestConfigurationMaintainanceMultiplePageEditViewModelHandler modelHandler) {
		Menu result = new Menu(treeViewer.getTree());

		TreeSelection treeSelection = (TreeSelection) selection;
		Object firstElement = treeSelection.getFirstElement();
		if (firstElement instanceof MasterDetailTreeNode) {
			MasterDetailTreeNode treeNode = (MasterDetailTreeNode) firstElement;
			Datatype datatype = treeNode.getDatatype();
			
			if(!MasterDetailHelper.isDatatypeEditable(MasterDetailHelper.getRootNode(treeNode).getDatatype())){
				return null;
			}

			// Menu 'new'
			Menu newElementMenu = createMenu(result, TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + NEW_ELEMENT, "icons/add.png");
			// Menu 'select'
			Menu selectElementMenu = createMenu(result, TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + SELECT_ELEMENT,
					"icons/reuse.png");
			// Menu 'clone'
			Menu cloneElementMenu = createMenu(result, TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + CLONE_ELEMENT, "icons/copy.png");

			TestConfigurationMaintenanceMultiplePageEditViewCloner cloner = new TestConfigurationMaintenanceMultiplePageEditViewCloner();

			if (datatype instanceof TestConfiguration) {
				final TestConfiguration testConfiguration = (TestConfiguration) datatype;

				SchemaConfig schemaConfig = testConfiguration.getSchemaConfig();
				List<SchemaElement> schemaElementList = schemaConfig.getSchemaElementList();
				// create menu entries for each schema element
				for (final SchemaElement schemaElement : schemaElementList) {
					HashMap<String, String> variableTextMap = new HashMap<String, String>();
					variableTextMap.put("name", schemaElement.getName().getValue());

					// New
					TestConfigElement testConfigElement = TestConfigurationElementFactory.createTestConfigElement(schemaElement);
					Image image = ImageProvider.createImage(TestConfigurationMaintainanceMasterDetailLabelProvider.getInstance().getImage(testConfigElement));
					new NewDatatypeMenuItem(newElementMenu, treeNode, modelHandler, testConfigElement, treeViewer,
							TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT, variableTextMap, image);

					// Select
					new SelectDatatypeMenuItem(selectElementMenu, treeNode, modelHandler, treeViewer,
							TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT,
							getElementPickerParameterForSelectAndClone(new ConfigElementCloneElementPickerContentProvider(schemaElement)),
							modelHandler.getLabelForDialog(), variableTextMap, image);

					// Clone
					new CloneDatatypeMenuItem(cloneElementMenu, treeNode, modelHandler, treeViewer,
							TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT,
							getElementPickerParameterForSelectAndClone(new ConfigElementCloneElementPickerContentProvider(schemaElement)),
							modelHandler.getLabelForDialog(), variableTextMap, image, cloner);

					// Separator
					new MenuItem(result, SWT.SEPARATOR);

					// Execute
					MenuItem item = new MenuItem(result, SWT.CASCADE);
					image = ImageProvider.createImage(ConfigImageRegistry.ICON_RUN.getId());
					item.setImage(image);
					item.setText(I18N.i18n(TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + EXECUTE));
					item.addSelectionListener(new ExecuteSelectionListener());
				}

			} else if (datatype instanceof TestConfigElement) {
				TestConfigElement testConfigElement = (TestConfigElement) datatype;
				SchemaElement parentSchemaElement = testConfigElement.getSchemaElement();
				List<SchemaElement> schemaElementList = parentSchemaElement.getSchemaElementList();
				// create menu entries for each schema element
				for (SchemaElement schemaElement : schemaElementList) {
					HashMap<String, String> variableTextMap = new HashMap<String, String>();
					variableTextMap.put("name", schemaElement.getName().getValue());

					TestConfigElement newTestConfigElement = TestConfigurationElementFactory.createTestConfigElement(schemaElement);
					Image image = ImageProvider
							.createImage(TestConfigurationMaintainanceMasterDetailLabelProvider.getInstance().getImage(newTestConfigElement));
					new NewDatatypeMenuItem(newElementMenu, treeNode, modelHandler, newTestConfigElement, treeViewer,
							TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT, variableTextMap, image);

					new SelectDatatypeMenuItem(selectElementMenu, treeNode, modelHandler, treeViewer,
							TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT,
							getElementPickerParameterForSelectAndClone(new ConfigElementSelectElementPickerContentProvider(schemaElement)),
							modelHandler.getLabelForDialog(), variableTextMap, image);

					new CloneDatatypeMenuItem(cloneElementMenu, treeNode, modelHandler, treeViewer,
							TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT,
							getElementPickerParameterForSelectAndClone(new ConfigElementCloneElementPickerContentProvider(schemaElement)),
							modelHandler.getLabelForDialog(), variableTextMap, image, cloner);
				}
				// create property list entry
				if (parentSchemaElement.getPropertyContainer() != null && parentSchemaElement.getPropertyContainer().getValue()
						&& testConfigElement.getPropertyList() == null) {

					PropertyContainer propertyContainer = TestConfigurationElementFactory.createProperty(PropertyList.class, null);
					Image image = ImageProvider.createImage(TestConfigurationMaintainanceMasterDetailLabelProvider.getInstance().getImage(propertyContainer));
					new NewDatatypeMenuItem(newElementMenu, treeNode, modelHandler, propertyContainer, treeViewer,
							TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + PROPERTY_LIST, null, image);

					new SelectDatatypeMenuItem(selectElementMenu, treeNode, modelHandler, treeViewer,
							TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + PROPERTY_LIST,
							getElementPickerParameter(new PropertyListSelectElementPickerContentProvider()), modelHandler.getLabelForDialog(), null, image);

					new CloneDatatypeMenuItem(cloneElementMenu, treeNode, modelHandler, treeViewer,
							TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + PROPERTY_LIST,
							getElementPickerParameter(new PropertyListCloneElementPickerContentProvider()), modelHandler.getLabelForDialog(), null, image,
							cloner);
				}
				// Separator
				new MenuItem(result, SWT.SEPARATOR);

				// Remove
				Image image = ImageProvider.createImage("icons/delete.png");
				new RemoveDatatypeMenuItem(result, treeNode, modelHandler, treeViewer, TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID
						+ REMOVE, null, image);
			} else if (datatype instanceof PropertyList) {
				Class<? extends Datatype>[] possibleChildrenTypes = getPossibleChildrenTypes((PropertyList) datatype);
				addPropertyMenuItems(newElementMenu, possibleChildrenTypes, treeNode, treeViewer, modelHandler);
				
				// Separator
				new MenuItem(result, SWT.SEPARATOR);

				// Remove
				Image image = ImageProvider.createImage("icons/delete.png");
				new RemoveDatatypeMenuItem(result, treeNode, modelHandler, treeViewer, TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID
						+ REMOVE, null, image);
			} else if (datatype instanceof Property) {
				// Separator
				new MenuItem(result, SWT.SEPARATOR);

				// Remove
				Image image = ImageProvider.createImage("icons/delete.png");
				new RemoveDatatypeMenuItem(result, treeNode, modelHandler, treeViewer, TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID
						+ REMOVE, null, image);
			}
			if (newElementMenu.getItemCount() == 0) {
				newElementMenu.getParentItem().setEnabled(false);
			}
			if (selectElementMenu.getItemCount() == 0) {
				selectElementMenu.getParentItem().setEnabled(false);
			}
			if (cloneElementMenu.getItemCount() == 0) {
				cloneElementMenu.getParentItem().setEnabled(false);
			}
		}
		return result;
	}
	
	private static void addPropertyMenuItems(final Menu parentMenu, Class<? extends Datatype>[] possibleChildrenTypes, MasterDetailTreeNode treeNode,
			TreeViewer treeViewer, TestConfigurationMaintainanceMultiplePageEditViewModelHandler modelHandler) {
		// Produce elements
		for (Class<? extends Datatype> clazz : possibleChildrenTypes) {

			// create menu entries for each allowed class
			Datatype datatype = TestConfigurationElementFactory.createProperty(clazz, null);
			String imagePath = TestConfigurationMaintainanceMasterDetailLabelProvider.getInstance().getImage(datatype);
			Image image = ImageProvider.createImage(imagePath);
			
			new NewDatatypeMenuItem(parentMenu, treeNode, modelHandler, datatype, treeViewer,
					TestConfigurationMaintainanceMultiplePageEditViewModelHandlerImpl.ID + "." + clazz.getSimpleName(), null, image);
		}
	}

	/**
	 * Create Menu
	 * 
	 * @param parentMenu
	 * @param label
	 * @return
	 */
	private static Menu createMenu(final Menu parentMenu, final String label, String imagePath) {
		MenuItem newElementMenuIten = new MenuItem(parentMenu, SWT.CASCADE);
		newElementMenuIten.setText(I18N.i18n(label));
		Image image = ImageProvider.createImage(imagePath);
		newElementMenuIten.setImage(image);
		Menu newElementMenu = new Menu(parentMenu);
		newElementMenuIten.setMenu(newElementMenu);
		return newElementMenu;
	}

	private static ElementPickerParameter getElementPickerParameterForSelectAndClone(ElementPickerContentProvider contentProvider) {
		ILabelProvider inputFieldLabelProvider = null;
		NabuccoTableColumnInfo[] tableColumnInfo = createColumnInfoForSelectAndClone();
		NabuccoTableSorter tableSorter = new ConfigSelectCloneTableSorter();
		TestConfigurationAddDialogTableFilter tableFilter = new TestConfigurationAddDialogTableFilter();
		ElementPickerParameter result = new ElementPickerParameter(tableSorter, tableFilter, inputFieldLabelProvider, contentProvider, tableColumnInfo);

		return result;
	}

	private static ElementPickerParameter getElementPickerParameter(ElementPickerContentProvider contentProvider) {
		ILabelProvider inputFieldLabelProvider = null;
		NabuccoTableColumnInfo[] tableColumnInfo = createColumnInfo();
		NabuccoTableSorter tableSorter = new ConfigSingleColumnTableSorter();
		TestConfigurationAddDialogTableFilter tableFilter = new TestConfigurationAddDialogTableFilter();
		ElementPickerParameter result = new ElementPickerParameter(tableSorter, tableFilter, inputFieldLabelProvider, contentProvider, tableColumnInfo);

		return result;
	}

	private static NabuccoTableColumnInfo[] createColumnInfo() {
		NabuccoTableColumnInfo[] result = new NabuccoTableColumnInfo[] { new NabuccoTableColumnInfo(
				"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.title",
				"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.tooltip", 200, SWT.LEFT, SWT.LEFT,
				new TestConfigurationMaintainanceMultiplePageAddDialogLabelProvider()) };
		return result;
	}

	private static NabuccoTableColumnInfo[] createColumnInfoForSelectAndClone() {
		NabuccoTableColumnInfo[] result = new NabuccoTableColumnInfo[] {
				new NabuccoTableColumnInfo("org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.selectclone.dialog.column.key.name",
						"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.selectclone.dialog.column.key.tooltip", 80, SWT.LEFT,
						SWT.LEFT, new TestConfigurationMaintainanceMultiplePageSelectCloneKeyColumnDialogLabelProvider()),
				new NabuccoTableColumnInfo("org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.selectclone.dialog.column.name.name",
						"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.selectclone.dialog.column.name.tooltip", 150, SWT.LEFT,
						SWT.LEFT, new TestConfigurationMaintainanceMultiplePageSelectCloneNameColumnDialogLabelProvider()),
				new NabuccoTableColumnInfo("org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.selectclone.dialog.column.owner.name",
						"org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.selectclone.dialog.column.owner.tooltip", 50, SWT.LEFT,
						SWT.LEFT, new TestConfigurationMaintainanceMultiplePageSelectCloneOwnerColumnDialogLabelProvider())};
		return result;
	}

	public static void normalizeOrderIndicies(TestConfiguration targetTestConfiguration, boolean recursively) {
		normalizeOrderIndicies(targetTestConfiguration.getTestConfigElementList(), recursively);
	}

	public static void normalizeOrderIndicies(TestConfigElement targetTestConfigElement, boolean recursively) {
		normalizeOrderIndicies(targetTestConfigElement.getTestConfigElementList(), recursively);
	}

	public static void normalizeOrderIndicies(List<TestConfigElementContainer> children, boolean recursively) {
		for (int i = 0; i < children.size(); i++) {
			TestConfigElementContainer testConfigElementContainer = children.get(i);
			if (testConfigElementContainer.getOrderIndex().getValue() != i) {
				testConfigElementContainer.setOrderIndex(i);
				if (testConfigElementContainer.getDatatypeState() == DatatypeState.PERSISTENT) {
					testConfigElementContainer.setDatatypeState(DatatypeState.MODIFIED);
				}
			}
		}
		if (recursively) {
			for (TestConfigElementContainer testConfigElementContainer : children) {
				TestConfigElement element = testConfigElementContainer.getElement();
				if (element instanceof TestConfigElement) {
					normalizeOrderIndicies((TestConfigElement) element, true);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static Class<? extends Datatype>[] getPossibleChildrenTypes(PropertyList datatype) {
		return new Class[] { PropertyList.class, BooleanProperty.class, DateProperty.class, DoubleProperty.class, IntegerProperty.class, LongProperty.class,
				StringProperty.class, XmlProperty.class, FileProperty.class };
	}

	@SuppressWarnings("unchecked")
	private static Class<? extends Datatype>[] getPossibleChildrenTypes(Property datatype) {
		return new Class[] {};
	}

}
