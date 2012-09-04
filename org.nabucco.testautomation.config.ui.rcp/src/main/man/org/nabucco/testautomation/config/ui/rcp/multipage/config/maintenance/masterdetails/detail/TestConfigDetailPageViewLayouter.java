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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.detail;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.detail.widget.BaseTypeWidgetFactory;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.detail.widget.enumeration.EnumerationWidgetCreator;
import org.nabucco.framework.plugin.base.model.ViewModel;
import org.nabucco.framework.plugin.base.view.NabuccoFormToolkit;
import org.nabucco.framework.plugin.base.view.NabuccoMessageManager;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.attributevalue.AttributeValueWidgetCreator;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.dependency.DependencyTableWidgetCreator;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script.TestScriptTableWidgetCreator;
import org.nabucco.testautomation.property.ui.rcp.multipage.detail.PropertyDetailPageViewLayouter;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;

/**
 * TestConfigDetailPageViewLayouter
 * 
 * @authors Nicolas Moser, Markus Jorroch, PRODYNA AG
 */
public class TestConfigDetailPageViewLayouter extends PropertyDetailPageViewLayouter {

    /** Name of the TestScript property in TestConfigElement. */
    private static final String PROPERTY_SCRIPT_LIST = "testScriptList";

    private static final String PROPERTY_ATTRIBUTE_VALUE_LIST = "attributeValueList";

    private static final String PROPERTY_DEPENDENCY_LIST = "dependencyList";

    private static final String PROPERTY_EXECUTION_TYPE = "executionType";

    private static final String PROPERTY_SKIP = "skip";

    private static final String PREFIX_SETTER = "set";

    /**
     * Creates a new {@link TestConfigDetailPageViewLayouter} instance.
     * 
     * @param title
     *            the detail view title
     */
    public TestConfigDetailPageViewLayouter(String title) {
        super(title);
    }

    @Override
    protected Control layoutElement(Composite parent, BaseTypeWidgetFactory widgetFactory, Datatype datatype,
            String masterBlockId, NabuccoProperty property, GridData data, boolean readOnly,
            ViewModel externalViewModel, NabuccoMessageManager messageManager) {

        // Validate property name
        String propertyName = property.getName();

        if (!propertyName.equalsIgnoreCase(PROPERTY_SCRIPT_LIST)
                && !propertyName.equalsIgnoreCase(PROPERTY_ATTRIBUTE_VALUE_LIST)
                && !propertyName.equalsIgnoreCase(PROPERTY_DEPENDENCY_LIST)
                && !propertyName.equalsIgnoreCase(PROPERTY_SKIP)
                && !propertyName.equalsIgnoreCase(PROPERTY_EXECUTION_TYPE)) {
            super.layoutElement(parent, widgetFactory, datatype, masterBlockId, property, data, readOnly,
                    externalViewModel, messageManager);
            return null;
        }

        // Validate parent Type
        if (!(datatype instanceof TestConfigElement)) {
            return null;
        }

        TestConfigElement configElement = (TestConfigElement) datatype;
        SchemaElement schemaElement = configElement.getSchemaElement();

        if (schemaElement == null) {
            return null;
        }

        if (propertyName.equalsIgnoreCase(PROPERTY_SKIP)) {

            if (schemaElement.getSkipable() != null
                    && schemaElement.getSkipable().getValue() != null && schemaElement.getSkipable().getValue()) {
                return super.layoutElement(parent, widgetFactory, datatype, masterBlockId, property, data, false,
                        externalViewModel, messageManager);
            } else {
                return super.layoutElement(parent, widgetFactory, datatype, masterBlockId, property, data, true,
                        externalViewModel, messageManager);
            }
        }

        if (propertyName.equalsIgnoreCase(PROPERTY_SCRIPT_LIST)) {

            if (schemaElement.getScriptsAllowed() == null) {
                return null;
            }

            switch (schemaElement.getScriptsAllowed()) {
            case ONE:
            case MANY:
                return this.layoutSciptTable(parent, widgetFactory, datatype, masterBlockId, property, data, readOnly,
                        externalViewModel, messageManager);
            }
        } else if (propertyName.equalsIgnoreCase(PROPERTY_DEPENDENCY_LIST)) {

            if (schemaElement.getHasDependencies().getValue() && !schemaElement.getDefaultDependency().getValue()) {
                return this.layoutDependencyTable(parent, widgetFactory, datatype, masterBlockId, property, data,
                        readOnly, externalViewModel, messageManager);
            } else {
                return null;
            }
        } else if (propertyName.equalsIgnoreCase(PROPERTY_ATTRIBUTE_VALUE_LIST)) {
            return this.layoutAttribute(parent, widgetFactory, datatype, masterBlockId, property, propertyName, data,
                    readOnly, externalViewModel, messageManager);
        } else if (propertyName.equalsIgnoreCase(PROPERTY_EXECUTION_TYPE)) {
            return this.layoutExecutionType(parent, widgetFactory, datatype, masterBlockId, property, data, readOnly,
                    externalViewModel, messageManager);
        }
        return null;
    }

