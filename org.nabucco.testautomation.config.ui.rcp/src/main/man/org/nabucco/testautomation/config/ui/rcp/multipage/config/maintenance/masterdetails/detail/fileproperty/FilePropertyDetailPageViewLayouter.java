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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.detail.fileproperty;

import java.util.List;
import java.util.Set;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.detail.widget.BaseTypeWidgetFactory;
import org.nabucco.framework.plugin.base.model.ViewModel;
import org.nabucco.framework.plugin.base.view.NabuccoFormToolkit;
import org.nabucco.framework.plugin.base.view.NabuccoMessageManager;
import org.nabucco.testautomation.property.facade.datatype.FileProperty;
import org.nabucco.testautomation.property.ui.rcp.multipage.detail.PropertyDetailPageViewLayouter;
import org.nabucco.testautomation.script.ui.rcp.multipage.metadata.masterdetail.widgetcreators.fileproperty.NameContentCombinationWidgetCreator;

/**
 * FilePropertyDetailPageViewLayouter
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class FilePropertyDetailPageViewLayouter extends PropertyDetailPageViewLayouter {

    private static final String PROPERTY_NAME = "name";

    private static final String PROPERTY_CONTENT = "content";

    /**
     * Creates a new {@link FilePropertyDetailPageViewLayouter} instance.
     * 
     * @param title
     *            the detail view title
     */
    public FilePropertyDetailPageViewLayouter(String title) {
        super(title);
    }

    @Override
    public Composite layout(Composite parent, Datatype datatype, String masterBlockId, Set<String> invisibleProperties,
            Set<String> readOnlyProperties, ViewModel externalViewModel, NabuccoMessageManager messageManager) {

        if (parent == null || datatype == null) {
            return null;
        }

        NabuccoFormToolkit nabuccoFormToolkit = new NabuccoFormToolkit(parent);
        super.addWidgetCreator(new NameContentCombinationWidgetCreator(nabuccoFormToolkit));

        return super.layout(parent, datatype, masterBlockId, invisibleProperties, readOnlyProperties,
                externalViewModel, messageManager);
    }

    @Override
    protected Control layoutElement(Composite parent, BaseTypeWidgetFactory widgetFactory, Datatype datatype,
            String masterBlockId, NabuccoProperty property, GridData data, boolean readOnly,
            ViewModel externalViewModel, NabuccoMessageManager messageManager) {

        Object instance = property.getInstance();

        // Validate property multiplicity
        if ((instance instanceof List<?>)) {
            return super.layoutElement(parent, widgetFactory, datatype, masterBlockId, property, data, readOnly,
                    externalViewModel, messageManager);
        }

        // Validate parent Type
        if (!(datatype instanceof FileProperty)) {
            return super.layoutElement(parent, widgetFactory, datatype, masterBlockId, property, data, readOnly,
                    externalViewModel, messageManager);
        }

        // Validate property name
        String propertyName = property.getName();

        if (propertyName.equalsIgnoreCase(PROPERTY_CONTENT)) {
            return null;
        }

        if (propertyName.equalsIgnoreCase(PROPERTY_NAME)) {
            return this.layoutFileChooser(parent, widgetFactory, datatype, masterBlockId, property, data, readOnly,
                    externalViewModel, messageManager);
        }

        return super.layoutElement(parent, widgetFactory, datatype, masterBlockId, property, data, readOnly,
                externalViewModel, messageManager);
    }

    private Control layoutFileChooser(Composite parent, BaseTypeWidgetFactory widgetFactory, Datatype datatype,
            String masterBlockId, NabuccoProperty property, GridData data, boolean readOnly,
            ViewModel externalViewModel, NabuccoMessageManager messageManager) {

        readOnly = !property.getConstraints().isEditable() || readOnly;

        FileProperty fileProperty = (FileProperty) datatype;
        NabuccoFormToolkit nft = widgetFactory.getNabuccoFormToolKit();
        NameContentCombinationWidgetCreator widgetCreator = new NameContentCombinationWidgetCreator(nft);
        try {
            Control[] newWidget = widgetCreator.createWidget(parent, data, fileProperty, externalViewModel, widgetFactory,
                    masterBlockId, readOnly);

            if (newWidget.length > 0 && newWidget[0] == null) {
                Activator.getDefault().logError("Cannot create FileProperty Widget [null].");
                newWidget[0] = nft.createRealLabel(parent, "INVALID");
            } else {
                for (Control control : newWidget) {
                    super.setDataForWidget(data, control);
                }
            }
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
        }
        return null;
    }
}
