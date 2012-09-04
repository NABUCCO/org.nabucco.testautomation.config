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
package org.nabucco.testautomation.config.impl.service.engine;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.Tenant;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.testautomation.engine.TestEngine;
import org.nabucco.testautomation.engine.base.context.TestContext;
import org.nabucco.testautomation.engine.base.net.TestEngineConnectionFactory;
import org.nabucco.testautomation.property.facade.datatype.TextProperty;
import org.nabucco.testautomation.settings.facade.component.SettingsComponent;
import org.nabucco.testautomation.settings.facade.component.SettingsComponentLocator;
import org.nabucco.testautomation.settings.facade.datatype.engine.ConnectionSpec;
import org.nabucco.testautomation.settings.facade.datatype.engine.TestEngineConfiguration;
import org.nabucco.testautomation.settings.facade.datatype.engine.TestExecutionMode;
import org.nabucco.testautomation.settings.facade.datatype.engine.extension.TestExecutionModeExtension;
import org.nabucco.testautomation.settings.facade.datatype.engine.proxy.ProxyConfiguration;
import org.nabucco.testautomation.settings.facade.exception.engine.TestEngineException;
import org.nabucco.testautomation.settings.facade.message.TestEngineConfigurationMsg;
import org.nabucco.testautomation.settings.facade.message.TestEngineConfigurationSearchMsg;
import org.nabucco.testautomation.settings.facade.service.resolve.ResolveSettings;
import org.nabucco.testautomation.settings.facade.service.search.SearchSettings;

