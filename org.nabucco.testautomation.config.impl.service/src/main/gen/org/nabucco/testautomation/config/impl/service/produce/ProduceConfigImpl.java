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
package org.nabucco.testautomation.config.impl.service.produce;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.testautomation.config.facade.message.AttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.DependencyMsg;
import org.nabucco.testautomation.config.facade.message.ProduceAttributeValueMsg;
import org.nabucco.testautomation.config.facade.message.ProduceTestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.ProduceTestScriptContainerMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementContainerMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.facade.service.produce.ProduceConfig;

/**
 * ProduceConfigImpl<p/>Service to produce and clone TestConfigElements<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ProduceConfigImpl extends ServiceSupport implements ProduceConfig {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceConfig";

    private static Map<String, String[]> ASPECTS;

    private ProduceAttributeValueServiceHandler produceAttributeValueServiceHandler;

    private ProduceTestConfigElementServiceHandler produceTestConfigElementServiceHandler;

    private ProduceTestConfigElementCloneServiceHandler produceTestConfigElementCloneServiceHandler;

    private ProduceDependencyServiceHandler produceDependencyServiceHandler;

    private ProduceTestConfigElementContainerServiceHandler produceTestConfigElementContainerServiceHandler;

    private ProduceTestConfigurationServiceHandler produceTestConfigurationServiceHandler;

    private ProduceTestConfigurationCloneServiceHandler produceTestConfigurationCloneServiceHandler;

    private ProduceTestScriptContainerServiceHandler produceTestScriptContainerServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ProduceConfigImpl instance. */
    public ProduceConfigImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.produceAttributeValueServiceHandler = injector.inject(ProduceAttributeValueServiceHandler.getId());
        if ((this.produceAttributeValueServiceHandler != null)) {
            this.produceAttributeValueServiceHandler.setPersistenceManager(persistenceManager);
            this.produceAttributeValueServiceHandler.setLogger(super.getLogger());
        }
        this.produceTestConfigElementServiceHandler = injector.inject(ProduceTestConfigElementServiceHandler.getId());
        if ((this.produceTestConfigElementServiceHandler != null)) {
            this.produceTestConfigElementServiceHandler.setPersistenceManager(persistenceManager);
            this.produceTestConfigElementServiceHandler.setLogger(super.getLogger());
        }
        this.produceTestConfigElementCloneServiceHandler = injector.inject(ProduceTestConfigElementCloneServiceHandler
                .getId());
        if ((this.produceTestConfigElementCloneServiceHandler != null)) {
            this.produceTestConfigElementCloneServiceHandler.setPersistenceManager(persistenceManager);
            this.produceTestConfigElementCloneServiceHandler.setLogger(super.getLogger());
        }
        this.produceDependencyServiceHandler = injector.inject(ProduceDependencyServiceHandler.getId());
        if ((this.produceDependencyServiceHandler != null)) {
            this.produceDependencyServiceHandler.setPersistenceManager(persistenceManager);
            this.produceDependencyServiceHandler.setLogger(super.getLogger());
        }
        this.produceTestConfigElementContainerServiceHandler = injector
                .inject(ProduceTestConfigElementContainerServiceHandler.getId());
        if ((this.produceTestConfigElementContainerServiceHandler != null)) {
            this.produceTestConfigElementContainerServiceHandler.setPersistenceManager(persistenceManager);
            this.produceTestConfigElementContainerServiceHandler.setLogger(super.getLogger());
        }
        this.produceTestConfigurationServiceHandler = injector.inject(ProduceTestConfigurationServiceHandler.getId());
        if ((this.produceTestConfigurationServiceHandler != null)) {
            this.produceTestConfigurationServiceHandler.setPersistenceManager(persistenceManager);
            this.produceTestConfigurationServiceHandler.setLogger(super.getLogger());
        }
        this.produceTestConfigurationCloneServiceHandler = injector.inject(ProduceTestConfigurationCloneServiceHandler
                .getId());
        if ((this.produceTestConfigurationCloneServiceHandler != null)) {
            this.produceTestConfigurationCloneServiceHandler.setPersistenceManager(persistenceManager);
            this.produceTestConfigurationCloneServiceHandler.setLogger(super.getLogger());
        }
        this.produceTestScriptContainerServiceHandler = injector.inject(ProduceTestScriptContainerServiceHandler
                .getId());
        if ((this.produceTestScriptContainerServiceHandler != null)) {
            this.produceTestScriptContainerServiceHandler.setPersistenceManager(persistenceManager);
            this.produceTestScriptContainerServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("produceAttributeValue", NO_ASPECTS);
            ASPECTS.put("produceTestConfigElement", NO_ASPECTS);
            ASPECTS.put("produceTestConfigElementClone", NO_ASPECTS);
            ASPECTS.put("produceDependency", NO_ASPECTS);
            ASPECTS.put("produceTestConfigElementContainer", NO_ASPECTS);
            ASPECTS.put("produceTestConfiguration", NO_ASPECTS);
            ASPECTS.put("produceTestConfigurationClone", NO_ASPECTS);
            ASPECTS.put("produceTestScriptContainer", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<AttributeValueMsg> produceAttributeValue(ServiceRequest<ProduceAttributeValueMsg> rq)
            throws ProduceException {
        if ((this.produceAttributeValueServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceAttributeValue().");
            throw new InjectionException("No service implementation configured for produceAttributeValue().");
        }
        ServiceResponse<AttributeValueMsg> rs;
        this.produceAttributeValueServiceHandler.init();
        rs = this.produceAttributeValueServiceHandler.invoke(rq);
        this.produceAttributeValueServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigElementMsg> produceTestConfigElement(ServiceRequest<ProduceTestConfigElementMsg> rq)
            throws ProduceException {
        if ((this.produceTestConfigElementServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceTestConfigElement().");
            throw new InjectionException("No service implementation configured for produceTestConfigElement().");
        }
        ServiceResponse<TestConfigElementMsg> rs;
        this.produceTestConfigElementServiceHandler.init();
        rs = this.produceTestConfigElementServiceHandler.invoke(rq);
        this.produceTestConfigElementServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigElementMsg> produceTestConfigElementClone(ServiceRequest<TestConfigElementMsg> rq)
            throws ProduceException {
        if ((this.produceTestConfigElementCloneServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceTestConfigElementClone().");
            throw new InjectionException("No service implementation configured for produceTestConfigElementClone().");
        }
        ServiceResponse<TestConfigElementMsg> rs;
        this.produceTestConfigElementCloneServiceHandler.init();
        rs = this.produceTestConfigElementCloneServiceHandler.invoke(rq);
        this.produceTestConfigElementCloneServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<DependencyMsg> produceDependency(ServiceRequest<TestConfigElementMsg> rq)
            throws ProduceException {
        if ((this.produceDependencyServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceDependency().");
            throw new InjectionException("No service implementation configured for produceDependency().");
        }
        ServiceResponse<DependencyMsg> rs;
        this.produceDependencyServiceHandler.init();
        rs = this.produceDependencyServiceHandler.invoke(rq);
        this.produceDependencyServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigElementContainerMsg> produceTestConfigElementContainer(
            ServiceRequest<TestConfigElementMsg> rq) throws ProduceException {
        if ((this.produceTestConfigElementContainerServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceTestConfigElementContainer().");
            throw new InjectionException(
                    "No service implementation configured for produceTestConfigElementContainer().");
        }
        ServiceResponse<TestConfigElementContainerMsg> rs;
        this.produceTestConfigElementContainerServiceHandler.init();
        rs = this.produceTestConfigElementContainerServiceHandler.invoke(rq);
        this.produceTestConfigElementContainerServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigurationMsg> produceTestConfiguration(ServiceRequest<EmptyServiceMessage> rq)
            throws ProduceException {
        if ((this.produceTestConfigurationServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceTestConfiguration().");
            throw new InjectionException("No service implementation configured for produceTestConfiguration().");
        }
        ServiceResponse<TestConfigurationMsg> rs;
        this.produceTestConfigurationServiceHandler.init();
        rs = this.produceTestConfigurationServiceHandler.invoke(rq);
        this.produceTestConfigurationServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigurationMsg> produceTestConfigurationClone(ServiceRequest<TestConfigurationMsg> rq)
            throws ProduceException {
        if ((this.produceTestConfigurationCloneServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceTestConfigurationClone().");
            throw new InjectionException("No service implementation configured for produceTestConfigurationClone().");
        }
        ServiceResponse<TestConfigurationMsg> rs;
        this.produceTestConfigurationCloneServiceHandler.init();
        rs = this.produceTestConfigurationCloneServiceHandler.invoke(rq);
        this.produceTestConfigurationCloneServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<ProduceTestScriptContainerMsg> produceTestScriptContainer(
            ServiceRequest<ProduceTestScriptContainerMsg> rq) throws ProduceException {
        if ((this.produceTestScriptContainerServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceTestScriptContainer().");
            throw new InjectionException("No service implementation configured for produceTestScriptContainer().");
        }
        ServiceResponse<ProduceTestScriptContainerMsg> rs;
        this.produceTestScriptContainerServiceHandler.init();
        rs = this.produceTestScriptContainerServiceHandler.invoke(rq);
        this.produceTestScriptContainerServiceHandler.finish();
        return rs;
    }
}
