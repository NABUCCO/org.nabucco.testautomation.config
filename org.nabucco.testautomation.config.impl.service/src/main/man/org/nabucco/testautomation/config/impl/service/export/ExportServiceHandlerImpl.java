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
package org.nabucco.testautomation.config.impl.service.export;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.nabucco.framework.base.facade.component.NabuccoInstance;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.exporting.ExportContainer;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationResult;
import org.nabucco.framework.base.facade.datatype.serialization.xml.XmlSerializer;
import org.nabucco.framework.base.facade.exception.exporting.ExportException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.exporting.ExportRs;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

/**
 * ExportServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class ExportServiceHandlerImpl extends ExportServiceHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected ExportRs export(EmptyServiceMessage msg)
			throws ExportException {
		
		ExportRs response = new ExportRs();
		response.setContainer(new ExportContainer());
		XmlSerializer xmlSerializer = new XmlSerializer();
		List<Datatype> entityList = new ArrayList<Datatype>();
		
		long start = System.currentTimeMillis();
		getLogger().info("Loading TestConfigurations ...");
		
		Query query = this.getEntityManager().createQuery("FROM TestConfiguration c WHERE c.owner = :owner");
		query.setParameter("owner", NabuccoInstance.getInstance().getOwner());
		
		@SuppressWarnings("unchecked")
		List<TestConfiguration> resultList = query.getResultList();
		
		for (TestConfiguration config : resultList) {
			load(config);
		}
		getLogger().info(resultList.size() + " TestConfigurations loaded. Duration: " + (System.currentTimeMillis() - start) + " ms");
		
		start = System.currentTimeMillis();
		getLogger().info("Loading TestConfigElement ...");

		query = this.getEntityManager().createQuery(
						"FROM TestConfigElement e WHERE e.owner = :owner " +
						"AND e NOT IN (SELECT DISTINCT c.element FROM TestConfigElementContainer c)");
		query.setParameter("owner", NabuccoInstance.getInstance().getOwner());
		
		@SuppressWarnings("unchecked")
		List<TestConfigElement> elementList = query.getResultList();
		
		for (TestConfigElement element : elementList) {
			load(element);
		}
		getLogger().info(elementList.size() + " TestConfigElements loaded. Duration: " + (System.currentTimeMillis() - start) + " ms");
		
		entityList.addAll(elementList);
		entityList.addAll(resultList);
		
		try {
			start = System.currentTimeMillis();
			getLogger().info("Start serializing Config ...");
			SerializationResult serializationResult = xmlSerializer.serialize(entityList, XmlSerializer.DEFAULT_INDENT, true);
			response.getContainer().setResult(serializationResult.getContent());
			response.getContainer().setResourceData(serializationResult.getResourceContainer().toByteArray());
			getLogger().info("Serializing of Config finished. Duration: " + (System.currentTimeMillis() - start) + " ms");
		} catch (SerializationException ex) {
			throw new ExportException("Fatal error during serialization of Config", ex);
		}

        return response;
	}
	
	private void load(TestConfiguration testConfiguration) throws ExportException {
        
		for (TestConfigElementContainer child : testConfiguration.getTestConfigElementList()) {
			load(child.getElement());
		}
    }
	
	private void load(TestConfigElement element) throws ExportException {
		
		// Load Dependencies, AttributeValues and TestScriptContainer
		element.getDependencyList().size();
		element.getAttributeValueList().size();
		element.getTestScriptList();
		
		// Load children
		for (TestConfigElementContainer child : element.getTestConfigElementList()) {
			load(child.getElement());
		}
	}
	
}
