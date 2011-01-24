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
import org.nabucco.testautomation.config.ui.rcp.search.config.model.TestConfigurationSearchViewModel;

/**
 * TestConfigurationSearchViewWidgetFactory<p/>Search view for datatype TestConfiguration<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class TestConfigurationSearchViewWidgetFactory extends WidgetFactory {

    private TestConfigurationSearchViewModel model;

    public static final String LABEL_TESTCONFIGURATIONNAME = "testConfiguration.name";

    public static final String OBSERVE_VALUE_TESTCONFIGURATIONNAME = TestConfigurationSearchViewModel.PROPERTY_TESTCONFIGURATION_NAME;

    public static final String LABEL_TESTCONFIGURATIONDESCRIPTION = "testConfiguration.description";

    public static final String OBSERVE_VALUE_TESTCONFIGURATIONDESCRIPTION = TestConfigurationSearchViewModel.PROPERTY_TESTCONFIGURATION_DESCRIPTION;

    /**
     * Constructs a new TestConfigurationSearchViewWidgetFactory instance.
     *
     * @param aModel the TestConfigurationSearchViewModel.
     * @param nabuccoFormToolKit the NabuccoFormToolkit.
     */
    public TestConfigurationSearchViewWidgetFactory(final NabuccoFormToolkit nabuccoFormToolKit,
            final TestConfigurationSearchViewModel aModel) {
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
