/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.web.communication;

import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionFactory;
import org.nabucco.framework.base.facade.component.connection.ConnectionSpecification;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.testautomation.config.facade.component.ConfigComponent;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.ui.web.communication.engine.TestEngineServiceDelegate;
import org.nabucco.testautomation.config.ui.web.communication.export.ExportConfigDelegate;
import org.nabucco.testautomation.config.ui.web.communication.importing.ImportConfigDelegate;
import org.nabucco.testautomation.config.ui.web.communication.maintain.MaintainTestConfigurationDelegate;
import org.nabucco.testautomation.config.ui.web.communication.produce.ProduceAttributeValueDelegate;
import org.nabucco.testautomation.config.ui.web.communication.produce.ProduceDependencyDelegate;
import org.nabucco.testautomation.config.ui.web.communication.produce.ProduceTestConfigElementContainerDelegate;
import org.nabucco.testautomation.config.ui.web.communication.produce.ProduceTestConfigElementDelegate;
import org.nabucco.testautomation.config.ui.web.communication.produce.ProduceTestConfigurationDelegate;
import org.nabucco.testautomation.config.ui.web.communication.produce.ProduceTestScriptContainerDelegate;
import org.nabucco.testautomation.config.ui.web.communication.search.SearchTestConfigElementDelegate;
import org.nabucco.testautomation.config.ui.web.communication.search.SearchTestConfigurationDelegate;

/**
 * ServiceDelegateFactoryTemplate<p/>Component for testautomation config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-16
 */
public class ConfigComponentServiceDelegateFactory {

    private static ConfigComponentServiceDelegateFactory instance = new ConfigComponentServiceDelegateFactory();

    private ConfigComponent component;

    private MaintainTestConfigurationDelegate maintainTestConfigurationDelegate;

    private ProduceTestConfigurationDelegate produceTestConfigurationDelegate;

    private ProduceTestConfigElementDelegate produceTestConfigElementDelegate;

    private ProduceTestConfigElementContainerDelegate produceTestConfigElementContainerDelegate;

    private ProduceDependencyDelegate produceDependencyDelegate;

    private ProduceAttributeValueDelegate produceAttributeValueDelegate;

    private ProduceTestScriptContainerDelegate produceTestScriptContainerDelegate;

    private SearchTestConfigurationDelegate searchTestConfigurationDelegate;

    private SearchTestConfigElementDelegate searchTestConfigElementDelegate;

    private TestEngineServiceDelegate testEngineServiceDelegate;

    private ExportConfigDelegate exportConfigDelegate;

    private ImportConfigDelegate importConfigDelegate;

    /** Constructs a new ConfigComponentServiceDelegateFactory instance. */
    private ConfigComponentServiceDelegateFactory() {
        super();
    }

    /**
     * Getter for the Component.
     *
     * @return the ConfigComponent.
     * @throws ConnectionException
     */
    private ConfigComponent getComponent() throws ConnectionException {
        if ((this.component == null)) {
            this.initComponent();
        }
        return this.component;
    }

    /**
     * InitComponent.
     *
     * @throws ConnectionException
     */
    private void initComponent() throws ConnectionException {
        ConnectionSpecification specification = ConnectionSpecification.getCurrentSpecification();
        Connection connection = ConnectionFactory.getInstance().createConnection(specification);
        this.component = ConfigComponentLocator.getInstance().getComponent(connection);
    }

