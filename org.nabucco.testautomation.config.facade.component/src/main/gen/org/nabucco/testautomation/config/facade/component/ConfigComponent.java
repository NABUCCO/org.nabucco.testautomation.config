/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.component;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.common.dynamiccode.facade.component.DynamicCodeComponent;
import org.nabucco.testautomation.config.facade.service.engine.TestEngineService;
import org.nabucco.testautomation.config.facade.service.export.ExportConfig;
import org.nabucco.testautomation.config.facade.service.importing.ImportConfig;
import org.nabucco.testautomation.config.facade.service.maintain.MaintainTestConfiguration;
import org.nabucco.testautomation.config.facade.service.produce.ProduceAttributeValue;
import org.nabucco.testautomation.config.facade.service.produce.ProduceDependency;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestConfigElement;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestConfigElementContainer;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestConfiguration;
import org.nabucco.testautomation.config.facade.service.produce.ProduceTestScriptContainer;
import org.nabucco.testautomation.config.facade.service.search.SearchTestConfigElement;
import org.nabucco.testautomation.config.facade.service.search.SearchTestConfiguration;
import org.nabucco.testautomation.facade.component.TestautomationComponent;
import org.nabucco.testautomation.schema.facade.component.SchemaComponent;
import org.nabucco.testautomation.script.facade.component.ScriptComponent;

/**
 * ConfigComponent<p/>Component for testautomation config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-16
 */
public interface ConfigComponent extends Component {

    final String COMPONENT_NAME = "org.nabucco.testautomation.config";

    /**
     * Getter for the TestautomationComponent.
     *
     * @return the TestautomationComponent.
     * @throws ServiceException
     */
    TestautomationComponent getTestautomationComponent() throws ServiceException;

    /**
     * Getter for the SchemaComponent.
     *
     * @return the SchemaComponent.
     * @throws ServiceException
     */
    SchemaComponent getSchemaComponent() throws ServiceException;

    /**
     * Getter for the DynamicCodeComponent.
     *
     * @return the DynamicCodeComponent.
     * @throws ServiceException
     */
    DynamicCodeComponent getDynamicCodeComponent() throws ServiceException;

    /**
     * Getter for the ScriptComponent.
     *
     * @return the ScriptComponent.
     * @throws ServiceException
     */
    ScriptComponent getScriptComponent() throws ServiceException;

    /**
     * Getter for the MaintainTestConfiguration.
     *
     * @return the MaintainTestConfiguration.
     * @throws ServiceException
     */
    MaintainTestConfiguration getMaintainTestConfiguration() throws ServiceException;

    /**
     * Getter for the ProduceTestConfiguration.
     *
     * @return the ProduceTestConfiguration.
     * @throws ServiceException
     */
    ProduceTestConfiguration getProduceTestConfiguration() throws ServiceException;

    /**
     * Getter for the ProduceTestConfigElement.
     *
     * @return the ProduceTestConfigElement.
     * @throws ServiceException
     */
    ProduceTestConfigElement getProduceTestConfigElement() throws ServiceException;

    /**
     * Getter for the ProduceTestConfigElementContainer.
     *
     * @return the ProduceTestConfigElementContainer.
     * @throws ServiceException
     */
    ProduceTestConfigElementContainer getProduceTestConfigElementContainer()
            throws ServiceException;

    /**
     * Getter for the ProduceDependency.
     *
     * @return the ProduceDependency.
     * @throws ServiceException
     */
    ProduceDependency getProduceDependency() throws ServiceException;

    /**
     * Getter for the ProduceAttributeValue.
     *
     * @return the ProduceAttributeValue.
     * @throws ServiceException
     */
    ProduceAttributeValue getProduceAttributeValue() throws ServiceException;

    /**
     * Getter for the ProduceTestScriptContainer.
     *
     * @return the ProduceTestScriptContainer.
     * @throws ServiceException
     */
    ProduceTestScriptContainer getProduceTestScriptContainer() throws ServiceException;

    /**
     * Getter for the SearchTestConfiguration.
     *
     * @return the SearchTestConfiguration.
     * @throws ServiceException
     */
    SearchTestConfiguration getSearchTestConfiguration() throws ServiceException;

    /**
     * Getter for the SearchTestConfigElement.
     *
     * @return the SearchTestConfigElement.
     * @throws ServiceException
     */
    SearchTestConfigElement getSearchTestConfigElement() throws ServiceException;

    /**
     * Getter for the TestEngineService.
     *
     * @return the TestEngineService.
     * @throws ServiceException
     */
    TestEngineService getTestEngineService() throws ServiceException;

    /**
     * Getter for the ExportConfig.
     *
     * @return the ExportConfig.
     * @throws ServiceException
     */
    ExportConfig getExportConfig() throws ServiceException;

    /**
     * Getter for the ImportConfig.
     *
     * @return the ImportConfig.
     * @throws ServiceException
     */
    ImportConfig getImportConfig() throws ServiceException;
}
