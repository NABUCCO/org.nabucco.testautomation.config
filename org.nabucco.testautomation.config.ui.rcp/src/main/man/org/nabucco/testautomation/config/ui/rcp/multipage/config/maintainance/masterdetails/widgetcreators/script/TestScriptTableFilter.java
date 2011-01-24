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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.script;

import org.eclipse.jface.viewers.Viewer;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableFilter;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;

import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;

/**
 * TestScriptTableFilter
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TestScriptTableFilter extends NabuccoTableFilter {

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean result = false;
        if (((null == searchFilter.getFilter()) || (0 == searchFilter.getFilter().length()))) {
            result = true;
        } else if ((element instanceof TestScript)) {
            TestScript script = (TestScript) element;
            result = (result || this.contains(script.getName(), searchFilter.getFilter()));
            result = (result || this.contains(script.getDescription(), searchFilter.getFilter()));
            result = (result || this.contains(script.getType(), searchFilter.getFilter()));
        } else if ((element instanceof TestScriptContainer)) {
            TestScriptContainer container = (TestScriptContainer) element;
            result = (result || this.contains(container.getTestScript().getName(),
                    searchFilter.getFilter()));
        }
        return result;
    }
}
