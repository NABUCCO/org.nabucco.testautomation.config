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
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyComposite;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyContainer;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyType;
import org.nabucco.testautomation.property.facade.message.ProducePropertyMsg;
import org.nabucco.testautomation.property.ui.rcp.communication.PropertyComponentServiceDelegateFactory;
import org.nabucco.testautomation.property.ui.rcp.multipage.metadata.model.PropertyCompositeDragAndDropHandler;
import org.nabucco.testautomation.property.ui.rcp.multipage.metadata.model.PropertyDragAndDropHandler;


public class TestConfigurationTreePropertyListDragAndDropHandler extends PropertyCompositeDragAndDropHandler {
    
    @Override
    public boolean validateDrop(MasterDetailTreeNode data, MasterDetailTreeNode target, int location) {
        MasterDetailTreeNode parent = null;
        // Einf�gen als Geschwisterelement von target
        switch(location) {
            case DropBefore:
                parent = target.getParent();
                if(!DataModelManager.canBeChildOf(parent.getDatatype(), data.getDatatype())) {
                    return false;
                }
                // Target has to be first element if parent is no PropertyComposite
                if((!(parent.getDatatype() instanceof PropertyComposite)) && parent.getChildren().get(0) != target) {
                    return false;
                }
                return true;

            case DropAfter:
                parent = target.getParent();
                if(!DataModelManager.canBeChildOf(parent.getDatatype(), data.getDatatype())) {
                    return false;
                }
                // data has to be first element unless parent is a PropertyComposite
                if(!(parent.getDatatype() instanceof PropertyComposite)) {
                    return false;
                }
                return true;
            // Einf�gen als Kind von target 
            case DropOnto:
                parent = target;
                if(!DataModelManager.canBeChildOf(parent.getDatatype(), data.getDatatype())) {
                    return false;
                }
                return true;
        }
        
        // Hierher sollte das Programm nie kommen
        return false;
    }
    
    @Override
    public boolean postDrop(MasterDetailTreeNode data, MasterDetailTreeNode target, int location) {
        MasterDetailTreeNode oldParent = data.getParent();
        Datatype oldParentDatatype = oldParent.getDatatype();
        int oldIndex = oldParent.getChildren().indexOf(data);
        PropertyContainer container = null;
        if(oldParentDatatype instanceof PropertyComposite) {
            container = PropertyDragAndDropHandler.removeProperty((PropertyComposite)oldParentDatatype, (Property)data.getDatatype());
        } else if(oldParentDatatype instanceof TestConfigElement) {
            TestConfigElement oldParentTestConfigElement = (TestConfigElement) oldParentDatatype;
            oldParentTestConfigElement.setPropertyList(null);
            if (oldParentDatatype.getDatatypeState() == DatatypeState.PERSISTENT) {
                oldParentDatatype.setDatatypeState(DatatypeState.MODIFIED);
            }
        }
        
        MasterDetailTreeNode newParent = null;
        switch (location) {
            case DropBefore:
            case DropAfter:
                newParent = target.getParent();
                break;
            case DropOnto:
                newParent = target;
                break;
        }
        Datatype newParentDatatype = newParent.getDatatype();

        if(newParentDatatype instanceof TestConfigElement) {
            if(container != null) {
                container.setProperty((Property)null);
                container.setDatatypeState(DatatypeState.DELETED);
            }
            TestConfigElement newParentTestConfigElement = (TestConfigElement) newParentDatatype;
            newParentTestConfigElement.setPropertyList((PropertyList) data.getDatatype());
            if (newParentDatatype.getDatatypeState() == DatatypeState.PERSISTENT) {
                newParentDatatype.setDatatypeState(DatatypeState.MODIFIED);
            }
        } else if(newParentDatatype instanceof PropertyComposite) {
            if(container == null) {
                try {
                    ProducePropertyMsg rq = new ProducePropertyMsg();
                    rq.setPropertyType(PropertyType.LIST);
                    container = PropertyComponentServiceDelegateFactory.getInstance().getProduceProperty().produceProperty(rq).getPropertyContainer();
                } catch (ClientException e) {
                    Activator.getDefault().logError("Cannot produce PropertyContainer");
                }
                container.setProperty((Property) data.getDatatype());
            }
            insertion(container, oldIndex, oldParentDatatype, target, location);
        }
        
        // return value is useless at the moment
        return false;
    }
    
    
}
