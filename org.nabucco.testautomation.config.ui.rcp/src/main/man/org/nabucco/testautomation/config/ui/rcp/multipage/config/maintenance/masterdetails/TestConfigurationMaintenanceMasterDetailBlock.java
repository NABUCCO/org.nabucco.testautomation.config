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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.ui.forms.DetailsPart;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailBlock;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailBlockLayouter;
import org.nabucco.framework.plugin.base.layout.Layoutable;
import org.nabucco.framework.plugin.base.view.NabuccoMessageManager;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.TestConfigurationMaintenanceMultiPageEditView;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.detail.TestConfigDetailPageView;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.detail.fileproperty.FilePropertyDetailPageView;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationMaintenanceMultiPageEditViewModel;
import org.nabucco.testautomation.property.facade.datatype.BooleanProperty;
import org.nabucco.testautomation.property.facade.datatype.DateProperty;
import org.nabucco.testautomation.property.facade.datatype.FileProperty;
import org.nabucco.testautomation.property.facade.datatype.NumericProperty;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.TextProperty;
import org.nabucco.testautomation.property.facade.datatype.XmlProperty;
import org.nabucco.testautomation.property.ui.rcp.multipage.detail.PropertyDetailPageView;

/**
 * TestConfigurationMaintenanceMasterDetailBlock
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationMaintenanceMasterDetailBlock extends
        MasterDetailBlock<TestConfigurationMaintenanceMultiPageEditViewModel> implements
        Layoutable<TestConfigurationMaintenanceMultiPageEditViewModel> {

    public static String ID = "org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.TestConfigurationMaintenanceMasterDetailBlock";

    /**
     * Creates a new {@link TestConfigurationMaintenanceMasterDetailBlock} instance.
     * 
     * @param model
     *            the view model
     * @param messageManager
     *            the message manager
     * @param view
     *            the view
     */
    public TestConfigurationMaintenanceMasterDetailBlock(
            TestConfigurationMaintenanceMultiPageEditViewModel model,
            NabuccoMessageManager messageManager,
            TestConfigurationMaintenanceMultiPageEditView view) {
        super(messageManager, view, model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void registerPages(DetailsPart arg0) {
    	Map<Class<? extends Datatype>, Set<String>> typeToInvisiblePropertiesMap = this.getTypeToInvisiblePropertiesMap();
        Set<String> invisibleProperties;
        Set<String> readOnlyProperties;

        /*
         * TestConfiguration
         */
        invisibleProperties = new HashSet<String>();
        readOnlyProperties = new HashSet<String>();
        readOnlyProperties.add("owner");
        readOnlyProperties.add("identificationKey");
    	invisibleProperties.add("id");
    	invisibleProperties.add("version");
        invisibleProperties.add("schemaConfigRefId");
        invisibleProperties.add("environmentTypeRefId");
        invisibleProperties.add("releaseTypeRefId");
        invisibleProperties.add("reused");

        detailsPart.registerPage(TestConfiguration.class,
                new PropertyDetailPageView<TestConfigurationMaintenanceMultiPageEditViewModel>(this, getManagedForm(),
                        getManagedFormViewPart(), nabuccoMessageManager, ID, ID
                                + "TestConfiguration", invisibleProperties, readOnlyProperties));
		typeToInvisiblePropertiesMap.put(TestConfiguration.class, invisibleProperties);
        
        /*
         * TestConfigElements
         */
        detailsPart.registerPage(TestConfigElement.class, new TestConfigDetailPageView(this,
                getManagedForm(), getManagedFormViewPart(), nabuccoMessageManager, ID, ID
                        + "TestConfigElement", invisibleProperties, readOnlyProperties));
        typeToInvisiblePropertiesMap.put(TestConfigElement.class, invisibleProperties);

        /*
         * Properties
         */
        readOnlyProperties = new HashSet<String>();
        readOnlyProperties.add("owner");
        readOnlyProperties.add("type");
        readOnlyProperties.add("identificationKey");
        invisibleProperties = new HashSet<String>();
        invisibleProperties.add("reference");
    	invisibleProperties.add("id");
    	invisibleProperties.add("version");
    	invisibleProperties.add("filename");
        
        detailsPart.registerPage(TextProperty.class, new PropertyDetailPageView<TestConfigurationMaintenanceMultiPageEditViewModel>(
                this, getManagedForm(), getManagedFormViewPart(), nabuccoMessageManager, ID, ID
                        + "TextProperty", invisibleProperties, readOnlyProperties));
        typeToInvisiblePropertiesMap.put(TextProperty.class, invisibleProperties);
        
        detailsPart.registerPage(BooleanProperty.class,
                new PropertyDetailPageView<TestConfigurationMaintenanceMultiPageEditViewModel>(this, getManagedForm(),
                        getManagedFormViewPart(), nabuccoMessageManager, ID,
                        ID + "BooleanProperty", invisibleProperties, readOnlyProperties));
        typeToInvisiblePropertiesMap.put(BooleanProperty.class, invisibleProperties);
        
        detailsPart.registerPage(DateProperty.class, new PropertyDetailPageView<TestConfigurationMaintenanceMultiPageEditViewModel>(
                this, getManagedForm(), getManagedFormViewPart(), nabuccoMessageManager, ID, ID
                        + "DateProperty", invisibleProperties, readOnlyProperties));
        typeToInvisiblePropertiesMap.put(DateProperty.class, invisibleProperties);
        
        detailsPart.registerPage(NumericProperty.class,
                new PropertyDetailPageView<TestConfigurationMaintenanceMultiPageEditViewModel>(this, getManagedForm(),
                        getManagedFormViewPart(), nabuccoMessageManager, ID,
                        ID + "NumericProperty", invisibleProperties, readOnlyProperties));
        typeToInvisiblePropertiesMap.put(NumericProperty.class, invisibleProperties);
        
        detailsPart.registerPage(XmlProperty.class, new PropertyDetailPageView<TestConfigurationMaintenanceMultiPageEditViewModel>(
                this, getManagedForm(), getManagedFormViewPart(), nabuccoMessageManager, ID, ID
                        + "XmlProperty", invisibleProperties, readOnlyProperties));
        typeToInvisiblePropertiesMap.put(XmlProperty.class, invisibleProperties);
        
        detailsPart.registerPage(FileProperty.class, new FilePropertyDetailPageView(this,
                getManagedForm(), getManagedFormViewPart(), nabuccoMessageManager, ID, ID
                + "FileProperty", invisibleProperties, readOnlyProperties));
        typeToInvisiblePropertiesMap.put(FileProperty.class, invisibleProperties);
        
        invisibleProperties.add("reused");
        invisibleProperties.add("usageType");
        detailsPart.registerPage(PropertyList.class, new PropertyDetailPageView<TestConfigurationMaintenanceMultiPageEditViewModel>(
                this, getManagedForm(), getManagedFormViewPart(), nabuccoMessageManager, ID, ID
                        + "Property", invisibleProperties, readOnlyProperties));
        typeToInvisiblePropertiesMap.put(PropertyList.class, invisibleProperties);
    }

    @Override
    protected MasterDetailBlockLayouter<TestConfigurationMaintenanceMultiPageEditViewModel> getLayouter() {
        return new TestConfigurationMaintenanceMasterDetailBlockLayouter();
    }

}
