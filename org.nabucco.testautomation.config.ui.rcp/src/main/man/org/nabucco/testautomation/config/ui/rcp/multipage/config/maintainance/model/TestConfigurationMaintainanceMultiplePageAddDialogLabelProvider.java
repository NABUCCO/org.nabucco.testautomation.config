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

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.addDialog.AddDialogLabelProvider;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;

import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;

/**
 * TestConfigurationMaintainanceMultiplePageAddDialogLabelProvider
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationMaintainanceMultiplePageAddDialogLabelProvider extends
        AddDialogLabelProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(final Object obj) {
        String result = null;
        if (obj instanceof TestConfigElementContainer) {
            TestConfigElement element = ((TestConfigElementContainer) obj).getElement();
            DatatypeState datatypeState = element.getDatatypeState();
            if (datatypeState != DatatypeState.INITIALIZED) {
                String name = element.getName().getValue();
                String description = "";
    			if(element.getDescription() != null && element.getDescription().getValue() != null && element.getDescription().getValue().length() > 0){
    				description = " - " + element.getDescription().getValue();
    			}
                result = name + description;
            } else {
                result = element.getSchemaElement().getName().getValue();
            }
        } else if (obj instanceof PropertyContainer) {
            Property property = ((PropertyContainer) obj).getProperty();
            DatatypeState datatypeState = property.getDatatypeState();
            if (datatypeState != DatatypeState.INITIALIZED) {
            	String name = property.getName().getValue();
                String description = "";
    			if(property.getDescription() != null && property.getDescription().getValue() != null && property.getDescription().getValue().length() > 0){
    				description = " - " + property.getDescription().getValue();
    			}
                result = name + description;
            } else {
                result = I18N.i18n(property.getClass().getCanonicalName());
            }
        } else if (obj instanceof TestConfigElement) {
            TestConfigElement element = (TestConfigElement) obj;
            DatatypeState datatypeState = element.getDatatypeState();
            if (datatypeState != DatatypeState.INITIALIZED) {
                String name = element.getName().getValue();
                String description = "";
    			if(element.getDescription() != null && element.getDescription().getValue() != null && element.getDescription().getValue().length() > 0){
    				description = " - " + element.getDescription().getValue();
    			}
                result = name + description;
            } else {
                result = element.getSchemaElement().getName().getValue();
            }
        } else if (obj instanceof Property) {
            Property property = ((Property) obj);
            DatatypeState datatypeState = property.getDatatypeState();
            if (datatypeState != DatatypeState.INITIALIZED) {
            	String name = property.getName().getValue();
                String description = "";
    			if(property.getDescription() != null && property.getDescription().getValue() != null && property.getDescription().getValue().length() > 0){
    				description = " - " + property.getDescription().getValue();
    			}
                result = name + description;
            } else {
                result = I18N.i18n(property.getClass().getCanonicalName());
            }
        }
        if (result == null) {
            result = I18N.i18n(obj.getClass().getCanonicalName());
        }
        return result;
    }
    

}
