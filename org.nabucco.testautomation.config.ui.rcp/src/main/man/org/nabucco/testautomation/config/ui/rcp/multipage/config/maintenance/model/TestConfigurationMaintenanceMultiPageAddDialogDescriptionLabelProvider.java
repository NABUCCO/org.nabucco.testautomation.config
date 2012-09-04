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

import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.addDialog.AddDialogLabelProvider;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyContainer;

/**
 * TestConfigurationMaintenanceMultiPageAddDialogDescriptionLabelProvider
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class TestConfigurationMaintenanceMultiPageAddDialogDescriptionLabelProvider extends AddDialogLabelProvider {

    @Override
    public String getText(final Object obj) {
        if (obj instanceof TestConfigElementContainer) {
            TestConfigElement element = ((TestConfigElementContainer) obj).getElement();
            return getValue(element.getDescription());
        } else if (obj instanceof PropertyContainer) {
            Property property = ((PropertyContainer) obj).getProperty();
            return this.getValue(property.getDescription());
        } else if (obj instanceof TestConfigElement) {
            TestConfigElement element = (TestConfigElement) obj;
            return this.getValue(element.getDescription());
        } else if (obj instanceof Property) {
            Property property = ((Property) obj);
            return this.getValue(property.getDescription());
        }
        return I18N.i18n(obj.getClass().getCanonicalName());
    }

    private String getValue(Description element) {
        if (element == null || element.getValue() == null) {
            return "";
        }
        return element.getValue();
    }

}
