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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.attributevalue;

import java.lang.reflect.Method;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.detail.widget.NStringWidgetCreator;
import org.nabucco.framework.plugin.base.model.ViewModel;
import org.nabucco.framework.plugin.base.view.NabuccoFormToolkit;
import org.nabucco.framework.plugin.base.view.NabuccoMessageManager;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.config.facade.datatype.attribute.StringAttributeValue;

import org.nabucco.testautomation.facade.datatype.base.StringValue;

/**
 * AttributeValueWidgetCreator
 * 
 * @authors Markus Jorroch, PRODYNA AG
 */
public class AttributeValueWidgetCreator {

    private NabuccoFormToolkit nft;

    public AttributeValueWidgetCreator(NabuccoFormToolkit nft) {
        this.nft = nft;
    }

    public Control createWidget(Composite parent, final TestConfigElement configElement, AttributeValue attributeValue, Method method,
            Object object, boolean readOnly, ViewModel externalViewModel,
            NabuccoMessageManager messageManager, String propertyName) {

        if (attributeValue instanceof StringAttributeValue) {
            StringAttributeValue stringAttributeValue = (StringAttributeValue) attributeValue;
            StringValue value = stringAttributeValue.getValue();
            // initialize value
            if (value == null) {
                value = new StringValue("");
                stringAttributeValue.setValue(value);
            }
            // call base WidgetCreatorForNString
            NStringWidgetCreator widgetCreator = new NStringWidgetCreator(nft);
            Control newWidget = widgetCreator.createViewInputElement(parent, value, method,
                    attributeValue, readOnly, externalViewModel, messageManager, propertyName);
            Text text = (Text) newWidget;
            text.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					if(configElement.getDatatypeState() == DatatypeState.PERSISTENT){
						configElement.setDatatypeState(DatatypeState.MODIFIED);
					}
				}
			});
            return newWidget;
        }
        return null;
    }

}