    /**
     * Layouts the {@link AttributeValue} list.
     * 
     * @param parent
     *            the parent widget
     * @param widgetFactory
     *            the widget factory
     * @param datatype
     *            the datatype
     * @param masterBlockId
     *            the id
     * @param property
     *            the property instance
     * @param propertyName
     *            the property name
     * @param data
     *            the grid data
     * @param readOnly
     *            the read onyl flag
     * @param externalViewModel
     *            the external view model
     * @param messageManager
     *            the message manager
     * 
     * @return the layouted table
     */
    private Control layoutAttribute(Composite parent, BaseTypeWidgetFactory widgetFactory, Datatype datatype,
            String masterBlockId, Object property, String propertyName, GridData data, boolean readOnly,
            ViewModel externalViewModel, NabuccoMessageManager messageManager) {

        Control result = null;
        TestConfigElement configElement = (TestConfigElement) datatype;
        List<AttributeValue> attributeValueList = configElement.getAttributeValueList();
        for (AttributeValue attributeValue : attributeValueList) {

            if (attributeValue.getAttribute() != null && attributeValue.getAttribute().getName() != null) {
                Map<String, Serializable> labelMap = new HashMap<String, Serializable>();
                labelMap.put("name", attributeValue.getAttribute().getName().getValue());
                Label label = widgetFactory.createLabel(parent, masterBlockId + "." + propertyName, labelMap);
                label.setToolTipText(label.getText());
                label.setLayoutData(data);
    
                NabuccoFormToolkit nft = widgetFactory.getNabuccoFormToolKit();
    
                AttributeValueWidgetCreator widgetCreator = new AttributeValueWidgetCreator(nft);
                Control newWidget = widgetCreator.createWidget(parent, configElement, attributeValue, null, attributeValue,
                        readOnly, externalViewModel, messageManager, propertyName);
    
                if (newWidget == null) {
                    Activator.getDefault().logError("Cannot create AttributeValue Widget [null].");
                    newWidget = nft.createRealLabel(parent, "INVALID");
                } else {
                    super.setDataForWidget(data, newWidget);
                }
                result = newWidget;
            }
        }
        return result;
    }

    /**
     * Layouts the {@link AttributeValue} list.
     * 
     * @param parent
     *            the parent widget
     * @param widgetFactory
     *            the widget factory
     * @param datatype
     *            the datatype
     * @param masterBlockId
     *            the id
     * @param property
     *            the property instance
     * @param propertyName
     *            the property name
     * @param data
     *            the grid data
     * @param readOnly
     *            the read onyl flag
     * @param externalViewModel
     *            the external view model
     * @param messageManager
     *            the message manager
     * 
     * @return the layouted table
     */
    private Control layoutExecutionType(Composite parent, BaseTypeWidgetFactory widgetFactory, Datatype datatype,
            String masterBlockId, NabuccoProperty property, GridData data, boolean readOnly,
            ViewModel externalViewModel, NabuccoMessageManager messageManager) {

        String propertyName = property.getName();
        Control result = null;
        TestConfigElement configElement = (TestConfigElement) datatype;

        Label label = widgetFactory.createLabel(parent, masterBlockId + "." + propertyName);
        label.setToolTipText(label.getText());
        label.setLayoutData(data);

        NabuccoFormToolkit nft = widgetFactory.getNabuccoFormToolKit();

        String firstChar = propertyName.substring(0, 1).toUpperCase();
        String lastPart = propertyName.substring(1);

        if (property.getInstance() == null) {
            property = property.createProperty(this.initializeBasetype(datatype, firstChar, lastPart));
        }

        String setterName = PREFIX_SETTER + firstChar + lastPart;
        Method setter;
        try {
            setter = datatype.getClass().getMethod(setterName, new Class[] { property.getType() });

            if (configElement.getSchemaElement() != null
                    && configElement.getSchemaElement().getManualExecutionAllowed() != null
                    && configElement.getSchemaElement().getManualExecutionAllowed().getValue() != null
                    && configElement.getSchemaElement().getManualExecutionAllowed().getValue()) {
                readOnly = false;
            } else {
                readOnly = true;
            }

            EnumerationWidgetCreator widgetCreator = new EnumerationWidgetCreator(nft);
            Control newWidget = widgetCreator.createViewInputElement(parent, property, setter, configElement, readOnly,
                    externalViewModel, messageManager, propertyName);

            if (newWidget == null) {
                Activator.getDefault().logError("Cannot create AttributeValue Widget [null].");
                newWidget = nft.createRealLabel(parent, "INVALID");
            } else {
                super.setDataForWidget(data, newWidget);
            }
            result = newWidget;
        } catch (SecurityException e) {
            Activator.getDefault().logError(e);
        } catch (NoSuchMethodException e) {
            Activator.getDefault().logError(e);
        }
        return result;
    }

