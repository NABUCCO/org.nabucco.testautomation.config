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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.contextmenu;

import java.lang.ref.SoftReference;

import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.picker.dialog.AbstractElementPickerContentProvider;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationElementFactory;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * ConfigElementSelectElementPickerContentProvider
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConfigElementSelectElementPickerContentProvider extends AbstractElementPickerContentProvider {

    private SoftReference<TestConfigElement[]> cache;

    private SchemaElement schemaElement;

    /**
     * Creates a new {@link ConfigElementSelectElementPickerContentProvider} instance.
     * 
     * @param schemaElement
     *            the schema element to load the test configs for
     */
    public ConfigElementSelectElementPickerContentProvider(SchemaElement schemaElement) {
        this.schemaElement = schemaElement;
    }

    @Override
    public Object[] getElements(Object arg0) {
        if (this.cache == null || this.cache.get() == null) {
            this.loadCache();
        }

        return this.cache.get();
    }

    /**
     * Load Test Configurations and add to the cache.
     */
    private void loadCache() {
        Owner owner = Activator.getDefault().getModel().getSecurityModel().getSubject().getOwner();
        TestConfigElement[] configs = TestConfigurationElementFactory.getExistingConfigElements(this.schemaElement,
                owner);

        this.cache = new SoftReference<TestConfigElement[]>(configs);
    }

}
