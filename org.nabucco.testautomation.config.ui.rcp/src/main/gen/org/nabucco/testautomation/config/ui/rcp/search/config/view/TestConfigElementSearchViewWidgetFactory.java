/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
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
        IObservableValue modelElement = BeansObservables.observeValue(model,
                OBSERVE_VALUE_TESTCONFIGELEMENTNNAME);
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
        IObservableValue modelElement = BeansObservables.observeValue(model,
                OBSERVE_VALUE_TESTCONFIGELEMENTDESCRIPTION);
        bindingContext.bindValue(uiElement, modelElement, null, null);
        return result;
    }
}
