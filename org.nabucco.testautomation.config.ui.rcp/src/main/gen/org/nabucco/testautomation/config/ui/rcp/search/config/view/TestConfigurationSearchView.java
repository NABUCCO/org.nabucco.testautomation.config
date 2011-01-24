/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.search.config.view;

import org.eclipse.swt.widgets.Composite;
import org.nabucco.framework.plugin.base.view.AbstractNabuccoSearchView;
import org.nabucco.framework.plugin.base.view.NabuccoMessageManager;
import org.nabucco.framework.plugin.base.view.NabuccoSearchView;
import org.nabucco.testautomation.config.ui.rcp.search.config.model.TestConfigurationSearchViewModel;

/**
 * TestConfigurationSearchView<p/>Search view for datatype TestConfiguration<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class TestConfigurationSearchView extends
        AbstractNabuccoSearchView<TestConfigurationSearchViewModel> implements NabuccoSearchView {

    private TestConfigurationSearchViewModel model;

    public static final String ID = "org.nabucco.testautomation.config.ui.search.config.TestConfigSearchView";

    /** Constructs a new TestConfigurationSearchView instance. */
    public TestConfigurationSearchView() {
        super();
        model = new TestConfigurationSearchViewModel(this.getCorrespondingListView());
    }

    @Override
    public void createPartControl(final Composite parent,
            final NabuccoMessageManager aMessageManager) {
        this.getLayouter().layout(parent, aMessageManager, model);
    }

    @Override
    public TestConfigurationSearchViewModel getModel() {
        return model;
    }

    @Override
    public String getId() {
        return TestConfigurationSearchView.ID;
    }
}
