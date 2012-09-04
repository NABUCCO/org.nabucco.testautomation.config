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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model;

import java.io.Serializable;
import java.util.Map;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Menu;
import org.nabucco.framework.base.facade.component.injector.NabuccoInjectionReciever;
import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.multipage.model.MultiPageEditViewModel;
import org.nabucco.framework.plugin.base.component.multipage.xml.XMLEditorTextPart;
import org.nabucco.framework.plugin.base.component.picker.dialog.ElementPickerParameter;
import org.nabucco.framework.plugin.base.component.picker.dialog.LabelForDialog;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.property.ui.rcp.util.DatatypeUtility;
import org.nabucco.testautomation.property.ui.rcp.util.LoggingUtility;


/**
 * TestConfigurationMaintenanceMultiPageEditViewModel
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationMaintenanceMultiPageEditViewModel extends MultiPageEditViewModel
        implements NabuccoInjectionReciever {

    private MasterDetailTreeNode treeStructure;

    private XMLEditorTextPart xmlStructure;

    private TestConfigurationMaintenanceMultiPageEditViewModelHandler handler = NabuccoInjector
            .getInstance(TestConfigurationMaintenanceMultiPageEditViewModel.class).inject(
                    TestConfigurationMaintenanceMultiPageEditViewModelHandler.class);

    private TestConfiguration testConfiguration;
    
    static {
        LoggingUtility.addUtility(TestConfiguration.class, new DatatypeUtility() {
            
            @Override
            public String toString(Datatype data) {
                if(data instanceof TestConfiguration) {
                    TestConfiguration m = (TestConfiguration)data;
                    return m.getName().getValueAsString();
                }
                return null;
            }
        });
        LoggingUtility.addUtility(TestConfigElement.class, new DatatypeUtility() {
            
            @Override
            public String toString(Datatype data) {
                if(data instanceof TestConfigElement) {
                    TestConfigElement m = (TestConfigElement)data;
                    return m.getName().getValueAsString();
                }
                return null;
            }
        });
    }

    public TestConfigurationMaintenanceMultiPageEditViewModel(TestConfiguration testConfiguration) {
        this.testConfiguration = testConfiguration;
    }

    public TestConfigurationMaintenanceMultiPageEditViewModel() {
	}

	// <MASTER DETAIL METHODS>
    /**
     * {@inheritDoc}
     */
    @Override
    public MasterDetailTreeNode add(MasterDetailTreeNode parent, Datatype newChild) throws ClientException {
        MasterDetailTreeNode result = handler.addChild(parent, newChild);
        updateProperty(getPropertyDatatype(), "", "*");
        return result;
    }

    @Override
    public MasterDetailTreeNode getTreeStructure() {
        return treeStructure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MasterDetailTreeNode createNewAndAdd(final MasterDetailTreeNode parent,
            final Datatype newChild) throws ClientException {
        // observers will be already informed in the node is added (which is
        // relevant for view)

        MasterDetailTreeNode result = add(parent, newChild);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(ISelection child) throws ClientException {
        handler.remove(child);
        updateProperty(getPropertyDatatype(), "", "*");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestConfiguration getRoot() {
        return (TestConfiguration) treeStructure.getDatatype();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Datatype[]> getPossibleChildren(Datatype datatype) throws ClientException {
        return handler.getPossibleChildren(datatype);
    }

    @Override
    public boolean hasPossibleChildren(Datatype datatype) {
        return handler.hasPossibleChildren(datatype);
    }

    // </MASTER DETAIL METHODS>

    // <XML METHODS>
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean parseXML(String content) {
        return xmlStructure.parseXML(content);
    }

    @Override
    public String getXMLText() {
        return xmlStructure.getXML();
    }

    @Override
    public XMLEditorTextPart getXmlStructure() {
        return xmlStructure;
    }

    // </XML METHODS>

    /**
     * Setter.
     * 
     * @param testScript
     *            value
     */
    public void setTestConfiguration(final TestConfiguration testConfiguration) throws ClientException {
        this.testConfiguration = testConfiguration;
        treeStructure = handler.createMasterDetailRepresentation(this.testConfiguration);
        xmlStructure = handler.createXmlRepresentation(this.testConfiguration);

        updateProperty(getPropertyDatatype(), "", "*");

    }

    /**
     * Getter.
     * 
     * @return value
     */
    public TestConfiguration getTestConfiguration() {
        return this.testConfiguration;
    }

    public Map<String, Serializable> getValues() {
        Map<String, Serializable> result = super.getValues();
        result.put("name", testConfiguration.getName());
        return result;
    }

    @Override
    public ElementPickerParameter getElementPickerParameter(Datatype datatype) throws ClientException {
        return handler.getElementPickerParameter(datatype);
    }

    @Override
    public LabelForDialog getLabelForDialog() throws ClientException {
        return handler.getLabelForDialog();
    }

    @Override
    public boolean up(ISelection selection) {
        return handler.up(selection);
    }

    @Override
    public boolean down(ISelection selection) {
        return handler.down(selection);
    }
    
    @Override
    public boolean replace(ISelection selection) throws ClientException {
    	// TODO Auto-generated method stub
    	return handler.replace(selection);
    }

    @Override
    public Menu getContextMenu(ISelection selection, TreeViewer parent) throws ClientException {
        return handler.getContextMenu(selection, parent);
    }

}
