/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.list.config.view.comparator;

import org.nabucco.framework.plugin.base.component.list.view.NabuccoColumComparator;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

/**
 * TestConfigurationListViewTestConfigurationNameComparator
 *
 * @author Undefined
 */
public class TestConfigurationListViewTestConfigurationNameComparator extends
        NabuccoColumComparator<TestConfiguration> {

    /** Constructs a new TestConfigurationListViewTestConfigurationNameComparator instance. */
    public TestConfigurationListViewTestConfigurationNameComparator() {
        super();
    }

    @Override
    public int compareConcrete(TestConfiguration object1, TestConfiguration object2) {
        return this.compareBasetype(object1.getName(), object2.getName());
    }
}
