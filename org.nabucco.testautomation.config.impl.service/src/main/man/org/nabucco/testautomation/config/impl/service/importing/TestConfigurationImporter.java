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
package org.nabucco.testautomation.config.impl.service.importing;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.importing.ImportContext;
import org.nabucco.framework.base.facade.datatype.importing.ImportContextEntry;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.importing.ImportException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.common.dynamiccode.facade.datatype.DynamicCodeCode;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.service.importing.BasicImporter;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;
import org.nabucco.testautomation.schema.facade.datatype.attribute.Attribute;
import org.nabucco.testautomation.script.facade.datatype.dictionary.TestScript;

/**
 * TestConfigurationImporter
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestConfigurationImporter extends BasicImporter {

	private final NabuccoLogger logger = NabuccoLoggingFactory.getInstance()
			.getLogger(TestConfigurationImporter.class);
	
	/**
	 * 
	 * @param em
	 * @param importContext
	 */
	protected TestConfigurationImporter(EntityManager em, ImportContext importContext) {
		super(em, importContext);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ImportContextEntry> importAll(List<Datatype> datatypes)
			throws ImportException {

		// Import TestScripts
		List<ImportContextEntry> importedElements = new ArrayList<ImportContextEntry>();
		List<TestConfigElementContainer> containerList = new ArrayList<TestConfigElementContainer>();
		
		for (Datatype current : datatypes) {
			
			if (current instanceof TestConfiguration) {
				importedElements.add(maintain((TestConfiguration) current));
			} else if (current instanceof TestConfigElement) {
				importedElements.add(maintain((TestConfigElement) current));
			} else if (current instanceof TestScriptContainer) {
				importedElements.add(maintain((TestScriptContainer) current));
			} else if (current instanceof AttributeValue) {
				importedElements.add(maintain((AttributeValue) current));
			} else if (current instanceof Dependency) {
				importedElements.add(maintain((Dependency) current));
			} else if (current instanceof TestConfigElementContainer) {
				containerList.add((TestConfigElementContainer) current);
			}
		}
		
		for (TestConfigElementContainer current : containerList) {
			importedElements.add(maintain(current));
		}
		
		return importedElements;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws ImportException
	 */
	private ImportContextEntry maintain(TestConfiguration entity)
			throws ImportException {

		// Update Schema
		if (entity.getSchemaConfigRefId() != null) {
			entity.setSchemaConfigRefId(getNewRefId(entity.getSchemaConfigRefId(), SchemaConfig.class.getName()));
		} else {
			throw new ImportException(
					"No SchemaConfig defined for TestConfiguration "
							+ entity.getIdentificationKey().getValue() + " - "
							+ entity.getName().getValue());
		}
		
		// Update EnvironmentType
		if(entity.getEnvironmentTypeRefId() != null) {
			entity.setEnvironmentTypeRefId(getNewRefId(entity.getEnvironmentTypeRefId(), DynamicCodeCode.class.getName()));
		}

		// Update ReleaseType
		if(entity.getReleaseTypeRefId() != null) {
			entity.setReleaseTypeRefId(getNewRefId(entity.getReleaseTypeRefId(), DynamicCodeCode.class.getName()));
		}
		
		ImportContextEntry entry = maintain((NabuccoDatatype) entity);
		logger.info("TestConfiguration '", entity.getIdentificationKey().getValue(), "' imported");
		return entry;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws ImportException
	 */
	private ImportContextEntry maintain(TestConfigElement entity)
			throws ImportException {

		// Update SchemaElement
		if (entity.getSchemaElementRefId() != null) {
			entity.setSchemaElementRefId(getNewRefId(entity.getSchemaElementRefId(), SchemaElement.class.getName()));
		} else {
			throw new ImportException(
					"No SchemaElement defined for TestConfigElement "
							+ entity.getIdentificationKey().getValue() + " - "
							+ entity.getName().getValue());
		}
		
		// Update BrandType
		if(entity.getBrandTypeRefId() != null) {
			entity.setBrandTypeRefId(getNewRefId(entity.getBrandTypeRefId(), DynamicCodeCode.class.getName()));
		}

		// Update PropertyList
		if(entity.getPropertyListRefId() != null) {
			entity.setPropertyListRefId(getNewRefId(entity.getPropertyListRefId(), PropertyList.class.getName()));
		}
		
		ImportContextEntry entry = maintain((NabuccoDatatype) entity);
		logger.info("TestConfigElement '", entity.getIdentificationKey().getValue(), "' imported");
		return entry;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws ImportException
	 */
	private ImportContextEntry maintain(TestScriptContainer entity)
			throws ImportException {

		// Update TestScript
		if (entity.getTestScriptRefId() != null) {
			entity.setTestScriptRefId(getNewRefId(entity.getTestScriptRefId(), TestScript.class.getName()));
		}
		
		return maintain((NabuccoDatatype) entity);
	}

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws ImportException
	 */
	private ImportContextEntry maintain(AttributeValue entity)
		throws ImportException {
		
		// Update TestScript
		if (entity.getAttributeRefId() != null) {
			entity.setAttributeRefId(getNewRefId(entity.getAttributeRefId(), Attribute.class.getName()));
		}
		
		return maintain((NabuccoDatatype) entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(Owner owner) throws ImportException {

		// Find and delete all dependencies first before deleting TestConfigurations
		Query query = this.getEntityManager().createQuery("FROM Dependency d WHERE d.element.owner = :owner");
		query.setParameter("owner", owner);
		
		@SuppressWarnings("unchecked")
		List<Dependency> dependencyList = query.getResultList();
		
		for (Dependency dependency : dependencyList) {
			delete(dependency);
		}
		
		// Find all remaining TestConfigElementContainer for the importing owner
		query = this.getEntityManager().createQuery("FROM TestConfigElementContainer c WHERE c.element.owner = :owner");
		query.setParameter("owner", owner);
		
		@SuppressWarnings("unchecked")
		List<TestConfigElementContainer> containerList = query.getResultList();
		
		logger.info("Deleting all TestConfigElementContainer");
		for (TestConfigElementContainer container : containerList) {
			delete(container);
		}
		logger.info("All TestConfigElementContainer deleted");

		// Find all remaining TestConfigElements for the importing owner
		query = this.getEntityManager().createQuery("FROM TestConfigElement e WHERE e.owner = :owner");
		query.setParameter("owner", owner);
		
		@SuppressWarnings("unchecked")
		List<TestConfigElement> elementList = query.getResultList();
		
		for (TestConfigElement element : elementList) {
			delete(element);
		}
		
		// Find all TestConfigurations for the importing owner
		query = this.getEntityManager().createQuery("FROM TestConfiguration c WHERE c.owner = :owner");
		query.setParameter("owner", owner);
		
		@SuppressWarnings("unchecked")
		List<TestConfiguration> testConfigList = query.getResultList();
		
		for (TestConfiguration testConfig : testConfigList) {
			delete(testConfig);
		}
	}
	
	/**
	 * 
	 * @param entity
	 * @throws ImportException
	 */
	private void delete(TestConfiguration entity) throws ImportException {
		
		try {
			// Delete TestConfiguration
			super.delete(entity);
			logger.info("TestConfiguration '", entity.getIdentificationKey().getValue(), "' deleted");
		} catch (PersistenceException ex) {
			throw new ImportException(ex);
		}
	}
	
	/**
	 * 
	 * @param entity
	 * @throws ImportException
	 */
	private void delete(TestConfigElementContainer entity) throws ImportException {
		
		try {
			// Delete TestConfigElementContainer
			super.delete(entity);
		} catch (PersistenceException ex) {
			throw new ImportException(ex);
		}
	}

	private void delete(TestConfigElement entity) throws ImportException {
		
		try {
			// Delete AttributeValues
			for (AttributeValue attribute : entity.getAttributeValueList()) {
				super.delete(attribute);
			}
			
			// Delete TestScriptContainer
			for (TestScriptContainer scriptContainer : entity.getTestScriptList()) {
				super.delete(scriptContainer);
			}
			
			// Delete TestConfigElement
			super.delete(entity);
			logger.info("TestConfigElement '", entity.getIdentificationKey().getValue(), "' deleted");
		} catch (PersistenceException ex) {
			throw new ImportException(ex);
		}
	}
	
	/**
	 * 
	 * @param entity
	 * @throws ImportException
	 */
	private void delete(Dependency entity) throws ImportException {
		
		try {
			super.delete(entity);
			logger.info("Dependency [" + entity.getId() + "] deleted");
		} catch (PersistenceException ex) {
			throw new ImportException(ex);
		}
	}

}
