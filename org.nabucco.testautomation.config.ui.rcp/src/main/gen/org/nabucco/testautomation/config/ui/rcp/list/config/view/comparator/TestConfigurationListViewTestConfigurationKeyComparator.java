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
package org.nabucco.testautomation.config.ui.rcp.list.config.view.comparator;

import org.nabucco.framework.plugin.base.component.list.view.NabuccoColumComparator;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

/**
 * TestConfigurationListViewTestConfigurationKeyComparator
 *
 * @author Undefined
 */
public class TestConfigurationListViewTestConfigurationKeyComparator extends NabuccoColumComparator<TestConfiguration> {

    /** Constructs a new TestConfigurationListViewTestConfigurationKeyComparator instance. */
    public TestConfigurationListViewTestConfigurationKeyComparator() {
        super();
    }

    @Override
    public int compareConcrete(TestConfiguration object1, TestConfiguration object2) {
        return this.compareBasetype(object1.getIdentificationKey(), object2.getIdentificationKey());
    }
}