/**
 * TestEngineSupport
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class TestEngineSupport {

    public static final String N_A = "n/a";

    private final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(TestEngineSupport.class);

    private SearchSettings searchSettings;

    private ResolveSettings resolveSettings;

    private final TestExecutionMode mode;

    private final Number limit;

    public TestEngineSupport(Tenant tenant) throws TestEngineException {
        try {
            TestExecutionModeExtension extension = (TestExecutionModeExtension) NabuccoSystem.getExtensionResolver()
                    .resolveExtension(ExtensionPointType.ORG_NABUCCO_TESTAUTOMATION_TESTENGINE_POOL, "Default", tenant);
            this.mode = extension.getExecutionMode();
            this.limit = extension.getTestEngineLimit();
        } catch (ExtensionException e) {
            throw new TestEngineException(e);
        }
    }

    public TestEngine getTestEngine(TestEngineConfiguration config) throws TestEngineException {

        ConnectionSpec connectionSpec = new ConnectionSpec(config.getHost().getValue(), config.getPort().getValue(),
                config.getRemoteReferenceName().getValue());

        try {
            TestEngine engine = TestEngineConnectionPool.getInstance().get(connectionSpec);

            if (engine == null) {
                logger.info("Looking up TestEngine using " + connectionSpec.toString());
                Registry registry = LocateRegistry.getRegistry(connectionSpec.getHost(), connectionSpec.getPort(),
                        new TestEngineConnectionFactory());
                engine = (TestEngine) registry.lookup(connectionSpec.getRemoteName());

                if (engine == null) {
                    throw new TestEngineException("No TestEngine found using " + connectionSpec);
                }
                TestEngineConnectionPool.getInstance().put(connectionSpec, engine);
                logger.info("Lookup of TestEngine successful");
            }

            return engine;
        } catch (AccessException ex) {
            throw new TestEngineException(ex);
        } catch (RemoteException ex) {
            throw new TestEngineException(ex);
        } catch (NotBoundException ex) {
            throw new TestEngineException(ex);
        }
    }

    public void returnTestEngine(TestEngineConfiguration config) throws TestEngineException {

        ConnectionSpec connectionSpec = new ConnectionSpec(config.getHost().getValue(), config.getPort().getValue(),
                config.getRemoteReferenceName().getValue());

        TestEngine engine = TestEngineConnectionPool.getInstance().remove(connectionSpec);

        if (engine != null) {
            logger.info("TestEngine returned (" + connectionSpec.toString() + ")");
        }
    }

    public List<TestEngineConfiguration> getTestEngineConfigurationList(Code release, Code environment,
            ServiceMessageContext ctx, User user) throws TestEngineException {

        if (searchSettings == null) {
            try {
                SettingsComponent settings = SettingsComponentLocator.getInstance().getComponent();
                searchSettings = settings.getSearchSettings();
            } catch (Exception ex) {
                throw new TestEngineException("Could not initilize SearchTestEngineConfiguration", ex);
            }
        }

        if (resolveSettings == null) {
            try {
                SettingsComponent settings = SettingsComponentLocator.getInstance().getComponent();
                resolveSettings = settings.getResolveSettings();
            } catch (Exception ex) {
                throw new TestEngineException("Could not initilize SearchTestEngineConfiguration", ex);
            }
        }

        ServiceRequest<TestEngineConfigurationSearchMsg> rq = new ServiceRequest<TestEngineConfigurationSearchMsg>(ctx);
        TestEngineConfigurationSearchMsg msg = new TestEngineConfigurationSearchMsg();
        msg.setEnvironmentType(environment);
        msg.setReleaseType(release);
        rq.setRequestMessage(msg);

        List<TestEngineConfiguration> searchResults;
        try {
            searchResults = searchSettings.searchTestEngineConfiguration(rq).getResponseMessage()
                    .getConfigurationList();
        } catch (SearchException e) {
            throw new TestEngineException("Error while searching for TestEngineConfigurations", e);
        }

        List<TestEngineConfiguration> testEngineConfigurationList = new ArrayList<TestEngineConfiguration>();

        for (TestEngineConfiguration testEngineConfiguration : searchResults) {
            try {
                TestEngineConfigurationSearchMsg resolveMsg = new TestEngineConfigurationSearchMsg();
                resolveMsg.setId(new Identifier(testEngineConfiguration.getId()));
                rq.setRequestMessage(resolveMsg);
                testEngineConfiguration = resolveSettings.resolveTestEngineConfiguration(rq).getResponseMessage()
                        .getTestEngineConfiguration();
                testEngineConfigurationList.add(testEngineConfiguration);
            } catch (ResolveException e) {
                logger.error("Could not resilve TestEngineConfiguation [" + testEngineConfiguration.getId() + "]");
            }
        }

        return testEngineConfigurationList;
    }

    public TestEngineConfiguration getTestEngineConfiguration(Code release, Code environment,
            ServiceMessageContext ctx, User user) throws TestEngineException {

        if (searchSettings == null) {
            try {
                SettingsComponent settings = SettingsComponentLocator.getInstance().getComponent();
                searchSettings = settings.getSearchSettings();
            } catch (Exception ex) {
                throw new TestEngineException("Could not initilize SearchTestEngineConfiguration", ex);
            }
        }

        if (resolveSettings == null) {
            try {
                SettingsComponent settings = SettingsComponentLocator.getInstance().getComponent();
                resolveSettings = settings.getResolveSettings();
            } catch (Exception ex) {
                throw new TestEngineException("Could not initilize SearchTestEngineConfiguration", ex);
            }
        }

        ServiceRequest<TestEngineConfigurationSearchMsg> rq = new ServiceRequest<TestEngineConfigurationSearchMsg>(ctx);
        TestEngineConfigurationSearchMsg msg = new TestEngineConfigurationSearchMsg();
        msg.setEnvironmentType(environment);
        msg.setReleaseType(release);
        msg.setUser(user);
        rq.setRequestMessage(msg);

        TestEngineConfiguration config = null;

        try {
            TestEngineConfigurationMsg rs = resolveSettings.resolveTestEngineConfiguration(rq).getResponseMessage();
            config = rs.getTestEngineConfiguration();
            return config;
        } catch (ResolveException ex) {
            throw new TestEngineException(ex);
        }
    }

    public void validateTestEngineConfiguration(TestEngineConfiguration config) throws TestEngineException {

        if (config == null) {
            throw new TestEngineException("No TestEngineConfiguration supplied");
        }

        if (config.getHost() == null || config.getHost().getValue() == null) {
            throw new TestEngineException("No hostname configured");
        }

        if (config.getPort() == null || config.getPort().getValue() == null) {
            throw new TestEngineException("No port configured");
        }

        if (config.getRemoteReferenceName() == null || config.getRemoteReferenceName().getValue() == null) {
            throw new TestEngineException("No remoteReferenceName configured");
        }
    }

    public TestContext createTestContext(TestEngineConfiguration config, User user, Code release, Code environment) {

        TestContext ctx = new TestContext();

        // Add ProxyConfigurations
        for (ProxyConfiguration proxyConfig : config.getProxyConfigurations()) {
            ctx.addProxyConfiguration(proxyConfig);
        }

        if (user != null && user.getUsername() != null) {
            TextProperty userProp = new TextProperty();
            userProp.setName(TestContext.USERNAME);
            userProp.setValue(user.getUsername().getValue());
            ctx.put(userProp);
        } else {
            TextProperty userProp = new TextProperty();
            userProp.setName(TestContext.USERNAME);
            userProp.setValue("Anonymous");
            ctx.put(userProp);
        }

        TextProperty emailProp = new TextProperty();
        emailProp.setName(TestContext.EMAIL);
        emailProp.setValue("no.reply@prodyna.de");
        ctx.put(emailProp);

        if (release != null && release.getName() != null) {
            TextProperty releaseProp = new TextProperty();
            releaseProp.setName(TestContext.RELEASE);
            releaseProp.setValue(release.getName().getValue());
            ctx.put(releaseProp);
        }

        if (environment != null && environment.getName() != null) {
            TextProperty envProp = new TextProperty();
            envProp.setName(TestContext.ENVIRONMENT);
            envProp.setValue(environment.getName().getValue());
            ctx.put(envProp);
        }

        return ctx;
    }

    /**
     * @return the mode
     */
    public TestExecutionMode getMode() {
        return mode;
    }

    /**
     * @return the limit
     */
    public Number getLimit() {
        return limit;
    }

}
