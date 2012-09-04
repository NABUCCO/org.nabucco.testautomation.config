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
package org.nabucco.testautomation.config.impl.service.produce;

import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.impl.service.produce.clone.TestConfigElementCloneVisitor;
import org.nabucco.testautomation.config.impl.service.produce.clone.TestConfigurationImportVisitor;


/**
 * ProduceTestConfigurationCloneServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ProduceTestConfigurationCloneServiceHandlerImpl extends
        ProduceTestConfigurationCloneServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected TestConfigurationMsg produceTestConfigurationClone(final TestConfigurationMsg msg)
            throws ProduceException {

    	TestConfiguration testConfiguration = msg.getTestConfiguration();
    	
    	if (testConfiguration == null) {
    		throw new ProduceException("No TestConfiguration to clone");
    	}
    	
    	// Clone
    	TestConfigElementCloneVisitor cloneVisitor;
    	TestConfiguration clone = testConfiguration.cloneObject();
       
		if (msg.getImportConfig() != null
				&& msg.getImportConfig().getValue() != null
				&& msg.getImportConfig().getValue().booleanValue()) {
			cloneVisitor = new TestConfigurationImportVisitor();
		} else {
			cloneVisitor = new TestConfigElementCloneVisitor();
		}
    	
        try {
			clone.accept(cloneVisitor);
		} catch (VisitorException ex) {
			throw new ProduceException(ex);
		}
		msg.setTestConfiguration(clone);
        return msg;
    }
    
}
