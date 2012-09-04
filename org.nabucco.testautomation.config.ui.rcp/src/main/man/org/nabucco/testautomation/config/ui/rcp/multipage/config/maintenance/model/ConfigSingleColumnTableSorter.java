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

import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableSorter;

/**
 * ConfigSingleColumnTableSorter
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class ConfigSingleColumnTableSorter extends NabuccoTableSorter {

    @Override
    protected int compare(Object e1, Object e2) {

        TestConfigurationMaintenanceMultiPageAddDialogNameLabelProvider provider = new TestConfigurationMaintenanceMultiPageAddDialogNameLabelProvider();

        String text1 = provider.getText(e1);
        String text2 = provider.getText(e2);

        if (text1 == null) {
            if (text2 == null) {
                return 0;
            }
            return -1;
        }
        if (text2 == null) {
            return 1;
        }

        return text1.compareToIgnoreCase(text2);
    }

}