    /**
     * Layouts the {@link TestScript} table.
     * 
     * @param parent
     *            the parent widget
     * @param widgetFactory
     *            the widget factory
     * @param datatype
     *            the datatype
     * @param masterBlockId
     *            the id
     * @param property
     *            the property instance
     * @param propertyName
     *            the property name
     * @param data
     *            the grid data
     * @param readOnly
     *            the read onyl flag
     * @param externalViewModel
     *            the external view model
     * @param messageManager
     *            the message manager
     * 
     * @return the layouted table
     */
    private Control layoutSciptTable(Composite parent, BaseTypeWidgetFactory widgetFactory, Datatype datatype,
            String masterBlockId, NabuccoProperty property, GridData data, boolean readOnly,
            ViewModel externalViewModel, NabuccoMessageManager messageManager) {

        readOnly = !property.getConstraints().isEditable() || readOnly;

        String propertyName = property.getName();
        String labelId = masterBlockId + "." + propertyName;

        Label label = widgetFactory.createLabel(parent, labelId);
        label.setToolTipText(label.getText());
        label.setLayoutData(data);

        NabuccoFormToolkit nft = widgetFactory.getNabuccoFormToolKit();

        TestScriptTableWidgetCreator widgetCreator = new TestScriptTableWidgetCreator(nft);
        Control newWidget = widgetCreator.createViewInputElement(parent, property, null, datatype, readOnly,
                externalViewModel, messageManager, propertyName);

        if (newWidget == null) {
            Activator.getDefault().logError("Cannot create TestScriptPicker Widget [null].");
            newWidget = nft.createRealLabel(parent, "INVALID");
        } else {
            super.setDataForWidget(data, newWidget);
        }

        return newWidget;
    }

    /**
     * Layouts the dependency table.
     * 
     * @param parent
     *            the parent widget
     * @param widgetFactory
     *            the widget factory
     * @param datatype
     *            the datatype
     * @param masterBlockId
     *            the id
     * @param property
     *            the property instance
     * @param propertyName
     *            the property name
     * @param data
     *            the grid data
     * @param readOnly
     *            the read onyl flag
     * @param externalViewModel
     *            the external view model
     * @param messageManager
     *            the message manager
     * 
     * @return the layouted table
     */
    private Control layoutDependencyTable(Composite parent, BaseTypeWidgetFactory widgetFactory, Datatype datatype,
            String masterBlockId, NabuccoProperty property, GridData data, boolean readOnly,
            ViewModel externalViewModel, NabuccoMessageManager messageManager) {

        String propertyName = property.getName();
        String labelId = masterBlockId + "." + propertyName;

        Label label = widgetFactory.createLabel(parent, labelId);
        label.setToolTipText(label.getText());
        label.setLayoutData(data);

        NabuccoFormToolkit nft = widgetFactory.getNabuccoFormToolKit();

        DependencyTableWidgetCreator widgetCreator = new DependencyTableWidgetCreator(nft);
        Control newWidget = widgetCreator.createViewInputElement(parent, property, null, datatype, readOnly,
                externalViewModel, messageManager, propertyName);

        if (newWidget == null) {
            Activator.getDefault().logError("Cannot create DependencyPicker Widget [null].");
            newWidget = nft.createRealLabel(parent, "INVALID");
        } else {
            super.setDataForWidget(data, newWidget);
        }

        return newWidget;
    }

}
