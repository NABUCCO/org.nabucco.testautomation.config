/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.testautomation.config.ui.rcp.communication;

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateFactorySupport;
import org.nabucco.testautomation.config.facade.component.ConfigComponent;
import org.nabucco.testautomation.config.facade.component.ConfigComponentLocator;
import org.nabucco.testautomation.config.ui.rcp.communication.engine.TestEngineServiceDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.maintain.MaintainConfigDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.produce.ProduceConfigDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.report.ReportConfigDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.resolve.ResolveConfigDelegate;
import org.nabucco.testautomation.config.ui.rcp.communication.search.SearchConfigDelegate;

/**
 * ServiceDelegateFactoryTemplate<p/>Component for testautomation config<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-16
 */
public class ConfigComponentServiceDelegateFactory extends ServiceDelegateFactorySupport<ConfigComponent> {

    private static ConfigComponentServiceDelegateFactory instance = new ConfigComponentServiceDelegateFactory();

    private MaintainConfigDelegate maintainConfigDelegate;

    private ProduceConfigDelegate produceConfigDelegate;

    private SearchConfigDelegate searchConfigDelegate;

    private ResolveConfigDelegate resolveConfigDelegate;

    private ReportConfigDelegate reportConfigDelegate;

    private TestEngineServiceDelegate testEngineServiceDelegate;

    /** Constructs a new ConfigComponentServiceDelegateFactory instance. */
    private ConfigComponentServiceDelegateFactory() {
        super(ConfigComponentLocator.getInstance());
    }

    /**
     * Getter for the MaintainConfig.
     *
     * @return the MaintainConfigDelegate.
     * @throws ClientException
     */
    public MaintainConfigDelegate getMaintainConfig() throws ClientException {
        try {
            if ((this.maintainConfigDelegate == null)) {
                this.maintainConfigDelegate = new MaintainConfigDelegate(this.getComponent().getMaintainConfig());
            }
            return this.maintainConfigDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: ConfigComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: MaintainConfig", e);
        }
    }

    /**
     * Getter for the ProduceConfig.
     *
     * @return the ProduceConfigDelegate.
     * @throws ClientException
     */
    public ProduceConfigDelegate getProduceConfig() throws ClientException {
        try {
            if ((this.produceConfigDelegate == null)) {
                this.produceConfigDelegate = new ProduceConfigDelegate(this.getComponent().getProduceConfig());
            }
            return this.produceConfigDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: ConfigComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ProduceConfig", e);
        }
    }

    /**
     * Getter for the SearchConfig.
     *
     * @return the SearchConfigDelegate.
     * @throws ClientException
     */
    public SearchConfigDelegate getSearchConfig() throws ClientException {
        try {
            if ((this.searchConfigDelegate == null)) {
                this.searchConfigDelegate = new SearchConfigDelegate(this.getComponent().getSearchConfig());
            }
            return this.searchConfigDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: ConfigComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: SearchConfig", e);
        }
    }

    /**
     * Getter for the ResolveConfig.
     *
     * @return the ResolveConfigDelegate.
     * @throws ClientException
     */
    public ResolveConfigDelegate getResolveConfig() throws ClientException {
        try {
            if ((this.resolveConfigDelegate == null)) {
                this.resolveConfigDelegate = new ResolveConfigDelegate(this.getComponent().getResolveConfig());
            }
            return this.resolveConfigDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: ConfigComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ResolveConfig", e);
        }
    }

    /**
     * Getter for the ReportConfig.
     *
     * @return the ReportConfigDelegate.
     * @throws ClientException
     */
    public ReportConfigDelegate getReportConfig() throws ClientException {
        try {
            if ((this.reportConfigDelegate == null)) {
                this.reportConfigDelegate = new ReportConfigDelegate(this.getComponent().getReportConfig());
            }
            return this.reportConfigDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: ConfigComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ReportConfig", e);
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
            throw new ClientException("Cannot connect to component: ConfigComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: TestEngineService", e);
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
