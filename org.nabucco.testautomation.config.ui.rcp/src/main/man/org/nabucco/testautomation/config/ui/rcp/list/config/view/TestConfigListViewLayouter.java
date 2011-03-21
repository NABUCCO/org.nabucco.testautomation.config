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
package org.nabucco.testautomation.config.ui.rcp.list.config.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoAbstractListLayouter;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoDefaultListContentProvider;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoDefaultTableSorter;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableColumnInfo;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableParameter;
import org.nabucco.framework.plugin.base.component.list.view.NabuccoTableViewer;
import org.nabucco.framework.plugin.base.layout.Layoutable;
import org.nabucco.framework.plugin.base.view.NabuccoFormToolkit;
import org.nabucco.framework.plugin.base.view.NabuccoMessageManager;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.list.config.model.TestConfigListViewModel;
import org.nabucco.testautomation.config.ui.rcp.list.config.view.TestConfigListViewTableFilter;
import org.nabucco.testautomation.config.ui.rcp.list.config.view.TestConfigListViewWidgetFactory;
import org.nabucco.testautomation.config.ui.rcp.list.config.view.comparator.TestConfigurationListViewOwnerComparator;
import org.nabucco.testautomation.config.ui.rcp.list.config.view.comparator.TestConfigurationListViewTestConfigurationDescriptionComparator;
import org.nabucco.testautomation.config.ui.rcp.list.config.view.comparator.TestConfigurationListViewTestConfigurationNameComparator;
import org.nabucco.testautomation.config.ui.rcp.list.config.view.label.TestConfigurationListViewOwnerLabelProvider;
import org.nabucco.testautomation.config.ui.rcp.list.config.view.label.TestConfigurationListViewTestConfigurationDescriptionLabelProvider;
import org.nabucco.testautomation.config.ui.rcp.list.config.view.label.TestConfigurationListViewTestConfigurationKeyLabelProvider;
import org.nabucco.testautomation.config.ui.rcp.list.config.view.label.TestConfigurationListViewTestConfigurationNameLabelProvider;


/**
 * @author Stefan Hüttenrauch, PRODYNA AG
 * 
 */
public class TestConfigListViewLayouter extends NabuccoAbstractListLayouter<TestConfigListViewModel> {

	private static final String OWNER_COLUMN_KEY = "org.nabucco.testautomation.config.ui.rcp.list.config.view.owner";
	
	private static final String DESCRIPTION_COLUMN_KEY = "org.nabucco.testautomation.config.ui.rcp.list.config.view.description";
	
	private static final String NAME_COLUMN_KEY = "org.nabucco.testautomation.config.ui.rcp.list.config.view.name";
	
	private static final String KEY_COLUMN_KEY = "org.nabucco.testautomation.config.ui.rcp.list.config.view.key";

	/**
     * Layouts the table
     **/
    @Override
    public NabuccoTableViewer layout(final Composite parent,
            final NabuccoMessageManager messageManager, final TestConfigListViewModel model,
            final Layoutable<TestConfigListViewModel> view) {

        NabuccoFormToolkit ntk = new NabuccoFormToolkit(parent);

        TestConfigListViewWidgetFactory widgetFactory = new TestConfigListViewWidgetFactory(ntk);

        NabuccoTableParameter parameter = new NabuccoTableParameter(
                new NabuccoDefaultTableSorter<TestConfiguration>(createComparators()),
                new TestConfigListViewTableFilter(), new NabuccoDefaultListContentProvider(model),
                createTableColumnInfo(), getDoubleClickCommand(view.getManagedFormViewPart()));

        return widgetFactory.createTable(parent, parameter, model);
    }

    private List<Comparator<TestConfiguration>> createComparators() {
        List<Comparator<TestConfiguration>> comparators = new ArrayList<Comparator<TestConfiguration>>();
        comparators.add(new TestConfigurationListViewTestConfigurationNameComparator());
        comparators.add(new TestConfigurationListViewTestConfigurationDescriptionComparator());
        comparators.add(new TestConfigurationListViewOwnerComparator());

        return comparators;
    }

    /**
     * Creates needed tables.
     * 
     * @return table columns
     */
    private NabuccoTableColumnInfo[] createTableColumnInfo() {
        NabuccoTableColumnInfo[] result = {
        		new NabuccoTableColumnInfo(KEY_COLUMN_KEY,
        				KEY_COLUMN_KEY, 150, SWT.LEFT,
        				SWT.CENTER,
        				new TestConfigurationListViewTestConfigurationKeyLabelProvider()),
                new NabuccoTableColumnInfo(NAME_COLUMN_KEY,
                		NAME_COLUMN_KEY, 400, SWT.LEFT,
                        SWT.CENTER,
                        new TestConfigurationListViewTestConfigurationNameLabelProvider()),
                new NabuccoTableColumnInfo(DESCRIPTION_COLUMN_KEY,
                		DESCRIPTION_COLUMN_KEY, 400,
                        SWT.LEFT, SWT.LEFT,
                        new TestConfigurationListViewTestConfigurationDescriptionLabelProvider()),
                new NabuccoTableColumnInfo(OWNER_COLUMN_KEY,
                		OWNER_COLUMN_KEY, 150,
                        SWT.LEFT, SWT.LEFT,
                        new TestConfigurationListViewOwnerLabelProvider())};

        return result;
    }

}
