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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.addDialog.AddDialogLabelProvider;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;

/**
 * TestConfigurationMaintenanceMultiplePageSelectCloneKeyColumnDialogLabelProvider
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationMaintenanceMultiplePageSelectCloneKeyColumnDialogLabelProvider extends
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
                String name = element.getIdentificationKey().getValue();
                result = name;
            } else {
                result = "";
            }
        } else if (obj instanceof TestConfigElement) {
            TestConfigElement element = (TestConfigElement) obj;
            DatatypeState datatypeState = element.getDatatypeState();
            if (datatypeState != DatatypeState.INITIALIZED) {
                String name = element.getIdentificationKey().getValue();
                result = name;
            } else {
                result = "";
            }
        } 
        if (result == null) {
            result = I18N.i18n(obj.getClass().getCanonicalName());
        }
        return result;
    }
    

}
