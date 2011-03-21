/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.impl.component;

import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.impl.component.ComponentSupport;
import org.nabucco.framework.common.dynamiccode.facade.component.DynamicCodeComponent;
import org.nabucco.testautomation.config.facade.component.ConfigComponent;
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
 * ConfigComponentImpl<p/>Component for testautomation config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-16
 */
public class ConfigComponentImpl extends ComponentSupport implements ConfigComponent {

    private static final long serialVersionUID = 1L;

    private ComponentRelationService componentRelationService;

    private TestautomationComponent testautomationComponent;

    private SchemaComponent schemaComponent;

    private DynamicCodeComponent dynamicCodeComponent;

    private ScriptComponent scriptComponent;

    private MaintainTestConfiguration maintainTestConfiguration;

    private ProduceTestConfiguration produceTestConfiguration;

    private ProduceTestConfigElement produceTestConfigElement;

    private ProduceTestConfigElementContainer produceTestConfigElementContainer;

    private ProduceDependency produceDependency;

    private ProduceAttributeValue produceAttributeValue;

    private ProduceTestScriptContainer produceTestScriptContainer;

    private SearchTestConfiguration searchTestConfiguration;

    private SearchTestConfigElement searchTestConfigElement;

    private TestEngineService testEngineService;

    private ExportConfig exportConfig;

    private ImportConfig importConfig;

    /** Constructs a new ConfigComponentImpl instance. */
    public ConfigComponentImpl() {
        super();
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return this.componentRelationService;
    }

    /**
     * Getter for the TestautomationComponent.
     *
     * @return the TestautomationComponent.
     */
    public TestautomationComponent getTestautomationComponent() {
        return this.testautomationComponent;
    }

    /**
     * Getter for the SchemaComponent.
     *
     * @return the SchemaComponent.
     */
    public SchemaComponent getSchemaComponent() {
        return this.schemaComponent;
    }

    /**
     * Getter for the DynamicCodeComponent.
     *
     * @return the DynamicCodeComponent.
     */
    public DynamicCodeComponent getDynamicCodeComponent() {
        return this.dynamicCodeComponent;
    }

    /**
     * Getter for the ScriptComponent.
     *
     * @return the ScriptComponent.
     */
    public ScriptComponent getScriptComponent() {
        return this.scriptComponent;
    }

    /**
     * Getter for the MaintainTestConfiguration.
     *
     * @return the MaintainTestConfiguration.
     */
    public MaintainTestConfiguration getMaintainTestConfiguration() {
        return this.maintainTestConfiguration;
    }

    /**
     * Getter for the ProduceTestConfiguration.
     *
     * @return the ProduceTestConfiguration.
     */
    public ProduceTestConfiguration getProduceTestConfiguration() {
        return this.produceTestConfiguration;
    }

    /**
     * Getter for the ProduceTestConfigElement.
     *
     * @return the ProduceTestConfigElement.
     */
    public ProduceTestConfigElement getProduceTestConfigElement() {
        return this.produceTestConfigElement;
    }

    /**
     * Getter for the ProduceTestConfigElementContainer.
     *
     * @return the ProduceTestConfigElementContainer.
     */
    public ProduceTestConfigElementContainer getProduceTestConfigElementContainer() {
        return this.produceTestConfigElementContainer;
    }

    /**
     * Getter for the ProduceDependency.
     *
     * @return the ProduceDependency.
     */
    public ProduceDependency getProduceDependency() {
        return this.produceDependency;
    }

    /**
     * Getter for the ProduceAttributeValue.
     *
     * @return the ProduceAttributeValue.
     */
    public ProduceAttributeValue getProduceAttributeValue() {
        return this.produceAttributeValue;
    }

    /**
     * Getter for the ProduceTestScriptContainer.
     *
     * @return the ProduceTestScriptContainer.
     */
    public ProduceTestScriptContainer getProduceTestScriptContainer() {
        return this.produceTestScriptContainer;
    }

    /**
     * Getter for the SearchTestConfiguration.
     *
     * @return the SearchTestConfiguration.
     */
    public SearchTestConfiguration getSearchTestConfiguration() {
        return this.searchTestConfiguration;
    }

    /**
     * Getter for the SearchTestConfigElement.
     *
     * @return the SearchTestConfigElement.
     */
    public SearchTestConfigElement getSearchTestConfigElement() {
        return this.searchTestConfigElement;
    }

    /**
     * Getter for the TestEngineService.
     *
     * @return the TestEngineService.
     */
    public TestEngineService getTestEngineService() {
        return this.testEngineService;
    }

    /**
     * Getter for the ExportConfig.
     *
     * @return the ExportConfig.
     */
    public ExportConfig getExportConfig() {
        return this.exportConfig;
    }

    /**
     * Getter for the ImportConfig.
     *
     * @return the ImportConfig.
     */
    public ImportConfig getImportConfig() {
        return this.importConfig;
    }
}
