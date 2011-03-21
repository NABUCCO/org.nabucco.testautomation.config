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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.contextmenu;

import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.picker.dialog.AbstractElementPickerContentProvider;
import org.nabucco.framework.plugin.base.model.NabuccoModel;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.TestConfigurationElementFactory;
import org.nabucco.testautomation.facade.datatype.property.base.Property;

/**
 * PropertyListSelectElementPickerContentProvider
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class PropertyListSelectElementPickerContentProvider extends AbstractElementPickerContentProvider {

    public PropertyListSelectElementPickerContentProvider() {
    }

    @Override
    public Object[] getElements(Object arg0) {
        Property[] allExistingPropertyLists = TestConfigurationElementFactory.getAllExistingPropertyLists(Activator.getDefault().getModel().getSecurityModel().getSubject().getOwner());
		return allExistingPropertyLists;
    }

}
