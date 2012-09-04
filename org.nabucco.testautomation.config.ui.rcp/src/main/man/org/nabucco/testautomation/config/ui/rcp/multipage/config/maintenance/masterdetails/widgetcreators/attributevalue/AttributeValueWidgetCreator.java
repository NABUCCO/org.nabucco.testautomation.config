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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.attributevalue;

import java.lang.reflect.Method;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.common.dynamiccode.ui.rcp.component.multipage.masterdetail.widgetcreators.code.CodeComboBoxHandler;
import org.nabucco.framework.common.dynamiccode.ui.rcp.component.multipage.masterdetail.widgetcreators.code.CodeContentProvider;
import org.nabucco.framework.common.dynamiccode.ui.rcp.component.multipage.masterdetail.widgetcreators.code.CodeLabelProvider;
import org.nabucco.framework.common.dynamiccode.ui.rcp.component.multipage.masterdetail.widgetcreators.code.CodeMiniModel;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.detail.widget.AbstractBaseTypeWidgetCreator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.detail.widget.NStringWidgetCreator;
import org.nabucco.framework.plugin.base.component.picker.combo.ElementPickerCombo;
import org.nabucco.framework.plugin.base.component.picker.combo.ElementPickerComboParameter;
import org.nabucco.framework.plugin.base.model.ViewModel;
import org.nabucco.framework.plugin.base.view.NabuccoFormToolkit;
import org.nabucco.framework.plugin.base.view.NabuccoMessageManager;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.config.facade.datatype.attribute.CodeAttributeValue;
import org.nabucco.testautomation.config.facade.datatype.attribute.TextAttributeValue;
import org.nabucco.testautomation.property.facade.datatype.base.Text;

/**
 * AttributeValueWidgetCreator
 * 
 * @authors Markus Jorroch, PRODYNA AG
 */
public class AttributeValueWidgetCreator extends AbstractBaseTypeWidgetCreator<Code> {

    public AttributeValueWidgetCreator(NabuccoFormToolkit nft) {
        super(nft);
    }

    public Control createWidget(Composite parent, final TestConfigElement configElement, AttributeValue attributeValue,
            Method method, Object object, boolean readOnly, ViewModel externalViewModel,
            NabuccoMessageManager messageManager, String propertyName) {

        if (attributeValue instanceof TextAttributeValue) {
            TextAttributeValue stringAttributeValue = (TextAttributeValue) attributeValue;
            Text value = stringAttributeValue.getValue();
            // initialize value
            if (value == null) {
                value = new Text("");
                stringAttributeValue.setValue(value);
            }
            // call base WidgetCreatorForNString
            NStringWidgetCreator widgetCreator = new NStringWidgetCreator(super.getFormToolkit());
            Control newWidget = widgetCreator.createViewInputElement(parent,
                    stringAttributeValue.getProperty(TextAttributeValue.VALUE), method, attributeValue, readOnly,
                    externalViewModel, messageManager, propertyName);
            org.eclipse.swt.widgets.Text text = (org.eclipse.swt.widgets.Text) newWidget;
            text.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(ModifyEvent arg0) {
                    if (configElement.getDatatypeState() == DatatypeState.PERSISTENT) {
                        configElement.setDatatypeState(DatatypeState.MODIFIED);
                    }
                }
            });
            return newWidget;
        } else if (attributeValue instanceof CodeAttributeValue) {
            CodeAttributeValue codeAttributeValue = (CodeAttributeValue) attributeValue;

            if (method == null) {
                try {
                    method = CodeAttributeValue.class.getMethod("setValue", Code.class);
                } catch (Exception e) {
                   Activator.getDefault().logError(e);
                }
            }
            Control newWidget = this.createWidget(parent, codeAttributeValue.getValue(), codeAttributeValue
                    .getAttribute().getCodePath(), method, attributeValue, readOnly, externalViewModel, messageManager,
                    propertyName);
            org.nabucco.framework.plugin.base.component.picker.combo.ElementPickerCombo combo = (org.nabucco.framework.plugin.base.component.picker.combo.ElementPickerCombo) newWidget;
            combo.addSelectionListener(new SelectionListener() {
                
                @Override
                public void widgetSelected(SelectionEvent arg0) {

                    if (configElement.getDatatypeState() == DatatypeState.PERSISTENT) {
                        configElement.setDatatypeState(DatatypeState.MODIFIED);
                    }
                }
                
                @Override
                public void widgetDefaultSelected(SelectionEvent arg0) {
                }
            });
            return newWidget;
        }
        return null;
    }

    protected Control createWidget(Composite parent, Code code, CodePath codePath, Method method, Object object,
            boolean readOnly, ViewModel externalViewModel, NabuccoMessageManager messageManager, String propertyName) {

        final CodeContentProvider codeContentProvider = new CodeContentProvider(codePath);

        final ElementPickerComboParameter params = new ElementPickerComboParameter(codeContentProvider,
                new CodeLabelProvider());

        // ElementPickerCombo elementCombo = new ElementPickerCombo(parent, params);
        ElementPickerCombo elementCombo = super.getFormToolkit().createElementPickerCombo(parent, params, readOnly,
                true);

        // combo.setLayoutData(gridData);

        final DataBindingContext bindingContext = new DataBindingContext();
        final IObservableValue uiElement = SWTObservables.observeSelection(elementCombo.getCombo());

        if (object instanceof Datatype) {

            final CodeMiniModel model = new CodeMiniModel(codeContentProvider, method, (Datatype) object,
                    externalViewModel, code);
            final IObservableValue modelElement = BeansObservables.observeValue(model, CodeMiniModel.PROPERTY_VALUE);
            bindingContext.bindValue(uiElement, modelElement, null, null);
            elementCombo.addSelectionListener(new CodeComboBoxHandler(model));
        }

        return elementCombo;
    }

    @Override
    protected Control createWidget(Composite arg0, Code arg1, Method arg2, Object arg3, boolean arg4, ViewModel arg5,
            NabuccoMessageManager arg6, String arg7) {
        // TODO Auto-generated method stub
        return null;
    }

}
