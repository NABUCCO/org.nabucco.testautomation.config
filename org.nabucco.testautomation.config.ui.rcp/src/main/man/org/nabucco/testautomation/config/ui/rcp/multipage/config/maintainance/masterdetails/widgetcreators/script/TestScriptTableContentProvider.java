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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;


/**
 * TestScriptPickerContentProvider
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TestScriptTableContentProvider implements IStructuredContentProvider {

    private TestScriptTableMiniModel model;

    private List<TestScriptContainer> values = new ArrayList<TestScriptContainer>();

    /**
     * Creates a new {@link TestScriptTableContentProvider} instance.
     */
    public TestScriptTableContentProvider(TestScriptTableMiniModel model) {
        this.model = model;
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldValue, Object newValue) {
    }

    @Override
    public Object[] getElements(Object element) {
        this.initValues();

        if (element instanceof String) {
            return this.values.toArray(new Object[this.values.size()]);
        }

        return new Object[0];
    }

    @Override
    public void dispose() {
        this.values = null;
    }

    /**
     * Initialize the script this.values.
     */
    private void initValues() {
        this.values.clear();

        this.values.addAll(this.model.getTestScriptList());
    }

}
