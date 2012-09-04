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
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.base.facade.exception.client.ClientException;
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
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.TestConfigurationMaintenanceMultiPageEditView;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.TestConfigurationMaintenanceMasterDetailLabelProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.ConfigElementCloneElementPickerContentProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.ConfigElementSelectElementPickerContentProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.ExecutionTypeMenuItem;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.PropertyListCloneElementPickerContentProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.PropertyListSelectElementPickerContentProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.TestConfigurationMaintenanceMultiplePageEditViewCloner;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.TestConfigurationMaintenanceMultiplePageSelectCloneDescriptionColumnDialogLabelProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.TestConfigurationMaintenanceMultiplePageSelectCloneKeyColumnDialogLabelProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.TestConfigurationMaintenanceMultiplePageSelectCloneNameColumnDialogLabelProvider;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.dependencies.DependencyChangeMenuItem;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu.dependencies.DependencyChangeMenuItemListener;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.listener.ExecuteSelectionListener;
import org.nabucco.testautomation.property.facade.datatype.BooleanProperty;
import org.nabucco.testautomation.property.facade.datatype.DateProperty;
import org.nabucco.testautomation.property.facade.datatype.FileProperty;
import org.nabucco.testautomation.property.facade.datatype.NumericProperty;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.TextProperty;
import org.nabucco.testautomation.property.facade.datatype.XmlProperty;
import org.nabucco.testautomation.property.facade.datatype.base.HierarchyLevelType;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyComposite;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyContainer;
import org.nabucco.testautomation.result.facade.datatype.ExecutionType;
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

    private static final String EDIT_ELEMENT = ".Edit";

    private static final String EDIT_EXECUTION_TYPE_ELEMENT = ".Edit.ExecutionType";

    private static final String MANUAL_EXECUTION_TYPE_ELEMENT = ".Edit.ExecutionType.Manual";

    private static final String AUTOMATED_EXECUTION_TYPE_ELEMENT = ".Edit.ExecutionType.Automated";

    private static final String DEPENDENCIES = ".Dependencies";
    
    private static final String CHANGE_ALL = ".Dependencies.All";
    
    private static final String CHANGE_NOT_SET = ".Dependencies.NotSet";

    private static final String PROPERTY_LIST = ".PropertyList";

    private static final String EXECUTE = ".Execute";

    private static final String REMOVE = ".Remove";

    // private static final String CHANGEBRAND = ".ChangeBrand";

    public static boolean isAllowedChild(Datatype parent, Datatype child) {
        if (parent instanceof TestConfiguration) {
            if (child instanceof TestConfigElement) {
                return ((TestConfigElement) child).getSchemaElement().getLevel() == HierarchyLevelType.ONE;
            }
        }
        if (parent instanceof TestConfigElement) {
            if (child instanceof TestConfigElement) {
                int childSchemaElementLevel = ((TestConfigElement) child).getSchemaElement().getLevel().ordinal();
                int parentSchemaElementLevel = ((TestConfigElement) parent).getSchemaElement().getLevel().ordinal();
                return (parentSchemaElementLevel == (childSchemaElementLevel - 1));
            }
            if (child instanceof PropertyList) {
                if (((TestConfigElement) parent).getPropertyList() == null) {
                    return true;
                }
            }
        }
        if (parent instanceof PropertyComposite) {
            if (child instanceof Property) {
                return true;
            }
        }
        return false;
    }

    public static boolean canBeChildOf(Datatype parent, Datatype child) {
        if (parent instanceof TestConfiguration) {
            if (child instanceof TestConfigElement) {
                TestConfigElement element = (TestConfigElement) child;
                TestConfiguration testConfiguration = (TestConfiguration) parent;
                SchemaConfig schemaConfig = testConfiguration.getSchemaConfig();
                SchemaElement schemaElement = element.getSchemaElement();
                if (schemaConfig.getSchemaElementList().contains(schemaElement)) {
                    return true;
                }
            }
        }
        if (parent instanceof TestConfigElement) {
            TestConfigElement parentElement = (TestConfigElement) parent;
            if (child instanceof TestConfigElement) {
                TestConfigElement element = (TestConfigElement) child;
                SchemaElement parentSchemaElement = parentElement.getSchemaElement();
                SchemaElement schemaElement = element.getSchemaElement();
                if (parentSchemaElement.getSchemaElementList().contains(schemaElement)) {
                    return true;
                }
            }
            if (child instanceof PropertyList) {
                Flag propertyContainer = parentElement.getSchemaElement().getPropertyContainer();
                if (propertyContainer != null && propertyContainer.getValue() != null && propertyContainer.getValue()) {
                    if (parentElement.getPropertyList() == null) {
                        return true;
                    }
                }
            }
        }
        if (parent instanceof PropertyComposite) {
            if (child instanceof Property) {
                return true;
            }
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

    @SuppressWarnings("unchecked")
    private static Class<? extends Datatype>[] getPossibleChildrenTypes(PropertyList datatype) {
        return new Class[] { PropertyList.class, BooleanProperty.class, DateProperty.class, NumericProperty.class,
                TextProperty.class, XmlProperty.class, FileProperty.class };
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Datatype>[] getPossibleChildrenTypes(Property datatype) {
        return new Class[] {};
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

    public static Menu getContextMenu(ISelection selection, TreeViewer treeViewer,
            TestConfigurationMaintenanceMultiPageEditViewModelHandler modelHandler) throws ClientException {

        Menu result = new Menu(treeViewer.getTree());
        TreeSelection treeSelection = (TreeSelection) selection;
        Object firstElement = treeSelection.getFirstElement();

        if (firstElement instanceof MasterDetailTreeNode) {
            MasterDetailTreeNode treeNode = (MasterDetailTreeNode) firstElement;
            Datatype datatype = treeNode.getDatatype();

            if (!MasterDetailHelper.isDatatypeEditable(MasterDetailHelper.getRootNode(treeNode).getDatatype())) {
                return null;
            }

            // Menu 'new'
            Menu newElementMenu = createMenu(result, TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID
                    + NEW_ELEMENT, "icons/add.png");
            // Menu 'select'
            Menu selectElementMenu = createMenu(result,
                    TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + SELECT_ELEMENT,
                    "icons/reuse.png");
            // Menu 'clone'
            Menu cloneElementMenu = createMenu(result, TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID
                    + CLONE_ELEMENT, "icons/copy.png");

            // Menu 'edit'
            Menu editElementMenu = createMenu(result, TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID
                    + EDIT_ELEMENT, ConfigImageRegistry.ICON_EDIT.getId());
            
            // Menu 'dependencies'
            Menu dependenciesMenu = createMenu(result, TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID
            		+ DEPENDENCIES, ConfigImageRegistry.ICON_EDIT.getId());

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
                    TestConfigElement testConfigElement = TestConfigurationElementFactory
                            .createTestConfigElement(schemaElement);
                    Image image = ImageProvider.createImage(TestConfigurationMaintenanceMasterDetailLabelProvider
                            .getInstance().getImage(testConfigElement));
                    new NewDatatypeMenuItem(newElementMenu, treeNode, modelHandler, testConfigElement, treeViewer,
                            TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT,
                            variableTextMap, image);

                    // Select
                    new SelectDatatypeMenuItem(
                            selectElementMenu,
                            treeNode,
                            modelHandler,
                            treeViewer,
                            TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT,
                            getElementPickerParameterForSelectAndClone(new ConfigElementCloneElementPickerContentProvider(
                                    schemaElement)), modelHandler.getLabelForDialog(), variableTextMap, image);

                    // Clone
                    new CloneDatatypeMenuItem(
                            cloneElementMenu,
                            treeNode,
                            modelHandler,
                            treeViewer,
                            TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT,
                            getElementPickerParameterForSelectAndClone(new ConfigElementCloneElementPickerContentProvider(
                                    schemaElement)), modelHandler.getLabelForDialog(), variableTextMap, image, cloner);

                    // Edit
                    editElementMenu.setEnabled(false);

                    // Separator
                    new MenuItem(result, SWT.SEPARATOR);
                    
                    // Batch Dependencies
                    new DependencyChangeMenuItem(dependenciesMenu, treeNode, modelHandler, treeViewer, TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + CHANGE_NOT_SET, null, image, DependencyChangeMenuItemListener.Action.CHANGENOTSET);
                    new DependencyChangeMenuItem(dependenciesMenu, treeNode, modelHandler, treeViewer, TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + CHANGE_ALL, null, image, DependencyChangeMenuItemListener.Action.CHANGEALL);
                 // Separator
                    new MenuItem(dependenciesMenu, SWT.SEPARATOR);
                    new DependencyChangeMenuItem(dependenciesMenu, treeNode, modelHandler, treeViewer, TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + REMOVE, null, image, DependencyChangeMenuItemListener.Action.REMOVE);
                    
                    // Multibrandchange
                    // new BrandChangeMenuItem(menu, parentNode, model, treeViewer, textKey,
                    // elementPickerParameter, labelForDialog, values, image, cloner);
                    // MenuItem menuItem = new MenuItem(result, SWT.CASCADE);
                    // menuItem.setText(I18N.i18n(TestConfigurationMaintenanceMultiplePageEditViewModelHandlerImpl.ID
                    // + CHANGEBRAND));
//                     Menu brandChangeMenu = getBrandChangeMenu(result, treeNode, modelHandler);
                    // menuItem.setMenu(brandChangeMenu);

                    // Separator
                    // new MenuItem(result, SWT.SEPARATOR);

                    // Execute
                    MenuItem item = new MenuItem(result, SWT.CASCADE);
                    image = ImageProvider.createImage(ConfigImageRegistry.ICON_RUN.getId());
                    item.setImage(image);
                    item.setText(I18N.i18n(TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + EXECUTE));
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

                    TestConfigElement newTestConfigElement = TestConfigurationElementFactory
                            .createTestConfigElement(schemaElement);
                    Image image = ImageProvider.createImage(TestConfigurationMaintenanceMasterDetailLabelProvider
                            .getInstance().getImage(newTestConfigElement));

                    // New
                    new NewDatatypeMenuItem(newElementMenu, treeNode, modelHandler, newTestConfigElement, treeViewer,
                            TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT,
                            variableTextMap, image);

                    // Select
                    new SelectDatatypeMenuItem(
                            selectElementMenu,
                            treeNode,
                            modelHandler,
                            treeViewer,
                            TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT,
                            getElementPickerParameterForSelectAndClone(new ConfigElementSelectElementPickerContentProvider(
                                    schemaElement)), modelHandler.getLabelForDialog(), variableTextMap, image);

                    // Clone
                    new CloneDatatypeMenuItem(
                            cloneElementMenu,
                            treeNode,
                            modelHandler,
                            treeViewer,
                            TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + SCHEMA_ELEMENT,
                            getElementPickerParameterForSelectAndClone(new ConfigElementCloneElementPickerContentProvider(
                                    schemaElement)), modelHandler.getLabelForDialog(), variableTextMap, image, cloner);
                }

                // Edit
                HashMap<String, String> values = new HashMap<String, String>();
                editElementMenu.setEnabled(true);
                Menu editExecutionTypeMenu = createMenu(editElementMenu,
                        TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + EDIT_EXECUTION_TYPE_ELEMENT,
                        ConfigImageRegistry.ICON_EDIT.getId());
                new ExecutionTypeMenuItem(editExecutionTypeMenu, testConfigElement, ExecutionType.AUTOMATED,
                        TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID
                                + AUTOMATED_EXECUTION_TYPE_ELEMENT, values, ImageProvider.createImage(ConfigImageRegistry.ICON_EDIT.getId()));
                new ExecutionTypeMenuItem(editExecutionTypeMenu, testConfigElement, ExecutionType.MANUAL, 
                        TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID
                                + MANUAL_EXECUTION_TYPE_ELEMENT, values, ImageProvider.createImage(ConfigImageRegistry.ICON_EDIT.getId()));

                // create property list entry
                if (parentSchemaElement.getPropertyContainer() != null
                        && parentSchemaElement.getPropertyContainer().getValue()
                        && testConfigElement.getPropertyList() == null) {

                    PropertyContainer propertyContainer = TestConfigurationElementFactory.createProperty(
                            PropertyList.class, null);
                    Image image = ImageProvider.createImage(TestConfigurationMaintenanceMasterDetailLabelProvider
                            .getInstance().getImage(propertyContainer));
                    new NewDatatypeMenuItem(newElementMenu, treeNode, modelHandler, propertyContainer, treeViewer,
                            TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + PROPERTY_LIST, null,
                            image);

                    new SelectDatatypeMenuItem(selectElementMenu, treeNode, modelHandler, treeViewer,
                            TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + PROPERTY_LIST,
                            getElementPickerParameter(new PropertyListSelectElementPickerContentProvider()),
                            modelHandler.getLabelForDialog(), null, image);

                    new CloneDatatypeMenuItem(cloneElementMenu, treeNode, modelHandler, treeViewer,
                            TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + PROPERTY_LIST,
                            getElementPickerParameter(new PropertyListCloneElementPickerContentProvider()),
                            modelHandler.getLabelForDialog(), null, image, cloner);
                }
                // Separator
                new MenuItem(result, SWT.SEPARATOR);

                // Remove
                Image image = ImageProvider.createImage("icons/delete.png");
                new RemoveDatatypeMenuItem(result, treeNode, modelHandler, treeViewer,
                        TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + REMOVE, null, image);
                
                // Separator
                new MenuItem(result, SWT.SEPARATOR);
                
                // Batch Dependencies
                new DependencyChangeMenuItem(dependenciesMenu, treeNode, modelHandler, treeViewer, TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + CHANGE_NOT_SET, null, image, DependencyChangeMenuItemListener.Action.CHANGENOTSET);
                new DependencyChangeMenuItem(dependenciesMenu, treeNode, modelHandler, treeViewer, TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + CHANGE_ALL, null, image, DependencyChangeMenuItemListener.Action.CHANGEALL);
             // Separator
                new MenuItem(dependenciesMenu, SWT.SEPARATOR);
                new DependencyChangeMenuItem(dependenciesMenu, treeNode, modelHandler, treeViewer, TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + REMOVE, null, image, DependencyChangeMenuItemListener.Action.REMOVE);
                
            } else if (datatype instanceof PropertyList) {
                Class<? extends Datatype>[] possibleChildrenTypes = getPossibleChildrenTypes((PropertyList) datatype);
                addPropertyMenuItems(newElementMenu, possibleChildrenTypes, treeNode, treeViewer, modelHandler);

                // Separator
                new MenuItem(result, SWT.SEPARATOR);

                // Remove
                Image image = ImageProvider.createImage("icons/delete.png");
                new RemoveDatatypeMenuItem(result, treeNode, modelHandler, treeViewer,
                        TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + REMOVE, null, image);
            } else if (datatype instanceof Property) {
                // Separator
                new MenuItem(result, SWT.SEPARATOR);

                // Remove
                Image image = ImageProvider.createImage("icons/delete.png");
                new RemoveDatatypeMenuItem(result, treeNode, modelHandler, treeViewer,
                        TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + REMOVE, null, image);
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

//    /**
//     * 
//     * @return
//     */
//     private static Menu getBrandChangeMenu(Menu parent, MasterDetailTreeNode node,
//     TestConfigurationMaintenanceMultiplePageEditViewModelHandler handler) {
//     class BrandChangeSelectionListener implements SelectionListener {
//     private Code brand;
//     private MasterDetailTreeNode node;
//     private TestConfigurationMaintenanceMultiplePageEditViewModelHandler handler;
//    
//     /**
//     * Creates a new {@link DataModelManager.BrandChangeSelectionListener} instance.
//     *
//     */
//     public BrandChangeSelectionListener(Code brand, MasterDetailTreeNode node) {
//     this.node = node;
//     this.brand = brand;
//     }
//    
//     /**
//     * {@inheritDoc}
//     */
//     @Override
//     public void widgetDefaultSelected(SelectionEvent arg0) {}
//    
//     /**
//     * {@inheritDoc}
//     */
//     @Override
//     public void widgetSelected(SelectionEvent arg0) {
//     setBrand(node);
//     }
//    
//     private void setBrand(MasterDetailTreeNode node) {
//	     Datatype datatype = node.getDatatype();
//	     if(datatype instanceof TestConfigElement) {
//		     TestConfigElement tce = (TestConfigElement) datatype;
//		     Code oldBrand = tce.getBrandType();
//		     tce.setBrandType(brand);
//		     tce.setDatatypeState(DatatypeState.MODIFIED);
//		     TestConfigurationMaintenanceMultiPageEditView view = (TestConfigurationMaintenanceMultiPageEditView)Activator.getDefault().getView("org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.TestConfigurationMaintenanceMultiplePageEditView");
//	     	// view.getModel().updateProperty("value", oldBrand, tce.getBrandType());
//	     	view.getModel().setDirty(true);
//	     }
//	     if(datatype instanceof TestConfigElement || datatype instanceof TestConfiguration) {
//		     List<MasterDetailTreeNode> children = node.getChildren();
//		     for(MasterDetailTreeNode child: children) {
//		    	 setBrand(child);
//		     }
//	     }
//     }
//    
//     }
//     Menu brandChangeMenu = new Menu(parent);
//     MenuItem mi = new MenuItem(brandChangeMenu, SWT.CASCADE);
//     mi.setText("");
//     mi.addSelectionListener(new BrandChangeSelectionListener((Code) null, node));
//     List<DynamicCodeCode> codes = getDynamicCodes("nabucco.testautomation.brand");
//     for(Code code: codes) {
//     MenuItem item = new MenuItem(brandChangeMenu, SWT.CASCADE);
//     item.setText(code.getName().getValue());
//     item.addSelectionListener(new BrandChangeSelectionListener(code, node));
//     }
//     return brandChangeMenu;
//     }

    /**
     * 
     * @param string
     * @return
     */
    // private static List<DynamicCodeCode> getDynamicCodes(String string) {
    // List<DynamicCodeCode> codes;
    // try {
    // CodePath codePath = new CodePath(string);
    // SearchDynamicCodeDelegate searchDynamicCodeDelegate =
    // DynamicCodeComponentServiceDelegateFactory
    // .getInstance().getSearchDynamicCode();
    //
    // CodePathSearchMsg rqMsg = new CodePathSearchMsg();
    // rqMsg.setCodePath(codePath);
    // DynamicCodeCodeListMsg rsMsg = searchDynamicCodeDelegate.searchByCodePath(rqMsg);
    // codes = rsMsg.getCodeList();
    // } catch (ClientException e) {
    // codes = new ArrayList<DynamicCodeCode>();
    // Activator.getDefault().logError("Problem when accessing SearchDynamicCodeDelegate:");
    // Activator.getDefault().logError(e);
    // }
    // return codes;
    // }

    private static void addPropertyMenuItems(final Menu parentMenu, Class<? extends Datatype>[] possibleChildrenTypes,
            MasterDetailTreeNode treeNode, TreeViewer treeViewer,
            TestConfigurationMaintenanceMultiPageEditViewModelHandler modelHandler) {
        // Produce elements
        for (Class<? extends Datatype> clazz : possibleChildrenTypes) {

            // create menu entries for each allowed class
            Datatype datatype = TestConfigurationElementFactory.createProperty(clazz, null);
            String imagePath = TestConfigurationMaintenanceMasterDetailLabelProvider.getInstance().getImage(datatype);
            Image image = ImageProvider.createImage(imagePath);

            new NewDatatypeMenuItem(parentMenu, treeNode, modelHandler, datatype, treeViewer,
                    TestConfigurationMaintenanceMultiPageEditViewModelHandlerImpl.ID + "." + clazz.getSimpleName(),
                    null, image);
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

    private static ElementPickerParameter getElementPickerParameterForSelectAndClone(
            ElementPickerContentProvider contentProvider) {
        ILabelProvider inputFieldLabelProvider = null;
        NabuccoTableColumnInfo[] tableColumnInfo = createColumnInfoForSelectAndClone();
        NabuccoTableSorter tableSorter = new ConfigSelectCloneTableSorter();
        TestConfigurationAddDialogTableFilter tableFilter = new TestConfigurationAddDialogTableFilter();
        ElementPickerParameter result = new ElementPickerParameter(tableSorter, tableFilter, inputFieldLabelProvider,
                contentProvider, tableColumnInfo);

        return result;
    }

    private static ElementPickerParameter getElementPickerParameter(ElementPickerContentProvider contentProvider) {
        ILabelProvider inputFieldLabelProvider = null;
        NabuccoTableColumnInfo[] tableColumnInfo = createColumnInfo();
        NabuccoTableSorter tableSorter = new ConfigSingleColumnTableSorter();
        TestConfigurationAddDialogTableFilter tableFilter = new TestConfigurationAddDialogTableFilter();
        ElementPickerParameter result = new ElementPickerParameter(tableSorter, tableFilter, inputFieldLabelProvider,
                contentProvider, tableColumnInfo);

        return result;
    }

    private static NabuccoTableColumnInfo[] createColumnInfo() {
        NabuccoTableColumnInfo col1 = new NabuccoTableColumnInfo(
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.key.title",
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.key.tooltip", 125,
                SWT.LEFT, SWT.LEFT, new TestConfigurationMaintenanceMultiPageAddDialogKeyLabelProvider());

        col1.setHidable(false);

        NabuccoTableColumnInfo col2 = new NabuccoTableColumnInfo(
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.name.title",
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.name.tooltip", 250,
                SWT.LEFT, SWT.LEFT, new TestConfigurationMaintenanceMultiPageAddDialogNameLabelProvider());

        col2.setHidable(false);

        NabuccoTableColumnInfo col3 = new NabuccoTableColumnInfo(
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.description.title",
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.description.tooltip", 200,
                SWT.LEFT, SWT.LEFT, new TestConfigurationMaintenanceMultiPageAddDialogDescriptionLabelProvider());

        return new NabuccoTableColumnInfo[] { col1, col2, col3 };
    }

    private static NabuccoTableColumnInfo[] createColumnInfoForSelectAndClone() {
        NabuccoTableColumnInfo col1 = new NabuccoTableColumnInfo(
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.selectclone.dialog.column.key.name",
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.selectclone.dialog.column.key.tooltip",
                125, SWT.LEFT, SWT.LEFT,
                new TestConfigurationMaintenanceMultiplePageSelectCloneKeyColumnDialogLabelProvider());

        col1.setHidable(false);

        NabuccoTableColumnInfo col2 = new NabuccoTableColumnInfo(
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.selectclone.dialog.column.name.name",
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.selectclone.dialog.column.name.tooltip",
                250, SWT.LEFT, SWT.LEFT,
                new TestConfigurationMaintenanceMultiplePageSelectCloneNameColumnDialogLabelProvider());

        col2.setHidable(false);

        NabuccoTableColumnInfo col3 = new NabuccoTableColumnInfo(
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.selectclone.dialog.column.description.name",
                "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.selectclone.dialog.column.description.tooltip",
                200, SWT.LEFT, SWT.LEFT,
                new TestConfigurationMaintenanceMultiplePageSelectCloneDescriptionColumnDialogLabelProvider());

        return new NabuccoTableColumnInfo[] { col1, col2, col3 };
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

}
