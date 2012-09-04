/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.testautomation.config.ui.rcp.search.config.model;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchModel;
import org.nabucco.framework.plugin.base.component.search.model.NabuccoComponentSearchParameter;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.ui.rcp.browser.config.TestConfigurationListViewBrowserElement;
import org.nabucco.testautomation.config.ui.rcp.communication.ConfigComponentServiceDelegateFactory;
import org.nabucco.testautomation.config.ui.rcp.communication.search.SearchConfigDelegate;

/**
 * Does the search for TestConfig.
 * 
 * @author Stefan Huettenrauch, PRODYNA AG
 * 
 */
public class TestConfigurationSearchBusinessModel implements NabuccoComponentSearchModel {

    public static final String ID = "org.nabucco.testautomation.config.ui.search.config.model.TestConfigurationSearchBusinessModel";

    /**
     * {@inheritDoc}
     */
    @Override
    public TestConfigurationListViewBrowserElement search(NabuccoComponentSearchParameter parameter) {

        TestConfigurationListViewBrowserElement result = null;

        if (parameter instanceof TestConfigurationSearchViewModel) {
            TestConfigurationSearchViewModel testConfigSearchViewModel = (TestConfigurationSearchViewModel) parameter;
            TestConfigurationSearchMsg rq = createTestConfigSearchMsg(testConfigSearchViewModel);
            result = new TestConfigurationListViewBrowserElement(search(rq).toArray(new TestConfiguration[0]));
        }
        return result;
    }

    private List<TestConfiguration> search(final TestConfigurationSearchMsg rq) {

        List<TestConfiguration> result = null;
        try {
            SearchConfigDelegate searchDelegate = ConfigComponentServiceDelegateFactory.getInstance().getSearchConfig();
            TestConfigurationListMsg response = searchDelegate.searchTestConfiguration(rq);

            result = response.getTestConfigList();
        } catch (ClientException e) {
            Activator.getDefault().logError(e);
        }
        return result;
    }

    private TestConfigurationSearchMsg createTestConfigSearchMsg(TestConfigurationSearchViewModel searchViewModel) {

        TestConfigurationSearchMsg result = new TestConfigurationSearchMsg();
        String owner = searchViewModel.getOwner();

        if (owner != null && owner.length() > 0) {
            result.setOwner(new Owner(owner));
        }
        result.setName(getName(searchViewModel));
        result.setTestConfigKey(getKey(searchViewModel));

        return result;
    }

    private Key getKey(final TestConfigurationSearchViewModel searchViewModel) {

        Key result = new Key();
        String key = searchViewModel.getTestConfigurationIdentificationKey();

        result.setValue((key == null || key.length() == 0) ? null : key);
        return result;
    }

    private Name getName(TestConfigurationSearchViewModel searchViewModel) {

        Name result = new Name();
        String name = searchViewModel.getTestConfigurationName();

        result.setValue((name == null || name.length() == 0) ? null : name);
        return result;
    }

}
