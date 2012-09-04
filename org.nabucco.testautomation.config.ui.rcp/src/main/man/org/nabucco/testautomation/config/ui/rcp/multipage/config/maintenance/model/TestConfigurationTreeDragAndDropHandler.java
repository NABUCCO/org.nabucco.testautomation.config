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

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.draganddrop.AbstractDragAndDropHandler;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.ui.rcp.multipage.metadata.model.PropertyDragAndDropHandler;
import org.nabucco.testautomation.property.ui.rcp.util.LoggingUtility;


public class TestConfigurationTreeDragAndDropHandler extends AbstractDragAndDropHandler {
    
    private static enum AllowedDataType {
        TestCofiguration, TestConfigElement, PropertyList, Property
    }
    
    private static AllowedDataType checkDataType(Datatype datatype) {
        if(datatype instanceof TestConfiguration) {
            return AllowedDataType.TestCofiguration;
        }
        if(datatype instanceof TestConfigElement) {
            return AllowedDataType.TestConfigElement;
        }
        if(datatype instanceof PropertyList) {
            return AllowedDataType.PropertyList;
        }
        if(datatype instanceof Property) {
            return AllowedDataType.Property;
        }
        // Hierher sollte das Programm nie kommen
        return null;
    }
    
    private static AllowedDataType checkDataType(MasterDetailTreeNode node) {
        return checkDataType(node.getDatatype());
    }
    
    @Override
    public boolean validateDrop(MasterDetailTreeNode data, MasterDetailTreeNode target, int location) {
        if(!super.validateDrop(data, target, location)) {
            return false;
        }
        switch(checkDataType(data)) {
            case TestCofiguration:
                return false;
            case TestConfigElement:
                return new TestConfigElementDragAndDropHandler().validateDrop(data, target, location);
            case PropertyList:
                return new TestConfigurationTreePropertyListDragAndDropHandler().validateDrop(data, target, location);
            case Property:
                return new PropertyDragAndDropHandler().validateDrop(data, target, location);
        }
        
        return false;
    }
    
    @Override
    public boolean postDrop(MasterDetailTreeNode data, MasterDetailTreeNode target, int location) {
        LoggingUtility.logDrop(data, target, location);
        switch(checkDataType(data)) {
            case TestCofiguration:
                return false;
            case TestConfigElement:
                new TestConfigElementDragAndDropHandler().postDrop(data, target, location);
                break;
            case PropertyList:
                new TestConfigurationTreePropertyListDragAndDropHandler().postDrop(data, target, location);
                break;
            case Property:
                new PropertyDragAndDropHandler().postDrop(data, target, location);
                break;
        }
        return this.performUiDrop(data, target, location);
    }
    
    @Override
    public boolean performUiDrop(MasterDetailTreeNode data, MasterDetailTreeNode target, int location) {
        switch(checkDataType(data)) {
            case TestCofiguration:
                return false;
            case TestConfigElement:
                return new TestConfigElementDragAndDropHandler().performUiDrop(data, target, location);
            case PropertyList:
                return new TestConfigurationTreePropertyListDragAndDropHandler().performUiDrop(data, target, location);
            case Property:
                return new PropertyDragAndDropHandler().performUiDrop(data, target, location);
        }
        
        // Diese Stelle sollte nie erreicht werden
        return false;
    }
    
    @Override
    public boolean postExternalDatatypeDrop(Datatype data, MasterDetailTreeNode targetNode, int location) {
        return false;
    }
    
    @Override
    public boolean validateExternalDrop(MasterDetailTreeNode target, int location) {
        return false;
    }
    
    @Override
    public void dragFinished(MasterDetailTreeNode movedNode) {}
    
}
