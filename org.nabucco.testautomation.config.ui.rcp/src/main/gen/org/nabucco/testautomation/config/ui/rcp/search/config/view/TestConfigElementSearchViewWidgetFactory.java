/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
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
import org.nabucco.testautomation.config.ui.rcp.search.config.model.TestConfigElementSearchViewModel;

/**
 * TestConfigElementSearchViewWidgetFactory<p/>Search view for datatype TestConfigElement<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2010-09-27
 */
public class TestConfigElementSearchViewWidgetFactory extends WidgetFactory {

    private TestConfigElementSearchViewModel model;

    public static final String LABEL_TESTCONFIGELEMENTNNAME = "testConfigElement.name";

    public static final String OBSERVE_VALUE_TESTCONFIGELEMENTNNAME = TestConfigElementSearchViewModel.PROPERTY_TESTCONFIGELEMENT_NAME;

    public static final String LABEL_TESTCONFIGELEMENTDESCRIPTION = "testConfigElement.description";

    public static final String OBSERVE_VALUE_TESTCONFIGELEMENTDESCRIPTION = TestConfigElementSearchViewModel.PROPERTY_TESTCONFIGELEMENT_DESCRIPTION;

    /**
     * Constructs a new TestConfigElementSearchViewWidgetFactory instance.
     *
     * @param aModel the TestConfigElementSearchViewModel.
     * @param nabuccoFormToolKit the NabuccoFormToolkit.
     */
    public TestConfigElementSearchViewWidgetFactory(final NabuccoFormToolkit nabuccoFormToolKit,
            final TestConfigElementSearchViewModel aModel) {
        super(nabuccoFormToolKit);
        model = aModel;
    }

    /**
     * CreateLabelTestConfigElementnName.
     *
     * @param parent the Composite.
     * @return the Label.
     */
    public Label createLabelTestConfigElementnName(Composite parent) {
        return nabuccoFormToolKit.createRealLabel(parent, LABEL_TESTCONFIGELEMENTNNAME);
    }

    /**
     * CreateInputFieldTestConfigElementnName.
     *
     * @param parent the Composite.
     * @return the Text.
     */
    public Text createInputFieldTestConfigElementnName(Composite parent) {
        Text result = nabuccoFormToolKit.createTextInput(parent);
        DataBindingContext bindingContext = new DataBindingContext();
        IObservableValue uiElement = SWTObservables.observeText(result, SWT.Modify);
        IObservableValue modelElement = BeansObservables.observeValue(model, OBSERVE_VALUE_TESTCONFIGELEMENTNNAME);
        bindingContext.bindValue(uiElement, modelElement, null, null);
        return result;
    }

    /**
     * CreateLabelTestConfigElementDescription.
     *
     * @param parent the Composite.
     * @return the Label.
     */
    public Label createLabelTestConfigElementDescription(Composite parent) {
        return nabuccoFormToolKit.createRealLabel(parent, LABEL_TESTCONFIGELEMENTDESCRIPTION);
    }

    /**
     * CreateInputFieldTestConfigElementDescription.
     *
     * @param parent the Composite.
     * @return the Text.
     */
    public Text createInputFieldTestConfigElementDescription(Composite parent) {
        Text result = nabuccoFormToolKit.createTextInput(parent);
        DataBindingContext bindingContext = new DataBindingContext();
        IObservableValue uiElement = SWTObservables.observeText(result, SWT.Modify);
        IObservableValue modelElement = BeansObservables
                .observeValue(model, OBSERVE_VALUE_TESTCONFIGELEMENTDESCRIPTION);
        bindingContext.bindValue(uiElement, modelElement, null, null);
        return result;
    }
}
