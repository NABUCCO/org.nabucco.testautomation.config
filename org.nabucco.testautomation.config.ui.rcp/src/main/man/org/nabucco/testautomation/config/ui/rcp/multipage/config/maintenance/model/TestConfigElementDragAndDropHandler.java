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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.MasterDetailTreeNode;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.master.draganddrop.AbstractDragAndDropHandler;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;


public class TestConfigElementDragAndDropHandler extends AbstractDragAndDropHandler {
    
    @Override
    public boolean validateDrop(MasterDetailTreeNode data, MasterDetailTreeNode target, int location) {
        MasterDetailTreeNode parent = null;
        // Einf�gen als Geschwisterelement von target
        switch(location) {
            case DropBefore:
                parent = target.getParent();
                int dropIndex = parent.getChildren().indexOf(target);
                if(!DataModelManager.canBeChildOf(parent.getDatatype(), data.getDatatype())) {
                    return false;
                }
                // 
                if(dropIndex == 0 && target.getDatatype() instanceof PropertyList) {
                    return false;
                }
                return true;

            case DropAfter:
                parent = target.getParent();
                if(!DataModelManager.canBeChildOf(parent.getDatatype(), data.getDatatype())) {
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
        // Setzen des alten Elternelements auf modified
        // Setzen der alten Folgegeschwisterelemente auf modified
        Datatype oldParentDatatype = data.getParent().getDatatype();
        int oldIndex = data.getParent().getChildren().indexOf(data);
        TestConfigElementContainer container = null;
        if(oldParentDatatype instanceof TestConfiguration) {
            container = removeProperty((TestConfiguration)oldParentDatatype, (TestConfigElement)data.getDatatype());
        } else if (oldParentDatatype instanceof TestConfigElement) {
            container = removeProperty((TestConfigElement)oldParentDatatype, (TestConfigElement)data.getDatatype());
        }

        insertion(container, oldIndex, oldParentDatatype, target, location);
        
        // return value is useless at the moment
        return false;
    }
    
    public static TestConfigElementContainer removeProperty(TestConfiguration pc, TestConfigElement prop) {
        List<TestConfigElementContainer> list = pc.getTestConfigElementList();
        TestConfigElementContainer propcont = null;
        for(int i = 0; i < list.size(); i++) {
            TestConfigElementContainer temp = list.get(i);
            if(propcont == null) {
                if(temp.getElement() == prop) {
                    propcont = temp;
                }
            } else {
                temp.setOrderIndex(temp.getOrderIndex().getValue() - 1);
                if (temp.getDatatypeState() == DatatypeState.PERSISTENT) {
                    temp.setDatatypeState(DatatypeState.MODIFIED);
                }
            }
        }
        if(propcont != null) {
            list.remove(propcont);
            if(pc.getDatatypeState() == DatatypeState.PERSISTENT) {
                pc.setDatatypeState(DatatypeState.MODIFIED);
            }
        }
        return propcont;
    }
    
    
    public static TestConfigElementContainer removeProperty(TestConfigElement pc, TestConfigElement prop) {
        List<TestConfigElementContainer> list = pc.getTestConfigElementList();
        TestConfigElementContainer propcont = null;
        for(int i = 0; i < list.size(); i++) {
            TestConfigElementContainer temp = list.get(i);
            if(propcont == null) {
                if(temp.getElement() == prop) {
                    propcont = temp;
                }
            } else {
                temp.setOrderIndex(temp.getOrderIndex().getValue() - 1);
                if (temp.getDatatypeState() == DatatypeState.PERSISTENT) {
                    temp.setDatatypeState(DatatypeState.MODIFIED);
                }
            }
        }
        if(propcont != null) {
            list.remove(propcont);
            if(pc.getDatatypeState() == DatatypeState.PERSISTENT) {
                pc.setDatatypeState(DatatypeState.MODIFIED);
            }
        }
        return propcont;
    }
    
    protected static void insertion(TestConfigElementContainer container, int oldIndex, Datatype oldParentDatatype, MasterDetailTreeNode target, int location) {
        MasterDetailTreeNode newParent = null;
        Datatype newParentDatatype = null;
        int newIndex = 0;
        switch (location) {
            case DropBefore:
                newParent = target.getParent();
                newParentDatatype = newParent.getDatatype();
                newIndex = newParent.getChildren().indexOf(target);
                break;
            case DropAfter:
                newParent = target.getParent();
                newParentDatatype = newParent.getDatatype();
                newIndex = newParent.getChildren().indexOf(target) + 1;
                break;
            case DropOnto:
                newParent = target;
                newParentDatatype = newParent.getDatatype();
                newIndex = newParent.getChildren().size();
                break;
        }
        
        // Wenn parent eine PropertyList enth�lt, muss vom index 1 abgezogen werden
        if(newParent.getChildren().size() > 0 && newParent.getChildren().get(0).getDatatype() instanceof PropertyList) {
            newIndex--;
        }
        
        // Wenn es in der gleichen Liste nach unten verschoben wird muss der neue Einf�gepunkt eins nach oben verschoben werden,
        // da durch das entfernen alle folgenden Elemente eins nach oben rutschen.
        if(newParentDatatype == oldParentDatatype && newIndex > oldIndex) {
            newIndex--;
        }
        // Setzen des neuen Elternelements auf modified
        // Setzen der alten Folgegeschwisterelemente auf modified
        if(newParentDatatype instanceof TestConfiguration) {
            insertProperty((TestConfiguration)newParentDatatype, container, newIndex);
        } else if(newParentDatatype instanceof TestConfigElement) {
            insertProperty((TestConfigElement)newParentDatatype, container, newIndex);
        }
    }
    
    public static void insertProperty(TestConfiguration pc, TestConfigElementContainer container, int index) {
        List<TestConfigElementContainer> list = pc.getTestConfigElementList();
        list.add(index, container);
        for(int i = index; i < list.size(); i++) {
            TestConfigElementContainer cont = list.get(i);
            cont.setOrderIndex(i);
            if (cont.getDatatypeState() == DatatypeState.PERSISTENT) {
                cont.setDatatypeState(DatatypeState.MODIFIED);
            }
        }
        if(pc.getDatatypeState() == DatatypeState.PERSISTENT) {
            pc.setDatatypeState(DatatypeState.MODIFIED);
        }
    }
    
    public static void insertProperty(TestConfigElement pc, TestConfigElementContainer container, int index) {
        List<TestConfigElementContainer> list = pc.getTestConfigElementList();
        list.add(index, container);
        for(int i = index; i < list.size(); i++) {
            TestConfigElementContainer cont = list.get(i);
            cont.setOrderIndex(i);
            if (cont.getDatatypeState() == DatatypeState.PERSISTENT) {
                cont.setDatatypeState(DatatypeState.MODIFIED);
            }
        }
        if(pc.getDatatypeState() == DatatypeState.PERSISTENT) {
            pc.setDatatypeState(DatatypeState.MODIFIED);
        }
    }
}
