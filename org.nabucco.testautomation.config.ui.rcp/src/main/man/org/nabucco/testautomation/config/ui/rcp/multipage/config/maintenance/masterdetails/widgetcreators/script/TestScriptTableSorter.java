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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script;

import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableSorter;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;


/**
 * TestScriptTableSorter
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TestScriptTableSorter extends NabuccoTableSorter {

    @Override
    protected int compare(Object o1, Object o2) {

        if (o1 instanceof TestScriptContainer && o2 instanceof TestScriptContainer) {
            return compare((TestScriptContainer) o1, (TestScriptContainer) o2);
        }

        return 0;
    }

    /**
     * Compares two TestScriptContainer instances.
     * 
     * @param s1
     *            the first script container
     * @param s2
     *            the second script container
     * 
     * @return the compared value
     */
    private int compare(TestScriptContainer s1, TestScriptContainer s2) {
        if (s1.getOrderIndex() == null || s2.getOrderIndex() == null) {
            throw new IllegalArgumentException(
                    "Cannot compare TestScriptContainer with order [null].");
        }

        return s1.getOrderIndex().compareTo(s2.getOrderIndex());
    }

}