    /**
     * Getter for the MaintainTestConfiguration.
     *
     * @return the MaintainTestConfigurationDelegate.
     * @throws ClientException
     */
    public MaintainTestConfigurationDelegate getMaintainTestConfiguration() throws ClientException {
        try {
            if ((this.maintainTestConfigurationDelegate == null)) {
                this.maintainTestConfigurationDelegate = new MaintainTestConfigurationDelegate(this
                        .getComponent().getMaintainTestConfiguration());
            }
            return this.maintainTestConfigurationDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: MaintainTestConfiguration", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ProduceTestConfiguration.
     *
     * @return the ProduceTestConfigurationDelegate.
     * @throws ClientException
     */
    public ProduceTestConfigurationDelegate getProduceTestConfiguration() throws ClientException {
        try {
            if ((this.produceTestConfigurationDelegate == null)) {
                this.produceTestConfigurationDelegate = new ProduceTestConfigurationDelegate(this
                        .getComponent().getProduceTestConfiguration());
            }
            return this.produceTestConfigurationDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ProduceTestConfiguration", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ProduceTestConfigElement.
     *
     * @return the ProduceTestConfigElementDelegate.
     * @throws ClientException
     */
    public ProduceTestConfigElementDelegate getProduceTestConfigElement() throws ClientException {
        try {
            if ((this.produceTestConfigElementDelegate == null)) {
                this.produceTestConfigElementDelegate = new ProduceTestConfigElementDelegate(this
                        .getComponent().getProduceTestConfigElement());
            }
            return this.produceTestConfigElementDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ProduceTestConfigElement", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ProduceTestConfigElementContainer.
     *
     * @return the ProduceTestConfigElementContainerDelegate.
     * @throws ClientException
     */
    public ProduceTestConfigElementContainerDelegate getProduceTestConfigElementContainer()
            throws ClientException {
        try {
            if ((this.produceTestConfigElementContainerDelegate == null)) {
                this.produceTestConfigElementContainerDelegate = new ProduceTestConfigElementContainerDelegate(
                        this.getComponent().getProduceTestConfigElementContainer());
            }
            return this.produceTestConfigElementContainerDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ProduceTestConfigElementContainer", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ProduceDependency.
     *
     * @return the ProduceDependencyDelegate.
     * @throws ClientException
     */
    public ProduceDependencyDelegate getProduceDependency() throws ClientException {
        try {
            if ((this.produceDependencyDelegate == null)) {
                this.produceDependencyDelegate = new ProduceDependencyDelegate(this.getComponent()
                        .getProduceDependency());
            }
            return this.produceDependencyDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ProduceDependency", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ProduceAttributeValue.
     *
     * @return the ProduceAttributeValueDelegate.
     * @throws ClientException
     */
    public ProduceAttributeValueDelegate getProduceAttributeValue() throws ClientException {
        try {
            if ((this.produceAttributeValueDelegate == null)) {
                this.produceAttributeValueDelegate = new ProduceAttributeValueDelegate(this
                        .getComponent().getProduceAttributeValue());
            }
            return this.produceAttributeValueDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ProduceAttributeValue", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ProduceTestScriptContainer.
     *
     * @return the ProduceTestScriptContainerDelegate.
     * @throws ClientException
     */
    public ProduceTestScriptContainerDelegate getProduceTestScriptContainer()
            throws ClientException {
        try {
            if ((this.produceTestScriptContainerDelegate == null)) {
                this.produceTestScriptContainerDelegate = new ProduceTestScriptContainerDelegate(
                        this.getComponent().getProduceTestScriptContainer());
            }
            return this.produceTestScriptContainerDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ProduceTestScriptContainer", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the SearchTestConfiguration.
     *
     * @return the SearchTestConfigurationDelegate.
     * @throws ClientException
     */
    public SearchTestConfigurationDelegate getSearchTestConfiguration() throws ClientException {
        try {
            if ((this.searchTestConfigurationDelegate == null)) {
                this.searchTestConfigurationDelegate = new SearchTestConfigurationDelegate(this
                        .getComponent().getSearchTestConfiguration());
            }
            return this.searchTestConfigurationDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: SearchTestConfiguration", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the SearchTestConfigElement.
     *
     * @return the SearchTestConfigElementDelegate.
     * @throws ClientException
     */
    public SearchTestConfigElementDelegate getSearchTestConfigElement() throws ClientException {
        try {
            if ((this.searchTestConfigElementDelegate == null)) {
                this.searchTestConfigElementDelegate = new SearchTestConfigElementDelegate(this
                        .getComponent().getSearchTestConfigElement());
            }
            return this.searchTestConfigElementDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: SearchTestConfigElement", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the TestEngineService.
     *
     * @return the TestEngineServiceDelegate.
     * @throws ClientException
     */
    public TestEngineServiceDelegate getTestEngineService() throws ClientException {
        try {
            if ((this.testEngineServiceDelegate == null)) {
                this.testEngineServiceDelegate = new TestEngineServiceDelegate(this.getComponent()
                        .getTestEngineService());
            }
            return this.testEngineServiceDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: TestEngineService", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ExportConfig.
     *
     * @return the ExportConfigDelegate.
     * @throws ClientException
     */
    public ExportConfigDelegate getExportConfig() throws ClientException {
        try {
            if ((this.exportConfigDelegate == null)) {
                this.exportConfigDelegate = new ExportConfigDelegate(this.getComponent()
                        .getExportConfig());
            }
            return this.exportConfigDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ExportConfig", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the ImportConfig.
     *
     * @return the ImportConfigDelegate.
     * @throws ClientException
     */
    public ImportConfigDelegate getImportConfig() throws ClientException {
        try {
            if ((this.importConfigDelegate == null)) {
                this.importConfigDelegate = new ImportConfigDelegate(this.getComponent()
                        .getImportConfig());
            }
            return this.importConfigDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot locate service: ImportConfig", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ServiceDelegateTemplate", e);
        }
    }

    /**
     * Getter for the Instance.
     *
     * @return the ConfigComponentServiceDelegateFactory.
     */
    public static ConfigComponentServiceDelegateFactory getInstance() {
        return instance;
    }
}
