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
package org.nabucco.testautomation.config.impl.service.produce;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.facade.service.search.SearchTestConfigElement;
import org.nabucco.testautomation.config.impl.service.produce.clone.TestConfigElementCloneVisitor;


/**
 * ProduceTestConfigElementCloneServiceHandlerImpl
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public class ProduceTestConfigElementCloneServiceHandlerImpl extends
        ProduceTestConfigElementCloneServiceHandler {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected TestConfigElementMsg produceTestConfigElementClone(final TestConfigElementMsg msg)
            throws ProduceException {

    	TestConfigElement element = msg.getTestConfigElement();
    	
    	if (element == null) {
    		throw new ProduceException("No TestConfigElement to clone");
    	}
    	
    	if (element.getId() == null) {
    		element = copy(element);
    	} else {
    		element = clone(element);
    	}

    	// Create Container
        TestConfigElementContainer container = new TestConfigElementContainer();
		container.setDatatypeState(DatatypeState.INITIALIZED);
		container.setElement(element);
    	
		msg.setTestConfigElement(element);
		msg.setTestConfigElementContainer(container);
        return msg;
    }
    
    private TestConfigElement copy(TestConfigElement element) throws ProduceException {
    	
    	// Clone
        TestConfigElementCloneVisitor cloneVisitor = new TestConfigElementCloneVisitor();
        TestConfigElement clonedObject = element.cloneObject();
        try {
			clonedObject.accept(cloneVisitor);
		} catch (VisitorException ex) {
			throw new ProduceException(ex);
		}
        return clonedObject;
    }
    
    private TestConfigElement clone(TestConfigElement element) throws ProduceException {
    	try {
    		// Load TestConfigElement deep
    		SearchTestConfigElement search = ConfigComponentLocator.getInstance().getComponent().getSearchTestConfigElement();
    		ServiceRequest<TestConfigElementSearchMsg> rq = new ServiceRequest<TestConfigElementSearchMsg>(getContext());
    		TestConfigElementSearchMsg message = new TestConfigElementSearchMsg();
    		message.setId(new Identifier(element.getId()));
			rq.setRequestMessage(message);
			element = search.getTestConfigElement(rq).getResponseMessage().getTestConfigElement();
    		
			// Clone
            TestConfigElementCloneVisitor cloneVisitor = new TestConfigElementCloneVisitor();
            TestConfigElement clonedObject = element.cloneObject();
            clonedObject.setReused(Boolean.FALSE);
            clonedObject.accept(cloneVisitor);
            return clonedObject;
        } catch (VisitorException ex) {
            throw new ProduceException(ex);
        } catch (ServiceException ex) {
        	throw new ProduceException(ex);
		} catch (ConnectionException ex) {
			throw new ProduceException(ex);
		}
    }

}
