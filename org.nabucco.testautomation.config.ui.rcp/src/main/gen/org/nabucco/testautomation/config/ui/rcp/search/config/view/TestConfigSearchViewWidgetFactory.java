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
package org.nabucco.testautomation.config.ui.rcp.search.config.view;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.nabucco.framework.plugin.base.layout.WidgetFactory;
import org.nabucco.framework.plugin.base.view.NabuccoFormToolkit;
import org.nabucco.testautomation.config.ui.rcp.search.config.model.TestConfigSearchViewModel;


/**
 * TestConfigSearchViewWidgetFactory<p/>Search view for datatype TestConfiguration<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class TestConfigSearchViewWidgetFactory extends WidgetFactory {

    private TestConfigSearchViewModel model;

    public static final String LABEL_TESTCONFIGURATIONNAME = "testConfiguration.name";

    public static final String OBSERVE_VALUE_TESTCONFIGURATIONNAME = TestConfigSearchViewModel.PROPERTY_TESTCONFIGURATION_NAME;

    public static final String LABEL_TESTCONFIGURATIONDESCRIPTION = "testConfiguration.description";

    public static final String OBSERVE_VALUE_TESTCONFIGURATIONDESCRIPTION = TestConfigSearchViewModel.PROPERTY_TESTCONFIGURATION_DESCRIPTION;

    /**
     * Constructs a new TestConfigSearchViewWidgetFactory instance.
     *
     * @param aModel the TestConfigSearchViewModel.
     * @param nabuccoFormToolKit the NabuccoFormToolkit.
     */
    public TestConfigSearchViewWidgetFactory(final NabuccoFormToolkit nabuccoFormToolKit,
            final TestConfigSearchViewModel aModel) {
        super(nabuccoFormToolKit);
        model = aModel;
    }

    /**
     * CreateLabelTestConfigurationName.
     *
     * @param parent the Composite.
     * @return the Label.
     */
    public Label createLabelTestConfigurationName(Composite parent) {
        return nabuccoFormToolKit.createRealLabel(parent, LABEL_TESTCONFIGURATIONNAME);
    }

    /**
     * CreateInputFieldTestConfigurationName.
     *
     * @param parent the Composite.
     * @return the Text.
     */
    public Text createInputFieldTestConfigurationName(Composite parent) {
        Text result = nabuccoFormToolKit.createTextInput(parent);
        DataBindingContext bindingContext = new DataBindingContext();
        IObservableValue uiElement = SWTObservables.observeText(result, SWT.Modify);
        IObservableValue modelElement = BeansObservables.observeValue(model,
                OBSERVE_VALUE_TESTCONFIGURATIONNAME);
        bindingContext.bindValue(uiElement, modelElement, null, null);
        return result;
    }

    /**
     * CreateLabelTestConfigurationDescription.
     *
     * @param parent the Composite.
     * @return the Label.
     */
    public Label createLabelTestConfigurationDescription(Composite parent) {
        return nabuccoFormToolKit.createRealLabel(parent, LABEL_TESTCONFIGURATIONDESCRIPTION);
    }

    /**
     * CreateInputFieldTestConfigurationDescription.
     *
     * @param parent the Composite.
     * @return the Text.
     */
    public Text createInputFieldTestConfigurationDescription(Composite parent) {
        Text result = nabuccoFormToolKit.createTextInput(parent);
        DataBindingContext bindingContext = new DataBindingContext();
        IObservableValue uiElement = SWTObservables.observeText(result, SWT.Modify);
        IObservableValue modelElement = BeansObservables.observeValue(model,
                OBSERVE_VALUE_TESTCONFIGURATIONDESCRIPTION);
        bindingContext.bindValue(uiElement, modelElement, null, null);
        return result;
    }
}
