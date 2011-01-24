/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.search.config.view;

import org.eclipse.swt.widgets.Composite;
import org.nabucco.framework.plugin.base.view.AbstractNabuccoSearchView;
import org.nabucco.framework.plugin.base.view.NabuccoMessageManager;
import org.nabucco.framework.plugin.base.view.NabuccoSearchView;
import org.nabucco.testautomation.config.ui.rcp.search.config.model.TestConfigElementSearchViewModel;

/**
 * TestConfigElementSearchView<p/>Search view for datatype TestConfigElement<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2010-09-27
 */
public class TestConfigElementSearchView extends
        AbstractNabuccoSearchView<TestConfigElementSearchViewModel> implements NabuccoSearchView {

    private TestConfigElementSearchViewModel model;

    public static final String ID = "org.nabucco.testautomation.config.ui.search.config.TestConfigElementSearchView";

    /** Constructs a new TestConfigElementSearchView instance. */
    public TestConfigElementSearchView() {
        super();
        model = new TestConfigElementSearchViewModel(this.getCorrespondingListView());
    }

    @Override
    public void createPartControl(final Composite parent,
            final NabuccoMessageManager aMessageManager) {
        this.getLayouter().layout(parent, aMessageManager, model);
    }

    @Override
    public TestConfigElementSearchViewModel getModel() {
        return model;
    }

    @Override
    public String getId() {
        return TestConfigElementSearchView.ID;
    }
}
