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
package org.nabucco.testautomation.config.impl.service.resolve;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.facade.service.resolve.ResolveConfig;

/**
 * ResolveConfigImpl<p/>Config search service<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class ResolveConfigImpl extends ServiceSupport implements ResolveConfig {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ResolveConfig";

    private static Map<String, String[]> ASPECTS;

    private ResolveTestConfigElementServiceHandler resolveTestConfigElementServiceHandler;

    private ResolveTestConfigurationServiceHandler resolveTestConfigurationServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ResolveConfigImpl instance. */
    public ResolveConfigImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.resolveTestConfigElementServiceHandler = injector.inject(ResolveTestConfigElementServiceHandler.getId());
        if ((this.resolveTestConfigElementServiceHandler != null)) {
            this.resolveTestConfigElementServiceHandler.setPersistenceManager(persistenceManager);
            this.resolveTestConfigElementServiceHandler.setLogger(super.getLogger());
        }
        this.resolveTestConfigurationServiceHandler = injector.inject(ResolveTestConfigurationServiceHandler.getId());
        if ((this.resolveTestConfigurationServiceHandler != null)) {
            this.resolveTestConfigurationServiceHandler.setPersistenceManager(persistenceManager);
            this.resolveTestConfigurationServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("resolveTestConfigElement", new String[] { "org.nabucco.aspect.resolving" });
            ASPECTS.put("resolveTestConfiguration", new String[] { "org.nabucco.aspect.resolving" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<TestConfigElementMsg> resolveTestConfigElement(ServiceRequest<TestConfigElementSearchMsg> rq)
            throws ResolveException {
        if ((this.resolveTestConfigElementServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for resolveTestConfigElement().");
            throw new InjectionException("No service implementation configured for resolveTestConfigElement().");
        }
        ServiceResponse<TestConfigElementMsg> rs;
        this.resolveTestConfigElementServiceHandler.init();
        rs = this.resolveTestConfigElementServiceHandler.invoke(rq);
        this.resolveTestConfigElementServiceHandler.finish();
        return rs;
    }

    @Override
    public ServiceResponse<TestConfigurationListMsg> resolveTestConfiguration(
            ServiceRequest<TestConfigurationSearchMsg> rq) throws ResolveException {
        if ((this.resolveTestConfigurationServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for resolveTestConfiguration().");
            throw new InjectionException("No service implementation configured for resolveTestConfiguration().");
        }
        ServiceResponse<TestConfigurationListMsg> rs;
        this.resolveTestConfigurationServiceHandler.init();
        rs = this.resolveTestConfigurationServiceHandler.invoke(rq);
        this.resolveTestConfigurationServiceHandler.finish();
        return rs;
    }
}
